package com.helco.xf.cs01.ws;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * ���ռ� üũ ������� ��
 */
public class MatchDutyOpr {

	public static boolean compare(String input, String spec, HashMap mapWork) {
		boolean is = false;

		String specWork = spec.trim().toUpperCase();

		// Spec���� ������ ��� �Է¿� ���� true
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
	 * ���� BOM Ư�� �⺻ �������
	 * @param expr
	 * @return
	 */
	public static boolean serviceCompare(String input, String spec, HashMap mapWork) {
		boolean is = false;

		String specWork = spec.trim().toUpperCase();

		// Spec���� ������ ��� �Է¿� ���� false
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
		inputWork = ("".equals(inputWork)) ? "N" : inputWork;	// �Է°��� �����̸� �⺻�� 'N' ó��
		String specWork = spec.trim().toUpperCase();

		is = (inputWork.equals(specWork)) ? true : false;
        
		return is;
	}

	// N�� �񱳷��� �߰�
	public static boolean isEqualN(String input, String spec) {
		boolean is = false;
		String inputWork = input.trim().toUpperCase();
		String specWork = spec.trim().replace("[", "").replace("]", "").toUpperCase();
		// ����� �Է°����� N���� �ԷµǴ� ��� ���Ǻ񱳽� ������ ���� ���� �ڼ���
        // inputWork = ("".equals(inputWork)) ? "N" : inputWork;	// �Է°��� �����̸� �⺻�� 'N' ó��
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
			// OR ������ �� ���� ������ ���� �߰� �ڼ���
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
	 * ����� �Էº��� ġȯ
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
            	   // ���� ������ ������ �V��
                   matcher.appendReplacement( result, (String)mapWork.get(propertyName));
               } else {
//     			   System.out.println("###### calValueChange NO Ư���ڵ� ["+spec+"] #########");
//    	           System.out.println("spec ��:"+propertyName);
//    	           System.out.println("###### calValueChange NO Ư���ڵ� ["+spec+"] #########");
            	   // ��Ģ������ �ش� ���� �Լ��� ���� ������ ó���ؾ� �Ѵ�.
            	   matcher.appendReplacement( result, "0");
	       	       //String error = "���� ���տ���Ŀ� ���ǵ� Ư���ڵ�["+propertyName+"]�� �������� �ʽ��ϴ�.";
	    		   //throw new SpecDutyException(error);
               }
        }
        matcher.appendTail(result);
        // ����ġȯ�� ���� ���帮��Ʈ�� �߰��Ѵ�.
        if( specList.length() > 0 ) {
            calcuMapSpec.put(spec, specList.substring(1));
        }
		return result.toString();
	}

	/**
	 * ����� ó��, ��Ģ���길 ���� ( + - * / ) A��
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
	 * ���ǿ��� �� ������ ��� ���� B��
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
	 * ���ǿ��� �� ������ ��� ���� C��
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
	 * N�� ���� �� ������ ��� ���� D��
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
	 * ������ �ڷ� �ݿø�, ���� ó��
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
				// ���ǵ� ���� ���� ������ �Ҽ��� 1�ڸ����� �ݿø��� ó��
				outValue = bd.setScale(1, BigDecimal.ROUND_CEILING).toString();
			}
		} else {
			// ���ǵ� ���� ���� ������ �Ҽ��� 0�ڸ����� �ݿø��� ó��
			BigDecimal bd = new BigDecimal(outValue);
			outValue = bd.setScale(0, BigDecimal.ROUND_CEILING).toString();
		}

		return outValue.toString();
	}
}
