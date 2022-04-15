package com.sap.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.sap.conversion.ConvertedTarget;
import com.sap.domain.Vbeln;

public class VbelnHandler extends BaseTypeHandler<ConvertedTarget> {

	@Override
	public ConvertedTarget getNullableResult(ResultSet rs, String columnLabel) throws SQLException {
		return new Vbeln(rs.getString(columnLabel));
	}

	@Override
	public ConvertedTarget getNullableResult(CallableStatement cs, int parameterIndex) throws SQLException {
		return new Vbeln(cs.getString(parameterIndex));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int parameterIndex, ConvertedTarget x, JdbcType arg3) throws SQLException {
		ps.setObject(parameterIndex, x.getValue());
	}

	public ConvertedTarget getResult(ResultSet rs, String columnName) throws SQLException {
		return getNullableResult(rs, columnName);	//Not NULL when the value in the database is null
	}
}