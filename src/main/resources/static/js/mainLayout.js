 document.getElementById('current-year').textContent = new Date().getFullYear();


    document.getElementById('logout-button').addEventListener('click', function(event) {
        event.preventDefault();

        Swal.fire({
            title: '¿Seguro que quieres cerrar sesión?',
            text: '¡Tendrás que volver a iniciar sesión para continuar!',
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#6a0572',
            cancelButtonColor: '#d1b1c6',
            confirmButtonText: 'Sí, cerrar sesión',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById('logout-form').submit();
            }
        });
    });