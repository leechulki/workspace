package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0060ParamVO;

import java.util.List;

public interface Com0060D {  
	
	// �ڵ����� ��ȸ
	public List<Com0060ParamVO> nation(Com0060ParamVO param);		// ����
	public List<Com0060ParamVO> area(Com0060ParamVO param);			// �������
	public List<Com0060ParamVO> zkunnr(Com0060ParamVO param);		// �������
	public List<Com0060ParamVO> spart(Com0060ParamVO param);		// ��ǰ
	public List<Com0060ParamVO> matnr(Com0060ParamVO param);		// ����
	public List<Com0060ParamVO> matnrClass(Com0060ParamVO param);	// �����ڵ庰 Ŭ����
	public List<Com0060ParamVO> gtype(Com0060ParamVO param);		// ����
	public List<Com0060ParamVO> zdeli(Com0060ParamVO param);		// �ܳ�����
	public List<Com0060ParamVO> shangh(Com0060ParamVO param);		// ��������
	public List<Com0060ParamVO> region(Com0060ParamVO param);		// ��ġ����
	public List<Com0060ParamVO> zterm(Com0060ParamVO param);		// ��������
	public List<Com0060ParamVO> mlbez(Com0060ParamVO param);		// ��������
	public List<Com0060ParamVO> nature(Com0060ParamVO param);		// ���Ư������Ʈ
	public List<Com0060ParamVO> brndfind(Com0060ParamVO param);		// �귣����Ư������Ʈ
	public List<Com0060ParamVO> lifnr(Com0060ParamVO param);		// ���¾�ü
	public List<Com0060ParamVO> kunnr(Com0060ParamVO param);		// �븮��
	public List<Com0060ParamVO> lifnrKunnr(Com0060ParamVO param);	// ���¾�ü & �븮�� 
	public List<Com0060ParamVO> auart(Com0060ParamVO param);		// ��������(�ǸŹ�������)
	public List<Com0060ParamVO> depart(Com0060ParamVO param);		// �μ�
	public List<Com0060ParamVO> rtype(Com0060ParamVO param);		// �Ǳ���
	public List<Com0060ParamVO> sorlt(Com0060ParamVO param);		// ���ְ��
	public List<Com0060ParamVO> zlcode(Com0060ParamVO param);		// zlcode
	public List<Com0060ParamVO> teamCd(Com0060ParamVO param);		// ����ȸ
	public List<Com0060ParamVO> abgru(Com0060ParamVO param);		// �źλ���
}
