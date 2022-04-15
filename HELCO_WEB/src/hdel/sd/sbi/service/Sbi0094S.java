package hdel.sd.sbi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hdel.lib.dao.SrmSqlSession;
import hdel.sd.sbi.dao.Sbi0090D;
import hdel.sd.sbi.dao.Sbi0094D;
import hdel.sd.sbi.domain.ZSDT1141;
import hdel.sd.sbi.domain.ZSDT1142;

/**
 * 브랜드 배치 등록 Service 클래스
 * 
 * @author  박수근
 * @since 2020.08.20
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.08.20         박수근          최초 생성
 * 
 * </pre>
 */
@Service
public class Sbi0094S {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmSqlSession srmQqlSession;	
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Sbi0090D sbi0090D;

	private Sbi0094D sbi0094D;
	
	public void setSqlSessionBySysid(String gSysid) {
		this.sqlSessionTemplate = (SqlSessionTemplate)srmQqlSession.getSqlSessionBySysid(gSysid);
		sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
	}

	/** 
	 * 브랜드 영업특성, 특성값 배치 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public void saveBatchSayang(List<ZSDT1141> deleteZsdt1141, List<ZSDT1141> iuZsdt1141
			                    ,List<ZSDT1142> deleteZsdt1142, List<ZSDT1142> iuZsdt1142) throws Exception {

		// 일괄 배치 처리 옵션 적용
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		
		sbi0094D = session.getMapper(Sbi0094D.class);
		sbi0090D = session.getMapper(Sbi0090D.class);
		try {
			List<ZSDT1141> insertZsdt1141 = new ArrayList<ZSDT1141>();
			List<ZSDT1141> updateZsdt1141 = new ArrayList<ZSDT1141>();
			List<ZSDT1142> insertZsdt1142 = new ArrayList<ZSDT1142>();
			List<ZSDT1142> updateZsdt1142 = new ArrayList<ZSDT1142>();

			// 처리값 체크를 통해 입력값을 생성한다.
			Map<String, Object> zsdt1141Map = new HashMap<String, Object>();
			if(iuZsdt1141.size() > 0) {
				ZSDT1141 inZSDT1141 = iuZsdt1141.get(0);
				zsdt1141Map.put("MANDT", inZSDT1141.getMANDT());
				zsdt1141Map.put("ZPRDGBN", inZSDT1141.getZPRDGBN());
				zsdt1141Map.put("ERNAM", inZSDT1141.getERNAM());
				zsdt1141Map.put("AENAM", inZSDT1141.getAENAM());
				zsdt1141Map.put("list", iuZsdt1141);
				List<ZSDT1141> zsdt1141List = sbi0094D.selectFlagZsdt1141List(zsdt1141Map);
				for(int i=0; i < zsdt1141List.size(); i++) {
					ZSDT1141 outZSDT1141 = zsdt1141List.get(i);
					if("I".equals(outZSDT1141.getFLAG())) {
						insertZsdt1141.add(outZSDT1141);
					} else if("U".equals(outZSDT1141.getFLAG())) {
						updateZsdt1141.add(outZSDT1141);
					}
				}
			}

			
			Map<String, Object> zsdt1142Map = new HashMap<String, Object>();
			if(iuZsdt1142.size() > 0) {
				ZSDT1142 inZSDT1142 = iuZsdt1142.get(0);
				zsdt1142Map.put("MANDT", inZSDT1142.getMANDT());
				zsdt1142Map.put("ZPRDGBN", inZSDT1142.getZPRDGBN());
				zsdt1142Map.put("ERNAM", inZSDT1142.getERNAM());
				zsdt1142Map.put("AENAM", inZSDT1142.getAENAM());
				zsdt1142Map.put("list", iuZsdt1142);
				List<ZSDT1142> zsdt1142List = sbi0094D.selectFlagZsdt1142List(zsdt1142Map);
				for(int i=0; i < zsdt1142List.size(); i++) {
					ZSDT1142 outZSDT1142 = zsdt1142List.get(i);
					if("I".equals(outZSDT1142.getFLAG())) {
						insertZsdt1142.add(outZSDT1142);
					} else if("U".equals(outZSDT1142.getFLAG())) {
						updateZsdt1142.add(outZSDT1142);
					}
				}
			}
			
			for(int i=0; i < deleteZsdt1141.size(); i++) {
				ZSDT1141 zsdt1141 = deleteZsdt1141.get(i);
				sbi0090D.deleteZsdt1141(zsdt1141);
			}

			// 배치 insert
			logger.debug("insertZsdt1141.size():"+insertZsdt1141.size());
			if(insertZsdt1141.size() > 0) {
				int count = 500;
				if(insertZsdt1141.size() <= count) {
					sbi0094D.insertBatchZsdt1141(insertZsdt1141);
				} else {
					int pre = insertZsdt1141.size() / count;
					int last = insertZsdt1141.size() % count;
					for(int i=0; i < pre; i++) {
						List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
						for(int j=0; j < count; j++) {
							tmpZSDT1141.add(insertZsdt1141.get(i*count+j));
						}
						sbi0094D.insertBatchZsdt1141(tmpZSDT1141);
					}
					
					if(last > 0) {
						List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
						for(int j=0; j < last; j++) {
							tmpZSDT1141.add(insertZsdt1141.get(pre*count+j));
						}
						sbi0094D.insertBatchZsdt1141(tmpZSDT1141);
					}
				}
			}
			
			for(int i=0; i < updateZsdt1141.size(); i++) {
				ZSDT1141 zsdt1141 = updateZsdt1141.get(i);
				sbi0090D.updateZsdt1141(zsdt1141);
			}

			// 영업특성값 delete, insert, update 처리
			for(int i=0; i < deleteZsdt1142.size(); i++) {
				ZSDT1142 zsdt1142 = deleteZsdt1142.get(i);
				sbi0090D.deleteZsdt1142(zsdt1142);
			}

			// 배치 insert
			logger.debug("insertZsdt1142.size():"+insertZsdt1142.size());
			if(insertZsdt1142.size() > 0) {
				int count = 500;
				if(insertZsdt1142.size() <= count) {
					sbi0094D.insertBatchZsdt1142(insertZsdt1142);
				} else {
					int pre = insertZsdt1142.size() / count;
					int last = insertZsdt1142.size() % count;
					for(int i=0; i < pre; i++) {
						List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
						for(int j=0; j < count; j++) {
							tmpZsdt1142.add(insertZsdt1142.get(i*count+j));
						}
						sbi0094D.insertBatchZsdt1142(tmpZsdt1142);
					}
					
					if(last > 0) {
						List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
						for(int j=0; j < last; j++) {
							tmpZsdt1142.add(insertZsdt1142.get(pre*count+j));
						}
						sbi0094D.insertBatchZsdt1142(tmpZsdt1142);
					}
				}
			}
			
			for(int i=0; i < updateZsdt1142.size(); i++) {
				ZSDT1142 zsdt1142 = updateZsdt1142.get(i);
				sbi0090D.updateZsdt1142(zsdt1142);
			}

			// 원본 데이터 저장 완료 후 EAI 연계 데이터 생성한다.
			// 일괄 배치 처리를 수행한다.
			saveBatchEaiSayang(deleteZsdt1141, insertZsdt1141, updateZsdt1141
                              ,deleteZsdt1142, insertZsdt1142, updateZsdt1142);

		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.commit();
			session.close();
		}
	}	

	/** 
	 * 브랜드 영업특성, 특성값 배치 연동데이터 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	private void saveBatchEaiSayang(List<ZSDT1141> deleteZsdt1141, List<ZSDT1141> insertZsdt1141, List<ZSDT1141> updateZsdt1141
			                    ,List<ZSDT1142> deleteZsdt1142, List<ZSDT1142> insertZsdt1142, List<ZSDT1142> updateZsdt1142) throws Exception {

		for(int i=0; i < deleteZsdt1141.size(); i++) {
			ZSDT1141 zsdt1141 = deleteZsdt1141.get(i);
			sbi0090D.insertZeaitZsdt1141(zsdt1141);
		}

		for(int i=0; i < insertZsdt1141.size(); i++) {
			ZSDT1141 zsdt1141 = insertZsdt1141.get(i);
			sbi0090D.insertZeaitZsdt1141(zsdt1141);
		}

		for(int i=0; i < updateZsdt1141.size(); i++) {
			ZSDT1141 zsdt1141 = updateZsdt1141.get(i);
			sbi0090D.insertZeaitZsdt1141(zsdt1141);
		}
		
		for(int i=0; i < deleteZsdt1142.size(); i++) {
			ZSDT1142 zsdt1142 = deleteZsdt1142.get(i);
			sbi0090D.insertZeaitZsdt1142(zsdt1142);
		}

		for(int i=0; i < insertZsdt1142.size(); i++) {
			ZSDT1142 zsdt1142 = insertZsdt1142.get(i);
			sbi0090D.insertZeaitZsdt1142(zsdt1142);
		}

		for(int i=0; i < updateZsdt1142.size(); i++) {
			ZSDT1142 zsdt1142 = updateZsdt1142.get(i);
			sbi0090D.insertZeaitZsdt1142(zsdt1142);
		}
	}	
	

	/** 
	 * 브랜드 영업특성, 특성값 배치 연동데이터 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void saveBatchEaiSayangErro(List<ZSDT1141> deleteZsdt1141, List<ZSDT1141> insertZsdt1141, List<ZSDT1141> updateZsdt1141
			                    ,List<ZSDT1142> deleteZsdt1142, List<ZSDT1142> insertZsdt1142, List<ZSDT1142> updateZsdt1142) throws Exception {

		if(deleteZsdt1141.size() > 0) {
			int count = 500;
			if(deleteZsdt1141.size() <= count) {
				sbi0094D.insertBatchZeaitZsdt1141(deleteZsdt1141);
			} else {
				int pre = deleteZsdt1141.size() / count;
				int last = deleteZsdt1141.size() % count;
				for(int i=0; i < pre; i++) {
					List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
					for(int j=0; j < count; j++) {
						tmpZSDT1141.add(deleteZsdt1141.get(i*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1141(tmpZSDT1141);
				}
				
				if(last > 0) {
					List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
					for(int j=0; j < last; j++) {
						tmpZSDT1141.add(deleteZsdt1141.get(pre*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1141(tmpZSDT1141);
				}
			}
		}

		// 배치 insert
		if(insertZsdt1141.size() > 0) {
			int count = 500;
			if(insertZsdt1141.size() <= count) {
				sbi0094D.insertBatchZeaitZsdt1141(insertZsdt1141);
			} else {
				int pre = insertZsdt1141.size() / count;
				int last = insertZsdt1141.size() % count;
				for(int i=0; i < pre; i++) {
					List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
					for(int j=0; j < count; j++) {
						tmpZSDT1141.add(insertZsdt1141.get(i*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1141(tmpZSDT1141);
				}
				
				if(last > 0) {
					List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
					for(int j=0; j < last; j++) {
						tmpZSDT1141.add(insertZsdt1141.get(pre*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1141(tmpZSDT1141);
				}
			}
		}
		
		if(updateZsdt1141.size() > 0) {
			int count = 500;
			if(updateZsdt1141.size() <= count) {
				sbi0094D.insertBatchZeaitZsdt1141(updateZsdt1141);
			} else {
				int pre = updateZsdt1141.size() / count;
				int last = updateZsdt1141.size() % count;
				for(int i=0; i < pre; i++) {
					List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
					for(int j=0; j < count; j++) {
						tmpZSDT1141.add(updateZsdt1141.get(i*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1141(tmpZSDT1141);
				}
				
				if(last > 0) {
					List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
					for(int j=0; j < last; j++) {
						tmpZSDT1141.add(updateZsdt1141.get(pre*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1141(tmpZSDT1141);
				}
			}
		}

		if(deleteZsdt1142.size() > 0) {
			int count = 500;
			if(deleteZsdt1142.size() <= count) {
				sbi0094D.insertBatchZeaitZsdt1142(deleteZsdt1142);
			} else {
				int pre = deleteZsdt1142.size() / count;
				int last = deleteZsdt1142.size() % count;
				for(int i=0; i < pre; i++) {
					List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
					for(int j=0; j < count; j++) {
						tmpZsdt1142.add(deleteZsdt1142.get(i*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1142(tmpZsdt1142);
				}
				
				if(last > 0) {
					List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
					for(int j=0; j < last; j++) {
						tmpZsdt1142.add(deleteZsdt1142.get(pre*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1142(tmpZsdt1142);
				}
			}
		}
		
		// 배치 insert
		if(insertZsdt1142.size() > 0) {
			int count = 500;
			if(insertZsdt1142.size() <= count) {
				sbi0094D.insertBatchZeaitZsdt1142(insertZsdt1142);
			} else {
				int pre = insertZsdt1142.size() / count;
				int last = insertZsdt1142.size() % count;
				for(int i=0; i < pre; i++) {
					List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
					for(int j=0; j < count; j++) {
						tmpZsdt1142.add(insertZsdt1142.get(i*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1142(tmpZsdt1142);
				}
				
				if(last > 0) {
					List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
					for(int j=0; j < last; j++) {
						tmpZsdt1142.add(insertZsdt1142.get(pre*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1142(tmpZsdt1142);
				}
			}
		}

		if(updateZsdt1142.size() > 0) {
			int count = 500;
			if(updateZsdt1142.size() <= count) {
				sbi0094D.insertBatchZeaitZsdt1142(updateZsdt1142);
			} else {
				int pre = updateZsdt1142.size() / count;
				int last = updateZsdt1142.size() % count;
				for(int i=0; i < pre; i++) {
					List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
					for(int j=0; j < count; j++) {
						tmpZsdt1142.add(updateZsdt1142.get(i*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1142(tmpZsdt1142);
				}
				
				if(last > 0) {
					List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
					for(int j=0; j < last; j++) {
						tmpZsdt1142.add(updateZsdt1142.get(pre*count+j));
					}
					sbi0094D.insertBatchZeaitZsdt1142(tmpZsdt1142);
				}
			}
		}
	}
	
	/** 
	 * 브랜드 영업특성, 특성값 배치 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void saveBatchSayangError(List<ZSDT1141> deleteZsdt1141, List<ZSDT1141> insertZsdt1141, List<ZSDT1141> updateZsdt1141
			               ,List<ZSDT1142> deleteZsdt1142, List<ZSDT1142> insertZsdt1142, List<ZSDT1142> updateZsdt1142) throws Exception {

		// 일괄 배치 처리 옵션 적용
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		
		sbi0094D = session.getMapper(Sbi0094D.class);
		//sbi0090D = session.getMapper(Sbi0090D.class);
		try {
			// 영업특성 delete, insert, update 처리
			logger.debug("deleteZsdt1141.size():"+deleteZsdt1141.size());
			if(deleteZsdt1141.size() > 0) {
				int count = 500;
				if(deleteZsdt1141.size() <= count) {
					sbi0094D.deleteBatchZsdt1141(deleteZsdt1141);
				} else {
					int pre = deleteZsdt1141.size() / count;
					int last = deleteZsdt1141.size() % count;
					for(int i=0; i < pre; i++) {
						List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
						for(int j=0; j < count; j++) {
							tmpZSDT1141.add(deleteZsdt1141.get(i*count+j));
						}
						sbi0094D.deleteBatchZsdt1141(tmpZSDT1141);
					}
					
					if(last > 0) {
						List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
						for(int j=0; j < last; j++) {
							tmpZSDT1141.add(deleteZsdt1141.get(pre*count+j));
						}
						sbi0094D.deleteBatchZsdt1141(tmpZSDT1141);
					}
				}
			}
			
			// 배치 insert
			logger.debug("insertZsdt1141.size():"+insertZsdt1141.size());
			if(insertZsdt1141.size() > 0) {
				int count = 500;
				if(insertZsdt1141.size() <= count) {
					sbi0094D.insertBatchZsdt1141(insertZsdt1141);
				} else {
					int pre = insertZsdt1141.size() / count;
					int last = insertZsdt1141.size() % count;
					for(int i=0; i < pre; i++) {
						List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
						for(int j=0; j < count; j++) {
							tmpZSDT1141.add(insertZsdt1141.get(i*count+j));
						}
						sbi0094D.insertBatchZsdt1141(tmpZSDT1141);
					}
					
					if(last > 0) {
						List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
						for(int j=0; j < last; j++) {
							tmpZSDT1141.add(insertZsdt1141.get(pre*count+j));
						}
						sbi0094D.insertBatchZsdt1141(tmpZSDT1141);
					}
				}
			}

			logger.debug("updateZsdt1141.size():"+updateZsdt1141.size());
			if(updateZsdt1141.size() > 0) {
				int count = 500;
				if(updateZsdt1141.size() <= count) {
					sbi0094D.updateBatchZsdt1141(updateZsdt1141);
				} else {
					int pre = updateZsdt1141.size() / count;
					int last = updateZsdt1141.size() % count;
					for(int i=0; i < pre; i++) {
						List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
						for(int j=0; j < count; j++) {
							tmpZSDT1141.add(updateZsdt1141.get(i*count+j));
						}
						sbi0094D.updateBatchZsdt1141(tmpZSDT1141);
					}
					
					if(last > 0) {
						List<ZSDT1141> tmpZSDT1141 = new ArrayList<ZSDT1141>();
						for(int j=0; j < last; j++) {
							tmpZSDT1141.add(updateZsdt1141.get(pre*count+j));
						}
						sbi0094D.updateBatchZsdt1141(tmpZSDT1141);
					}
				}
			}

			// 영업특성값 delete, insert, update 처리
			logger.debug("deleteZsdt1142.size():"+deleteZsdt1142.size());
			if(deleteZsdt1142.size() > 0) {
				int count = 500;
				if(deleteZsdt1142.size() <= count) {
					sbi0094D.deleteBatchZsdt1142(deleteZsdt1142);
				} else {
					int pre = deleteZsdt1142.size() / count;
					int last = deleteZsdt1142.size() % count;
					for(int i=0; i < pre; i++) {
						List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
						for(int j=0; j < count; j++) {
							tmpZsdt1142.add(deleteZsdt1142.get(i*count+j));
						}
						sbi0094D.deleteBatchZsdt1142(tmpZsdt1142);
					}
					
					if(last > 0) {
						List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
						for(int j=0; j < last; j++) {
							tmpZsdt1142.add(deleteZsdt1142.get(pre*count+j));
						}
						sbi0094D.deleteBatchZsdt1142(tmpZsdt1142);
					}
				}
			}

			// 배치 insert
			logger.debug("insertZsdt1142.size():"+insertZsdt1142.size());
			if(insertZsdt1142.size() > 0) {
				int count = 500;
				if(insertZsdt1142.size() <= count) {
					sbi0094D.insertBatchZsdt1142(insertZsdt1142);
				} else {
					int pre = insertZsdt1142.size() / count;
					int last = insertZsdt1142.size() % count;
					for(int i=0; i < pre; i++) {
						List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
						for(int j=0; j < count; j++) {
							tmpZsdt1142.add(insertZsdt1142.get(i*count+j));
						}
						sbi0094D.insertBatchZsdt1142(tmpZsdt1142);
					}
					
					if(last > 0) {
						List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
						for(int j=0; j < last; j++) {
							tmpZsdt1142.add(insertZsdt1142.get(pre*count+j));
						}
						sbi0094D.insertBatchZsdt1142(tmpZsdt1142);
					}
				}
			}

			logger.debug("updateZsdt1142.size():"+updateZsdt1142.size());
			if(updateZsdt1142.size() > 0 ) {
				int count = 500;
				if(updateZsdt1142.size() <= count) {
					sbi0094D.updateBatchZsdt1142(updateZsdt1142);
				} else {
					int pre = updateZsdt1142.size() / count;
					int last = updateZsdt1142.size() % count;
					for(int i=0; i < pre; i++) {
						List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
						for(int j=0; j < count; j++) {
							tmpZsdt1142.add(updateZsdt1142.get(i*count+j));
						}
						sbi0094D.updateBatchZsdt1142(tmpZsdt1142);
					}
					
					if(last > 0) {
						List<ZSDT1142> tmpZsdt1142 = new ArrayList<ZSDT1142>();
						for(int j=0; j < last; j++) {
							tmpZsdt1142.add(updateZsdt1142.get(pre*count+j));
						}
						sbi0094D.updateBatchZsdt1142(tmpZsdt1142);
					}
				}
			}
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.commit();
			session.close();
		}
	}	
}

