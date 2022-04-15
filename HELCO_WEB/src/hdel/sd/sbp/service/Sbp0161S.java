package hdel.sd.sbp.service;


import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.sbp.dao.Sbp0161D;
import hdel.sd.sbp.domain.Sbp0161;
import hdel.sd.sbp.domain.Sbp0161ParamVO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Sbp0161S extends SrmService {
	 
	private Sbp0161D sbp0161D;

	@Override
	public void createDao(SqlSession sqlSession) {
		sbp0161D = sqlSession.getMapper(Sbp0161D.class);
	}
	
	public List<Sbp0161> selectListDetail(Sbp0161ParamVO param) {
		return sbp0161D.selectListDetail(param);
	}
	
	public int insertZSDT1002(Sbp0161 param) {
		return sbp0161D.insertZSDT1002(param);
	}
	
	public int deleteZSDT1002(Sbp0161 param) {
		return sbp0161D.deleteZSDT1002(param);
	}
	
	public int insertZSDT1003(Sbp0161 param) {
		return sbp0161D.insertZSDT1003(param);
	}
	
	public int deleteZSDT1003(Sbp0161 param) {
		return sbp0161D.deleteZSDT1003(param);
	}
	
	public int insertZSDT1004(Sbp0161 param) {
		return sbp0161D.insertZSDT1004(param);
	}
	
	public int deleteZSDT1004(Sbp0161 param) {
		return sbp0161D.deleteZSDT1004(param);
	}

	public void save(MipParameterVO paramVO, Model model, SqlSession session) {
		Dataset dsCondDetail = paramVO.getDatasetList().getDataset("ds_cond_detail");
		Dataset dsYearm = paramVO.getDatasetList().getDataset("ds_yearm");
		Dataset dsListDetail = paramVO.getDatasetList().getDataset("ds_list_detail");
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
		String userId = paramVO.getVariable("G_USER_ID");		// WEB USER ID
		String sonnr = DatasetUtility.getString(dsCondDetail,"SONNR");

		try {
			Sbp0161 param = new Sbp0161();				// ����/û��/���� ���� �Ķ����
			String gbn = null;							// ����/û��/���� ����	(1:����, 2:û��, 3:����)
			BigDecimal amt = null;						// ����/û��/���� �ݾ�
			BigDecimal zero = new BigDecimal("0.00");	// 0.00
			
			// DAO����
			createDao(session);

			param.setMANDT(mandt);
			param.setSONNR(sonnr);
			
			// ������� ����CALL  (�����ȹ��ȣ �ѰǴ� ����)
			deleteZSDT1002(param);
			
			// û������ ����CALL  (�����ȹ��ȣ �ѰǴ� ����)
			deleteZSDT1003(param);
			
			// ���ݻ��� ����CALL  (�����ȹ��ȣ �ѰǴ� ����)
			deleteZSDT1004(param);  
			
			// ����/û��/���ݵ�� ����CALL
			detailSave( dsListDetail												// ����/û��/���� ����ݾ�
								, dsYearm											// ����/û��/���� ������
								, mandt												// CLIENT
								, userId 											// WEB USER_ID
								, sonnr 											// �����ȹ��ȣ
								, DatasetUtility.getString(dsCondDetail,"ZMPFLG") 	// �̵���ȹ�ݿ�����
								, DatasetUtility.getString(dsCondDetail,"WAERK")	// ��ȭ
								);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	// ����/û��/���� ���
	public void detailSave(	 Dataset dsListDetail
								,Dataset dsYearm	// ������ 18����
								,String mandt		// CLIENT
								,String userId 		// WEB USER_ID
								,String sonnr 		// ���ְ�ȹ��ȣ
								,String zmpflg 		// �̵���ȹ�ݿ�����
								,String waerk 		// ��ȭ
								)
	{ 

		Sbp0161 param = new Sbp0161();				// ����/û��/���� ���� �Ķ����
		String gbn = null;							// ����/û��/���� ����	(1:����, 2:û��, 3:����)
		BigDecimal amt = null;						// ����/û��/���� �ݾ�
		BigDecimal zero = new BigDecimal("0.00");	// 0.00
		String colId = null;
		String yearm = null;
		
		for (int iRow1=0;iRow1<dsListDetail.getRowCount(); iRow1++ ) 
		{
			gbn = dsListDetail.getColumnAsString(iRow1, "GBN"); 	// ����/û��/���� ����
			
			for (int iCol1=0;iCol1<dsListDetail.getColumnCount(); iCol1++ ) 
			{
				colId = dsListDetail.getColumnID(iCol1);
				
				if (!("MANDT".equals(colId)) && !("GBN".equals(colId)) && !("GBN_NM".equals(colId)))
				{
					// �ݾ��׸� ���� �ִ� ��쿡�� ����
					if (StringUtils.isNotBlank(dsListDetail.getColumnAsString(iRow1, iCol1)))
					{ 
						// ����ݾ�
						amt = new BigDecimal(dsListDetail.getColumnAsDouble(iRow1, iCol1));  
						
						// �ݾ� > 0 �� ��츸 ���
						if( amt.compareTo(zero) == 1 )  // compareTo :::: -1�� �۴�, 0�� ����, 1�� ũ�� 
						{  	
							if ( colId.endsWith("00")) yearm = dsYearm.getColumnAsString( 0, "YEARM");
							if ( colId.endsWith("01")) yearm = dsYearm.getColumnAsString( 1, "YEARM");
							if ( colId.endsWith("02")) yearm = dsYearm.getColumnAsString( 2, "YEARM");
							if ( colId.endsWith("03")) yearm = dsYearm.getColumnAsString( 3, "YEARM");
							if ( colId.endsWith("04")) yearm = dsYearm.getColumnAsString( 4, "YEARM");
							if ( colId.endsWith("05")) yearm = dsYearm.getColumnAsString( 5, "YEARM");
							if ( colId.endsWith("06")) yearm = dsYearm.getColumnAsString( 6, "YEARM");
							if ( colId.endsWith("07")) yearm = dsYearm.getColumnAsString( 7, "YEARM");
							if ( colId.endsWith("08")) yearm = dsYearm.getColumnAsString( 8, "YEARM");
							if ( colId.endsWith("09")) yearm = dsYearm.getColumnAsString( 9, "YEARM");
							if ( colId.endsWith("10")) yearm = dsYearm.getColumnAsString(10, "YEARM");
							if ( colId.endsWith("11")) yearm = dsYearm.getColumnAsString(11, "YEARM");
							if ( colId.endsWith("12")) yearm = dsYearm.getColumnAsString(12, "YEARM");
							if ( colId.endsWith("13")) yearm = dsYearm.getColumnAsString(13, "YEARM");
							if ( colId.endsWith("14")) yearm = dsYearm.getColumnAsString(14, "YEARM");
							if ( colId.endsWith("15")) yearm = dsYearm.getColumnAsString(15, "YEARM");
							if ( colId.endsWith("16")) yearm = dsYearm.getColumnAsString(16, "YEARM");
							if ( colId.endsWith("17")) yearm = dsYearm.getColumnAsString(17, "YEARM");
							if ( colId.endsWith("18")) yearm = dsYearm.getColumnAsString(18, "YEARM");
							if ( colId.endsWith("19")) yearm = dsYearm.getColumnAsString(19, "YEARM");
							if ( colId.endsWith("20")) yearm = dsYearm.getColumnAsString(20, "YEARM");
							if ( colId.endsWith("21")) yearm = dsYearm.getColumnAsString(21, "YEARM");
							if ( colId.endsWith("22")) yearm = dsYearm.getColumnAsString(22, "YEARM");
							if ( colId.endsWith("23")) yearm = dsYearm.getColumnAsString(23, "YEARM");
							if ( colId.endsWith("24")) yearm = dsYearm.getColumnAsString(24, "YEARM");
							if ( colId.endsWith("25")) yearm = dsYearm.getColumnAsString(25, "YEARM");
							if ( colId.endsWith("26")) yearm = dsYearm.getColumnAsString(26, "YEARM");
							if ( colId.endsWith("27")) yearm = dsYearm.getColumnAsString(27, "YEARM");
							if ( colId.endsWith("28")) yearm = dsYearm.getColumnAsString(28, "YEARM");
							if ( colId.endsWith("29")) yearm = dsYearm.getColumnAsString(29, "YEARM");
							
							param.setMANDT(mandt);  		// SAP CLIENT
							param.setUSER_ID(userId);  	// WEB USER_ID
							param.setSONNR(sonnr);		// �����ȹ��ȣ
							param.setZMPFLG(zmpflg);	// �̵���ȹ�ݿ�����
							param.setWAERK(waerk);		// ��ȭ
							param.setZSAYM(yearm);		// ������
							
							// ��ϼ��� CALL
							if 		 ("1".equals(gbn)) { 
								param.setZSAYM(yearm);	// ������
								param.setNETWR_SA(amt);	// ����ݾ�
								insertZSDT1002(param);  // ������ ����CALL
							}else if ("2".equals(gbn)) { 
								param.setZRQYM(yearm);	// ������
								param.setNETWR_RQ(amt);	// ����ݾ�
								insertZSDT1003(param);  // û����� ����CALL
							}else if ("3".equals(gbn)) { 
								param.setBYSYM(yearm);	// ������
								param.setCOLBI(amt);	// ����ݾ�
								insertZSDT1004(param);  // ���ݵ�� ����CALL
							}
						}  	// end of if( amt.compareTo(zero) == 1 ) 
					}		// end of if (StringUtils.isNotBlank(ds_list_detail.getColumnAsString(iRow1, iCol1)))
				}			// 
			}  				// end of for (int iCol1=2;iCol1<=ds_list_detail.getColumnCount(); iCol1++ ) 
		}					// end of for (int iRow1=0;iRow1<=ds_list_detail.getRowCount(); iRow1++ ) 
	} 
	
}
