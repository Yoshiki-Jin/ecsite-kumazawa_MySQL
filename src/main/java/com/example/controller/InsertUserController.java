package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.InsertUserForm;
import com.example.service.InsertUserService;

/**
 * ユーザー登録画面の処理を行うコントローラークラス.
 * 
 * @author hongo
 *
 */
@Controller
@RequestMapping("/insertUser")
public class InsertUserController {
	@Autowired
	InsertUserService insertUserService;

	/**
	 * ユーザー情報登録画面に遷移する.
	 * 
	 * @param form ユーザー情報を登録する際のフォーム.
	 * @return ユーザー情報登録画面
	 */
	@GetMapping("/toInsert")
	public String toInsert(InsertUserForm form) {
		return "register_user";
	}

	/**
	 * ユーザー情報の登録を行う.
	 * 
	 * @param form
	 * @return
	 */
	@PostMapping("/insert")
	public String insert(InsertUserForm form) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setName(form.getLastName()+form.getFirstName());
		insertUserService.insert(user);

		return "redirect:/insertUser/login";
	}

	/**
	 * ログイン画面に遷移する.
	 * 
	 * @return ログイン画面
	 */
	@GetMapping("/login")
	public String tologin() {
		return "login";
	}

}
