package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.service.SrmService;
import hdel.sd.com.domain.ZLang;
import hdel.sd.ses.dao.Ses0510D;
import hdel.sd.ses.domain.Ses0510;
import hdel.sd.ses.domain.Ses0510ParamVO;
import tit.util.DatasetUtility;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0510S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Ses0510D Ses0510Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0510Dao = sqlSession.getMapper(Ses0510D.class);
	}	

	/*-----------------------------------------------------
	 *  파트 코드 조회
	 ------------------------------------------------------*/
	public MipResultVO searchPartCd(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_part_cd = paramVO.getDataset("ds_part_cd");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
				
		try{
			Ses0510ParamVO param  = new Ses0510ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			
			List<Ses0510> partCd = Ses0510Dao.selectPartCd(param);
			
			CallSAPHdel.moveListToDsSub(ds_part_cd, Ses0510.class, partCd);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		
		resultVO.addDataset(ds_part_cd);
		resultVO.addDataset(ds_error);
		return resultVO;
	}
	
	/*-----------------------------------------------------
	 *  세부파트 코드 조회
	 ------------------------------------------------------*/
	public MipResultVO searchDetailCd(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_detail_cd = paramVO.getDataset("ds_detail_cd");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
				
		try{
			Ses0510ParamVO param  = new Ses0510ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setPart_cd(paramVO.getVariable("part_cd"));
			
			List<Ses0510> detailCd = Ses0510Dao.selectDetailCd(param);
			
			CallSAPHdel.moveListToDsSub(ds_detail_cd, Ses0510.class, detailCd);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		
		resultVO.addDataset(ds_detail_cd);
		resultVO.addDataset(ds_error);
		return resultVO;
	}
	
	/*-----------------------------------------------------
	 *  검토항목 코드 조회
	 ------------------------------------------------------*/
	public MipResultVO searchCheckId(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_check_id = paramVO.getDataset("ds_check_id");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
				
		try{
			Ses0510ParamVO param  = new Ses0510ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setDetail_cd(paramVO.getVariable("detail_cd"));
			
			List<Ses0510> checkCd = Ses0510Dao.selectCheckId(param);
			
			CallSAPHdel.moveListToDsSub(ds_check_id, Ses0510.class, checkCd);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		
		resultVO.addDataset(ds_check_id);
		resultVO.addDataset(ds_error);
		return resultVO;
	}

	public MipResultVO selectDeviationList(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_cond = paramVO.getDataset("ds_cond");
		Dataset ds_list = paramVO.getDataset("ds_list");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try{
			Ses0510ParamVO param  = new Ses0510ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setCal_zrqdat_fm(DatasetUtility.getString(ds_cond,"cal_zrqdat_fm"));
			param.setCal_zrqdat_to(DatasetUtility.getString(ds_cond,"cal_zrqdat_to"));
			param.setDestat(DatasetUtility.getString(ds_cond,"destat"));
			param.setPart_cd(DatasetUtility.getString(ds_cond,"part_cd"));
			param.setDetail_cd(DatasetUtility.getString(ds_cond,"detail_cd"));
			param.setCheck_id(DatasetUtility.getString(ds_cond,"check_id"));
			param.setZrqno(DatasetUtility.getString(ds_cond,"zrqno"));
			param.setSearchkey(DatasetUtility.getString(ds_cond,"searchkey"));
			param.setItem_cd(DatasetUtility.getString(ds_cond,"item_cd"));
			param.setSman(DatasetUtility.getString(ds_cond,"sman"));
			param.setKunnr(DatasetUtility.getString(ds_cond,"kunnr"));
			param.setSpras(ZLang.convSapLang(paramVO.getVariable("G_LANG")));
					
			List<Ses0510> dsList = Ses0510Dao.selectDeviationList(param);
			
			CallSAPHdel.moveListToDsSub(ds_list, Ses0510.class, dsList);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		
		resultVO.addDataset(ds_list);
		resultVO.addDataset(ds_error);
		return resultVO;
	}
}
