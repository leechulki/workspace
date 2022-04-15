package hdel.sd.sbp.service;

/**
 * 사업계획 현황(보수집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.service.SrmService;
import hdel.sd.sbp.dao.Sbp0120D;
import hdel.sd.sbp.domain.Sbp0120ParamVO;
import hdel.sd.sbp.domain.Sbp0120VO;
import hdel.sd.smp.control.SmpComC;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Sbp0120S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Sbp0120D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Sbp0120D.class);
		
	}
 

	// 조회
	public List<Sbp0120VO> find(Sbp0120ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");

		// 검색조건중 [부서]의 값이 있을경우
		String vkburYn = "N";
		if ( !"".equals( paramV.getVKBUR_F() ) || !"".equals( paramV.getVKBUR_T() ) )
		{
			vkburYn = "Y";
			
			// 월별조회
			List<Sbp0120VO> zpym_list = null;

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
			
			// 최대는 2년(24개월)로 월별리스트를 담을 배열을 선언
			String[] zpym_list_arr = new String[40];
			
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
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1023.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 매출
							else if ( "2".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1024.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 청구
							else if ( "3".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1025.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 수금
							else
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1026.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							//mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1023.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							mon[zpym_listNum_dummy] += "\n";
							// 수주
							if ( "1".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1023.SOFOCA)";
							}
							// 매출
							else if ( "2".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1024.NETWR_SA)";
							}
							// 청구
							else if ( "3".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1025.NETWR_RQ)";
							}
							// 수금
							else
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1026.COLBI)";
							}
						}

						// 반복년월이 존재하는지 상관없이  
						// monNot에 처리
						monNot[zpym_listNum_dummy] += "CASE";
						// 수주
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1023.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 매출
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1024.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 청구
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1025.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 수금
						else
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1026.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						//monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1023.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						monNot[zpym_listNum_dummy] += "\n";

						// 수주
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1023.SOFOCA)";
						}
						// 매출
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1024.NETWR_SA)";
						}
						// 청구
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1025.NETWR_RQ)";
						}
						// 수금
						else
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1026.COLBI)";
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
			    selectColumn += "\n ZSDT1023.MANDT -- 클라이언트 "; 
			    selectColumn += "\n ,ZSDT1023.ZPYEAR -- 편성년도 ";
			    selectColumn += "\n ,(SELECT BEZEI FROM SAPHEE.TVKBT   ";
			    selectColumn += "\n WHERE MANDT = '"+paramV.getMANDT()+"'  ";
			    selectColumn += "\n AND VKBUR = ZSDT1023.VKBUR AND SPRAS = '3')AS VKBUR -- 사업장  ";
			    selectColumn += "\n ,ZSDT1023.VKGRP -- 영업그룹 ";
			    selectColumn += "\n ,(SELECT NAME1 FROM SAPHEE.KNA1  ";
			    selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' AND KUNNR = ZSDT1023.ZAGNT   ";
			    selectColumn += "\n UNION ALL   ";
			    selectColumn += "\n SELECT NAME1   ";
			    selectColumn += "\n FROM SAPHEE.LFA1   ";
			    selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
			    selectColumn += "\n AND LIFNR = ZSDT1023.ZAGNT ) AS ZAGNT -- 협력사  "; 
			    selectColumn += "\n ,(SELECT NAME1 FROM SAPHEE.KNA1   ";
			    selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
			    selectColumn += "\n AND KTOKD = 'Z201' ";
			    selectColumn += "\n AND KUNNR = ZSDT1023.ZKUNNR )AS ZKUNNR -- 영업사원  ";
			    fromTable += "\n , 0 AS SOFOCA";
				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1023 ZSDT1023 -- 사업계획(보수수주) ";
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1023 ZSDT1023 -- 사업계획(보수수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1024 ZSDT1024 -- 사업계획(보수매출)  ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1023 ZSDT1023 -- 사업계획(보수수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1025 ZSDT1025	-- 사업계획(보수청구)  ";
				}
				// 수금
				else
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1023 ZSDT1023 -- 사업계획(보수수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1026 ZSDT1026-- 사업계획(보수수금)  ";
				}
			    fromTable += "\n WHERE 1=1 ";
			    fromTable += "\n AND ZSDT1023.MANDT = '"+paramV.getMANDT()+"' -- 클라이언트 ";
			    fromTable += "\n AND ZSDT1023.ZPYEAR = '"+paramV.getZPYEAR()+"' -- 편성년도 ";
				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1023.MANDT = ZSDT1024.MANDT -- 클라이언트 ";
				   fromTable += "\n AND ZSDT1023.PSPIDSV = ZSDT1024.PSPIDSV -- 보수프로젝트   ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1023.MANDT = ZSDT1025.MANDT -- 클라이언트 ";
				   fromTable += "\n AND ZSDT1023.PSPIDSV = ZSDT1025.PSPIDSV -- 보수프로젝트   ";
				}
				// 수금
				else
				{
				   fromTable += "\n AND ZSDT1023.MANDT = ZSDT1026.MANDT -- 클라이언트 ";
				   fromTable += "\n AND ZSDT1023.PSPIDSV = ZSDT1026.PSPIDSV -- 보수프로젝트   ";
				}
				// 부서
				if ( !"".equals( paramV.getVKBUR_F() ) && !"".equals( paramV.getVKBUR_T() ) )
				{
					where += "\n AND ZSDT1023.VKBUR BETWEEN '"+paramV.getVKBUR_F()+"' AND '"+paramV.getVKBUR_T()+"'";
				}
				else if ( !"".equals( paramV.getVKBUR_F() ) || !"".equals( paramV.getVKBUR_T() ) )
				{
					if ( !"".equals( paramV.getVKBUR_F() ) )
					{
						where += "\n AND ZSDT1023.VKBUR = '"+paramV.getVKBUR_F()+"' ";
					}
					else if ( !"".equals( paramV.getVKBUR_T() ) )
					{
						where += "\n AND ZSDT1023.VKBUR = '"+paramV.getVKBUR_T()+"' ";
					}
					
				}
				// 팀
				if ( !"".equals( paramV.getVKGRP_F() ) && !"".equals( paramV.getVKGRP_T() ) )
				{
					where += "\n AND ZSDT1023.VKGRP BETWEEN '"+paramV.getVKGRP_F()+"' AND '"+paramV.getVKGRP_T()+"'";
				}
				else if ( !"".equals( paramV.getVKGRP_F() ) || !"".equals( paramV.getVKGRP_T() ) )
				{
					if ( !"".equals( paramV.getVKGRP_F() ) )
					{
						where += "\n AND ZSDT1023.VKGRP = '"+paramV.getVKGRP_F()+"' ";
					}
					else if ( !"".equals( paramV.getVKGRP_T() ) )
					{
						where += "\n AND ZSDT1023.VKGRP = '"+paramV.getVKGRP_T()+"' ";
					}
					
				}
				
				// 영업담당자
				if ( !"".equals( paramV.getZKUNNR() ) )
				{
					where += "\n AND ZSDT1023.ZKUNNR = '"+paramV.getZKUNNR()+"' ";
				}
					
				// 오더유형(판매유형문서)
				if ( !"".equals( paramV.getAUART() ) )
				{
					where += "\n AND ZSDT1023.AUART = '"+paramV.getAUART()+"' ";
				}
			    orderby += "\n GROUP BY ZSDT1023.MANDT -- 클라이언트  ";
			    orderby += "\n ,ZSDT1023.ZPYEAR -- 편성년도  ";
			    orderby += "\n ,ZSDT1023.VKBUR -- 사업장  ";
			    orderby += "\n ,ZSDT1023.VKGRP -- 영업그룹 ";
			    orderby += "\n ,ZSDT1023.ZAGNT -- 대리점 ";
			    orderby += "\n ,ZSDT1023.ZKUNNR -- 영업사원 ";
			    //orderby += "\n ,ZSDT1023.ZPYM -- 계획년월  ";

				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
				 	orderby += "\n ,ZSDT1023.ZPYM 			-- 계획년월  ";
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
				 	orderby += "\n ,ZSDT1024.ZSAYM 			-- 매출년월  ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1025.ZRQYM 			-- 청구년월  ";
				}
				// 수금
				else
				{
					orderby += "\n ,ZSDT1026.BYSYM 			-- 수금년월  ";
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
			List<Sbp0120VO> list = dao.SelectWaerk(paramV);

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
						sofocaVal = "SUM(ZSDT1023.SOFOCA)";
					}
					// 매출
					else if ( "2".equals(paramV.getWHERE()) )
					{
						sofocaVal = "SUM(ZSDT1024.NETWR_SA)";
					}
					// 청구
					else if ( "3".equals(paramV.getWHERE()) )
					{
						sofocaVal = "SUM(ZSDT1025.NETWR_RQ)";
					}
					// 수금
					else
					{
						sofocaVal = "SUM(ZSDT1026.COLBI)";
					}
					sofoca += " WHEN ZSDT1023.WAERK = '"+list.get(i).getWAERK()+"' THEN "+sofocaVal;
					sofoca += "\n";
				}
				sofoca += "END ";
			}
			else
			{
				sofoca 	= "0 AS SOFOCA";
			}
			
			paramV.setFILTER1(sofoca);
			//logger.info(" @@@@@@@@@@@@@@ filter query : "+sofoca);

			// 월별조회
			List<Sbp0120VO> zpym_list = null;

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
			String[] zpym_list_arr = new String[40];
			
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
							mon[zpym_listNum_dummy] += "CASE";							// 수주
							if ( "1".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1023.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 매출
							else if ( "2".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1024.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 청구
							else if ( "3".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1025.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// 수금
							else
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1026.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							mon[zpym_listNum_dummy] += "\n";
							mon[zpym_listNum_dummy] += paramV.getFILTER1();
						}

						// 반복년월이 존재하는지 상관없이  
						// monNot에 처리
						monNot[zpym_listNum_dummy] += "CASE";
						// 수주
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1023.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 매출
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1024.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 청구
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1025.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// 수금
						else
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1026.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						monNot[zpym_listNum_dummy] += "\n";

						// 수주
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1023.SOFOCA)";
						}
						// 매출
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1024.NETWR_SA)";
						}
						// 청구
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1025.NETWR_RQ)";
						}
						// 수금
						else
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1026.COLBI)";
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
			    selectColumn += "\n ZSDT1023.MANDT -- 클라이언트 "; 
			    selectColumn += "\n ,ZSDT1023.ZPYEAR -- 편성년도 ";
			    selectColumn += "\n ,(SELECT BEZEI FROM SAPHEE.TVKBT   ";
			    selectColumn += "\n WHERE MANDT = '"+paramV.getMANDT()+"'  ";
			    selectColumn += "\n AND VKBUR = ZSDT1023.VKBUR AND SPRAS = '3')AS VKBUR -- 사업장  ";
			    selectColumn += "\n ,ZSDT1023.VKGRP -- 영업그룹 ";
			    selectColumn += "\n ,(SELECT NAME1 FROM SAPHEE.KNA1  ";
			    selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' AND KUNNR = ZSDT1023.ZAGNT   ";
			    selectColumn += "\n UNION ALL   ";
			    selectColumn += "\n SELECT NAME1   ";
			    selectColumn += "\n FROM SAPHEE.LFA1   ";
			    selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
			    selectColumn += "\n AND LIFNR = ZSDT1023.ZAGNT ) AS ZAGNT -- 협력사  "; 
			    selectColumn += "\n ,(SELECT NAME1 FROM SAPHEE.KNA1   ";
			    selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
			    selectColumn += "\n AND KTOKD = 'Z201' ";
			    selectColumn += "\n AND KUNNR = ZSDT1023.ZKUNNR )AS ZKUNNR -- 영업사원  ";
		        fromTable += "\n ,'' AS WAERK ";
			    fromTable += "\n , 0 AS SOFOCA";
			    fromTable += "\n , 0 AS KRWUSD";
				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1023 ZSDT1023 -- 사업계획(보수수주) ";
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1023 ZSDT1023 -- 사업계획(보수수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1024 ZSDT1024 -- 사업계획(보수매출)  ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1023 ZSDT1023 -- 사업계획(보수수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1025 ZSDT1025	-- 사업계획(보수청구)  ";
				}
				// 수금
				else
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1023 ZSDT1023 -- 사업계획(보수수주) ";
				   fromTable += "\n ,SAPHEE.ZSDT1026 ZSDT1026-- 사업계획(보수수금)  ";
				}
			   fromTable += "\n WHERE 1=1 ";
			   fromTable += "\n AND ZSDT1023.MANDT = '"+paramV.getMANDT()+"' -- 클라이언트 ";
			   fromTable += "\n AND ZSDT1023.ZPYEAR = '"+paramV.getZPYEAR()+"' -- 편성년도 ";
				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1023.MANDT = ZSDT1024.MANDT -- 클라이언트 ";
				   fromTable += "\n AND ZSDT1023.PSPIDSV = ZSDT1024.PSPIDSV -- 보수프로젝트   ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1023.MANDT = ZSDT1025.MANDT -- 클라이언트 ";
				   fromTable += "\n AND ZSDT1023.PSPIDSV = ZSDT1025.PSPIDSV -- 보수프로젝트   ";
				}
				// 수금
				else
				{
				   fromTable += "\n AND ZSDT1023.MANDT = ZSDT1026.MANDT -- 클라이언트 ";
				   fromTable += "\n AND ZSDT1023.PSPIDSV = ZSDT1026.PSPIDSV -- 보수프로젝트   ";
				}
				// 부서
				if ( !"".equals( paramV.getVKBUR_F() ) && !"".equals( paramV.getVKBUR_T() ) )
				{
					where += "\n AND ZSDT1023.VKBUR BETWEEN '"+paramV.getVKBUR_F()+"' AND '"+paramV.getVKBUR_T()+"'";
				}
				else if ( !"".equals( paramV.getVKBUR_F() ) || !"".equals( paramV.getVKBUR_T() ) )
				{
					if ( !"".equals( paramV.getVKBUR_F() ) )
					{
						where += "\n AND ZSDT1023.VKBUR = '"+paramV.getVKBUR_F()+"' ";
					}
					else if ( !"".equals( paramV.getVKBUR_T() ) )
					{
						where += "\n AND ZSDT1023.VKBUR = '"+paramV.getVKBUR_T()+"' ";
					}
					
				}
				// 팀
				if ( !"".equals( paramV.getVKGRP_F() ) && !"".equals( paramV.getVKGRP_T() ) )
				{
					where += "\n AND ZSDT1023.VKGRP BETWEEN '"+paramV.getVKGRP_F()+"' AND '"+paramV.getVKGRP_T()+"'";
				}
				else if ( !"".equals( paramV.getVKGRP_F() ) || !"".equals( paramV.getVKGRP_T() ) )
				{
					if ( !"".equals( paramV.getVKGRP_F() ) )
					{
						where += "\n AND ZSDT1023.VKGRP = '"+paramV.getVKGRP_F()+"' ";
					}
					else if ( !"".equals( paramV.getVKGRP_T() ) )
					{
						where += "\n AND ZSDT1023.VKGRP = '"+paramV.getVKGRP_T()+"' ";
					}
					
				}
				
				// 영업담당자
				if ( !"".equals( paramV.getZKUNNR() ) )
				{
					where += "\n AND ZSDT1023.ZKUNNR = '"+paramV.getZKUNNR()+"' ";
				}
				
				// 오더유형(판매유형문서)
				if ( !"".equals( paramV.getAUART() ) )
				{
					where += "\n AND ZSDT1023.AUART = '"+paramV.getAUART()+"' ";
				}
			    orderby += "\n GROUP BY ZSDT1023.MANDT -- 클라이언트  ";
			    orderby += "\n ,ZSDT1023.ZPYEAR -- 편성년도  ";
			    orderby += "\n ,ZSDT1023.VKBUR -- 사업장  ";
			    orderby += "\n ,ZSDT1023.VKGRP -- 영업그룹 ";
			    orderby += "\n ,ZSDT1023.ZAGNT -- 대리점 ";
			    orderby += "\n ,ZSDT1023.ZKUNNR -- 영업사원 ";

				// 수주
				if ( "1".equals(paramV.getWHERE()) )
				{
				 	orderby += "\n ,ZSDT1023.ZPYM 			-- 계획년월  ";
				}
				// 매출
				else if ( "2".equals(paramV.getWHERE()) )
				{
				 	orderby += "\n ,ZSDT1024.ZSAYM 			-- 매출년월  ";
				}
				// 청구
				else if ( "3".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1025.ZRQYM 			-- 청구년월  ";
				}
				// 수금
				else
				{
					orderby += "\n ,ZSDT1026.BYSYM 			-- 수금년월  ";
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
