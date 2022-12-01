package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.User;
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
	 * 選択されたOrderItemを登録する.
	 * 
	 * @param form CartForm
	 * @return 商品一覧ページに移動
	 */
	@PostMapping("/insertOrderItem")
	public String insertOrderItem(CartForm form) {

		User user = (User) session.getAttribute("user");
		Integer userId = null;
		try {
			userId = user.getId();
		}catch(Exception e) {
			//ここでセッションIDを取得
			userId = session.hashCode();
//			userId = 1234221344;
			Order order =service.searchDummyOrder(userId);
			if(order.getUserId()==null) {
				service.createDummyOrder(userId);
			}
		}
		System.out.println("addItem()に移動します");
		service.addItem(form, userId);
		return "redirect:/cart/showCart";
	}

	/**
	 * カートの中身を表示する.
	 * 
	 * @param model リクエストスコープ用
	 * @return カート詳細画面に移動
	 */
	@GetMapping("/showCart")
	public String showCart(Model model) {
		System.out.println("showCart()に移動しました。");

		User user = (User) session.getAttribute("user");
		Integer userId = 0;
		if (user == null) {
			
			//ここでセッションIDを取得.
//		Integer dummyUserId = 1232445;
		Integer dummyUserId = session.hashCode();
		Order dummyOrder = service.createDummyOrder(dummyUserId);
		userId = dummyOrder.getUserId();
		}
		
		Order order = service.showCart(userId);
		model.addAttribute("order", order);
		return "cart_list";
	}
//
//			session.setAttribute("throughOrderConfirmation", true);
//			return "redirect:/loginUser/toLogin";

	/**
	 * 選択されたOrderItemとそのOrderToppingを削除する.
	 * 
	 * @param orderItemId 選択されたorderitemId
	 * @return 削除後のカート一覧画面
	 */
	@PostMapping("/deleteOrderItem")
	public String deleteOrderItem(Integer orderItemId) {

		service.deleteOrderItem(orderItemId);
		return "redirect:/cart/showCart";
	}
}
