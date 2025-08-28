document.addEventListener('DOMContentLoaded', () => {
    const imageModal = new bootstrap.Modal(document.getElementById('imageModal'));
    const modalImage = document.getElementById('modalImage');
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');

    let currentImages = []; // Array para guardar las URLs de las imágenes del servicio actual
    let currentImageIndex = 0; // Índice de la imagen que se está mostrando

    // Escucha clics en todas las miniaturas de imágenes en la página
    document.body.addEventListener('click', (event) => {
        const thumbnail = event.target.closest('.thumbnail');
        if (thumbnail) {

            event.preventDefault();

            // Encuentra el contenedor de la galería para obtener la lista de imágenes
            const galleryContainer = thumbnail.closest('.service-gallery');
            const imagesString = galleryContainer.dataset.images;
            currentImages = imagesString ? imagesString.split(',') : [];

            // Obtiene la URL de la imagen en la que se hizo clic
            const clickedImageUrl = thumbnail.getAttribute('src');
            currentImageIndex = currentImages.indexOf(clickedImageUrl);

            // Muestra la imagen en el modal
            if (currentImages.length > 0) {
                modalImage.src = currentImages[currentImageIndex];
                imageModal.show();
                updateNavigationButtons();
            }
        }
    });

    // Actualiza el estado de los botones de navegación (prev/next)
    const updateNavigationButtons = () => {
        if (currentImages.length <= 1) {
            prevBtn.style.display = 'none';
            nextBtn.style.display = 'none';
        } else {
            prevBtn.style.display = 'flex';
            nextBtn.style.display = 'flex';
            prevBtn.disabled = currentImageIndex === 0;
            nextBtn.disabled = currentImageIndex === currentImages.length - 1;
        }
    };

    // Maneja el clic en el botón de "anterior"
    prevBtn.addEventListener('click', () => {
        if (currentImageIndex > 0) {
            currentImageIndex--;
            modalImage.src = currentImages[currentImageIndex];
            updateNavigationButtons();
        }
    });

    // Maneja el clic en el botón de "siguiente"
    nextBtn.addEventListener('click', () => {
        if (currentImageIndex < currentImages.length - 1) {
            currentImageIndex++;
            modalImage.src = currentImages[currentImageIndex];
            updateNavigationButtons();
        }
    });
});
