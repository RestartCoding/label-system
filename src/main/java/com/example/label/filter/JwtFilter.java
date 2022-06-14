package com.example.label.filter;

import com.example.label.exception.JwtExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

/**
 * @author xiabiao
 * @date 2022-06-14
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

  private static final String BEARER_PREFIX = "Bearer ";

  @Resource private Key jwtSecretKey;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // TODO: 开放接口统一配置在表里
    if (request.getServletPath().startsWith("/jwt")) {
      filterChain.doFilter(request, response);
      return;
    }
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(authorization) && authorization.startsWith(BEARER_PREFIX)) {
      Jws<Claims> claims =
          Jwts.parserBuilder()
              .setSigningKey(jwtSecretKey)
              .build()
              .parseClaimsJws(authorization.substring(BEARER_PREFIX.length()));
      Date date = claims.getBody().getExpiration();
      if (date.getTime() < System.currentTimeMillis()) {
        logger.error("Failed to Jwt authenticated.");
        throw new JwtExpiredException("Your jwt is expired. Please apply a new one.");
      }
      // TODO: 查询真正的用户权限
      String username = claims.getBody().getAudience();
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(
              "admin", "123456", AuthorityUtils.createAuthorityList("label:export"));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }
}
