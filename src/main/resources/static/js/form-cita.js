$(document).ready(function() {


    const today = new Date().toISOString().split('T')[0];
    $('#fecha').attr('min', today);


    $('#telefono').on('input', function() {
        this.value = this.value.replace(/[^0-9]/g, '');
        if (this.value.length > 15) {
            this.value = this.value.substring(0, 15);
        }
    });

    // 💡 PASO 1: DETERMINAR EL ELEMENTO DE SERVICIO ACTIVO (El ID que existe en la página)
    let servicioInput = $('#servicio-global'); // Intenta obtener el ID del modo Global
    if (servicioInput.length === 0) {
        servicioInput = $('#servicio-individual'); // Si no existe, intenta obtener el ID del modo Individual
    }

    // Si no se encuentra ningún input de servicio esencial, detenemos la lógica de cupos.
    if (servicioInput.length === 0) {
        console.error("No se encontró el campo de Servicio (#servicio-global o #servicio-individual).");
        // Asegúrate de que el mensaje de cupos refleje la falta de selección.
        $('#cupos').html('<option value="">Error de configuración: falta campo Servicio</option>');
        return;
    }

    // Ahora, la variable 'servicioInput' apunta al elemento correcto, sin importar su ID.

    function cargarCupos() {
        var fecha = $('#fecha').val();
        // 🔥 PASO 2: USAMOS LA VARIABLE DETECTADA PARA OBTENER EL VALOR
        var servicioId = servicioInput.val();

        // El mensaje ahora debe ser general, ya que el servicio podría estar seleccionado
        // o ser fijo. Si el servicioId no existe, significa que es el modo global y no ha seleccionado.

        if(fecha && servicioId) {
            $.ajax({
                url: '/cupos-disponibles',
                data: { fecha: fecha, servicioId: servicioId },
                success: function(cupos) {
                    var cuposSelect = $('#cupos');
                    var seleccionAnterior = cuposSelect.val();

                    cuposSelect.empty();
                    if(cupos.length > 0) {

                        cuposSelect.append('<option value="">Elige un cupo</option>');

                        cupos.forEach(function(cupo) {
                            cuposSelect.append(
                                $('<option></option>')
                                    .val(cupo.id)
                                    .text(cupo.nombreTurno + ' | ' + cupo.horaInicio + ' - ' + cupo.horaFin)
                            );
                        });

                        if(seleccionAnterior && cuposSelect.find('option[value="' + seleccionAnterior + '"]').length > 0){
                            cuposSelect.val(seleccionAnterior);
                        } else {
                            cuposSelect.val('');
                        }
                    } else {
                        cuposSelect.append('<option value="">No hay cupos disponibles</option>');
                    }
                },
                error: function() {
                    $('#cupos').empty();
                    $('#cupos').append('<option value="">Error al cargar cupos</option>');
                }
            });
        } else if (!fecha) {
             $('#cupos').html('<option value="">Selecciona una fecha</option>');
        } else {
             $('#cupos').html('<option value="">Selecciona un servicio y una fecha</option>');
        }
    }

    $('#fecha').change(cargarCupos); // Este evento aplica a ambos modos

    // 💡 PASO 3: LÓGICA DE DISPARO DE EVENTOS Y CARGA INICIAL

    // MODO GLOBAL: Si es un SELECT, agregamos el evento change para cuando el usuario lo cambie.
    if (servicioInput.is('select')) {
        servicioInput.change(cargarCupos);
    }
    // MODO INDIVIDUAL: Si es un INPUT HIDDEN, el servicio ya está fijo. Cargamos al iniciar
    // si ya hay una fecha (o si el usuario la selecciona).
    else if (servicioInput.is('input:hidden') && $('#fecha').val()) {
        cargarCupos();
    }


    setInterval(cargarCupos, 30000);


    $('#appointmentForm').on('submit', function(e) {
        e.preventDefault();
        const form = this;


        let isValid = true;
        $(form).find('[required]').each(function() {
            if (!$(this).val()) { isValid = false; return false; }
        });

        if (!isValid) {
            Swal.fire({
                icon:'warning',
                title:'Campos vacíos',
                text:'Por favor completa todos los campos requeridos',
                confirmButtonColor:'#6a0572'
            });
            return;
        }

        // Validación de teléfono mínimo 8 dígitos
        const telefono = $('#telefono').val();
        if (telefono.length < 8) {
            Swal.fire({
                icon:'warning',
                title:'Teléfono inválido',
                text:'El teléfono debe tener al menos 8 dígitos',
                confirmButtonColor:'#6a0572'
            });
            return;
        }


        Swal.fire({
            title: '¿Confirmar cita?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Sí, agendar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if(result.isConfirmed){
                $.ajax({
                    type: 'POST',
                    url: $(form).attr('action'),
                    data: $(form).serialize(),
                    success: function() {
                        Swal.fire({
                            title: '¡Éxito!',
                            text: 'Tu cita ha sido agendada correctamente',
                            icon: 'success'
                        }).then(() => {
                            window.location.href = '/citas/mis-citas';
                        });
                    },
                    error: function(xhr) {
                        Swal.fire({
                            title:'Error',
                            text:'No se pudo agendar la cita: ' + xhr.responseText,
                            icon:'error'
                        });
                    }
                });
            }
        });
    });

    // Lógica para mostrar errores de servidor (si vienen del POST)
    const errorMessageElement = $('#errorMessageContainer');
    if (errorMessageElement.length && errorMessageElement.data('error-message')) {
        Swal.fire({
            icon:'error',
            title:'Error al Agendar Cita',
            text: errorMessageElement.data('error-message'),
            confirmButtonColor:'#6a0572'
        });
    }

});