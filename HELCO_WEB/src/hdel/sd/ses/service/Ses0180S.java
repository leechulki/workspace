package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0180D;
import hdel.sd.ses.domain.Ses0180;
import hdel.sd.ses.domain.Ses0180ParamVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.service.sapjco3.RfcException;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0180S extends SrmService {

	private Ses0180D ses0180D;

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void createDao(SqlSession sqlSession) {
		ses0180D = sqlSession.getMapper(Ses0180D.class);
	}
	
	public List<Ses0180> selectZSDT1055(Ses0180ParamVO param) {
		return ses0180D.selectZSDT1055(param);
	}

	public List<Ses0180> selectZSDT0181(Ses0180ParamVO param) {
		return ses0180D.selectZSDT0181(param);
	}
	
	
	public void updateZSDT1055(Ses0180 param)	{
		ses0180D.mergeZSDT1055(param);
	}

	public void updateZSDT1001(Ses0180 param)	{
		ses0180D.updateZSDT1001(param);
	}

	public void updateZSDT1046(Ses0180 param)	{
		ses0180D.updateZSDT1046(param);
	}

	public void deleteZSDT1055(Ses0180 param)	{
		ses0180D.deleteZSDT1055(param);
	}
	
	public List<Ses0180> checkBeforeDelete(Ses0180 param)	{
		return ses0180D.selectZSDT1046(param);
	}
	
	public void save(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		// INPUT DATASET GET
		Dataset dsDetail = paramVO.getDatasetList().getDataset("ds_detail");
		
		try {
			// DAO생성
			createDao(session);

			Ses0180 param = new Ses0180();
			
			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setQTNUM(DatasetUtility.getString(dsDetail, 0, "QTNUM"));
			param.setBDSEQ(DatasetUtility.getInt(dsDetail, "BDSEQ"));
			param.setQTSER(DatasetUtility.getInt(dsDetail, 0, "QTSER"));

			param.setBDDAT(DatasetUtility.getString(dsDetail, 0, "BDDAT") == null ? "" 	: DatasetUtility.getString(dsDetail, 0, "BDDAT"));
			param.setCTDAT(DatasetUtility.getString(dsDetail, 0, "CTDAT") == null ? "" 	: DatasetUtility.getString(dsDetail, 0, "CTDAT"));
			param.setSBDCM(DatasetUtility.getString(dsDetail, 0, "SBDCM") == null ? "" 	: DatasetUtility.getString(dsDetail, 0, "SBDCM"));
			param.setSBDAM(new BigDecimal(DatasetUtility.getDouble(dsDetail, 0, "SBDAM")).setScale(2, RoundingMode.DOWN));
			param.setZCOST(new BigDecimal(DatasetUtility.getDouble(dsDetail, 0, "ZCOST")).setScale(2, RoundingMode.DOWN));
			param.setPBDAM(new BigDecimal(DatasetUtility.getDouble(dsDetail, 0, "PBDAM")).setScale(2, RoundingMode.DOWN));
			param.setZFRSN(DatasetUtility.getString(dsDetail, 0, "ZFRSN") == null ? "" 	: DatasetUtility.getString(dsDetail, 0, "ZFRSN"));
			param.setZSEC(DatasetUtility.getString(dsDetail, 0, "ZSEC") == null ? "" 	: DatasetUtility.getString(dsDetail, 0, "ZSEC"));
			param.setZSECAM(new BigDecimal(DatasetUtility.getDouble(dsDetail, 0, "ZSECAM")).setScale(2, RoundingMode.DOWN));
			param.setZFOREC(new BigDecimal(DatasetUtility.getDouble(dsDetail, 0, "ZFOREC")).setScale(2, RoundingMode.DOWN));
			param.setZPRGFLG(DatasetUtility.getString(dsDetail, 0, "ZPRGFLG") == null ? "" : DatasetUtility.getString(dsDetail, 0, "ZPRGFLG"));
			param.setZSFLG(DatasetUtility.getString(dsDetail, 0, "ZSFLG") == null ? "" 	: 	DatasetUtility.getString(dsDetail, 0, "ZSFLG"));
			param.setZRMK(DatasetUtility.getString(dsDetail, 0, "ZRMK") == null ? "" 	: 	DatasetUtility.getString(dsDetail, 0, "ZRMK"));
			param.setSHANG(new BigDecimal(DatasetUtility.getDouble(dsDetail, 0, "SHANG")).setScale(2, RoundingMode.DOWN));
			param.setCDATE(DateTime.getDateString());
			param.setCTIME(DateTime.getShortTimeString());
			param.setCUSER(paramVO.getVariable("G_USER_ID"));
			param.setUDATE(DateTime.getDateString());
			param.setUTIME(DateTime.getShortTimeString());
			param.setUUSER(paramVO.getVariable("G_USER_ID"));

			//국내 승강기 영업 입찰 결과 통제 
			param.setSBDERATE(new BigDecimal(DatasetUtility.getDouble(dsDetail, 0, "SBDERATE")).setScale(2, RoundingMode.DOWN));
			param.setBDRSLTDT(DatasetUtility.getString(dsDetail, 0, "BDRSLTDT") == null ? "" : DatasetUtility.getString(dsDetail, 0, "BDRSLTDT"));
			param.setBDCM2(DatasetUtility.getString(dsDetail, 0, "BDCM2") == null ? "" 		 : DatasetUtility.getString(dsDetail, 0, "BDCM2"));
			param.setBDCM2_T(DatasetUtility.getString(dsDetail, 0, "BDCM2_T") == null ? "" 	 : DatasetUtility.getString(dsDetail, 0, "BDCM2_T"));
			param.setBDAMT2(new BigDecimal(DatasetUtility.getDouble(dsDetail, 0, 	"BDAMT2")).setScale(2, RoundingMode.DOWN));
			param.setBDERATE2(new BigDecimal(DatasetUtility.getDouble(dsDetail, 0, 	"BDERATE2")).setScale(2, RoundingMode.DOWN));
			param.setBDCM3(DatasetUtility.getString(dsDetail, 0, "BDCM3") == null ? "" 		 : DatasetUtility.getString(dsDetail, 0, "BDCM3"));
			param.setBDCM3_T(DatasetUtility.getString(dsDetail, 0, "BDCM3_T") == null ? "" 	 : DatasetUtility.getString(dsDetail, 0, "BDCM3_T"));
			param.setBDAMT3(new BigDecimal(DatasetUtility.getDouble(dsDetail, 0, 	"BDAMT3")).setScale(2, RoundingMode.DOWN));
			param.setBDERATE3(new BigDecimal(DatasetUtility.getDouble(dsDetail, 0, 	"BDERATE3")).setScale(2, RoundingMode.DOWN));
			param.setZFRSN_T(DatasetUtility.getString(dsDetail, 0, "ZFRSN_T") == null ? "" 	 : DatasetUtility.getString(dsDetail, 0, "ZFRSN_T"));
			param.setZRMKLIST(DatasetUtility.getString(dsDetail, 0, "ZRMKLIST") == null ? "" : DatasetUtility.getString(dsDetail, 0, "ZRMKLIST"));
			
			updateZSDT1055(param);
			
			//2021.06 jss : 오더유형별 견적진행상태 변경 - 영업전략기획팀 정재연 요청
			String auart = DatasetUtility.getString(dsDetail, 0, "AUART");
			if(auart==null)auart="";
			
			if(auart.equals("ZS01")||auart.equals("ZS02")){
				if("21".equals(param.getZPRGFLG())|| "22".equals(param.getZPRGFLG())|| "50".equals(param.getZPRGFLG())){//입찰연기, 입찰취소, 기타
					if(auart.equals("ZS01")) param.setCHGFLG("32");//원가산출
					//else param.setCHGFLG("31");//원가산출요청
					else param.setCHGFLG("33");//원가산출승인  : 2021.08 jss 변경 -영업전략기획팀 정재연 요청
				}else{
					if("10".equals(param.getZPRGFLG())|| "30".equals(param.getZPRGFLG())){
						param.setCHGFLG("61");//수주
					}else{
						param.setCHGFLG("62");//수주실패
					}					
				}
			}else{
				// 2012.10.19 입찰결과 등록 후 수주계획의 수주계획상태 및 견적의 견적진행상태 값을 변경처리
				// 입찰결과 등록 상태가 낙찰(30) 및 수의계약(10)의 경우 수주('61')
				// 이외의 경우에는 수주실패('62')로 처리
				if ("10".equals( param.getZPRGFLG() ) || "30".equals( param.getZPRGFLG() ) )	{
					param.setCHGFLG("61");		// 수주
				}else	{
					param.setCHGFLG("62");		// 수주실패
				}
			}
			
			// 타 대리점 진행 / 직영 진행 건인 경우 해당 견적의 담당자 변경 처리 및 입찰 상태로 남아있도록 함
			if ("60".equals(param.getZPRGFLG()) || "70".equals(param.getZPRGFLG())) {
				param.setCHGFLG("50");		// 입찰
				param.setZKUNNR(DatasetUtility.getString(dsDetail, 0, "ZKUNNR"));
			}

			updateZSDT1001(param);	// 수주진행상태
			
			updateZSDT1046(param);	// 견적진행상태
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public MipResultVO delete(MipParameterVO paramVO, Model model, SqlSession session) throws Exception {
		
		MipResultVO resultVO = new MipResultVO();
		
		// INPUT DATASET GET
		Dataset dsDetail = paramVO.getDatasetList().getDataset("ds_detail");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		try {
			// DAO생성
			createDao(session);
			
			Ses0180 param = new Ses0180();
			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setQTNUM(DatasetUtility.getString(dsDetail, 0, "QTNUM"));
			param.setBDSEQ(DatasetUtility.getInt(dsDetail, "BDSEQ"));
			// 2012.10.19 견젃차수 및 수주계획번호 적용
			param.setQTSER(DatasetUtility.getInt(dsDetail, 0, "QTSER"));
			//param.setSONNR(DatasetUtility.getString(dsDetail, 0, "SONNR"));
			param.setUTIME(DateTime.getShortTimeString());
			param.setUUSER(paramVO.getVariable("G_USER_ID"));

// ----<  2017.06.13 입찰 결과 삭제 전 체크 로직 추가 by mj.Shin 	Start
			List<Ses0180> delChkList = checkBeforeDelete(param);
			
			if (delChkList.size() == 0) {
				//CE10006=삭제할 대상이 아닙니다.
				dsError = CallSAPHdel.makeErrorInfoColSet(dsError,"",  "CE10006", "Y", "Y");
				dsError.setId("ds_error");
				resultVO.addDataset(dsError); 	// 오류결과내역
				return resultVO;
			} 
							
			String lv_qtnum = delChkList.get(0).getQTNUM().trim();
			int lv_qtser = delChkList.get(0).getQTSER();
			int lv_bdseq = delChkList.get(0).getBDSEQ();
			String lv_vbeln = delChkList.get(0).getVBELN().trim();
			String lv_zprgflg_1046 = delChkList.get(0).getZPRGFLG_1046().trim();
			
			if ( (lv_vbeln != null && !"".equals(lv_vbeln) ) || "90".equals(lv_zprgflg_1046) )  {
				//CW00109=[${}] 정보가 존재하므로 삭제가 불가합니다.
				dsError = CallSAPHdel.makeErrorInfoColSet(dsError,"수주생성",  "CW00109", "Y", "Y");
				logger.debug("수주 생성 건 삭제 불가 : [" + lv_qtnum + "-" + lv_qtser + "-" + lv_bdseq + "-" + lv_vbeln + "]");			
			}
			
			if (lv_bdseq == 0) {
				//CE10006=삭제할 대상이 아닙니다.
				dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "", "CE10006", "Y", "Y");
				logger.debug("입찰결과 자동입력 건 삭제 불가 : [" + lv_qtnum + "-" + lv_qtser + "-" + lv_bdseq + "-" + lv_vbeln + "]");
			}
			
// ---->  2017.06.13 입찰 결과 삭제 전 체크 로직 추가 by mj.Shin 	End 
			
			if ( dsError.getRowCount() == 0) {
				
				deleteZSDT1055(param);
				// 입찰결과 등록 삭제 시 수주계획 및 견적상태를 입철('50')로 변경
				//param.setCHGFLG("50");		// 입찰
				// 입찰결과 등록 삭제 시 수주계획 및 견적상태를 원가산출 승인('33')로 변경
				param.setCHGFLG("33");		// 원가산출 승인

				updateZSDT1001(param);	// 수주진행상태
				
				updateZSDT1046(param);	// 견적진행상태
				
			} else {
				dsError.setId("ds_error");
				resultVO.addDataset(dsError); 	// 오류결과내역	
			}
							
		} catch (Exception e) {
			e.printStackTrace();
			
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", "CE00001", "Y", "Y");
			
			resultVO.addDataset(dsError); 	// 오류결과내역

			return resultVO;
			
		}
		
		return resultVO;
	}
}
