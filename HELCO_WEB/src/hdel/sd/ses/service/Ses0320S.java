package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0320D;
import hdel.sd.ses.domain.Ses0320;
import hdel.sd.ses.domain.Ses0320ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0320S extends SrmService {

	private Ses0320D Ses0320Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0320Dao = sqlSession.getMapper(Ses0320D.class);
	}

	public List<Ses0320> searchMinTemplate(Ses0320ParamVO param) {
		return Ses0320Dao.selectMinTemplate(param);
	}
	
	public List<Ses0320> findTemp(Ses0320ParamVO param) {
		return Ses0320Dao.selectListTemp(param);
	} 

	public List<Ses0320> findQt(Ses0320ParamVO param) {
		return Ses0320Dao.selectListQt(param);
	} 
	
	public List<Ses0320> find(Ses0320ParamVO param) {
		return Ses0320Dao.selectList(param);
	}  
	
	public List<Ses0320> findHeaderSeq(Ses0320ParamVO param) {
		return Ses0320Dao.findHeaderSeq(param);
	}

   // 2020 브랜드
	public List<Ses0320> findPt(Ses0320ParamVO param) {
		return Ses0320Dao.selectListPt(param);
	} 

	public Dataset save(MipParameterVO paramVO, Model model, SqlSession session) 	throws Exception{

		Dataset dsSave   = paramVO.getDataset("ds_save" );
		Dataset dsHeader = paramVO.getDataset("ds_head");

		try {
			createDao(session);  // DAO생성
		
			Ses0320ParamVO param = new Ses0320ParamVO();
		
			String sZtpLno = DatasetUtility.getString(dsSave, 0, "ZTPLNO");
			String pGubun  = paramVO.getVariable("pGubun");
			
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
			param.setZwriter(DatasetUtility.getString(dsHeader, 0, "ZWRITER"));
			param.setZrmk(DatasetUtility.getString(dsHeader, 0, "ZRMK"));

			// 2020 브랜드
			param.setBrndcd(brndcd);
			
			//System.out.println("============ ZTPLNM = " + DatasetUtility.getString(dsHeader,  0, "ZTPLNM") );
			//System.out.println("============ ZRMK  = " + DatasetUtility.getString(dsHeader,  0, "ZRMK") );
			//System.out.println("============ ZWRITER = " + DatasetUtility.getString(dsHeader,  0, "ZWRITER") );
			/*			
			
*/			

			if (pGubun.equals("U")) {

				List<Ses0320> Headerseq= findHeaderSeq(param);
				param.setZtplno(Headerseq.get(0).getZtplno());

				mergeHeader(param); // 마스터 테이블 저장

			} else if (pGubun.equals("S")) {	// update

				String headFlag = DatasetUtility.getString(dsHeader, 0, "FLAG");

				if (headFlag.equals("U")) {
					
					param.setZtplno(DatasetUtility.getString(dsHeader, 0, "ZTPLNO"));
//					System.out.println("============ ZTPLNO  = " + DatasetUtility.getString(dsHeader,  0, "ZTPLNO") );
					mergeHeader(param); // 마스터 테이블 저장
				}
			}
			
//System.out.println("============ dsSave.getRowCount() = " + dsSave.getRowCount() );
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
			throw e;
		}
		
		return dsSave;
	}
	
	public int mergeHeader(Ses0320ParamVO param) {
		return Ses0320Dao.mergeHeader(param);
	}
	
	public int mergeDetail(Ses0320ParamVO param) {
		return Ses0320Dao.mergeDetail(param);
	}
	
	public int deleteHeader(Ses0320ParamVO param) {
		return Ses0320Dao.deleteHeader(param);
	}
	
	public int deleteDetail (Ses0320ParamVO param) {
		return Ses0320Dao.deleteDetail (param);
	}	
}
