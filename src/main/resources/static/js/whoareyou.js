
function toggleMenu() {
    document.body.classList.toggle('menu-open');
    document.querySelector('.content-container').classList.toggle('menu-open');
}

document.addEventListener("DOMContentLoaded", function() {
    // JSON 파일을 fetch API로 로드
    fetch('/database/Progamer.json')  // static 경로를 통해 JSON 파일에 접근
        .then(response => response.json())
        .then(data => {
            console.log('JSON data:', data);
            const progamerList = data;  // JSON 데이터를 자바스크립트로 받아옴
            const input = document.getElementById('player-input');
            const suggestions = document.getElementById('suggestions');

            // 입력값에 따라 필터링하여 suggestion에 표시
            input.addEventListener('input', function () {
                const query = input.value.toLowerCase();
                suggestions.innerHTML = '';  // 이전 내용을 비움
                if (query.length >= 3) {
                    const filtered = progamerList.filter(progamer => progamer.pid.toLowerCase().includes(query));
                    filtered.forEach(progamer => {
                        const suggestionItem = document.createElement('div');
                        suggestionItem.textContent = progamer.pid;  // pid를 표시
                        suggestions.appendChild(suggestionItem);
                    });
                }
            });
        }).catch(error => console.error('Error fetching JSON:', error));
});

document.addEventListener('DOMContentLoaded', function () {
    const input = document.getElementById('player-input');

    input.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault(); // 기본 Enter 동작을 막고
            document.getElementById('playerForm').submit(); // form 제출
        }
    });
});

/*
document.querySelectorAll('.quiz-item').forEach(item => {
    const hintsContainer = document.querySelector('.hints-container');
    const suggestions = item.querySelector('.suggestions');
    const inputField = item.querySelector('input');
    const maxAttempts = 8;
    let attempts = 0;

    inputField.addEventListener('input', function() {
        const userInput = this.value.toLowerCase();
        if (userInput === '') {
            suggestions.style.display = 'none';
        } else {
            const matchedNames = playerNames.filter(name => name.toLowerCase().includes(userInput));
            suggestions.innerHTML = matchedNames.map(name => `<div>${name}</div>`).join('');
            suggestions.style.display = 'block';
        }
    });

    inputField.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            const answer = 'Player1';//item.getAttribute('data-answer');
            const userAnswer = this.value.trim();
            attempts++;

            if (userAnswer.toLowerCase() === answer.toLowerCase()) {
                item.classList.add('correct');
                item.classList.remove('incorrect');
                item.querySelector('img').style.filter = 'none';
            } else {
                item.classList.add('incorrect');
                item.classList.remove('correct');
            }

            hintsContainer.style.display = 'flex';
            displayHints(hintsContainer, playerHints[answer], playerHints[userAnswer]);

            inputField.placeholder = `${attempts + 1} of ${maxAttempts}`;
            inputField.value = '';

            if (attempts >= maxAttempts) {
                showAnswer(hintsContainer, playerHints[answer]);
                item.querySelector('img').style.filter = 'none';
                inputField.disabled = true;
            }
        }
    });

    suggestions.addEventListener('click', function(event) {
        if (event.target.tagName === 'DIV') {
            inputField.value = event.target.textContent;
            suggestions.style.display = 'none';
        }
    });
});

function displayHints(hintsContainer, correctHints, userHints) {
    const hintRow = document.createElement('div');
    hintRow.classList.add('hint-row');

    userHints.forEach((hint, index) => {
        const hintItem = document.createElement('div');
        hintItem.classList.add('hint-item', 'fade-in');
        hintItem.style.display = 'flex';

        const cover = document.createElement('div');
        cover.classList.add('cover');
        cover.style.backgroundColor = hint === correctHints[index] ? 'green' : '#808080';

        const img = document.createElement('img');
        img.src = playerImages[hint] || '../image/content/default.jpg'; // 적절한 이미지 경로를 추가하세요
        img.alt = hint;
        cover.appendChild(img);

        const span = document.createElement('span');
        span.textContent = hintNames[index];

        hintItem.appendChild(cover);
        hintItem.appendChild(span);

        hintRow.appendChild(hintItem);
    });

    hintsContainer.insertBefore(hintRow, hintsContainer.firstChild);
}

function showAnswer(hintsContainer, correctHints) {
    const answerRow = document.createElement('div');
    answerRow.classList.add('hint-row');

    correctHints.forEach((hint, index) => {
        const hintItem = document.createElement('div');
        hintItem.classList.add('hint-item', 'fade-in');
        hintItem.style.display = 'flex';

        const cover = document.createElement('div');
        cover.classList.add('cover');
        cover.style.backgroundColor = 'green';

        const img = document.createElement('img');
        img.src = playerImages[hint] || '../image/content/default.jpg'; // 적절한 이미지 경로를 추가하세요
        img.alt = hint;
        cover.appendChild(img);

        const span = document.createElement('span');
        span.textContent = hintNames[index];

        hintItem.appendChild(cover);
        hintItem.appendChild(span);

        answerRow.appendChild(hintItem);
    });

    hintsContainer.insertBefore(answerRow, hintsContainer.firstChild);
}*/
