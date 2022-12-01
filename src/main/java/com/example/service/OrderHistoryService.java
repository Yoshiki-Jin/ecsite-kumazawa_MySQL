package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

@Service
public class OrderHistoryService {

	@Autowired
	private OrderRepository orderRepository;

	public List<Order> showHistoryOfStatusFour(Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatusFour(userId);
		return orderList;
	}
	public List<Order> showHistoryOfStatusThree(Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatusThree(userId);
		return orderList;
	}
	public List<Order> showHistoryOfStatusTwo(Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatusTow(userId);
		return orderList;
	}
	public List<Order> showHistoryOfStatusOne(Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatusOne(userId);
		return orderList;
	}

}
