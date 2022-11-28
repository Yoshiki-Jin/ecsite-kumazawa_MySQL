package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public void insert(OrderItem oi) {
		
		String sql = "INSERT INTO orderitem VALUES (:itemId, :orderId, :quantity, :size) ;";
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(oi);
		
		template.update(sql,param);
	}
}
