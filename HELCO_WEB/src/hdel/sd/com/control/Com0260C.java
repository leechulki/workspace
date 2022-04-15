package hdel.sd.com.control;

import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0260ParamVO;
import hdel.sd.com.domain.Com0260;
import hdel.sd.com.service.Com0260S;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

@Controller
@RequestMapping("com0260")
public class Com0260C {

	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Com0260S service;

	@RequestMapping(value = "find")
	public View Com0260FindView(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");

		// OUTPUT DATASET DECLARE
		Dataset dsList = paramVO.getDataset("ds_list"); // UI로 return할 out
														// dataset
		// Dataset dsError = paramVO.getDataset("ds_error_buyr"); // UI로 return할
		// 오류메세지 dataset

		// --------------------------------------------------------------------------------------------
		// INPUT PARAM INFO
		// sap client (개발 : 310 or 910)
		System.out.print("\n@@@ paramVO.G_MANDT ===>"
				+ paramVO.getVariable("G_MANDT") + "\n");
		System.out.print("\n@@@ paramVO.G_LANG	===>"
				+ paramVO.getVariable("G_LANG") + "\n");
		System.out.print("\n@@@ dsCond.CODE	===>"
				+ DatasetUtility.getString(dsCond, "CODE") + "\n");
		System.out.print("\n@@@ dsCond.CODE_NAME	===>"
				+ DatasetUtility.getString(dsCond, "CODE_NAME") + "\n");
		System.out.print("\n@@@ dsCond.CODE_FLAG	===>"
				+ DatasetUtility.getString(dsCond, "CODE_FLAG") + "\n");
		// --------------------------------------------------------------------------------------------

		try {

			Com0260ParamVO param = new Com0260ParamVO();

			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			String rqdat_f = DatasetUtility.getString(dsCond, "RQDAT_F");
			String rqdat_t = DatasetUtility.getString(dsCond, "RQDAT_T");
			String sman = DatasetUtility.getString(dsCond, "SMAN");
			String gsnam = DatasetUtility.getString(dsCond, "GSNAM");

			if (rqdat_f == null)
				rqdat_f = "";
			if (rqdat_t == null)
				rqdat_t = "";
			if (sman == null)
				sman = "";
			if (gsnam == null)
				gsnam = "";

			// 파라메터SET
			param.setMandt(paramVO.getVariable("G_MANDT")); // SAP CLIENT
			param.setRqdat_f(rqdat_f); // 요청일 1
			param.setRqdat_t(rqdat_t); // 요청일 2
			param.setSman(sman); // 담당자
			param.setGsnam(gsnam); // 공사명

			// 서비스CALL
			List<Com0260> list = service.find(param);

			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow = 0; iRow < list.size(); iRow++) {
				// 행추가
				dsList.appendRow();

				// 컬럼SET
				for (int iCol = 0; iCol < dsList.getColumnCount(); iCol++) {
					dsList.setColumn(iRow, "ZRQSEQ", list.get(iRow).getZrqseq());
					dsList.setColumn(iRow, "GSNAM", list.get(iRow).getGsnam());
				}
			}

			// 데이터건수 INFO
			System.out.print("\n@@@ ds_list.getRowCount ===>"
					+ dsList.getRowCount() + "\n");

			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;

	}

}
