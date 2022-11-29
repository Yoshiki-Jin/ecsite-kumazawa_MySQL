package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.CartForm;
import com.example.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService service;

	@GetMapping("insertOrderItem")
	public String insertOrderItem(CartForm form) {
		
		//ユーザーIDを入手する（未実装）
		Integer userId = 0;
		service.addItem(form, userId);
		return "item_list";
	}
}
