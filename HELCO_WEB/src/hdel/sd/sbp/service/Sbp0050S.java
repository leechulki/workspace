package hdel.sd.sbp.service;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.com.service.Com0040S;
import hdel.sd.com.domain.Com0040;
import hdel.sd.com.domain.Com0040ParamVO;
import hdel.sd.sbp.dao.Sbp0050D;
import hdel.sd.sbp.domain.Sbp0040;
import hdel.sd.sbp.domain.Sbp0050;
import hdel.sd.sbp.domain.Sbp0050ParamVO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.service.resource.TransactionManager;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import org.apache.log4j.Logger;


/**
 * �����ȹ����ä�ǰ���(����)(Sbp0050S) Service
 * @Comment
 *     	1.  void createDao
 *		2.  List selectOpenDtm  	( �����ȹ(����) �����Ͻ�(14�ڸ�) ��ȸ )
 *		3.  List selectList 		( �����ȹ(����) ��� ��ȸ )
 *		4.  List selectListDetail	( �����ȹ��ȣ �� �ǿ� ��ϵ� �����ȹ(����) ����/û��/���ݸ�� ��ȸ )
 *		5.  void save 				( ���� ���� )
 *		6.  void detail_save 		( ����/û��/���� ���� )
 *
 * @since 1.0
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version
 * @version 1.0
 */


@Service
public class Sbp0050S {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

//	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Com0040S 		BuyrService ;	// �ŷ�����ȸ ����

	private Sbp0050D Sbp0050Dao;

	public void createDao(SqlSession sqlSession) {
		Sbp0050Dao = sqlSession.getMapper(Sbp0050D.class);
	}

	// �����ȹ �����Ͻ�(14�ڸ�) ��ȸ
	public List<Sbp0050> selectOpenDtm(Sbp0050ParamVO param) {
		return Sbp0050Dao.selectOpenDtm(param);
	}

	// �����ȹ ��� ��ȸ
	public List<Sbp0050> selectList(Sbp0050ParamVO param) {
		return Sbp0050Dao.selectList(param);
	}

	// �����ȹ��ȣ�� �� �ǿ� ��ϵ� �����ȹ ����/û��/���ݸ�� ��ȸ
	public List<Sbp0050> selectListDetail(Sbp0050ParamVO param) {
		return Sbp0050Dao.selectListDetail(param);
	}

