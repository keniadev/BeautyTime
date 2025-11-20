document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".service-card").forEach(card => {

        const desc = card.querySelector(".descripcion");
        const leerMas = card.querySelector(".leer-mas");

        if (!desc || !leerMas) return;

        const lineHeight = parseFloat(window.getComputedStyle(desc).lineHeight);
        const fullHeight = desc.scrollHeight;
        const threeLines = lineHeight * 3;

        // Si el texto es corto → ocultar botón y quitar truncamiento
        if (fullHeight <= threeLines + 2) {
            leerMas.style.display = "none";
            desc.style.maxHeight = "none";
            desc.style.webkitLineClamp = "unset";
            desc.style.overflow = "visible";
            desc.style.display = "block";
            return;
        }

        // Texto largo → mostrar truncado
        desc.style.maxHeight = threeLines + "px";
        desc.style.display = "-webkit-box";
        desc.style.webkitLineClamp = "3";
        desc.style.overflow = "hidden";

        // Click
        leerMas.addEventListener("click", () => {
            const expanded = desc.classList.contains("expandido");

            if (expanded) {
                // Encoger
                desc.classList.remove("expandido");
                desc.style.maxHeight = threeLines + "px";
                leerMas.textContent = "Leer más";
            } else {
                // Expandir
                desc.classList.add("expandido");
                desc.style.maxHeight = fullHeight + "px";
                leerMas.textContent = "Leer menos";

                // Quitar límite después de la animación
                setTimeout(() => {
                    if (desc.classList.contains("expandido")) {
                        desc.style.maxHeight = "none";
                    }
                }, 350);
            }
        });
    });
});
