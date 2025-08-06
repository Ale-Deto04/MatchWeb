const csrf_token = document.getElementById("csrf_token").getAttribute("content");
const csrf_header = document.getElementById("csrf_header").getAttribute("content");

function displayScore(counter) {
    const url = document.getElementById("dashboard-url").dataset.url;
    const button = document.getElementById("send-button");
    const results = document.createElement("div");
    results.classList.add("container", "text-center", "py-3");

    results.innerHTML = [
        `<h3>Hai indovinato <strong>${counter} pronostici</strong></h3>`,
        `<a href="${url}" class="btn btn-primary">Torna alla dashboard</a>`
    ].join(``);
    button.replaceWith(results);
}

function disableSelectors() {
    const selectors = document.getElementsByClassName("form-select");

    for (let i = 0; i < selectors.length; i++) {
        selectors[i].disable = true;
    }
}

function displayResults(guesses, results) {

    const tableHead = document.getElementById("table-head-row");
    const newHeader = document.createElement("th");

    newHeader.setAttribute("scope", "col");
    newHeader.textContent = "Risultati";
    tableHead.appendChild(newHeader);

    let counter = 0;

    const tableRows = document.getElementsByClassName("table-row");
    for (let i = 0; i < tableRows.length; i++) {

        const newCol = document.createElement("td");
        if (guesses[i] === results[i]) {
            newCol.textContent = "RES: [" + results[i] + "] CORRETTO";
            newCol.classList.add("text-success");
            counter++;
        } else {
            newCol.textContent = "RES: [" + results[i] + "] SBAGLIATO";
            newCol.classList.add("text-danger");
        }
        tableRows[i].appendChild(newCol);
    }

    disableSelectors();
    displayScore(counter);
}

function sendGuess() {
    const url = document.getElementById("result_url").dataset.url;
    const guesses = document.getElementsByClassName("user_guess");

    const dati = [];
    for (let i = 0; i < guesses.length; i++) {
        dati.push(parseInt(guesses[i].value));
    }

    fetch(url, {
        method: "post",
        headers: {
            "Content-Type": "application/json",
            [csrf_header]: csrf_token
        },
        credentials: "same-origin",
        body: JSON.stringify(dati)
    }).then(response => {
        if (response.status !== 200) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    }).then(results => {
        displayResults(dati, results);
        showAlert("Congratulazioni! Schedina inviata con successo", "success");
    }).catch(error => {
        showAlert("[Errore] Ops, qualcosa è andato storto", "danger");
        console.error('There was a problem with the fetch operation: ', error);
    });
}