package com.helco.xf.comm;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.Variant;

/**
 * @author any2me
 *
 */
public class DatasetUtil {
	/**
	 * @param list
	 * @param dataset
	 */
	public static void moveListToDs(List list, Dataset dataset) {
		for (Object obj : list) {
			dataset.appendRow();
			moveObjToDsRow(obj, dataset, dataset.getRowCount() - 1);
		}

	}

	/**
	 * @param obj
	 * @param dataset
	 * @param rowIdx
	 */
	public static void moveObjToDsRow(Object obj, Dataset dataset, int rowIdx) {

		PropertyDescriptor propertyDesc;
		Object objValue = null;

		for (int idx = 0; idx < dataset.getColumnCount(); idx++) {
			try {
				propertyDesc = new PropertyDescriptor(dataset.getColumnID(idx), obj.getClass());
				if (propertyDesc.getReadMethod() == null)
					continue;

				objValue = propertyDesc.getReadMethod().invoke(obj, new Object[]{});
			} catch (IntrospectionException e) {
				//System.out.println(e.getMessage() + " - " + objList.toString() + " related to " + dataset.getDataSetID());
				continue;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch (dataset.getColumnInfo(idx).getType()) {
			case 0: // CHAR, CURRENCY, LONGTEXT, URL
			case 1: // String
				dataset.setColumn(rowIdx, dataset.getColumnID(idx),
						//(String) (objValue == null ? "" : objValue)
						(objValue == null ? "" : objValue).toString()
						);
				break;

			case 2: // INT
				dataset.setColumn(rowIdx, dataset.getColumnID(idx),
						//(Integer) (objValue == null ? 0 : objValue));
						(new BigDecimal((objValue == null ? 0 : objValue).toString())).intValue()
						);
				break;

			case 4: // DECIMAL
				dataset.setColumn(rowIdx, dataset.getColumnID(idx),
						(new BigDecimal((objValue == null ? 0.0 : objValue).toString())).doubleValue()
						);
				break;

			case 8: // DATE
				dataset.setColumn(rowIdx, dataset.getColumnID(idx),
						//(objValue == null ? new Date().toString() : objValue.toString())
						(objValue == null ? "" : objValue.toString())
						);
				break;

			case 9: // BLOB
				dataset.setColumn(rowIdx, dataset.getColumnID(idx),
						(String) (objValue == null ? "" : objValue));
				break;

			default:
			}
		}
	}


	/**
	 * @param dataset
	 * @param obj
	 */
	public static void moveDsRowToObj(Dataset dataset, int rowIdx, Object obj) {

		PropertyDescriptor propertyDesc;
		// for (Object objList : list) {
		// dataset.appendRow();
		Class<?> cls;
		Constructor<?> constructor;
		String param;
		for (int idx = 0; idx < dataset.getColumnCount(); idx++) {
			try {
				propertyDesc = new PropertyDescriptor(dataset.getColumnID(idx), obj.getClass());

				if (propertyDesc.getReadMethod() == null)
					continue;

				Variant varinat = dataset.getColumn(rowIdx, dataset.getColumnID(idx));

				switch (dataset.getColumnInfo(idx).getType()) {
				case 0: // CHAR, CURRENCY, LONGTEXT, URL
				case 1: // String
					cls = propertyDesc.getWriteMethod().getParameterTypes()[0];
					constructor = cls.getConstructor(String.class);
					param = varinat.toString() == null ? "" : varinat.toString();
					propertyDesc.getWriteMethod().invoke(obj, constructor.newInstance(param));
					break;

				case 2: // INT
				case 4: // DECIMAL
					cls = propertyDesc.getWriteMethod().getParameterTypes()[0];
					constructor = cls.getConstructor(String.class);
					param = varinat.toString() == null ? "0" : varinat.toString();
					propertyDesc.getWriteMethod().invoke(obj, constructor.newInstance(param));
					break;

				case 8: // DATE
					propertyDesc.getWriteMethod().invoke(obj, varinat.getDate());
					// dataset.setColumn(dataset.getRowCount()-1,
					// dataset.getColumnID(idx), (Date) objValue);
					break;

				case 9: // BLOB
					propertyDesc.getWriteMethod().invoke(obj, varinat.toString());
					// dataset.setColumn(dataset.getRowCount()-1,
					// dataset.getColumnID(idx), (String) objValue);
					break;

				default:
				}
			} catch (IntrospectionException e) {
				//System.out.println(e.getMessage() + " - " + obj.toString() + " related to " + dataset.getDataSetID());
				continue;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
//				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
}