package com.newproject.demo.controller;

import com.newproject.demo.dto.AuthRequest;
import com.newproject.demo.entity.User;
import com.newproject.demo.security.JwtUtil;
import com.newproject.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public String register(@RequestBody User user) {
		userService.saveUser(user);
		return "User saved";
	}

	@PostMapping("/login")
	public String login(@RequestBody AuthRequest request) {

		User user = userService.getByUsername(request.getUsername());

		if (user == null) {
			throw new RuntimeException("User not found");
		}

		if (!userService.checkPassword(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid password");
		}

		return jwtUtil.generateToken(user.getUsername());
	}
}