package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0280;
import hdel.sd.ses.domain.Ses0280ParamVO;
import hdel.sd.ses.service.Ses0280S;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0280")
public class Ses0280C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0280S service;
	
	@RequestMapping(value="header")
	public View Ses0280FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_header");	// UI로 return할 out dataset  
		 
		try { 
			Ses0280ParamVO param = new Ses0280ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrqtdat   (DatasetUtility.getString(dsCond,"FR_QTDAT"  ));
			param.setToqtdat  (DatasetUtility.getString(dsCond,"TO_QTDAT"  ));
			param.setFrzestdat (DatasetUtility.getString(dsCond,"FR_ZESTDAT"));
			param.setTozestdat(DatasetUtility.getString(dsCond,"TO_ZESTDAT"));
			param.setVkbur    (DatasetUtility.getString(dsCond,"VKBUR"       ));
			param.setVkgrp    (DatasetUtility.getString(dsCond,"VKGRP"       ));
			param.setZprgflg  (DatasetUtility.getString(dsCond,"ZPRGFLG"    ));
			param.setZkunnr  (DatasetUtility.getString(dsCond,"SMAN"        ));
			//param.setZapflg  (DatasetUtility.getString(dsCond,"ZAPFLG"        ));

			//--------------------------------------------------------------------------------------------
			// INPUT PARAM INFO     		
//			System.out.print("\n@@@ paramVO.G_MANDT   ===>"+paramVO.getVariable("G_MANDT") 	       +"\n");  
//			System.out.print("\n@@@ dsCond.FR_QTDAT 	===>"+DatasetUtility.getString(dsCond,"FR_QTDAT")	+"\n");
//			System.out.print("\n@@@ dsCond.TO_QTDAT	===>"+DatasetUtility.getString(dsCond,"TO_QTDAT")	+"\n");
//			System.out.print("\n@@@ dsCond.FR_ZESTDAT	===>"+DatasetUtility.getString(dsCond,"FR_ZESTDAT")+"\n");
//			System.out.print("\n@@@ dsCond.TO_ZESTDAT ===>"+DatasetUtility.getString(dsCond,"TO_ZESTDAT")+"\n");
//			System.out.print("\n@@@ dsCond.VKBUR        ===>"+DatasetUtility.getString(dsCond,"VKBUR")	    +"\n");
//			System.out.print("\n@@@ dsCond.VKGRP        ===>"+DatasetUtility.getString(dsCond,"VKGRP")	    +"\n");
//			System.out.print("\n@@@ dsCond.ZPRGFLG     ===>"+DatasetUtility.getString(dsCond,"ZPRGFLG")	    +"\n");
//			System.out.print("\n@@@ dsCond.SMAN         ===>"+DatasetUtility.getString(dsCond,"SMAN")	    +"\n");
			//System.out.print("\n@@@ dsCond.ZAPFLG         ===>"+DatasetUtility.getString(dsCond,"ZAPFLG")	    +"\n");
			//--------------------------------------------------------------------------------------------
			
			List<Ses0280> list = service.searchHeader(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if       (dsList.getColumnID(iCol).equals("MANDT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("QTNUM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtnum());
					else if (dsList.getColumnID(iCol).equals("QTSER"))               dsList.setColumn(iRow, iCol, list.get(iRow).getQtser()); 
					else if (dsList.getColumnID(iCol).equals("ZRQSEQ"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqseq()); 
					else if (dsList.getColumnID(iCol).equals("ZRQDAT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqdat()); 
					else if (dsList.getColumnID(iCol).equals("ZRQCN"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqcn()); 
					else if (dsList.getColumnID(iCol).equals("ZRQSTAT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqstat()); 
					else if (dsList.getColumnID(iCol).equals("ZRQRLT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqrlt()); 
					else if (dsList.getColumnID(iCol).equals("QTDAT"))              dsList.setColumn(iRow, iCol, list.get(iRow).getQtdat()); 
					else if (dsList.getColumnID(iCol).equals("SPART"))               dsList.setColumn(iRow, iCol, list.get(iRow).getSpart()); 
					else if (dsList.getColumnID(iCol).equals("QTGBN"))              dsList.setColumn(iRow, iCol, list.get(iRow).getQtgbn()); 
					else if (dsList.getColumnID(iCol).equals("ZBDTYP"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZbdtyp()); 
					else if (dsList.getColumnID(iCol).equals("ZPRGFLG"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZprgflg()); 
					else if (dsList.getColumnID(iCol).equals("VKBUR"))              dsList.setColumn(iRow, iCol, list.get(iRow).getVkbur()); 
					else if (dsList.getColumnID(iCol).equals("VKGRP"))               dsList.setColumn(iRow, iCol, list.get(iRow).getVkgrp()); 
					else if (dsList.getColumnID(iCol).equals("ZKUNNR"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZkunnr()); 
					else if (dsList.getColumnID(iCol).equals("ZAGNT"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZagnt()); 
					else if (dsList.getColumnID(iCol).equals("KUNNR"))              dsList.setColumn(iRow, iCol, list.get(iRow).getKunnr()); 
					else if (dsList.getColumnID(iCol).equals("ZCST"))                 dsList.setColumn(iRow, iCol, list.get(iRow).getZcst()); 
					else if (dsList.getColumnID(iCol).equals("ZGNM"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZgnm()); 
					else if (dsList.getColumnID(iCol).equals("ZDEV"))                dsList.setColumn(iRow, iCol, list.get(iRow).getZdev()); 
					else if (dsList.getColumnID(iCol).equals("GSNAM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getGsnam()); 
					else if (dsList.getColumnID(iCol).equals("ZREGN"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZregn()); 
					else if (dsList.getColumnID(iCol).equals("ZPID"))                 dsList.setColumn(iRow, iCol, list.get(iRow).getZpid()); 
					else if (dsList.getColumnID(iCol).equals("ZTEL"))                 dsList.setColumn(iRow, iCol, list.get(iRow).getZtel()); 
					else if (dsList.getColumnID(iCol).equals("ZADDR_ZPOST"))     dsList.setColumn(iRow, iCol, list.get(iRow).getZaddr_zpost()); 
					else if (dsList.getColumnID(iCol).equals("ZADDR_ADR1"))      dsList.setColumn(iRow, iCol, list.get(iRow).getZaddr_adr1()); 
					else if (dsList.getColumnID(iCol).equals("ZADDR_ADR2"))      dsList.setColumn(iRow, iCol, list.get(iRow).getZaddr_adr2()); 
					else if (dsList.getColumnID(iCol).equals("SOABLE"))             dsList.setColumn(iRow, iCol, list.get(iRow).getSoable()); 
					else if (dsList.getColumnID(iCol).equals("ZESTDAT"))           dsList.setColumn(iRow, iCol, list.get(iRow).getZestdat()); 
					else if (dsList.getColumnID(iCol).equals("DELDAT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getDeldat()); 
					else if (dsList.getColumnID(iCol).equals("ZDVMTS"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZdvmts()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_ZNUMBER")) dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_znumber()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_ZCOST"))     dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_zcost()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_ZEAM"))      dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_zeam()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_SHANG"))    dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_shang()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_PPRT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_pprt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_PPCT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_ppct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_PPCD"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_ppcd()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_1STRT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_1strt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_1STCT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_1stct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_1STCD"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_1stcd()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_2STRT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_2strt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_2STCT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_2stct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_2STCD"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_2stcd()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_RMRT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_rmrt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_RMCT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_rmct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_RMCD"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_rmcd()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK1"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk1()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK2"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk2()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK3"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk3()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK4"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk4()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK5"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk5()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK6"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk6()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK7"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk7()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK8"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk8()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK9"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk9()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK10"))          dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk10());
					else if (dsList.getColumnID(iCol).equals("ZSOFLG"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZsoflg()); 
					else if (dsList.getColumnID(iCol).equals("SONNR"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSonnr());
				}
			} 
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList); 
			
//			System.out.println("============== ds_header.getRowCount() " + dsList.getRowCount());
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
}
