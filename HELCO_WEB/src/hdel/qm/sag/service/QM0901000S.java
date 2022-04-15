package hdel.qm.sag.service;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;

import hdel.qm.sag.dao.QM0901000D;
import hdel.qm.sag.domain.QM0901000;
import hdel.qm.sag.domain.QM0901000ParamVO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// 매출/청구/수금 자동산출용 INPUT
// 매출/청구/수금 자동산출용 OUTPUT
// 원가산출결과 저장용
// 원가산출결과 저장용
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;


@Service
public class QM0901000S {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

//	@Autowired
	private SrmSqlSession sqlSession;


	private QM0901000D QM0901000Dao;


	public void createDao(SqlSession sqlSession) {
		QM0901000Dao = sqlSession.getMapper(QM0901000D.class);
	}

	// 목록 조회
	public List<QM0901000> selectList(QM0901000ParamVO param) {
		return QM0901000Dao.selectList(param);
	}

	// 목록 상세 조회
	public List<QM0901000> getListDetail(QM0901000 param) {
		return QM0901000Dao.selectListDetail(param);
	}

	public String save(List<QM0901000> list, List<QM0901000> listFile) throws Exception {

		try {

			for ( int i = 0 ; i < list.size() ; i++ ) {

				QM0901000 paramlist = list.get(i);
				if ( paramlist.getZrqseq().equals("") ) {

					List<QM0901000> maxlist = QM0901000Dao.selectMaxZRqSeq(paramlist);

					String strMaxSerial = maxlist.get(0).getZrqseq();
					//strMaxSerial = strMaxSerial.substring(13, strMaxSerial.length());
					String strNewSerial = StringUtils.leftPad((Integer.parseInt(strMaxSerial)+1)+"", 10, "0");

					//String strNewZRQSEQ = "";

					//strNewZRQSEQ += param.getZRQDAT() + "-";	// (8)+'-'
					//strNewZRQSEQ += param.getGVGCD()  + "-";	// (3)+'-'
					//strNewZRQSEQ += strNewSerial;				// (3)

					paramlist.setZrqseq(strNewSerial);
					QM0901000Dao.insertHeader(paramlist);

				}   else {
					QM0901000Dao.updateHeader(paramlist);
				}

			}

		    /*ZRQSEQ = param.getZrqseq();

			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Sag0990 paramFile = listFile.get(i);
				if ( paramFile.getZrqseq().equals("") ) {
					paramFile.setZrqseq(ZRQSEQ);
				}
			}*/

			//String FZRQSEQ = paramFile.getZrqseq();

			for ( int i = 0 ; i < listFile.size() ; i++ ) {

				QM0901000 paramlist = listFile.get(i);
				if ( paramlist.getZrqseq().equals("") ) {

					List<QM0901000> maxlist = QM0901000Dao.selectMaxZRqSeq(paramlist);

					String strMaxSerial = maxlist.get(0).getZrqseq();

					paramlist.setZrqseq(strMaxSerial);
					QM0901000Dao.insertFile(paramlist);
				}

				else{
					QM0901000Dao.updateFile(paramlist);
				}

			}

		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public String request(List<QM0901000> list) throws Exception {

		try {

			for ( int i = 0 ; i < list.size() ; i++ ) {

				QM0901000 paramlist = list.get(i);
				QM0901000Dao.updateRequestHeader(paramlist);
			}

		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public String register(List<QM0901000> list, List<QM0901000> listFile) throws Exception {

		try {

			for ( int i = 0 ; i < list.size() ; i++ ) {

				QM0901000 paramlist = list.get(i);
				if (!paramlist.getTstdt().equals("")) {
					QM0901000Dao.updateRegisterHeader(paramlist);
				}
			}

			for ( int i = 0 ; i < listFile.size() ; i++ ) {

				QM0901000 paramlist = listFile.get(i);
				if ( paramlist.getZatgbn().equals("F") ) {
					List<QM0901000> maxlist = QM0901000Dao.selectMaxZattSeq(paramlist);

					String strMaxSerial = maxlist.get(0).getZattseq();
					int strMaxSerialInt = Integer.parseInt(strMaxSerial)+1;
					String strNewSerial = Integer.toString(strMaxSerialInt);

					paramlist.setZattseq(strNewSerial);
					QM0901000Dao.insertFile(paramlist);
				}

			}

		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public String result(List<QM0901000> list) throws Exception {

		try {

			for ( int i = 0 ; i < list.size() ; i++ ) {

				QM0901000 paramlist = list.get(i);
				QM0901000Dao.updateResultHeader(paramlist);
			}

		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public String confirm(List<QM0901000> list) throws Exception {

		try {

			for ( int i = 0 ; i < list.size() ; i++ ) {

				QM0901000 paramlist = list.get(i);
				QM0901000Dao.updateConfirmHeader(paramlist);
			}

		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public String reject(List<QM0901000> list) throws Exception {

		try {

			for ( int i = 0 ; i < list.size() ; i++ ) {

				QM0901000 paramlist = list.get(i);
				QM0901000Dao.updateRejectHeader(paramlist);
			}

		} catch (Exception e) {
			throw e;
		}
		return null;
	}

}
