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

/**
 * OrderItemのRepository.
 * 
 * @author kaneko
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
//	private final static RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(OrderItem.class);

	private final static RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER2 = (rs,i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
//		orderItem.setItemId(rs.getInt("item_id"));
//		orderItem.setOrderId(rs.getInt("order_id"));
//		orderItem.setQuantity(rs.getInt("quantity"));
		return orderItem;
	};
	/**
	 * OrderItemを１件登録する.
	 * 
	 * @param oi Orderitemオブジェクト
	 */
	public void insert(OrderItem oi) {

		String sql = "INSERT INTO order_items(item_id,order_id,quantity,size) VALUES (:itemId, :orderId, :quantity, :size) ;";

		SqlParameterSource param = new BeanPropertySqlParameterSource(oi);

		template.update(sql, param);
	}

	/**
	 * トッピングも含め、OrderItemを１件削除する.
	 * 
	 * @param id orderItemId
	 */
	public void delete(Integer id) {

		String sql = "DELETE FROM order_items WHERE id = :id;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		template.update(sql, param);
	}

	public OrderItem findMaxId() {

		String sql = "SELECT max(id) id FROM order_items;";

		List<OrderItem> oiList = template.query(sql, ORDER_ITEM_ROW_MAPPER2);

		return oiList.get(0);
	}
}
