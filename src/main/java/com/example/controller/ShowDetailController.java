package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowDetailService;

/**
 * 商品情報を操作するコントローラー.
 * 
 * @author inagakisaia
 *
 */
@Controller
@RequestMapping("/show-detail")
public class ShowDetailController {

	@Autowired
	private ShowDetailService showDetailService;

	/**
	 * 商品詳細画面を表示します.
	 * 
	 * @param id ID
	 * @return 商品詳細画面
	 */
	@GetMapping("/")
	public String showDetail(Integer id, Model model) {
		Item item = showDetailService.showDetail(id);
		model.addAttribute("item", item);
		return "item_detail";
	}

}
