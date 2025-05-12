// 가이드 오버레이 보이기
function showGuide() {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'flex';
}

// 가이드 오버레이 닫기
function closeGuide() {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'none';
    // 사용자가 가이드를 닫았으므로, localStorage에 방문 기록을 저장
    localStorage.setItem('guideShown', 'true');
    fetch('/whoareyou/start', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json(); // JSON 파싱
        })
        .then(data => {
            // 데이터 저장 (예: Local Storage 또는 전역 변수)
            localStorage.setItem("quizData", JSON.stringify(data));

            // player-image src 설정
            const playerImage = document.getElementById("player-image");
            playerImage.src = data.answer && data.answer.id ? `/images/player/${data.answer.id}.webp` : "/images/none.png";
        })
        .catch(error => console.error("Error starting quiz:", error));
}

// 페이지가 로드될 때 처음 방문한 경우에만 가이드 보여주기
window.onload = function () {
    // localStorage에 'guideShown' 키가 없는 경우에만 가이드 보여줌
    if (!localStorage.getItem('guideShown')) {
        showGuide();
    }
};

document.addEventListener("DOMContentLoaded", function () {
    // JSON 파일을 fetch API로 로드
    fetch('/database/Progamer.json')  // static 경로를 통해 JSON 파일에 접근
        .then(response => response.json())
        .then(data => {
            const progamerList = data;  // JSON 데이터를 자바스크립트로 받아옴
            const input = document.getElementById('player-input');
            const suggestions = document.getElementById('suggestions');

            // 입력값에 따라 필터링하여 suggestion에 표시
            input.addEventListener('input', function () {
                const query = input.value.toLowerCase();
                suggestions.innerHTML = '';  // 이전 내용을 비움
                if (query.length >= 2) {
                    const filtered = progamerList.filter(progamer => progamer.progamerTag.toLowerCase().includes(query));
                    filtered.forEach(progamer => {
                        const suggestionItem = document.createElement('div');
                        suggestionItem.textContent = progamer.progamerTag;  // pid를 표시
                        suggestionItem.classList.add('suggestion-item');  // 스타일링을 위한 클래스 추가

                        // suggestionItem 클릭 시 해당 값을 input에 넣음
                        suggestionItem.addEventListener('click', function () {
                            input.value = progamer.progamerTag;
                            suggestions.innerHTML = '';  // 클릭 후 suggestion 목록을 비움
                        });

                        suggestions.appendChild(suggestionItem);
                    });
                }
            });
        }).catch(error => console.error('Error fetching JSON:', error));
});

//Progamer 입력 제출
document.getElementById('player-input').addEventListener('keydown', function (event) {
    if (event.key !== 'Enter') return;
    event.preventDefault();

    const userInput = document.getElementById('player-input').value.trim();
    const quizData = JSON.parse(localStorage.getItem('quizData'));
    const errorMessage = document.getElementById('error-message-player');

    if (!userInput) {
        errorMessage.textContent = "Please enter a player name.";
        errorMessage.style.display = "block";
        return;
    }

    fetch('/whoareyou/submitAnswer', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            input: userInput,
            attempts: quizData.attempts,
            answer: quizData.answer,
            guessedList: quizData.guessedList
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // quizData 업데이트
            quizData.attempts = data.attempts;
            quizData.guessedList = data.guessedList;
            localStorage.setItem('quizData', JSON.stringify(quizData));

            // 최신 힌트 표시
            showHint(data.hintResults, data.correct, data.answer, data.guessedList, data.attempts);

            // 최대 시도 횟수 초과 시 처리
            if (data.attempts >= 8 && !data.correct) {
                document.getElementById('player-input').disabled = true;
                document.getElementById('go-to-main').style.display = 'block';
                errorMessage.textContent = `Game over! The answer was ${data.answer.progamerTag}.`;
                errorMessage.style.display = 'block';
            }

            // 입력란 초기화
            document.getElementById('player-input').value = '';
        })
        .catch(error => {
            console.error("Error:", error);
            errorMessage.textContent = "An error occurred. Please try again.";
            errorMessage.style.display = "block";
        });
});

