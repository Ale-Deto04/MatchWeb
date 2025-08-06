const TIME_STORAGE = "time";
const NEWS_STORAGE = "news";
const INTERVAL = 30000;

function getTime() {
    const currentTime = new Date();
    const hour = currentTime.getHours();
    let minutes = currentTime.getMinutes();
    if(minutes < 10) {
        minutes = "0" + minutes;
    }
    return hour + ":" + minutes;
}

function createToast(text, time) {
    const toast = document.getElementById('news-toast');
    const toastInstance = bootstrap.Toast.getOrCreateInstance(toast);
    document.getElementById("news-date").innerHTML = time;
    document.getElementById("news-text").innerHTML = text;
    toastInstance.show();
}

function updateOffCanvas(text, time) {
    const list = document.getElementById("news-list");

    if(list) {
        const li = document.createElement("li");
        li.className = "list-group-item";

        const wrapper = document.createElement("div");
        wrapper.innerHTML = [
            `<h5>${time}</h5>`,
            `<p>${text}</p>`
        ].join('');

        li.appendChild(wrapper);
        list.insertBefore(li, list.firstChild);
    }
}

function loadNews() {
    const savedNews = sessionStorage.getItem(NEWS_STORAGE);
    return savedNews ? JSON.parse(savedNews) : [];
}

function saveNews(newsArray) {
    sessionStorage.setItem(NEWS_STORAGE, JSON.stringify(newsArray));
}

function addNews(text, time) {
    const MAX_NEWS = 6;
    let newsArray = loadNews();
    newsArray.unshift({ text: text, time: time });
    if (newsArray.length > MAX_NEWS) {
        newsArray.pop();
    }
    //saves only if there are less than MAX_NEWS news
    saveNews(newsArray);
    updateOffCanvas(text, time);
}

function getNews(){
    const url = document.getElementById('news-url').dataset.url;
    fetch(url)
        .then(response => {
            if (response.status !== 200) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(text => {
            createToast(text, getTime());
            addNews(text, getTime());
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation: ', error);
        });
}

function loadNewsOnStart() {
    const newsArray = loadNews();
    newsArray.forEach(function(news) {
        updateOffCanvas(news.text, news.time);
    });
}

function startNews() {
    let elapsedTime = parseInt(sessionStorage.getItem(TIME_STORAGE), 10);
    if (isNaN(elapsedTime)) {
        elapsedTime = 0;
    }

    let delay = INTERVAL - elapsedTime;

    function showNews() {
        getNews();
        sessionStorage.setItem(TIME_STORAGE, '0');
    }

    function updateElapsed() {
        elapsedTime = (elapsedTime + 1000) % INTERVAL;
        sessionStorage.setItem(TIME_STORAGE, elapsedTime.toString());
    }

    //Synchronization
    setTimeout(function() {
        showNews();
        setInterval(function() {
            showNews();
        }, INTERVAL);
    }, delay);

    setInterval(updateElapsed, 1000);
}


document.addEventListener("DOMContentLoaded", () => {
    loadNewsOnStart();
    startNews();
}, false);