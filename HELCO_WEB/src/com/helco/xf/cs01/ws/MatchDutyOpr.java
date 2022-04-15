package com.helco.xf.cs01.ws;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 정합성 체크 연산수식 빈
 */
public class MatchDutyOpr {

	public static boolean compare(String input, String spec, HashMap mapWork) {
		boolean is = false;

		String specWork = spec.trim().toUpperCase();

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

	/**
	 * 서비스 BOM 특성 기본 연산로직
	 * @param expr
	 * @return
	 */
	public static boolean serviceCompare(String input, String spec, HashMap mapWork) {
		boolean is = false;

		String specWork = spec.trim().toUpperCase();

		// Spec값이 공백은 모든 입력에 대해 false
		if ("".equals(specWork)) {
			is = false;
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
			case '[':
				is = isEqualN(input, spec);
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

	// N값 비교로직 추가
	public static boolean isEqualN(String input, String spec) {
		boolean is = false;
		String inputWork = input.trim().toUpperCase();
		String specWork = spec.trim().replace("[", "").replace("]", "").toUpperCase();
		// 사용자 입력값으로 N값이 입력되는 경우 조건비교식 오류로 로직 수정 박수근
        // inputWork = ("".equals(inputWork)) ? "N" : inputWork;	// 입력값이 공백이면 기본값 'N' 처리
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

		if (0 <= specWork.indexOf("|")) {
			// OR 조건의 비교 연산 로직이 없어 추가 박수근
			String[] specPeriod = specWork.split("[|]");
			boolean isFrom = false, isTo = false;
			isFrom = isRelationOneSide(inputWork, specPeriod[0], mapWork);
			isTo = isRelationOneSide(inputWork, specPeriod[1], mapWork);
			is = (isFrom || isTo) ? true : false;
		} else if (0 <= specWork.indexOf(",")) {
			// Both Side -- >400,<=780
			String[] specPeriod = specWork.split(",");
			boolean isFrom = false, isTo = false;
			isFrom = isRelationOneSide(inputWork, specPeriod[0], mapWork);
			isTo = isRelationOneSide(inputWork, specPeriod[1], mapWork);

			is = (isFrom && isTo) ? true : false;
		} else {
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

	/**
	 * 연산식 입력변수 치환
	 * @param expr
	 * @return
	 */
	public static String calValueChange(String spec, String calculation, HashMap<String, String> mapWork, HashMap<String, String> calcuMapSpec) {
		String specList = "";
		String regexp = "\\{(\\w+)\\}";
		Pattern pattern = Pattern.compile( regexp);
		Matcher matcher = pattern.matcher( calculation.replaceAll(System.getProperty("line.separator"), ""));
        StringBuffer result = new StringBuffer();
        while ( matcher.find()) {
               String propertyName =  matcher.group(1);
               specList = specList + ","+ propertyName;
               if( mapWork.containsKey(propertyName) ) {
            	   // 오류 때문에 강제로 셑팅
                   matcher.appendReplacement( result, (String)mapWork.get(propertyName));
               } else {
//     			   System.out.println("###### calValueChange NO 특성코드 ["+spec+"] #########");
//    	           System.out.println("spec 명:"+propertyName);
//    	           System.out.println("###### calValueChange NO 특성코드 ["+spec+"] #########");
            	   // 원칙적으로 해당 연산 함수에 대한 오류로 처리해야 한다.
            	   matcher.appendReplacement( result, "0");
	       	       //String error = "오류 복합연산식에 정의된 특성코드["+propertyName+"]가 존재하지 않습니다.";
	    		   //throw new SpecDutyException(error);
               }
        }
        matcher.appendTail(result);
        // 연산치환에 대한 스펙리스트를 추가한다.
        if( specList.length() > 0 ) {
            calcuMapSpec.put(spec, specList.substring(1));
        }
		return result.toString();
	}

	/**
	 * 연산식 처리, 사칙연산만 가능 ( + - * / ) A형
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public static String calculation(String spec, String calculation, String format, HashMap<String, String> mapWork) throws Exception {
		String outValue = "0";
        StringBuffer script = new StringBuffer();
		try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            script.append("var calculation = new Object(); \n");
            script.append("calculation.stringRslt = function() { \n");
            script.append("var outValue = 0; ");
            script.append("return outValue = "+calculation+"; ");
            script.append("} ");
            engine.eval(script.toString());
            Invocable inv = (Invocable) engine;
            Object obj = engine.get("calculation");
            outValue = inv.invokeMethod(obj, "stringRslt").toString();
            outValue = formatFunction(outValue,format);
//			System.out.println("###### calculation ["+spec+"] #########");
//	        System.out.println(calculation);
//	        System.out.println("###### calculation ["+spec+"] #########");
		} catch (Exception e) {
			System.out.println("###### calculation ["+spec+"] #########");
	        System.out.println(calculation);
	        System.out.println("###### calculation ["+spec+"] #########");
			throw new SpecDutyException(e);
		}
        
        return outValue;
	}

	/**
	 * 조건연산 후 문자형 결과 리턴 B형
	 * @param String
	 * @return
	 */
	public static String ifElseStringFunction(String spec, String operation) throws Exception {
		String outValue;
        StringBuffer script = new StringBuffer();
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            String[] operList = operation.split(",");
            script.append("var ifElseStringFunction = new Object(); \n");
            script.append("ifElseStringFunction.stringRslt = function() { \n");
            boolean isElse = false;
            for(int i=0; i < operList.length; i++ ) {
            	String ifStatement = "";
            	String retnStatement = "";
            	
        		if( operList[i].lastIndexOf("=") > 0 ) {
            	    ifStatement = operList[i].substring(0, operList[i].lastIndexOf("=")).trim();
        		}
        		
            	retnStatement = operList[i].substring(operList[i].lastIndexOf("=")+1).trim();
            	if( i == 0 ) {
            		script.append("if( "+ifStatement+" ) { \n");
            		script.append(" return "+retnStatement+"; \n");
            		script.append("} ");
            	} else {
            		if( ifStatement != null && !"".equals(ifStatement)) {
            		    script.append(" else if( "+ifStatement+" ) { \n");
                		script.append(" return "+retnStatement+"; \n");
            		    script.append("} ");
            		} else {
            			isElse = true;
            		    script.append(" else { \n");
                		script.append(" return "+retnStatement+"; \n");
            		    script.append("} \n");
            		}
            	}
            }
            
            if( !isElse ) {
    		    script.append(" else { \n");
        		script.append(" return \"\"; \n");
    		    script.append("} \n");
            }
            script.append("} ");
            engine.eval(script.toString());
            Invocable inv = (Invocable) engine;
            Object obj = engine.get("ifElseStringFunction");
            outValue = inv.invokeMethod(obj, "stringRslt").toString();
//            System.out.println("###### ifElseStringFunction ["+spec+"] #########");
//            System.out.println(script.toString());
//            System.out.println("###### ifElseStringFunction ["+spec+"] #########");
        } catch (Exception e) {
            System.out.println("###### ifElseStringFunction ["+spec+"] #########");
            System.out.println(script.toString());
            System.out.println("###### ifElseStringFunction ["+spec+"] #########");
			throw new SpecDutyException(e);
		}
        
        return outValue;
	}

	/**
	 * 조건연산 후 숫자형 결과 리턴 C형
	 * @param String
	 * @return
	 */
	public static String ifElseNumFunction(String spec, String format, String operation) throws Exception {
		String outValue;
        StringBuffer script = new StringBuffer();
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            String[] operList = operation.split(",");
            script.append("var ifElseNumFunction = new Object(); \n");
            script.append("ifElseNumFunction.numRslt = function() { \n");
            boolean isElse = false;
            for(int i=0; i < operList.length; i++ ) {
            	String ifStatement = "";
            	String retnStatement = "";

            	if( operList[i].lastIndexOf("=") > 0 ) {
            	    ifStatement = operList[i].substring(0, operList[i].lastIndexOf("=")).trim();
            	}
            	retnStatement = operList[i].substring(operList[i].lastIndexOf("=")+1).trim();
            	if( i == 0 ) {
            		script.append("if( "+ifStatement+" ) { \n");
            		script.append(" return ("+retnStatement+"); \n");
            		script.append("} ");
            	} else {
            		if( ifStatement != null && !"".equals(ifStatement)) {
    	        		script.append(" else if( "+ifStatement+" ) { \n");
    	        		script.append(" return ("+retnStatement+"); \n");
    	        		script.append("} ");
            		} else {
            			isElse = true;
    	        		script.append(" else { \n");
    	        		script.append(" return ("+retnStatement+"); \n");
    	        		script.append("} \n");
            		}
            	} 
            }

            if( !isElse ) {
    		    script.append(" else { \n");
        		script.append(" return 0; \n");
    		    script.append("} \n");
            }
            script.append("} ");

            engine.eval(script.toString());
            Invocable inv = (Invocable) engine;
            Object obj = engine.get("ifElseNumFunction");
            outValue = inv.invokeMethod(obj, "numRslt").toString();
            outValue = formatFunction(outValue,format);
//            System.out.println("###### ifElseNumFunction ["+spec+"] #########");
//            System.out.println(script.toString());
//            System.out.println("###### ifElseNumFunction ["+spec+"] #########");
        } catch (Exception e) {
            System.out.println("###### ifElseNumFunction ["+spec+"] #########");
            System.out.println(script.toString());
            System.out.println("###### ifElseNumFunction ["+spec+"] #########");
			throw new SpecDutyException(e);
		}
        return outValue;
	}

	/**
	 * N개 조건 후 문자형 결과 리턴 D형
	 * @param String
	 * @return
	 */
	public static String ifStringFunction(String spec, String operation) throws Exception {
		String outValue;
        StringBuffer script = new StringBuffer();
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            String[] operList = operation.split(",");
            script.append("var ifStringFunction = new Object(); \n");
            script.append("ifStringFunction.stringRslt = function() { \n");
    		script.append("var retnData = ''; \n");
            for(int i=0; i < operList.length; i++ ) {
            	String ifStatement = "";
            	String retnStatement = "";
            	
        		if( operList[i].lastIndexOf("=") > 0 ) {
            	    ifStatement = operList[i].substring(0, operList[i].lastIndexOf("=")).trim();
        		}
        		
            	retnStatement = operList[i].substring(operList[i].lastIndexOf("=")+1).trim();
        		script.append("if( "+ifStatement+" ) { \n");
        		script.append("    retnData = "+retnStatement+"; \n");
        		script.append("} ");
            }
       		script.append(" return retnData;");
            script.append("} ");

            engine.eval(script.toString());
            Invocable inv = (Invocable) engine;
            Object obj = engine.get("ifStringFunction");
            outValue = inv.invokeMethod(obj, "stringRslt").toString();
//            System.out.println("###### ifStringFunction ["+spec+"] #########");
//            System.out.println(script.toString());
//            System.out.println("###### ifStringFunction ["+spec+"] #########");
        } catch (Exception e) {
            System.out.println("###### ifStringFunction ["+spec+"] #########");
            System.out.println(script.toString());
            System.out.println("###### ifStringFunction ["+spec+"] #########");
			throw new SpecDutyException(e);
		}

        return outValue;
	}
	
	/**
	 * 숫자형 자료 반올림, 절삭 처리
	 * @param String
	 * @return
	 */
	public static String formatFunction(String value, String format) throws Exception {
		String outValue = null;
		if( format != null ) {
			BigDecimal bd = new BigDecimal(value);
			if( format.indexOf(",") > -1) {
				String fmt[] = format.split(",");
                if( fmt.length == 2 ) {
                    if( "R".equals(fmt[0])) {
                	    int scal = new Integer(fmt[1]).intValue();
                	    outValue = bd.setScale(scal, BigDecimal.ROUND_HALF_UP).toString();
                    } else if("T".equals(fmt[0])) {
                	    int scal = new Integer(fmt[1]).intValue();
                	    outValue = bd.setScale(scal, BigDecimal.ROUND_CEILING).toString();
                    } else {
    				    outValue = bd.setScale(1, BigDecimal.ROUND_CEILING).toString();
                    }
                }
			} else {
				// 정의된 포맷 값이 없으면 소수점 1자리에서 반올림값 처리
				outValue = bd.setScale(1, BigDecimal.ROUND_CEILING).toString();
			}
		} else {
			// 정의된 포맷 값이 없으면 소수점 0자리에서 반올림값 처리
			BigDecimal bd = new BigDecimal(outValue);
			outValue = bd.setScale(0, BigDecimal.ROUND_CEILING).toString();
		}

		return outValue.toString();
	}
}
