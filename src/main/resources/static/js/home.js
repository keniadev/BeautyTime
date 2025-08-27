// ===== BUSCADOR Y FILTRO =====
document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const btnBuscar = document.getElementById('btnBuscar');
    const buscadorInput = document.getElementById('buscadorInput');
    const filtroSelect = document.getElementById('filtroSelect');
    const logo = document.querySelector('.logo-click');

    // Event Listeners
    if (btnBuscar) {
        btnBuscar.addEventListener('click', buscarYFiltrar);
    }

    if (buscadorInput) {
        buscadorInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                buscarYFiltrar();
            }
        });
    }

    if (filtroSelect) {
        filtroSelect.addEventListener('change', buscarYFiltrar);
    }

    if (logo) {
        logo.addEventListener('click', resetBusqueda);
    }

    // Inicializar carrusel
    inicializarCarrusel();
});

function buscarYFiltrar() {
    const termino = document.getElementById('buscadorInput').value.toLowerCase();
    const filtro = document.getElementById('filtroSelect').value;
    const categorias = document.querySelectorAll('.categoria-item');

    categorias.forEach(categoria => {
        const nombreCategoria = categoria.querySelector('h6').textContent.toLowerCase();
        const categoriaTipo = categoria.getAttribute('data-categoria');

        const coincideTexto = nombreCategoria.includes(termino);
        const coincideFiltro = filtro === 'todas' || categoriaTipo === filtro;

        if (coincideTexto && coincideFiltro) {
            categoria.style.display = 'block';
        } else {
            categoria.style.display = 'none';
        }
    });
}

function resetBusqueda() {
    document.getElementById('buscadorInput').value = '';
    document.getElementById('filtroSelect').value = 'todas';

    const categorias = document.querySelectorAll('.categoria-item');
    categorias.forEach(cat => {
        cat.style.display = 'block';
    });
}

function inicializarCarrusel() {
    const myCarousel = document.getElementById('bannerCarrusel');
    if (myCarousel) {
        const carousel = new bootstrap.Carousel(myCarousel, {
            interval: 5000,
            wrap: true,
            pause: false
        });
    }
}