package org.security.app.util;

import java.sql.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {

   	
 public static String generateToken(String userId) {
	   return Jwts.builder()
	            .setSubject(userId)
	            .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong("1000")))
	            .signWith(SignatureAlgorithm.HS512, "secretToken" )
	            .compact();
 }
}
