package hdel.sd.com.control;


import java.util.List;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.LockVO;
import hdel.sd.com.service.LockS;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;


@Controller
@RequestMapping("lock")
public class LockC {

	@Autowired
	private SrmView view;
	
	@Autowired
	private LockS service;

	@Autowired
	private SrmSqlSession sqlSession;
	
	/**
	 * �۾� ���� ���� ���� �ɷ��ִ��� ��ȸ�Ѵ�.
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="selectLock")
	public View selectLock(MipParameterVO paramVO, Model model)
	{
		String strMandt   = paramVO.getVariable("G_MANDT");
		String strPgmCode = paramVO.getVariable("PGMCODE");
		String strKeyId   = paramVO.getVariable("KEYID"  );
		String strUserId  = paramVO.getVariable("USERID" );
		
		if ( strMandt   == null ) strMandt   = "";
		if ( strPgmCode == null ) strPgmCode = "";
		if ( strKeyId   == null ) strKeyId   = "";
		if ( strUserId  == null ) strUserId  = "";
		
		LockVO lockVo = new LockVO();
		
		lockVo.setMANDT  (strMandt  );
		lockVo.setPGMCODE(strPgmCode);
		lockVo.setKEYID  (strKeyId  );
		lockVo.setUSERID (strUserId );
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error"); // UI�� return�� �����޼��� dataset
		
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			service.selectLock(lockVo);
			
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
	
	/**
	 * ���� ���� �ɷ��ִ� ���α׷��� ����� ��ȸ�Ѵ�. (�����ڿ� ���������� ��� ��ȸ��)
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="selectLockList")
	public View selectLockList(MipParameterVO paramVO, Model model)
	{
		String strMandt   = paramVO.getVariable("G_MANDT");
		String strPgmCode = paramVO.getVariable("PGMCODE");
		String strKeyId   = paramVO.getVariable("KEYID"  );
		String strUserId  = paramVO.getVariable("USERID" );
		
		if ( strMandt   == null ) strMandt   = "";
		if ( strPgmCode == null ) strPgmCode = "";
		if ( strKeyId   == null ) strKeyId   = "";
		if ( strUserId  == null ) strUserId  = "";
		
		LockVO lockVo = new LockVO();
		
		lockVo.setMANDT  (strMandt  );
		lockVo.setPGMCODE(strPgmCode);
		lockVo.setKEYID  (strKeyId  );
		lockVo.setUSERID (strUserId );
		
		Dataset ds_lock  = paramVO.getDatasetList().getDataset("ds_lock");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error"); // UI�� return�� �����޼��� dataset
		String strErrorMsg = "";
		
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			List<LockVO> list = service.selectLockList(lockVo);
			
			if ( ds_lock != null ) {
				ds_lock.deleteAll();
				for ( int i = 0 ; i < list.size() ; i++ ) {
					ds_lock.appendRow();
					ds_lock.setColumn(i, "MANDT"     , list.get(i).getMANDT()    );
					ds_lock.setColumn(i, "REGDAT"    , list.get(i).getREGDAT()   );
					ds_lock.setColumn(i, "PGMCODE"   , list.get(i).getPGMCODE()  );
					ds_lock.setColumn(i, "KEYID"     , list.get(i).getKEYID()    );
					ds_lock.setColumn(i, "USERID"    , list.get(i).getUSERID()   );
					ds_lock.setColumn(i, "USERNAME"  , list.get(i).getUSERNAME() );
					ds_lock.setColumn(i, "USERMAIL"  , list.get(i).getUSERMAIL() );
					ds_lock.setColumn(i, "USERTELE"  , list.get(i).getUSERTELE() );
					ds_lock.setColumn(i, "USERMBPN"  , list.get(i).getUSERMBPN() );
					ds_lock.setColumn(i, "STADAT"    , list.get(i).getSTADAT()   );
					ds_lock.setColumn(i, "STATIM"    , list.get(i).getSTATIM()   );
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			strErrorMsg = e.getMessage();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO();
		if ( ds_lock != null ) {
			resultVO.addDataset(ds_lock);
		}
		resultVO.addVariable("strErrorMsg", strErrorMsg);
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
		
	/**
	 * �۾� ������ ���� ����� �����Ѵ�.
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="setLock")
	public View setLock(MipParameterVO paramVO, Model model)
	{
		String strMandt   = paramVO.getVariable("G_MANDT");
		String strPgmCode = paramVO.getVariable("PGMCODE");
		String strKeyId   = paramVO.getVariable("KEYID"  );
		String strUserId  = paramVO.getVariable("USERID" );
		
		strMandt   = strMandt   == null ? "" :  strMandt.trim();
		strPgmCode = strPgmCode == null ? "" :  strPgmCode.trim();
		strKeyId   = strKeyId   == null ? "" :  strKeyId.trim();
		strUserId  = strUserId  == null ? "" :  strUserId.trim();
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error"); // UI�� return�� �����޼��� dataset
		String strErrorMsg = "";
		
		try {
			SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

			LockVO lockVo = new LockVO();
			
			lockVo.setMANDT  (strMandt  );
			lockVo.setPGMCODE(strPgmCode);
			lockVo.setKEYID  (strKeyId  );
			lockVo.setUSERID (strUserId );
			
			service.setLock(lockVo, session);
			
		} catch (Exception e) {
			e.printStackTrace();
			strErrorMsg = e.getMessage();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO();
		resultVO.addVariable("strErrorMsg", strErrorMsg);
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
	
	/**
	 * �۾� ���Ḧ ���� ���� �����Ѵ�.
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="releasLock")
	public View releasLock(MipParameterVO paramVO, Model model)
	{
		String strMandt   = paramVO.getVariable("G_MANDT");
		String strPgmCode = paramVO.getVariable("PGMCODE");
		String strKeyId   = paramVO.getVariable("KEYID"  );
		String strUserId  = paramVO.getVariable("USERID" );
		
		if ( strMandt   == null ) strMandt   = "";
		if ( strPgmCode == null ) strPgmCode = "";
		if ( strKeyId   == null ) strKeyId   = "";
		if ( strUserId  == null ) strUserId  = "";

		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		LockVO lockVo = new LockVO();
		
		lockVo.setMANDT  (strMandt  );
		lockVo.setPGMCODE(strPgmCode);
		lockVo.setKEYID  (strKeyId  );
		lockVo.setUSERID (strUserId );
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error"); // UI�� return�� �����޼��� dataset
		String strErrorMsg = "";
		
		try {
			
			service.releaseLock(lockVo, session);
			
		} catch (Exception e) {
			e.printStackTrace();
			strErrorMsg = e.getMessage();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO();
		resultVO.addVariable("strErrorMsg", strErrorMsg);
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
}
