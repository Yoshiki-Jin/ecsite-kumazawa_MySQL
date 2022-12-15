package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginUserControllerTest {
	@Autowired
	LoginUserController loginUserController;
	
	@Autowired
	private HttpSession session;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLogin1() {

		String login = loginUserController.login("yoshiki.kumazawa@gmail.com","12345678" );
		System.out.println(login);
		assertEquals("redirect:/", login, "T1:期待値と実際の結果が異なります");

	}

	@Test
	void testLogin2() {
		String login = loginUserController.login("yoshiki@gmail.com","12345678");
		System.out.println(login);
		assertEquals("login", login, "T2:期待値と実際の結果が異なります");

	}

	@Test
	void testLogin3() {
		String login = loginUserController.login("yoshiki.kumazawa@gmail.com","999999");
		assertEquals("login", login, "T3:期待値と実際の結果が異なります");

	}

	@Test
	void testLogin4() {
		String login = loginUserController.login("yoshiki@gmail.com","999999");
		assertEquals("login", login, "T4:期待値と実際の結果が異なります");

	}
}
