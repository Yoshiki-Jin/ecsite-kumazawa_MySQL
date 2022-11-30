package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.form.OrderForm;
import com.example.service.CartService;
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
	
	@Autowired
	private CartService cartService;
	
	/**
	 * 注文画面を表示します.
	 * 
	 * @return 注文画面
	 */
	@GetMapping("/toOrder")
	public String toOrder(Model model) {
		
		//UserId取得の機能実装後書き換えます
		Integer userId = 1;
		
		Order order = cartService.showCart(userId);
		model.addAttribute("order",order);
		
		int totalPrice=order.getTotalPrice();
		int tax=(int)(totalPrice*10/110);
		model.addAttribute("tax", tax);
		
		return "order_confirm";
	}

	/**
	 * 注文をします.
	 * 
	 * @param orderForm 注文情報を受け取るフォーム
	 * @return 注文完了画面
	 */
	@PostMapping("/")
	public String order(OrderForm orderForm) {
		orderService.order(orderForm);
		return "order_finished";
	}
}
