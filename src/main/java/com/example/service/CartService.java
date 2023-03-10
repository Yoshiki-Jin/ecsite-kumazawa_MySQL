package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

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
 * @author kumazawa
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

	@Autowired
	private HttpSession session;

	public Order load(Integer orderId) {
		Order order = orderRepository.load(orderId);
		return order;
	}

	/**
	 * status=0のorderIdの有無を確認→無い場合作成. カートに追加するOrderItemを登録する.
	 * カートに追加したOrderItemのToppingListを登録する.
	 * 
	 * @param form   CartForm（登録する商品の内容）
	 * @param userId ユーザーID
	 */
	public void addItem(CartForm form, Integer userId) {
		System.out.println(form);
		Order order = orderRepository.findByUserIdAndStatus(userId);
		System.out.println("order" + order);
		if (order == null) {
			Order createOrder = new Order();
			createOrder.setUserId(userId);
			createOrder.setStatus(0);
			createOrder.setTotalPrice(0);
			orderRepository.insert(createOrder);
			System.out.println("クリエイト" + createOrder);
			// Order newOrder = orderRepository.findByUserIdAndStatus(userId);
		}

		// ステータス０の注文を検索
		order = orderRepository.findByUserIdAndStatus(userId);
		Integer orderId = order.getId();

		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(form.getItemId());
		orderItem.setOrderId(orderId);
		orderItem.setQuantity(form.getQuantity());
		orderItem.setSize(form.getSize());
		System.out.println(orderItem);
		orderItemRepository.insert(orderItem);

		OrderTopping orderTopping = new OrderTopping();
		List<Integer> toppinglist = form.getToppingList();

		if (toppinglist != null) {
			for (Integer toppingId : toppinglist) {
				orderTopping.setToppingId(toppingId);
				OrderItem orderItemMax = orderItemRepository.findMaxId();
				Integer recentId = orderItemMax.getId();
				orderTopping.setOrderItemId(recentId);

				Topping topping = toppingRepository.load(toppingId);
				orderTopping.setTopping(topping);
				orderToppingRepository.insert(orderTopping);
			}
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

	// 追加機能のログイン未済時のカートに入れるメソッド.
//	public Order createDummyOrder(Integer dummuUserId) {
//
//		Order dummyOrderDetail = new Order();
//		dummyOrderDetail.setUserId(dummuUserId);
//		dummyOrderDetail.setStatus(0);
//		dummyOrderDetail.setTotalPrice(0);
//
//		orderRepository.insert(dummyOrderDetail);
//		return dummyOrderDetail;
//	}
//
//	public Order searchDummyOrder(Integer dummyUserId) {
//		Order order = orderRepository.findByUserIdAndStatus(dummyUserId);
//		System.out.println("SearchDummyOrderメソッド内のorder = " + order);
//		return order;
//	}

//	public Order transferItemList(Order trueOrder, List<OrderItem> dummyOrderItemList) {
//
////		dummyOrder.setId(trueOrder.getId());
////		dummyOrder.setUserId(trueOrder.getUserId());
////		dummyOrder.setStatus(trueOrder.getStatus());
////		dummyOrder.setTotalPrice(trueOrder.getTotalPrice());
////		List<OrderItem> dummyList = dummyOrderItemList.getOrderItemList();
////		
//
//		// trueOrderのOrderItemListにOrderItemを加えるのではなくて、
//		// dummyOrderItem(List)のorderIdをtrueOrderのOrderIdにして、更新する。
//		Integer trueOrderId = trueOrder.getId();
//		for (OrderItem dummyOrderItem : dummyOrderItemList) {
//			dummyOrderItem.setOrderId(trueOrderId);
//			orderItemRepository.update(dummyOrderItem);
//		}
//		return trueOrder;
//	}
//
//	public void update(Order transferdOrder) {
//		orderRepository.update(transferdOrder);
//	}
//
//	public Integer findIdAtRecentOrder() {
//		return orderRepository.findRecentId();
//	}
//
//	public Integer findUserIdAtRecentOrder(Integer recentId) {
//		return orderRepository.findRecentUserId(recentId);
//	}
//
//	public List<OrderItem> getOrderItemListByOrderId(Integer orderId) {
//		return orderItemRepository.getOrderItemListByOrderId(orderId);
//	}

}
