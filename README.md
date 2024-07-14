# Oracle Next Education - Challenge 4
## Desafio 4 - API REST: Forohub con Spring
### Objetivos
- Crear un CRUD para un foro
- Realizar validaciones de datos
- Implementar un sistema de autenticación con JWT

### Desarrollo
- Para la autenticación se utilizó la librería auth0, se creó un filtro que intercepta las peticiones y valida el token.
- Se creó un CRUD para un foro, con validaciones de datos, usando Bean Validation.
- Se utilizó una base de datos MySQL para almacenar los datos.
- Se creó un .env para almacenar las variables de entorno.
- Se utilizó Postman para probar las peticiones.
- Se utilizó Spring Boot para el desarrollo de la API REST.
- Se utilizó una arquitectura de capas para el desarrollo: Controller, Service, Repository, Model.
- Se crearon DTOs para la transferencia de datos.