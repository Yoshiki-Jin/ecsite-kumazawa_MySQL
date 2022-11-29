package com.example.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.form.OrderForm;
import com.example.repository.OrderRepository;

/**
 * 注文情報を操作するサービス.
 * 
 * @author inagakisaia
 *
 */
@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * 注文をします.
	 * 
	 * @param orderForm 注文情報を受け取るフォーム.
	 */
	public void order(OrderForm orderForm) {
		// コメントアウト部分はload()が完成したら使用します。

//		Order order=orderRepository.load(orderForm.getId());

		// 以下２行load()ができたら消します
		Order order = new Order();
		order.setId(1);

		BeanUtils.copyProperties(orderForm, order);
		LocalDate localDate = orderForm.getDeliveryDate().toLocalDate();
		LocalDateTime localDateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(),
				localDate.getDayOfMonth(), orderForm.getDeliveryTime(), 0, 0, 0);
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		order.setDeliveryTime(timestamp);
//		order.setId(Integer.parseInt(orderForm.getId()));
		order.setPaymentMethod(Integer.parseInt(orderForm.getPaymentMethod()));
		order.setStatus(1);
		orderRepository.update(order);
	}

}
