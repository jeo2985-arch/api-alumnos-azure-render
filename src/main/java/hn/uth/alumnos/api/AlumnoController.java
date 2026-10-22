package hn.uth.alumnos.api;
import hn.uth.alumnos.model.*;
import hn.uth.alumnos.repository.AlumnoRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.util.*;
@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {
 private final AlumnoRepository repo;
 public AlumnoController(AlumnoRepository repo){this.repo=repo;}
 @GetMapping public List<Alumno> listar(){return repo.listar();}
 @GetMapping("/{id}") public Alumno buscar(@PathVariable int id){return repo.buscar(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Alumno no encontrado"));}
 @PostMapping public ResponseEntity<Alumno> crear(@Valid @RequestBody AlumnoRequest req){Alumno nuevo=repo.registrar(req);return ResponseEntity.created(URI.create("/api/alumnos/"+nuevo.id())).body(nuevo);}
 @PutMapping("/{id}") public Alumno actualizar(@PathVariable int id,@Valid @RequestBody AlumnoRequest req){if(!repo.actualizar(id,req))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Alumno no encontrado");return repo.buscar(id).orElseThrow();}
 @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable int id){if(!repo.eliminar(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Alumno no encontrado");return ResponseEntity.noContent().build();}
}
