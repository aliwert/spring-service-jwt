package com.alimert.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Beareer ...token
        String header;
        String token;
        String username;
        header =  request.getHeader("Authorization");

        if (header== null) {
            filterChain.doFilter(request, response);
            return;
        }

       token = header.substring(7);
        try {
            username = jwtService.getUserNameByToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails!=null && jwtService.isTokenExpired(token)) {
                    // I can to give permission person to SecurityContext
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

                    authentication.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        }catch (ExpiredJwtException e) {
            System.out.println("Token has been expired" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception occured" + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
