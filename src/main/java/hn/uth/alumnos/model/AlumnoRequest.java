package hn.uth.alumnos.model;
import jakarta.validation.constraints.*;
public record AlumnoRequest(
 @NotBlank(message="El nombre es obligatorio") @Size(max=100) String nombre,
 @NotBlank(message="El correo es obligatorio") @Email @Size(max=100) String correo
) {}
