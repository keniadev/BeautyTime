$(document).ready(function() {


    const today = new Date().toISOString().split('T')[0];
    $('#fecha').attr('min', today);


    $('#telefono').on('input', function() {
        this.value = this.value.replace(/[^0-9]/g, '');
        if (this.value.length > 15) {
            this.value = this.value.substring(0, 15);
        }
    });


    function cargarCupos() {
        var fecha = $('#fecha').val();
        var servicioId = $('#servicio').val();

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
        } else {
            $('#cupos').html('<option value="">Selecciona un servicio y una fecha</option>');
        }
    }
    $('#fecha').change(cargarCupos);
    $('#servicio').change(cargarCupos);


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

});
