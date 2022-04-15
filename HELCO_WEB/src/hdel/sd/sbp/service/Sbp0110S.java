package hdel.sd.sbp.service;

/**
 * �����ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
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

	// ��ȸ
	public List<Sbp0110VO> find(Sbp0110ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		

		// �˻������� [�μ�]�� ���� �������
		String vkburYn = "N";
		if ( !"".equals( paramV.getVKBUR_F() ) || !"".equals( paramV.getVKBUR_T() ) )
		{
			vkburYn = "Y";

			// ������ȸ
			List<Sbp0110VO> zpym_list = null;

			// ����
			if ( "1".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectOrderZpym(paramV);
			}
			// ����
			else if ( "2".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectSalesZpym(paramV);
			}
			// û��
			else if ( "3".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectChargeZpym(paramV);
			}
			// ����
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
			
			// �ִ�� 2��(24����)�� ��������Ʈ�� ���� �迭�� ����
			String[] zpym_list_arr = new String[60];
			
			// ��������Ʈ�� �Ѱ��̻� �����
			if ( zpym_list.size() > 0 )
			{
				// ���� ���
				String start_zpym = zpym_list.get(0).getZPYM();
				// �ִ� ���
				String end_zpym = zpym_list.get(zpym_list.size()-1).getZPYM();
				
				//logger.info(" @@@@@ start_zpym : " +start_zpym );
				//logger.info(" @@@@@ end_zpym : " +end_zpym );
				
				// �迭������ ���� ����
				int cnt = 0;

				// �⵵ ���̼�
				int between_zpym = Integer.parseInt( end_zpym.substring(0, 4) ) - Integer.parseInt( start_zpym.substring(0, 4) );
				
				// ���ʳ������ ���ʳ⿡ �ش��ϴ� 12������ �ݺ��Ͽ� ����� ����
				for ( int a = Integer.parseInt( zpym_list.get(0).getZPYM().substring(4) ) ; a < 13 ; a++ )
				{
					zpym_list_arr[cnt] = start_zpym.substring(0, 4) + SmpComC.lpad(Integer.toString(a), 2, "0");//Integer.toString(a);
					cnt++;
				}
				
				//logger.info(" @@@@@ between_zpym : " +between_zpym );
				// �⵵���̼���ŭ ����
				if ( between_zpym == 1 )
				{
					// �ִ����� �׻� 12������ fix�Ѵ�.
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
						
						// �ִ����� �׻� 12������ fix�Ѵ�.
						for ( int b = 1 ; b < 13 ; b++ )
						{
							zpym_list_arr[cnt] = start_zpym.substring(0, 4) + SmpComC.lpad(Integer.toString(b), 2, "0");
							cnt++;
						}
					}
				}
			}
			// cnt�� �ڵ����� ���ʳ������ �ִ��������� ������ �ȴ�.(�ּҿ����� �ִ�������� �Ѱ���)
			
			//logger.info(" @@@@@ zpym_list_arr : " +zpym_list_arr.toString() );

			// �ּҿ����� �ִ�������� �Ѱ��� �� �����Ͱ� �����ϴ� ��
			String mon[] =  new String[zpym_list_arr.length];
			// �ݺ��ϸ鼭 �ʱ�ȭ ó���� ���ش�. �� �ʿ���.
			for ( int xx = 0 ; xx < zpym_list_arr.length ; xx++ )
			{
				mon[xx] = "";
			}
			// �ּҿ����� �ִ�������� �Ѱ��� �� �����Ͱ� �������� �ʴ� ��
			// �ʱ�ȭó�� �ʿ����� ����.
			String monNot[] =  new String[zpym_list_arr.length];

			// �ּҿ����� �ִ�������� �Ѱ����� �Ѱ��̻� �����
			if ( zpym_list_arr.length > 0 )
			{
				// ���� ������ �Ǽ���ŭ ����
				for ( int zpym_listNum_real = 0 ; zpym_listNum_real < zpym_list.size() ; zpym_listNum_real++ )
				{
					// �����ϴ� ����ϰ��
					String currentMon = zpym_list.get(zpym_listNum_real).getZPYM();
					//logger.info(" @@@@@ currentMon : " +currentMon );
					
					// dummy���� �ѰǼ���ŭ ����
					for ( int zpym_listNum_dummy = 0 ; zpym_listNum_dummy < zpym_list_arr.length ; zpym_listNum_dummy++ )
					{
						// �ּҿ����� �ִ�������� �Ѱ��� �� �����Ͱ� �������� �ʴ� �� - �ʱ�ȭ
						monNot[zpym_listNum_dummy] = "";
						
						// �ݺ������ �����ϴ� ����� ������� 
						// mon�� ó��
						if ( currentMon.equals(zpym_list_arr[zpym_listNum_dummy]) )
						{
							mon[zpym_listNum_dummy] += "CASE";
							// ����
							if ( "1".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// ����
							else if ( "2".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1013.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// û��
							else if ( "3".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1014.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// ����
							else
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1015.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							//mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							mon[zpym_listNum_dummy] += "\n";
							// ����
							if ( "1".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1012.SOFOCA)";
							}
							// ����
							else if ( "2".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1013.NETWR_SA)";
							}
							// û��
							else if ( "3".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1014.NETWR_RQ)";
							}
							// ����
							else
							{
								mon[zpym_listNum_dummy] += "SUM(ZSDT1015.COLBI)";
							}
						}

						// �ݺ������ �����ϴ��� �������  
						// monNot�� ó��
						monNot[zpym_listNum_dummy] += "CASE";
						// ����
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// ����
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1013.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// û��
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1014.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// ����
						else
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1015.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						//monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						monNot[zpym_listNum_dummy] += "\n";
						
						// ����
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1012.SOFOCA)";
						}
						// ����
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1013.NETWR_SA)";
						}
						// û��
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1014.NETWR_RQ)";
						}
						// ����
						else
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1015.COLBI)";
						}
						
					}
				}
				// ���� ����Ÿ�� �������� ������� 
				// �ش���� ����Ÿ�� dummyó�� �ϱ� ����
				String rs 			= "";
				String rsUnion 		= "";
				//logger.info("@@@@@ �⵵: "+lst.get(0).getZPYM().substring(0, 4));

				// �ݺ����� ������
				String selectColumn = "";
				String fromTable 	= "";
			    String where 		= "";
				String orderby 		= "";
				
			    selectColumn += "\n UNION ALL ";
			    selectColumn += "\n ";
			    selectColumn += "\n SELECT ";
			    selectColumn += "\n ZSDT1012.MANDT -- Ŭ���̾�Ʈ "; 
			    selectColumn += "\n ,ZSDT1012.ZPYEAR -- ���⵵ ";
			    selectColumn += "\n ,(SELECT BEZEI FROM SAPHEE.TVKBT   ";
			    selectColumn += "\n WHERE MANDT = '"+paramV.getMANDT()+"'  ";
			    selectColumn += "\n AND VKBUR = ZSDT1012.VKBUR AND SPRAS = '3')AS VKBUR -- �����  ";
			    selectColumn += "\n ,ZSDT1012.VKGRP -- �����׷� ";
			    selectColumn += "\n ,(SELECT NAME1   ";
			    selectColumn += "\n FROM SAPHEE.LFA1   ";
			    selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
			    selectColumn += "\n AND LIFNR = ZSDT1012.ZAGNT ) AS ZAGNT -- ���»�  "; 
			    selectColumn += "\n ,(SELECT NAME1 FROM SAPHEE.KNA1   ";
			    selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
			    selectColumn += "\n AND KTOKD = 'Z201' ";
			    selectColumn += "\n AND KUNNR = ZSDT1012.ZKUNNR )AS ZKUNNR -- �������  ";
			    fromTable += "\n , 0 AS SOFOCA";
				// ����
				if ( "1".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 -- �����ȹ(����) ";
				}
				// ����
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 -- �����ȹ(����) ";
				   fromTable += "\n ,SAPHEE.ZSDT1013 ZSDT1013 -- �����ȹ(����)  ";
				}
				// û��
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 -- �����ȹ(����) ";
				   fromTable += "\n ,SAPHEE.ZSDT1014 ZSDT1014	-- �����ȹ(û��)  ";
				}
				// ����
				else
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 -- �����ȹ(����) ";
				   fromTable += "\n ,SAPHEE.ZSDT1015 ZSDT1015-- �����ȹ(����)  ";
				}
			    fromTable += "\n WHERE 1=1 ";
			    fromTable += "\n AND ZSDT1012.MANDT = '"+paramV.getMANDT()+"' -- Ŭ���̾�Ʈ ";
			    fromTable += "\n AND ZSDT1012.ZPYEAR = '"+paramV.getZPYEAR()+"' -- ���⵵ ";
			   
				// ����
				if ( "1".equals(paramV.getWHERE()) )
				{
				}
				// ����
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1013.MANDT -- Ŭ���̾�Ʈ ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1013.ZBPNNR -- �����ȹ��ȣ   ";
				}
				// û��
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1014.MANDT -- Ŭ���̾�Ʈ ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1014.ZBPNNR -- �����ȹ��ȣ   ";
				}
				// ����
				else
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1015.MANDT -- Ŭ���̾�Ʈ ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1015.ZBPNNR -- �����ȹ��ȣ   ";
				}
				// �μ�
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
				// ��
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
					
				// ���������
				if ( !"".equals( paramV.getZKUNNR() ) )
				{
					where += "\n AND ZSDT1012.ZKUNNR = '"+paramV.getZKUNNR()+"' ";
				}
					
				// ��������(�Ǹ���������)
				if ( !"".equals( paramV.getAUART() ) )
				{
					where += "\n AND ZSDT1012.AUART = '"+paramV.getAUART()+"' ";
				}
				orderby += "\n GROUP BY ZSDT1012.MANDT 	-- Ŭ���̾�Ʈ  ";
				orderby += "\n ,ZSDT1012.ZPYEAR 		-- ���⵵  ";
				orderby += "\n ,ZSDT1012.VKBUR 			-- �����  ";
				orderby += "\n ,ZSDT1012.VKGRP 			-- �����׷� ";
				orderby += "\n ,ZSDT1012.ZAGNT 			-- ���»� ";
				orderby += "\n ,ZSDT1012.ZKUNNR 		-- ������� ";
				//orderby += "\n ,ZSDT1012.ZPYM 			-- ��ȹ���  ";

				// ����
				if ( "1".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1012.ZPYM 			-- ��ȹ���  ";
				}
				// ����
				else if ( "2".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1013.ZSAYM 			-- ������  ";
				}
				// û��
				else if ( "3".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1014.ZRQYM 			-- û�����  ";
				}
				// ����
				else
				{
					orderby += "\n ,ZSDT1015.BYSYM 			-- ���ݳ��  ";
				}
				

				// �ּҳ������ �ִ��������� ������
				// null�� �ƴϰ�
				// �����Ͱ� �����ϴ� ������
				// case������ �����ϰ�
				// �����Ͱ� �������� �ʴ�������
				// dummy�λ����ϱ������̴�.
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
							//logger.info("@@@@@ monNot ����: "+monNot[unionLoop]);
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
							//logger.info("@@@@@ mon ����: "+mon[unionLoop]);
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
		
				// sofoca(�����) ���� ���� ���ڿ�
				paramV.setFILTER2(rs);
				//logger.info("@@@@@ rs: "+rs);
				
				// union ���� ���� ���ڿ�
				paramV.setFILTER3(rsUnion);
				//logger.info("@@@@@ rsUnion: "+rsUnion);
			}
			// �ּҿ����� �ִ�������� �Ѱ����� �������
			else
			{
				// sofoca(�����) ���� ���� ���ڿ�
				paramV.setFILTER2("0 AS SOFOCA");
				
				// union ���� ���� ���ڿ�
				paramV.setFILTER3("");
			}
			
		}
		// �μ��� �������� �ʾ��� ���
		else
		{

			// [WARERK - ��ȭ]�÷������� ���� �÷��� ���������Ͽ�
			// �÷������� �Ұ��÷��� ó��
			List<Sbp0110VO> list = dao.SelectWaerk(paramV);

			String sofoca 	= null;
			if ( list.size() > 0 )
			{
				sofoca 	= "CASE";
				for ( int i = 0 ; i < list.size() ; i++ )
				{
					String sofocaVal = null;
					// ����
					if ( "1".equals(paramV.getWHERE()) )
					{
						sofocaVal = "SUM(ZSDT1012.SOFOCA)";
					}
					// ����
					else if ( "2".equals(paramV.getWHERE()) )
					{
						sofocaVal = "SUM(ZSDT1013.NETWR_SA)";
					}
					// û��
					else if ( "3".equals(paramV.getWHERE()) )
					{
						sofocaVal = "SUM(ZSDT1014.NETWR_RQ)";
					}
					// ����
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
			
			// ��ȭ�� ����
			paramV.setFILTER1(sofoca);
			//logger.info(" @@@@@@@@@@@@@@ filter query : "+sofoca);

			// ������ȸ
			List<Sbp0110VO> zpym_list = null;

			// ����
			if ( "1".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectOrderZpym(paramV);
			}
			// ����
			else if ( "2".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectSalesZpym(paramV);
			}
			// û��
			else if ( "3".equals(paramV.getWHERE()) )
			{
				zpym_list = dao.SelectChargeZpym(paramV);
			}
			// ����
			else
			{
				zpym_list = dao.SelectCollectionZpym(paramV);
			}
			
			//logger.info(" @@@@@ zpym_list size : " +zpym_list.size());
			
			// ��������Ʈ�� ���� �迭�� ����
			String[] zpym_list_arr = new String[60];
			
			// ��������Ʈ�� �Ѱ��̻� �����
			if ( zpym_list.size() > 0 )
			{
				// ���� ���
				String start_zpym = zpym_list.get(0).getZPYM();
				// �ִ� ���
				String end_zpym = zpym_list.get(zpym_list.size()-1).getZPYM();
				
				//logger.info(" @@@@@ start_zpym : " +start_zpym );
				//logger.info(" @@@@@ end_zpym : " +end_zpym );
				
				// �迭������ ���� ����
				int cnt = 0;
				
				// �⵵ ���̼�
				int between_zpym = Integer.parseInt( end_zpym.substring(0, 4) ) - Integer.parseInt( start_zpym.substring(0, 4) );
				
				// ���ʳ������ ���ʳ⿡ �ش��ϴ� 12������ �ݺ��Ͽ� ����� ����
				for ( int a = Integer.parseInt( zpym_list.get(0).getZPYM().substring(4) ) ; a < 13 ; a++ )
				{
					zpym_list_arr[cnt] = start_zpym.substring(0, 4) + SmpComC.lpad(Integer.toString(a), 2, "0");//Integer.toString(a);
					cnt++;
				}

				//logger.info(" @@@@@ between_zpym : " +between_zpym );
				// �⵵���̼���ŭ ����
				if ( between_zpym == 1 )
				{
					// �ִ����� �׻� 12������ fix�Ѵ�.
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
						
						// �ִ����� �׻� 12������ fix�Ѵ�.
						for ( int b = 1 ; b < 13 ; b++ )
						{
							zpym_list_arr[cnt] = start_zpym.substring(0, 4) + SmpComC.lpad(Integer.toString(b), 2, "0");
							cnt++;
						}
					}
				}
				
				
			}
			// cnt�� �ڵ����� ���ʳ������ �ִ��������� ������ �ȴ�.(�ּҿ����� �ִ�������� �Ѱ���)
			
			//logger.info(" @@@@@ zpym_list_arr : " +zpym_list_arr.toString() );

			// �ּҿ����� �ִ�������� �Ѱ��� �� �����Ͱ� �����ϴ� ��
			String mon[] =  new String[zpym_list_arr.length];
			// �ݺ��ϸ鼭 �ʱ�ȭ ó���� ���ش�. �� �ʿ���.
			for ( int xx = 0 ; xx < zpym_list_arr.length ; xx++ )
			{
				mon[xx] = "";
			}
			// �ּҿ����� �ִ�������� �Ѱ��� �� �����Ͱ� �������� �ʴ� ��
			// �ʱ�ȭó�� �ʿ����� ����.
			String monNot[] =  new String[zpym_list_arr.length];
			
			// �ּҿ����� �ִ�������� �Ѱ����� �Ѱ��̻� �����
			if ( zpym_list_arr.length > 0 )
			{
				
				// ���� ������ �Ǽ���ŭ ����
				for ( int zpym_listNum_real = 0 ; zpym_listNum_real < zpym_list.size() ; zpym_listNum_real++ )
				{
					// �����ϴ� ����ϰ��
					String currentMon = zpym_list.get(zpym_listNum_real).getZPYM();
					//logger.info(" @@@@@ currentMon : " +currentMon );
					
					// dummy���� �ѰǼ���ŭ ����
					for ( int zpym_listNum_dummy = 0 ; zpym_listNum_dummy < zpym_list_arr.length ; zpym_listNum_dummy++ )
					{
		
						// �ּҿ����� �ִ�������� �Ѱ��� �� �����Ͱ� �������� �ʴ� �� - �ʱ�ȭ
						monNot[zpym_listNum_dummy] = "";
						
						// �ݺ������ �����ϴ� ����� ������� 
						// mon�� ó��
						if ( currentMon.equals(zpym_list_arr[zpym_listNum_dummy]) )
						{
							mon[zpym_listNum_dummy] += " CASE";
							// ����
							if ( "1".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// ����
							else if ( "2".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1013.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// û��
							else if ( "3".equals(paramV.getWHERE()) )
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1014.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							// ����
							else
							{
								mon[zpym_listNum_dummy] += " WHEN MAX(ZSDT1015.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
							}
							mon[zpym_listNum_dummy] += "\n";
							mon[zpym_listNum_dummy] += paramV.getFILTER1();
						}
						
						// �ݺ������ �����ϴ��� �������  
						// monNot�� ó��
						monNot[zpym_listNum_dummy] += " CASE";
						// ����
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1012.ZPYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// ����
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1013.ZSAYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// û��
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1014.ZRQYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						// ����
						else
						{
							monNot[zpym_listNum_dummy] += " WHEN MAX(ZSDT1015.BYSYM)='"+zpym_list_arr[zpym_listNum_dummy]+"' THEN ";
						}
						monNot[zpym_listNum_dummy] += "\n";

						// ����
						if ( "1".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1012.SOFOCA)";
						}
						// ����
						else if ( "2".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1013.NETWR_SA)";
						}
						// û��
						else if ( "3".equals(paramV.getWHERE()) )
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1014.NETWR_RQ)";
						}
						// ����
						else
						{
							monNot[zpym_listNum_dummy] += "SUM(ZSDT1015.COLBI)";
						}
						
					}
				}
				

				// ���� ����Ÿ�� �������� ������� 
				// �ش���� ����Ÿ�� dummyó�� �ϱ� ����
				String rs 			= "";
				String rsUnion 		= "";
				//logger.info("@@@@@ �⵵: "+zpym_list.get(0).getZPYM().substring(0, 4));

				// �ݺ����� ������
				String selectColumn = "";
				String fromTable 	= "";
			    String where 		= "";
				String orderby 		= "";
				
				selectColumn += "\n UNION ALL ";
				selectColumn += "\n ";
				selectColumn += "\n SELECT ";
				selectColumn += "\n ZSDT1012.MANDT 									-- Ŭ���̾�Ʈ "; 
				selectColumn += "\n ,ZSDT1012.ZPYEAR 								-- ���⵵ ";
				selectColumn += "\n ,(SELECT BEZEI FROM SAPHEE.TVKBT   ";
				selectColumn += "\n WHERE MANDT = '"+paramV.getMANDT()+"'  ";
				selectColumn += "\n AND VKBUR = ZSDT1012.VKBUR AND SPRAS = '3')AS VKBUR 			-- �����  ";
				selectColumn += "\n ,ZSDT1012.VKGRP 								-- �����׷� ";
				selectColumn += "\n ,( SELECT NAME1   ";
				selectColumn += "\n FROM SAPHEE.LFA1   ";
				selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
				selectColumn += "\n AND LIFNR = ZSDT1012.ZAGNT ) AS ZAGNT 			-- ���»�  "; 
				selectColumn += "\n ,(SELECT NAME1 FROM SAPHEE.KNA1   ";
				selectColumn += "\n WHERE MANDT =  '"+paramV.getMANDT()+"' ";
				selectColumn += "\n AND KTOKD = 'Z201' ";
				selectColumn += "\n AND KUNNR = ZSDT1012.ZKUNNR )AS ZKUNNR 			-- �������  ";
				fromTable += "\n , '' AS WAERK ";
				fromTable += "\n , 0 AS SOFOCA";
				fromTable += "\n , 0 AS KRWUSD";
				// ����
				if ( "1".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 					-- �����ȹ(����) ";
				}
				// ����
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 					-- �����ȹ(����) ";
				   fromTable += "\n ,SAPHEE.ZSDT1013 ZSDT1013 						-- �����ȹ(����)  ";
				}
				// û��
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 					-- �����ȹ(����) ";
				   fromTable += "\n ,SAPHEE.ZSDT1014 ZSDT1014						-- �����ȹ(û��)  ";
				}
				// ����
				else
				{
				   fromTable += "\n FROM SAPHEE.ZSDT1012 ZSDT1012 					-- �����ȹ(����) ";
				   fromTable += "\n ,SAPHEE.ZSDT1015 ZSDT1015						-- �����ȹ(����)  ";
					}
				   fromTable += "\n WHERE 1=1 ";
				   fromTable += "\n AND ZSDT1012.MANDT = '"+paramV.getMANDT()+"' 	-- Ŭ���̾�Ʈ ";
				   fromTable += "\n AND ZSDT1012.ZPYEAR = '"+paramV.getZPYEAR()+"' 	-- ���⵵ ";
				// ����
				if ( "1".equals(paramV.getWHERE()) )
				{
				}
				// ����
				else if ( "2".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1013.MANDT 			-- Ŭ���̾�Ʈ ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1013.ZBPNNR 			-- �����ȹ��ȣ   ";
				}
				// û��
				else if ( "3".equals(paramV.getWHERE()) )
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1014.MANDT 			-- Ŭ���̾�Ʈ ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1014.ZBPNNR 			-- �����ȹ��ȣ   ";
				}
				// ����
				else
				{
				   fromTable += "\n AND ZSDT1012.MANDT = ZSDT1015.MANDT 			-- Ŭ���̾�Ʈ ";
				   fromTable += "\n AND ZSDT1012.ZBPNNR = ZSDT1015.ZBPNNR 			-- �����ȹ��ȣ   ";
				}
				// �μ�
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
				// ��
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
				
				// ���������
				if ( !"".equals( paramV.getZKUNNR() ) )
				{
					where += "\n AND ZSDT1012.ZKUNNR = '"+paramV.getZKUNNR()+"' ";
				}
				
				// ��������(�Ǹ���������)
				if ( !"".equals( paramV.getAUART() ) )
				{
					where += "\n AND ZSDT1012.AUART = '"+paramV.getAUART()+"' ";
				}
				orderby += "\n GROUP BY ZSDT1012.MANDT 	-- Ŭ���̾�Ʈ  ";
				orderby += "\n ,ZSDT1012.ZPYEAR 		-- ���⵵  ";
				orderby += "\n ,ZSDT1012.VKBUR 			-- �����  ";
				orderby += "\n ,ZSDT1012.VKGRP			-- �����׷� ";
				orderby += "\n ,ZSDT1012.ZAGNT 			-- ���»� ";
				orderby += "\n ,ZSDT1012.ZKUNNR 		-- ������� ";
				
				// ����
				if ( "1".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1012.ZPYM 			-- ��ȹ���  ";
				}
				// ����
				else if ( "2".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1013.ZSAYM 			-- ������  ";
				}
				// û��
				else if ( "3".equals(paramV.getWHERE()) )
				{
					orderby += "\n ,ZSDT1014.ZRQYM 			-- û�����  ";
				}
				// ����
				else
				{
					orderby += "\n ,ZSDT1015.BYSYM 			-- ���ݳ��  ";
				}
				
				// �ּҳ������ �ִ��������� ������
				// null�� �ƴϰ�
				// �����Ͱ� �����ϴ� ������
				// case������ �����ϰ�
				// �����Ͱ� �������� �ʴ�������
				// dummy�λ����ϱ������̴�.
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
							//logger.info("@@@@@ monNot ����: "+monNot[unionLoop]);
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
							//logger.info("@@@@@ mon ����: "+mon[unionLoop]);
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

				// sofoca(�����) ���� ���� ���ڿ�
				paramV.setFILTER2(rs);
				//logger.info("@@@@@ rs: "+rs);
				
				// union ���� ���� ���ڿ�
				paramV.setFILTER3(rsUnion);
				//logger.info("@@@@@ rsUnion: "+rsUnion);	
					
			}
			// �ּҿ����� �ִ�������� �Ѱ����� �������
			else
			{
				// sofoca(�����) ���� ���� ���ڿ�
				paramV.setFILTER2("0 AS SOFOCA");
				
				// union ���� ���� ���ڿ�
				paramV.setFILTER3("");
			}
			
	
		}// �μ��� ������� end

			
		// ����
		if ( "1".equals(paramV.getWHERE()) && "N".equals(vkburYn) )
		{
			return dao.SelectOrder(paramV);
		}
		else if ( "1".equals(paramV.getWHERE()) && "Y".equals(vkburYn) )
		{
			return dao.SelectOrderExitWaerk(paramV);
		}
		// ����
		else if ( "2".equals(paramV.getWHERE()) && "N".equals(vkburYn) )
		{
			return dao.SelectSales(paramV);
		}
		else if ( "2".equals(paramV.getWHERE()) && "Y".equals(vkburYn) )
		{
			return dao.SelectSalesExitWaerk(paramV);
		}
		// û��
		else if ( "3".equals(paramV.getWHERE()) && "N".equals(vkburYn) )
		{
			return dao.SelectCharge(paramV);
		}
		else if ( "3".equals(paramV.getWHERE()) && "Y".equals(vkburYn) )
		{
			return dao.SelectChargeExitWaerk(paramV);
		}
		// ����
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
