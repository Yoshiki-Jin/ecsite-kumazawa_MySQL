package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.form.CartForm;
import com.example.service.CartService;

/**
 * カートに商品を追加、削除、カートの表示をするコントローラ.
 * 
 * @author kaneko
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService service;
	
	@Autowired
	private HttpSession session;

	/**
	 * 選択されたOrderItemを登録する
	 * 
	 * @param form CartForm
	 * @return 商品一覧ページに移動
	 */
	@PostMapping("insertOrderItem")
	public String insertOrderItem(CartForm form) {

		// ユーザーIDを入手する（未実装）
		Integer userId = 0;
		service.addItem(form, userId);
		return "item_list";
	}

	/**
	 * カートの中身を表示する
	 * 
	 * @param model リクエストスコープ用
	 * @return カート詳細画面に移動
	 */
	@GetMapping("showCart")
	public String showCart(Model model) {
		
		if(session.getAttribute("user") == null) {
			
			session.setAttribute("throughOrderConfirmation", true);
			return "login";
		}

		// ユーザーIDを入手する（未実装）
		Integer userId = 1;
		Order order = service.showCart(userId);
		model.addAttribute("order", order);
		return "cart_list";
	}
}
