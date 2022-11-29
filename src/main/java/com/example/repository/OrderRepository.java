package com.example.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;

@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	//戻り値をOrderのListにしている。（注文履歴表示のfind（）にも使えるから。）
	public static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Order> orderList = new LinkedList<Order>();
		List<OrderItem> ordeItemList = null;
		List<OrderTopping> orderToppingList = null;
		
		//前の行のOrderIdを対比しておく変数
		long beforeOrderId = 0;
		
		while(rs.next()) {
			//現在検索されている記事Idを退避
			int nowOrderId = rs.getInt("id");
			
			if(nowOrderId != beforeOrderId) {
				Order order = new Order();
				order.setId(nowOrderId);
				order.setUserId(rs.getInt("userId"));
				order.setStatus(rs.getInt("status"));
				order.setTotalPrice(rs.getInt("totalPrice"));
				order.setOrderDate(rs.getDate("orderDate"));
				order.setDestinationName(rs.getString("destinationName"));
				order.setDestinationEmail(rs.getString("destinationEmail"));
				order.setDestinationZipcode(rs.getString("destinationZipcode"));
				order.setDestinationAddress(rs.getString("destinationAddress"));
				order.setDestinationTel(rs.getString("destinationTel"));
				order.setDeliveryTime(rs.getTimestamp("deliveryTime"));
				order.setPaymentMethod(rs.getInt("paymentMethod"));
				
				ordeItemList = new ArrayList<OrderItem>();
			
			//Orderだけ合ってOrderItemが無い場合はオブジェクトを作らない
			if(rs.getInt("oi_id") != 0) {
				OrderItem oi = new OrderItem();
				oi.setId(rs.getInt("oi_id"));
				oi.setItemId(rs.getInt("oi_item_id"));
				oi.setOrderId(rs.getInt("oi_order_id"));
				oi.setQuantity(rs.getInt("oi_quantity"));
				oi.setSize(rs.getString("oi_size").charAt(0));
				
				
				Item item = new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("i_description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("i_image_path"));
				
				orderToppingList = new ArrayList<>();
				
				oi.setItem(item);
				
				if(rs.getInt("ot_id") != 0) {
					OrderTopping ot = new OrderTopping();
					ot.setId(rs.getInt("ot_id"));
					ot.setToppingId(rs.getInt("ot_topping_id"));
					ot.setOrderItemId(rs.getInt("ot_order_item_id"));
					
					Topping t = new Topping();
					t.setId(rs.getInt("t_id"));
					t.setName(rs.getString("t_name"));
					t.setPriceM(rs.getInt("t_price_m"));
					t.setPriceL(rs.getInt("t_price_l"));
					ot.setTopping(t);
					orderToppingList.add(ot);
				}
				oi.setOrderToppingList(orderToppingList);
				ordeItemList.add(oi);
			}
			order.setOrderItemList(ordeItemList);
		}
		}
		
		return orderList;
	};
	
	public static final RowMapper<Order> ORDER_ROW_MAPPER = (rs,i) -> {
		
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("userId"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("totalPrice"));
		order.setOrderDate(rs.getDate("orderDate"));
		order.setDestinationName(rs.getString("destinationName"));
		order.setDestinationEmail(rs.getString("destinationEmail"));
		order.setDestinationZipcode(rs.getString("destinationZipcode"));
		order.setDestinationAddress(rs.getString("destinationAddress"));
		order.setDestinationTel(rs.getString("destinationTel"));
		order.setDeliveryTime(rs.getTimestamp("deliveryTime"));
		order.setPaymentMethod(rs.getInt("paymentMethod"));
//		order.setUser(rs.getUser("user"));
//		order.setOrderItemList(rs.getOrderItemList("orderItemList"));
		
		
		return order;
	};
	
	/**
	 * userId と status=0 を条件にOrderオブジェクトを1件取得する
	 * @param userId　ユーザーID
	 */
	public Order findByUserIdAndStatus(Integer userId) {
		
		String sql = "SELECT id FROM orders WHERE userId = :userId AND status = 0; ";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId);
		
		//queryForObjectだと該当０件の時エラーが起きてしまう
		//→Serviceクラスで例外処理　OR　queryにして戻り値をList<Order>にする？
		Order order = template.queryForObject(sql,param,ORDER_ROW_MAPPER);
		
		return order;
	}
	
	/**
	 * orderIdを条件にOrderリストを返す。
	 * @param orderId　オーダーID
	 * @return　Orderリスト（履歴検索も考慮して、複数検索ができるようにしてます。）
	 */
	public Order load(Integer orderId) {
		
		String sql = "SELECT o.id, o.user_id, o.status, o.total_price, o.order_date, o.destination_name, o.destination_email, o.destination_zipcode, o.destination_address, o.destination_tel, o.delivery_time, o.payment_method, "
				+ "oi.id, oi.item_id, oi.order_id, oi.quantity, oi.size, "
				+ "ot.id, ot.topping_id, ot.order_item_id, "
				+ "i.id, i.name, i.description, i.price_m, i.price_l, i.image_path, i.deleted, "
				+ "t.id, t.name, t.price_m, t.price_l "
				+ "FROM Orders o LEFT OUTER JOIN order_items oi ON o.id = oi.order_id "
				+ "LEFT OUTER JOIN order_toppings ot ON oi.id = ot.order_item_id "
				+ "JOIN items i ON i.id = oi.item_id "
				+ "JOIN toppings t ON t.id = ot.topping_id "
				+ "WHERE o.id = :orderId ;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId",orderId);
		
		List<Order> orderList = null;
		orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		return orderList.get(0);
	}
	
	public void insert(Order order) {
		
		//注文内容確認～宛先情報入力～完了等に関係するカラムは含めていない。
		String sql = "INSERT INTO orders(user_id,status,total_price) VALUES(:userId, :status, :total_price);";
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		
		template.update(sql,param);
	}
}
