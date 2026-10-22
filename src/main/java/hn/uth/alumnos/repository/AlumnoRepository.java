package hn.uth.alumnos.repository;
import hn.uth.alumnos.model.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.*;
@Repository
public class AlumnoRepository {
 private final JdbcTemplate db;
 public AlumnoRepository(JdbcTemplate db) { this.db=db; }
 private final RowMapper<Alumno> mapper=(rs,rowNum)->new Alumno(rs.getInt("id"),rs.getString("nombre"),rs.getString("correo"));
 public List<Alumno> listar() {return db.query("SELECT id, nombre, correo FROM dbo.Alumnos ORDER BY id",mapper);}
 public Optional<Alumno> buscar(int id) {
  List<Alumno> encontrados=db.query("SELECT id, nombre, correo FROM dbo.Alumnos WHERE id=?",mapper,id);
  return encontrados.stream().findFirst();
 }
 public Alumno registrar(AlumnoRequest req) {
  KeyHolder key=new GeneratedKeyHolder();
  db.update(con->{PreparedStatement ps=con.prepareStatement("INSERT INTO dbo.Alumnos (nombre, correo) VALUES (?, ?)",Statement.RETURN_GENERATED_KEYS);ps.setString(1,req.nombre().trim());ps.setString(2,req.correo().trim());return ps;},key);
  if(key.getKey()==null) throw new IllegalStateException("No se obtuvo el ID generado");
  return new Alumno(key.getKey().intValue(),req.nombre().trim(),req.correo().trim());
 }
 public boolean actualizar(int id,AlumnoRequest req) {return db.update("UPDATE dbo.Alumnos SET nombre=?, correo=? WHERE id=?",req.nombre().trim(),req.correo().trim(),id)>0;}
 public boolean eliminar(int id) {return db.update("DELETE FROM dbo.Alumnos WHERE id=?",id)>0;}
}
