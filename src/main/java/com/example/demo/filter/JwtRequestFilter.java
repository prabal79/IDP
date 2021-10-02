package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.services.MyUserDetailsService;
import com.example.demo.util.JwtUtil;
@Service
public class JwtRequestFilter extends OncePerRequestFilter{
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
	throws ServletException,IOException{
		final String authHeader=request.getHeader("Authorization");
		
		String username =null;
		String jwt =null;
		
		if(authHeader!=null && authHeader.startsWith("Bearer"))
		{
			jwt=authHeader.substring(7);
			username=jwtUtil.extractUsername(jwt);
			
					
		}
		
		if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usr= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usr.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usr);
			}
		}
		chain.doFilter(request, response);
	}

}