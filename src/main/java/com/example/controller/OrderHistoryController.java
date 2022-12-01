package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.service.OrderHistoryService;

@Controller
@RequestMapping("/orderHistory")
public class OrderHistoryController {

	@Autowired
	private OrderHistoryService orderHistoryService;

	@Autowired
	private HttpSession session;

	@GetMapping("/")
	public String showHistory(Model model) {
		User user = (User) session.getAttribute("user");

		List<Order> finOrderList = orderHistoryService.showHistoryOfStatusFour(user.getId());
		model.addAttribute("finOrderList", finOrderList);
		List<Order> orderListThree = orderHistoryService.showHistoryOfStatusThree(user.getId());
		model.addAttribute("orderListThree", orderListThree);
		List<Order> orderListTwo = orderHistoryService.showHistoryOfStatusTwo(user.getId());
		model.addAttribute("orderListTwo", orderListTwo);
		List<Order> orderListOne = orderHistoryService.showHistoryOfStatusOne(user.getId());
		model.addAttribute("orderListOne", orderListOne);

		return "order_history";
	}

}
