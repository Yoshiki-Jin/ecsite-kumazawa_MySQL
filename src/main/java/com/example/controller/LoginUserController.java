package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.service.LoginUserService;

/**
 * ログイン画面の処理を行うコントローラークラス.
 * 
 * @author hongo
 *
 */
@Controller
@RequestMapping("/loginUser")
public class LoginUserController {
	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	private HttpSession session;

	/**
	 * ログイン画面を出力します.
	 * 
	 * @param form ログインユーザーフォーム
	 * @return ログイン画面
	 */
	@RequestMapping("/toLogin")
	public String toLogin(LoginUserForm form) {

		return "login";
	}

	/**
	 * ログインを行うメソッド.
	 * 
	 * @param form   ユーザーログイン情報フォーム
	 * @param result エラー情報を格納するオブジェクト
	 * @return ログイン前に表示していたページ
	 */
	@PostMapping("/login")
	public String login(Model model, LoginUserForm form, BindingResult result) {

		User user = loginUserService.login(form.getEmail(), form.getPassword());

		if (user == null) {
			model.addAttribute("errorMessage", "メールアドレス、またはパスワードが間違っています。");
			return toLogin(form);
		}
		session.setAttribute("user", user);
		
//		下記はCartControllerのshowCartメソッドと統合した際に実装するためコメントアウトします

		// CartControllerでログインだった場合、「true」・未ログインだった場合「null」でsessionスコープにthroughOrderConfirmationがセットされるため、それを、"isThroughOrderConfirmation"に受け取る。
		boolean isThroughOrderConfirmation = (boolean) session.getAttribute("throughOrderConfirmation");

		if (isThroughOrderConfirmation) { // もし、isThroughOrderConfirmationがtrueだった場合、注文確認画面へ
			session.removeAttribute("throughOrderConfirmation");
			return "redirect:/cart/showCart";

		} else { // それ以外の場合は商品一覧へ
			session.removeAttribute("throughOrderConfirmation");
		}

		return "forward:/show-itemList/";
	}

}
