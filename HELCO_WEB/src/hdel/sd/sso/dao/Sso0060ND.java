package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0060;
import hdel.sd.sso.domain.Sso0060ParamVO;
import hdel.sd.sso.domain.ZSDT1100;
import hdel.sd.sso.domain.ZSDT1101;
import hdel.sd.sso.domain.ZSDT1102;
import hdel.sd.sso.domain.ZSDT1103;

import java.util.List;

import org.apache.ibatis.annotations.Param;


/**
 * ���ֻ���(Sso0060D) DAO
 * @Comment
 *        - List selectListZclose      ( �������� ��ȸ )
 *
 * @since 1.0
 *         History
 *         1.0  2016.0.25 �ڼ��� ����
 * @version 1.0
 */


public interface Sso0060ND {


    // ������Ʈ��ȣ�� ������ȣ ��ȸ
    public List<Sso0060> selectListQtnum(Sso0060ParamVO param); 

    // ������ȣ�� �������� �������� �� ������Ʈ��ȣ ��ȸ
    public List<Sso0060> selectListQtser(Sso0060ParamVO param); 

    // ������ȣ�� ���������� ��ȸ
    public List<Sso0060> selectListQtnumInfo(Sso0060ParamVO param);   

    // ������ȣ�� ����ǰ������� ��ȸ
    public List<Sso0060> selectListQtnumItemInfo(Sso0060ParamVO param); 

    // �Ǹ�ó���� ��ȸ
    public List<Sso0060> selectListKunnrRg(Sso0060ParamVO param); 

    // �μ���,���� ��ȸ
    public List<Sso0060> selectListVbVg(Sso0060ParamVO paramV);  

    // ��ǰó��� ��ȸ
    public List<Sso0060> selectListKunnrWe(Sso0060ParamVO param); 

    // �븮������ ��ȸ
    public List<Sso0060> selectListKunnrZ1(Sso0060ParamVO param); 

    // ��������� ��ȸ
    public List<Sso0060> selectListKunnrZ2(Sso0060ParamVO param); 

    // ���������������� ��ȸ
    public List<Sso0060> selectListKunnrZ3(Sso0060ParamVO param); 
    
    // ��������� ��ȸ
    public List<Sso0060> selectListManager(Sso0060ParamVO param);

    // ����������� ����
    public void updateZsdt1046Zprgflg(Sso0060 param); 

    // ���ְ�ȹ���� ����
    public void updateZsdt1001Sorlt(Sso0060 param); 

    //public Sso0060 selectExchangeRate(Sso0060ParamVO param); 
    
    //=========================================================================================
    //  ���ID    : UF014
    //  ��������  : ���� ����� �Է� data ��ȸ(���ֻ����ӽ�����)
    //=========================================================================================
    
    // �ӽ� ������ HEADER �Ǽ� ��ȸ
    public int selectTmpCount(Sso0060ParamVO param);

    // �ӽ� ������ HEADER ��ȸ
    public List<ZSDT1100> selectTmpQtHeader(Sso0060ParamVO param);

    // �ӽ� ������ HEADER �Է�/����
    public int mergeTmpQtHeader(ZSDT1100 zSDT1100);

    // �ӽ� ������ HEADER ����
    public int insertTmpQtHeader(ZSDT1100 zSDT1100);

    // �ӽ� ������ HEADER ����
    public int updateTmpQtHeader(ZSDT1100 zSDT1100);
    
    // �ӽ� ������ HEADER ����
    public int deleteTmpQtHeader(ZSDT1100 zSDT1100);
    
    // �ӽ� ������ ITEM ��ȸ
    public List<ZSDT1101> selectTmpQtItem(Sso0060ParamVO param);

    // �ӽ� ������ ITEM �Է�
    public int insertTmpQtItem(ZSDT1101 zSDT1101);
    
    // �ӽ� ������ ITEM ����
    public int updateTmpQtItem(ZSDT1101 zSDT1101);

    // �ӽ� ������ ITEM All ����
    public int deleteAllTmpQtItem(ZSDT1101 zSDT1101);

    // �ӽ� ������ ITEM ����
    public int deleteTmpQtItem(ZSDT1101 zSDT1101);

    // �ӽ� ������ û����ȹ ��ȸ
    public List<ZSDT1102> selectTmpQtBiliingPlan(Sso0060ParamVO param);
    
    // �ӽ� ������ û����ȹ ����
    public int deleteTmpQtBiliing(Sso0060ParamVO param);

    // �ӽ� ������ û����ȹ �ƽ� ������ ��ȸ
    public int deleteTmpQtBiliingMaxSeq(Sso0060ParamVO param);
    
    // �ӽ� ������ û����ȹ �Է�
    public int insertTmpQtBiliingPlan(ZSDT1102 zSDT1102);

    // �ӽ� ������ û����ȹ ����
    public int updateTmpQtBiliingPlan(ZSDT1102 zSDT1102);
    
    // �ӽ� ������ û����ȹ ����
    public int deleteTmpQtBiliingPlan(ZSDT1102 zSDT1102);
    
    // �ӽ� ������ �ؽ�Ʈ ��ȸ
    public List<ZSDT1103> selectTmpQtText(Sso0060ParamVO param);

    // �ӽ� ������ û����ȹ �ƽ� ������ ��ȸ
    public int selectTmpQtTxtMaxSeq(Sso0060ParamVO param);
    
    // �ӽ� ������ û����ȹ �ƽ� ������ ��ȸ
    public int deleteTmpQtMsTxtSer(Sso0060ParamVO param);
    
    // �ӽ� ������ �ؽ�Ʈ �Է�
    public int insertTmpQtText(ZSDT1103 zSDT1103);

    // �ӽ� ������ �ؽ�Ʈ ����
    public int updateTmpQtText(ZSDT1103 zSDT1103);
    
    // �ӽ� ������ �ؽ�Ʈ ����
    public int deleteTmpQtText(ZSDT1103 zSDT1103);
    
    //=========================================================================================
    //  ���ID    : UF014
    //  ��������  : ���� ����� �Է� data ��ȸ(���ֻ����ӽ�����)
    //=========================================================================================
    
    public Sso0060 getVbeln(Sso0060ParamVO param);

	public List<Sso0060> selectSopind(Sso0060ParamVO param);
}
