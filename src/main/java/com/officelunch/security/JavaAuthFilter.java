package com.officelunch.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JavaAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JavaTokenUtil jwtService;
    @Autowired
    private  UserSpringService userSpringService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

           try {
               String token = authorizetoken(request);
           }catch (ExpiredJwtException exception) {
               System.out.println("Your Session is expired");
               System.out.println(exception);
           }
        filterChain.doFilter(request,response);
    }

    public String authorizetoken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token=authHeader.substring(7);
            username = jwtService.getUsernameFromToken(token);
            System.out.println(username+"99999");
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userSpringService.loadUserByUsername(username);
//            System.out.println(token+"ass"+ userDetails.getUsername());
            if(jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
//                System.out.println(SecurityContextHolder.getContext());
            }
        }
        return token;
    }
}
