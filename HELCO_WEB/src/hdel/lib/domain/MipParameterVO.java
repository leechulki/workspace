package hdel.lib.domain;

import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.DatasetList;
import com.tobesoft.platform.data.VariableList;

public class MipParameterVO extends BaseDomain {
	
	private VariableList variableList = new VariableList();
	private DatasetList datasetList = new DatasetList();
	
	public VariableList getVariableList() {
		return variableList;
	}
	public void setVariableList(VariableList variableList) {
		this.variableList = variableList;
	}
	public DatasetList getDatasetList() {
		return datasetList;
	}
	public void setDatasetList(DatasetList datasetList) {
		this.datasetList = datasetList;
	}
	
	public String getVariable(String variable) {
		return this.variableList.getValueAsString(variable);
	}
	public Dataset getDataset(String dataset) {
		return this.datasetList.getDataset(dataset);
	}
	
}
