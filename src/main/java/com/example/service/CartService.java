package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;
import com.example.form.CartForm;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;
import com.example.repository.ToppingRepository;

/**
 * Cartへの追加、削除、表示をするServiceクラス.
 * 
 * @author kaneko
 *
 */
@Service
@Transactional
public class CartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * status=0のorderIdの有無を確認→無い場合作成. カートに追加するOrderItemを登録する.
	 * カートに追加したOrderItemのToppingListを登録する.
	 * 
	 * @param form   CartForm（登録する商品の内容）
	 * @param userId ユーザーID
	 */
	public void addItem(CartForm form, Integer userId) {

		Order order = orderRepository.findByUserIdAndStatus(userId);

		Integer orderId = order.getId();
		if (order.getStatus() != 0) {
			Order createOrder = new Order();
			createOrder.setUserId(userId);
			createOrder.setStatus(0);
			createOrder.setTotalPrice(0);
			orderRepository.insert(createOrder);
			Order newOrder = orderRepository.findByUserIdAndStatus(userId);
			orderId = newOrder.getId();

		} else {
			orderId = order.getId();
		}
		OrderItem oi = new OrderItem();
		oi.setItemId(form.getItemId());
		oi.setOrderId(orderId);
		oi.setQuantity(form.getQuantity());
		oi.setSize(form.getSize());
		orderItemRepository.insert(oi);

		OrderTopping ot = new OrderTopping();
		List<Integer> toppinglist = form.getToppingList();

		for (Integer toppingId : toppinglist) {
			ot.setToppingId(toppingId);
			OrderItem orderItem = orderItemRepository.findMaxId();
			Integer recentId = orderItem.getId();
			ot.setOrderItemId(recentId);
			Topping topping = toppingRepository.load(toppingId);
			ot.setTopping(topping);
			orderToppingRepository.insert(ot);
		}
	}

	/**
	 * カートの中身を表示する.
	 * 戻り値がList<Order>なのは履歴表示の際にもこのメソッドを使うことができ、そのようにOrderRepositoryのload()を作ったから.
	 * 
	 * @param userId ユーザーID
	 * @return Orderリスト
	 */
	public Order showCart(Integer userId) {

		Order existorder = orderRepository.findByUserIdAndStatus(userId);
		if (existorder == null) {
			return null;
		}
		Order order = orderRepository.load(existorder.getId());
		return order;
	}

	/**
	 * OrderItemを削除する. 該当するorderIdを検索し、OrderItemを削除する.
	 * 
	 * @param orderItemId OrderItemId
	 */
	public void deleteOrderItem(Integer orderItemId) {

		orderToppingRepository.delete(orderItemId);

		orderItemRepository.delete(orderItemId);
	}
}
