package hdel.sd.ssa.service;

import java.util.List;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ssa.dao.Ssa0070D;
import hdel.sd.ssa.domain.Ssa0070;
import hdel.sd.ssa.domain.Ssa0070ParamVO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ssa0070S extends SrmService {

	private Ssa0070D Ssa0070Dao;

	public void createDao(SqlSession sqlSession) {
		Ssa0070Dao = sqlSession.getMapper(Ssa0070D.class);
	}

	public Dataset selectList(MipParameterVO paramVO, Model model, SqlSession session) throws Exception {
		
		Dataset ds_cond = paramVO.getDataset("ds_cond");
		Dataset ds_list = paramVO.getDataset("ds_list");
		ds_list.deleteAll();
		
		try {
			
			Ssa0070ParamVO param = new Ssa0070ParamVO();
			
			String mandt 	= DatasetUtility.getString(ds_cond, 0, "MANDT"		);
			String dategbn 	= DatasetUtility.getString(ds_cond, 0, "DATEGBN"	);
			String dateFr 	= DatasetUtility.getString(ds_cond, 0, "DATEFR"		);
			String dateTo 	= DatasetUtility.getString(ds_cond, 0, "DATETO"		);
			String pspid 	= DatasetUtility.getString(ds_cond, 0, "PSPID"		);
			String vkbur 	= DatasetUtility.getString(ds_cond, 0, "VKBUR"		);
			String vkgrp 	= DatasetUtility.getString(ds_cond, 0, "VKGRP"		);
			String pm 		= DatasetUtility.getString(ds_cond, 0, "PM"			);
			String said		= DatasetUtility.getString(ds_cond, 0, "SAID"		);
			String asugm 	= DatasetUtility.getString(ds_cond, 0, "ASUGM"		);
			String bsugm 	= DatasetUtility.getString(ds_cond, 0, "BSUGM"		);
			String csugm 	= DatasetUtility.getString(ds_cond, 0, "CSUGM"		);
			String zzactss 	= DatasetUtility.getString(ds_cond, 0, "ZZACTSS"	); // 사무소
			String zzpmnum 	= DatasetUtility.getString(ds_cond, 0, "ZZPMNUM"	); // PM
			
			if ( mandt 		== null ) mandt 	= paramVO.getVariable("G_MANDT");
			if ( dategbn 	== null ) dategbn 	= "";
			if ( dateFr 	== null ) dateFr 	= "";
			if ( dateTo 	== null ) dateTo 	= "";
			if ( pspid 		== null ) pspid 	= "";
			if ( vkbur 		== null ) vkbur 	= "";
			if ( vkgrp 		== null ) vkgrp 	= "";
			if ( pm 		== null ) pm 		= "";
			if ( said 		== null ) said 		= "";
			if ( asugm 		== null ) asugm 	= ""; if ( asugm.equals("0") ) asugm = "";
			if ( bsugm 		== null ) bsugm 	= ""; if ( bsugm.equals("0") ) bsugm = "";
			if ( csugm 		== null ) csugm 	= ""; if ( csugm.equals("0") ) csugm = "";
			if ( zzactss 	== null ) zzactss 	= "";
			if ( zzpmnum 	== null ) zzpmnum 	= "";
			
			param.setMandt		(mandt		);
			param.setDategbn	(dategbn	);
			if ( dategbn.equals("S") ) {
				param.setDateFrShip(dateFr);
				param.setDateToShip(dateTo);
			} else {
				param.setDateFrComp(dateFr);
				param.setDateToComp(dateTo);
			}
			param.setPspid		(pspid	);
			param.setVkbur		(vkbur		);
			param.setVkgrp		(vkgrp		);
			param.setPm			(pm			);
			param.setSaid		(said		);
			param.setAsugm		(asugm		);
			param.setBsugm		(bsugm		);
			param.setCsugm		(csugm		);
			param.setZzactss	(zzactss	);
			param.setZzpmnum	(zzpmnum	);
			
			List<Ssa0070> list = Ssa0070Dao.selectList(param);
			for ( int i = 0 ; i < list.size() ; i++ ) { // 호출결과를 데이타셋에 복사
				ds_list.appendRow();
				ds_list.setColumn(i, "CHK"      , list.get(i).getChk());
				ds_list.setColumn(i, "STATUS"   , list.get(i).getStatus());
				ds_list.setColumn(i, "PSPID"    , list.get(i).getPspid());
				ds_list.setColumn(i, "HOGI"     , list.get(i).getHogi());
				ds_list.setColumn(i, "BSTNK"    , list.get(i).getBstnk());
				ds_list.setColumn(i, "ZZSHIP1"  , list.get(i).getZzship1());
				ds_list.setColumn(i, "ZZCOMP2"  , list.get(i).getZzcomp2());
				ds_list.setColumn(i, "TEMNO"    , list.get(i).getTemno());
				ds_list.setColumn(i, "TEMNM"    , list.get(i).getTemnm());
				ds_list.setColumn(i, "LFNM"     , list.get(i).getLfnm());
				ds_list.setColumn(i, "VKBUR"    , list.get(i).getVkbur());
				ds_list.setColumn(i, "VKGRP"    , list.get(i).getVkgrp());
				ds_list.setColumn(i, "SADEPT"   , list.get(i).getSadept());
				ds_list.setColumn(i, "SAID"     , list.get(i).getSaid());
				ds_list.setColumn(i, "SANM"     , list.get(i).getSanm());
				ds_list.setColumn(i, "STRAS"    , list.get(i).getStras());
				ds_list.setColumn(i, "ZQEMIDDL" , list.get(i).getZqemiddl());
				ds_list.setColumn(i, "ZBEPJEO"  , list.get(i).getZbepjeo());
				ds_list.setColumn(i, "ZBOSUOUT" , list.get(i).getZbosuout());
				ds_list.setColumn(i, "ZBOSUIN"  , list.get(i).getZbosuin());
				ds_list.setColumn(i, "IRATE"    , list.get(i).getIrate());
				ds_list.setColumn(i, "SUGM"     , list.get(i).getSugm());
				ds_list.setColumn(i, "ASUGM_DT" , list.get(i).getAsugm_dt());
				ds_list.setColumn(i, "BSUGM_DT" , list.get(i).getBsugm_dt());
				ds_list.setColumn(i, "CSUGM_DT" , list.get(i).getCsugm_dt());
				ds_list.setColumn(i, "ZDLYBIGO" , list.get(i).getZdlybigo());
				ds_list.setColumn(i, "ERDAT"    , list.get(i).getErdat());
				ds_list.setColumn(i, "ERZZT"    , list.get(i).getErzzt());
				ds_list.setColumn(i, "ERNAM"    , list.get(i).getErnam());
				ds_list.setColumn(i, "CHAKYN"    , list.get(i).getChakyn());
				ds_list.setColumn(i, "JUNYN"    , list.get(i).getJunyn());
				ds_list.setColumn(i, "GADONGYN"    , list.get(i).getGadongyn());
				
			}
			
		} catch(Exception e) {
			throw e;
		}
		
		return ds_list;
	}

	public void saveList(MipParameterVO paramVO, Model model, SqlSession session) throws Exception {
		
		Dataset ds_list = paramVO.getDataset("ds_list");
		
		try {
			
			Ssa0070 param = new Ssa0070();
			
			for ( int i = 0 ; i < ds_list.getRowCount() ; i++ ) {
				String mandt 		= DatasetUtility.getString(ds_list, i, "MANDT");
				String pspid 		= DatasetUtility.getString(ds_list, i, "PSPID");
				String hogi 		= DatasetUtility.getString(ds_list, i, "HOGI");
				String asugm_dt 	= DatasetUtility.getString(ds_list, i, "ASUGM_DT");
				String bsugm_dt 	= DatasetUtility.getString(ds_list, i, "BSUGM_DT");
				String csugm_dt 	= DatasetUtility.getString(ds_list, i, "CSUGM_DT");
				String zdlybigo		= DatasetUtility.getString(ds_list, i, "ZDLYBIGO");
				String erzzt		= paramVO.getVariable("G_SAP_USER_ID");
				String ernam		= paramVO.getVariable("G_SAP_USER_NAME");
				
				if ( mandt 		== null ) mandt 	= paramVO.getVariable("G_MANDT");
				if ( pspid 		== null ) pspid 	= "";
				if ( hogi 		== null ) hogi 		= "";
				if ( asugm_dt 	== null ) asugm_dt 	= "00000000";
				if ( bsugm_dt 	== null ) bsugm_dt 	= "00000000";
				if ( csugm_dt 	== null ) csugm_dt 	= "00000000";
				if ( zdlybigo 	== null ) zdlybigo 	= "";
				if ( erzzt 		== null ) erzzt 	= "";
				if ( ernam 		== null ) ernam 	= "";
				
				param.setMandt(mandt);
				param.setPspid(pspid);
				param.setHogi(hogi);
				param.setAsugm_dt(asugm_dt);
				param.setBsugm_dt(bsugm_dt);
				param.setCsugm_dt(csugm_dt);
				param.setZdlybigo(zdlybigo);
				param.setErzzt(erzzt);
				param.setErnam(ernam);
				
				Ssa0070Dao.mergeList(param);
			}
		} catch (Exception e) {
			throw e;
		}
		
	}

}
