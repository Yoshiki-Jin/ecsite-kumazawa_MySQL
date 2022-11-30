package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;

/**
 * OrderToppingのRepository.
 * 
 * @author kaneko
 */
@Repository
public class OrderToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private final static RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(OrderItem.class);

	/**
	 * OrderToppingを１件登録する.
	 * 
	 * @param ot OrderToppingオブジェクト
	 */
	public void insert(OrderTopping ot) {
		
		System.out.println("WWWWWW ot = "+ ot);

		String sql = "INSERT INTO order_toppings (topping_id,order_item_id) VALUES (:toppingId, :orderItemId);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(ot);

		template.update(sql, param);
	}

	/**
	 * OrderItemIdを元に該当するOrderToppingを削除する.
	 * 
	 * @param orderItemId
	 */
	public void delete(Integer orderItemId) {
		
		System.out.println("OrderToppingRepository内のorderItemId　= "+orderItemId);

		String sql = "DELETE FROM order_toppings WHERE order_item_id = :orderItemId ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);

		template.update(sql, param);
	}
	
//	public OrderItem findMaxId() {
//		
//		String sql = "SELECT max(id) FROM order_items;";
//		
//		List<OrderItem> oiList = template.query(sql, ORDER_ITEM_ROW_MAPPER);
//		
//		return oiList.get(0);
//	}

}
