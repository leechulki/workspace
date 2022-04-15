/**
 * Program Name : LoggablePreparedStatement.java
 * Description  : PreparedStatement에 대한 바인딩 로그를 찍기 위해 구성.
 * Create Date  : 2005.06.17
 * History      : 2005.06.17  Initial created by 구자경
 * 
 */
package com.helco.xf.cs12.evladm.dbutil;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * <pre>
 *   Program Name	: LoggablePreparedStatement 
 *   Description	: 2005년6월 동부정보기술  
 *   Author		: jkkoo 
 *   Create Date	: 2005. 6. 21. 
 *   History		: 
 *   $id:$
 * </pre>
 */
public class LoggablePreparedStatement implements PreparedStatement {

    private ArrayList parameterValues;

    private String sqlTemplate;

    private PreparedStatement wrappedStatement;

    
    
    /**
     * <pre>
     * Method Name : LoggablePreparedStatement
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public LoggablePreparedStatement(Connection connection, String sql)
            throws SQLException {
        wrappedStatement = connection.prepareStatement(sql);
        sqlTemplate = sql;
        parameterValues = new ArrayList();
    }

    /**
     * <pre>
     * Method Name : addBatch
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void addBatch() throws SQLException {
        wrappedStatement.addBatch();
    }
    /**
     * <pre>
     * Method Name : addBatch
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void addBatch(String sql) throws SQLException {
        wrappedStatement.addBatch(sql);
    }
    /**
     * <pre>
     * Method Name : cancel
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void cancel() throws SQLException {
        wrappedStatement.cancel();
    }

    /**
     * <pre>
     * Method Name : clearBatch
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void clearBatch() throws SQLException {
        wrappedStatement.clearBatch();
    }

    /**
     * <pre>
     * Method Name : clearParameters
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void clearParameters() throws SQLException {
        wrappedStatement.clearParameters();
    }

    /**
     * <pre>
     * Method Name : clearWarnings
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void clearWarnings() throws SQLException {
        wrappedStatement.clearWarnings();
    }

    /**
     * <pre>
     * Method Name : close
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void close() throws SQLException {
        wrappedStatement.close();
    }

    /**
     * <pre>
     * Method Name : execute
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public boolean execute() throws SQLException {
        return wrappedStatement.execute();
    }

    /**
     * <pre>
     * Method Name : execute
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public boolean execute(String sql) throws SQLException {
        return wrappedStatement.execute(sql);
    }

    /**
     * <pre>
     * Method Name : executeBatch
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public int[] executeBatch() throws SQLException {
        return wrappedStatement.executeBatch();
    }

    /**
     * <pre>
     * Method Name : executeQuery
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public ResultSet executeQuery() throws SQLException {
        return wrappedStatement.executeQuery();
    }

    /**
     * <pre>
     * Method Name : executeQuery
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        return wrappedStatement.executeQuery(sql);
    }

    /**
     * <pre>
     * Method Name : executeUpdate
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public int executeUpdate() throws SQLException {
        return wrappedStatement.executeUpdate();
    }

    /**
     * <pre>
     * Method Name : executeUpdate
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public int executeUpdate(String sql) throws SQLException {
        return wrappedStatement.executeUpdate(sql);
    }

    /**
     * <pre>
     * Method Name : getConnection
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public Connection getConnection() throws SQLException {
        return wrappedStatement.getConnection();
    }

    /**
     * <pre>
     * Method Name : getFetchDirection
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public int getFetchDirection() throws SQLException {
        return wrappedStatement.getFetchDirection();
    }

    /**
     * <pre>
     * Method Name : getFetchSize
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public int getFetchSize() throws SQLException {
        return wrappedStatement.getFetchSize();
    }

    /**
     * <pre>
     * Method Name : getMaxFieldSize
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public int getMaxFieldSize() throws SQLException {
        return wrappedStatement.getMaxFieldSize();
    }

    /**
     * <pre>
     * Method Name : getMaxRows
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public int getMaxRows() throws SQLException {
        return wrappedStatement.getMaxRows();
    }

    /**
     * <pre>
     * Method Name : getMetaData
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public ResultSetMetaData getMetaData() throws SQLException {
        return wrappedStatement.getMetaData();
    }

    /**
     * <pre>
     * Method Name : getMoreResults
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public boolean getMoreResults() throws SQLException {
        return wrappedStatement.getMoreResults();
    }

    /**
     * <pre>
     * Method Name : getQueryTimeout
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public int getQueryTimeout() throws SQLException {
        return wrappedStatement.getQueryTimeout();
    }

    /**
     * <pre>
     * Method Name : getResultSet
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public ResultSet getResultSet() throws SQLException {
        return wrappedStatement.getResultSet();
    }

    /**
     * <pre>
     * Method Name : getResultSetConcurrency
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public int getResultSetConcurrency() throws SQLException {
        return wrappedStatement.getResultSetConcurrency();
    }

    /**
     * <pre>
     * Method Name : getResultSetType
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public int getResultSetType() throws SQLException {
        return wrappedStatement.getResultSetType();
    }

    /**
     * <pre>
     * Method Name : getUpdateCount
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public int getUpdateCount() throws SQLException {
        return wrappedStatement.getUpdateCount();
    }

    /**
     * <pre>
     * Method Name : getWarnings
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public SQLWarning getWarnings() throws SQLException {
        return wrappedStatement.getWarnings();
    }

    /**
     * <pre>
     * Method Name : setArray
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setArray(int i, Array x) throws SQLException {
        wrappedStatement.setArray(i, x);
        saveQueryParamValue(i, x);
    }

    /**
     * <pre>
     * Method Name : setAsciiStream
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setAsciiStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        wrappedStatement.setAsciiStream(parameterIndex, x, length);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setBigDecimal
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setBigDecimal(int parameterIndex, BigDecimal x)
            throws SQLException {
        wrappedStatement.setBigDecimal(parameterIndex, x);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setBinaryStream
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setBinaryStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        wrappedStatement.setBinaryStream(parameterIndex, x, length);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setBlob
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setBlob(int i, Blob x) throws SQLException {
        wrappedStatement.setBlob(i, x);
        saveQueryParamValue(i, x);
    }

    /**
     * <pre>
     * Method Name : setBoolean
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        wrappedStatement.setBoolean(parameterIndex, x);
        saveQueryParamValue(parameterIndex, new Boolean(x));
    }

    /**
     * <pre>
     * Method Name : setByte
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setByte(int parameterIndex, byte x) throws SQLException {
        wrappedStatement.setByte(parameterIndex, x);
        saveQueryParamValue(parameterIndex, new Integer(x));
    }

    /**
     * <pre>
     * Method Name : setBytes
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setBytes(int parameterIndex, byte x[]) throws SQLException {
        wrappedStatement.setBytes(parameterIndex, x);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setCharacterStream
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setCharacterStream(int parameterIndex, Reader reader, int length)
            throws SQLException {
        wrappedStatement.setCharacterStream(parameterIndex, reader, length);
        saveQueryParamValue(parameterIndex, reader);
    }

    /**
     * <pre>
     * Method Name : setClob
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setClob(int i, Clob x) throws SQLException {
        wrappedStatement.setClob(i, x);
        saveQueryParamValue(i, x);
    }

    /**
     * <pre>
     * Method Name : setCursorName
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setCursorName(String name) throws SQLException {
        wrappedStatement.setCursorName(name);
    }

    /**
     * <pre>
     * Method Name : setDate
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setDate(int parameterIndex, java.sql.Date x)
            throws SQLException {
        wrappedStatement.setDate(parameterIndex, x);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setDate
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setDate(int parameterIndex, java.sql.Date x, Calendar cal)
            throws SQLException {
        wrappedStatement.setDate(parameterIndex, x, cal);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setDouble
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setDouble(int parameterIndex, double x) throws SQLException {
        wrappedStatement.setDouble(parameterIndex, x);
        saveQueryParamValue(parameterIndex, new Double(x));
    }

    /**
     * <pre>
     * Method Name : setEscapeProcessing
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setEscapeProcessing(boolean enable) throws SQLException {
        wrappedStatement.setEscapeProcessing(enable);
    }

    /**
     * <pre>
     * Method Name : setFetchDirection
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setFetchDirection(int direction) throws SQLException {
        wrappedStatement.setFetchDirection(direction);
    }

    /**
     * <pre>
     * Method Name : setFetchSize
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setFetchSize(int rows) throws SQLException {
        wrappedStatement.setFetchSize(rows);
    }

    /**
     * <pre>
     * Method Name : setFloat
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setFloat(int parameterIndex, float x) throws SQLException {
        wrappedStatement.setFloat(parameterIndex, x);
        saveQueryParamValue(parameterIndex, new Float(x));
    }

    /**
     * <pre>
     * Method Name : setInt
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setInt(int parameterIndex, int x) throws SQLException {
        wrappedStatement.setInt(parameterIndex, x);
        saveQueryParamValue(parameterIndex, new Integer(x));
    }

    /**
     * <pre>
     * Method Name : setLong
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setLong(int parameterIndex, long x) throws SQLException {
        wrappedStatement.setLong(parameterIndex, x);
        saveQueryParamValue(parameterIndex, new Long(x));
    }

    /**
     * <pre>
     * Method Name : setMaxFieldSize
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setMaxFieldSize(int max) throws SQLException {
        wrappedStatement.setMaxFieldSize(max);
    }

    /**
     * <pre>
     * Method Name : setMaxRows
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setMaxRows(int max) throws SQLException {
        wrappedStatement.setMaxRows(max);
    }

    /**
     * <pre>
     * Method Name : setNull
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        wrappedStatement.setNull(parameterIndex, sqlType);
        saveQueryParamValue(parameterIndex, null);
    }

    /**
     * <pre>
     * Method Name : setNull
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setNull(int paramIndex, int sqlType, String typeName)
            throws SQLException {
        wrappedStatement.setNull(paramIndex, sqlType, typeName);
        saveQueryParamValue(paramIndex, null);
    }

    /**
     * <pre>
     * Method Name : setObject
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setObject(int parameterIndex, Object x) throws SQLException {
        wrappedStatement.setObject(parameterIndex, x);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setObject
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType)
            throws SQLException {
        wrappedStatement.setObject(parameterIndex, x, targetSqlType);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setObject
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType,
            int scale) throws SQLException {
        wrappedStatement.setObject(parameterIndex, x, targetSqlType, scale);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setRef
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setQueryTimeout(int seconds) throws SQLException {
        wrappedStatement.setQueryTimeout(seconds);
    }

    /**
     * <pre>
     * Method Name : setRef
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setRef(int i, Ref x) throws SQLException {
        wrappedStatement.setRef(i, x);
        saveQueryParamValue(i, x);
    }

    /**
     * <pre>
     * Method Name : setShort
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setShort(int parameterIndex, short x) throws SQLException {
        wrappedStatement.setShort(parameterIndex, x);
        saveQueryParamValue(parameterIndex, new Integer(x));
    }

    /**
     * <pre>
     * Method Name : setString
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setString(int parameterIndex, String x) throws SQLException {
        wrappedStatement.setString(parameterIndex, x);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setTime
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setTime(int parameterIndex, Time x) throws SQLException {
        wrappedStatement.setTime(parameterIndex, x);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setTime
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setTime(int parameterIndex, Time x, Calendar cal)
            throws SQLException {
        wrappedStatement.setTime(parameterIndex, x, cal);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setTimestamp
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setTimestamp(int parameterIndex, Timestamp x)
            throws SQLException {
        wrappedStatement.setTimestamp(parameterIndex, x);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * <pre>
     * Method Name : setTimestamp
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
            throws SQLException {
        wrappedStatement.setTimestamp(parameterIndex, x, cal);
        saveQueryParamValue(parameterIndex, x);
    }

    /**
     * @deprecated Method setUnicodeStream is deprecated
     */

