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

@Service
@Transactional
public class CartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

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
}
