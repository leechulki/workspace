package hdel.sd.ses.control;

import com.helco.xf.comm.JSONUtil;
import com.sz.ui.data.DataSet;
import com.sz.ui.data.DataSetList;
import com.sz.ui.data.DataTypes;
import com.sz.ui.data.PlatformData;
import com.sz.ui.data.Variable;
import com.sz.ui.data.VariableList;

public class MakeJSONSample {
	public static void main(String args[]) {
		try {
			
			DataSet ds = new DataSet("ds_list");
			ds.addColumn("col1");
			ds.addColumn("col2");
			ds.addColumn("col3");
			ds.addColumn("col4");

			ds.newRow();
			ds.set(0, "col1", "test1");
			ds.set(0, "col2", "test2");
			ds.set(0, "col3", "test3");
			ds.set(0, "col4", "");

			PlatformData p = new PlatformData();
			p.setDataSetList(new DataSetList());
			p.getDataSetList().add(ds);

			p.setVariableList(new VariableList());
			p.getVariableList().add(new Variable("param1", DataTypes.STRING, "test01"));
			p.getVariableList().add(new Variable("param2", DataTypes.INT, 33));

			System.out.println(JSONUtil.makeToJSON(p).toJSONString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
