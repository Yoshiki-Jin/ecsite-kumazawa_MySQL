package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ユーザー情報を登録する操作を行うサービスクラス.
 * 
 * @author kumazawa
 *
 */
@Service
@Transactional
public class InsertUserService {
	@Autowired
	private UserRepository userRepository;

	public void insert(User user) {
		userRepository.insert(user);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
