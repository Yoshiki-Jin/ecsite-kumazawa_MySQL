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
		System.out.println("insertOrderItem()に移動しました。");
		System.out.println("セッションID　＝　"+session.hashCode());
		User user = (User) session.getAttribute("user");
		System.out.println("ログイン中のuser情報　="+user);
		
		Integer userId = null;
		if(user == null) {
			userId = session.hashCode();
		} else {
			userId = user.getId();
		}
//		try {
//			userId = user.getId();
//		}catch(Exception e) {
//			//ここでセッションIDを取得
//			userId = session.hashCode();
////			userId = 1234221344;
//			Order order =service.searchDummyOrder(userId);
//			try {
//				order.getUserId();
//			}catch(Exception ee) {
//				service.createDummyOrder(userId);
//				userId = service.searchDummyOrder(userId).getUserId();
//			}
//		}
		System.out.println("登録予定のuserId ="+userId);
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
		System.out.println("セッションID ＝　"+session.hashCode());

		User user = (User) session.getAttribute("user");
		Integer userId = 0;
		if (user == null) {
			userId = session.hashCode();
			
		} else {
			userId = user.getId();
		}
//		Integer userId = 0;
//		if (user == null) {
//			
//			//ここでセッションIDを取得.
////		Integer dummyUserId = 1232445;
//		Integer dummyUserId = session.hashCode();
//		Order dummyOrder = service.createDummyOrder(dummyUserId);
//		userId = dummyOrder.getUserId();
//		}
		
		Order order = service.showCart(userId);
		if(order==null) {
			model.addAttribute("NoOrder","カート内は空です。");
		}else {
			model.addAttribute("order", order);		
		}
		System.out.println("showCart()からcart_listに遷移する直前です。");
		System.out.println("セッションID ＝　"+session.hashCode());
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
