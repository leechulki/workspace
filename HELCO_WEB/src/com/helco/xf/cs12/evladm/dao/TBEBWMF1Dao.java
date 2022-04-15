/**
 * �� �� �� : TBEBWMF1Dao.java
 * �� �� �� : shhwang
 * �� �� �� : 2006-03-27
 * ��    �� : ���󺸼� ���� Dao
 * ���泻�� :
 *  
 */
package com.helco.xf.cs12.evladm.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tit.biz.AbstractBusinessService;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWMF1Vo;

// import org.apache.log4j.Logger;

public class TBEBWMF1Dao extends AbstractBusinessService implements FrwCrudDAO
{
//	static Logger logger = Logger.getLogger(TBEBWMF1Dao.class);
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-03
    * ��  ��: 
    * ��  Ÿ:
    */
	public void insert(Object obj) throws Exception
	{
		// TODO �ڵ� ������ �޼ҵ� ����
	}
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-03
    * ��  ��: 
    * ��  Ÿ:
    */
	public ArrayList selectList(Object searchCondVO) throws Exception
	{
		ArrayList resultList = new ArrayList();
		return resultList;
	}
	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-05-03
	 * ��  ��: �⼺��/�����ȹ �����ϱ� ���� ������ ���� ��������.
	 * ��  Ÿ: ����޼ҵ忡�� ���������� Key������ ������ ���� �׸���� ��ȸ �Ѵ�.
	 */
	public Object select(Object condVO) throws Exception
	{
		ComMethodVo vo = (ComMethodVo) condVO; 
		TBEBWMF1Vo rtnVo = new TBEBWMF1Vo();
		StringBuffer sqlBuff = new StringBuffer();
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		sqlBuff.append("SELECT                        \n");
		sqlBuff.append(" RTRIM(A.MANDT  ) AS MANDT   \n");
		sqlBuff.append(",RTRIM(CS126_UPN) AS CS126_UPN \n");
		sqlBuff.append(",RTRIM(CS126_CST) AS CS126_CST \n");
		sqlBuff.append(",RTRIM(CS126_GND) AS CS126_GND \n");
		sqlBuff.append(",RTRIM(CS126_BSU) AS CS126_BSU \n");
		sqlBuff.append(",RTRIM(CS126_HTY) AS CS126_HTY \n");
		sqlBuff.append(",RTRIM(CS126_ARA) AS CS126_ARA \n");
		sqlBuff.append(",RTRIM(CS126_AGB) AS CS126_AGB \n");
		sqlBuff.append(",RTRIM(CS126_ABG) AS CS126_ABG \n");
		sqlBuff.append(",RTRIM(CS126_GKD) AS CS126_GKD \n");
		sqlBuff.append(",RTRIM(CS126_RGB) AS CS126_RGB \n");
		sqlBuff.append(",RTRIM(CS126_YYB) AS CS126_YYB \n");
		sqlBuff.append(",RTRIM(CS126_USD) AS CS126_USD \n");
		sqlBuff.append(",CS126_UMS \n");
		sqlBuff.append(",CS126_MMN \n");
		sqlBuff.append(",RTRIM(CS126_UGS) AS CS126_UGS \n");
		sqlBuff.append(",RTRIM(CS126_UMR) AS CS126_UMR \n");
		sqlBuff.append(",RTRIM(CS126_UHJ) AS CS126_UHJ \n");
		sqlBuff.append(",CS126_AMT \n");
		sqlBuff.append(",CS126_HMT \n");
		sqlBuff.append(",CS126_DMT \n");
		sqlBuff.append(",CS126_VAT \n");
		sqlBuff.append(",CS126_TOT \n");
		sqlBuff.append(",CS126_AMBT \n");
		sqlBuff.append(",CS126_VABT \n");
		sqlBuff.append(",CS126_TOBT \n");
		sqlBuff.append(",RTRIM(CS126_ZER) AS CS126_ZER \n");
		sqlBuff.append(",CS126_RTO \n");
		sqlBuff.append(",CS126_PLT \n");
		sqlBuff.append(",RTRIM(CS126_KND) AS CS126_KND \n");
		sqlBuff.append(",RTRIM(CS126_TYP) AS CS126_TYP \n");
		sqlBuff.append(",RTRIM(CS126_BGB) AS CS126_BGB \n");
		sqlBuff.append(",RTRIM(CS126_JUJ) AS CS126_JUJ \n");
		sqlBuff.append(",RTRIM(CS126_IYN) AS CS126_IYN \n");
		sqlBuff.append(",RTRIM(CS126_WIL) AS CS126_WIL \n");
		sqlBuff.append(",RTRIM(CS126_BJG) AS CS126_BJG \n");
		sqlBuff.append(",RTRIM(CS126_TIS) AS CS126_TIS \n");
		sqlBuff.append(",RTRIM(CS121_SJU) AS CS121_SJU \n");
		sqlBuff.append(",CS126_RTO                     \n");
		sqlBuff.append(",CS126_SLA                     \n");
		sqlBuff.append(",CS126_BDGBN                   \n");
		//===========================����û���� �߰� 20200515 Han.JH==================================
		sqlBuff.append(",CS126_ACMT                    \n");
		//======================================================================================
		sqlBuff.append("FROM SAPHEE.ZCST126 A,         \n");
		sqlBuff.append("     SAPHEE.ZCST121 B          \n");
		sqlBuff.append("WHERE A.MANDT   = ?            \n");
		sqlBuff.append("  AND CS126_UPN = ?            \n");
		sqlBuff.append("  AND CS126_CST = ?            \n");
		sqlBuff.append("  AND CS126_PJT = ?            \n");
		sqlBuff.append("  AND CS126_HNO = ?            \n");
		sqlBuff.append("  AND CS126_SEQ = ?            \n");
		sqlBuff.append("  AND A.MANDT = B.MANDT        \n");
		sqlBuff.append("  AND A.CS126_UPN = B.CS121_UPN \n");
		sqlBuff.append("  AND A.CS126_CST = B.CS121_CST \n");

		try {
			String db_con = Utillity.getSapJndi(vo.getMandt());
	    	conn  = getConnection(db_con);

			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			pstmt.setString(1, vo.getMandt());
			pstmt.setString(2, vo.getUpn());
			pstmt.setString(3, vo.getCst());
			pstmt.setString(4, vo.getPjt());
			pstmt.setString(5, vo.getHno());
			pstmt.setString(6, vo.getSeq());

			// logger.debug("\n" + ((LoggablePreparedStatement)
			rs = pstmt.executeQuery();

			if (rs.next()) {
				rtnVo.setMandt(vo.getMandt());
				rtnVo.setCebwmupn(vo.getUpn());
				rtnVo.setCebwmcst(vo.getCst());
				rtnVo.setCebwmpjt(vo.getPjt());
				rtnVo.setCebwmhno(vo.getHno());
				rtnVo.setCebwmseq(vo.getSeq());
				rtnVo.setUserId(vo.getUserId());
				rtnVo.setGno(vo.getGno());
				rtnVo.setSla(rs.getString("CS126_SLA"));
				rtnVo.setCebwmgnd(rs.getString("CS126_GND"));
				rtnVo.setCebwmbsu(rs.getString("CS126_BSU"));
				rtnVo.setCebwmhty(rs.getString("CS126_HTY"));
				rtnVo.setCebwmara(rs.getString("CS126_ARA"));
				rtnVo.setCebwmagb(rs.getString("CS126_AGB"));
				rtnVo.setCebwmabg(rs.getString("CS126_ABG"));
				rtnVo.setCebwmgkd(rs.getString("CS126_GKD"));
				rtnVo.setCebwmrgb(rs.getString("CS126_RGB"));
				rtnVo.setCebwmyyb(rs.getString("CS126_YYB"));
				rtnVo.setCebwmusd(rs.getString("CS126_USD"));
				rtnVo.setCebwmums(rs.getString("CS126_UMS"));
				rtnVo.setCebwmmmn(rs.getString("CS126_MMN"));
				rtnVo.setCebwmugs(rs.getString("CS126_UGS"));
				rtnVo.setCebwmumr(rs.getString("CS126_UMR"));
				rtnVo.setCebwmuhj(rs.getString("CS126_UHJ"));
				rtnVo.setCebwmamt(rs.getString("CS126_AMT"));
				rtnVo.setCebwmhmt(rs.getString("CS126_HMT"));
				rtnVo.setCebwmdmt(rs.getString("CS126_DMT"));
				rtnVo.setCebwmvat(rs.getString("CS126_VAT"));
				rtnVo.setCebwmtot(rs.getString("CS126_TOT"));
				rtnVo.setCebwmambt(rs.getString("CS126_AMBT"));
				rtnVo.setCebwmvabt(rs.getString("CS126_VABT"));
				rtnVo.setCebwmtobt(rs.getString("CS126_TOBT"));
				rtnVo.setCebwmzer(rs.getString("CS126_ZER"));
				rtnVo.setCebwmrto(rs.getString("CS126_RTO"));
				rtnVo.setCebwmplt(rs.getString("CS126_PLT"));
				rtnVo.setCebwmknd(rs.getString("CS126_KND"));
				rtnVo.setCebwmtyp(rs.getString("CS126_TYP"));
				rtnVo.setCebwmbgb(rs.getString("CS126_BGB"));
				rtnVo.setCebwmjuj(rs.getString("CS126_JUJ"));
				rtnVo.setCebwmiyn(rs.getString("CS126_IYN"));
				rtnVo.setCebwmwil(rs.getString("CS126_WIL"));
				rtnVo.setCebwmbjg(rs.getString("CS126_BJG"));
				rtnVo.setCebwmtis(rs.getString("CS126_TIS"));
				rtnVo.setCebwmsju(rs.getString("CS121_SJU"));
				rtnVo.setCebwmrto(rs.getString("CS126_RTO"));
				rtnVo.setCebwmbdgbn(rs.getString("CS126_BDGBN"));
				//===========================����û���� �߰� 20200515 Han.JH==================================
				rtnVo.setCebwmacmt(rs.getString("CS126_ACMT"));
				//======================================================================================
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				DBUtil.close(rs, pstmt, null);
			} catch (Exception e) {
			}
		}
		return rtnVo;
	}
   /**
	 * �ۼ���: jkkoo �ۼ���: 2006-05-03 �� ��: �� Ÿ:
	 */
	public Object select(ArrayList compPk, String subMethodFlag) throws Exception
	{
		return null;
	}
	public Object select2(Object condVO) throws Exception
	{
		return null;
	}
   /**
	 * �ۼ���: jkkoo �ۼ���: 2006-05-03 �� ��: �� Ÿ:
	 */
	public void deleteList(String pkList) throws Exception
	{
	}
   /**
	 * �ۼ���: shhwang �ۼ���: 2006-05-03 �� ��: �� Ÿ:
	 */
	public void deleteList(Object pkList, String subMethodFlag) throws Exception
	{
		   Connection  conn  = null ;
			ArrayList alist = (ArrayList) pkList;
			try{
				if(alist.size() > 0 ) {
					conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
					if("GY020102".equals(subMethodFlag))  // 2006.07.08
						deleteRowForGY020102(alist, conn);             
				}
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}finally{
				DBUtil.close(null, null, conn);
			}      
	}
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-03
    * ��  ��: 
    * ��  Ÿ:
    */
	public void insert(Object vo, String subMethodFlag) throws Exception
	{
		Connection conn = null;
		ArrayList alist = (ArrayList) vo;
		try
		{
			if (alist.size() > 0)
			{
				conn = DBUtil.getConnection(DBConstants.EVLADMPOOL);
				if ("GY020102".equals(subMethodFlag)) // 2006.03.23 shwhang
					insertRowForGY020102(alist, conn);
				if ("GY020105".equals(subMethodFlag)) // 2006.05.02 shwhang
					insertRowForGY020105(alist, conn);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			DBUtil.close(null, null, conn);
		}
	}
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-03
    * ��  ��: 
    * ��  Ÿ:
    */
	public void update(Object obj) throws Exception
	{	
	}
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-03
    * ��  ��: 
    * ��  Ÿ:
    */
	public void update(Object vo, String subMethodFlag) throws Exception
	{
		Connection conn = null;
		ArrayList alist = (ArrayList) vo;
		try
		{
			if (alist.size() > 0)
			{
				conn = DBUtil.getConnection(DBConstants.EVLADMPOOL);
				if ("GY020102".equals(subMethodFlag)) // 2006.03.19 shwhang
					updateRowForGY020102(alist, conn);
				if ("GY020102_02".equals(subMethodFlag)) // 2006.03.30 shwhang
					updateRowForGY020102_02(alist, conn);
				if ("GY020105_02".equals(subMethodFlag)) // 2006.04.04 shwhang �ݼ۽� 
					updateRowForGY020105_02(alist, conn);
				if ("GY020105_03".equals(subMethodFlag)) // 2006.04.04 shwhang ������ 
					updateRowForGY020105_03(alist, conn);
				if ("GY020105_04".equals(subMethodFlag)) // 2006.08.25 shwhang ������ҽ� 
					updateRowForGY020105_04(alist, conn);
				if ("GY020105".equals(subMethodFlag)) // 2006.04.10 shwhang ���ν� 
					updateRowForGY020105(alist, conn);
				if ("GY030202_JA6".equals(subMethodFlag)) // 2006.04.21 mhcho ���к���� ���ν� 
					updateRowForGY030202_JA6(alist, conn);				
				if("GY090201".equals(subMethodFlag))  // 2006.08.14 jhlee
					updateRowForGY090201(alist, conn);
				if("GY090301".equals(subMethodFlag))  // 2006.08.16 jhlee
					updateRowForGY090301(alist, conn);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			DBUtil.close(null, null, conn);
		}
	}
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-03
    * ��  ��: 
    * ��  Ÿ:
    */
	public void updateRow(ArrayList list, Connection conn) throws Exception
	{
	}
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-03
    * ��  ��: 
    * ��  Ÿ:
    */
	public void insertRow(ArrayList alist, Connection conn) throws Exception
	{
	}
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-03
    * ��  ��: 
    * ��  Ÿ:
    */
	public void deleteRow(ArrayList alist, Connection conn) throws Exception
	{
	}
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-03
    * ��  ��: 
    * ��  Ÿ:
    */
	public int multiSaveForGrid(Object iobj, Object uobj, Object dobj) throws Exception
	{
		return 0;
	}
	
	/**
	* �ۼ��� : shhwang
	* �ۼ��� : 2006.03.27
	* ����    : ���󺸼�����  insert �Ѵ� . 
	* ��Ÿ    : 
	*/
	public void insertRowForGY020102(ArrayList list, Connection conn) throws Exception
	{
		
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = null;
		TBEBWMF1Vo vo = new TBEBWMF1Vo();
		try
		{
			//				��ϳ�¥, ��Ͻð��� �ý��� �ð����� �ޱ� ����..
			Date date = new Date();
			SimpleDateFormat formatDay = new SimpleDateFormat("yyyyMMdd");
			String updDate = formatDay.format(date); //���糯¥
			String CEBWMCGB = ""; //���⺯�汸�� 
			String str = ""; //�����Ϲ��϶� ������ ���� 
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWMF1Vo();
				vo = (TBEBWMF1Vo) list.get(i);				
				sqlBuff = new StringBuffer();				
				//----------------------------�÷��� 53�� 
				sqlBuff.append("INSERT  INTO  EVLADM.TBEBWMF1 (  \n");
				sqlBuff.append("	        CEBWMUPN ,	      	    \n");
				sqlBuff.append("	        CEBWMCST ,	      	    \n");
				sqlBuff.append("	        CEBWMPJT ,	      	    \n");
				sqlBuff.append("	        CEBWMHGB ,	      	    \n");
				sqlBuff.append("	        CEBWMHNO ,	      	    \n"); //CEBWMHNO
				sqlBuff.append("	        CEBWMSEQ ,	      	    \n"); //0331 �߰� ���� 					
				sqlBuff.append("	        CEBWMMNO ,	      	    \n"); //CEBWMMNO4�� 
				sqlBuff.append("	        CEBWMPST ,	      	    \n");
				sqlBuff.append("	        CEBWMTYP ,	      	    \n"); //
				sqlBuff.append("	        CEBWMHTY ,	      	    \n");
				sqlBuff.append("	        CEBWMARA ,	      	    \n"); //CEBWMARA
				sqlBuff.append("	        CEBWMBPM ,	      	    \n"); //0330�߰� 
				sqlBuff.append("	        CEBWMBSU ,	      	    \n"); //0330�߰�
				sqlBuff.append("	        CEBWMJUJ ,	      	    \n");
				sqlBuff.append("	        CEBWMBUJ ,	      	    \n");
				sqlBuff.append("	        CEBWMGND ,	      	    \n"); //������� �߰� 0331
				sqlBuff.append("	        CEBWMGKD ,	      	    \n"); //CEBWMGKD
				sqlBuff.append("	        CEBWMGYB ,	      	    \n");
				sqlBuff.append("	        CEBWMRGB ,	      	    \n"); //CEBWMRGB
				sqlBuff.append("	        CEBWMCGB ,	      	    \n"); //���⺯�汸�� 
				sqlBuff.append("	        CEBWMUSD ,	      	    \n");
				sqlBuff.append("	        CEBWMUGS ,	      	    \n");
				sqlBuff.append("	        CEBWMUMS ,	      	    \n");
				sqlBuff.append("	        CEBWMMMN ,	      	    \n"); //CEBWMMMN
				sqlBuff.append("	        CEBWMUMR ,	      	    \n"); //CEBWMUMR
				sqlBuff.append("	        CEBWMUHJ ,	      	    \n");
				sqlBuff.append("	        CEBWMAGB ,	      	    \n");
				sqlBuff.append("	        CEBWMABG ,	      	    \n");
				sqlBuff.append("	        CEBWMBGB ,	      	    \n"); //CEBWMBGB
				sqlBuff.append("	        CEBWMGBN ,	      	    \n"); //CEBWMGBN
				sqlBuff.append("	        CEBWMBJG ,	      	    \n");
				sqlBuff.append("	        CEBWMTIS ,	      	    \n");
				sqlBuff.append("	        CEBWMJKH ,	      	    \n");
				sqlBuff.append("	        CEBWMYYB ,	      	    \n"); //CEBWMYYB
				sqlBuff.append("	        CEBWMRMK ,	      	    \n"); //CEBWMRMK
				sqlBuff.append("	        CEBWMZER ,	      	    \n"); // ������ �Է� :���������뿩�� 
				sqlBuff.append("	        CEBWMWYB ,	      	    \n");
				sqlBuff.append("	        CEBWMAMT ,	      	    \n");
				sqlBuff.append("	        CEBWMVAT ,	      	    \n"); //CEBWMVAT
				sqlBuff.append("	        CEBWMTOT ,	      	    \n"); //
				sqlBuff.append("	        CEBWMKND ,	      	    \n"); //CEBWMKND
				sqlBuff.append("	        CEBWMRTO ,	      	    \n");
				sqlBuff.append("	        CEBWMIYN ,	      	    \n");
				sqlBuff.append("	        CEBWMGBM ,	      	    \n"); //CEBWMGBM
				sqlBuff.append("	        CEBWMBDT ,	      	    \n"); //-
				sqlBuff.append("	        CEBWMBSJ ,	      	    \n"); //-CEBWMBSJ
				sqlBuff.append("	        CEBWMRDT ,	      	    \n");
				sqlBuff.append("	        CEBWMREQ ,	      	    \n");
				sqlBuff.append("	        CEBWMSDT ,	      	    \n");
				sqlBuff.append("	        CEBWMAPP ,	      	    \n");
				sqlBuff.append("	        CEBWMCHA ,	      	    \n"); //CEBWMCHA
				sqlBuff.append("	        CEBWMCDT ,	      	    \n");
				sqlBuff.append("	        CEBWMWIL )	      	    \n");
				sqlBuff.append(" SELECT    '"+vo.getCebwmupn()+"',				 \n");
				sqlBuff.append(" 				 '"+vo.getCebwmcst()+"',				 \n");
				sqlBuff.append(" 				 '"+vo.getCebwmpjt()+"',				 \n");
				sqlBuff.append(" 				 '"+vo.getCebwmhgb()+"',				 \n");
				sqlBuff.append(" 				 '"+vo.getCebwmhno()+"',				 \n"); //CEBWMHNO				
				sqlBuff.append(" SUBSTR(DIGITS(CAST(COALESCE(MAX(CEBWMSEQ),'0') AS INTEGER)+1),9,2) ,  \n");//���� ���� 	
				sqlBuff.append(" 				 '"+vo.getCebwmmno()+"',				 \n");
				sqlBuff.append(" 				 '"+vo.getCebwmpst()+"',				 \n"); //getCebwmpst				
				sqlBuff.append(" 				 '"+vo.getCebwmtyp()+"',				 \n"); //CEBWMTYP
				sqlBuff.append(" 				 '"+vo.getCebwmhty()+"',				 \n"); //cebwmhty
				sqlBuff.append(" 				 '"+vo.getCebwmara()+"',				 \n"); //CEBWMARA
				sqlBuff.append(" 				 '"+vo.getCebwmbpm()+"',				 \n"); //
				sqlBuff.append(" 				 '"+vo.getCebwmbsu()+"',				 \n"); //				
				sqlBuff.append(" 				 '"+vo.getCebwmjuj()+"',				 \n"); //JUJ
				sqlBuff.append(" 				 '"+vo.getCebwmbuj()+"',				 \n"); //getCebwmbuj
				sqlBuff.append(" 				 '"+vo.getCebwmgnd()+"',				 \n"); //GND
				sqlBuff.append(" 				 '"+vo.getCebwmgkd()+"',				 \n"); //GKD
				sqlBuff.append(" 				 '"+vo.getCebwmgyb()+"',				 \n"); //CEBWMGYB
				sqlBuff.append(" 				 '"+vo.getCebwmrgb()+"',				 \n"); //CEBWMRGB
				sqlBuff.append(" 				 '"+vo.getCebwmcgb()+"',		 \n"); //CEBWMCGB ���⺯�汸�� 
				sqlBuff.append(" 				 '"+vo.getCebwmusd()+"',				 \n"); //Cebwmusd
				sqlBuff.append(" 				 '"+vo.getCebwmugs()+"',				 \n"); //getCebwmugs
				sqlBuff.append(" 				 "+vo.getCebwmums()+",				 \n"); //Cebwmums
				sqlBuff.append(" 				 "+vo.getCebwmmmn()+",				 \n"); //CEBWMMMN
				sqlBuff.append(" 				 '"+vo.getCebwmumr()+"',				 \n"); //CEBWMUMR
				sqlBuff.append(" 				 '"+vo.getCebwmuhj()+"',				 \n"); //UHJ
				sqlBuff.append(" 				 '"+vo.getCebwmagb()+"',				 \n"); //getCebwmagb
				sqlBuff.append(" 				 '"+vo.getCebwmabg()+"',				 \n"); //ABG
				sqlBuff.append(" 				 '"+vo.getCebwmbgb()+"',				 \n"); //CEBWMBGB
				sqlBuff.append(" 				 '"+vo.getCebwmgbn()+"',				 \n"); //CEBWMGBN
				sqlBuff.append(" 				 '"+vo.getCebwmbjg()+"',				 \n");
				sqlBuff.append(" 				 '"+vo.getCebwmtis()+"',				 \n"); //getCebwmtis
				sqlBuff.append(" 				 "+vo.getCebwmjkh()+",				 \n");
				sqlBuff.append(" 				 '"+vo.getCebwmyyb()+"',				 \n"); //CEBWMYYB
				sqlBuff.append(" 				 '"+vo.getCebwmrmk()+"',				 \n"); //CEBWMRMK				
				sqlBuff.append(" 				 '"+vo.getCebwmzer()+"',				 \n"); //CEBWMZER ���������뿩�� 
				sqlBuff.append(" 				 '"+vo.getCebwmwyb()+"',				 \n"); //CEBWMWYB
				sqlBuff.append(" 				 "+vo.getCebwmamt()+",				 \n"); //CEBWMAMT
				sqlBuff.append(" 				 "+vo.getCebwmvat()+",				 \n"); //CEBWMVAT
				sqlBuff.append(" 				 "+vo.getCebwmtot()+",				 \n"); //CEBWMTOT
				sqlBuff.append(" 				 '"+vo.getCebwmknd()+"',				 \n"); //CEBWMKND
				sqlBuff.append(" 				 "+vo.getCebwmrto()+",				 \n"); //				
				sqlBuff.append(" 				 '"+vo.getCebwmiyn()+"',				 \n"); //CEBWMIYN
				sqlBuff.append(" 				 '"+vo.getCebwmgbm()+"',				 \n"); //CEBWMGBM
				sqlBuff.append(" 				 '"+vo.getCebwmbdt()+"',				 \n"); //CEBWMBDT
				sqlBuff.append(" 				 '"+vo.getCebwmbsj()+"',				 \n"); //CEBWMBSJ
				sqlBuff.append(" 				 '"+vo.getCebwmrdt()+"',				 \n"); //CEBWMRDT	
				sqlBuff.append(" 				 '"+vo.getCebwmreq()+"',				 \n"); //��������� 											
				sqlBuff.append(" 				 '"+vo.getCebwmsdt()+"',				 \n"); //CEBWMSDT
				sqlBuff.append(" 				 '"+vo.getCebwmapp()+"',				 \n"); //CEBWMAPP
				sqlBuff.append(" 				 '"+vo.getCebwmcha()+"',				 \n"); //CEBWMCHA
				sqlBuff.append(" 				 '"+vo.getCebwmcdt()+"',				 \n"); //CEBWMCDT
				sqlBuff.append(" 				 '"+vo.getCebwmwil()+"'				 \n"); //CEBWMWIL
				sqlBuff.append(" 	FROM EVLADM.TBEBWMF1   \n");
				sqlBuff.append(" 	WHERE CEBWMUPN=  '"+ vo.getCebwmupn()+ "'  \n"); 
				sqlBuff.append("  AND CEBWMCST =  '"+ vo.getCebwmcst()+ "'  \n");
				sqlBuff.append("  AND CEBWMPJT = '"+ vo.getCebwmpjt()+ "'   \n");
				sqlBuff.append("  AND CEBWMHGB = '"+ vo.getCebwmhgb()+ "'  \n");
				sqlBuff.append("  AND CEBWMHNO = '"+ vo.getCebwmhno()+ "'   \n");					
				//------------------------------------
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
								
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				pstmt.executeUpdate();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	
	/**
	* �ۼ��� : shhwang
	* �ۼ��� : 2006.03.27
	* ����    : �ڵ������ ���󺸼�����  insert �Ѵ� .
	* ��Ÿ    : 
	*/
	public void insertRowForGY020105(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = null;
		TBEBWMF1Vo vo = new TBEBWMF1Vo();
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				sqlBuff = new StringBuffer();
				vo = new TBEBWMF1Vo();
				vo = (TBEBWMF1Vo) list.get(i);
				//bwm �Է�Į������ 49�� (�����ؾߵ� �Ű����� 45�� )
				sqlBuff = new StringBuffer();
				sqlBuff.append("INSERT  INTO  EVLADM.TBEBWMF1 (  \n");
				sqlBuff.append("	        CEBWMUPN ,	      	    \n");
				sqlBuff.append("	        CEBWMCST ,	      	    \n");
				sqlBuff.append("	        CEBWMPJT ,	      	    \n");
				sqlBuff.append("	        CEBWMHGB ,	      	    \n");
				sqlBuff.append("	        CEBWMHNO ,	      	    \n"); //CEBWMHNO
				sqlBuff.append("	        CEBWMSEQ ,	      	    \n"); //0331 �߰� ���� 					
				sqlBuff.append("	        CEBWMMNO ,	      	    \n"); //CEBWMMNO4�� 
				sqlBuff.append("	        CEBWMPST ,	      	    \n");
				sqlBuff.append("	        CEBWMTYP ,	      	    \n"); //
				sqlBuff.append("	        CEBWMHTY ,	      	    \n");
				sqlBuff.append("	        CEBWMARA ,	      	    \n"); //CEBWMARA
				sqlBuff.append("	        CEBWMBPM ,	      	    \n"); //0330�߰� 
				sqlBuff.append("	        CEBWMBSU ,	      	    \n"); //0330�߰�
				sqlBuff.append("	        CEBWMJUJ ,	      	    \n");
				sqlBuff.append("	        CEBWMBUJ ,	      	    \n");
				sqlBuff.append("	        CEBWMGND ,	      	    \n"); //������� �߰� 0331
				sqlBuff.append("	        CEBWMGKD ,	      	    \n"); //CEBWMGKD
				sqlBuff.append("	        CEBWMGYB ,	      	    \n");
				sqlBuff.append("	        CEBWMRGB ,	      	    \n"); //CEBWMRGB
				sqlBuff.append("	        CEBWMCGB ,	      	    \n"); //���⺯�汸�� 
				sqlBuff.append("	        CEBWMUSD ,	      	    \n");
				sqlBuff.append("	        CEBWMUGS ,	      	    \n");
				sqlBuff.append("	        CEBWMUMS ,	      	    \n");
				sqlBuff.append("	        CEBWMMMN ,	      	    \n"); //CEBWMMMN
				sqlBuff.append("	        CEBWMUMR ,	      	    \n"); //CEBWMUMR
				sqlBuff.append("	        CEBWMUHJ ,	      	    \n");
				sqlBuff.append("	        CEBWMAGB ,	      	    \n");
				sqlBuff.append("	        CEBWMABG ,	      	    \n");
				sqlBuff.append("	        CEBWMBGB ,	      	    \n"); //CEBWMBGB
				sqlBuff.append("	        CEBWMGBN ,	      	    \n"); //CEBWMGBN
				sqlBuff.append("	        CEBWMBJG ,	      	    \n");
				sqlBuff.append("	        CEBWMTIS ,	      	    \n");
				sqlBuff.append("	        CEBWMJKH ,	      	    \n");
				sqlBuff.append("	        CEBWMYYB ,	      	    \n"); //CEBWMYYB
				sqlBuff.append("	        CEBWMRMK ,	      	    \n"); //CEBWMRMK
				sqlBuff.append("	        CEBWMZER ,	      	    \n"); // ������ �Է� :���������뿩�� 
				sqlBuff.append("	        CEBWMWYB ,	      	    \n");
				sqlBuff.append("	        CEBWMAMT ,	      	    \n");
				sqlBuff.append("	        CEBWMVAT ,	      	    \n"); //CEBWMVAT
				sqlBuff.append("	        CEBWMTOT ,	      	    \n"); //
				sqlBuff.append("	        CEBWMKND ,	      	    \n"); //CEBWMKND
				sqlBuff.append("	        CEBWMRTO ,	      	    \n");
				sqlBuff.append("	        CEBWMIYN ,	      	    \n");
				sqlBuff.append("	        CEBWMGBM ,	      	    \n"); //CEBWMGBM
				sqlBuff.append("	        CEBWMBDT ,	      	    \n"); //-
				sqlBuff.append("	        CEBWMBSJ ,	      	    \n"); //-CEBWMBSJ
				sqlBuff.append("	        CEBWMRDT ,	      	    \n");
				sqlBuff.append("	        CEBWMREQ ,	      	    \n");
				sqlBuff.append("	        CEBWMSDT ,	      	    \n");
				sqlBuff.append("	        CEBWMAPP ,	      	    \n");
				sqlBuff.append("	        CEBWMCHA ,	      	    \n"); //CEBWMCHA
				sqlBuff.append("	        CEBWMCDT ,	      	    \n");
				sqlBuff.append("	        CEBWMWIL )	      	    \n");
				sqlBuff.append("	SELECT  CEBWMUPN ,	      	    \n");
				sqlBuff.append("	        		   CEBWMCST ,	      	    \n");
				sqlBuff.append("	        			CEBWMPJT ,	      	    \n");
				sqlBuff.append("	        			CEBWMHGB ,	      	    \n");
				sqlBuff.append("	        			CEBWMHNO ,	      	    \n"); //CEBWMHNO
				sqlBuff.append(" '" + vo.getExtseq() + "' , \n"); //�ڵ������ ����   
				sqlBuff.append(" '" + vo.getCebwmmno() + "'  ,  \n"); //CEBWMMNO 
				sqlBuff.append("	        CEBWMPST ,	      	    \n");
				sqlBuff.append("	        CEBWMTYP ,	      	    \n"); //
				sqlBuff.append("	        '" + vo.getCebwmhty() + "'  ,	      	    \n");//��ӱ������� : �ڵ������ �ٽ� ��ȸ�ؼ� ������ �� 
				sqlBuff.append("	        CEBWMARA ,	      	    \n"); //CEBWMARA
				sqlBuff.append("	        CEBWMBPM ,	      	    \n"); //0330�߰� 
				sqlBuff.append("	        CEBWMBSU ,	      	    \n"); //0330�߰�
				sqlBuff.append("	        CEBWMJUJ ,	      	    \n");
				sqlBuff.append("	        CEBWMBUJ ,	      	    \n");
				sqlBuff.append("	        'D' ,	      	    \n"); //�ڵ������ CEBWMGND�� 'D'
				sqlBuff.append("	        '2' ,	      	    \n"); //CEBWMGKD : ���� 
				sqlBuff.append("	        'Y' ,	      	    \n");//�ڵ���࿩�� 
				sqlBuff.append("	        CEBWMRGB ,	      	    \n"); //CEBWMRGB
				sqlBuff.append("	        'G' ,	      	    \n"); //���⺯�汸�� 
				sqlBuff.append("	        '" + vo.getCebwmusd() + "'  ,	      	    \n");//������ 
				sqlBuff.append("	        '" + vo.getCebwmugs() + "'  ,	      	    \n"); //���󰳽��� 
				sqlBuff.append("	         " + vo.getCebwmums() + ",	      	    \n");//CEBWMUMS �����ళ���� 
				sqlBuff.append("	        0 ,	      	    \n"); //CEBWMMMN : ���󼭺񽺰����� 
				sqlBuff.append("	         '" + vo.getCebwmumr() + "'  ,	      	    \n"); //���󰳽� ������ 
				sqlBuff.append("	         '" + vo.getCebwmuhj() + "' ,	      	    \n");
				sqlBuff.append("	        CEBWMAGB ,	      	    \n");
				sqlBuff.append("	        CEBWMABG ,	      	    \n");
				sqlBuff.append("	        CEBWMBGB ,	      	    \n"); //CEBWMBGB
				sqlBuff.append("	        CEBWMGBN ,	      	    \n"); //CEBWMGBN
				sqlBuff.append("	        CEBWMBJG ,	      	    \n");
				sqlBuff.append("	        CEBWMTIS ,	      	    \n");
				sqlBuff.append("	        CEBWMJKH ,	      	    \n");
				sqlBuff.append("	        CEBWMYYB ,	      	    \n"); //CEBWMYYB
				sqlBuff.append("	        CEBWMRMK ,	      	    \n"); //CEBWMRMK
				sqlBuff.append("	        CEBWMZER ,	      	    \n"); // ������ �Է� :���������뿩�� 
				sqlBuff.append("	        CEBWMWYB ,	      	    \n");
				sqlBuff.append("	        CEBWMAMT ,	      	    \n");
				sqlBuff.append("	        CEBWMVAT ,	      	    \n"); //CEBWMVAT
				sqlBuff.append("	        CEBWMTOT ,	      	    \n"); //
				sqlBuff.append("	        CEBWMKND ,	      	    \n"); //CEBWMKND
				sqlBuff.append("	        CEBWMRTO ,	      	    \n");
				sqlBuff.append("	        CEBWMIYN ,	      	    \n");
				sqlBuff.append("	         '" + vo.getCebwmgbm() + "' ,	\n"); //CEBWMGBM
				sqlBuff.append("	        CEBWMBDT ,	      	    \n"); //-
				sqlBuff.append("	        CEBWMBSJ ,	      	    \n"); //-CEBWMBSJ
				sqlBuff.append("	        CEBWMRDT ,	      	    \n");
				sqlBuff.append("	        CEBWMREQ ,	      	    \n");
				sqlBuff.append("	        '" + vo.getCebwmsdt() + "'  ,	\n");//CEBWMSDT
				sqlBuff.append("	         '" + vo.getCebwmapp() + "' ,	\n");//CEBWMAPP
				sqlBuff.append("	        CEBWMCHA ,	      	    \n"); //CEBWMCHA
				sqlBuff.append("	        CEBWMCDT ,	      	    \n");
				sqlBuff.append("	        CEBWMWIL 	      	    \n");
				sqlBuff.append("FROM  EVLADM.TBEBWMF1			 \n");
				sqlBuff.append("WHERE  CEBWMUPN   =	?			 \n");
				sqlBuff.append("AND  CEBWMCST   =	?					 \n");
				sqlBuff.append("AND  CEBWMPJT   =	?					 \n");
				sqlBuff.append("AND  CEBWMHGB   =	?					 \n");
				sqlBuff.append("AND  CEBWMHNO   =	?					 \n");
				sqlBuff.append("AND  CEBWMSEQ   =	?					 \n");
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				pstmt.setString(idxparam++, vo.getCebwmupn()); //               
				pstmt.setString(idxparam++, vo.getCebwmcst()); //
				pstmt.setString(idxparam++, vo.getCebwmpjt()); //
				pstmt.setString(idxparam++, vo.getCebwmhgb()); //
				pstmt.setString(idxparam++, vo.getCebwmhno()); //
				pstmt.setString(idxparam++, vo.getCebwmseq()); //
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				pstmt.executeUpdate();				
			}
			
			  //			�⼺�� ����
			 ComMethodVo comVo;
			 comVo = new ComMethodVo();			
			 comVo.setDataId("37");//�ڵ����� 			
			 comVo.setJobGubun("I");//�۾�����		 
			 comVo.setUpn(vo.getCebwmupn());//����������Ʈ��ȣ
			 comVo.setCst(vo.getCebwmcst());//�ŷ�ó
			 comVo.setPjt(vo.getCebwmpjt());
			 comVo.setHgb(vo.getCebwmhgb());
			 comVo.setHno(vo.getCebwmhno());
			 comVo.setSeq(vo.getExtseq());// �ڵ������� max+1 �� ��ȸ�� seq�� �־��� ;vo.getCebwmseq()
			 comVo.setFirstUserId(vo.getCebwmapp());//������ 

//			 ComMethodDao.SetBXRBXL(comVo);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	/**
	* �ۼ��� : shhwang
	* �ۼ��� : 2006.03.08
	* ����    : ���󺸼� �ű� ���� + �߼۽�  update �Ѵ� . ���¸� 
	* ��Ÿ    : 
	*/
	public void updateRowForGY020102(ArrayList list, Connection conn) throws Exception
	{
		
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWMF1Vo vo = new TBEBWMF1Vo();
		try
		{
			sqlBuff.append("UPDATE EVLADM.TBEBWMF1  \n");
			sqlBuff.append("SET    CEBWMPST   = ? ,             \n"); //
			sqlBuff.append("          CEBWMBDT   = ?  ,            \n"); // 
			sqlBuff.append("          CEBWMBSJ   = ?              \n"); // 
			sqlBuff.append("WHERE  CEBWMUPN   =	?					 \n");
			sqlBuff.append("AND  CEBWMCST   =	?					 \n");
			sqlBuff.append("AND  CEBWMPJT   =	?					 \n");
			sqlBuff.append("AND  CEBWMHGB   =	?					 \n");
			sqlBuff.append("AND  CEBWMHNO   =	?					 \n");
			sqlBuff.append("AND  CEBWMSEQ   =	?					 \n");
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWMF1Vo();
				vo = (TBEBWMF1Vo) list.get(i);
				pstmt.setString(idxparam++, vo.getCebwmpst());
				pstmt.setString(idxparam++, vo.getCebwmbdt());
				pstmt.setString(idxparam++, vo.getCebwmbsj());
				pstmt.setString(idxparam++, vo.getCebwmupn());
				pstmt.setString(idxparam++, vo.getCebwmcst());
				pstmt.setString(idxparam++, vo.getCebwmpjt());
				pstmt.setString(idxparam++, vo.getCebwmhgb());
				pstmt.setString(idxparam++, vo.getCebwmhno());
				pstmt.setString(idxparam++, vo.getCebwmseq());
				pstmt.addBatch();
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			}
			pstmt.executeBatch();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	/**
	* �ۼ��� : shhwang
	* �ۼ��� : 2006.03.08
	* ����    : ���󺸼� ���� + �����  update �Ѵ� . :���� ���� 
	* ��Ÿ    : 
	*/
	public void updateRowForGY020102_02(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWMF1Vo vo = new TBEBWMF1Vo();
		try
		{
			sqlBuff.append("UPDATE EVLADM.TBEBWMF1  \n");
			sqlBuff.append("SET CEBWMHTY	= ?,   \n"); // 						
			sqlBuff.append("CEBWMUSD= ?,   \n"); // 
			sqlBuff.append("CEBWMUGS= ?,   \n"); // 
			sqlBuff.append("CEBWMUMR= ?,   \n"); // 
			sqlBuff.append("CEBWMUHJ= ?,   \n"); //
			sqlBuff.append("CEBWMRGB= ?,   \n"); // 
			sqlBuff.append("CEBWMAMT= ?,   \n"); // 
			sqlBuff.append("CEBWMVAT= ?,   \n"); // 
			sqlBuff.append("CEBWMTOT= ?,   \n"); // 
			sqlBuff.append("CEBWMKND= ?,	   \n"); // 
			sqlBuff.append("CEBWMIYN= ?,   \n"); // 
			sqlBuff.append("CEBWMJUJ= ?,   \n"); // 
			sqlBuff.append("CEBWMBUJ= ?,	   \n"); // 	
			sqlBuff.append("CEBWMGND= ?,   \n"); //				
			sqlBuff.append("CEBWMGKD= ?,   \n"); // 		
			sqlBuff.append("CEBWMBGB= ?,   \n"); // 	
			sqlBuff.append("CEBWMBJG= ?,	   \n"); // 
			sqlBuff.append("CEBWMTIS= ?,   \n"); // 	
			sqlBuff.append("CEBWMJKH= ?,   \n"); // 	
			sqlBuff.append("CEBWMYYB= ?,	   \n"); // 	
			sqlBuff.append("CEBWMRMK= ?,   \n"); // 	
			sqlBuff.append("CEBWMZER= ? 		   \n"); // 						
			sqlBuff.append("WHERE  CEBWMUPN   =	?					 \n");
			sqlBuff.append("AND  CEBWMCST   =	?					 \n");
			sqlBuff.append("AND  CEBWMPJT   =	?					 \n");
			sqlBuff.append("AND  CEBWMHGB   =	?					 \n");
			sqlBuff.append("AND  CEBWMHNO   =	?					 \n");
			sqlBuff.append("AND  CEBWMSEQ   =	?					 \n");
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWMF1Vo();
				vo = (TBEBWMF1Vo) list.get(i);
				pstmt.setString(idxparam++, vo.getCebwmhty());
				pstmt.setString(idxparam++, vo.getCebwmusd());
				pstmt.setString(idxparam++, vo.getCebwmugs());
				pstmt.setString(idxparam++, vo.getCebwmumr());
				pstmt.setString(idxparam++, vo.getCebwmuhj());
				pstmt.setString(idxparam++, vo.getCebwmrgb());								
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwmamt())); //
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwmvat())); //
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwmtot())); //								
				pstmt.setString(idxparam++, vo.getCebwmknd());
				pstmt.setString(idxparam++, vo.getCebwmiyn());
				pstmt.setString(idxparam++, vo.getCebwmjuj());
				pstmt.setString(idxparam++, vo.getCebwmbuj());
				pstmt.setString(idxparam++, vo.getCebwmgnd());
				pstmt.setString(idxparam++, vo.getCebwmgkd());
				pstmt.setString(idxparam++, vo.getCebwmbgb());
				pstmt.setString(idxparam++, vo.getCebwmbjg());
				pstmt.setString(idxparam++, vo.getCebwmtis());
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwmjkh()));
				pstmt.setString(idxparam++, vo.getCebwmyyb());
				pstmt.setString(idxparam++, vo.getCebwmrmk());
				pstmt.setString(idxparam++, vo.getCebwmzer()); //
				pstmt.setString(idxparam++, vo.getCebwmupn());
				pstmt.setString(idxparam++, vo.getCebwmcst());
				pstmt.setString(idxparam++, vo.getCebwmpjt());
				pstmt.setString(idxparam++, vo.getCebwmhgb());
				pstmt.setString(idxparam++, vo.getCebwmhno());
				pstmt.setString(idxparam++, vo.getCebwmseq());
				pstmt.executeUpdate();
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	/**
	* �ۼ��� : shhwang
	* �ۼ��� : 2006.04.04
	* ����    : ��������� �ݼ۽�   update �Ѵ� . ���¸� 
	* ��Ÿ    : 
	*/
	public void updateRowForGY020105_02(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWMF1Vo vo = new TBEBWMF1Vo();
		try
		{
			sqlBuff.append("UPDATE EVLADM.TBEBWMF1  \n");
			sqlBuff.append("SET    CEBWMPST   = ?              \n"); // 
			sqlBuff.append("WHERE  CEBWMUPN   =	?					 \n");
			sqlBuff.append("AND  CEBWMCST   =	?					 \n");
			sqlBuff.append("AND  CEBWMPJT   =	?					 \n");
			sqlBuff.append("AND  CEBWMHGB   =	?					 \n");
			sqlBuff.append("AND  CEBWMHNO   =	?					 \n");
			sqlBuff.append("AND  CEBWMSEQ   =	?					 \n");
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWMF1Vo();
				vo = (TBEBWMF1Vo) list.get(i);
				pstmt.setString(idxparam++, vo.getCebwmpst());
				pstmt.setString(idxparam++, vo.getCebwmupn());
				pstmt.setString(idxparam++, vo.getCebwmcst());
				pstmt.setString(idxparam++, vo.getCebwmpjt());
				pstmt.setString(idxparam++, vo.getCebwmhgb());
				pstmt.setString(idxparam++, vo.getCebwmhno());
				pstmt.setString(idxparam++, vo.getCebwmseq());
				pstmt.addBatch();
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			}
			pstmt.executeBatch();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	/**
	* �ۼ��� : shhwang
	* �ۼ��� : 2006.04.04
	* ����    : ��������� ������   update �Ѵ�  
	* ��Ÿ    : 
	*/
	public void updateRowForGY020105_03(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = null;
		TBEBWMF1Vo vo = new TBEBWMF1Vo();
		try
		{
			   
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWMF1Vo();
				vo = (TBEBWMF1Vo) list.get(i);
				
				sqlBuff = new StringBuffer();				
								
				sqlBuff.append("UPDATE EVLADM.TBEBWMF1  \n");
				sqlBuff.append("SET CEBWMPST	= ?,   \n"); //	
				sqlBuff.append("CEBWMTYP	= ?,   \n"); //								 
				sqlBuff.append("CEBWMHTY	=  ?,   \n"); //						CEBWMHTY : ��ӱ�������
				sqlBuff.append("CEBWMARA	= ?,   \n"); //
				sqlBuff.append("CEBWMBPM	= ? ,   \n");
				sqlBuff.append("CEBWMBSU	= ?  ,   \n");			
				sqlBuff.append("CEBWMJUJ= ?,   \n"); // 
				sqlBuff.append("CEBWMBUJ= ?,	   \n"); // 
				sqlBuff.append("CEBWMGND= ?,   \n"); // 
				sqlBuff.append("CEBWMGKD= ?,   \n"); // 	
				sqlBuff.append("CEBWMGYB= ?,   \n"); //
				sqlBuff.append("CEBWMRGB= ?,   \n"); // 
				sqlBuff.append("CEBWMCGB= ?,   \n"); //
				sqlBuff.append("CEBWMUSD= ?,   \n"); // 
				sqlBuff.append("CEBWMUGS= ?,   \n"); // 
				sqlBuff.append("CEBWMUMS= ?,   \n"); // 
				sqlBuff.append("CEBWMMMN= ?,   \n"); //
				sqlBuff.append("CEBWMUMR= ?,   \n"); // 					
				sqlBuff.append("CEBWMUHJ= ?,   \n"); // 
				sqlBuff.append("CEBWMAGB= ?,   \n"); // 
				sqlBuff.append("CEBWMABG= ?,   \n"); // 
				sqlBuff.append("CEBWMBGB= ?,   \n"); // 
				sqlBuff.append("CEBWMBJG= ?,   \n"); //					
				sqlBuff.append("CEBWMTIS= ?,   \n"); // 
				sqlBuff.append("CEBWMJKH= ?,   \n"); // 
				sqlBuff.append("CEBWMYYB= ?,   \n"); // 
				sqlBuff.append("CEBWMRMK= ?,   \n"); // 
				sqlBuff.append("CEBWMZER= ?,   \n"); //					
				sqlBuff.append("CEBWMWYB= ?,   \n"); // 
				sqlBuff.append("CEBWMAMT= ?,   \n"); // 
				sqlBuff.append("CEBWMVAT= ?,   \n"); // 
				sqlBuff.append("CEBWMTOT= ?,   \n"); // 
				sqlBuff.append("CEBWMKND= ?,   \n"); // 
				sqlBuff.append("CEBWMRTO=  " + vo.getCebwmrto() + ",   \n"); //			
				sqlBuff.append("CEBWMIYN= ?,   \n"); // 
				sqlBuff.append("CEBWMRDT= ?,   \n"); // �������� 
				sqlBuff.append("CEBWMREQ= ?,   \n"); // ������  
				sqlBuff.append("CEBWMGBM= ? \n"); //
				sqlBuff.append("WHERE  CEBWMUPN   =	?			 \n");
				sqlBuff.append("AND  CEBWMCST   =	?					 \n");
				sqlBuff.append("AND  CEBWMPJT   =	?					 \n");
				sqlBuff.append("AND  CEBWMHGB   =	?					 \n");
				sqlBuff.append("AND  CEBWMHNO   =	?					 \n");
				sqlBuff.append("AND  CEBWMSEQ   =	?					 \n");

				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
	
				pstmt.setString(idxparam++, vo.getCebwmpst());
				pstmt.setString(idxparam++, vo.getCebwmtyp());
				pstmt.setString(idxparam++, vo.getCebwmhty());
				pstmt.setString(idxparam++, vo.getCebwmara());
				pstmt.setString(idxparam++, vo.getCebwmbpm());//
				pstmt.setString(idxparam++, vo.getCebwmbsu());//
				pstmt.setString(idxparam++, vo.getCebwmjuj());
				pstmt.setString(idxparam++, vo.getCebwmbuj());
				pstmt.setString(idxparam++, vo.getCebwmgnd());
				pstmt.setString(idxparam++, vo.getCebwmgkd());
				pstmt.setString(idxparam++, vo.getCebwmgyb()); //�ڵ���࿩�� 
				pstmt.setString(idxparam++, vo.getCebwmrgb());
				pstmt.setString(idxparam++, vo.getCebwmcgb()); //���⺯�汸�� 
				pstmt.setString(idxparam++, vo.getCebwmusd());
				pstmt.setString(idxparam++, vo.getCebwmugs());
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwmums())); //
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwmmmn())); // 
				pstmt.setString(idxparam++, vo.getCebwmumr());
				pstmt.setString(idxparam++, vo.getCebwmuhj());
				pstmt.setString(idxparam++, vo.getCebwmagb());
				pstmt.setString(idxparam++, vo.getCebwmabg());
				pstmt.setString(idxparam++, vo.getCebwmbgb());
				pstmt.setString(idxparam++, vo.getCebwmbjg());
				pstmt.setString(idxparam++, vo.getCebwmtis());
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwmjkh())); //
				pstmt.setString(idxparam++, vo.getCebwmyyb());
				pstmt.setString(idxparam++, vo.getCebwmrmk());
				pstmt.setString(idxparam++, vo.getCebwmzer());
				pstmt.setString(idxparam++, vo.getCebwmwyb());
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwmamt())); //
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwmvat())); //
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwmtot())); //
				pstmt.setString(idxparam++, vo.getCebwmknd());
				//pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwmrto())); // rto�� bigdecimal�� �ϸ� integer�� �Ѿ�°��̱⶧���� ������ 
				pstmt.setString(idxparam++, vo.getCebwmiyn());	
				pstmt.setString(idxparam++, vo.getCebwmrdt());
				pstmt.setString(idxparam++, vo.getCebwmreq());	
				pstmt.setString(idxparam++, vo.getCebwmgbm());		
				
				pstmt.setString(idxparam++, vo.getCebwmupn());
				pstmt.setString(idxparam++, vo.getCebwmcst());
				pstmt.setString(idxparam++, vo.getCebwmpjt());
				pstmt.setString(idxparam++, vo.getCebwmhgb());
				pstmt.setString(idxparam++, vo.getCebwmhno());
				pstmt.setString(idxparam++, vo.getCebwmseq());
				
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				pstmt.executeUpdate();
				
			}
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	
	/**
		* �ۼ��� : shhwang
		* �ۼ��� : 2006.08.25
		* ����    : ��������� ������� 
		* ��Ÿ    : 
		*/
		public void updateRowForGY020105_04(ArrayList list, Connection conn) throws Exception
		{
			LoggablePreparedStatement pstmt = null;
			StringBuffer sqlBuff = new StringBuffer();
			TBEBWMF1Vo vo = new TBEBWMF1Vo();
			try
			{
				sqlBuff.append("UPDATE EVLADM.TBEBWMF1  \n");
				sqlBuff.append("SET    CEBWMPST   = ? ,             \n"); // 
				sqlBuff.append("          CEBWMSDT   = ? ,             \n"); // 
				sqlBuff.append("          CEBWMAPP   = ? ,             \n"); // 
				sqlBuff.append("          CEBWMCHA   = ? ,             \n"); // 
				sqlBuff.append("          CEBWMCDT   = ?              \n"); // 
				sqlBuff.append("WHERE  CEBWMUPN   =	?					 \n");
				sqlBuff.append("AND  CEBWMCST   =	?					 \n");
				sqlBuff.append("AND  CEBWMPJT   =	?					 \n");
				sqlBuff.append("AND  CEBWMHGB   =	?					 \n");
				sqlBuff.append("AND  CEBWMHNO   =	?					 \n");
				sqlBuff.append("AND  CEBWMSEQ   =	?					 \n");
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				for (int i = 0; i < list.size(); i++)
				{
					int idxparam = 1;
					vo = new TBEBWMF1Vo();
					vo = (TBEBWMF1Vo) list.get(i);
					pstmt.setString(idxparam++, vo.getCebwmpst());
					pstmt.setString(idxparam++, vo.getCebwmsdt());
					pstmt.setString(idxparam++, vo.getCebwmapp());
					pstmt.setString(idxparam++, vo.getCebwmcha());
					pstmt.setString(idxparam++, vo.getCebwmcdt());
					pstmt.setString(idxparam++, vo.getCebwmupn());
					pstmt.setString(idxparam++, vo.getCebwmcst());
					pstmt.setString(idxparam++, vo.getCebwmpjt());
					pstmt.setString(idxparam++, vo.getCebwmhgb());
					pstmt.setString(idxparam++, vo.getCebwmhno());
					pstmt.setString(idxparam++, vo.getCebwmseq());
					pstmt.addBatch();
					// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				}
				pstmt.executeBatch();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
			finally
			{
				try
				{
					if (pstmt != null)
						pstmt.close();
				}
				catch (Exception e)
				{
				}
			}
		}	
	
	/**
	* �ۼ��� : shhwang
	* �ۼ��� : 2006.04.10
	* ����    : ������  ���ν�   update �Ѵ� . 
	* ��Ÿ    : 
	*/
	public void updateRowForGY020105(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		LoggablePreparedStatement pstmt2 = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = null;
		StringBuffer sqlBuff2 = null;
		TBEBWMF1Vo vo = new TBEBWMF1Vo();
				
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWMF1Vo();
				vo = (TBEBWMF1Vo) list.get(i);
				
				if ("D".equals(vo.getCebwmgnd()))
				{//���������� D(�����Ϲ�)  #############################
					
					if ( "1".equals(vo.getCebwmgkd()))
					{//��౸���� �ű��ϰ��  #############################						 
						
						if (!"".equals(vo.getCebwmuhj1()))
						{
							sqlBuff = new StringBuffer();
							//������� ����, ������� �ڷᰡ ����� ����ó�� (������� �Է½� ���� ����ó��?)
							sqlBuff.append("UPDATE  EVLADM.TBEBWMF1      \n");
							sqlBuff.append("SET     CEBWMUHJ    = ?,     \n"); //1.
							sqlBuff.append("        CEBWMCHA    = ?,     \n"); //2.
							sqlBuff.append("        CEBWMCDT    = ?     \n"); //3.						
							sqlBuff.append("WHERE   CEBWMUPN    = ?      \n");
							sqlBuff.append("AND     CEBWMCST    = ?      \n");
							sqlBuff.append("AND     CEBWMPJT    = ?      \n");
							sqlBuff.append("AND     CEBWMHGB    = ?      \n");
							sqlBuff.append("AND     CEBWMHNO    = ?      \n");
							sqlBuff.append("AND     CEBWMGND    = 'C'      \n");
							sqlBuff.append("AND     CEBWMSEQ    = ?      \n");
							pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
							pstmt.setString(1, vo.getCebwmuhj1());//cebwmusd -1�� ��¥ 
							pstmt.setString(2, vo.getCebwmcha());
							pstmt.setString(3, vo.getCebwmcdt());
							pstmt.setString(4, vo.getCebwmupn());
							pstmt.setString(5, vo.getCebwmcst());
							pstmt.setString(6, vo.getCebwmpjt());
							pstmt.setString(7, vo.getCebwmhgb());
							pstmt.setString(8, vo.getCebwmhno());
							pstmt.setString(9, vo.getCebwmseq1());//������ ó���� ���������  max seq 
							// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
							pstmt.executeUpdate();
						}
					}
					
					if ( "3".equals(vo.getCebwmgkd()))
					{//��౸���� ��ຯ���ϰ��  ############################# 
						
						if (!"".equals(vo.getCebwmuhj1()))
						{
							sqlBuff = new StringBuffer();
							//'������' ���� ���
							sqlBuff.append("UPDATE  EVLADM.TBEBWMF1      \n");
							sqlBuff.append("SET     CEBWMUHJ    = ?,     \n"); //1.
							sqlBuff.append("        CEBWMCHA    = ?,     \n"); //2.
							sqlBuff.append("        CEBWMCDT    = ?     \n"); //3.						
							sqlBuff.append("WHERE   CEBWMUPN    = ?      \n");
							sqlBuff.append("AND     CEBWMCST    = ?      \n");
							sqlBuff.append("AND     CEBWMPJT    = ?      \n");
							sqlBuff.append("AND     CEBWMHGB    = ?      \n");
							sqlBuff.append("AND     CEBWMHNO    = ?      \n");
							sqlBuff.append("AND     CEBWMGND    = 'D'      \n");
							sqlBuff.append("AND  CEBWMSEQ   =	(SELECT SUBSTR(DIGITS(CAST(COALESCE(MAX(CEBWMSEQ),'0') AS INTEGER)-1),9,2)  \n");
							sqlBuff.append("FROM EVLADM.TBEBWMF1  \n");
							sqlBuff.append("WHERE CEBWMUPN   =	 '"+ vo.getCebwmupn()	+ "'  \n");
							sqlBuff.append("AND CEBWMCST = '" + vo.getCebwmcst()+ "'  \n");
							sqlBuff.append("AND CEBWMPJT   =	 '"+ vo.getCebwmpjt()+ "'  \n");
							sqlBuff.append("AND CEBWMHGB=  '"+ vo.getCebwmhgb()+ "'  \n");
							sqlBuff.append("AND CEBWMHNO = '"+ vo.getCebwmhno()+ "' )     \n");
							sqlBuff.append("AND CEBWMGND    = 'D'      \n");
		
							pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
							pstmt.setString(1, vo.getCebwmuhj1());//cebwmusd -1�� ��¥ 
							pstmt.setString(2, vo.getCebwmcha());
							pstmt.setString(3, vo.getCebwmcdt());
							pstmt.setString(4, vo.getCebwmupn());
							pstmt.setString(5, vo.getCebwmcst());
							pstmt.setString(6, vo.getCebwmpjt());
							pstmt.setString(7, vo.getCebwmhgb());
							pstmt.setString(8, vo.getCebwmhno());	
		
							// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
							pstmt.executeUpdate();
						}
					}//			

				}//���������� D(�����Ϲ�)  ############################# 
							
				//vo = (TBEBWMF1Vo) list.get(i);
				
				sqlBuff = new StringBuffer();
				sqlBuff.append("UPDATE EVLADM.TBEBWMF1  \n");
				sqlBuff.append("SET CEBWMPST	= ?,   \n"); //	
				sqlBuff.append("CEBWMTYP	= ?,   \n"); //								 
				sqlBuff.append("CEBWMHTY	=  '" + vo.getCebwmhty() + "',   \n"); //						CEBWMHTY : ��ӱ�������
				sqlBuff.append("CEBWMARA	= ?,   \n"); //
				sqlBuff.append("CEBWMBPM	= '"+ vo.getCebwmbpm()+ "'  ,   \n");
				sqlBuff.append("CEBWMBSU	= '"+ vo.getCebwmbsu()+ "'  ,   \n");			
				sqlBuff.append("CEBWMJUJ= ?,   \n"); // 
				sqlBuff.append("CEBWMBUJ= ?,	   \n"); // 
				sqlBuff.append("CEBWMGND= ?,   \n"); // 
				sqlBuff.append("CEBWMGKD= ?,   \n"); // 	
				sqlBuff.append("CEBWMGYB= ?,   \n"); //
				sqlBuff.append("CEBWMRGB= ?,   \n"); // 
				sqlBuff.append("CEBWMCGB= ?,   \n"); //
				sqlBuff.append("CEBWMUSD= ?,   \n"); // 
				sqlBuff.append("CEBWMUGS= ?,   \n"); // 
				sqlBuff.append("CEBWMUMS= ?,   \n"); // 
				sqlBuff.append("CEBWMMMN= ?,   \n"); //
				sqlBuff.append("CEBWMUMR= ?,   \n"); // 					
				sqlBuff.append("CEBWMUHJ= ?,   \n"); // 
				sqlBuff.append("CEBWMAGB= ?,   \n"); // 
				sqlBuff.append("CEBWMABG= ?,   \n"); // 
				sqlBuff.append("CEBWMBGB= ?,   \n"); // 
				sqlBuff.append("CEBWMBJG= ?,   \n"); //					
				sqlBuff.append("CEBWMTIS= ?,   \n"); // 
				sqlBuff.append("CEBWMJKH= ?,   \n"); // 
				sqlBuff.append("CEBWMYYB= ?,   \n"); // 
				sqlBuff.append("CEBWMRMK= ?,   \n"); // 
				sqlBuff.append("CEBWMZER= ?,   \n"); //					
				sqlBuff.append("CEBWMWYB= ?,   \n"); // 
				sqlBuff.append("CEBWMAMT= ?,   \n"); // 
				sqlBuff.append("CEBWMVAT= ?,   \n"); // 
				sqlBuff.append("CEBWMTOT= ?,   \n"); // 
				sqlBuff.append("CEBWMKND= ?,   \n"); // 
				sqlBuff.append("CEBWMRTO=  " + vo.getCebwmrto() + ",   \n"); //			
				sqlBuff.append("CEBWMIYN= ?,   \n"); // 
				if ("A5".equals(vo.getCebwmpst()))
				{ //����
				}
				else if ("A6".equals(vo.getCebwmpst()))
				{ //���� 
					sqlBuff.append("CEBWMSDT= ?,   \n"); // 
					sqlBuff.append("CEBWMAPP= ?,   \n"); // 	�������� 	
				}
				sqlBuff.append("CEBWMGBM=  '"+ vo.getCebwmgbm()+ "'  \n"); //
				sqlBuff.append("WHERE  CEBWMUPN   =	?			 \n");
				sqlBuff.append("AND  CEBWMCST   =	?					 \n");
				sqlBuff.append("AND  CEBWMPJT   =	?					 \n");
				sqlBuff.append("AND  CEBWMHGB   =	?					 \n");
				sqlBuff.append("AND  CEBWMHNO   =	?					 \n");
				sqlBuff.append("AND  CEBWMSEQ   =	?					 \n");
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				pstmt.setString(idxparam++, vo.getCebwmpst());
				pstmt.setString(idxparam++, vo.getCebwmtyp());
				//pstmt.setString(idxparam++, vo.getCebwmhty());//���� ���� ó�� 
				pstmt.setString(idxparam++, vo.getCebwmara());
				//pstmt.setString(idxparam++, vo.getCebwmbpm());//���� ���� ó�� 
				//pstmt.setString(idxparam++, vo.getCebwmbsu());//���� ���� ó�� 
				pstmt.setString(idxparam++, vo.getCebwmjuj());
				pstmt.setString(idxparam++, vo.getCebwmbuj());
				pstmt.setString(idxparam++, vo.getCebwmgnd());
				pstmt.setString(idxparam++, vo.getCebwmgkd());
				pstmt.setString(idxparam++, vo.getCebwmgyb()); //�ڵ���࿩�� 
				pstmt.setString(idxparam++, vo.getCebwmrgb());
				pstmt.setString(idxparam++, vo.getCebwmcgb()); //���⺯�汸�� 
				pstmt.setString(idxparam++, vo.getCebwmusd());
				pstmt.setString(idxparam++, vo.getCebwmugs());
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwmums())); //
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwmmmn())); // 
				pstmt.setString(idxparam++, vo.getCebwmumr());
				pstmt.setString(idxparam++, vo.getCebwmuhj());
				pstmt.setString(idxparam++, vo.getCebwmagb());
				pstmt.setString(idxparam++, vo.getCebwmabg());
				pstmt.setString(idxparam++, vo.getCebwmbgb());
				pstmt.setString(idxparam++, vo.getCebwmbjg());
				pstmt.setString(idxparam++, vo.getCebwmtis());
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwmjkh())); //
				pstmt.setString(idxparam++, vo.getCebwmyyb());
				pstmt.setString(idxparam++, vo.getCebwmrmk());
				pstmt.setString(idxparam++, vo.getCebwmzer());
				pstmt.setString(idxparam++, vo.getCebwmwyb());
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwmamt())); //
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwmvat())); //
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwmtot())); //
				pstmt.setString(idxparam++, vo.getCebwmknd());
				//pstmt.setString(idxparam++, vo.getCebwmrto());//�������� �ذ� 
				pstmt.setString(idxparam++, vo.getCebwmiyn());	
							
				if ("A5".equals(vo.getCebwmpst()))
				{ //���� 
				}
				else if ("A6".equals(vo.getCebwmpst()))
				{ //���� 
					pstmt.setString(idxparam++, vo.getCebwmsdt());
					pstmt.setString(idxparam++, vo.getCebwmapp());
				}
				pstmt.setString(idxparam++, vo.getCebwmupn());
				pstmt.setString(idxparam++, vo.getCebwmcst());
				pstmt.setString(idxparam++, vo.getCebwmpjt());
				pstmt.setString(idxparam++, vo.getCebwmhgb());
				pstmt.setString(idxparam++, vo.getCebwmhno());
				pstmt.setString(idxparam++, vo.getCebwmseq());
				
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				
				pstmt.executeUpdate();
				
			}//for 
			
			//�⼺�� ����
			ComMethodVo comVo;
			comVo = new ComMethodVo();
                  
			if("1".equals(vo.getCebwmgkd()) || "5".equals(vo.getCebwmgkd()))
				comVo.setDataId("31");//�ڷ�id �ű� ,Ÿ��ű� 
			else if("2".equals(vo.getCebwmgkd()))
				comVo.setDataId("32");//����
			else if("3".equals(vo.getCebwmgkd()))
				comVo.setDataId("34");//��ຯ��(��)
			else if("4".equals(vo.getCebwmgkd())){//����ȸ�� 
				if("3".equals(vo.getCebwmwil())){					
					comVo.setDataId("35");//Ÿ��ȸ�� 
				}	else{					
					comVo.setDataId("36");//�ڻ�ȸ�� 
				}
					
			}else if("5".equals(vo.getFlag())){//�ڵ�����
				comVo.setDataId("37");
			}else{
				throw new BizException("Data Id�� ���� ���� �ʾҽ��ϴ�");
			}

			if("3".equals(vo.getCebwmgkd()))   
				 comVo.setJobGubun("I/D");//�۾�����
			else
				 comVo.setJobGubun("I");//�۾�����
				 
			comVo.setUpn(vo.getCebwmupn());//����������Ʈ��ȣ
			comVo.setCst(vo.getCebwmcst());//�ŷ�ó
			comVo.setPjt(vo.getCebwmpjt());
			comVo.setHgb(vo.getCebwmhgb());
			comVo.setHno(vo.getCebwmhno());
			
			comVo.setSeq(vo.getCebwmseq());
			comVo.setFirstUserId(vo.getCebwmapp());//������ 

