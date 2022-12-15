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
 * @author kumazawa
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

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
		System.out.println(user);
		Integer userId = null;
		// いったんログインした状態でカートに追加していることを想定
//		if (user == null) {
//			userId = session.hashCode();
//		} else {
		userId = user.getId();
//		}
		cartService.addItem(form, userId);
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

		User user = (User) session.getAttribute("user");
		Integer userId = 0;
		// orderを手打ちで入力した際、userIdを１でとうろくしてあるため。稼働確認
		// Integer userId = 1;
		// 以下コメントアウトは表示機能のテストのためカートへの追加機能開発後にコメントアウト解除
		if (user == null) {
			userId = session.hashCode();

		} else {
			userId = user.getId();
		}

		Order order = cartService.showCart(userId);
		System.out.println("--------------------->"+order);
		if (order == null) {
			model.addAttribute("NoOrder", "カート内は空です。");
		} else {
			model.addAttribute("order", order);

//			// htmlのヘッダーのカードにアイテム数を表示させるためにセットしてます
//			int orderItemCount = order.getOrderItemList().size();
//			session.setAttribute("orderItemCount", orderItemCount);
		}
		return "cart_list";
	}

	/**
	 * 選択されたOrderItemとそのOrderToppingを削除する.
	 * 
	 * @param orderItemId 選択されたorderitemId
	 * @return 削除後のカート一覧画面
	 * 
	 *         引数に入れる。金ちゃんのやつString toOrderConfirm if
	 *         (toOrderConfirm.equals("toOrderConfirm")) { return
	 *         "redirect:/order/toOrder"; }
	 */
	@PostMapping("/deleteOrderItem")
	public String deleteOrderItem(Integer orderItemId) {
		cartService.deleteOrderItem(orderItemId);

		return "redirect:/cart/showCart";
	}
}
