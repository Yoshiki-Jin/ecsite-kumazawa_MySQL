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

/**
 * Cartへの追加、削除、表示をするServiceクラス
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

	/**
	 * status=0のorderIdの有無を確認→無い場合作成.
	 * カートに追加するOrderItemを登録する.
	 * カートに追加したOrderItemのToppingListを登録する.
	 * @param form CartForm（登録する商品の内容）
	 * @param userId　ユーザーID
	 */
	public void addItem(CartForm form, Integer userId) {

		Order order = orderRepository.findByUserIdAndStatus(userId);
		// 該当ユーザーIdでstatusが0のorderIdが存在するかチェック

		Integer orderId = 0;
		if (order.getStatus() != 0) {
			// 存在しない場合orderオブジェクトを新たに作る。（ここでユーザーIdが必要になってくる。）
			Order createOrder = new Order();
			createOrder.setUserId(userId);
			createOrder.setStatus(0);
			createOrder.setTotalPrice(0);
			orderRepository.insert(createOrder);
			Order newOrder = orderRepository.findByUserIdAndStatus(userId);
			orderId = newOrder.getId();

		} else {
			// 存在した場合そのorderIdとCartFormを使って登録
			orderId = order.getId();
		}
		// OrderItemRepositoryのinsert()のためOrderItemをインスタンス化
		OrderItem oi = new OrderItem();
		oi.setItemId(form.getItemId());
		oi.setOrderId(orderId);
		oi.setQuantity(form.getQuantity());
		oi.setSize(form.getSize());
		orderItemRepository.insert(oi);

		// OrderToppingRepositoryのinsert()のためにOrderToppingをインスタンス化し、ToopingList（Integer）をfor文で回し、登録。
		OrderTopping ot = new OrderTopping();
		ot.setOrderItemId(oi.getId());

		List<Integer> toppinglist = form.getToppingList();

		for (Integer topping : toppinglist) {
			ot.setToppingId(topping);
			orderToppingRepository.insert(ot);
		}
	}
	
	/**
	 * カートの中身を表示する.
	 * 戻り値がList<Order>なのは履歴表示の際にもこのメソッドを使うことができ、そのようにOrderRepositoryのload()を作ったから。
	 * @param userId　ユーザーID
	 * @return Orderリスト
	 */
	public Order showCart(Integer userId){
		
		Order existorder = orderRepository.findByUserIdAndStatus(userId);
		if(existorder == null) {
			return null;
		}
//		try {
		Order order = orderRepository.load(existorder.getId());
//		}catch(Exception e) {
//			System.out.println("load()でエラー");
//		}
		System.out.println("WWWWWWWWWWWWWWW　order = "+order);
		return order;
	}
	
	/**
	 * OrderItemを削除する
	 * 該当するorderIdを検索し、OrderItemを削除する。
	 * @param orderItemId　OrderItemId
	 */
	public void deleteOrderItem(Integer orderItemId) {
		
		//OrderToppingを削除する
		orderToppingRepository.delete(orderItemId);
		
		//OrderItemを削除する
		orderItemRepository.delete(orderItemId);
	}
}
