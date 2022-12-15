package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	 * @param model  モデル
	 * @param itemId クリックしたアイテムのID
	 * @return 商品詳細画面
	 */
	@RequestMapping("/")
	public String showDetail(Model model, int itemId) {

		Item item = showDetailService.showItemDetail(itemId);
		model.addAttribute("item", item);

		return "item_detail";
	}
	
}