    public void setUnicodeStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        wrappedStatement.setUnicodeStream(parameterIndex, x, length);
        saveQueryParamValue(parameterIndex, x);
    }
    /**
     * <pre>
     * Method Name : getQueryString
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    public String getQueryString() {
        StringBuffer buf = new StringBuffer();
        int qMarkCount = 0;
        ArrayList chunks = new ArrayList();
        for (StringTokenizer tok = new StringTokenizer(sqlTemplate + " ", "?"); tok
                .hasMoreTokens();) {
            String oneChunk = tok.nextToken();
            buf.append(oneChunk);
            try {
                Object value;
                if (parameterValues.size() > 1 + qMarkCount)
                    value = parameterValues.get(1 + qMarkCount++);
                else if (tok.hasMoreTokens())
                    value = null;
                else
                    value = "";
                buf.append(((String) (value)));
            } catch (Throwable e) {
                buf.append("ERROR WHEN PRODUCING QUERY STRING FOR LOG."
                        + e.toString());
            }
        }

        return buf.toString().trim();
    }
    /**
     * <pre>
     * Method Name : saveQueryParamValue
     * Description :
     * Author      : jkkoo
     * Create Date : 2005. 6. 21.
     * History     : 
     * $id:$
     * </pre>
     */
    private void saveQueryParamValue(int position, Object obj) {
        String strValue;
        if ((obj instanceof String) || (obj instanceof Date))
            strValue = "'" + obj + "'";
        else if (obj == null)
            strValue = "null";
        else
            strValue = obj.toString();
        for (; position >= parameterValues.size(); parameterValues.add(null))
            ;
        parameterValues.set(position, strValue);
    }

    // 새로 생성 
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return wrappedStatement.getParameterMetaData();
	}

	public void setURL(int parameterIndex, URL x) throws SQLException {
		wrappedStatement.setURL(parameterIndex, x);
	}

	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		// TODO Auto-generated method stub
		return wrappedStatement.execute(sql, autoGeneratedKeys);
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return wrappedStatement.execute(sql, columnIndexes);
	}

	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		// TODO Auto-generated method stub
		return wrappedStatement.execute(sql, columnNames);
	}

	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		// TODO Auto-generated method stub
		return wrappedStatement.executeUpdate(sql, autoGeneratedKeys);
	}

	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		// TODO Auto-generated method stub
		return wrappedStatement.executeUpdate(sql, columnIndexes);
	}

	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		return wrappedStatement.executeUpdate(sql, columnNames);
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return wrappedStatement.getGeneratedKeys();
	}

	public boolean getMoreResults(int current) throws SQLException {
		// TODO Auto-generated method stub
		return wrappedStatement.getMoreResults(current);
	}

	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return wrappedStatement.getResultSetHoldability();
	}

	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setPoolable(boolean poolable) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNString(int parameterIndex, String value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNCharacterStream(int parameterIndex, Reader value,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBlob(int parameterIndex, InputStream inputStream, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setSQLXML(int parameterIndex, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setAsciiStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBinaryStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterStream(int parameterIndex, Reader reader,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setAsciiStream(int parameterIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBinaryStream(int parameterIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterStream(int parameterIndex, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNCharacterStream(int parameterIndex, Reader value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setBlob(int parameterIndex, InputStream inputStream)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}