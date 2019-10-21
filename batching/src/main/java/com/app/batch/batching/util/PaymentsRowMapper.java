package com.app.batch.batching.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.app.batch.batching.entity.Payments;
import com.app.batch.batching.enumeration.StatusEnum;

public class PaymentsRowMapper implements RowMapper<Payments> {

	@Override
	public Payments mapRow(ResultSet rs, int rowNum) throws SQLException {

		Payments payments = new Payments();
		payments.setId(rs.getLong("id"));
		payments.setClientCode(rs.getString("client_code"));
		payments.setValue(rs.getDouble("value"));
		payments.setStatus(StatusEnum.PAYMENT_OK);
		return payments;
	}

}
