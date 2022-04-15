package hdel.sd.sso.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.SapFunction;
import com.sap.domain.Vbeln;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.domain.BAPIRET2;
/**
 * 수주변경
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDT0129;
import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0180D;
import hdel.sd.com.dao.TestindD;
import hdel.sd.com.domain.Com0180;
import hdel.sd.com.domain.Com0180ParamVO;
import hdel.sd.com.domain.DutyObj;
import hdel.sd.com.service.DutyNS;
import hdel.sd.com.service.DutyS;
import hdel.sd.ses.dao.Ses0030D;
import hdel.sd.ses.dao.Ses0031D;
import hdel.sd.ses.domain.Ses0030;
import hdel.sd.ses.domain.Ses0030ParamVO;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.smp.control.SmpComC;
import hdel.sd.sso.dao.Sso0055ND;
import hdel.sd.sso.domain.Sso0055ParamVO;
import hdel.sd.sso.domain.Sso0055VO;
import hdel.sd.sso.domain.ZSDS0063;
import hdel.sd.sso.domain.ZSDT0090;
import hdel.sd.sso.domain.ZSDT0091;
import hdel.sd.sso.domain.ZSDT0092;
import hdel.sd.sso.domain.ZSDT0093;
import hdel.sd.sso.domain.ZSDT0094;
import hdel.sd.sso.domain.ZSDT1108;
import tit.service.sapjco3.Rfc;
import tit.service.sapjco3.RfcException;
import tit.util.DatasetUtility;
// 2020 브랜드
import hdel.sd.com.domain.Coms01A;
import hdel.sd.com.dao.Coms01AD;

@Service
public class Sso0055NS extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Sso0055ND dao;
	private Ses0031D Ses0031Dao;
	private TestindD TestindDao;
	private Com0180D Com0180Dao;
	private Ses0030D Ses0030Dao;
	
	// 2020 브랜드
	private Coms01AD Coms01ADao;
	
	@Autowired
	private Sso0055NJS sso0055NJS;
	
	@Autowired
	private DutyS dutyService;

	@Autowired
	private DutyNS dutyNService;

	@Override
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Sso0055ND.class);
		TestindDao = sqlSession.getMapper(TestindD.class);
		Com0180Dao = sqlSession.getMapper(Com0180D.class);
		// 2020 브랜드
		Coms01ADao = sqlSession.getMapper(Coms01AD.class);
	}
	
	public void createSes0031Dao(SqlSession sqlSession) {
		Ses0031Dao = sqlSession.getMapper(Ses0031D.class);
	}
	
	public void createSes0030Dao(SqlSession sqlSession) {
		Ses0030Dao = sqlSession.getMapper(Ses0030D.class);
	}
	
	//=========================================================================================
    //  함수기능 	: 영업사양 조회 및 신규생성
    //  기능ID   	: UF019
    //  개선내역 	: 컨트롤단에서 서비스단으로 이동 
	//            신규 생성일 경우 sap을 이용하던 로직을 java로 변경.
    //  HISTORY : 2016.02.29 최초 코딩 hsi
    //=========================================================================================
	public MipResultVO find(MipParameterVO paramVO, SqlSession sessionVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
	
		// INPUT DATASET GET
		String i_pspId 	= paramVO.getVariable("pspId"); // 프로젝트번호
		String i_cmd	= paramVO.getVariable("cmd");	// 구분
		String i_seq	= paramVO.getVariable("seq");	// 차수
		String i_mandt  = paramVO.getVariable("G_MANDT");
		String chkCmd   = "S";
		String chkCmd2   = "";
		String userid   = paramVO.getVariable("G_USER_ID");
		String usergp   = paramVO.getVariable("G_USER_GROUP_B");
		Double adtza90  = new Double(0);
		String ls_vbpa = "";
		// 2020 브랜드
		String F_QTNUM = "";
		String F_BRND_FLAG = "N";
		
		int zsdt0214 = 0;
		int maxSeq = 0;

		// OUTPUT DATASET DECLARE
		Dataset ds_output_ZSDS0063			= ZSDS0063.getDataset();		// 수주 DATA
		Dataset ds_output_ZSDT0090			= ZSDT0090.getDataset();		// 변경 HEADER DATA
		Dataset ds_output_ZSDT0091			= ZSDT0091.getDataset();		// 변경 ITEM DATA
		Dataset ds_output_ZSDT0092			= ZSDT0092.getDataset();		// 현행 청구계획DATA
		Dataset ds_output_ZSDT0093			= ZSDT0093.getDataset();		// 변경 청구계획DATA
		Dataset ds_output_ZSDT0094_original	= ZSDT0094.getDataset();		// 현행 사양
		Dataset ds_output_ZSDT0094_change	= ZSDT0094.getDataset();		// 변경 사양
		Dataset dsError 					= new Dataset("ds_error");		// sap의 에러메시지를 datSet으로 담기위함.	
		Dataset dsVbeln = new Dataset();
		Dataset dsVbeln2   = new Dataset();
		Dataset dsRecad_da = new Dataset();
		Dataset ds129   = paramVO.getDataset("ds_129");
		Dataset dsJqpr = ZSDT1108.getDataset(); // jqpr 정보
		Dataset dsMaxSeq = new Dataset();
		Dataset dsDateCommi = paramVO.getDataset("ds_date_commi");
		Dataset dsSameVbeln = new Dataset(); //프로젝트 번호 중복 확인 2021.05.06.
	
		ds129.deleteAll();
			
		try {
			// vo 선언
			ZSDT0090 paramZsdt0090 = new ZSDT0090();
			Sso0055ParamVO paramSso0055 = new Sso0055ParamVO();
			Ses0030ParamVO paramSes0030 = new Ses0030ParamVO();
			
			// ds id 선언
			ds_output_ZSDS0063.setId("ds_output_ZSDS0063");
			ds_output_ZSDT0090.setId("ds_output_ZSDT0090");
			ds_output_ZSDT0091.setId("ds_output_ZSDT0091");
			ds_output_ZSDT0092.setId("ds_output_ZSDT0092");
			ds_output_ZSDT0093.setId("ds_output_ZSDT0093");
			ds_output_ZSDT0094_original.setId("ds_output_ZSDT0094_original");
			ds_output_ZSDT0094_change.setId("ds_output_ZSDT0094_change");
			dsJqpr.setId("ds_jqpr");
			
			//-----< 2021.05.26  프로젝트번호 유효성 Check 수정 by mj.Shin Start						
			int cntPspid = dao.validation_pspid(i_mandt, i_pspId);
			if (cntPspid == 0) {
				dsError.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);
				dsError.addColumn("ERRMSG", ColumnInfo.COLTYPE_STRING, 256);
				
				dsError.appendRow();
				dsError.setColumn(0, "IDX", "CW00022");
				dsError.setColumn(0, "ERRMSG", "프로젝트");
				resultVO.addDataset(dsError); 	// 오류결과내역
				return resultVO;
			}
			
			/*// validation_pspid 대체 처리 
			List<Com0180> list0180 = validationCheck_Project(i_mandt, i_pspId);
			if(list0180.size() == 0) {
				
				dsError.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);
				dsError.addColumn("ERRMSG", ColumnInfo.COLTYPE_STRING, 256);
				
				dsError.appendRow();
				dsError.setColumn(0, "IDX", "CW00022");
				dsError.setColumn(0, "ERRMSG", "프로젝트");
				resultVO.addDataset(dsError); 	// 오류결과내역
				return resultVO;
			}
			*/
			//-----> 2021.05.26 프로젝트번호 유효성 Check 수정  by mj.Shin Start
			
			//프로젝트 번호 중복 확인 2021.05.06.
			createSes0030Dao(sessionVO);
			
			paramSes0030.setMandt(i_mandt);
			paramSes0030.setVbeln(i_pspId);
			List<Ses0030> sameVbelnlist = Ses0030Dao.selectSameVbeln(paramSes0030);
			
			if(sameVbelnlist.size() > 1){
				dsSameVbeln.setId("ds_samevbeln");
								
				dsSameVbeln.addColumn("QTNUM", ColumnInfo.COLTYPE_STRING, 256);
				dsSameVbeln.addColumn("QTSER", ColumnInfo.COLTYPE_STRING, 256);
				dsSameVbeln.addColumn("QTDAT", ColumnInfo.COLTYPE_STRING, 256);
				dsSameVbeln.addColumn("GSNAM", ColumnInfo.COLTYPE_STRING, 256);
				dsSameVbeln.addColumn("VBELN", ColumnInfo.COLTYPE_STRING, 256);
				dsSameVbeln.addColumn("ZKUNNR", ColumnInfo.COLTYPE_STRING, 256);
				dsSameVbeln.addColumn("ZKUNNR_NM", ColumnInfo.COLTYPE_STRING, 256);
				
				for(int i=0;i<sameVbelnlist.size();i++){
				dsSameVbeln.appendRow();
				dsSameVbeln.setColumn(i, "QTNUM", sameVbelnlist.get(i).getQtnum());
				dsSameVbeln.setColumn(i, "QTSER", sameVbelnlist.get(i).getQtser());
				dsSameVbeln.setColumn(i, "QTDAT", sameVbelnlist.get(i).getQtdat().substring(0, 4)+"-"+sameVbelnlist.get(i).getQtdat().substring(4, 6)+"-"+sameVbelnlist.get(i).getQtdat().substring(6));
				dsSameVbeln.setColumn(i, "GSNAM", sameVbelnlist.get(i).getGsnam());
				dsSameVbeln.setColumn(i, "VBELN", sameVbelnlist.get(i).getVbeln());
				dsSameVbeln.setColumn(i, "ZKUNNR", sameVbelnlist.get(i).getZkunnr());
				dsSameVbeln.setColumn(i, "ZKUNNR_NM", sameVbelnlist.get(i).getZkunnr_nm());				
				}
				
				resultVO.addDataset(dsSameVbeln);
				return resultVO;
				}
			
			// 차수가 없을 경우 신규 생성으로.
			if("".equals(i_seq) || i_seq == null) chkCmd = "I";

			// 차수가 있지만 해당 차수로 자료가 없을 경우 생성으로.
			if(!"I".equals(chkCmd)) {
				paramZsdt0090.setMANDT(i_mandt);   // SAP CLIENT
				paramZsdt0090.setPSPID(i_pspId);
				paramZsdt0090.setSEQ(i_seq);
				
				List<ZSDT0090> list = dao.SelectHeader(paramZsdt0090); // 서비스CALL
				
				if(list.size() < 1) chkCmd = "I";
			} 

			// 2020년 브랜드별 영업사양 분리 적용을 위한 기준 데이터는 견적번호를 조회힌다.
			paramZsdt0090.setMANDT(i_mandt);   // SAP CLIENT
			paramZsdt0090.setPSPID(i_pspId);
			F_QTNUM = dao.SelectQtnum(paramZsdt0090);
			
			
			// 견적번호를 통해서 작성된 기준으로 
			Coms01A coms01A = new Coms01A();
			coms01A.setMANDT(i_mandt);
			coms01A.setQTNUM(F_QTNUM);
			F_BRND_FLAG = Coms01ADao.selectBrandFlag(coms01A);
			
			dsJqpr = getJqpr(dsJqpr, i_pspId, i_mandt);


			if("I".equals(chkCmd)) {
				paramSso0055.setMANDT(paramVO.getVariable("G_MANDT")); 				// 세션권한
				paramSso0055.setPSPID(i_pspId); 									// 
			
				List<Sso0055VO> listSeq = dao.SelectMaxSeq(paramSso0055);
				
				SmpComC.moveDs2List(dsMaxSeq, ZSDS0063.class, listSeq);
				
				if(dsMaxSeq.getRowCount() > 0) {
					// 구분값이 조회이지만 maxseq가 0이면 생성로직 0보다 크다면 조회로직
					if("DISP".equals(i_cmd)) {
						if("0".equals(listSeq.get(0).getSEQ())) {
							i_seq = "1";
						} else {
							i_seq = String.valueOf(listSeq.get(0).getSEQ());
							chkCmd = "S";
						}
					} else {
						
					//-----< 2016.12.01 "입력"(CRET)으로 maxseq = 0 이면 차수 1로 세팅하는 분기 추가 by mj.Shin Start
						i_seq = String.valueOf(listSeq.get(0).getSEQ());
						if("0".equals(i_seq)) {
							i_seq = "1";
						} else {
					//-----> 2016.12.01 "입력"(CRET)으로 maxseq = 0 이면 차수 1로 세팅하는 분기 추가 by mj.Shin End 
							
							// 이전 차수가 승인처리가 되었는지 확인
							//i_seq = String.valueOf(listSeq.get(0).getSEQ());
							paramZsdt0090.setMANDT(i_mandt);   // SAP CLIENT
							paramZsdt0090.setPSPID(i_pspId);
							paramZsdt0090.setSEQ(i_seq);
							
							List<ZSDT0090> list = dao.SelectHeader(paramZsdt0090); // 서비스CALL
							// X는 승인이므로 X만 추가 차수로 진행가능하다.
							if("X".equals(list.get(0).getFINL())) {
								i_seq = String.valueOf(Integer.parseInt(listSeq.get(0).getSEQ())+1);
							} else {
								i_seq = String.valueOf(listSeq.get(0).getSEQ());
								chkCmd = "S1";
								//chkCmd2 = "S1";
							}
							
						}		// 2016.12.01 "입력"(CRET)으로 maxseq = 0 이면 차수 1로 세팅하는 분기 추가 by mj.Shin 
					
					}
				}
			}
			if("S".equals(chkCmd.substring(0, 1))) {       // 20150105 DISP 에서는 RFC 를 사용 하지 않음으로 변경 BY 김선호
				ds_output_ZSDS0063 = projectToDataset(ds_output_ZSDS0063,i_pspId,i_seq,i_mandt, chkCmd.substring(0, 1));  			// 프로젝트 DATA
				String zkuno = ds_output_ZSDS0063.getColumnAsString(0, "ZKUNNR").substring(1,8);
				logger.info(zkuno);
				if (!(zkuno).equals(userid) && ("B10".equals(usergp))) {					
					logger.info(userid);
					logger.info(usergp);
					throw new BizException("CW10025,gggggggggggggggggggg");
				}
				ds_output_ZSDT0090 = headerToDataset(ds_output_ZSDT0090,i_pspId,i_seq,i_mandt, chkCmd.substring(0, 1));				// 계약변경 header data
				ds_output_ZSDT0091 = itemToDataset(ds_output_ZSDT0091,i_pspId,i_seq,i_mandt, chkCmd.substring(0, 1));				// 계약변경 item data
				ds_output_ZSDT0092 = billoToDataset(ds_output_ZSDT0092,i_pspId,i_seq,i_mandt, chkCmd.substring(0, 1));				// 현행 청구계획 data
				ds_output_ZSDT0093 = billcToDataset(ds_output_ZSDT0093,i_pspId,i_seq,i_mandt, chkCmd.substring(0, 1));				// 변경 청구계획 data
				ds_output_ZSDT0094_change = speccToDataset(ds_output_ZSDT0094_change,i_pspId,i_seq,i_mandt, chkCmd.substring(0, 1));// 변경 사양 data
				
				// chkCmd값이 S1이라면 이전 차수가 승인되지 않은 상태에서 신규생성을 누른것이므로 값을 리턴후 ui에서 확인시켜준다.
				if("S1".equals(chkCmd)) {
					ds_output_ZSDS0063.setColumn(0, "KUNZ1", "RESET");
				}
			}else{
				//=========================================================================================
				//  기능ID : UF019  
				//  수정내역 : sap RFC 제외하고 java 로직으로 변경.
				//=========================================================================================
				ds_output_ZSDS0063 = projectToDataset(ds_output_ZSDS0063,i_pspId,i_seq,i_mandt, chkCmd);  			// 프로젝트 DATA
				String zkuno = ds_output_ZSDS0063.getColumnAsString(0, "ZKUNNR").substring(1,8);
				logger.info(zkuno);
				if (!(zkuno).equals(userid) && ("B10".equals(usergp))) {
					
				//if (!( ds_output_ZSDS0063.getColumnAsString(0, "ZKUNNR").substring(1,8)).equals(userid) && ("B10".equals(usergp))) {					
					logger.info(userid);
					logger.info(usergp);
					throw new BizException("CW10025,gggggggggggggggggggg");
				}
				ds_output_ZSDT0090 = headerToDataset(ds_output_ZSDT0090,i_pspId,i_seq,i_mandt, chkCmd);				// 계약변경 header data
				ds_output_ZSDT0091 = itemToDataset(ds_output_ZSDT0091,i_pspId,i_seq,i_mandt, chkCmd);				// 계약변경 item data
				ds_output_ZSDT0092 = billoToDataset(ds_output_ZSDT0092,i_pspId,i_seq,i_mandt, chkCmd);				// 현행 청구계획 data
				ds_output_ZSDT0093 = billcToDataset(ds_output_ZSDT0093,i_pspId,i_seq,i_mandt, chkCmd);				// 변경 청구계획 data
				ds_output_ZSDT0094_change = speccToDataset(ds_output_ZSDT0094_change,i_pspId,i_seq,i_mandt, chkCmd);// 변경 사양 data
			}
			
			/*
			   입력시 호기탭 변경구분 flag 'X' 세팅으로 인한 구문추가
			   - 상단 chkCmd = "S1" 으로  승인이  아닐경우 조회쿼리를 탐
			   - chkCmd2 로 지정하여 테스트 결과 기존데이터와 차이가 있어, 영향도고려  ds_output_ZSDT0091만 강제세팅함
			*/
			if("CRET".equals(i_cmd)){
				for ( int i = 0 ; i < ds_output_ZSDT0091.getRowCount() ; i++ ) {
				  ds_output_ZSDT0091.setColumn(i,"CHCK","");
				}
			}
			
			// 조회한 VBELN이 견적Header 테이블에 존재하는지 확인 
			// INPUT DATASET GET
			if(ds_output_ZSDS0063.getRowCount() > 0 ) {
				String i_vbeln = "";
								
				if("S".equals(chkCmd.substring(0, 1))) i_vbeln = ds_output_ZSDS0063.getColumnAsString(0, "PSPID");
				else if("I".equals(chkCmd)) i_vbeln = ds_output_ZSDS0063.getColumnAsString(0, "VBELN");
				
				if(i_vbeln == null) i_vbeln = ""; 
			
				try { 
					Sso0055ParamVO paramV = new Sso0055ParamVO();	// vo
					paramV.setMANDT(i_mandt); 						// 세션권한
					paramV.setVBELN(i_vbeln); 						// 
				
					List<Sso0055VO> list = dao.SelectVbeln(paramV);
					
					//영업사원 등록여부 확인
					ls_vbpa = selectKunnr(i_mandt, i_vbeln);
					//STO/협력사 대리점 판매수수료 지급 관련 현대협력사 여부 조회
					if(!"".equals(ls_vbpa)) {
						zsdt0214 = selectZsdt0214(ls_vbpa);
					}
					
					// list결과를 dataset으로 담기
					SmpComC.moveDs2List(dsVbeln, Sso0055VO.class, list );
					dsVbeln.setId("ds_vbeln");
					
					//수주승인일 조회 
					List<Sso0055VO> list2 = dao.getRecad_da(paramV);
					SmpComC.moveDs2List(dsRecad_da, Sso0055VO.class, list2 );
					dsRecad_da.setId("ds_recad_da");
					
                    // 주차프로젝트중 승강기 동시 수주 현장 확인 - 20150930 김선호
					List<Sso0055VO> list3 = dao.SelectVbeln2(paramV);					
					SmpComC.moveDs2List(dsVbeln2, Sso0055VO.class, list3 );
					dsVbeln2.setId("ds_vbeln2");					
					/*for( int j = 0; j < dsVbeln2.getRowCount(); j++){
						logger.info(dsVbeln2.getColumnAsString(j,"VBELN"));
					}*/
										
					ZSDT0129 excptVbeln = dao.getExceptionalVbeln(i_mandt, new Vbeln(i_vbeln));
					maxSeq = dao.getMaxSeq(i_mandt, i_vbeln);
					
					//2020.11.11 jss
					String  contrDa = ds_output_ZSDS0063.getColumnAsString(0, "CONTR_DA").replaceAll("-", ""); //수주계약일
					Double  kbetr2 = dao.getZsdt0220Kbetr(i_mandt, contrDa, new Vbeln(i_vbeln));
					
					//2021.02.25 smj 운송지역 수수료 권역관리
					//String  area = dao.getZsdt0223Area(i_mandt, new Vbeln(i_vbeln));
					String  area = dao.getZsdt0223Area(i_mandt, contrDa, new Vbeln(i_vbeln)); //2021.06.28 jss : contrDa추가
					
					if(excptVbeln != null) {	
						ds129.appendRow();
						ds129.setColumn(0,"vbeln",excptVbeln.getVbeln().toString());
						ds129.setColumn(0,"kbetr",excptVbeln.getKbetr() / 10);
						ds129.setColumn(0,"kbetr2",kbetr2==null?"0":kbetr2.toString()); //2020.11.11 jss
						ds129.setColumn(0,"area",area);	//2021.02.25 smj 운송지역 수수료 권역관리
					}else{
						ds129.appendRow();
						ds129.setColumn(0,"kbetr2",kbetr2==null?"0":kbetr2.toString()); //2020.11.11 jss
						ds129.setColumn(0,"area",area);	//2021.02.25 smj 운송지역 수수료 권역관리
					}
					
					adtza90 = dao.getAdtZa90(i_mandt, new Vbeln(i_vbeln));
					
					// 대리점 수수료 기준일자 조회 
					dsDateCommi = dateCommiToDataset(dsDateCommi,i_mandt,i_pspId, i_vbeln);	// 대리점 수수료 기준일자				
					dsDateCommi.setId("ds_date_commi");
										
				}  catch (BizException e) {
					String msg = e.getMessage().toString();
					String message[] = msg.split(",");
				
					if(message.length <= 1) SmpComC.moveDs2Msg(message[0], message[0], model);
					else SmpComC.moveDs2Msg(message[0], message[1], model);
					
				}  catch (Exception e) { 
					e.printStackTrace();
				}
			}
		} catch (BizException e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");

			if(message.length <= 1) SmpComC.moveDs2Msg(message[0], message[0], model);
			else SmpComC.moveDs2Msg(message[0], message[1], model);
			
		} catch (Exception e) {
			// java Exc

			logger.info("@@@@@@@ java Exception ERROR!!!");
			e.printStackTrace();
		}	 
		
		resultVO.addDataset(ds_output_ZSDS0063); 			// 처리결과내역
		resultVO.addDataset(ds_output_ZSDT0090); 			// 처리결과내역
		resultVO.addDataset(ds_output_ZSDT0091); 			// 처리결과내역
		resultVO.addDataset(ds_output_ZSDT0092); 			// 처리결과내역
		resultVO.addDataset(ds_output_ZSDT0093); 			// 처리결과내역
		resultVO.addDataset(ds_output_ZSDT0094_original); 	// 처리결과내역
		resultVO.addDataset(ds_output_ZSDT0094_change); 	// 처리결과내역
		resultVO.addDataset(dsVbeln);
		resultVO.addDataset(dsVbeln2);
		resultVO.addDataset(dsRecad_da);
		resultVO.addDataset(ds129);
		resultVO.addDataset(dsJqpr);
		resultVO.addDataset(dsDateCommi);
		resultVO.addVariable("adtza90", adtza90==null?"0":adtza90.toString());
		resultVO.addVariable("ls_vbpa", ls_vbpa);
		resultVO.addVariable("zsdt0214", Integer.toString(zsdt0214));
		resultVO.addVariable("maxSeq", Integer.toString(maxSeq));
        
		// 2020년 브랜드
		resultVO.addVariable("F_QTNUM", F_QTNUM);
		resultVO.addVariable("F_BRND_FLAG", F_BRND_FLAG);
		
		return resultVO;
	}	

	//=========================================================================================
	//  함수기능 	: 원가산출
	//  기능ID 	: UF024, UF025
	//  개선내역 	: 종속성 체크 로직 변경 sap 호출방식 및 리턴 처리방식 변경
	//  HISTORY : 2016.03.01 최초 코딩 hsi
	//=========================================================================================
	public MipResultVO cost(MipParameterVO paramVO, SqlSession session, Model model) throws RfcException, Exception {
		Rfc rfc = null;
		
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_output_char = paramVO.getDataset("ds_output_char");		// 단가 처리용 데이타셋

		Dataset ds_log = new Dataset("ds_log");                  // ds_log 추가 20150423 김선호 
		ds_log.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);  
		ds_log.addColumn("LOGMSG", ColumnInfo.COLTYPE_STRING, 256);
		Dataset ds_order = paramVO.getDataset("ds_order");
		
		//dutyService.createDao(session);
		dutyNService.createDao(session);
		
		String item = "";
		try {
			String mandt = paramVO.getVariable("G_MANDT");				// MANDT
			String lang  = paramVO.getVariable("G_LANG");				// LANG
			String flag  = "P"; 										// Q-견적, P-계약
			item = ds_output_char.getColumnAsString(0, "HOGI"); 	// 호기 번호
			DutyObj dutyObj = new DutyObj();
			
			//dutyService.genSpecCheck(mandt, flag, item, ds_output_char, ds_log, lang);     // ds_log 추가 20150423 김선호
			dutyNService.genSpecCheck(mandt, flag, item, ds_output_char, ds_log, lang, dutyObj, ds_order);

			if ( ds_log.getRowCount() > 0 ) {   // 종속성 체크후 log 있으면 return
				resultVO.addDataset(ds_log); 	// log 내역 
				return resultVO;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
			Dataset dsError = new Dataset("ds_error");
			dsError.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);
			dsError.addColumn("ERRMSG", ColumnInfo.COLTYPE_STRING, 256);
			dsError.appendRow();
			dsError.setColumn(0, "IDX", "9999");
			dsError.setColumn(0, "ERRMSG", "[필수 입력 에러] 호기번호:" + item + ", " + e.getMessage());
			
			resultVO.addDataset(dsError); 	// 오류결과내역
			return resultVO;
		}
		
		try{

			// CW00010=데이터가 없습니다.
			if ( ds_output_char.getRowCount() < 1 ) {
				throw new BizException("CW00010,''");
			}
			
			// OUTPUT DATASET DECLARE
			Dataset ds_300    = null; // UI로 return할 out dataset
			Dataset ds_202    = null; // UI로 return할 out dataset
			Dataset ds_302    = null; // UI로 return할 out dataset
			Dataset ds_204    = null; // UI로 return할 out dataset
			Dataset ds_304    = null; // UI로 return할 out dataset
			Dataset ds_check = null;
			
			rfc = sso0055NJS.callZwebCo4FindCost(paramVO.getVariable("G_SYSID"), "4", "3", paramVO.getVariable("i_gjahr"), "", paramVO.getVariable("i_seq"), 
					     paramVO.getVariable("i_zdate"), paramVO.getVariable("i_chk"), ds_output_char, ds_check, null, null, null, null, null, null, null, null, null);

			ds_300 = rfc.getDatasetFromJcoTable("T_T300");
			ds_202 = rfc.getDatasetFromJcoTable("T_T202");
			ds_302 = rfc.getDatasetFromJcoTable("T_T302");
			ds_204 = rfc.getDatasetFromJcoTable("T_T204");
			ds_304 = rfc.getDatasetFromJcoTable("T_T304");
			ds_check = rfc.getDatasetFromJcoTable("T_CHEK");
			
			ds_202.setId("ds_t202");
			ds_204.setId("ds_t204");
			ds_300.setId("ds_t300");
			ds_302.setId("ds_t302");
			ds_304.setId("ds_t304");
			ds_check.setId("ds_check");
			
			resultVO.addDataset(ds_300);
			resultVO.addDataset(ds_204);
			resultVO.addDataset(ds_304);
			resultVO.addDataset(ds_check);

			// 2013.03.13 부분원가 처리 : STATE가 모두 X 가 아닐 때만 부분원가 보여 줌 -> 1054 초기화
			int nStateNotXCount = 0;
			String strState = "";
			for ( int i = 0 ; i < ds_check.getRowCount() ; i++ ) {
				strState = ds_check.getColumnAsString(i, "STATE");
				if ( strState == null ) {
					strState = "";
				}
				if ( !strState.equals("X") ) {
					nStateNotXCount++;
				}
			}
			if ( nStateNotXCount > 0 ) {
				this.deleteZsdt1054(paramVO, model, session);
			}
		} catch (RfcException e) { 
			// RfC Exc
			logger.info("@@@@@@@ RfC Exception ERROR!!!");
			e.printStackTrace();
			
			Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", "CE00001", "Y", "Y");
			
			resultVO.addDataset(dsError); 	// 오류결과내역
			//model.addAttribute("resultVO", resultVO);
			return resultVO;
		} catch (BizException e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");

			if ( message.length <= 1) {
				SmpComC.moveDs2Msg(message[0], message[0], model);
			} else {
				SmpComC.moveDs2Msg(message[0], message[1], model);
			}
			
			Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", "CE00001", "Y", "Y");
			
			resultVO.addDataset(dsError); 	// 오류결과내역
			//model.addAttribute("resultVO", resultVO);
			return resultVO;
		} catch (Exception e) {
			// java Exc
			logger.info("@@@@@@@ java Exception ERROR!!!");
			e.printStackTrace();
			
			Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", "CE00001", "Y", "Y");
			
			resultVO.addDataset(dsError); 	// 오류결과내역
			//model.addAttribute("resultVO", resultVO);
			return resultVO;
		}
		
		return resultVO;
	}
	
	//=========================================================================================
    //  함수기능 	: 계약변경 저장
    //  기능ID   	: UF020
    //  개선내역 	: 컨트롤단에서 서비스단으로 이동 
	//            sap을 이용하던 로직을 java로 변경.
    //  HISTORY : 2016.03.11 최초 코딩 hsi
    //=========================================================================================
	public MipResultVO save(MipParameterVO paramVO, SqlSession sessionVO) throws RfcException, Exception {
		MipResultVO resultVO = new MipResultVO();
	
		// INPUT DATASET GET
		String i_pspId 		= paramVO.getVariable("pspId");			 				// 프로젝트번호
		String i_cmd		= paramVO.getVariable("cmd");							// 구분
		String i_seq		= paramVO.getVariable("seq");							// 차수
		String i_mandt		= paramVO.getVariable("G_MANDT");
		String i_userId  	= paramVO.getVariable("G_USER_ID");						// 사용장ID 
		String zsdt0090Finl = "";
		Dataset ds_ZSDS0063	= paramVO.getDataset("ds_output_ZSDS0063");				// ZSDS0063
		Dataset ds_ZSDT0090	= paramVO.getDataset("ds_output_ZSDT0090");				// ZSDT0090
		Dataset ds_ZSDT0091	= paramVO.getDataset("ds_output_ZSDT0091");				// ZSDT0091
		Dataset ds_ZSDT0092	= paramVO.getDataset("ds_output_ZSDT0092");				// ZSDT0092
		Dataset ds_ZSDT0093	= paramVO.getDataset("ds_output_ZSDT0093");				// ZSDT0093
		Dataset ds_ZSDT0094 = paramVO.getDataset("ds_output_ZSDT0094_change");		// ZSDT0094_change
		Dataset dsJqpr = paramVO.getDataset("ds_jqpr");								// ZSDT1108
		
		// 2020 브랜드 추가
		Dataset ds_brnd_hogi_del = paramVO.getDataset("ds_brnd_hogi_del");
		
		String hogi = null;
		
		// define Error Dataset 2013.02.08
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
				
		try {

			// vo 선언
			ZSDT0090 paramZsdt0090 = new ZSDT0090();
			ZSDT0091 paramZsdt0091 = new ZSDT0091();
			ZSDT0092 paramZsdt0092 = new ZSDT0092();
			ZSDT0093 paramZsdt0093 = new ZSDT0093();
			ZSDT0094 paramZsdt0094 = new ZSDT0094();
			ZSDT1108 paramZsdt1108 = new ZSDT1108();
			
			paramZsdt0090.setMANDT(i_mandt);
			paramZsdt0090.setPSPID(i_pspId);
			paramZsdt0090.setSEQ(i_seq);
			paramZsdt0090.setERNAM(i_userId);
			paramZsdt0090.setAENAM(i_userId);
			
			paramZsdt0091.setMANDT(i_mandt);
			paramZsdt0091.setPSPID(i_pspId);
			paramZsdt0091.setSEQ(Integer.parseInt(i_seq));
			paramZsdt0091.setERNAM(i_userId);
			paramZsdt0091.setAENAM(i_userId);
			
			paramZsdt0092.setMANDT(i_mandt);
			paramZsdt0092.setPSPID(i_pspId);
			paramZsdt0092.setSEQ(i_seq);
			paramZsdt0092.setERNAM(i_userId);
			paramZsdt0092.setAENAM(i_userId);
			
			paramZsdt0093.setMANDT(i_mandt);
			paramZsdt0093.setPSPID(i_pspId);
			paramZsdt0093.setSEQ(i_seq);
			paramZsdt0093.setERNAM(i_userId);
			paramZsdt0093.setAENAM(i_userId);
			
			paramZsdt0094.setMANDT(i_mandt);
			paramZsdt0094.setPSPID(i_pspId);
			paramZsdt0094.setSEQ(i_seq);
			paramZsdt0094.setERNAM(i_userId);
			paramZsdt0094.setAENAM(i_userId);
			
			paramZsdt1108.setMandt(i_mandt);
			paramZsdt1108.setVbeln(i_pspId);
			paramZsdt1108.setRemarkseq(i_seq);
			paramZsdt1108.setErnam(i_userId);
			paramZsdt1108.setAenam(i_userId);
			
			logger.debug("eunha debugging i_seq -> "+i_seq);
			
			String inputYn = ds_ZSDS0063.getColumnAsString(0, "KUNZ1");
			
			if("INPUT".equals(inputYn)) inputYn = "Y";
			else inputYn = "N";
			
			// 삭제
			if("DLET".equals(i_cmd)) {
				
				zsdt0090Finl = dao.SelectFinl(paramZsdt0090);
				if("X".equals(zsdt0090Finl)) {
					dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CW10019", "승인처리된 data입니다. \n\n삭제할 수 없습니다.", "Y", "Y");
					resultVO.addDataset(dsError); 	// 오류결과내역
					return resultVO;
				}
				
				dao.deleteZsdt0090(paramZsdt0090);
				dao.deleteZsdt0091(paramZsdt0091);
				dao.deleteZsdt0092(paramZsdt0092);
				dao.deleteZsdt0093(paramZsdt0093);
				dao.deleteZsdt0094(paramZsdt0094);
				dao.deleteZsdt1108(paramZsdt1108);
			} else if("ADMT".equals(i_cmd)) {
				//=========================================================================================
				//  기능ID : UF019  
				//  수정내역 : sap RFC JCO 방식으로 변경.
				//=========================================================================================
				
				// 2017.07.24 save처리 전 데이터 Check logic 추가 by mj.Shin 
				Dataset  dsCheck = check_BeforeSave(i_mandt, i_pspId, i_seq, 
													ds_ZSDT0090, ds_ZSDT0091,ds_ZSDT0092, ds_ZSDT0093, ds_ZSDT0094 );
				if(dsCheck.getRowCount() > 0 ) {
					dsCheck.setId("ds_error");
					resultVO.addDataset(dsCheck); 	// 오류결과내역
					return resultVO;
				}

				// 2017.04.10 save처리 후 승인 처리 되도록 보완 by mj.Shin
				saveTransaction(i_mandt, i_pspId, i_seq,  inputYn,  
								paramZsdt0090, paramZsdt0091, paramZsdt0092, paramZsdt0093, paramZsdt0094, paramZsdt1108,
								ds_ZSDT0090, ds_ZSDT0091,ds_ZSDT0092, ds_ZSDT0093, ds_ZSDT0094, ds_brnd_hogi_del, dsJqpr, paramVO.getVariable("G_SYSID"));
				
				//-----<		2017.03.13 RFC Test 구분자 추가 by mj.Shin Start				
				// 서비스호출
//				String i_rfcName = "ZWEB_SD_CHN_SO_N";
//				
//				Testind TestindInfo = TestindDao.selectTestUser(i_mandt, i_rfcName, i_userId);
//
//		    	if (TestindInfo == null) {
//		    		i_rfcName = "ZWEB_SD_CHN_SO";
//		    	}	
		    	//----->		2017.03.13 RFC Test 구분자 추가 by mj.Shin End 
		    	
//				Rfc rfc = null;
//				rfc = sso0055NJS.callZwebSdChnSo(paramVO.getVariable("G_SYSID"), i_pspId, i_cmd, i_seq, "ZWEB_SD_CHN_SO", ds_ZSDS0063);
//				ds_bapiret2 = rfc.getDatasetFromJcoTable("TT_BAPIRET2");

				Dataset ds_bapiret2 = BAPIRET2.getDataset();
				Object obj[] = new Object[] {
						  new ZSDT0092[]{}
						, new ZSDT0093[]{}
						, new ZSDT0090[]{}
						, i_cmd
						, i_pspId
						, i_seq
						, new ZSDT0091[]{}
						, new ZSDT0094[]{}
						, new ZSDT0094[]{}
						, new BAPIRET2[]{}
						, new ZSDS0063[]{}
				};

				SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), i_mandt
												   , "hdel.sd.sso.domain.ZWEB_SD_CHN_SO"
												   , obj, false);
				
				if (((BAPIRET2[])stub.getOutput("TT_BAPIRET2")).length > 0) {
					for(BAPIRET2 bp2 : (BAPIRET2[])stub.getOutput("TT_BAPIRET2")) {
						if(bp2.getTYPE().equals("S"))
							continue;
						dsError.insertRow(1);
						dsError.setColumn(0, "IDX", 4);
						dsError.setColumn(0, "ERRMSG", bp2.getMESSAGE().toString());
						break;
					}
					//SAP RFC 수행결과 return message Type이 S로만 구성되었는데도 처리실패인 경우가 발생하기 때문에 임시조치
					if(dsError.getRowCount() == 0) {
						dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "", ((BAPIRET2[])stub.getOutput("TT_BAPIRET2"))[0].getMESSAGE().toString(), "Y", "Y");
					}
					dsError.setId("ds_error");
					resultVO.addDataset(dsError);
				}

			} else {
				
				// 2017.07.24 save처리 전 데이터 Check logic 추가 by mj.Shin 
				Dataset  dsCheck = check_BeforeSave(i_mandt, i_pspId, i_seq, 
													ds_ZSDT0090, ds_ZSDT0091,ds_ZSDT0092, ds_ZSDT0093, ds_ZSDT0094 );
				if(dsCheck.getRowCount() > 0 ) {
					dsCheck.setId("ds_error");
					resultVO.addDataset(dsCheck); 	// 오류결과내역
					return resultVO;
				}
				
				// 2017.04.10 save처리 로직 공통화 by mj.Shin 
				saveTransaction(i_mandt, i_pspId, i_seq,  inputYn,  
								paramZsdt0090, paramZsdt0091, paramZsdt0092, paramZsdt0093, paramZsdt0094, paramZsdt1108,
								ds_ZSDT0090, ds_ZSDT0091,ds_ZSDT0092, ds_ZSDT0093, ds_ZSDT0094, ds_brnd_hogi_del, dsJqpr, paramVO.getVariable("G_SYSID"));
			} // SAVE 
			
		} catch (RfcException e) { 
			// RfC Exc
			logger.info("@@@@@@@ RfC Exception ERROR!!!");
			e.printStackTrace();
			
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", "CE00001", "Y", "Y");
			
			resultVO.addDataset(dsError); 	// 오류결과내역

			return resultVO;
		} catch (Exception e) {
			// java Exc
			logger.info("@@@@@@@ java Exception ERROR!!!");
			e.printStackTrace();
			
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError); 	// 오류결과내역
		}
		
		return resultVO;
	}	
	
	private Dataset check_BeforeSave(String mandt, String pspId, String seq,
			 Dataset ds_ZSDT0090,	Dataset ds_ZSDT0091, Dataset ds_ZSDT0092, Dataset ds_ZSDT0093, Dataset ds_ZSDT0094) {

		String strChgNetwr = "N";			//헤더 기준 금액 변경 여부 
		String strChgNetwr_posid = "N";		//호기기준 금액 변경 여부
		String strNewHogi = "N";			//호기 추가 여부 
		String strAdjustBplan = "N";  		//조정청구계획 존재 여부
		String strChfksaf;
		
		BigDecimal zero = new BigDecimal("0");
		BigDecimal Netwr = new BigDecimal("0");
		BigDecimal Chnetwr = new BigDecimal("0");
		
		Dataset dsCheck = new Dataset("ds_error");		// sap의 에러메시지를 datSet으로 담기위함.
		
		dsCheck.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);
		dsCheck.addColumn("ERRMSG", ColumnInfo.COLTYPE_STRING, 256);
				
		Sso0055VO docStat = dao.SelectDocStatusGBSTK(mandt, pspId);
		int cntFksaf = dao.SelectFksaf(mandt, pspId);
		
		if ("C".equals(docStat.getGBSTK()) || cntFksaf == 0) {
			for(int i = 0; i < ds_ZSDT0090.getRowCount(); i++) {
				
				Netwr = new BigDecimal(DatasetUtility.getString(ds_ZSDT0090,  i, "NETWR"));
				Chnetwr = new BigDecimal(DatasetUtility.getString(ds_ZSDT0090, i, "CHNETWR"));
				if (Netwr.compareTo(Chnetwr) != 0)
				{
					strChgNetwr = "Y";	
					break;
				}
			}
			 
			for(int i = 0; i < ds_ZSDT0091.getRowCount(); i++) {
				
				Netwr = new BigDecimal(DatasetUtility.getString(ds_ZSDT0091, i,"NETWR"));
				Chnetwr = new BigDecimal(DatasetUtility.getString(ds_ZSDT0091, i,"CHNETWR"));
				if ( Netwr.compareTo(zero) == 0 && Chnetwr.compareTo(zero) != 0)
				{
					strNewHogi = "Y";	
					break;
				} else if ( Netwr.compareTo(zero) != 0 && Netwr.compareTo(Chnetwr) != 0 ) {
					strChgNetwr_posid = "Y";	
					break;					
				}
				
			}
			
			for(int i = 0; i < ds_ZSDT0093.getRowCount() ; i++) {
				
				Chnetwr = new BigDecimal(DatasetUtility.getString(ds_ZSDT0093, i, "CHFAKWR"));
				strChfksaf = DatasetUtility.getString(ds_ZSDT0093, i, "CHFKSAF");
				if( Chnetwr.compareTo(zero) == 0 && "A".equals(strChfksaf))
				{
					strAdjustBplan = "Y";	
					break;
				}
			}
			
			if(strChgNetwr == "N" && ( strNewHogi == "Y" || strChgNetwr_posid == "Y" ) && strAdjustBplan == "N")
			{	dsCheck.deleteAll();			
				dsCheck.appendRow();
				dsCheck.setColumn(0, "IDX", "CW10025");
				dsCheck.setColumn(0, "ERRMSG", "조정 대금청구 계획(금액=0)을 반드시 청구(Billing)하여야 합니다.");
				logger.info("@@@@@@@ Sales Order document's status Error!!!, Please append to adjusted(amount = 0) billing plan.!!!");
			}
		}
		
		return dsCheck;
	}
	
	private void saveTransaction(String mandt, String pspId, String seq, String inputYn,
								 ZSDT0090 paramZsdt0090, ZSDT0091 paramZsdt0091, ZSDT0092 paramZsdt0092, ZSDT0093 paramZsdt0093, ZSDT0094 paramZsdt0094, ZSDT1108 paramZsdt1108,
								 Dataset ds_ZSDT0090,	Dataset ds_ZSDT0091, Dataset ds_ZSDT0092, Dataset ds_ZSDT0093, Dataset ds_ZSDT0094, Dataset ds_brnd_hogi_del, Dataset dsJqpr, String sysId) {			
		
		String strPosnr = "";
		String[] arrPosnr = null;
		String strPosnrChk = "";
		String hogi = null;
		
		BigDecimal zero = new BigDecimal(0);
		int iZero = new Integer(0);

		// 신규 생성일 경우 해당 차수 모두 삭제 후 처리
		if("Y".equals(inputYn)) {
			dao.deleteZsdt0090(paramZsdt0090);
			dao.deleteZsdt0091(paramZsdt0091);
			dao.deleteZsdt0092(paramZsdt0092);
			dao.deleteZsdt0093(paramZsdt0093);
			dao.deleteZsdt0094(paramZsdt0094);
		}
		
		// header
		for(int i = 0; i < ds_ZSDT0090.getRowCount(); i++) {
			if(DatasetUtility.getString(ds_ZSDT0090, i, "VBELN") == null) paramZsdt0090.setVBELN("");		else paramZsdt0090.setVBELN(DatasetUtility.getString(ds_ZSDT0090, i, "VBELN"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "ZQNTY") == null) paramZsdt0090.setZQNTY(""); 		else paramZsdt0090.setZQNTY(DatasetUtility.getString(ds_ZSDT0090, i, "ZQNTY"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "NETWR") == null) paramZsdt0090.setNETWR(zero); 	else paramZsdt0090.setNETWR(new BigDecimal(DatasetUtility.getString(ds_ZSDT0090, i, "NETWR")));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "WAVWR") == null) paramZsdt0090.setWAVWR(zero); 	else paramZsdt0090.setWAVWR(new BigDecimal(DatasetUtility.getString(ds_ZSDT0090, i, "WAVWR")));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "COMMI") == null) paramZsdt0090.setCOMMI(zero); 	else paramZsdt0090.setCOMMI(new BigDecimal(DatasetUtility.getString(ds_ZSDT0090, i, "COMMI")));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "VDATU") == null) paramZsdt0090.setVDATU(""); 		else paramZsdt0090.setVDATU(DatasetUtility.getString(ds_ZSDT0090, i, "VDATU"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "WAERK") == null) paramZsdt0090.setWAERK(""); 		else paramZsdt0090.setWAERK(DatasetUtility.getString(ds_ZSDT0090, i, "WAERK"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "CHNETWR") == null) paramZsdt0090.setCHNETWR(zero); else paramZsdt0090.setCHNETWR(new BigDecimal(DatasetUtility.getString(ds_ZSDT0090, i, "CHNETWR")));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "CHWAVWR") == null) paramZsdt0090.setCHWAVWR(zero); else paramZsdt0090.setCHWAVWR(new BigDecimal(DatasetUtility.getString(ds_ZSDT0090, i, "CHWAVWR")));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "CHCOMMI") == null) paramZsdt0090.setCHCOMMI(zero); else paramZsdt0090.setCHCOMMI(new BigDecimal(DatasetUtility.getString(ds_ZSDT0090, i, "CHCOMMI")));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "CHVDATU") == null) paramZsdt0090.setCHVDATU(""); 	else paramZsdt0090.setCHVDATU(DatasetUtility.getString(ds_ZSDT0090, i, "CHVDATU"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "CHWAERK") == null) paramZsdt0090.setCHWAERK(""); 	else paramZsdt0090.setCHWAERK(DatasetUtility.getString(ds_ZSDT0090, i, "CHWAERK"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "FINL") == null) paramZsdt0090.setFINL(""); 		else paramZsdt0090.setFINL(DatasetUtility.getString(ds_ZSDT0090, i, "FINL"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "ERDAT") == null) paramZsdt0090.setERDAT(""); 		else paramZsdt0090.setERDAT(DatasetUtility.getString(ds_ZSDT0090, i, "ERDAT"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "BIGO1") == null) paramZsdt0090.setBIGO1(""); 		else paramZsdt0090.setBIGO1(DatasetUtility.getString(ds_ZSDT0090, i, "BIGO1"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "BIGO2") == null) paramZsdt0090.setBIGO2(""); 		else paramZsdt0090.setBIGO2(DatasetUtility.getString(ds_ZSDT0090, i, "BIGO2"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "BIGO3") == null) paramZsdt0090.setBIGO3(""); 		else paramZsdt0090.setBIGO3(DatasetUtility.getString(ds_ZSDT0090, i, "BIGO3"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "BIGO4") == null) paramZsdt0090.setBIGO4(""); 		else paramZsdt0090.setBIGO4(DatasetUtility.getString(ds_ZSDT0090, i, "BIGO4"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "BIGO5") == null) paramZsdt0090.setBIGO5(""); 		else paramZsdt0090.setBIGO5(DatasetUtility.getString(ds_ZSDT0090, i, "BIGO5"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES") == null) paramZsdt0090.setCHRES(""); 		else paramZsdt0090.setCHRES(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP") == null) paramZsdt0090.setREDEP(""); 		else paramZsdt0090.setREDEP(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_A") == null) paramZsdt0090.setCHRES_A(""); 	else paramZsdt0090.setCHRES_A(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_A"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_A") == null) paramZsdt0090.setREDEP_A(""); 	else paramZsdt0090.setREDEP_A(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_A"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_B") == null) paramZsdt0090.setCHRES_B(""); 	else paramZsdt0090.setCHRES_B(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_B"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_B") == null) paramZsdt0090.setREDEP_B(""); 	else paramZsdt0090.setREDEP_B(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_B"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_C") == null) paramZsdt0090.setCHRES_C(""); 	else paramZsdt0090.setCHRES_C(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_C"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_C") == null) paramZsdt0090.setREDEP_C(""); 	else paramZsdt0090.setREDEP_C(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_C"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_D") == null) paramZsdt0090.setCHRES_D(""); 	else paramZsdt0090.setCHRES_D(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_D"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_D") == null) paramZsdt0090.setREDEP_D(""); 	else paramZsdt0090.setREDEP_D(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_D"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_E") == null) paramZsdt0090.setCHRES_E(""); 	else paramZsdt0090.setCHRES_E(DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_E"));
			if(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_E") == null) paramZsdt0090.setREDEP_E(""); 	else paramZsdt0090.setREDEP_E(DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_E"));
			
			// 2017.03.08 Check check-box value(SHCHK, CHSHCHK) by mj.Shin			
			if("X".equals(DatasetUtility.getString(ds_ZSDT0090, i, "SHCHK")))
				paramZsdt0090.setSHCHK(DatasetUtility.getString(ds_ZSDT0090, i, "SHCHK"));
			else
				paramZsdt0090.setSHCHK("");
			if("X".equals(DatasetUtility.getString(ds_ZSDT0090, i, "CHSHCHK")))
				paramZsdt0090.setCHSHCHK(DatasetUtility.getString(ds_ZSDT0090, i, "CHSHCHK"));										
			else
				paramZsdt0090.setCHSHCHK("");
			
			if(DatasetUtility.getString(ds_ZSDT0090, i, "UCH_DUTY") == null) paramZsdt0090.setUCH_DUTY(""); 	else paramZsdt0090.setUCH_DUTY(DatasetUtility.getString(ds_ZSDT0090, i, "UCH_DUTY"));
			
			dao.mergeHeader(paramZsdt0090);                    // 계약변경 header save
		}
		
		// item
		for(int i = 0; i < ds_ZSDT0091.getRowCount(); i++) {
			if(DatasetUtility.getString(ds_ZSDT0091, i, "VBELN") == null) paramZsdt0091.setVBELN("");			else paramZsdt0091.setVBELN(DatasetUtility.getString(ds_ZSDT0091, i, "VBELN"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "POSNR") == null) paramZsdt0091.setPOSNR("");			else paramZsdt0091.setPOSNR(DatasetUtility.getString(ds_ZSDT0091, i, "POSNR"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "HOGI") == null) paramZsdt0091.setHOGI("");				else paramZsdt0091.setHOGI(DatasetUtility.getString(ds_ZSDT0091, i, "HOGI"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "AUART") == null) paramZsdt0091.setAUART("");			else paramZsdt0091.setAUART(DatasetUtility.getString(ds_ZSDT0091, i, "AUART"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "KZWI4") == null) paramZsdt0091.setKZWI4(zero);			else paramZsdt0091.setKZWI4(new BigDecimal(DatasetUtility.getString(ds_ZSDT0091, i, "KZWI4")));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "NETWR") == null) paramZsdt0091.setNETWR(zero);			else paramZsdt0091.setNETWR(new BigDecimal(DatasetUtility.getString(ds_ZSDT0091, i, "NETWR")));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "WAVWR") == null) paramZsdt0091.setWAVWR(zero);			else paramZsdt0091.setWAVWR(new BigDecimal(DatasetUtility.getString(ds_ZSDT0091, i, "WAVWR")));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "EDATU") == null) paramZsdt0091.setEDATU("");			else paramZsdt0091.setEDATU(DatasetUtility.getString(ds_ZSDT0091, i, "EDATU"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "REPMO") == null) paramZsdt0091.setREPMO(iZero);		else paramZsdt0091.setREPMO(DatasetUtility.getInt(ds_ZSDT0091, i, "REPMO"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "GUAMO") == null) paramZsdt0091.setGUAMO(iZero);		else paramZsdt0091.setGUAMO(DatasetUtility.getInt(ds_ZSDT0091, i, "GUAMO"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "WORMO") == null) paramZsdt0091.setWORMO(iZero);		else paramZsdt0091.setWORMO(DatasetUtility.getInt(ds_ZSDT0091, i, "WORMO"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "CHNETWR") == null) paramZsdt0091.setCHNETWR(zero);		else paramZsdt0091.setCHNETWR(new BigDecimal(DatasetUtility.getString(ds_ZSDT0091, i, "CHNETWR")));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "CHWAVWR") == null) paramZsdt0091.setCHWAVWR(zero);		else paramZsdt0091.setCHWAVWR(new BigDecimal(DatasetUtility.getString(ds_ZSDT0091, i, "CHWAVWR")));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "CHEDATU") == null) paramZsdt0091.setCHEDATU("");		else paramZsdt0091.setCHEDATU(DatasetUtility.getString(ds_ZSDT0091, i, "CHEDATU"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "CHREPMO") == null) paramZsdt0091.setCHREPMO(iZero);	else paramZsdt0091.setCHREPMO(DatasetUtility.getInt(ds_ZSDT0091, i, "CHREPMO"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "CHGUAMO") == null) paramZsdt0091.setCHGUAMO(iZero);	else paramZsdt0091.setCHGUAMO(DatasetUtility.getInt(ds_ZSDT0091, i, "CHGUAMO"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "CHWORMO") == null) paramZsdt0091.setCHWORMO(0);		else paramZsdt0091.setCHWORMO(DatasetUtility.getInt(ds_ZSDT0091, i, "CHWORMO"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "WAERK") == null) paramZsdt0091.setWAERK("");			else paramZsdt0091.setWAERK(DatasetUtility.getString(ds_ZSDT0091, i, "WAERK"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "CHCK") == null) paramZsdt0091.setCHCK("");				else paramZsdt0091.setCHCK(DatasetUtility.getString(ds_ZSDT0091, i, "CHCK"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "HOGI_CANC") == null) paramZsdt0091.setHOGI_CANC("");	else paramZsdt0091.setHOGI_CANC(DatasetUtility.getString(ds_ZSDT0091, i, "HOGI_CANC"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "ERDAT") == null) paramZsdt0091.setERDAT("");			else paramZsdt0091.setERDAT(DatasetUtility.getString(ds_ZSDT0091, i, "ERDAT"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "MATNR") == null) paramZsdt0091.setMATNR("");			else paramZsdt0091.setMATNR(DatasetUtility.getString(ds_ZSDT0091, i, "MATNR"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "CLASS") == null) paramZsdt0091.setCLASS("");			else paramZsdt0091.setCLASS(DatasetUtility.getString(ds_ZSDT0091, i, "CLASS"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "SPEC_STAT") == null) paramZsdt0091.setSPEC_STAT("");	else paramZsdt0091.setSPEC_STAT(DatasetUtility.getString(ds_ZSDT0091, i, "SPEC_STAT"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "ARKTX") == null) paramZsdt0091.setARKTX("");			else paramZsdt0091.setARKTX(DatasetUtility.getString(ds_ZSDT0091, i, "ARKTX"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "ABGRU") == null) paramZsdt0091.setABGRU("");			else paramZsdt0091.setABGRU(DatasetUtility.getString(ds_ZSDT0091, i, "ABGRU"));
			
			
			// 2020 브랜드
			if(DatasetUtility.getString(ds_ZSDT0091, i, "BRNDCD") == null) paramZsdt0091.setBRNDCD("");	 else paramZsdt0091.setBRNDCD(DatasetUtility.getString(ds_ZSDT0091, i, "BRNDCD"));
			if(DatasetUtility.getString(ds_ZSDT0091, i, "BRNDSEQ") == null) {
				paramZsdt0091.setBRNDSEQ("000");	
			} else {
				if( "".equals(DatasetUtility.getString(ds_ZSDT0091, i, "BRNDSEQ"))) {
					paramZsdt0091.setBRNDSEQ("000");
				} else {
					paramZsdt0091.setBRNDSEQ(DatasetUtility.getString(ds_ZSDT0091, i, "BRNDSEQ"));
				}
			}
			
			// 호기가 없을 경우 신규채번한다.
			if("".equals(paramZsdt0091.getHOGI())) {
				hogi = selectMaxHogi(mandt, pspId, seq, paramZsdt0091.getMATNR(), "item");				
				paramZsdt0091.setHOGI(hogi);
			} 
			
			// 신규생성인 경우 최초 한번은 select insert를 한 후 수정건에 대한 update를 진행한다.
			if("Y".equals(inputYn) && i == 0) dao.insertItem(paramZsdt0091);
			
			if("insert".equals(DatasetUtility.getString(ds_ZSDT0091, i, "STATUS")) || "update".equals(DatasetUtility.getString(ds_ZSDT0091, i, "STATUS"))) {
				dao.mergeItem(paramZsdt0091);                    // 계약변경 item save
			}
		}
		
		dao.deleteZsdt0092(paramZsdt0092);
		// billing original
		for(int i = 0; i < ds_ZSDT0092.getRowCount(); i++) {
			if(DatasetUtility.getString(ds_ZSDT0092, i, "VBELN") == null) paramZsdt0092.setVBELN("");	else paramZsdt0092.setVBELN(DatasetUtility.getString(ds_ZSDT0092, i, "VBELN"));
			if(DatasetUtility.getString(ds_ZSDT0092, i, "POSNR") == null) paramZsdt0092.setPOSNR("");	else paramZsdt0092.setPOSNR(DatasetUtility.getString(ds_ZSDT0092, i, "POSNR"));
			if(DatasetUtility.getString(ds_ZSDT0092, i, "HOGI") == null) paramZsdt0092.setHOGI("");	 	else paramZsdt0092.setHOGI(DatasetUtility.getString(ds_ZSDT0092, i, "HOGI"));
			if(DatasetUtility.getString(ds_ZSDT0092, i, "FKDAT") == null) paramZsdt0092.setFKDAT("");	else paramZsdt0092.setFKDAT(DatasetUtility.getString(ds_ZSDT0092, i, "FKDAT"));
			if(DatasetUtility.getString(ds_ZSDT0092, i, "MLBEZ") == null) paramZsdt0092.setMLBEZ("");	else paramZsdt0092.setMLBEZ(DatasetUtility.getString(ds_ZSDT0092, i, "MLBEZ"));
			if(DatasetUtility.getString(ds_ZSDT0092, i, "ZTERM") == null) paramZsdt0092.setZTERM("");	else paramZsdt0092.setZTERM(DatasetUtility.getString(ds_ZSDT0092, i, "ZTERM"));
			if(DatasetUtility.getString(ds_ZSDT0092, i, "FKSAF") == null) paramZsdt0092.setFKSAF("");	else paramZsdt0092.setFKSAF(DatasetUtility.getString(ds_ZSDT0092, i, "FKSAF"));
			if(DatasetUtility.getString(ds_ZSDT0092, i, "FPLTR") == null) paramZsdt0092.setFPLTR("");	else paramZsdt0092.setFPLTR(DatasetUtility.getString(ds_ZSDT0092, i, "FPLTR"));
			if(DatasetUtility.getString(ds_ZSDT0092, i, "MSTXT") == null) paramZsdt0092.setMSTXT("");	else paramZsdt0092.setMSTXT(DatasetUtility.getString(ds_ZSDT0092, i, "MSTXT"));
			if(DatasetUtility.getString(ds_ZSDT0092, i, "FAKWR") == null) paramZsdt0092.setFAKWR(zero); else paramZsdt0092.setFAKWR(new BigDecimal(DatasetUtility.getString(ds_ZSDT0092, i, "FAKWR")));
			if(DatasetUtility.getString(ds_ZSDT0092, i, "WAERK") == null) paramZsdt0092.setWAERK("");	else paramZsdt0092.setWAERK(DatasetUtility.getString(ds_ZSDT0092, i, "WAERK"));
			if(DatasetUtility.getString(ds_ZSDT0092, i, "ERDAT") == null) paramZsdt0092.setERDAT("");	else paramZsdt0092.setERDAT(DatasetUtility.getString(ds_ZSDT0092, i, "ERDAT"));
			
			dao.mergeBillO(paramZsdt0092);                    // 계약변경 billing original save
		}
		
		dao.deleteZsdt0093(paramZsdt0093);
		// billing change
		for(int i = 0; i < ds_ZSDT0093.getRowCount(); i++) {
			if(DatasetUtility.getString(ds_ZSDT0093, i, "VBELN") == null) paramZsdt0093.setVBELN("");	 	else paramZsdt0093.setVBELN(DatasetUtility.getString(ds_ZSDT0093, i, "VBELN"));
			if(DatasetUtility.getString(ds_ZSDT0093, i, "POSNR") == null) paramZsdt0093.setPOSNR("");	 	else paramZsdt0093.setPOSNR(DatasetUtility.getString(ds_ZSDT0093, i, "POSNR"));
			if(DatasetUtility.getString(ds_ZSDT0093, i, "HOGI") == null) paramZsdt0093.setHOGI("");		 	else paramZsdt0093.setHOGI(DatasetUtility.getString(ds_ZSDT0093, i, "HOGI"));
			if(DatasetUtility.getString(ds_ZSDT0093, i, "CHFKDAT") == null) paramZsdt0093.setCHFKDAT("");	else paramZsdt0093.setCHFKDAT(DatasetUtility.getString(ds_ZSDT0093, i, "CHFKDAT"));
			if(DatasetUtility.getString(ds_ZSDT0093, i, "CHMLBEZ") == null) paramZsdt0093.setCHMLBEZ("");	else paramZsdt0093.setCHMLBEZ(DatasetUtility.getString(ds_ZSDT0093, i, "CHMLBEZ"));
			if(DatasetUtility.getString(ds_ZSDT0093, i, "CHZTERM") == null) paramZsdt0093.setCHZTERM("");	else paramZsdt0093.setCHZTERM(DatasetUtility.getString(ds_ZSDT0093, i, "CHZTERM"));
			if(DatasetUtility.getString(ds_ZSDT0093, i, "CHFKSAF") == null) paramZsdt0093.setCHFKSAF("");	else paramZsdt0093.setCHFKSAF(DatasetUtility.getString(ds_ZSDT0093, i, "CHFKSAF"));
			if(DatasetUtility.getString(ds_ZSDT0093, i, "FPLTR") == null) paramZsdt0093.setFPLTR("");		else paramZsdt0093.setFPLTR(DatasetUtility.getString(ds_ZSDT0093, i, "FPLTR"));
			if(DatasetUtility.getString(ds_ZSDT0093, i, "CHMSTXT") == null) paramZsdt0093.setCHMSTXT("");	else paramZsdt0093.setCHMSTXT(DatasetUtility.getString(ds_ZSDT0093, i, "CHMSTXT"));
			if(DatasetUtility.getString(ds_ZSDT0093, i, "CHFAKWR") == null) paramZsdt0093.setCHFAKWR(zero); else paramZsdt0093.setCHFAKWR(new BigDecimal(DatasetUtility.getString(ds_ZSDT0093, i, "CHFAKWR")));
			if(DatasetUtility.getString(ds_ZSDT0093, i, "WAERK") == null) paramZsdt0093.setWAERK("");		else paramZsdt0093.setWAERK(DatasetUtility.getString(ds_ZSDT0093, i, "WAERK"));
			if(DatasetUtility.getString(ds_ZSDT0093, i, "ERDAT") == null) paramZsdt0093.setERDAT("");		else paramZsdt0093.setERDAT(DatasetUtility.getString(ds_ZSDT0093, i, "ERDAT"));
			
			// 호기가 없을 경우 신규채번한다.
			if("".equals(paramZsdt0093.getHOGI()) && !"000000".equals(paramZsdt0093.getPOSNR())) {
				hogi = selectHogi(mandt, paramZsdt0093.getVBELN(), pspId, seq, paramZsdt0093.getPOSNR());				
				paramZsdt0093.setHOGI(hogi);
			} 
			
			dao.mergeBillC(paramZsdt0093);                    // 계약변경 billing change save
		}

		// 2020 브랜드
		// 브랜드 변경에 의해서 브랜드변경 호기 삭제
		if(ds_brnd_hogi_del != null && !"Y".equals(inputYn)) {
			if(ds_brnd_hogi_del.getRowCount()>0) {
				for(int i=0; i < ds_brnd_hogi_del.getRowCount(); i++) {
					if(DatasetUtility.getString(ds_brnd_hogi_del, i, "POSNR") == null) paramZsdt0094.setPOSNR(""); else paramZsdt0094.setPOSNR(DatasetUtility.getString(ds_brnd_hogi_del, i, "POSNR"));
					if(DatasetUtility.getString(ds_brnd_hogi_del, i, "HOGI") == null) paramZsdt0094.setHOGI(""); else paramZsdt0094.setHOGI(DatasetUtility.getString(ds_brnd_hogi_del, i, "HOGI"));					
					if(DatasetUtility.getString(ds_brnd_hogi_del, i, "MATNR") == null) paramZsdt0094.setMATNR(""); else paramZsdt0094.setMATNR(DatasetUtility.getString(ds_brnd_hogi_del, i, "MATNR"));
					if(DatasetUtility.getString(ds_brnd_hogi_del, i, "BRNDSEQ") == null) paramZsdt0094.setBRNDSEQ(""); else paramZsdt0094.setBRNDSEQ(DatasetUtility.getString(ds_brnd_hogi_del, i, "BRNDSEQ"));
					if(DatasetUtility.getString(ds_brnd_hogi_del, i, "ZPRDGBN") == null) paramZsdt0094.setZPRDGBN(""); else paramZsdt0094.setZPRDGBN(DatasetUtility.getString(ds_brnd_hogi_del, i, "ZPRDGBN"));
					if(DatasetUtility.getString(ds_brnd_hogi_del, i, "BRNDCD") == null) paramZsdt0094.setBRNDCD(""); else paramZsdt0094.setBRNDCD(DatasetUtility.getString(ds_brnd_hogi_del, i, "BRNDCD"));
					// 브랜드 변경에 따른 호기 Zsdt0094 삭제 처리
					dao.deletePosnrZsdt0094(paramZsdt0094);
					dao.insertPosnrZsdt0094(paramZsdt0094);
				}
				ds_brnd_hogi_del.clearAll();
			}
		}

		// spec
		for(int i = 0; i < ds_ZSDT0094.getRowCount(); i++) {
			if(DatasetUtility.getString(ds_ZSDT0094, i, "POSNR") == null) paramZsdt0094.setPOSNR("");				else paramZsdt0094.setPOSNR(DatasetUtility.getString(ds_ZSDT0094, i, "POSNR"));
			if(DatasetUtility.getString(ds_ZSDT0094, i, "HOGI") == null) paramZsdt0094.setHOGI("");				 	else paramZsdt0094.setHOGI(DatasetUtility.getString(ds_ZSDT0094, i, "HOGI"));
			if(DatasetUtility.getString(ds_ZSDT0094, i, "NAM_CHAR") == null) paramZsdt0094.setNAM_CHAR("");		 	else paramZsdt0094.setNAM_CHAR(DatasetUtility.getString(ds_ZSDT0094, i, "NAM_CHAR"));
			if(DatasetUtility.getString(ds_ZSDT0094, i, "ATBEZ") == null) paramZsdt0094.setATBEZ("");				else paramZsdt0094.setATBEZ(DatasetUtility.getString(ds_ZSDT0094, i, "ATBEZ"));
			if(DatasetUtility.getString(ds_ZSDT0094, i, "MATNR") == null) paramZsdt0094.setMATNR("");				else paramZsdt0094.setMATNR(DatasetUtility.getString(ds_ZSDT0094, i, "MATNR"));
			if(DatasetUtility.getString(ds_ZSDT0094, i, "CHAR_VALUE") == null) paramZsdt0094.setCHAR_VALUE("");	 	else paramZsdt0094.setCHAR_VALUE(DatasetUtility.getString(ds_ZSDT0094, i, "CHAR_VALUE"));
			if(DatasetUtility.getString(ds_ZSDT0094, i, "ATWTB") == null) paramZsdt0094.setATWTB("");				else paramZsdt0094.setATWTB(DatasetUtility.getString(ds_ZSDT0094, i, "ATWTB"));
			if(DatasetUtility.getString(ds_ZSDT0094, i, "CNT") == null) paramZsdt0094.setCNT("");					else paramZsdt0094.setCNT(DatasetUtility.getString(ds_ZSDT0094, i, "CNT"));
			if(DatasetUtility.getString(ds_ZSDT0094, i, "ERDAT") == null) paramZsdt0094.setERDAT("");				else paramZsdt0094.setERDAT(DatasetUtility.getString(ds_ZSDT0094, i, "ERDAT"));
			if(DatasetUtility.getString(ds_ZSDT0094, i, "CNGBN") == null) paramZsdt0094.setCNGBN("");				else paramZsdt0094.setCNGBN(DatasetUtility.getString(ds_ZSDT0094, i, "CNGBN"));
			if(DatasetUtility.getString(ds_ZSDT0094, i, "ATKLA") == null) paramZsdt0094.setATKLA("");				else paramZsdt0094.setATKLA(DatasetUtility.getString(ds_ZSDT0094, i, "ATKLA"));
			if(DatasetUtility.getString(ds_ZSDT0094, i, "CHAR_VALUEMD") == null) paramZsdt0094.setCHAR_VALUEMD(""); else paramZsdt0094.setCHAR_VALUEMD(DatasetUtility.getString(ds_ZSDT0094, i, "CHAR_VALUEMD"));
			
			// 호기가 없을 경우 신규채번한다.
			if("".equals(paramZsdt0094.getHOGI())) {
				if("0".equals(DatasetUtility.getString(ds_ZSDT0094, i, "SEQ"))) {
					arrPosnr = strPosnr.split(",");
					strPosnrChk = "N";
					
					for(int j = 0; j < arrPosnr.length; j++) {
						if(arrPosnr[j].equals(DatasetUtility.getString(ds_ZSDT0094, i, "POSNR"))) {
							strPosnrChk = "Y";
							break;
						}
					}
					
					if("N".equals(strPosnrChk)) {
						hogi = selectMaxHogi(mandt, pspId, seq, paramZsdt0094.getMATNR(), "spec");
						paramZsdt0094.setHOGI(hogi);
						dao.insertSpecCsub(paramZsdt0094);
						strPosnr += DatasetUtility.getString(ds_ZSDT0094, i, "POSNR") + ",";
					}
				}
			}

			// 신규생성인 경우 최초 한번은 select insert를 한 후 수정건에 대한 update를 진행한다.
			if("Y".equals(inputYn) && i == 0) {
				dao.insertSpecC(paramZsdt0094);

				// 2020 브랜드
				// 브랜드 변경에 의해서 브랜드변경 호기 삭제
				if(ds_brnd_hogi_del != null) {
					if(ds_brnd_hogi_del.getRowCount()>0) {
						for(int j=0; j < ds_brnd_hogi_del.getRowCount(); j++) {
							if(DatasetUtility.getString(ds_brnd_hogi_del, j, "POSNR") == null) paramZsdt0094.setPOSNR(""); else paramZsdt0094.setPOSNR(DatasetUtility.getString(ds_brnd_hogi_del, j, "POSNR"));
							if(DatasetUtility.getString(ds_brnd_hogi_del, j, "HOGI") == null) paramZsdt0094.setHOGI(""); else paramZsdt0094.setHOGI(DatasetUtility.getString(ds_brnd_hogi_del, j, "HOGI"));					
							if(DatasetUtility.getString(ds_brnd_hogi_del, j, "MATNR") == null) paramZsdt0094.setMATNR(""); else paramZsdt0094.setMATNR(DatasetUtility.getString(ds_brnd_hogi_del, j, "MATNR"));
							if(DatasetUtility.getString(ds_brnd_hogi_del, j, "BRNDSEQ") == null) paramZsdt0094.setBRNDSEQ(""); else paramZsdt0094.setBRNDSEQ(DatasetUtility.getString(ds_brnd_hogi_del, j, "BRNDSEQ"));
							if(DatasetUtility.getString(ds_brnd_hogi_del, j, "ZPRDGBN") == null) paramZsdt0094.setZPRDGBN(""); else paramZsdt0094.setZPRDGBN(DatasetUtility.getString(ds_brnd_hogi_del, j, "ZPRDGBN"));
							if(DatasetUtility.getString(ds_brnd_hogi_del, j, "BRNDCD") == null) paramZsdt0094.setBRNDCD(""); else paramZsdt0094.setBRNDCD(DatasetUtility.getString(ds_brnd_hogi_del, j, "BRNDCD"));
							// 브랜드 변경에 따른 호기 Zsdt0094 삭제 처리
							dao.deletePosnrZsdt0094(paramZsdt0094);
							dao.insertPosnrZsdt0094(paramZsdt0094);
						}
						ds_brnd_hogi_del.clearAll();
					}
				}
			}
			
			if("insert".equals(DatasetUtility.getString(ds_ZSDT0094, i, "STATUS")) || "update".equals(DatasetUtility.getString(ds_ZSDT0094, i, "STATUS"))) {
			    dao.mergeSpecC(paramZsdt0094);                    // 계약변경 billing change save
			}
		}
		
		// jqpr
		for(int i = 0; i < dsJqpr.getRowCount(); i++) {
			
			if("U".equals(DatasetUtility.getString(dsJqpr, i, "FLAG"))){
				if(DatasetUtility.getString(dsJqpr, i, "HOGI") == null) paramZsdt1108.setHogi("");							else paramZsdt1108.setHogi(DatasetUtility.getString(dsJqpr, i, "HOGI"));
				if(DatasetUtility.getString(dsJqpr, i, "JQPRNUM") == null) paramZsdt1108.setJqprnum("");					else paramZsdt1108.setJqprnum(DatasetUtility.getString(dsJqpr, i, "JQPRNUM"));
				if(DatasetUtility.getString(dsJqpr, i, "JQPRNO") == null) paramZsdt1108.setJqprno("");						else paramZsdt1108.setJqprno(DatasetUtility.getString(dsJqpr, i, "JQPRNO"));
				if(DatasetUtility.getString(dsJqpr, i, "REMARKHOGI") == null) paramZsdt1108.setRemarkhogi("");				else paramZsdt1108.setRemarkhogi(DatasetUtility.getString(dsJqpr, i, "REMARKHOGI"));
				if(DatasetUtility.getString(dsJqpr, i, "REMARKROW") == null) paramZsdt1108.setRemarkrow("");				else paramZsdt1108.setRemarkrow(DatasetUtility.getString(dsJqpr, i, "REMARKROW"));
				if(DatasetUtility.getString(dsJqpr, i, "WAERS") == null) paramZsdt1108.setWaers("");						else paramZsdt1108.setWaers(DatasetUtility.getString(dsJqpr, i, "WAERS"));
				if(DatasetUtility.getString(dsJqpr, i, "IWBTRORG") == null) paramZsdt1108.setIwbtrorg("");					else paramZsdt1108.setIwbtrorg(DatasetUtility.getString(dsJqpr, i, "IWBTRORG"));
				if(DatasetUtility.getString(dsJqpr, i, "SEQ") == null) paramZsdt1108.setSeq("0");					        else paramZsdt1108.setSeq(DatasetUtility.getString(dsJqpr, i, "SEQ"));
				
				dao.mergeZsdt1108(paramZsdt1108);
				dao.updateZqmt007(paramZsdt1108);
				
				/**
				System.out.println("sysId : "+sysId+", mandt : "+mandt);
				System.out.println("paramZsdt1108.getJqprnum() : "+paramZsdt1108.getJqprnum());
				System.out.println("paramZsdt1108.getRemarkrow() : "+paramZsdt1108.getRemarkrow());
				System.out.println("paramZsdt1108.getWaers() : "+paramZsdt1108.getWaers());
				System.out.println("paramZsdt1108.getIwbtrorg() : "+paramZsdt1108.getIwbtrorg());
				*/
				
				/**
				if( (!"".equals(paramZsdt1108.getJqprnum())) && (!"".equals(paramZsdt1108.getRemarkrow())) && (!"".equals(paramZsdt1108.getIwbtrorg()))){
					try {
							SapFunction stub = CallSAP.callSap(sysId, mandt,
								"hdel.sd.sso.domain.ZWEB_QM_UPDATE_JQPR_SDINFO",
								new Object[] {paramZsdt1108.getJqprnum()}, false);
					} catch (RfcException e) {
						logger.info("### RfC Exception ERROR!!!");
						//e.printStackTrace();
					} catch (Exception e) {
						logger.info("### java Exception ERROR!!!");
						//e.printStackTrace();
					}
				}
				*/
			}
		}
		
		// 차수가 1일 경우 0차수가 존재하는지 확인후 없을 경우 0차수 생성
		if("1".equals(seq)) {
			paramZsdt0090.setSEQ("0");
			paramZsdt0091.setSEQ(0);
			paramZsdt0092.setSEQ("0");
			paramZsdt0093.setSEQ("0");
			paramZsdt0094.setSEQ("0");
			
			// 0차수가 존재할 수도 있기에 0차수에 대한 삭제 진행
			dao.deleteZsdt0090(paramZsdt0090);
			dao.deleteZsdt0091(paramZsdt0091);
			dao.deleteZsdt0092(paramZsdt0092);
			dao.deleteZsdt0093(paramZsdt0093);
			dao.deleteZsdt0094(paramZsdt0094);
			
			paramZsdt0090.setSEQ(seq);
			paramZsdt0091.setSEQ(Integer.parseInt(seq));
			paramZsdt0092.setSEQ(seq);
			paramZsdt0093.setSEQ(seq);
			paramZsdt0094.setSEQ(seq);
			
			// 0차수 생성
			dao.insertZero0090(paramZsdt0090);
			dao.insertZero0091(paramZsdt0091);
			dao.insertZero0092(paramZsdt0092);
			dao.insertZero0093(paramZsdt0093);
			dao.insertZero0094(paramZsdt0094);
		}
		
		// 마지막 HEADER의 비고5 UPDATE
		List<Sso0055VO> list = dao.selectChkHeader(paramZsdt0090);                    // 계약변경 header save
		
		String strBigo5 = "";
		int hogiCnt = 0, chkCnt = 0;
		
		for(int i = 0; i < list.size(); i++) {
			if("X".equals(list.get(i).getCHCK())) {
				hogiCnt = Integer.parseInt(list.get(i).getHOGICNT());
				chkCnt = Integer.parseInt(list.get(i).getCHKCNT());
				
				if(hogiCnt == 0) {
					strBigo5 = "전체취소";
					break;
				} else if(chkCnt > 9) {
					strBigo5 = "변경호기 " + list.get(i).getHOGI() + " 외 " + (Integer.parseInt(list.get(i).getCHKCNT()) - 1) + "건";
					break;
				} else {
					if(!strBigo5.equals(""))
						strBigo5 = strBigo5 + ",";

					strBigo5 = strBigo5 + list.get(i).getHOGI();
				}
			}
		}

		if(!strBigo5.equals("")) {
			paramZsdt0090.setBIGO5(strBigo5);
			dao.updateHeader(paramZsdt0090);
		}
	}
	
	private Dataset projectToDataset(Dataset dsList, String pspid, String seq, String mandt, String cmd) {
		ZSDS0063 param = new ZSDS0063();
		int i_seq = Integer.parseInt(seq);
						
		if(mandt == null) mandt = "";
		
		param.setMANDT(mandt);   // SAP CLIENT
		param.setPSPID(pspid);
		param.setSEQ(i_seq);
		
		List<ZSDS0063> list = null;
		
		// cmd값이 S면 기존 data 조회, I면 신규 data 조회
		if("S".equals(cmd)) list = dao.SelectProject(param); // 서비스CALL
		else if("I".equals(cmd)) list = dao.SelectProjectInput(param); // 서비스CALL
		
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"     , list.get(i).getMANDT());
			if("0".equals(list.get(i).getVBELN().substring(0, 1))) dsList.setColumn(i, "VBELN", String.valueOf(Integer.parseInt(list.get(i).getVBELN())));
			else dsList.setColumn(i, "VBELN"     , list.get(i).getVBELN());
			dsList.setColumn(i, "PSPID"     , pspid);   
			dsList.setColumn(i, "SEQ"       , i_seq);     
			dsList.setColumn(i, "AUART"     , list.get(i).getAUART());   
			dsList.setColumn(i, "SPART"     , list.get(i).getSPART());   
			dsList.setColumn(i, "VKBUR"     , list.get(i).getVKBUR());   
			dsList.setColumn(i, "VKBURT"    , list.get(i).getVKBURT());  
			dsList.setColumn(i, "ZKUNNR"    , list.get(i).getZKUNNR());  
			dsList.setColumn(i, "ZKUNNRT"   , list.get(i).getZKUNNRT()); 
			dsList.setColumn(i, "KUNRG"     , String.valueOf(Integer.parseInt(list.get(i).getKUNRG())));   
			dsList.setColumn(i, "KUNRG_NM"  , list.get(i).getKUNRG_NM());
			dsList.setColumn(i, "KUNWE"     , String.valueOf(Integer.parseInt(list.get(i).getKUNWE())));   
			dsList.setColumn(i, "KUNWE_NM"  , list.get(i).getKUNWE_NM());
			dsList.setColumn(i, "KUNZ1"     , list.get(i).getKUNZ1());   
			dsList.setColumn(i, "KUNZ2"     , list.get(i).getKUNZ2());   
			dsList.setColumn(i, "KUNZ3"     , list.get(i).getKUNZ3());   
			dsList.setColumn(i, "TELF1"     , list.get(i).getTELF1());   
			dsList.setColumn(i, "BSTNK"     , list.get(i).getBSTNK());   
			dsList.setColumn(i, "BRAN1"     , list.get(i).getBRAN1());   
			dsList.setColumn(i, "BSTDK"     , list.get(i).getBSTDK());   
			dsList.setColumn(i, "ZQNTY"     , list.get(i).getZQNTY());   
			dsList.setColumn(i, "NETWR"     , String.valueOf(list.get(i).getNETWR()));   
			dsList.setColumn(i, "WAVWR"     , String.valueOf(list.get(i).getWAVWR()));   
			dsList.setColumn(i, "COMMI"     , String.valueOf(list.get(i).getCOMMI()));   
			dsList.setColumn(i, "VDATU"     , list.get(i).getVDATU());   
			dsList.setColumn(i, "WAERK"     , list.get(i).getWAERK());   
			dsList.setColumn(i, "ZTERM"     , list.get(i).getZTERM());   
			dsList.setColumn(i, "INCO1"     , list.get(i).getINCO1());   
			dsList.setColumn(i, "INCO2"     , list.get(i).getINCO2());   
			dsList.setColumn(i, "WWBLD"     , list.get(i).getWWBLD());   
			dsList.setColumn(i, "REPMO"     , list.get(i).getREPMO());   
			dsList.setColumn(i, "GUAMO"     , list.get(i).getGUAMO());   
			dsList.setColumn(i, "ZAPDAT"    , list.get(i).getZAPDAT());   
			dsList.setColumn(i, "STADA"     , list.get(i).getSTADA());   
			dsList.setColumn(i, "WORMO"     , list.get(i).getWORMO());   
			dsList.setColumn(i, "REGION"    , list.get(i).getREGION());  
			dsList.setColumn(i, "HIGH_G"    , list.get(i).getHIGH_G());  
			dsList.setColumn(i, "ADMITNO"   , list.get(i).getADMITNO()); 
			dsList.setColumn(i, "CHEOR"     , list.get(i).getCHEOR());   
			dsList.setColumn(i, "COMPL_DA"  , list.get(i).getCOMPL_DA());
			dsList.setColumn(i, "FINAL_DA"  , list.get(i).getFINAL_DA());
			dsList.setColumn(i, "ENFOR"     , list.get(i).getENFOR());   
			dsList.setColumn(i, "TURNKEY"   , list.get(i).getTURNKEY()); 
			dsList.setColumn(i, "KISCON"    , list.get(i).getKISCON());  
			dsList.setColumn(i, "HEL_REG"   , list.get(i).getHEL_REG()); 
			dsList.setColumn(i, "LIFNR"     , list.get(i).getLIFNR());   
			dsList.setColumn(i, "LIFNR2"    , list.get(i).getLIFNR2());  
			dsList.setColumn(i, "LIFNR3"    , list.get(i).getLIFNR3());  
			dsList.setColumn(i, "LIFNRCHK"  , list.get(i).getLIFNRCHK());
			dsList.setColumn(i, "REG_NO"    , list.get(i).getREG_NO());  
			dsList.setColumn(i, "REG_PE"    , list.get(i).getREG_PE());  
			dsList.setColumn(i, "EMAIL"     , list.get(i).getEMAIL());   
			dsList.setColumn(i, "SOCNO"     , list.get(i).getSOCNO());   
			dsList.setColumn(i, "CORNO"     , list.get(i).getCORNO());   
			dsList.setColumn(i, "COMNO"     , list.get(i).getCOMNO());   
			dsList.setColumn(i, "SEP_MON"   , list.get(i).getSEP_MON()); 
			dsList.setColumn(i, "CDATE"     , list.get(i).getCDATE());   
			dsList.setColumn(i, "WWBLDT"    , list.get(i).getWWBLDT());  
			dsList.setColumn(i, "CONTR_DA"  , list.get(i).getCONTR_DA());			
			
		}

		List<ZSDS0063> list2 = dao.SelectQtdat(param); // 견적일 및 견적 승인일 

		
		for (int i=0;i<list.size();i++) {	// 견적일, 견적승인일 입력		20150416 김선호
			if(list2.size() > 0) {
				dsList.setColumn(i, "ZAPDAT"    , list2.get(0).getZAPDAT());   
				dsList.setColumn(i, "STADA"     , list2.get(0).getSTADA());   				
			}
		}
		return dsList;
	}

	private Dataset headerToDataset(Dataset dsList, String pspid, String seq, String mandt, String cmd) {
		ZSDT0090 param = new ZSDT0090();
		
		if(mandt == null) mandt = "";
		
		param.setMANDT(mandt);   // SAP CLIENT
		param.setPSPID(pspid);
		param.setSEQ(seq);
		
		List<ZSDT0090> list = null;
		
		if("S".equals(cmd)) list = dao.SelectHeader(param); // 서비스CALL
		else if("I".equals(cmd)) list = dao.SelectHeaderInput(param); // 서비스CALL
		
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"     , list.get(i).getMANDT());
			dsList.setColumn(i, "VBELN"     , list.get(i).getVBELN());
			dsList.setColumn(i, "PSPID"     , list.get(i).getPSPID());   
			dsList.setColumn(i, "SEQ"       , list.get(i).getSEQ());     
			dsList.setColumn(i, "ZQNTY"     , list.get(i).getZQNTY());   
			dsList.setColumn(i, "NETWR"     , String.valueOf(list.get(i).getNETWR()));   
			dsList.setColumn(i, "WAVWR"     , String.valueOf(list.get(i).getWAVWR()));   
			dsList.setColumn(i, "COMMI"     , String.valueOf(list.get(i).getCOMMI()));   
			dsList.setColumn(i, "VDATU"     , list.get(i).getVDATU());   
			dsList.setColumn(i, "WAERK"     , list.get(i).getWAERK());
			dsList.setColumn(i, "CHNETWR" , String.valueOf(list.get(i).getCHNETWR()));
			dsList.setColumn(i, "CHWAVWR" , String.valueOf(list.get(i).getCHWAVWR()));
			dsList.setColumn(i, "CHCOMMI" , String.valueOf(list.get(i).getCHCOMMI()));
			dsList.setColumn(i, "CHVDATU" , list.get(i).getCHVDATU());
			dsList.setColumn(i, "CHWAERK" , list.get(i).getCHWAERK());
			dsList.setColumn(i, "FINL"    , list.get(i).getFINL());   
			dsList.setColumn(i, "BIGO1"   , list.get(i).getBIGO1());  
			dsList.setColumn(i, "BIGO2"   , list.get(i).getBIGO2());  
			dsList.setColumn(i, "BIGO3"   , list.get(i).getBIGO3());  
			dsList.setColumn(i, "BIGO4"   , list.get(i).getBIGO4());  
			dsList.setColumn(i, "BIGO5"   , list.get(i).getBIGO5());  
			dsList.setColumn(i, "CHRES"   , list.get(i).getCHRES());  
			dsList.setColumn(i, "REDEP"   , list.get(i).getREDEP());  
			dsList.setColumn(i, "CHRES_A" , list.get(i).getCHRES_A());
			dsList.setColumn(i, "REDEP_A" , list.get(i).getREDEP_A());
			dsList.setColumn(i, "CHRES_B" , list.get(i).getCHRES_B());
			dsList.setColumn(i, "REDEP_B" , list.get(i).getREDEP_B());
			dsList.setColumn(i, "CHRES_C" , list.get(i).getCHRES_C());
			dsList.setColumn(i, "REDEP_C" , list.get(i).getREDEP_C());
			dsList.setColumn(i, "CHRES_D" , list.get(i).getCHRES_D());
			dsList.setColumn(i, "REDEP_D" , list.get(i).getREDEP_D());
			dsList.setColumn(i, "CHRES_E" , list.get(i).getCHRES_E());
			dsList.setColumn(i, "REDEP_E" , list.get(i).getREDEP_E());
			dsList.setColumn(i, "ERDAT"   , list.get(i).getERDAT());
			dsList.setColumn(i, "ERZET"   , list.get(i).getERZET());
			dsList.setColumn(i, "ERNAM"   , list.get(i).getERNAM());
			dsList.setColumn(i, "AEDAT"   , list.get(i).getAEDAT());
			dsList.setColumn(i, "AEZET"   , list.get(i).getAEZET());
			dsList.setColumn(i, "AENAM"   , list.get(i).getAENAM());			
			dsList.setColumn(i, "SHCHK"   , list.get(i).getSHCHK());
			dsList.setColumn(i, "CHSHCHK" , list.get(i).getCHSHCHK());
			dsList.setColumn(i, "UCH_DUTY", list.get(i).getUCH_DUTY());
		}

		return dsList;
	}

	private Dataset itemToDataset(Dataset dsList, String pspid, String seq, String mandt, String cmd) {

		ZSDT0091 param = new ZSDT0091();
		int i_seq = Integer.parseInt(seq);
		
		if(mandt == null) mandt = "";
		
		param.setMANDT(mandt);   // SAP CLIENT
		param.setPSPID(pspid);
		param.setSEQ(i_seq);
		
		List<ZSDT0091> list = null;
		
		if("S".equals(cmd)) list = dao.SelectItem(param); // 서비스CALL
		else if("I".equals(cmd)) list = dao.SelectItemInput(param); // 서비스CALL
		
		
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사  
			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"     , list.get(i).getMANDT());
			dsList.setColumn(i, "VBELN"     , list.get(i).getVBELN());
			dsList.setColumn(i, "PSPID"     , list.get(i).getPSPID());   
			dsList.setColumn(i, "SEQ"       , list.get(i).getSEQ());     
			dsList.setColumn(i, "POSNR"     , list.get(i).getPOSNR());   
			dsList.setColumn(i, "HOGI"      , list.get(i).getHOGI());   
			dsList.setColumn(i, "AUART"     , list.get(i).getAUART());   
			dsList.setColumn(i, "KZWI4"     , String.valueOf(list.get(i).getKZWI4()));			
			dsList.setColumn(i, "NETWR"     , String.valueOf(list.get(i).getNETWR()));   
			dsList.setColumn(i, "WAVWR"     , String.valueOf(list.get(i).getWAVWR()));     
			dsList.setColumn(i, "EDATU"     , list.get(i).getEDATU());  
			dsList.setColumn(i, "REPMO"     , list.get(i).getREPMO());  
			dsList.setColumn(i, "GUAMO"     , list.get(i).getGUAMO());  
			dsList.setColumn(i, "WORMO"     , list.get(i).getWORMO());
			dsList.setColumn(i, "CHNETWR"   , String.valueOf(list.get(i).getCHNETWR()));
			dsList.setColumn(i, "CHWAVWR"   , String.valueOf(list.get(i).getCHWAVWR()));
			dsList.setColumn(i, "CHEDATU"   , list.get(i).getCHEDATU());  
			dsList.setColumn(i, "CHREPMO"   , list.get(i).getCHREPMO());  
			dsList.setColumn(i, "CHGUAMO"   , list.get(i).getCHGUAMO());  
			dsList.setColumn(i, "CHWORMO"   , list.get(i).getCHWORMO());			
			dsList.setColumn(i, "WAERK"     , list.get(i).getWAERK());			
			dsList.setColumn(i, "CHCK"      , list.get(i).getCHCK());			
			dsList.setColumn(i, "HOGI_CANC" , list.get(i).getHOGI_CANC());			
			dsList.setColumn(i, "SPEC_STAT" , list.get(i).getSPEC_STAT());			
			dsList.setColumn(i, "MATNR"     , list.get(i).getMATNR());			
			dsList.setColumn(i, "CLASS"     , list.get(i).getCLASS());			
			dsList.setColumn(i, "ARKTX"     , list.get(i).getARKTX());			
			dsList.setColumn(i, "ERDAT"   	, list.get(i).getERDAT());
			dsList.setColumn(i, "ERNAM"   	, list.get(i).getERNAM());
			dsList.setColumn(i, "ABGRU"		, list.get(i).getABGRU());
			dsList.setColumn(i, "INCRTF_TP"	, list.get(i).getINCRTF_TP());
			
			// 2020 브랜드
			dsList.setColumn(i, "BRNDCD"	, list.get(i).getBRNDCD());
			dsList.setColumn(i, "BRNDSEQ"	, list.get(i).getBRNDSEQ());
			dsList.setColumn(i, "SNDSYS"	, list.get(i).getSNDSYS());
			
		}

		return dsList;
	}

	private Dataset billoToDataset(Dataset dsList, String pspid, String seq, String mandt, String cmd) {

		ZSDT0092 param = new ZSDT0092();
		
		if(mandt == null) mandt = "";
		
		param.setMANDT(mandt);   // SAP CLIENT
		param.setPSPID(pspid);
		param.setSEQ(seq);
		
		List<ZSDT0092> list = null;
		
		if("S".equals(cmd)) list = dao.SelectBillO(param); // 서비스CALL
		else if("I".equals(cmd)) list = dao.SelectBillOInput(param); // 서비스CALL
		
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사  
			
			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"     , list.get(i).getMANDT());
			dsList.setColumn(i, "VBELN"     , list.get(i).getVBELN());
			dsList.setColumn(i, "PSPID"     , list.get(i).getPSPID());   
			dsList.setColumn(i, "SEQ"       , list.get(i).getSEQ());     
			dsList.setColumn(i, "POSNR"     , list.get(i).getPOSNR());   
			dsList.setColumn(i, "HOGI"      , list.get(i).getHOGI());			
			dsList.setColumn(i, "FKDAT"     , list.get(i).getFKDAT());   
			dsList.setColumn(i, "MLBEZ"     , list.get(i).getMLBEZ());   
			dsList.setColumn(i, "ZTERM"     , list.get(i).getZTERM());   
			dsList.setColumn(i, "FKSAF"     , list.get(i).getFKSAF());   
			dsList.setColumn(i, "FPLTR"     , list.get(i).getFPLTR());   
			dsList.setColumn(i, "MSTXT"     , list.get(i).getMSTXT());   
			dsList.setColumn(i, "FAKWR"     , String.valueOf(list.get(i).getFAKWR()));
			dsList.setColumn(i, "WAERK"     , list.get(i).getWAERK());			
			dsList.setColumn(i, "ERDAT"   , list.get(i).getERDAT());
			dsList.setColumn(i, "ERNAM"   , list.get(i).getERNAM());
		}

		return dsList;
	}

	private Dataset billcToDataset(Dataset dsList, String pspid, String seq, String mandt, String cmd) {

		ZSDT0093 param = new ZSDT0093();
		
		if(mandt == null) mandt = "";
		
		param.setMANDT(mandt);   // SAP CLIENT
		param.setPSPID(pspid);
		param.setSEQ(seq);
		
		List<ZSDT0093> list = null;
		
		if("S".equals(cmd)) list = dao.SelectBillC(param); // 서비스CALL
		else if("I".equals(cmd)) list = dao.SelectBillCInput(param); // 서비스CALL
		
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사  
			
			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"     , list.get(i).getMANDT());
			dsList.setColumn(i, "VBELN"     , list.get(i).getVBELN());
			dsList.setColumn(i, "PSPID"     , list.get(i).getPSPID());   
			dsList.setColumn(i, "SEQ"       , list.get(i).getSEQ());     
			dsList.setColumn(i, "POSNR"     , list.get(i).getPOSNR());   
			dsList.setColumn(i, "HOGI"      , list.get(i).getHOGI());			
			dsList.setColumn(i, "CHFKDAT"     , list.get(i).getCHFKDAT());   
			dsList.setColumn(i, "CHMLBEZ"     , list.get(i).getCHMLBEZ());   
			dsList.setColumn(i, "CHZTERM"     , list.get(i).getCHZTERM());   
			dsList.setColumn(i, "CHFKSAF"     , list.get(i).getCHFKSAF());   
			dsList.setColumn(i, "FPLTR"     , list.get(i).getFPLTR());   
			dsList.setColumn(i, "CHMSTXT"     , list.get(i).getCHMSTXT());   
			dsList.setColumn(i, "CHFAKWR"     , String.valueOf(list.get(i).getCHFAKWR()));
			dsList.setColumn(i, "WAERK"     , list.get(i).getWAERK());			
			dsList.setColumn(i, "ERDAT"   , list.get(i).getERDAT());
			dsList.setColumn(i, "ERNAM"   , list.get(i).getERNAM());
		}

		return dsList;
	}

	private Dataset speccToDataset(Dataset dsList, String pspid, String seq, String mandt, String cmd) {

		ZSDT0094 param = new ZSDT0094();
		
		if(mandt == null) mandt = "";
		
		param.setMANDT(mandt);   // SAP CLIENT
		param.setPSPID(pspid);
		param.setSEQ(seq);
		
		List<ZSDT0094> list = null;
		
		if("S".equals(cmd)) list = dao.SelectSpecC(param); // 서비스CALL
		else if("I".equals(cmd)) list = dao.SelectSpecCInput(param); // 서비스CALL
		
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사  
			
			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"     , list.get(i).getMANDT());
			dsList.setColumn(i, "PSPID"     , list.get(i).getPSPID());   
			dsList.setColumn(i, "SEQ"       , list.get(i).getSEQ());     
			dsList.setColumn(i, "POSNR"     , list.get(i).getPOSNR());   
			dsList.setColumn(i, "HOGI"      , list.get(i).getHOGI());    
			dsList.setColumn(i, "MATNR"     , list.get(i).getMATNR());   
			dsList.setColumn(i, "NAM_CHAR"  , list.get(i).getNAM_CHAR());   
			dsList.setColumn(i, "ATBEZ"     , list.get(i).getATBEZ());   
			dsList.setColumn(i, "CHAR_VALUE", list.get(i).getCHAR_VALUE());   
			dsList.setColumn(i, "CHAR_VALUEMD", list.get(i).getCHAR_VALUEMD());   
			dsList.setColumn(i, "ATWTB"     , list.get(i).getATWTB());   
			dsList.setColumn(i, "ATKLA"     , list.get(i).getATKLA());   
			dsList.setColumn(i, "CNT"       , list.get(i).getCNT());   
			dsList.setColumn(i, "CNGBN"     , list.get(i).getCNGBN());			
			dsList.setColumn(i, "ERDAT"   , list.get(i).getERDAT());
			dsList.setColumn(i, "ERNAM"   , list.get(i).getERNAM());
			
			//2020 브랜드
			dsList.setColumn(i, "PCNCD"   , list.get(i).getPCNCD());
			dsList.setColumn(i, "DISPTP"   , list.get(i).getDISPTP());
			dsList.setColumn(i, "MODITP"   , list.get(i).getMODITP());
			dsList.setColumn(i, "ZPRDGBN"   , list.get(i).getZPRDGBN());
			dsList.setColumn(i, "BRNDCD"   , list.get(i).getBRNDCD());
			dsList.setColumn(i, "BRNDSEQ"   , list.get(i).getBRNDSEQ());
			dsList.setColumn(i, "SNDSYS"   , list.get(i).getSNDSYS());
		}
		return dsList;
	}

	private String selectMaxHogi(String mandt, String pspid, String seq, String matnr, String flag) {
		Sso0055VO param = new Sso0055VO();
		
		String hogi = null;
		
		param.setMANDT(mandt);   // SAP CLIENT
		param.setPSPID(pspid);
		param.setSEQ(seq);
		param.setMATNR(matnr);
		param.setCHCK(flag);
		
		// "SP EL 예비품과 ES 예비품은 호기번호를 별도로 채번해준다.   -- 로직 추가 2014.03.13 KSK
		if("Y-200".equals(matnr)) {
			hogi = pspid + "Y88"; // EL예비품
		} else if("Y-300".equals(matnr)) {
			hogi = pspid + "Y99"; // ES예비품
		} else if("Y-400".equals(matnr)) {
			hogi = pspid + "Y77"; // 설치관련
		} else if("Y-500".equals(matnr)) {
			hogi = pspid + "Y90"; // 매입세
		} else {
			List<Sso0055VO> list = dao.SelectMaxHogi(param); // 서비스CALL
			hogi = list.get(0).getHOGI();
		}
		
		return hogi; 
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String selectHogi(String mandt, String vbeln, String pspid, String seq, String posnr) {
		return dao.SelectHogi(mandt, vbeln, pspid, seq, posnr); 
	}	
	
	// 최종 변경차수 조회	
	public List<Sso0055VO> selectMaxSeq(Sso0055ParamVO param) {
		
		return dao.SelectMaxSeq(param);
			
	}
	// 프로젝트 데이터 조회
	public List<ZSDS0063> searchProject(ZSDS0063 param) {
		
		return dao.SelectProject(param);
			
	}

	// 프로젝트 데이터 조회
	public List<ZSDS0063> searchQtdat(ZSDS0063 param) {
		
		return dao.SelectQtdat(param);
			
	}
	
	// 계약변경 header 데이터 조회
	public List<ZSDT0090> searchHeader(ZSDT0090 param) {
		
		return dao.SelectHeader(param);
			
	}
	// 계약변경item 데이터 조회
	public List<ZSDT0091> searchItem(ZSDT0091 param) {
		
		return dao.SelectItem(param);
			
	}
	// 계약변경 billing 현행 데이터 조회
	public List<ZSDT0092> searchBillO(ZSDT0092 param) {
		
		return dao.SelectBillO(param);
			
	}
	// 계약변경 billing 변경 데이터 조회
	public List<ZSDT0093> searchBillC(ZSDT0093 param) {
		
		return dao.SelectBillC(param);
			
	}
	// 계약변경  변경사양 데이터 조회
	public List<ZSDT0094> searchSpecC(ZSDT0094 param) {
		
		return dao.SelectSpecC(param);
			
	}
	
	// 조회
	public List<Sso0055VO> find(Sso0055ParamVO paramV) {
		return dao.SelectSayang(paramV);
		
	}

	// 조회
	public List<Sso0055VO> findVbeln(Sso0055ParamVO paramV) {
		return dao.SelectVbeln(paramV);
		
	}
	// 조회
	public List<Sso0055VO> findVbeln2(Sso0055ParamVO paramV) {
		
		return dao.SelectVbeln2(paramV);
		
	}
	
	// 조회
	public List<Sso0055VO> findSayangForPrint(Sso0055ParamVO paramV) {
		return dao.SelectSayangForPrint(paramV);
	}
	
	public List<Sso0055VO> selectRecad_da(Sso0055ParamVO paramV) {
		return dao.getRecad_da(paramV);
	}
	
	public Dataset deleteZsdt1054(MipParameterVO paramVO, Model model, SqlSession session) {

		Dataset dsZsdt1054 = paramVO.getDataset("ds_output_char");

		try {
			createSes0031Dao(session);  // DAO생성
			
			String mandt = paramVO.getVariable("G_MANDT");
			String pspid = DatasetUtility.getString(dsZsdt1054, 0, "PSPID");
			String posid = DatasetUtility.getString(dsZsdt1054, 0, "POSID");

			if (pspid == null) pspid = "";
			if (posid == null) posid = "0";
			//System.out.print("\n@@@ MANDT ===>"+ mandt +"\n");
			//System.out.print("\n@@@ PSPID ===>"+ pspid +"\n");
			//System.out.print("\n@@@ POSID   ===>"+ posid   +"\n");

			Ses0031ParamVO param = new Ses0031ParamVO();

			param.setMandt(mandt );	 // SAP CLIENT
			param.setPspid(pspid);
			param.setPosid(posid);

			//deleteZsdt1054H(param); // 2013.03.20 제거
			//deleteZsdt1054D(param); // 2013.03.20 제거

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsZsdt1054;
	}
	
	public void setCostState(MipParameterVO paramVO) {
		Dataset dsZsdt0090 = paramVO.getDataset("ds_cond");
		
		try {
			
			String mandt = paramVO.getVariable("G_MANDT");
			String pspid = DatasetUtility.getString(dsZsdt0090, 0, "PSPID");
			String seq   = DatasetUtility.getString(dsZsdt0090, 0, "SEQ"  );
			String finl  = DatasetUtility.getString(dsZsdt0090, 0, "FINL" );
			
			if (mandt == null) mandt = "";
			if (pspid == null) pspid = "";
			if (seq   == null) seq   = "0";
			if (finl  == null) finl  = "";
			
			//System.out.print("\n@@@ MANDT ===>"+ mandt +"\n");
			//System.out.print("\n@@@ PSPID ===>"+ pspid +"\n");
			//System.out.print("\n@@@ POSID ===>"+ posid +"\n");
			//System.out.print("\n@@@ FINL  ===>"+ finl  +"\n");
			
			Sso0055ParamVO param = new Sso0055ParamVO();
			
			param.setMANDT(mandt );	 // SAP CLIENT
			param.setPSPID(pspid);
			param.setSEQ(seq);
			param.setFINL(finl);
			
			dao.setCostState(param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int deleteZsdt1054H(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt1054H(param);
	}

	public int deleteZsdt1054D(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt1054D(param);
	}
	
	public List<Sso0055VO> searchZcobt304(Sso0055ParamVO param) {
		List<Sso0055VO> list = dao.searchZcobt304D(param); //zcobt304D 체크후 리턴 
		
		if ( list.size() > 0 ) {
			//return dao.searchZcobt304D(param);
			return list;
		} else {
			return dao.searchZcobt304DPrev(param);
		}
		
	}

	public List<Sso0055VO> searchBlockName(Sso0055ParamVO param) {
		List<Sso0055VO> list2 = dao.searchZcobt204D(param); //zcobt204D 체크후 리턴
		
		if ( list2.size() > 0 ) {
			return dao.selectListBlockNameD(param);
		} else {
			return dao.selectListBlockName(param);
		}
	}
	
	/**
	 * 매매기준환율 조회 2013.02.20
	 * @param param
	 * @return
	 */
	public List<Sso0055VO> searchExchange(Sso0055ParamVO param) {
		return dao.selectListExchange(param);
	}
	public List<Sso0055VO> checkZcobt204D(Sso0055ParamVO param) {
		return dao.searchZcobt204D(param);
	}
	public List<Sso0055VO> searchBlockNameD(Sso0055ParamVO param) {
		return dao.selectListBlockNameD(param);
	}
	
	/* //-----< 2021.05.26  프로젝트번호 유효성 Check 수정 by mj.Shin Start	
	public List<Com0180> validationCheck_Project(String sMandt, String sPspid) {
		
		Com0180ParamVO param0180 = new Com0180ParamVO();
		param0180.setMandt(sMandt);
		param0180.setZzpjt_id(sPspid);
		param0180.setErdat_fr("00000000");
		param0180.setErdat_to("99999999");
		
		return Com0180Dao.selectListBuyr(param0180);
	}
	*/	//-----> 2021.05.26  프로젝트번호 유효성 Check 수정 by mj.Shin Start
	
	public String getPartnerZ3(String mandt, String posid) {
		return dao.getPartnerZ3(mandt, posid);
	}
	
	private Dataset dateCommiToDataset(Dataset dsList, String mandt, String pspid, String vbeln) {
	
		Sso0055ParamVO param = new Sso0055ParamVO();
		
		if (mandt == null) mandt = "";
		if (pspid == null) pspid = "";
		if (vbeln == null) vbeln = "";
		
		param.setMANDT(mandt);
		param.setPSPID(pspid);
		param.setVBELN(vbeln);
				
		List<Sso0055VO> list = dao.SelectDateCommi(param); // 서비스CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
			dsList.appendRow(); 			// 행추가
			dsList.setColumn(i, "DATE_TP"    , list.get(i).getDATE_TP());
			dsList.setColumn(i, "BSDATE"     , list.get(i).getBSDATE());   
		}

		return dsList;
		
	}
	
	private String selectKunnr(String mandt, String vbeln) {
		return dao.SelectKunnr(mandt, vbeln); 
	}
	
	private int selectZsdt0214(String kunnr) {
		return dao.SelectZsdt0214(kunnr);
	}
	
	//=========================================================================================
	//  함수기능 	: 단가 합계 전 Duty 체크 실행
	//  HISTORY : 2020.08.31 최초 코딩 박수근
	//=========================================================================================
	public MipResultVO singleGenCostDuty(MipParameterVO paramVO, SqlSession session, Model model) throws RfcException, Exception {
		MipResultVO resultVO = new MipResultVO();
		Dataset ds_output_char = paramVO.getDataset("ds_output_char");		// 단가 처리용 데이타셋
		Dataset ds_order = paramVO.getDataset("ds_order");

		Dataset ds_log = new Dataset("ds_log"); 
		ds_log.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);  
		ds_log.addColumn("LOGMSG", ColumnInfo.COLTYPE_STRING, 256);
		// 2020 브랜드
		ds_log.addColumn("PRCSYS", ColumnInfo.COLTYPE_STRING, 256);
		ds_log.addColumn("HOGI", ColumnInfo.COLTYPE_STRING, 256);
		ds_log.addColumn("ATKLA", ColumnInfo.COLTYPE_STRING, 256);
		ds_log.addColumn("PRHNAME", ColumnInfo.COLTYPE_STRING, 256);

		dutyNService.createDao(session);
		String item = "";

		try {
			String mandt = paramVO.getVariable("G_MANDT");				// MANDT
			String lang  = paramVO.getVariable("G_LANG");				// LANG
			String flag  = "P"; 										// Q-견적, P-계약
			item = ds_output_char.getColumnAsString(0, "HOGI"); 	    // 호기 번호
			DutyObj dutyObj = new DutyObj();
			dutyNService.genSpecCheck(mandt, flag, item, ds_output_char, ds_log, lang, dutyObj, ds_order);
			if ( ds_log.getRowCount() > 0 ) {   // 종속성 체크후 log 있으면 return
				resultVO.addDataset(ds_log); 	// log 내역 
				return resultVO;
			}
		} catch(Exception e) {
			e.printStackTrace();
			Dataset dsError = new Dataset("ds_error");
			dsError.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);
			dsError.addColumn("ERRMSG", ColumnInfo.COLTYPE_STRING, 256);
			dsError.appendRow();
			dsError.setColumn(0, "IDX", "9999");
			dsError.setColumn(0, "ERRMSG", "[필수 입력 에러] 호기번호:" + item + ", " + e.getMessage());
			resultVO.addDataset(dsError); 	// 오류결과내역
			return resultVO;
		}
		return resultVO;
	}

	//=========================================================================================
	//  함수기능 	: Duty 체크 분리 단가 합계
	//  HISTORY : 2020.08.31 최초 코딩 박수근
	//=========================================================================================
	public MipResultVO singleCost(MipParameterVO paramVO, SqlSession session, Model model) {
		Rfc rfc = null;
		MipResultVO resultVO = new MipResultVO();
		Dataset ds_output_char = paramVO.getDataset("ds_output_char");		// 단가 처리용 데이타셋
		
		try{
			// CW00010=데이터가 없습니다.
			if ( ds_output_char.getRowCount() < 1 ) {
				throw new BizException("CW00010,''");
			}
			
			// OUTPUT DATASET DECLARE
			Dataset ds_300    = null; // UI로 return할 out dataset
			Dataset ds_202    = null; // UI로 return할 out dataset
			Dataset ds_302    = null; // UI로 return할 out dataset
			Dataset ds_204    = null; // UI로 return할 out dataset
			Dataset ds_304    = null; // UI로 return할 out dataset
			Dataset ds_check = null;
			
			try {
			    rfc = sso0055NJS.callZwebCo4FindCost(paramVO.getVariable("G_SYSID"), "4", "3", paramVO.getVariable("i_gjahr"), "", paramVO.getVariable("i_seq"), 
					         paramVO.getVariable("i_zdate"), paramVO.getVariable("i_chk"), ds_output_char, ds_check, null, null, null, null, null, null, null, null, null);
				ds_300 = rfc.getDatasetFromJcoTable("T_T300");
				ds_202 = rfc.getDatasetFromJcoTable("T_T202");
				ds_302 = rfc.getDatasetFromJcoTable("T_T302");
				ds_204 = rfc.getDatasetFromJcoTable("T_T204");
				ds_304 = rfc.getDatasetFromJcoTable("T_T304");
				ds_check = rfc.getDatasetFromJcoTable("T_CHEK");
			} catch (Exception e) {
				e.printStackTrace();
				Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
				dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CI30001", "원가산출 기능이 정상 작동하지 않습니다.  CE팀 확인 바랍니다.", "Y", "Y");
				resultVO.addDataset(dsError); 	// 오류결과내역
				return resultVO;
			}
			
			ds_202.setId("ds_t202");
			ds_204.setId("ds_t204");
			ds_300.setId("ds_t300");
			ds_302.setId("ds_t302");
			ds_304.setId("ds_t304");
			ds_check.setId("ds_check");
			
			resultVO.addDataset(ds_300);
			resultVO.addDataset(ds_204);
			resultVO.addDataset(ds_304);
			resultVO.addDataset(ds_check);

			// 2013.03.13 부분원가 처리 : STATE가 모두 X 가 아닐 때만 부분원가 보여 줌 -> 1054 초기화
			int nStateNotXCount = 0;
			String strState = "";
			for ( int i = 0 ; i < ds_check.getRowCount() ; i++ ) {
				strState = ds_check.getColumnAsString(i, "STATE");
				if ( strState == null ) {
					strState = "";
				}
				if ( !strState.equals("X") ) {
					nStateNotXCount++;
				}
			}
			if ( nStateNotXCount > 0 ) {
				this.deleteZsdt1054(paramVO, model, session);
			}
		} catch (BizException e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");

			if ( message.length <= 1) {
				SmpComC.moveDs2Msg(message[0], message[0], model);
			} else {
				SmpComC.moveDs2Msg(message[0], message[1], model);
			}
			
			Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", "CE00001", "Y", "Y");
			
			resultVO.addDataset(dsError); 	// 오류결과내역
			//model.addAttribute("resultVO", resultVO);
			return resultVO;
		} catch (Exception e) {
			// java Exc
			logger.info("@@@@@@@ java Exception ERROR!!!");
			e.printStackTrace();
			
			Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", "CE00001", "Y", "Y");
			
			resultVO.addDataset(dsError); 	// 오류결과내역
			//model.addAttribute("resultVO", resultVO);
			return resultVO;
		}
		
		return resultVO;
	}
	
	public Dataset getJqpr(Dataset dsList, String pspid, String mandt) {
		ZSDT1108 param = new ZSDT1108();
		
		if(mandt == null) mandt = "";
		
		param.setMandt(mandt);   // SAP CLIENT
		param.setVbeln(pspid);
		
		List<ZSDT1108> list = dao.selectJqpr(param); // service call
		
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사  
			
			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT", list.get(i).getMandt());
			dsList.setColumn(i, "VBELN", list.get(i).getVbeln());
			dsList.setColumn(i, "HOGI", list.get(i).getHogi());
			dsList.setColumn(i, "JQPRNUM", list.get(i).getJqprnum());
			dsList.setColumn(i, "JQPRNO", list.get(i).getJqprno());
			dsList.setColumn(i, "REMARKHOGI", list.get(i).getRemarkhogi());
			dsList.setColumn(i, "REMARKROW", list.get(i).getRemarkrow());
			dsList.setColumn(i, "ERDAT", list.get(i).getErdat());
			dsList.setColumn(i, "ERZET", list.get(i).getErzet());
			dsList.setColumn(i, "ERNAM", list.get(i).getErnam());
			dsList.setColumn(i, "AEDAT", list.get(i).getAedat());
			dsList.setColumn(i, "AEZET", list.get(i).getAezet());
			dsList.setColumn(i, "AENAM", list.get(i).getAenam());
			dsList.setColumn(i, "WAERS", "");
			dsList.setColumn(i, "IWBTRORG", "");
			dsList.setColumn(i, "FLAG", "");
			dsList.setColumn(i, "SEQ", list.get(i).getSeq());
		}

		return dsList;
	}
}
