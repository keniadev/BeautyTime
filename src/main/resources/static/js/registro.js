$(document).ready(function () {
    $('#registrationForm').on('submit', function (e) {
        e.preventDefault();

        const form = $(this);
        const formData = form.serialize();

        Swal.fire({
            title: '¿Guardar usuario?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Sí, guardar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#6a0572'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: '/usuarios/save',
                    type: 'POST',
                    data: formData,
                    success: function (response) {
                        Swal.fire({
                            icon: 'success',
                            title: '¡Éxito!',
                            text: response,
                            confirmButtonColor: '#6a0572'
                        }).then(() => {
                            window.location.href = '/usuarios'; // redirigir a la lista
                        });
                    },
                    error: function (xhr) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: xhr.responseText,
                            confirmButtonColor: '#6a0572'
                        });
                    }
                });
            }
        });
    });
});
