package com.example.demo;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.AuthenticanResponse;
import com.example.demo.model.AuthenticationRequest;
import com.example.demo.services.MyUserDetailsService;


@RestController
public class CardInterface {
	
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private MyUserDetailsService userDetailService;
	
	@Autowired
	private   JwtUtil   jwtTokenUtil;
	
	@RequestMapping("/getCardInfo")
	public String getCardInfo()
	{
		return "Welcome! you have access right";
	}
	
	@RequestMapping(value="/authenticate",method= RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authreq) throws Exception
	{
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(authreq.getUsername(),authreq.getUserpassword()));
		
		}catch(BadCredentialsException e)
		{
			throw new Exception("Incorrect username or password" , e);
		}
		
		//if authentication succeed- get the userDeytails 
		
		final UserDetails userDetails= userDetailService.loadUserByUsername(authreq.getUsername());
		
		//Generate the JWT token
	    final String jwt=jwtTokenUtil.generateToken(userDetails);
	    
	    return ResponseEntity.ok(new AuthenticanResponse(jwt));
	
	
	}
	

}
