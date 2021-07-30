package test.example.helloworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import test.example.helloworld.model.AuthenticationRequest;
import test.example.helloworld.model.AuthenticationResponse;
import test.example.helloworld.util.JwtUtil;

@RestController
public class UserController {
	
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private UserDetailsService userService;
	@Autowired
	public JwtUtil jwtUtil;
	
	
	@RequestMapping("/sample")
	private String returnSample() {
		return "sample-test";
	}

	@RequestMapping(value="/auth", method=RequestMethod.POST)
	public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authReq){
		//TODO add try catch
		authManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUserName(), authReq.getPassword()));
		
		final String jwtToken = jwtUtil.generateToken(userService.loadUserByUsername(authReq.getUserName()));
		
		return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
	}
}
