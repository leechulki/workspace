package hdel.lib.control;

import hdel.lib.domain.MipResultVO;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.DatasetList;
import com.tobesoft.platform.data.PlatformData;
import com.tobesoft.platform.data.VariableList;

@Component
public class SrmView implements View {
	protected Log log = LogFactory.getLog(SrmView.class);

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		VariableList variableList = new VariableList();
		DatasetList datasetList = new DatasetList();
		PlatformData platformdata = new PlatformData();
		Object resultVO = model.get("resultVO");
		if (null != resultVO) {
			variableList = ((MipResultVO) resultVO).getVariableList();
			datasetList = ((MipResultVO) resultVO).getDatasetList();
		}
		// I/F규격 호환 처리(srm --response--> egis )

		PlatformResponse pRes = new PlatformResponse(res, PlatformResponse.XML, "utf-8");
		pRes.sendData(variableList, datasetList);
	}


}
