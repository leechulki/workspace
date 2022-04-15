package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0020D;
import hdel.sd.ses.domain.Ses0020;
import hdel.sd.ses.domain.Ses0020ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0020S extends SrmService {

	private Ses0020D Ses0020Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0020Dao = sqlSession.getMapper(Ses0020D.class);
	}

	public List<Ses0020> searchMinTemplate(Ses0020ParamVO param) {
		return Ses0020Dao.selectMinTemplate(param);
	}
	
	public List<Ses0020> findTemp(Ses0020ParamVO param) {
		return Ses0020Dao.selectListTemp(param);
	} 
	
	public List<Ses0020> find(Ses0020ParamVO param) {
		return Ses0020Dao.selectList(param);
	}  
	
	public List<Ses0020> findHeaderSeq(Ses0020ParamVO param) {
		return Ses0020Dao.findHeaderSeq(param);
	}

	public Dataset save(MipParameterVO paramVO, Model model, SqlSession session) {

		Dataset dsSave    = paramVO.getDataset("ds_save" );
		Dataset dsHeader = paramVO.getDataset("ds_head");

		try {
			createDao(session);  // DAO생성
		
			Ses0020ParamVO param = new Ses0020ParamVO();
		
			String sZtpLno = DatasetUtility.getString(dsSave, 0, "ZTPLNO");
			String pGubun = paramVO.getVariable("pGubun");
			
			
			// 2020 브랜드
			String brndcd = DatasetUtility.getString(dsHeader, 0, "BRNDCD");
			if(brndcd == null) brndcd = "";
			
			if (pGubun == null) pGubun = "";

			if (sZtpLno == null || sZtpLno.length() == 0) {
				sZtpLno = "0";
				param.setZtplno(sZtpLno);
			} else {
				param.setZtplno(sZtpLno);
			}

			param.setMandt(paramVO.getVariable("G_MANDT"));	
			param.setZtplgbn(DatasetUtility.getString(dsHeader, 0, "ZTPLGBN"));
			param.setZtplnm(DatasetUtility.getString(dsHeader,  0, "ZTPLNM"));
			param.setZprdgbn(DatasetUtility.getString(dsHeader, 0, "ZPRDGBN"));
			param.setZrmk(DatasetUtility.getString(dsHeader, 0, "ZRMK"));
			
			// 2020 브랜드
			param.setBrndcd(brndcd);

			//param.setZprdgbn(DatasetUtility.getString(dsSave, 0, "ATKLA"));

/*			System.out.println("============ ZTPLGBN = " + DatasetUtility.getString(dsHeader,  0, "ZTPLGBN") );
			System.out.println("============ ZTPLNM  = " + DatasetUtility.getString(dsHeader,  0, "ZTPLNM") );
			System.out.println("============ ZPRDGBN = " + DatasetUtility.getString(dsHeader,  0, "ZPRDGBN") );*/

			if (pGubun.equals("U")) {

				List<Ses0020> Headerseq= findHeaderSeq(param);
				param.setZtplno(Headerseq.get(0).getZtplno());
				mergeHeader(param); // 마스터 테이블 저장
			} else if (pGubun.equals("S")) {	// update
				String headFlag = DatasetUtility.getString(dsHeader, 0, "FLAG");
				if (headFlag.equals("U")) {
					param.setZtplno(DatasetUtility.getString(dsHeader, 0, "ZTPLNO"));
					mergeHeader(param); // 마스터 테이블 저장
				}
			}

			for (int i=0;i< dsSave.getRowCount(); i++) {
				
				int sZtplseq = DatasetUtility.getInt(dsSave, i, "ZTPLSEQ");
				String sPrd  = DatasetUtility.getString(dsSave, i, "PRD");
				String sPrh  = DatasetUtility.getString(dsSave, i, "PRH");
				String sFlag = DatasetUtility.getString(dsSave, i, "FLAG");

				if (sPrd == null) sPrd = "";
				if (sPrh == null) sPrh = "";
				if (sFlag == null) sFlag = "";

				param.setZtplseq(sZtplseq);
				param.setPrh(sPrh);
				param.setPrd(sPrd);

//				System.out.print("\n@@@ i ===>"+ i +"\n");
//				System.out.print("\n@@@ mandt ===>"+ param.getMandt() +"\n");
//				System.out.print("\n@@@ Ztplno ===>"+ param.getZtplno() +"\n");
//				System.out.print("\n@@@ ZTPLSEQ ===>"+ param.getZtplseq() +"\n");
//				System.out.print("\n@@@ PRH ===>"+ param.getPrh() +"\n");
//				System.out.print("\n@@@ PRD ===>"+ param.getPrd()+"\n");

				if(sFlag.equals("D")) {
					deleteHeader(param);
					deleteDetail(param);

				} else if(sFlag.equals("I") || sFlag.equals("U") ) {
					mergeDetail(param);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dsSave;
	}
	
	public int mergeHeader(Ses0020ParamVO param) {
		return Ses0020Dao.mergeHeader(param);
	}
	
	public int mergeDetail(Ses0020ParamVO param) {
		return Ses0020Dao.mergeDetail(param);
	}
	
	public int deleteHeader(Ses0020ParamVO param) {
		return Ses0020Dao.deleteHeader(param);
	}
	
	public int deleteDetail (Ses0020ParamVO param) {
		return Ses0020Dao.deleteDetail (param);
	}	
}
