package com.example.label.filter;

import com.example.label.authentication.JwtAuthenticationToken;
import com.example.label.entity.User;
import com.example.label.exception.JwtExpiredException;
import com.example.label.repository.UserRepository;
import com.example.label.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
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
import java.util.Optional;

/**
 * @author xiabiao
 * @date 2022-06-14
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

  private static final String BEARER_PREFIX = "Bearer ";

  @Resource private Key jwtSecretKey;

  @Resource
  private UserService userService;

  @Resource
  private UserRepository userRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

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

      String username = claims.getBody().getAudience();
      Optional<User> optionalUser = userRepository.findByUsername(username);
      if (optionalUser.isPresent()){
        // 这里的密码已经是加密之后的了
        Authentication authentication =
            new JwtAuthenticationToken(
                username, optionalUser.get().getPassword(), userService.getAuthorities(username));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    filterChain.doFilter(request, response);
  }
}
