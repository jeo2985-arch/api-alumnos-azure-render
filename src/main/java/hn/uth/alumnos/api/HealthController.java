package hn.uth.alumnos.api;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
public class HealthController {
 private final JdbcTemplate jdbc;
 public HealthController(JdbcTemplate jdbc){this.jdbc=jdbc;}
 @GetMapping("/") public Map<String,String> inicio(){return Map.of("aplicacion","API Alumnos","estado","iniciada","documentacion","Consulte README.md");}
 @GetMapping("/health") public ResponseEntity<Map<String,String>> health(){try{jdbc.queryForObject("SELECT 1",Integer.class);return ResponseEntity.ok(Map.of("status","UP","database","UP"));}catch(Exception ex){return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("status","DOWN","database","UNAVAILABLE"));}}
}
