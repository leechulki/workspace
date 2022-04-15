package com.sap.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.sap.domain.SapBool;

public class SapBoolHandler extends BaseTypeHandler<SapBool> {

	@Override
	public SapBool getNullableResult(ResultSet rs, String columnLabel) throws SQLException {
		return new SapBool(rs.getString(columnLabel));
	}

	@Override
	public SapBool getNullableResult(CallableStatement cs, int parameterIndex) throws SQLException {
		return new SapBool(cs.getString(parameterIndex));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int parameterIndex, SapBool x, JdbcType arg3) throws SQLException {
		ps.setObject(parameterIndex, x.toDBString());
	}

	public SapBool getResult(ResultSet rs, String columnName) throws SQLException {
		return getNullableResult(rs, columnName);	//Not NULL when the value in the database is null
	}
}