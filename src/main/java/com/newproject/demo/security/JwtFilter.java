package com.newproject.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {

			String token = header.substring(7);

			try {
				String username = jwtUtil.extractUsername(token);

				if (username != null) {
					// Mark user as authenticated
					SecurityContextHolder.getContext().setAuthentication(
							new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
									username, null, new ArrayList<>()));
				}

			} catch (Exception e) {
				System.out.println("Invalid Token");
			}
		}

		filterChain.doFilter(request, response);
	}

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
       String path = request.getRequestURI();

       return path.startsWith("/api/auth") ||
               path.startsWith("/swagger-ui") ||
               path.startsWith("/v3/api-docs");
    }
}