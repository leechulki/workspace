package hdel.sd.sbp.service;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.sd.com.domain.Com0040;
import hdel.sd.com.domain.Com0040ParamVO;
import hdel.sd.com.service.Com0040S;
import hdel.sd.sbp.dao.Sbp0010D;
import hdel.sd.sbp.domain.Sbp0010;
import hdel.sd.sbp.domain.Sbp0010ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// ����/û��/���� �ڵ������ INPUT
// ����/û��/���� �ڵ������ OUTPUT
// ���������� �����
// ���������� �����


/**
 * �����ȹ����(Sbp0010S) Service
 * @Comment
 *     	1.  void createDao
 *		2.  List selectOpenDtm  	( �����ȹ �����Ͻ�(14�ڸ�) ��ȸ )
 *		3.  List selectList 		( �����ȹ ��� ��ȸ )
 *		4.  List selectListDetail	( �����ȹ��ȣ �� �ǿ� ��ϵ� �����ȹ ����/û��/���ݸ�� ��ȸ )
 *		5.  void save 				( ���� ���� )
 *		6.  void detail_save 		( ����/û��/���� ���� )
 *
 * @since 1.0
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version
 * @version 1.0
 */


@Service
public class Sbp0010S {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

//	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Com0040S BuyrService ;	// �ŷ�����ȸ ����

	private Sbp0010D sbp0010Dao;

	public void createDao(SqlSession sqlSession) {
		sbp0010Dao = sqlSession.getMapper(Sbp0010D.class);
	}

	// �����ȹ �����Ͻ�(14�ڸ�) ��ȸ
	public List<Sbp0010> selectOpenDtm(Sbp0010ParamVO param) {
		return sbp0010Dao.selectOpenDtm(param);
	}

	// �����ȹ ���ֿ����, ��ȭ ��ȸ : ����/û��/���� �ڵ����� ��� ��������
	public List<Sbp0010> selectListComputeItem(Sbp0010ParamVO param) {
		return sbp0010Dao.selectListComputeItem(param);
	}

	// �����ȹ ��� ��ȸ
	public List<Sbp0010> selectList(Sbp0010ParamVO param) {
		return sbp0010Dao.selectList(param);
	}

	// �����ȹ��ȣ �� �ǿ� ��ϵ� �����ȹ ����/û��/���ݸ�� ��ȸ
	public List<Sbp0010> selectListDetail(Sbp0010ParamVO param) {
		return sbp0010Dao.selectListDetail(param);
	}

	// �����Ͻ� ���翩�� üũ
	public Boolean chkOpenDtm(SqlSession session
					            , String mandt
								, String zpyear		// ���⵵
								)
	{
		logger.debug("@@@ Sbp0010S.chkOpenDtm START!!!" + "");
		logger.debug("@@@ Sbp0010S.chkOpenDtm.mandt	: "	+ mandt + "");
		logger.debug("@@@ Sbp0010S.chkOpenDtm.zpyear	: "	+ zpyear + "");

		Sbp0010ParamVO param = new Sbp0010ParamVO();

		// �Ķ����SET
		param.setMANDT	(mandt);  	// SAP CLIENT
		param.setZPYEAR	(zpyear);	// ���⵵

		// ����CALL (�����ȹ�⵵�� �ش��ϴ� �������� ��ȸ)
		List<Sbp0010> list = selectOpenDtm(param);

		// ��ȸ�� �����Ͻ� ������ ���� ��� ������ �����Ѵ�.(����Ұ�ó��)
		if (list.size()==0) return false;

		return true;
	}

	// �ŷ����ڵ� ��ȿ�� üũ
	public Boolean chkBuyrCd(SqlSession session
			                    , String mandt
								, String kunnr		// �ŷ����ڵ�
								)
	{

		logger.debug("@@@ Sbp0010S.chkBuyrCd START!!!" 	+ "");
		logger.debug("@@@ Sbp0010S.chkBuyrCd.mandt : " 	+ mandt + "");
		logger.debug("@@@ Sbp0010S.chkBuyrCd.kunnr : " 	+ kunnr + "");

		Com0040ParamVO param = new Com0040ParamVO();

		// DAO����
		BuyrService.createDao(session);

		// Parameter SET
		param.setMandt		(mandt);  	// SAP CLIENT
		param.setBuyr_cd	(kunnr);	// �ŷ����ڵ�

		// �ŷ����� ��ȸ
		List<Com0040> list = BuyrService.find(param);

		// ��ȸ�� �ŷ��������� ���� ��� ������ �����Ѵ�.(����Ұ�ó��)
		if (list.size()==0) return false;

		return true;
	}

	// �����ȹ ���� �� ������� ����
	public void saveCost(Sbp0010 param) {
		sbp0010Dao.updateCostZSDT1012(param);
	}

	// �븮������ ��ȸ
	public List<Sbp0010> selectListZagnt(Sbp0010ParamVO paramV) {
		return sbp0010Dao.selectListZagnt(paramV);
	}

	// �Ǳ��� ����  ��ȸ
	public List<Sbp0010> selectListRGtype(Sbp0010ParamVO paramV) {
		return sbp0010Dao.selectListRGtype(paramV);
	}
}
