package hdel.sd.sbp.service;

/**
 * 사업계획 현황(집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.service.SrmService;
import hdel.sd.sbp.dao.Sbp0110D;
import hdel.sd.sbp.domain.Sbp0110ParamVO;
import hdel.sd.sbp.domain.Sbp0110VO;
import hdel.sd.smp.control.SmpComC;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Sbp0110S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Sbp0110D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Sbp0110D.class);
		
	}

	// 조회
	public List<Sbp0110VO> find(Sbp0110ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		

		// 검색조건중 [부서]의 값이 있을경우
		String vkburYn = "N";
		if ( !"".equals( paramV.getVKBUR_F() ) || !"".equals( paramV.getVKBUR_T() ) )
		{
			vkburYn = "Y";

			// 월별조회
			List<Sbp0110VO> zpym_list = null;

			// 수주
			if ( "1".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectOrderZpym(paramV);
			}
			// 매출
			else if ( "2".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectSalesZpym(paramV);
			}
			// 청구
			else if ( "3".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectChargeZpym(paramV);
			}
			// 수금
			else
			{
				zpym_list = dao.SelectCollectionZpym(paramV);
			}
			
			//logger.info(" @@@@@ zpym_list size : " +zpym_list.size());
			//logger.info(" @@@@@ paramV.getWHERE()  : " +paramV.getWHERE() );
			//for ( int aa = 0 ; aa < zpym_list.size() ; aa++ )
			//{
			//	logger.info(" @@@@@ zpym_list size : " +zpym_list.get(aa).getZPYM() );
			//}
			
			// 최대는 2년(24개월)로 월별리스트를 담을 배열을 선언
			String[] zpym_list_arr = new String[60];
			
			// 월별리스트가 한건이상 존재시
			if ( zpym_list.size() > 0 )
			{
				// 최초 년월
				String start_zpym = zpym_list.get(0).getZPYM();
				// 최대 년월
				String end_zpym = zpym_list.get(zpym_list.size()-1).getZPYM();
				
				//logger.info(" @@@@@ start_zpym : " +start_zpym );
				//logger.info(" @@@@@ end_zpym : " +end_zpym );
				
				// 배열순서를 정할 변수
				int cnt = 0;

				// 년도 차이수
				int between_zpym = Integer.parseInt( end_zpym.substring(0, 4) ) - Integer.parseInt( start_zpym.substring(0, 4) );
				
				// 최초년월부터 최초년에 해당하는 12월까지 반복하여 년월을 구함
				for ( int a = Integer.parseInt( zpym_list.get(0).getZPYM().substring(4) ) ; a < 13 ; a++ )
				{
					zpym_list_arr[cnt] = start_zpym.substring(0, 4) + SmpComC.lpad(Integer.toString(a), 2, "0");//Integer.toString(a);
					cnt++;
				}
				
				//logger.info(" @@@@@ between_zpym : " +between_zpym );
				// 년도차이수만큼 루프
				if ( between_zpym == 1 )
				{
					// 최대년월은 항상 12개월로 fix한다.
					for ( int b = 1 ; b < 13 ; b++ )
					{
						zpym_list_arr[cnt] = end_zpym.substring(0, 4) + SmpComC.lpad(Integer.toString(b), 2, "0");
						cnt++;
					}
				}
				else if ( between_zpym > 1 )
				{
					for ( int aa = 0 ; aa < between_zpym ; aa++ )
					{
						int repl = Integer.parseInt( start_zpym.substring(0, 4) ) +1;
						start_zpym = Integer.toString( repl );
						//logger.info(" @@@@@ between_zpym start_zpym: " +start_zpym );
						
						// 최대년월은 항상 12개월로 fix한다.
						for ( int b = 1 ; b < 13 ; b++ )
						{
							zpym_list_arr[cnt] = start_zpym.substring(0, 4) + SmpComC.lpad(Integer.toString(b), 2, "0");
							cnt++;
						}
					}
				}
			}
			// cnt는 자동으로 최초년월부터 최대년월까지의 나래비가 된다.(최소월부터 최대월까지의 총갯수)
			
			//logger.info(" @@@@@ zpym_list_arr : " +zpym_list_arr.toString() );

			// 최소월부터 최대월까지의 총갯수 중 데이터가 존재하는 월
			String mon[] =  new String[zpym_list_arr.length];
			// 반복하면서 초기화 처리를 해준다. 꼭 필요함.
			for ( int xx = 0 ; xx < zpym_list_arr.length ; xx++ )
			{
				mon[xx] = "";
			}
			// 최소월부터 최대월까지의 총갯수 중 데이터가 존재하지 않는 월
			// 초기화처리 필요하지 않음.
			String monNot[] =  new String[zpym_list_arr.length];

			// 최소월부터 최대월까지의 총갯수가 한건이상 존재시
			if ( zpym_list_arr.length > 0 )
			{
				// 실제 데이터 건수만큼 루프
				for ( int zpym_listNum_real = 0 ; zpym_listNum_real < zpym_list.size() ; zpym_listNum_real++ )
				{
					// 존재하는 년월일경우
					String currentMon = zpym_list.get(zpym_listNum_real).getZPYM();
					//logger.info(" @@@@@ currentMon : " +currentMon );
					
					// dummy포함 총건수만큼 루프
					for ( int zpym_listNum_dummy = 0 ; zpym_listNum_dummy < zpym_list_arr.length ; zpym_listNum_dummy++ )
					{
						// 최소월부터 최대월까지의 총갯수 중 데이터가 존재하지 않는 월 - 초기화
						monNot[zpym_listNum_dummy] = "";
						
						// 반복년월이 존재하는 년월과 같을경우 
						// mon에 처리
						if ( currentMon.equals(zpym_list_arr[zpym_listNum_dummy]) )
						{
							mon[zpym_listNum_dummy] += "CASE";
							// 수주
							if ( "1".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 매출
							else if ( "2".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1013.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 청구
							else if ( "3".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1014.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 수금
							else
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1015.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							//mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							mon[zpym_listNum_dummy] += "\n";
							// 수주
							if ( "1".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1012.SOFOCA)";
							}
							// 매출
							else if ( "2".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1013.NETWR_SA)";
							}
							// 청구
							else if ( "3".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1014.NETWR_RQ)";
							}
							// 수금
							else
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1015.COLBI)";
							}
						}

						// 반복년월이 존재하는지 상관없이  
						// monNot에 처리
						monNot[zpym_listNum_dummy] += "CASE";
						// 수주
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 매출
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1013.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 청구
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1014.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 수금
						else
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1015.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						//monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						monNot[zpym_listNum_dummy] += "\n";
						
						// 수주
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1012.SOFOCA)";
						}
						// 매출
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1013.NETWR_SA)";
						}
						// 청구
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1014.NETWR_RQ)";
						}
						// 수금
						else
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1015.COLBI)";
						}
						
					}
				}
				// 월별 데이타가 존재하지 않을경우 
				// 해당월별 데이타를 dummy처리 하기 위함
				String rs 			= "";
				String rsUnion 		= "";
				//logger.info("@@@@@ 년도: "+lst.get(0).getZPYM().substring(0, 4));

				// 반복쿼리 생성용
				String selectColumn = "";
				String fromTable 	= "";
			    String where 		= "";
				String orderby 		= "";
				
			    selectColumn += "\n UNION ALL ";
			    selectColumn += "\n ";
			    selectColumn += "\n SELECT ";
			    selectColumn += "\n ZSDT1012.MANDT -- 클라이언트 "; 
			    selectColumn += "\n ,ZSDT1012.ZPYEAR -- 편성년도 ";
			    selectColumn += "\n ,(SELECT BEZEI FROM SAPHEE.TVKBT   ";
			    selectColumn += "\n WHERE MANDT = '"+paramV.getMANDT()+"'  ";
			    selectColumn += "\n AND VKBUR = ZSDT1012.VKBUR AND SPRAS = '3')AS VKBUR -- 사업장  ";
			    selectColumn += "\n ,ZSDT1012.VKGRP -- 영업그룹 ";
			    selectColumn += "\n ,(SELECT NAME1   ";
			    selectColumn += "\n FROM SAPHEE.LFA1   ";
			    selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
			    selectColumn += "\n AND LIFNR = ZSDT1012.ZAGNT ) AS ZAGNT -- 협력사  "; 
			    selectColumn += "\n ,(SELECT NAME1 FROM SAPHEE.KNA1   ";
			    selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
			    selectColumn += "\n AND KTOKD = 'Z201' ";
			    selectColumn += "\n AND KUNNR = ZSDT1012.ZKUNNR )AS ZKUNNR -- 영업사원  ";
			    fromTable += "\n , 0 AS SOFOCA";
				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 -- 사업계획(수주) ";
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 -- 사업계획(수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1013 ZSDT1013 -- 사업계획(매출)  ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 -- 사업계획(수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1014 ZSDT1014	-- 사업계획(청구)  ";
				}
				// 수금
				else
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 -- 사업계획(수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1015 ZSDT1015-- 사업계획(수금)  ";
				}
			    fromTable += "\n WHERE 1=1 ";
			    fromTable += "\n AND ZSDT1012.MANDT = '"+paramV.getMANDT()+"' -- 클라이언트 ";
			    fromTable += "\n AND ZSDT1012.ZPYEAR = '"+paramV.getZPYEAR()+"' -- 편성년도 ";
			   
				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1013.MANDT -- 클라이언트 ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1013.ZBPNNR -- 사업계획번호   ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1014.MANDT -- 클라이언트 ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1014.ZBPNNR -- 사업계획번호   ";
				}
				// 수금
				else
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1015.MANDT -- 클라이언트 ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1015.ZBPNNR -- 사업계획번호   ";
				}
				// 부서
				if ( !"".equals( paramV.getVKBUR_F() ) && !"".equals( paramV.getVKBUR_T() ) )
				{
					where += "\n AND ZSDT1012.VKBUR BETWEEN '"+paramV.getVKBUR_F()+"' AND '"+paramV.getVKBUR_T()+"'";
				}
				else if ( !"".equals( paramV.getVKBUR_F() ) || !"".equals( paramV.getVKBUR_T() ) )
				{
					if ( !"".equals( paramV.getVKBUR_F() ) )
					{
						where += "\n AND ZSDT1012.VKBUR = '"+paramV.getVKBUR_F()+"' ";
					}
					else if ( !"".equals( paramV.getVKBUR_T() ) )
					{
						where += "\n AND ZSDT1012.VKBUR = '"+paramV.getVKBUR_T()+"' ";
					}
					
				}
				// 팀
				if ( !"".equals( paramV.getVKGRP_F() ) && !"".equals( paramV.getVKGRP_T() ) )
				{
					where += "\n AND ZSDT1012.VKGRP BETWEEN '"+paramV.getVKGRP_F()+"' AND '"+paramV.getVKGRP_T()+"'";
				}
				else if ( !"".equals( paramV.getVKGRP_F() ) || !"".equals( paramV.getVKGRP_T() ) )
				{
					if ( !"".equals( paramV.getVKGRP_F() ) )
					{
						where += "\n AND ZSDT1012.VKGRP = '"+paramV.getVKGRP_F()+"' ";
					}
					else if ( !"".equals( paramV.getVKGRP_T() ) )
					{
						where += "\n AND ZSDT1012.VKGRP = '"+paramV.getVKGRP_T()+"' ";
					}
					
				}
					
				// 영업담당자
				if ( !"".equals( paramV.getZKUNNR() ) )
				{
					where += "\n AND ZSDT1012.ZKUNNR = '"+paramV.getZKUNNR()+"' ";
				}
					
				// 오더유형(판매유형문서)
				if ( !"".equals( paramV.getAUART() ) )
				{
					where += "\n AND ZSDT1012.AUART = '"+paramV.getAUART()+"' ";
				}
				orderby += "\n GROUP BY ZSDT1012.MANDT 	-- 클라이언트  ";
				orderby += "\n ,ZSDT1012.ZPYEAR 		-- 편성년도  ";
				orderby += "\n ,ZSDT1012.VKBUR 			-- 사업장  ";
				orderby += "\n ,ZSDT1012.VKGRP 			-- 영업그룹 ";
				orderby += "\n ,ZSDT1012.ZAGNT 			-- 협력사 ";
				orderby += "\n ,ZSDT1012.ZKUNNR 		-- 영업사원 ";
				//orderby += "\n ,ZSDT1012.ZPYM 			-- 계획년월  ";

				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1012.ZPYM 			-- 계획년월  ";
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1013.ZSAYM 			-- 매출년월  ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1014.ZRQYM 			-- 청구년월  ";
				}
				// 수금
				else
				{
					orderby += "\n ,ZSDT1015.BYSYM 			-- 수금년월  ";
				}
				

				// 최소년월에서 최대년월까지의 정보중
				// null이 아니고
				// 데이터가 존재하는 정보는
				// case문으로 생성하고
				// 데이터가 존재하지 않는정보는
				// dummy로생성하기위함이다.
				for ( int unionLoop = 0 ; unionLoop < zpym_list_arr.length ; unionLoop++ )
				{
					if ( zpym_list_arr[unionLoop] != null)
					{
						String rsYear = zpym_list_arr[unionLoop];//.substring(0, 4);
						//logger.info("@@@@@ rsYear: "+rsYear);
						//logger.info("@@@@@  mon[unionLoop].equals: "+ mon[unionLoop].equals(""));
						
						if ( mon[unionLoop].equals("") )
						{
							if ( unionLoop != 0 )
							{
								rs += " ELSE ";
							}
							//logger.info("@@@@@ monNot 순서: "+monNot[unionLoop]);
							rs += monNot[unionLoop];
							rsUnion += selectColumn; 
							rsUnion += "\n ,'"+rsYear+"' AS ZPYM ";
							rsUnion += fromTable;
							rsUnion += where;
							rsUnion += orderby;
						}
						else 
						{
							if ( unionLoop != 0 )
							{
								rs += " ELSE ";
							}
							//logger.info("@@@@@ mon 순서: "+mon[unionLoop]);
							rs += mon[unionLoop];
						}
					}
				}
				for ( int unionLoopend = 0 ; unionLoopend < zpym_list_arr.length ; unionLoopend++ )
				{
					if ( zpym_list_arr[unionLoopend] != null)
					{
						rs += " END ";
					}
				}
				rs += " AS SOFOCA ";
		
				// sofoca(예상액) 동적 쿼리 문자열
				paramV.setFILTER2(rs);
				//logger.info("@@@@@ rs: "+rs);
				
				// union 동적 쿼리 문자열
				paramV.setFILTER3(rsUnion);
				//logger.info("@@@@@ rsUnion: "+rsUnion);
			}
			// 최소월부터 최대월까지의 총갯수가 미존재시
			else
			{
				// sofoca(예상액) 동적 쿼리 문자열
				paramV.setFILTER2("0 AS SOFOCA");
				
				// union 동적 쿼리 문자열
				paramV.setFILTER3("");
			}
			
		}
		// 부서를 선택하지 않았을 경우
		else
		{

			// [WARERK - 통화]컬럼정보에 따라 컬럼을 동적생성하여
			// 컬럼정보별 소계컬럼을 처리
			List<Sbp0110VO> list = dao.SelectWaerk(paramV);

			String sofoca 	= null;
			if ( list.size() > 0 )
			{
				sofoca 	= "CASE";
				for ( int i = 0 ; i < list.size() ; i++ )
				{
					String sofocaVal = null;
					// 수주
					if ( "1".equals(paramV.getWHERE()) )
					{
						sofocaVal = "SUM(ZSDT1012.SOFOCA)";
					}
					// 매출
					else if ( "2".equals(paramV.getWHERE()) )
					{
						sofocaVal = "SUM(ZSDT1013.NETWR_SA)";
					}
					// 청구
					else if ( "3".equals(paramV.getWHERE()) )
					{
						sofocaVal = "SUM(ZSDT1014.NETWR_RQ)";
					}
					// 수금
					else
					{
						sofocaVal = "SUM(ZSDT1015.COLBI)";
					}
					sofoca += " WHEN ZSDT1012.WAERK = '"+list.get(i).getWAERK()+"' THEN "+sofocaVal;
					sofoca += "\n";
				}
				sofoca += "END ";
			}
			else
			{
				sofoca 	= "0 AS SOFOCA";
			}
			
			// 통화별 쿼리
			paramV.setFILTER1(sofoca);
			//logger.info(" @@@@@@@@@@@@@@ filter query : "+sofoca);

			// 월별조회
			List<Sbp0110VO> zpym_list = null;

			// 수주
			if ( "1".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectOrderZpym(paramV);
			}
			// 매출
			else if ( "2".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectSalesZpym(paramV);
			}
			// 청구
			else if ( "3".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectChargeZpym(paramV);
			}
			// 수금
			else
			{
				zpym_list = dao.SelectCollectionZpym(paramV);
			}
			
			//logger.info(" @@@@@ zpym_list size : " +zpym_list.size());
			
			// 월별리스트를 담을 배열을 선언
			String[] zpym_list_arr = new String[60];
			
			// 월별리스트가 한건이상 존재시
			if ( zpym_list.size() > 0 )
			{
				// 최초 년월
				String start_zpym = zpym_list.get(0).getZPYM();
				// 최대 년월
				String end_zpym = zpym_list.get(zpym_list.size()-1).getZPYM();
				
				//logger.info(" @@@@@ start_zpym : " +start_zpym );
				//logger.info(" @@@@@ end_zpym : " +end_zpym );
				
				// 배열순서를 정할 변수
				int cnt = 0;
				
				// 년도 차이수
				int between_zpym = Integer.parseInt( end_zpym.substring(0, 4) ) - Integer.parseInt( start_zpym.substring(0, 4) );
				
				// 최초년월부터 최초년에 해당하는 12월까지 반복하여 년월을 구함
				for ( int a = Integer.parseInt( zpym_list.get(0).getZPYM().substring(4) ) ; a < 13 ; a++ )
				{
					zpym_list_arr[cnt] = start_zpym.substring(0, 4) + SmpComC.lpad(Integer.toString(a), 2, "0");//Integer.toString(a);
					cnt++;
				}

				//logger.info(" @@@@@ between_zpym : " +between_zpym );
				// 년도차이수만큼 루프
				if ( between_zpym == 1 )
				{
					// 최대년월은 항상 12개월로 fix한다.
					for ( int b = 1 ; b < 13 ; b++ )
					{
						zpym_list_arr[cnt] = end_zpym.substring(0, 4) + SmpComC.lpad(Integer.toString(b), 2, "0");
						cnt++;
					}
				}
				else if ( between_zpym > 1 )
				{
					for ( int aa = 0 ; aa < between_zpym ; aa++ )
					{
						int repl = Integer.parseInt( start_zpym.substring(0, 4) ) +1;
						start_zpym = Integer.toString( repl );
						//logger.info(" @@@@@ between_zpym start_zpym: " +start_zpym );
						
						// 최대년월은 항상 12개월로 fix한다.
						for ( int b = 1 ; b < 13 ; b++ )
						{
							zpym_list_arr[cnt] = start_zpym.substring(0, 4) + SmpComC.lpad(Integer.toString(b), 2, "0");
							cnt++;
						}
					}
				}
				
				
			}
			// cnt는 자동으로 최초년월부터 최대년월까지의 나래비가 된다.(최소월부터 최대월까지의 총갯수)
			
			//logger.info(" @@@@@ zpym_list_arr : " +zpym_list_arr.toString() );

			// 최소월부터 최대월까지의 총갯수 중 데이터가 존재하는 월
			String mon[] =  new String[zpym_list_arr.length];
			// 반복하면서 초기화 처리를 해준다. 꼭 필요함.
			for ( int xx = 0 ; xx < zpym_list_arr.length ; xx++ )
			{
				mon[xx] = "";
			}
			// 최소월부터 최대월까지의 총갯수 중 데이터가 존재하지 않는 월
			// 초기화처리 필요하지 않음.
			String monNot[] =  new String[zpym_list_arr.length];
			
			// 최소월부터 최대월까지의 총갯수가 한건이상 존재시
			if ( zpym_list_arr.length > 0 )
			{
				
				// 실제 데이터 건수만큼 루프
				for ( int zpym_listNum_real = 0 ; zpym_listNum_real < zpym_list.size() ; zpym_listNum_real++ )
				{
					// 존재하는 년월일경우
					String currentMon = zpym_list.get(zpym_listNum_real).getZPYM();
					//logger.info(" @@@@@ currentMon : " +currentMon );
					
					// dummy포함 총건수만큼 루프
					for ( int zpym_listNum_dummy = 0 ; zpym_listNum_dummy < zpym_list_arr.length ; zpym_listNum_dummy++ )
					{
		
						// 최소월부터 최대월까지의 총갯수 중 데이터가 존재하지 않는 월 - 초기화
						monNot[zpym_listNum_dummy] = "";
						
						// 반복년월이 존재하는 년월과 같을경우 
						// mon에 처리
						if ( currentMon.equals(zpym_list_arr[zpym_listNum_dummy]) )
						{
							mon[zpym_listNum_dummy] += " CASE";
							// 수주
							if ( "1".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 매출
							else if ( "2".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1013.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 청구
							else if ( "3".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1014.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 수금
							else
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1015.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							mon[zpym_listNum_dummy] += "\n";
							mon[zpym_listNum_dummy] += paramV.getFILTER1();
						}
						
						// 반복년월이 존재하는지 상관없이  
						// monNot에 처리
						monNot[zpym_listNum_dummy] += " CASE";
						// 수주
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 매출
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1013.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 청구
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1014.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 수금
						else
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1015.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						monNot[zpym_listNum_dummy] += "\n";

						// 수주
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1012.SOFOCA)";
						}
						// 매출
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1013.NETWR_SA)";
						}
						// 청구
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1014.NETWR_RQ)";
						}
						// 수금
						else
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1015.COLBI)";
						}
						
					}
				}
				

				// 월별 데이타가 존재하지 않을경우 
				// 해당월별 데이타를 dummy처리 하기 위함
				String rs 			= "";
				String rsUnion 		= "";
				//logger.info("@@@@@ 년도: "+zpym_list.get(0).getZPYM().substring(0, 4));

				// 반복쿼리 생성용
				String selectColumn = "";
				String fromTable 	= "";
			    String where 		= "";
				String orderby 		= "";
				
				selectColumn += "\n UNION ALL ";
				selectColumn += "\n ";
				selectColumn += "\n SELECT ";
				selectColumn += "\n ZSDT1012.MANDT 									-- 클라이언트 "; 
				selectColumn += "\n ,ZSDT1012.ZPYEAR 								-- 편성년도 ";
				selectColumn += "\n ,(SELECT BEZEI FROM SAPHEE.TVKBT   ";
				selectColumn += "\n WHERE MANDT = '"+paramV.getMANDT()+"'  ";
				selectColumn += "\n AND VKBUR = ZSDT1012.VKBUR AND SPRAS = '3')AS VKBUR 			-- 사업장  ";
				selectColumn += "\n ,ZSDT1012.VKGRP 								-- 영업그룹 ";
				selectColumn += "\n ,( SELECT NAME1   ";
				selectColumn += "\n FROM SAPHEE.LFA1   ";
				selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
				selectColumn += "\n AND LIFNR = ZSDT1012.ZAGNT ) AS ZAGNT 			-- 협력사  "; 
				selectColumn += "\n ,(SELECT NAME1 FROM SAPHEE.KNA1   ";
				selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
				selectColumn += "\n AND KTOKD = 'Z201' ";
				selectColumn += "\n AND KUNNR = ZSDT1012.ZKUNNR )AS ZKUNNR 			-- 영업사원  ";
				fromTable += "\n , '' AS WAERK ";
				fromTable += "\n , 0 AS SOFOCA";
				fromTable += "\n , 0 AS KRWUSD";
				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 					-- 사업계획(수주) ";
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 					-- 사업계획(수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1013 ZSDT1013 						-- 사업계획(매출)  ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 					-- 사업계획(수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1014 ZSDT1014						-- 사업계획(청구)  ";
				}
				// 수금
				else
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 					-- 사업계획(수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1015 ZSDT1015						-- 사업계획(수금)  ";
					}
				   fromTable += "\n WHERE 1=1 ";
				   fromTable += "\n AND ZSDT1012.MANDT = '"+paramV.getMANDT()+"' 	-- 클라이언트 ";
				   fromTable += "\n AND ZSDT1012.ZPYEAR = '"+paramV.getZPYEAR()+"' 	-- 편성년도 ";
				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1013.MANDT 			-- 클라이언트 ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1013.ZBPNNR 			-- 사업계획번호   ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1014.MANDT 			-- 클라이언트 ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1014.ZBPNNR 			-- 사업계획번호   ";
				}
				// 수금
				else
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1015.MANDT 			-- 클라이언트 ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1015.ZBPNNR 			-- 사업계획번호   ";
				}
				// 부서
				if ( !"".equals( paramV.getVKBUR_F() ) && !"".equals( paramV.getVKBUR_T() ) )
				{
					where += "\n AND ZSDT1012.VKBUR BETWEEN '"+paramV.getVKBUR_F()+"' AND '"+paramV.getVKBUR_T()+"'";
				}
				else if ( !"".equals( paramV.getVKBUR_F() ) || !"".equals( paramV.getVKBUR_T() ) )
				{
					if ( !"".equals( paramV.getVKBUR_F() ) )
					{
						where += "\n AND ZSDT1012.VKBUR = '"+paramV.getVKBUR_F()+"' ";
					}
					else if ( !"".equals( paramV.getVKBUR_T() ) )
					{
						where += "\n AND ZSDT1012.VKBUR = '"+paramV.getVKBUR_T()+"' ";
					}
					
				}
				// 팀
				if ( !"".equals( paramV.getVKGRP_F() ) && !"".equals( paramV.getVKGRP_T() ) )
				{
					where += "\n AND ZSDT1012.VKGRP BETWEEN '"+paramV.getVKGRP_F()+"' AND '"+paramV.getVKGRP_T()+"'";
				}
				else if ( !"".equals( paramV.getVKGRP_F() ) || !"".equals( paramV.getVKGRP_T() ) )
				{
					if ( !"".equals( paramV.getVKGRP_F() ) )
					{
						where += "\n AND ZSDT1012.VKGRP = '"+paramV.getVKGRP_F()+"' ";
					}
					else if ( !"".equals( paramV.getVKGRP_T() ) )
					{
						where += "\n AND ZSDT1012.VKGRP = '"+paramV.getVKGRP_T()+"' ";
					}
					
				}
				
				// 영업담당자
				if ( !"".equals( paramV.getZKUNNR() ) )
				{
					where += "\n AND ZSDT1012.ZKUNNR = '"+paramV.getZKUNNR()+"' ";
				}
				
				// 오더유형(판매유형문서)
				if ( !"".equals( paramV.getAUART() ) )
				{
					where += "\n AND ZSDT1012.AUART = '"+paramV.getAUART()+"' ";
				}
				orderby += "\n GROUP BY ZSDT1012.MANDT 	-- 클라이언트  ";
				orderby += "\n ,ZSDT1012.ZPYEAR 		-- 편성년도  ";
				orderby += "\n ,ZSDT1012.VKBUR 			-- 사업장  ";
				orderby += "\n ,ZSDT1012.VKGRP			-- 영업그룹 ";
				orderby += "\n ,ZSDT1012.ZAGNT 			-- 협력사 ";
				orderby += "\n ,ZSDT1012.ZKUNNR 		-- 영업사원 ";
				
				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1012.ZPYM 			-- 계획년월  ";
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1013.ZSAYM 			-- 매출년월  ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1014.ZRQYM 			-- 청구년월  ";
				}
				// 수금
				else
				{
					orderby += "\n ,ZSDT1015.BYSYM 			-- 수금년월  ";
				}
				
				// 최소년월에서 최대년월까지의 정보중
				// null이 아니고
				// 데이터가 존재하는 정보는
				// case문으로 생성하고
				// 데이터가 존재하지 않는정보는
				// dummy로생성하기위함이다.
				for ( int unionLoop = 0 ; unionLoop < zpym_list_arr.length ; unionLoop++ )
				{
					if ( zpym_list_arr[unionLoop] != null)
					{
						String rsYear = zpym_list_arr[unionLoop];//.substring(0, 4);
						//logger.info("@@@@@ rsYear: "+rsYear);
						//logger.info("@@@@@  mon[unionLoop].equals: "+ mon[unionLoop].equals(""));
						
						if ( mon[unionLoop].equals("") )
						{
							if ( unionLoop != 0 )
							{
								rs += " ELSE ";
							}
							//logger.info("@@@@@ monNot 순서: "+monNot[unionLoop]);
							rs += monNot[unionLoop];
							rsUnion += selectColumn; 
							rsUnion += "\n ,'"+rsYear+"' AS ZPYM ";
							rsUnion += fromTable;
							rsUnion += where;
							rsUnion += orderby;
						}
						else 
						{
							if ( unionLoop != 0 )
							{
								rs += " ELSE ";
							}
							//logger.info("@@@@@ mon 순서: "+mon[unionLoop]);
							rs += mon[unionLoop];
						}
					}
				}
				for ( int unionLoopend = 0 ; unionLoopend < zpym_list_arr.length ; unionLoopend++ )
				{
					if ( zpym_list_arr[unionLoopend] != null)
					{
						rs += " END ";
					}
				}
				rs += " AS SOFOCA ";

				// sofoca(예상액) 동적 쿼리 문자열
				paramV.setFILTER2(rs);
				//logger.info("@@@@@ rs: "+rs);
				
				// union 동적 쿼리 문자열
				paramV.setFILTER3(rsUnion);
				//logger.info("@@@@@ rsUnion: "+rsUnion);	
					
			}
			// 최소월부터 최대월까지의 총갯수가 미존재시
			else
			{
				// sofoca(예상액) 동적 쿼리 문자열
				paramV.setFILTER2("0 AS SOFOCA");
				
				// union 동적 쿼리 문자열
				paramV.setFILTER3("");
			}
			
	
		}// 부서가 없을경우 end

			
		// 수주
		if ( "1".equals(paramV.getWHERE()) && "N".equals(vkburYn) )
		{
			return dao.SelectOrder(paramV);
		}
		else if ( "1".equals(paramV.getWHERE()) && "Y".equals(vkburYn) )
		{
			return dao.SelectOrderExitWaerk(paramV);
		}
		// 매출
		else if ( "2".equals(paramV.getWHERE()) && "N".equals(vkburYn) )
		{
			return dao.SelectSales(paramV);
		}
		else if ( "2".equals(paramV.getWHERE()) && "Y".equals(vkburYn) )
		{
			return dao.SelectSalesExitWaerk(paramV);
		}
		// 청구
		else if ( "3".equals(paramV.getWHERE()) && "N".equals(vkburYn) )
		{
			return dao.SelectCharge(paramV);
		}
		else if ( "3".equals(paramV.getWHERE()) && "Y".equals(vkburYn) )
		{
			return dao.SelectChargeExitWaerk(paramV);
		}
		// 수금
		else if ( "4".equals(paramV.getWHERE()) && "N".equals(vkburYn) )
		{
			return dao.SelectCollection(paramV);
		}
		else 
		{
			return dao.SelectCollectionExitWaerk(paramV);
		}
		
		
	}
	
	
	
	
	
}