function showHint(hintResults, isCorrect, answer, guessedList, attempts) {
    const hintContainer = document.getElementById("hintContainer");
    const playerInput = document.getElementById("player-input");
    const playerImage = document.getElementById("player-image");
    const quizContainer = document.getElementById("quiz-container");
    const errorMessage = document.getElementById("error-message-player");
    const goMainButton = document.getElementById("go-to-main");
    const totalItems = 8;

    playerInput.setAttribute("placeholder", `${attempts} of ${totalItems}`);

    const hintRow = document.createElement("div");
    hintRow.classList.add("hint-row");

    // 힌트 데이터 구성
    const hintData = hintResults.map(hint => {
        const label = hint.hintName.charAt(0).toUpperCase() + hint.hintName.slice(1);
        if (hint.hintName === "team") {
            // inputValue를 ,로 분리
            const [teamName, teamImageId] = hint.inputValue.split(",");
            const [answerTeamName, answerTeamImageId] = hint.answerValue.split(",");
            return {
                label,
                value: teamName || "Unknown", // 팀 이름
                answerValue: answerTeamName || "Unknown",
                match: hint.match,
                icon: teamImageId ? `/images/team/${teamImageId}.webp` : "/images/none.png",
                fallbackIcon: "/images/none.png",
                getArrow: null
            };
        }
        return {
            label,
            value: hint.inputValue,
            answerValue: hint.answerValue,
            match: hint.match,
            icon: getHintIcon(hint.hintName, hint.inputValue),
            fallbackIcon: "/images/none.png",
            getArrow: ["birth", "leagueWin", "intlWin"].includes(hint.hintName) ? () => {
                if (parseInt(hint.inputValue) > parseInt(hint.answerValue)) return "↓";
                if (parseInt(hint.inputValue) < parseInt(hint.answerValue)) return "↑";
                return "";
            } : null
        };
    });

    // 힌트 아이템 생성
    hintData.forEach(hint => {
        const hintItem = document.createElement("div");
        hintItem.classList.add("hint-item");
        hintItem.style.display = "block";
        hintItem.style.opacity = "1";

        const cover = document.createElement("div");
        cover.classList.add("cover");
        const img = document.createElement("img");
        img.src = hint.icon;
        img.alt = hint.label;
        img.onerror = () => { img.src = hint.fallbackIcon; };
        cover.appendChild(img);

        const span = document.createElement("span");
        span.classList.add("hint-value");
        span.textContent = `${hint.value}${hint.getArrow ? ` ${hint.getArrow()}` : ""}`;

        cover.style.backgroundColor = hint.match ? "#28a745" : "#dc3545";

        hintItem.appendChild(cover);
        hintItem.appendChild(span);
        hintRow.appendChild(hintItem);
    });

    hintContainer.prepend(hintRow);
    requestAnimationFrame(() => {
        hintRow.style.opacity = "1";
    });

    quizContainer.style.transition = "background-color 1s ease";
    if (isCorrect) {
        playerImage.classList.add("correct");
        quizContainer.classList.add("correct");
        playerInput.disabled = true;
        goMainButton.style.display = "block";
        errorMessage.style.display = "none";
    } else {
        quizContainer.classList.add("error");
        errorMessage.textContent = "Wrong answer! Try again.";
        errorMessage.style.display = "block";
        setTimeout(() => {
            quizContainer.classList.remove("error");
            quizContainer.style.backgroundColor = "#091428";
        }, 1000);
    }
}

// 힌트 아이콘 경로 생성
function getHintIcon(hintName, value) {
    if (!value || value === "Unknown") return "/images/none.png";
    switch (hintName) {
        case "league": return `/images/league/${value}.webp`;
        case "position": return `/images/position/${value}.png`;
        case "birth": return `/images/number/number_${value.slice(-2)}.png`;
        case "leagueWin": return `/images/number/number_${value}.png`;
        case "intlWin": return `/images/number/number_${value}.png`;
        default: return "/images/none.png";
    }
}