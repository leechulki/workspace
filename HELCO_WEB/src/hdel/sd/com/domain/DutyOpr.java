package hdel.sd.com.domain;

import java.util.HashMap;

public class DutyOpr {

	public static boolean compare(String input, String spec, HashMap mapWork) {
		boolean is = false;
		
		String specWork = spec.trim().toUpperCase();
		
		// 박수근 입력변수값 비교연산 로직 처리를 위해 스펙데이터 변수 처리 추가 2021.07.29
		// 예) 스펙에 {EL_DAUSE} 입력변수 정의 시 처리
		if(spec.indexOf("{") > -1) {
			if(spec.lastIndexOf("}") > -1) {
				if(mapWork.containsKey(spec.replace("{", "").replace("}", ""))) {
					spec = (String) mapWork.get(spec.replace("{", "").replace("}", ""));			 
				}
			}
		}
		
		// Spec값이 공백은 모든 입력에 대해 true
		if ("".equals(specWork)) {
			is = true;
		} else {
			switch (spec.charAt(0)) {
			// Not
			case '!':
				is = isNot(input, spec);
				break;
			// OR
			case ',':
				is = isOR(input, spec);
				break;
			// Relation
			case '>':
			case '<':
				is = isRelation(input, spec, mapWork);
				break;
			case '?':
				is = isInclude(input, spec);
				break;
			// Equal
			default:
				is = isEqual(input, spec);
				break;
			}
		}
		
		return is;
	}
	
	public static boolean isNot(String input, String spec) {
		boolean is = false;
		
		switch (spec.charAt(1)) {
		// OR -- !(?BT?) !(BS,EN81) !(?BT?,?BB?)
		case '(':
			// OR -- !(BS,EN81) !(?BT?,?BB?)
			if (0 < spec.indexOf(",")) {
				is = (isOR(input, "," + spec.substring(2, spec.indexOf(")")))) ? false : true;
			}
			// Include -- !(?BT?)
			else {
				is = (isInclude(input, spec.substring(2, spec.indexOf(")")))) ? false : true;
			}
			break;
		// Single Include -- !?BS?
		case '?':
			is = (isInclude(input, spec.substring(1).replace("?", ""))) ? false : true;
			break;
		// Single Equal -- !N
		default:
			is = (isEqual(input, spec.substring(1))) ? false : true;
			break;
		}
			
		return is;
	}
	
	public static boolean isOR(String input, String spec) {
		boolean is = false;
		
		String inputWork = input.trim().toUpperCase();
		String specWork = spec.trim().replace("(", "").replace(")", "").toUpperCase();
		specWork = (',' == specWork.charAt(0)) ? specWork.substring(1) : specWork;
		
		String[] specArray = specWork.split(",");
		for (int i=0; i<specArray.length; i++) {
			// Include -- ?BT?
			if (0 <= specArray[i].indexOf("?")) {
				is = isInclude(inputWork, specArray[i]);
			}
			// Equal -- BB
			else {
				is = isEqual(inputWork, specArray[i]);
			}
			if (is) break;
		}
		
		return is;
	}
	
	public static boolean isEqual(String input, String spec) {
		boolean is = false;
		
		String inputWork = input.trim().toUpperCase();
		inputWork = ("".equals(inputWork)) ? "N" : inputWork;	// 입력값이 공백이면 기본값 'N' 처리
		String specWork = spec.trim().toUpperCase();
		
		is = (inputWork.equals(specWork)) ? true : false;
		
		return is;
	}
	
	public static boolean isInclude(String input, String spec) {
		boolean is = false;
		
		String inputWork = input.trim().toUpperCase();
		String specWork = spec.trim().replace("?", "").toUpperCase();
		
		is = (0 <= inputWork.indexOf(specWork)) ? true : false;
		
		return is;
	}
	
	public static boolean isRelation(String input, String spec, HashMap mapWork) {
		boolean is = false;
		
		String inputWork = input.trim();
		String specWork = spec.trim();
		
		// Both Side -- >400,<=780
		if (0 <= specWork.indexOf(",")) {
			String[] specPeriod = specWork.split(",");
			boolean isFrom = false, isTo = false;
			isFrom = isRelationOneSide(inputWork, specPeriod[0], mapWork);
			isTo = isRelationOneSide(inputWork, specPeriod[1], mapWork);
			
			is = (isFrom && isTo) ? true : false;
		}
		// One Side -- >400
		else {
			is = isRelationOneSide(inputWork, specWork, mapWork);
		}
		
		return is;
	}
	
	private static boolean isRelationOneSide(String input, String spec, HashMap mapWork) {
		boolean is = false;
		
		String inputWork = input.trim();
		String specWork = spec.trim();
		
		// >=400 <=400
		if ('=' == specWork.charAt(1)) {
			if ('>' == specWork.charAt(0)) {
				is = isRelationCompare(inputWork, specWork.substring(2), OP_GREATER_THAN_EQUAL, mapWork);
			} else {
				is = isRelationCompare(inputWork, specWork.substring(2), OP_LESS_THAN_EQUAL, mapWork); 
			}
		}
		// >400 <400
		else {
			if ('>' == specWork.charAt(0)) {
				is = isRelationCompare(inputWork, specWork.substring(1), OP_GREATER_THAN, mapWork);
			} else {
				is = isRelationCompare(inputWork, specWork.substring(1), OP_LESS_THAN,  mapWork); 
			}
		}
		
		return is;
	}
	
	private static final int OP_LESS_THAN = 1;
	private static final int OP_LESS_THAN_EQUAL = 2; 
	private static final int OP_GREATER_THAN = 3;
	private static final int OP_GREATER_THAN_EQUAL = 4;
	
	private static boolean isRelationCompare(String input, String spec, int op, HashMap mapWork) {
		boolean is = false;
		String outValue = "";
		if (0 <= spec.indexOf("{") || 0 <= spec.indexOf("}") ) {
			outValue = (String) mapWork.get(spec.replace("{", "").replace("}", ""));
			spec = outValue;
		}		

		if(input == null) {
			input = "0";
		} else {
			input = input.trim();
			if("".equals(input)) {
				input = "0";
			}
		}

		if(spec == null) {
			spec = "0";
		} else {
			spec = spec.trim();
			if("".equals(spec)) {
				spec = "0";
			}
		}
		
		float inputWork = Float.parseFloat(input);
		float specWork = Float.parseFloat(spec);
		
		switch (op) {
		case OP_LESS_THAN:
			is = (inputWork < specWork) ? true : false;
			break;
		case OP_LESS_THAN_EQUAL:
			is = (inputWork <= specWork) ? true : false;
			break;
		case OP_GREATER_THAN:
			is = (inputWork > specWork) ? true : false;
			break;
		case OP_GREATER_THAN_EQUAL: 
			is = (inputWork >= specWork) ? true : false;
			break;
		}
		
		return is;
	}
}
