package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.OrderForm;
import com.example.service.OrderService;

/**
 * 注文情報を操作するコントローラ.
 * 
 * @author inagakisaia
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/toOrder")
	public String toOrder() {
		return "order_confirm";
	}

	/**
	 * 注文をします.
	 * 
	 * @param orderForm 注文情報を受け取るフォーム
	 * @return 注文完了画面
	 */
	@PostMapping("/")
	public String order(OrderForm orderForm,Integer responsibleCompany) {
		orderService.order(orderForm);
		return "order_finished";
	}
}
