# API REST de Alumnos · Azure SQL + Render

API Java 21 + Spring Boot + JDBC sobre la tabla **dbo.Alumnos** existente en **AvanzadaII**. No crea ni borra tablas.

## Etapas de la actividad
1. Descargar, revisar y compilar este proyecto **sin compartir secretos**.
2. Crear repositorio nuevo en GitHub y subir los archivos (previa autorización).
3. Crear un Render Web Service con runtime **Docker** y vincular el repositorio.
4. Configurar las variables `DB_HOST`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` y `API_KEY` desde **Environment** de Render, nunca en el código.
5. Comprobar la salida de Render hacia Azure SQL y ajustar reglas de red con el mínimo acceso indispensable.
6. Probar endpoints y operaciones CRUD.

## Variables
- `DB_HOST`: DNS Azure SQL sin `https://` ni `:1433`.
- `DB_NAME`: `AvanzadaII`.
- `DB_USER`: usuario SQL con privilegios mínimos sobre `dbo.Alumnos`.
- `DB_PASSWORD`: contraseña SQL.
- `API_KEY`: secreto largo y aleatorio para proteger las rutas `/api/alumnos`. Es diferente de la contraseña SQL.
- `PORT`: aportado por Render; localmente 8080.

**No copies tus valores reales a `.env.example`, archivos Java, Dockerfile, Git o capturas.**

## Iniciar localmente / Codespaces
En una terminal donde las variables estén configuradas:

```bash
mvn clean package
mvn spring-boot:run
```

La aplicación escucha en `http://localhost:8080` (o en `PORT`).

## Endpoints
| Método | Ruta | Función |
| --- | --- | --- |
| GET | `/` | Estado básico de la aplicación (público) |
| GET | `/health` | Comprueba conexión SQL, 200/503 (público) |
| GET | `/api/alumnos` | Lista todos los registros (requiere clave) |
| GET | `/api/alumnos/{id}` | Busca por ID (requiere clave) |
| POST | `/api/alumnos` | Inserta alumno (requiere clave) |
| PUT | `/api/alumnos/{id}` | Actualiza alumno (requiere clave) |
| DELETE | `/api/alumnos/{id}` | Elimina alumno (requiere clave) |

Todos los endpoints `/api/alumnos` requieren cabecera `X-API-KEY` con valor `API_KEY`.

Ejemplos *locales* con clave solo en variable (no la pegues en mensajes):

```bash
curl -H "X-API-KEY: $API_KEY" http://localhost:8080/api/alumnos
curl -H "X-API-KEY: $API_KEY" http://localhost:8080/api/alumnos/1
curl -X POST http://localhost:8080/api/alumnos -H "Content-Type: application/json" -H "X-API-KEY: $API_KEY" -d '{"nombre":"Alumno de Prueba","correo":"prueba@uth.hn"}'
curl -X PUT http://localhost:8080/api/alumnos/6 -H "Content-Type: application/json" -H "X-API-KEY: $API_KEY" -d '{"nombre":"Alumno Actualizado","correo":"actualizado@uth.hn"}'
curl -X DELETE http://localhost:8080/api/alumnos/6 -H "X-API-KEY: $API_KEY"
```

No uses el ID `6` sin comprobar antes que corresponda a tu registro de prueba.

## Render
- New > Web Service > vincula repositorio nuevo.
- Language/Runtime: **Docker**; Dockerfile en la raíz.
- Variables en Render Environment; ninguna credencial en Git.
- Health check path: `/health`.
- URL de la API: `https://<nombre-servicio>.onrender.com`.
- **Cuidado con Azure SQL firewall:** el servicio puede salir por direcciones IP distintas a Codespaces. Verifica la IP efectiva o rangos de salida documentados antes de permitirla.

## Notas de seguridad y funcionamiento
- Conexión SQL cifrada y certificado validado (`encrypt=true;trustServerCertificate=false`).
- Clave API sencilla para práctica académica; para un servicio de producción con usuarios reales, implementar autenticación individual, autorización, limitación de peticiones y auditoría.
- El endpoint `/health` prueba la DB y no publica detalles internos del error.
- Un servicio gratuito de Render puede suspenderse por inactividad, según sus condiciones vigentes.
- La base Serverless de Azure también puede reactivarse al recibir conexiones, lo que ocasionalmente produce un error transitorio.
