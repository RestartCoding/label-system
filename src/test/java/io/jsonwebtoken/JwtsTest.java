package io.jsonwebtoken;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiabiao
 * @date 2022-06-14
 */
public class JwtsTest {

  @Test
  public void testGenerate() throws NoSuchAlgorithmException {
    SecretKey secretKey = KeyGenerator.getInstance("HmacSHA512").generateKey();
    String subject = "214.com";
    String jwtStr =
        Jwts.builder().setSubject(subject).signWith(secretKey, SignatureAlgorithm.HS512).compact();
    System.out.println("jwt = " + jwtStr);

    Jws<Claims> claims =
        Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtStr);
    Assert.assertEquals(subject, claims.getBody().getSubject());
  }
}
