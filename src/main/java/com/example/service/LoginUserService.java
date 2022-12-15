package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ログインの処理を行うサービスクラス.
 * 
 * @author kumazawa
 *
 */
@Service
public class LoginUserService {
	@Autowired
	private UserRepository userRepository;

	public User findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

}