	// �����ȹ��ȣ ����
	public void save(MipParameterVO paramVO, Model model, String pspidsv) throws Exception {


		logger.debug("@@@ Sbp0050S.save START!!!" + "");


		String ErrCode = "CE00001";  // "ó���� �����߽��ϴ�.Ȯ�� �� �ٽ� ó���� �ֽʽÿ�."


		// INPUT DATASET GET
		Dataset ds_detail 		= paramVO.getDataset("ds_detail");  	// ���� ���,����,���� �������
		Dataset ds_list_detail 	= paramVO.getDataset("ds_list_detail"); // ����/û��/���� ���� �������(����ݾ�)
		Dataset ds_yearm 		= paramVO.getDataset("ds_yearm"); 		// ����/û��/���� ���� �������(18����ġ ������)
		String 	mandt 			= paramVO.getVariable("G_MANDT");		// SAP CLIENT
		String 	user_id 		= paramVO.getVariable("G_USER_ID");		// WEB USER ID

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO
		// sap client (���� : 310 or 910)
		logger.debug("@@@ mandt 	: " + mandt);
		logger.debug("@@@ user_id 	: " + user_id);
		logger.debug("@@@ ds_detail.getRowCount   ===>"+ds_detail.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_detail.getRowCount(); irow++ )
		{
			for( int icol = 0; icol < ds_detail.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_detail["+irow+"Record : "+ds_detail.getColumnID(icol)+"] ===>"
								+ DatasetUtility.getString(ds_detail, irow, ds_detail.getColumnID(icol)));
			}
		}
		logger.debug("@@@ ds_yearm.getRowCount ===>"+ds_yearm.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_yearm.getRowCount(); irow++ )
		{
			for( int icol = 0; icol < ds_yearm.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_yearm["+irow+"Record : "+ds_yearm.getColumnID(icol)+"] ===>"
								+ DatasetUtility.getString(ds_yearm, irow, ds_yearm.getColumnID(icol)));
			}
		}
		logger.debug("@@@ ds_list_detail.getRowCount ===>"+ds_list_detail.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_list_detail.getRowCount(); irow++ )
		{
			for( int icol = 0; icol < ds_list_detail.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_list_detail["+irow+"Record : "+ds_list_detail.getColumnID(icol)+"] ===>"
								+ DatasetUtility.getString(ds_list_detail, irow, ds_list_detail.getColumnID(icol)));
			}
		}
		//--------------------------------------------------------------------------------------------

		logger.debug("@@@ Sbp0050SaveView start" + "");

		String zbpnnr = "";		// �����ȹ��ȣ

		//------------------------------------------------------------------------
		// ���� ����
		//------------------------------------------------------------------------
		Sbp0050[] 	param 		= null;						// ���� ���� �Ķ����
		Sbp0050 	paramDetail = null;						// ����/û��/���� ���� �Ķ����
		String      flag    	= "";						// ���屸�� 			(I:�űԼ���, U:����, D:����)
		String 		auart		= "";						// �ǸŹ�������(��������)
		String		zkunnr		= "";						// ������ڵ�
		String		kunnr		= "";						// �ŷ����ڵ�
		String		zpyear		= "";						// �����Ͻ�
		String 		gbn 		= "";						// ����/û��/���� ����	(1:����, 2:û��, 3:����)
		BigDecimal 	amt 		= null;						// ����/û��/���� �ݾ�
		BigDecimal 	zero 		= new BigDecimal("0.00");	// 0.00
		String 		bill_save_yn= "Y"; 						// ����/û��/���� �ڷ� ����SKIP����

		//------------------------------------------------------------------------
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		//------------------------------------------------------------------------

		//------------------------------------------------------------------------
		// DAO ����
		//------------------------------------------------------------------------
		createDao(session);

		//------------------------------------------------------------------------
		// �����Ͻ� ���翩�� üũ (�����Ͻ� ������� ����Ұ�ó��)
		//------------------------------------------------------------------------
		zpyear	= DatasetUtility.getString(ds_detail, 0, "ZPYEAR");	// ���⵵
		if (chkOpenDtm(session, mandt, zpyear)==false)
		{
			// "CE10002", "���µ��� ���� ���⵵�� �����ȹ�Դϴ�.\n�ٽ� �ѹ� Ȯ�� �ٶ��ϴ�."
			throw new BizException("CE10002");
		}

		//------------------------------------------------------------------------
		// �����ȹ��ȣ�� ó�� START
		//------------------------------------------------------------------------
		for( int irow = 0; irow < ds_detail.getRowCount(); irow++ )
		{

			ds_detail.setColumn(irow, "USER_ID"	,user_id); 	// WEB USER ID
			for( int icol = 0; icol < ds_detail.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_detail["+irow+"Record : "
									+ ds_detail.getColumnID(icol)+"] ===>"
									+ DatasetUtility.getString(ds_detail, irow, ds_detail.getColumnID(icol))
									);
			}

			flag 		= DatasetUtility.getString(ds_detail, irow, "FLAG");		// ���屸��(I,U,D)
			auart		= DatasetUtility.getString(ds_detail, irow, "AUART");		// �ǸŹ�������(��������)
			kunnr		= DatasetUtility.getString(ds_detail, irow, "KUNNR");		// �ŷ����ڵ�
			bill_save_yn= DatasetUtility.getString(ds_detail, irow, "BILL_SAVE_YN");// ����/û��/���� �ڷ� ����SKIP����
			logger.debug("@@@ bill_save_yn : " + bill_save_yn);

			// 1. ����������Ʈ���� Input Dataset(ds_detail) --> class(Sbp0050)�� copy
			logger.debug("@@@ 1. ����������Ʈ���� Input Dataset(ds_detail) --> class(Sbp0050)�� copy");
			param = (Sbp0050[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0050.class, "", null);

			//------------------------------------------------------------------------
			// ������ ���
			//------------------------------------------------------------------------
			if ("U".equals(DatasetUtility.getString(ds_detail, irow, "FLAG")))
			{

					// 4.1 ������� ����CALL  (�����ȹ��ȣ �ѰǴ� ����)
					logger.debug("@@@ 4.1 ������� ����CALL  (�����ȹ��ȣ �ѰǴ� ����) ");
					Sbp0050Dao.deleteZSDT1024(param[0]);

					// 4.2 û������ ����CALL  (�����ȹ��ȣ �ѰǴ� ����)
					logger.debug("@@@ 4.2 û������ ����CALL  (�����ȹ��ȣ �ѰǴ� ����) ");
					Sbp0050Dao.deleteZSDT1025(param[0]);

					// 4.3 ���ݻ��� ����CALL  (�����ȹ��ȣ �ѰǴ� ����)
					logger.debug("@@@ 4.3 ���ݻ��� ����CALL  (�����ȹ��ȣ �ѰǴ� ����) ");
					Sbp0050Dao.deleteZSDT1026(param[0]);

					// 4.4 ����/û��/���ݵ�� ����CALL
					logger.debug("@@@ 4.4 ����/û��/���ݵ�� ����CALL ");
					detail_save(  ds_list_detail	// ����/û��/���� ����ݾ�
								, ds_yearm			// ����/û��/���� ������
								, mandt				// CLIENT
								, user_id 			// WEB USER_ID
								, pspidsv 			// ����������Ʈ��ȣ
								);

			}

		} // end of for

		logger.debug("@@@ Sbp0050S.save SUCCESS!!!" + "");

	}

	// ����/û��/���� ���
	public void detail_save(	 Dataset ds_list_detail
								,Dataset ds_yearm	// ������ 18����
								,String mandt		// CLIENT
								,String user_id 	// WEB USER_ID
								,String pspidsv 	// ����������Ʈ��ȣ
								)
	{

		logger.debug("@@@ Sbp0050SaveView.detail_save START!!!" + "");

		Sbp0050 	paramDetail = new Sbp0050(); 			// ����/û��/����
		String 		gbn 		= null;						// ����/û��/���� ����	(1:����, 2:û��, 3:����)
		BigDecimal 	amt 		= null;						// ����/û��/���� �ݾ�
		BigDecimal 	zero 		= new BigDecimal("0.00");	// 0.00
		String      colId       = null;
		String      yearm 		= null;
		int 		row 		= 0;

		for (int iRow1=0;iRow1<ds_list_detail.getRowCount(); iRow1++ )
		{
			gbn = ds_list_detail.getColumnAsString(iRow1, "GBN"); 	// ����/û��/���� ����

			for (int iCol1=0;iCol1<ds_list_detail.getColumnCount(); iCol1++ )
			{

				colId = ds_list_detail.getColumnID(iCol1);

				if (!("MANDT".equals(colId)) && !("GBN".equals(colId)) && !("GBN_NM".equals(colId)))
				{

					// �ݾ��׸� ���� �ִ� ��쿡�� ����
					if (StringUtils.isNotBlank(ds_list_detail.getColumnAsString(iRow1, iCol1)))
					{
						// ����ݾ�
						amt = new BigDecimal(ds_list_detail.getColumnAsDouble(iRow1, iCol1));

						// �ݾ� > 0 �� ��츸 ���
						if( amt.compareTo(zero) == 1 )  // compareTo :::: -1�� �۴�, 0�� ����, 1�� ũ��
						{

							row = Integer.parseInt(colId.substring(3,5));
							yearm = ds_yearm.getColumnAsString(row, "YEARM");
							logger.debug("@@@ row   : " + row);
							logger.debug("@@@ yearm : " + yearm);

							paramDetail.setMANDT	(mandt);  		// SAP CLIENT
							paramDetail.setUSER_ID	(user_id);  	// WEB USER_ID
							paramDetail.setPSPIDSV	(pspidsv);		// ����������Ʈ��ȣ
							paramDetail.setZSAYM	(yearm);		// ������
							paramDetail.setNETWR_SA (amt);			// ����ݾ�

							logger.debug("@@@ paramDetail.mandt 	[" + mandt 		+  "] ");
							logger.debug("@@@ paramDetail.user_id 	[" + user_id 	+  "] ");
							logger.debug("@@@ paramDetail.pspidsv 	[" + pspidsv 	+  "] ");
							logger.debug("@@@ paramDetail.yearm 	[" + yearm 		+  "] ");
							logger.debug("@@@ paramDetail.amt 		[" + amt 		+  "] ");

							// ��ϼ��� CALL (����,û���� ���� ������ ����Ѵ�.)
							if 		 ("1".equals(gbn)) {
								Sbp0050Dao.insertZSDT1024(paramDetail);  // ������ ����CALL
								Sbp0050Dao.insertZSDT1025(paramDetail);  // û����� ����CALL
							}else if ("3".equals(gbn)) {
								Sbp0050Dao.insertZSDT1026(paramDetail);  // ���ݵ�� ����CALL
							}
						}  	// end of if( amt.compareTo(zero) == 1 )
					}		// end of if (StringUtils.isNotBlank(ds_list_detail.getColumnAsString(iRow1, iCol1)))
				}			//
			}  				// end of for (int iCol1=2;iCol1<=ds_list_detail.getColumnCount(); iCol1++ )
		}					// end of for (int iRow1=0;iRow1<=ds_list_detail.getRowCount(); iRow1++ )

		logger.debug("@@@ Sbp0050SaveView.detail_save SUCCESS !!!" + "");

	}

	// �����Ͻ� ���翩�� üũ
	public Boolean chkOpenDtm(SqlSession session
					            , String mandt
								, String zpyear		// ���⵵
								)
	{


		logger.debug("@@@ Sbp0050SaveView.chkOpenDtm START!!!" 	+ "");
		logger.debug("@@@ Sbp0050SaveView.chkOpenDtm.mandt" 		+ mandt + "");
		logger.debug("@@@ Sbp0050SaveView.chkOpenDtm.zpyear" 		+ zpyear + "");

		Sbp0050ParamVO param = new Sbp0050ParamVO();

		// �Ķ����SET
		param.setMANDT	(mandt);  	// SAP CLIENT
		param.setZPYEAR	(zpyear);	// ���⵵

		// ����CALL (�����ȹ�⵵�� �ش��ϴ� �������� ��ȸ)
		List<Sbp0050> list = selectOpenDtm(param);

		// ��ȸ�� �����Ͻ� ������ ���� ��� ������ �����Ѵ�.(����Ұ�ó��)
		if (list.size()==0) return false;

		return true;
	}


}
