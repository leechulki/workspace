package hdel.sd.sbp.service;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;

import hdel.sd.sbp.dao.Sbp0070D;
import hdel.sd.sbp.domain.Sbp0010;
import hdel.sd.sbp.domain.Sbp0010ParamVO;
import hdel.sd.sbp.domain.Sbp0070;
import hdel.sd.sbp.domain.Sbp0070ParamVO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.service.resource.TransactionManager;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import org.apache.log4j.Logger;


/**
 * �����ȹ ����/����(Sbp0070S) Service
 * @Comment
 *     	1.  void createDao
 *		2.  List selectList 	( �����ȹ����  ��� ��ȸ )
 *		3.  List selectListLast ( ���� �����ȹ���� ���� ��ȸ )
 *		4.  void save 			( �����ȹ���� ���� ���� )
 *			- ó������ : IN(��������) DL(��������) OP(����) OC(�������) CL(����) CC(�������)
 *
 * @since 1.0
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version
 * @version 1.0
 */


@Service
public class Sbp0070S {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

//	@Autowired
	private SrmSqlSession sqlSession;

	private Sbp0070D sbp0070Dao;

	public void createDao(SqlSession sqlSession) {
		this.sqlSession = (SrmSqlSession) sqlSession;
		sbp0070Dao = sqlSession.getMapper(Sbp0070D.class);
	}

	// �����ȹ���� ��� ��ȸ
	public List<Sbp0070> selectList(Sbp0070ParamVO param) {
		return sbp0070Dao.selectList(param);
	}

	// �����ȹ���� �������� ��ȸ
	public List<Sbp0070> selectListLast(Sbp0070ParamVO param) {
		return sbp0070Dao.selectListLast(param);
	}

	// �����ȹ���� ���� ����
	public void save(MipParameterVO paramVO, Model model) throws Exception{

		logger.debug("@@@ Sbp0070S.save START!!!" + "");

		// INPUT PARAM GET
		Dataset ds_list_save 	= paramVO.getDataset("ds_list_save");  					// ���� ���,����,���� �������

		// ó������ (IN:��������, DL:��������, OP:����, OC:�������, CL:����, CC:�������
		String  proc_gb			= DatasetUtility.getString(ds_list_save, 0, "PROCGB");

		createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		//-----------------------------------------------------------------------
		// �ű����������� ���
		//-----------------------------------------------------------------------
		if ("IN".equals(proc_gb))
		{
			Sbp0070ParamVO 	param 	= new Sbp0070ParamVO();
			String 	mandt 			= paramVO.getVariable("G_MANDT");						// SAP CLIENT
			String 	user_id 		= paramVO.getVariable("G_USER_ID");						// WEB USER ID
			String  zpyear			= DatasetUtility.getString(ds_list_save, 0, "ZPYEAR");	// ���⵵

			param.setMANDT(mandt);
			param.setUSER_ID(user_id);
			param.setZPYEAR(zpyear);

			// ��������
			sbp0070Dao.insertZSDT1017(param);
		}

		//-----------------------------------------------------------------------
		// �׿�
		//-----------------------------------------------------------------------
		else
		{

			Sbp0070[] param = (Sbp0070[])CallSAPHdel.moveDs2Obj2(ds_list_save, Sbp0070.class, "", null);

			//------------------------------------------
			// ���� ������ ���
			//------------------------------------------
			if ("DL".equals(proc_gb)) {

				// ��������
				sbp0070Dao.deleteZSDT1017(param[0]);

			}
			//------------------------------------------
			// ���� �Ǵ� ������� �� ���
			//------------------------------------------
			else if ("OP".equals(proc_gb) || "OC".equals(proc_gb)) {

				// �������� / �����������
				sbp0070Dao.openZSDT1017(param[0]);

			}
			//------------------------------------------
			// ���� �Ǵ� ������� �� ���
			//------------------------------------------
			else if ("CL".equals(proc_gb) || "CC".equals(proc_gb)) {

				// ��������/�������
				sbp0070Dao.closeZSDT1017(param[0]);

				if ("CL".equals(proc_gb)){ 		// ������ ���
					sbp0070Dao.intoZSDT1018(param[0]);	// �����ȹ����-���� ���
					sbp0070Dao.intoZSDT1019(param[0]);	// �����ȹ����-���� ���
					sbp0070Dao.intoZSDT1020(param[0]);	// �����ȹ����-û�� ���
					sbp0070Dao.intoZSDT1021(param[0]);	// �����ȹ����-���� ���
					sbp0070Dao.intoZSDT1022(param[0]);	// �����ȹ����-���� ���
					sbp0070Dao.intoZSDT1028(param[0]);	// �����ȹ����(����)-���� ���
					sbp0070Dao.intoZSDT1029(param[0]);	// �����ȹ����(����)-���� ���
					sbp0070Dao.intoZSDT1030(param[0]);	// �����ȹ����(����)-û�� ���
					sbp0070Dao.intoZSDT1031(param[0]);	// �����ȹ����(����)-���� ���
					sbp0070Dao.intoZSDT1032(param[0]);	// �����ȹ����(����)-���� ���
				}
				else if ("CC".equals(proc_gb)){  // ��������� ���
					sbp0070Dao.deleteZSDT1018(param[0]);	// �����ȹ����-���� ����ڷ� ����
					sbp0070Dao.deleteZSDT1019(param[0]);	// �����ȹ����-���� ����ڷ� ����
					sbp0070Dao.deleteZSDT1020(param[0]);	// �����ȹ����-û�� ����ڷ� ����
					sbp0070Dao.deleteZSDT1021(param[0]);	// �����ȹ����-���� ����ڷ� ����
					sbp0070Dao.deleteZSDT1022(param[0]);	// �����ȹ����-���� ����ڷ� ����
					sbp0070Dao.deleteZSDT1028(param[0]);	// �����ȹ����(����)-���� ����ڷ� ����
					sbp0070Dao.deleteZSDT1029(param[0]);	// �����ȹ����(����)-���� ����ڷ� ����
					sbp0070Dao.deleteZSDT1030(param[0]);	// �����ȹ����(����)-û�� ����ڷ� ����
					sbp0070Dao.deleteZSDT1031(param[0]);	// �����ȹ����(����)-���� ����ڷ� ����
					sbp0070Dao.deleteZSDT1032(param[0]);	// �����ȹ����(����)-���� ����ڷ� ����
				}
			}

		}

		logger.debug("@@@ Sbp0070S.save SUCCESS!!!" + "");

	}

}
