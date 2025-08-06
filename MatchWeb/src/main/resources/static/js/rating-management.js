const MAX_CHAR = 511

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("rating-form");
    const textArea = document.getElementById("text");

    form.addEventListener("submit", (event) => {
        textArea.setCustomValidity("");
        if(textArea.value.length > MAX_CHAR) {
            textArea.setCustomValidity("Testo troppo lungo");
        }

        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }

        form.classList.add("was-validated");
    }, false);
})