/**
 * 파 일 명 : FrwCrudDAO.java
 * 설    명 : CrudDAO에 대한 인터페이스
 *            - 모든 DAO는 이 클래스를 구현 해야만 한다.
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-02-14
 * 변경내역 :
 *
 * struts-Config : 
 * 
 */ 
package com.helco.xf.cs12.evladm.dao;
import java.io.Serializable;
import java.util.ArrayList;

public interface FrwCrudDAO extends Serializable
{
	public void insert(Object vo) throws Exception;
   public void insert(Object vo, String subMethodFlag) throws Exception;
	public ArrayList selectList(Object searchCondVO) throws Exception;
	public Object select(Object condVO) throws Exception;
	public Object select2(Object condVO) throws Exception;
	public Object select(ArrayList compPk, String subMethodFlag) throws Exception;
	public void deleteList(Object pkList, String subMethodFlag) throws Exception;
	public void update(Object vo) throws Exception;
	public void update(Object vo, String subMethodFlag) throws Exception;
	public int multiSaveForGrid(Object iobj, Object uobj, Object dobj) throws Exception;
}
