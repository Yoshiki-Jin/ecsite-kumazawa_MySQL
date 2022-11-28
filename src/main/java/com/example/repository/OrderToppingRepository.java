package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;

@Repository
public class OrderToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public void insert(OrderTopping ot) {
		
		String sql = "INSERT INTO ordertopping VALUE (:topping_id, :order_item_id, :topping ;";
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(ot);
		
		template.update(sql, param);
	}
	
	public void delete(Integer orderItemId) {
		
		String sql = "DELETE FROM ordertopping WHERE order_item_id = :orderItemId ;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId",orderItemId);
		
		template.update(sql,param);
	}
	
}
