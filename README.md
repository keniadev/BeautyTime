# BeautyTime App

BeautyTime es una aplicación web para la gestión de citas en salones de belleza y servicios relacionados. Permite a los usuarios registrarse, explorar categorías y servicios, y reservar citas en horarios disponibles. Además, cuenta con un panel administrativo para la gestión integral de citas, servicios y usuarios.

## Flujo de la aplicación

### Para el cliente (usuario)

1. **Registro y autenticación:**  
   El usuario se registra con su información personal (nombre, apellido, correo, contraseña) y puede iniciar sesión.

2. **Explorar servicios:**  
   En la página principal, el usuario ve una lista de categorías (como corte de cabello, manicura, etc.). Al seleccionar una categoría, puede ver los servicios disponibles dentro de ella.

3. **Agendar cita:**  
   Desde un servicio, el usuario puede elegir agendar una cita.  
   - Selecciona un cupo disponible (fecha y turno).  
   - Ingresa un número de teléfono para contacto.  
   - Confirma la reserva.

4. **Ver y gestionar citas:**  
   El usuario puede ver el estado de sus citas (reservadas, canceladas, realizadas) y cancelar citas si lo desea.

### Para el administrador

1. **Gestión de datos:**  
   El administrador puede crear, modificar o eliminar:  
   - Categorías  
   - Servicios  
   - Turnos  
   - Roles  
   - Estados de cita  
   - Cupos disponibles  

2. **Gestión de citas:**  
   Puede visualizar todas las citas, filtrarlas por estado y administrar su estado (por ejemplo, marcar como realizada o cancelada).

3. **Gestión de usuarios:**  
   Puede administrar los roles y usuarios registrados.

## Tecnologías utilizadas

- Java EE / Jakarta EE  
- JPA / Hibernate  
- Base de datos MySQL  
- Framework web (Spring Boot, Jakarta REST, etc.

