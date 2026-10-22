package hn.uth.alumnos.api;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<Map<String,Object>> invalid(MethodArgumentNotValidException e){
  return ResponseEntity.badRequest().body(Map.of("error","Datos inválidos","detalles",e.getBindingResult().getFieldErrors().stream().map(f->f.getField()+": "+f.getDefaultMessage()).toList()));
 }
}
