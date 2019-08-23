package com.demo.ms.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.demo.ms.domain.AutenticatioVO;
import com.demo.ms.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("$security.jwt.token.secret-key:secret")
	private String secretKey;
	
	private long validityInMilliSeconds = 3600000;
	
	@Autowired
	private UserRepository repository;
	
	@PostConstruct
	protected void init() {
		
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	public String createToken(String username, List<String> roles) {
		
		Claims claims = Jwts.claims().setSubject(username);
		
		claims.put("roles", roles);
		
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliSeconds);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
		
	}
	
	public Authentication getAuthentication(String token) {
		
		UserDetails userDetails = repository.findByName(token);
		return new UsernamePasswordAuthenticationToken(userDetails, "",
				userDetails.getAuthorities());
	}

	public AutenticatioVO getUsername(String token) {
		
		AutenticatioVO autentication = new AutenticatioVO();
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		
		autentication.expiration = claims.get("exp").toString();
		autentication.name = claims.get("sub").toString();
		autentication.role = claims.get("roles").toString();
		
		return autentication;
	}
	
	public String resolveToken(HttpServletRequest req) {
		
		String bearerToken = req.getHeader("Authorization");
		
		if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
			
			return bearerToken.substring(7, bearerToken.length());
		}
		
		return null;
	}
	
	public boolean validateToken(String token) {
		
		try {
			
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			
			if(claims.getBody().getExpiration().before(new Date())) {
				
				return false;
			}
			
			return true;
			
		} catch (JwtException | IllegalArgumentException e) {
			
			throw new JwtException("TOKEN INVALIDO");
		}
		
	}
	
	
}