//			ComMethodDao.SetBXRBXL(comVo);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				
			}
			catch (Exception e)
			{
			}
		}
	}
	
	/**
	 * �ۼ��� : mhcho
	 * �ۼ��� : 2006.04.21
	 * ����   : ���к����(�������� - ����) update �Ѵ� . 
	 * ��Ÿ   : 
	 */
	public void updateRowForGY030202_JA6(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement   pstmt    = null;
		StringBuffer                sqlBuff  = new StringBuffer();
      LoggablePreparedStatement   pstmt1   = null;
      StringBuffer                sqlBuff1 = new StringBuffer();
		TBEBWMF1Vo                  vo       = new TBEBWMF1Vo();
      ComMethodVo                 comVo;   
		try
		{         
			sqlBuff.append("UPDATE EVLADM.TBEBWMF1  SET \n");
			sqlBuff.append("  CEBWMUHJ = ?,      \n"); // ����  
			sqlBuff.append("  CEBWMCHA = ?,      \n"); //     
			sqlBuff.append("  CEBWMCDT = ?       \n"); //          
			sqlBuff.append("WHERE   CEBWMUPN = ? \n"); // ����ProjNo        
         sqlBuff.append("AND     CEBWMCST = ? \n"); // �ŷ�ó      
         sqlBuff.append("AND     CEBWMPJT = ? \n"); // ProjNo                  
			sqlBuff.append("AND     CEBWMHGB = ? \n"); // ȣ�ⱸ��                
			sqlBuff.append("AND     CEBWMHNO = ? \n"); // ȣ���ȣ        
         sqlBuff.append("AND     CEBWMSEQ = ? \n"); // ����                 
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWMF1Vo();
				vo = (TBEBWMF1Vo) list.get(i);
				pstmt.setString(idxparam++, vo.getCebwmuhj());
				pstmt.setString(idxparam++, vo.getCebwmcha());
				pstmt.setString(idxparam++, vo.getCebwmcdt());
            pstmt.setString(idxparam++, vo.getCebwmupn());
            pstmt.setString(idxparam++, vo.getCebwmcst());
				pstmt.setString(idxparam++, vo.getCebwmpjt());
				pstmt.setString(idxparam++, vo.getCebwmhgb());
				pstmt.setString(idxparam++, vo.getCebwmhno());
            pstmt.setString(idxparam++, vo.getCebwmseq());
            pstmt.executeUpdate();
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
            
            comVo = new ComMethodVo();
                  
            comVo.setDataId("51");            
            comVo.setJobGubun("D");            
            comVo.setUpn(vo.getCebwmupn());
            comVo.setCst(vo.getCebwmcst()); 
            comVo.setPjt(vo.getCebwmpjt());
            comVo.setHgb(vo.getCebwmhgb());
            comVo.setHno(vo.getCebwmhno());
            comVo.setSeq(vo.getCebwmseq());            
            comVo.setFirstUserId(vo.getCebwmcdt());
            
//            ComMethodDao.SetBXRBXL(comVo);                    
			}			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	} 
	
	/**
					* �ۼ��� : shhwang
					* �ۼ��� : 2006.07.08
					* ����    : ������ ���� :A1,A2�� �������� 
					* ��Ÿ    : 
		*/
		public void deleteRowForGY020102(ArrayList list, Connection conn) throws Exception 
		{
			LoggablePreparedStatement  pstmt    = null ;
			StringBuffer               sqlBuff  = new StringBuffer(); 
			TBEBWMF1Vo                 vo       = new TBEBWMF1Vo();  
           
			try { 
				sqlBuff.append("  DELETE            \n");
				sqlBuff.append("  FROM     EVLADM.TBEBWMF1 \n");
				sqlBuff.append("WHERE  CEBWMUPN   = ?               \n");  
				sqlBuff.append("AND  CEBWMCST   =   ?             \n");
				sqlBuff.append("AND  CEBWMPJT   =   ?               \n"); 
				sqlBuff.append("AND  CEBWMHGB   =   ?               \n"); 
				sqlBuff.append("AND  CEBWMHNO   =   ?               \n"); 
				sqlBuff.append("AND  CEBWMSEQ   =   ?               \n"); 
               
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
            
				for (int i = 0; i < list.size(); i++) {
					int idxparam = 1;
					vo = new TBEBWMF1Vo();
					vo = (TBEBWMF1Vo) list.get(i);
					pstmt.setString(idxparam++, vo.getCebwmupn());  
					pstmt.setString(idxparam++, vo.getCebwmcst());  
					pstmt.setString(idxparam++, vo.getCebwmpjt());
					pstmt.setString(idxparam++, vo.getCebwmhgb());
					pstmt.setString(idxparam++, vo.getCebwmhno());   
					pstmt.setString(idxparam++, vo.getCebwmseq());        
					
					// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
					pstmt.executeUpdate();					
				}
         
      
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}finally{
				try{if (pstmt != null) pstmt.close();}catch(Exception e){}
			}
		}

	/**
	 * �ۼ��� : jhlee
	 * �ۼ��� : 2006.08.14
	 * ����   : ����PM����ÿ� update �Ѵ�. 
	 * ��Ÿ   : 
	 */
	public void updateRowForGY090201(ArrayList list, Connection conn) throws Exception {
	   LoggablePreparedStatement  pstmt   = null;
	   StringBuffer               sqlBuff = null; 
	   TBEBWMF1Vo                 vo      = null;

	   try {
		  for(int i=0; i<list.size(); i++) {
			 int idxparam = 1;
			 sqlBuff = new StringBuffer(); 
			 vo = new TBEBWMF1Vo();
			 vo = (TBEBWMF1Vo) list.get(i);

			 sqlBuff.append("UPDATE                 \n");
			 sqlBuff.append("       EVLADM.TBEBWMF1 \n");
			 sqlBuff.append("   SET                 \n");
			 sqlBuff.append("       CEBWMBPM = ?    \n");
			 sqlBuff.append(" WHERE                 \n");
			 sqlBuff.append("       CEBWMBSU = ?    \n");
			 sqlBuff.append("   AND CEBWMBPM = ?    \n");

			 pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());             

			 pstmt.setString(idxparam++, vo.getCebwmbpm());
			 pstmt.setString(idxparam++, vo.getCebwmbsu());
			 pstmt.setString(idxparam++, vo.getCebwmbpm_f());

			 // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			 pstmt.executeUpdate();
		  }
	   } catch(Exception e) {
		  e.printStackTrace();
		  throw e;
	   } finally {
		  try {
			 if(pstmt != null) pstmt.close();
		  } catch (Exception e) {}
	   }
	}
	
	/**
	 * �ۼ��� : jhlee
	 * �ۼ��� : 2006.08.16
	 * ����   : ��(��)������ ����ÿ� update �Ѵ�. 
	 * ��Ÿ   : 
	 */
	public void updateRowForGY090301(ArrayList list, Connection conn) throws Exception {
	   LoggablePreparedStatement  pstmt   = null;
	   StringBuffer               sqlBuff = null; 
	   TBEBWMF1Vo                 vo      = null;

	   try {
		  for(int i=0; i<list.size(); i++) {
			 int idxparam = 1;
			 sqlBuff = new StringBuffer(); 
			 vo = new TBEBWMF1Vo();
			 vo = (TBEBWMF1Vo) list.get(i);

			 sqlBuff.append("UPDATE                 \n");
			 sqlBuff.append("       EVLADM.TBEBWMF1 \n");
			 sqlBuff.append("   SET                 \n");
			 sqlBuff.append("       CEBWMJUJ = ?,   \n");
			 sqlBuff.append("       CEBWMBUJ = ?    \n");
			 sqlBuff.append(" WHERE                 \n");
			 sqlBuff.append("       CEBWMPJT = ?    \n");
			 sqlBuff.append("   AND CEBWMHGB = ?    \n");
			 sqlBuff.append("   AND CEBWMHNO = ?    \n");
			 sqlBuff.append("   AND CEBWMBSU = ?    \n");
			 sqlBuff.append("   AND RTRIM(CEBWMUHJ) > REPLACE(CHAR(CURRENT DATE),'-','') \n");

			 pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());             

			 pstmt.setString(idxparam++, vo.getCebwmjuj());
			 pstmt.setString(idxparam++, vo.getCebwmbuj());
			 pstmt.setString(idxparam++, vo.getCebwmpjt());
			 pstmt.setString(idxparam++, vo.getCebwmhgb());
			 pstmt.setString(idxparam++, vo.getCebwmhno());
			 pstmt.setString(idxparam++, vo.getCebwmbsu());

			 // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			 pstmt.executeUpdate();
		  }
	   } catch(Exception e) {
		  e.printStackTrace();
		  throw e;
	   } finally {
		  try {
			 if(pstmt != null) pstmt.close();
		  } catch (Exception e) {}
	   }
	}
}
