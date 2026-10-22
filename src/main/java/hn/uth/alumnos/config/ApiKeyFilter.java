package hn.uth.alumnos.config;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
@Component
public class ApiKeyFilter extends OncePerRequestFilter {
 @Value("${app.api-key:}") private String apiKey;
 @Override protected boolean shouldNotFilter(HttpServletRequest request){return !request.getRequestURI().startsWith("/api/alumnos");}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain) throws ServletException,IOException {
  String supplied=req.getHeader("X-API-KEY");
  boolean valid=!apiKey.isBlank() && supplied!=null && MessageDigest.isEqual(apiKey.getBytes(StandardCharsets.UTF_8),supplied.getBytes(StandardCharsets.UTF_8));
  if(!valid){res.setStatus(401);res.setContentType(MediaType.APPLICATION_JSON_VALUE);res.getWriter().write("{\"error\":\"No autorizado\"}");return;}
  chain.doFilter(req,res);
 }
}
