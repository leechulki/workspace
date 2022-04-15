package hdel.lib.control;

import java.io.IOException;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.exception.NoMatchException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.data.DatasetList;
import com.tobesoft.platform.data.VariableList;


@Component
public class SrmArgumentConvertorImpl implements SrmArgumentConvertor {

	public Object convert(HttpServletRequest req) {
		MipParameterVO obj = new MipParameterVO();

		PlatformRequest pReq;
		try {
			// 2019.02.28
			pReq = new PlatformRequest(req);
			pReq.receiveData();
			VariableList vList = pReq.getVariableList();
			DatasetList dsList = pReq.getDatasetList();

			// I/F규격 호환 처리(egis --request--> srm )
			obj.setVariableList(vList);
			obj.setDatasetList(dsList);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new NoMatchException("IOException");
		}
//			obj.setVariableList(pReq.getVariableList());
//			obj.setDatasetList(pReq.getDatasetList());
		return obj;
	}

}
