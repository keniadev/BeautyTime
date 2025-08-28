
        $(document).ready(function() {


$('#telefono').on('input', function() {
    this.value = this.value.replace(/[^0-9]/g, '');
    if (this.value.length > 15) {
        this.value = this.value.substring(0, 15);
    }
});


            $('#appointmentForm').on('submit', function(e) {

                e.preventDefault();

                const form = this;
                let isValid = true;


                $(form).find('[required]').each(function() {
                    if (!$(this).val()) {
                        isValid = false;
                        return false;
                    }
                });

                if (!isValid) {
                    Swal.fire({
                        icon: 'warning',
                        title: 'Campos Vacíos',
                        text: 'Por favor, completa todos los campos requeridos para agendar la cita.',
                        confirmButtonColor: '#6a0572'
                    });
                } else {

                    Swal.fire({
                        title: '¿Confirmar Cita?',
                        text: 'Una vez agendada, no podrás revertir esta acción fácilmente.',
                        icon: 'question',
                        showCancelButton: true,
                        confirmButtonColor: '#6a0572',
                        cancelButtonColor: '#d1b1c6',
                        confirmButtonText: 'Sí, agendar',
                        cancelButtonText: 'No, cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {

                            Swal.fire({
                                title: '¡Éxito!',
                                text: 'Tu cita ha sido agendada correctamente.',
                                icon: 'success',
                                confirmButtonColor: '#6a0572',
                                timer: 2000,
                                timerProgressBar: true,
                                didClose: () => {
                                    form.submit();
                                }
                            });
                        }
                    });
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
            setInterval(cargarCupos, 1000);
        });
