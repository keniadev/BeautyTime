$(document).ready(function() {

    // 1️⃣ Mostrar errores del backend con SweetAlert
    const backendError = $('#backendError').text().trim();
    if (backendError.length > 0) {
        Swal.fire({
            icon: 'error',
            title: 'Errores al guardar usuario',
            html: backendError.replace(/\n/g, '<br>'),
            confirmButtonColor: '#6a0572'
        });
    }

    // 2️⃣ Validación frontend
    $('.registration-form').on('submit', function(e) {
        e.preventDefault(); // evitar submit automático
        const form = this;

        const nombre = $('#nombre').val().trim();
        const apellido = $('#apellido').val().trim();
        const correo = $('#correo').val().trim();
        const contrasena = $('#contrasena').val().trim();
        const rol = $('#rol').val();

        if (!nombre) { Swal.fire({icon:'warning', title:'Nombre vacío', text:'Por favor ingresa el nombre', confirmButtonColor:'#6a0572'}); return; }
        if (!apellido) { Swal.fire({icon:'warning', title:'Apellido vacío', text:'Por favor ingresa el apellido', confirmButtonColor:'#6a0572'}); return; }
        if (!correo) { Swal.fire({icon:'warning', title:'Correo vacío', text:'Por favor ingresa un correo', confirmButtonColor:'#6a0572'}); return; }
        const correoRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!correoRegex.test(correo)) { Swal.fire({icon:'warning', title:'Correo inválido', text:'Por favor ingresa un correo válido', confirmButtonColor:'#6a0572'}); return; }
        if (!contrasena) { Swal.fire({icon:'warning', title:'Contraseña vacía', text:'Por favor ingresa una contraseña', confirmButtonColor:'#6a0572'}); return; }
        if (contrasena.length < 6) { Swal.fire({icon:'warning', title:'Contraseña demasiado corta', text:'Debe tener al menos 6 caracteres', confirmButtonColor:'#6a0572'}); return; }
        if (!rol) { Swal.fire({icon:'warning', title:'Rol no seleccionado', text:'Selecciona un rol', confirmButtonColor:'#6a0572'}); return; }

        // Confirmación antes de enviar
        Swal.fire({
            title: '¿Guardar usuario?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Sí, guardar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#6a0572'
        }).then((result) => {
            if (result.isConfirmed) {
                form.submit();
            }
        });
    });

});
