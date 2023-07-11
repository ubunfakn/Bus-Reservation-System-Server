package com.ubunfakn.reservation.bus_reserv_systm.SecurityConfig;

import java.io.IOException;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

@Component
public class RequestFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                String requestHeader = request.getHeader("Authorization");

                String username = null;
                String token = null;
                logger.info(requestHeader);
                if(requestHeader!=null && requestHeader.startsWith("Bearer")){
                    token = requestHeader.substring(7);
                    try {
                        username = this.jwtHelper.getUsernameFromToken(token);
                    } catch (IllegalArgumentException exception) {
                        logger.info("Illegal Argument while fetching the username !!");
                    }catch (ExpiredJwtException exception){
                        logger.info("Token is expired !!");
                    }catch(MalformedJwtException exception){
                        logger.info("Changes made to token !! Invalid Token");
                    }catch(Exception exception){
                        exception.printStackTrace();
                    }
                }else{
                    logger.info("Invalid header value !!");
                }

                if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    Boolean validToken = this.jwtHelper.validateToken(token, userDetails);
                    if(validToken){
                        UsernamePasswordAuthenticationToken uAuthenticationToken = 
                                new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                        uAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(uAuthenticationToken);
                    }else{
                        logger.info("Validation fails");
                    }
                }
                filterChain.doFilter(request, response);
    }
    
}
