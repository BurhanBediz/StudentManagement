package com.TechProEd.StudentManagementSystem.security.jwt;

import com.TechProEd.StudentManagementSystem.domain.Student;
import com.TechProEd.StudentManagementSystem.repository.StudentRepository;
import com.TechProEd.StudentManagementSystem.security.service.StudentDetailsServiceImpl;
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
import java.util.Optional;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private StudentDetailsServiceImpl studentDetailsService;
    @Autowired
    private StudentRepository studentRepository;

    private String parseJwt(HttpServletRequest request){
        String headerAuth=request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String jwt=parseJwt(request);
            if(jwt != null && jwtUtils.validateJwtToken(jwt)){
                Long id = jwtUtils.getIdFromJwtToken(jwt);
                request.setAttribute("id",id);
                Optional<Student> student = studentRepository.findById(id);
                UserDetails userDetails = studentDetailsService.loadUserByUsername(student.get().getEmail());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        catch(Exception e){

            logger.error("Kimlik doğrulama yapılamıyor: {}", e);

        }
        filterChain.doFilter(request,response);
    }

}
