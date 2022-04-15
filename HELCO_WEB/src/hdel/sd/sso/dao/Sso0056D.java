package hdel.sd.sso.dao;

import java.util.List;

import hdel.sd.sso.domain.ZSDT0004;
import hdel.sd.sso.domain.ZSDT0090;
import hdel.sd.sso.domain.ZSDT0091;
import hdel.sd.sso.domain.ZSDTHOGIMV;

/*
 ******************************************************************************************
 * ��      ��   : ��� ȣ����� ���� DAO(Sso0056.xml)
 * ��  ��  ��   :
 * �ۼ�  ����   : 2016.03.07
 * ���ID       : UF006
 * ----------------------------------------------------------------------------------------
 * HISTORY      :  2016.02.12 ���� �ڵ� �ڼ���
 ******************************************************************************************
*/

public interface Sso0056D { 

	// ������ �̷»��� ���� ��ȸ
	public int selectZSDT0090Cnt(ZSDT0090 zSDT0090);
	
	// max seq ��ȸ
	public String selectZSDT0090MaxSeq(ZSDT0090 zSDT0090);
	
	// ����� X ȣ�� ���� ��ȸ
	public List<ZSDT0091> selectZSDT0091ChangeList(ZSDT0090 zSDT0090);
	
	// ������ ���� ����Ʈ ��ȸ
	public List<ZSDT0004> selectZSDT0004List(ZSDT0004 zSDT0004);
	
	// ���� ��ຯ�� ��� �ڷḦ SAP ���� �������� ��ȸ�Ͽ� ����
	public int insertFirsrMvZSDT0090(ZSDTHOGIMV zSDTHOGIMV);
	
	// ���� ��ຯ�� ȣ�� ������ �ڷḦ SAP ���� �������� ��ȸ�Ͽ� ����
	public int insertFirstMvZSDT0091(ZSDTHOGIMV zSDTHOGIMV);
	
	// ���� ��ຯ�� ȣ�⺰ û����ȹ ���� SAP ���� �������� ��ȸ�Ͽ� ����
	public int insertFirstMvZSDT0092(ZSDTHOGIMV zSDTHOGIMV);
	
	// ���� ��ຯ�� ȣ�⺰ û����ȹ �������� SAP ���� �������� ��ȸ�Ͽ� ����
	public int insertFirstMvZSDT0093(ZSDTHOGIMV zSDTHOGIMV);

	// ���� ��ຯ�� ȣ�⺰ ��缭 ���� ���� �Ǽ� ��ȸ
    public int selectFirstMvOriginZSDT0094Cnt(ZSDTHOGIMV zSDTHOGIMV);
    
	// ���� ��ຯ�� ȣ�⺰ ��缭 ���� SAP ���� �������� ��ȸ �Ͽ� ����
    public int insertFirstMvOriginZSDT0094(ZSDTHOGIMV zSDTHOGIMV);
    
	// ���� ��ຯ�� ȣ�⺰ ��缭 ���� ���� SAP ���� �������� ��ȸ �Ͽ� ����
    public int insertFirstMvChangeZSDT0094(ZSDTHOGIMV zSDTHOGIMV);

	// ��ȹ���� ��ຯ�� �����Ϳ��� ���� ������ �ű������� �����ؼ� ����
    public int insertMvZSDT0090(ZSDTHOGIMV zSDTHOGIMV);

	// ��ȹ���� ��ຯ�� ȣ�� �����Ϳ��� ���� ������ �ű������� �����ؼ� ����
    public int insertMvZSDT0091(ZSDTHOGIMV zSDTHOGIMV);
    
	// ��ȹ���� ��ຯ�� ȣ�⺰ û����ȹ ���� ������ �ű������� �����ؼ� ����
    public int insertMvZSDT0092(ZSDTHOGIMV zSDTHOGIMV);
    
	// ��ȹ���� ��ຯ�� ȣ�⺰ ���� û����ȹ ���� ������ �ű������� �����ؼ� ����
    public int insertMvZSDT0093(ZSDTHOGIMV zSDTHOGIMV);
    
	// ��ȹ���� ��ຯ�� ȣ�⺰ ���� ��系�� �Ǽ� ��ȸ(ȣ�⺰ ���� ȣ�� ���� �ݿ�)
    public int selectMvOriginZSDT0094Cnt(ZSDTHOGIMV zSDTHOGIMV);

	// ��ȹ���� ��ຯ�� ȣ�⺰ ���� ��系������ �ű� ���� ��系�� ����(ȣ�⺰ ���� ȣ�� ���� �ݿ�)
    public int insertMvOriginZSDT0094(ZSDTHOGIMV zSDTHOGIMV);

    // ��ȹ���� ��ຯ�� ȣ�⺰ ���� ��系������ �ű� ��系�� ����(ȣ�⺰ ���� ȣ�� ���� �ݿ�)
    public int insertMvChangeZSDT0094(ZSDTHOGIMV zSDTHOGIMV);
    
    // ȣ�⺰ ���� ��� ���� ����
	public int updateZSDT0090(ZSDTHOGIMV zSDTHOGIMV);

	// ȣ�⺰ ���濩�� �÷� X�� ����
	public int updateZSDT0091(ZSDTHOGIMV zSDTHOGIMV);

	// ȣ�⺰ ����� ȣ���ȣ ����
	public int updateTmpZSDT0091(ZSDTHOGIMV zSDTHOGIMV);

	public int updateOrgMvZSDT0091(ZSDTHOGIMV zSDTHOGIMV);

	public int updateTmpMvZSDT0091(ZSDTHOGIMV zSDTHOGIMV);


	// ȣ�⺰ ����� û����ȹ ȣ���ȣ ����
	public int updateTmpZSDT0093(ZSDTHOGIMV zSDTHOGIMV);

	public int updateOrgMvZSDT0093(ZSDTHOGIMV zSDTHOGIMV);

	public int updateTmpMvZSDT0093(ZSDTHOGIMV zSDTHOGIMV);


	// ȣ�⺰ ����� ��系�� ����
	public int updateTmpZSDT0094(ZSDTHOGIMV zSDTHOGIMV);

	public int updateOrgMvZSDT0094(ZSDTHOGIMV zSDTHOGIMV);

	public int updateTmpMvZSDT0094(ZSDTHOGIMV zSDTHOGIMV);
	
	// ����Ǿ� �ִ� ������ ����
	public int deleteZSDT0091(ZSDT0090 zSDT0090);
	public int deleteZSDT0092(ZSDT0090 zSDT0090);
	public int deleteZSDT0093(ZSDT0090 zSDT0090);
	public int deleteZSDT0094(ZSDT0090 zSDT0090);
	
	
}
