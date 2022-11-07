package com.kms.seft203.auth.JWT;

import com.kms.seft203.auth.user.User;
import com.kms.seft203.auth.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService customeUserDetailsService;


    private String getJwtTokenFromRequest(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal
            (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get only jwt token included in header
        String jwt = getJwtTokenFromRequest(request);
        //kiểm tra token và validate token trong validtaeToken
        if(StringUtils.hasText(jwt) && jwtService.validateToken(jwt)) {
            //lấy thông tin user
            Map<String,String> info = jwtService.getInfoFromJWT(jwt);
            User user = userService.findUserByUserName(info.get("username"));
            //lấy userDetail dựa trên userID
            UserDetails userDetails = customeUserDetailsService.loadUserById(user.getId());
            if(userDetails!=null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                //chỗ này không cần validate lại token username lần nữa vì điều đó đã
                //được check lúc đăng nhập
                //System.out.println("UserDetails "+userDetails);
                    //authenticate token receive
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    System.out.println("Authentication Token " + authenticationToken);
                    //accept token
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        //trong trường hợp token bị thay đổi -> load lại holder
        else SecurityContextHolder.getContext().setAuthentication(null);
        filterChain.doFilter(request, response);
    }

}
