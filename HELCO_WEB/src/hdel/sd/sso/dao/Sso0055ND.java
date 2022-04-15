package hdel.sd.sso.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sap.domain.Vbeln;

import hdel.lib.domain.ZSDT0129;
import hdel.sd.sso.domain.Sso0055ParamVO;
import hdel.sd.sso.domain.Sso0055VO;
import hdel.sd.sso.domain.ZSDS0063;
import hdel.sd.sso.domain.ZSDT0090;
import hdel.sd.sso.domain.ZSDT0091;
import hdel.sd.sso.domain.ZSDT0092;
import hdel.sd.sso.domain.ZSDT0093;
import hdel.sd.sso.domain.ZSDT0094;
import hdel.sd.sso.domain.ZSDT1108;

/**
 * ���ֺ���
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sso0055ND { 

	public List<ZSDS0063> SelectQtdat(ZSDS0063 param);	  		// ������ , ���������� 
	public List<ZSDS0063> SelectProject(ZSDS0063 param);	  	// ������Ʈ ������
	public List<ZSDS0063> SelectProjectInput(ZSDS0063 param); 	// ������Ʈ ������(�űԻ���)
	public List<ZSDT0090> SelectHeader(ZSDT0090 param);		  	// ��ຯ�� header
	public List<ZSDT0090> SelectHeaderInput(ZSDT0090 param);	// ��ຯ�� header(�űԻ���)
	public List<ZSDT0091> SelectItem(ZSDT0091 param);		  	// ��ຯ�� item
	public List<ZSDT0091> SelectItemInput(ZSDT0091 param);		// ��ຯ�� item(�űԻ���)
	public List<ZSDT0092> SelectBillO(ZSDT0092 param);		  	// ��ຯ�� billing original
	public List<ZSDT0092> SelectBillOInput(ZSDT0092 param);		// ��ຯ�� billing original(�űԻ���)
	public List<ZSDT0093> SelectBillC(ZSDT0093 param);		  	// ��ຯ�� billing change
	public List<ZSDT0093> SelectBillCInput(ZSDT0093 param);		// ��ຯ�� billing change(�űԻ���)
	public List<ZSDT0094> SelectSpecC(ZSDT0094 param);		  	// ��ຯ�� spec change
	public List<ZSDT0094> SelectSpecCInput(ZSDT0094 param);		// ��ຯ�� spec change(�űԻ���)

	public int mergeHeader(ZSDT0090 param);                    	// ��ຯ�� header save
	public int updateHeader(ZSDT0090 param);                    // ��ຯ�� header save
	public int insertItem(ZSDT0091 param);                    	// ��ຯ�� item save
	public int mergeItem(ZSDT0091 param);                    	// ��ຯ�� item save
	public int mergeBillO(ZSDT0092 param);                    	// ��ຯ�� billing original save
	public int mergeBillC(ZSDT0093 param);                    	// ��ຯ�� billing change save
	public int insertSpecC(ZSDT0094 param);                    	// ��ຯ�� spec chage save
	public int insertSpecCsub(ZSDT0094 param);                    	// ��ຯ�� spec chage save( (+) ��ư Ŭ����)
	public int mergeSpecC(ZSDT0094 param);                    	// ��ຯ�� spec chage save
	public List<Sso0055VO> getRecad_da(Sso0055ParamVO param);  
	
	public List<ZSDT1108> selectJqpr(ZSDT1108 param);			// JQPR ��ȸ
	public int mergeZsdt1108(ZSDT1108 param);					// JQPR ����
	public int updateZqmt007(ZSDT1108 param); 
	
	public List<Sso0055VO> SelectSayang(Sso0055ParamVO param);		// ����
	public List<Sso0055VO> SelectVbeln(Sso0055ParamVO param);		// ����
	public List<Sso0055VO> SelectVbeln2(Sso0055ParamVO param);		// ����
	public List<Sso0055VO> SelectMaxSeq(Sso0055ParamVO param);	  // ��ຯ�� ����
	public List<Sso0055VO> SelectSayangForPrint(Sso0055ParamVO param);
	public List<Sso0055VO> SelectMaxHogi(Sso0055VO param);		  	// �ű� ȣ�� ����

	public void setCostState(Sso0055ParamVO param); // �����Ƿ�
	public List<Sso0055VO> searchZcobt304(Sso0055ParamVO param);
	public List<Sso0055VO> searchZcobt304D(Sso0055ParamVO param);
	public List<Sso0055VO> searchZcobt304DPrev(Sso0055ParamVO param);
	public List<Sso0055VO> selectListBlockName(Sso0055ParamVO param);
	public List<Sso0055VO> selectListExchange(Sso0055ParamVO param); // �Ÿű���ȯ�� ��ȸ 2013.02.20
	public List<Sso0055VO> searchZcobt204D(Sso0055ParamVO param);
	public List<Sso0055VO> selectListBlockNameD(Sso0055ParamVO param);
	
	// ����
	public int deleteZsdt0090(ZSDT0090 param); // ��ຯ�� header
	public int deleteZsdt0091(ZSDT0091 param); // ��ຯ�� item
	public int deleteZsdt0092(ZSDT0092 param); // ��ຯ�� billing original
	public int deleteZsdt0093(ZSDT0093 param); // ��ຯ�� billing change
	public int deleteZsdt0094(ZSDT0094 param); // ��ຯ�� spec
	public int deletePosnrZsdt0094(ZSDT0094 param); // 2020 �귣�� ��ຯ�� ȣ�� ���� spec
	public int insertPosnrZsdt0094(ZSDT0094 param); // 2020 �귣�� ��ຯ�� ȣ�� ���� spec
	
	public int deleteZsdt1108(ZSDT1108 param);	// JQPR ����
	
	public List<Sso0055VO> selectChkHeader(ZSDT0090 param);	  	// ��ຯ�� header (���5 ����)
	
	public int insertZero0090(ZSDT0090 param);                   // ��ຯ�� 0���� ���� (HEADER)
	public int insertZero0091(ZSDT0091 param);                   // ��ຯ�� 0���� ���� (ITEM)
	public int insertZero0092(ZSDT0092 param);                   // ��ຯ�� 0���� ���� (BILLING ORIGINAL)
	public int insertZero0093(ZSDT0093 param);                   // ��ຯ�� 0���� ���� (BILLING CHANGE)
	public int insertZero0094(ZSDT0094 param);                   // ��ຯ�� 0���� ���� (SPEC)

	public String getPartnerZ3(@Param("mandt")String mandt, @Param("posid")String posid);
	public Sso0055VO SelectDocStatusGBSTK(@Param("mandt") String sMandt, @Param("pspid") String sPspid);
	public ZSDT0129 getExceptionalVbeln(@Param("mandt")String mandt, @Param("vbeln")Vbeln vbeln);
	
	public Double getZsdt0220Kbetr(@Param("mandt") String mandt, @Param("contrda")String contrda, @Param("vbeln")Vbeln vbeln);

	public String getZsdt0223Area(@Param("mandt")String mandt, @Param("contrda")String contrda, @Param("vbeln")Vbeln vbeln);
	
	public Double getAdtZa90(@Param("mandt") String mandt, @Param("vbeln")Vbeln vbeln);

	public String SelectHogi(@Param("mandt")String mandt, @Param("vbeln")String vbeln, 
							 @Param("pspid")String pspid, @Param("seq")String seq, @Param("posnr")String posnr);	//�űԻ��� ȣ�� ����
	
	public List<Sso0055VO> SelectDateCommi(Sso0055ParamVO param);		// �븮�� ������ ��������	
	
	public String SelectKunnr(@Param("mandt")String mandt, @Param("vbeln")String vbeln); //������� ��ȸ
	public String SelectFinl(ZSDT0090 param);

	public int SelectFksaf(@Param("mandt")String mandt, @Param("pspId")String pspId); 			//standard Table û������ ��ȸ
	public int SelectZsdt0214(@Param("kunnr")String kunnr); 									//���𵨸� �븮�� �������»� ī��Ʈ
	public int getMaxSeq(@Param("mandt")String i_mandt, @Param("vbeln")String i_vbeln);
	public int validation_pspid(@Param("mandt")String i_mandt, @Param("pspId")String i_pspId); 	//������Ʈ ��ȿ�� üũ 	
	
	// 2020�� �귣�� ����
	public String SelectQtnum(ZSDT0090 param);
} 
