const PASSWORD = "utente!03";
const SPORT = "cricket";
const SIGNUP_META = document.getElementById("isSignup");
const IS_SIGNUP = (SIGNUP_META && SIGNUP_META.content === "true");

function isValidPw (password) {
    return password === PASSWORD //Not safe for sure
}

function isOldEnough(date) {
    const currentDate = new Date();
    const minDate = new Date(currentDate.getFullYear() - 18, currentDate.getMonth(), currentDate.getDate());
    const birthday = new Date(date);
    return birthday <= minDate;
}

function isSamePassword(password, confirmed) {
    return password === confirmed
}

function isValidSport(sport) {
        return sport === SPORT;
}

function addConfirmPwHandler() {
    const password = document.getElementById("pw");
    const confirmed = document.getElementById("confirm-pw");

    confirmed.addEventListener("keyup", () => {
        if(isSamePassword(password.value, confirmed.value)) {
            confirmed.classList.add("is-valid");
            confirmed.classList.remove("is-invalid");
            confirmed.setCustomValidity("");
        } else {
            confirmed.classList.add("is-invalid");
            confirmed.classList.remove("is-valid");
            confirmed.setCustomValidity("Le password non coincidono");
        }
    }, false);
}

function addSportHandler() {
    const sport = document.getElementById("sport");
    const team = document.getElementById("team");

    sport.addEventListener("change", () => {
        sport.setCustomValidity("");

        if(isValidSport(sport.value)) {
            team.disabled = false;
            sport.classList.add("is-valid");
            sport.classList.remove("is-invalid");
        } else {
            team.value = "";
            team.disabled = true
            sport.classList.add("is-invalid");
            sport.classList.remove("is-valid");
            sport.setCustomValidity("Sport non disponibile");
        }
    }, false);
}

function addBDayHandler() {
    const birthday = document.getElementById("birthday");

    birthday.addEventListener("blur", () => {
        if (birthday.value !== "" && !isOldEnough(birthday.value)) {
            birthday.classList.add("is-invalid");
            birthday.classList.remove("is-valid");
            birthday.setCustomValidity("Devi essere maggiorenne");
        } else {
            birthday.classList.add("is-valid");
            birthday.classList.remove("is-invalid");
            birthday.setCustomValidity("");
        }
    }, false);
}

function addPwHandler() {
    const password = document.getElementById("pw");

    password.addEventListener("blur", () => {
        if (!isValidPw(password.value)) {
            password.classList.add("is-invalid");
            password.classList.remove("is-valid");
            password.setCustomValidity("La password deve essere " + PASSWORD);
        } else {
            password.classList.add("is-valid");
            password.classList.remove("is-invalid");
            password.setCustomValidity("");
        }
    }, false);

}

function addResetHandler() {
    const form = document.getElementById("my-form");

    form.addEventListener("reset", () => {
        //it only works with timeout (on my browser)
        setTimeout(() => {
            form.classList.remove("was-validated");

            form.querySelectorAll(".is-valid, .is-invalid").forEach(input => {
                input.classList.remove("is-valid", "is-invalid");
                input.setCustomValidity("");
            });

            if(IS_SIGNUP) {
                const team = document.getElementById("team");
                team.disabled = true;
                team.value = "";
            }
        }, 0);
    });
}


function validateForm() {
    'use strict'

    const form = document.getElementById("my-form");

    form.addEventListener("submit", event => {

        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }

        form.classList.add("was-validated");

    }, false);
}

document.addEventListener("DOMContentLoaded", () => {
    addConfirmPwHandler();
    addResetHandler();
    validateForm();
    if(IS_SIGNUP) {
        addPwHandler();
        addSportHandler();
        addBDayHandler();
    }
}, false);

//TODO:

// function togglePwVisibility() {
//     const icons = document.getElementsByClassName("toggle-pw-icon");
//
//     for (let i = 0; i < icons.length; i++) {
//         icons[i].addEventListener("click", function() {
//             const input = this.previousElementSibling;
//             input.type = input.type === "password" ? "text" : "password";
//             this.classList.toggle("bi-eye");
//             this.classList.toggle("bi-eye-slash");
//         });
//     }
// }


