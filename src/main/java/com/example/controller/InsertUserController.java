package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.User;
import com.example.form.InsertUserForm;
import com.example.form.LoginUserForm;
import com.example.service.InsertUserService;

/**
 * ユーザー登録画面の処理を行うコントローラークラス.
 * 
 * @author kumazawa
 *
 */
@Controller
@RequestMapping("/insertUser")
public class InsertUserController {
	@Autowired
	InsertUserService insertUserService;

	@GetMapping("/toInsertScreen")
	public String toInsert(InsertUserForm insertUserForm) {
		return "register_admin";
	}

	@PostMapping("/insert")
	public String insert(@Validated InsertUserForm insertUserForm, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		if (insertUserService.findByEmail(insertUserForm.getEmail()) != null) {
			result.rejectValue("email", null, "既に登録されているメールアドレスです");
			return toInsert(insertUserForm);
		} else if (result.hasErrors()) {
			return toInsert(insertUserForm);
		}
		User user = new User();
		BeanUtils.copyProperties(insertUserForm, user);
		user.setName(insertUserForm.getLastName() + insertUserForm.getFirstName());
		insertUserService.insert(user);
		return "reirect:/insertUser/login";
	}

	@GetMapping("/toLogin")
	public String toLogin() {
		return "login";
	}
}
