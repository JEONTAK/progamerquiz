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
            showHint(data.guessedList, data.isCorrect, data.answer, data.imagePath);
        })
        .catch(error => console.error("Error starting quiz:", error));
}

// 페이지가 로드될 때 처음 방문한 경우에만 가이드 보여주기
window.onload = function() {
    // localStorage에 'guideShown' 키가 없는 경우에만 가이드 보여줌
    if (!localStorage.getItem('guideShown')) {
        showGuide();
    }
};


document.addEventListener("DOMContentLoaded", function() {
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
                if (query.length >= 3) {
                    const filtered = progamerList.filter(progamer => progamer.pid.toLowerCase().includes(query));
                    filtered.forEach(progamer => {
                        const suggestionItem = document.createElement('div');
                        suggestionItem.textContent = progamer.pid;  // pid를 표시
                        suggestionItem.classList.add('suggestion-item');  // 스타일링을 위한 클래스 추가

                        // suggestionItem 클릭 시 해당 값을 input에 넣음
                        suggestionItem.addEventListener('click', function() {
                            input.value = progamer.pid;
                            suggestions.innerHTML = '';  // 클릭 후 suggestion 목록을 비움
                        });

                        suggestions.appendChild(suggestionItem);
                    });
                }
            });
        }).catch(error => console.error('Error fetching JSON:', error));
});

//Progamer 입력 제출
document.getElementById('player-input').addEventListener('keydown', function(event) {
    const userInput = document.getElementById('player-input').value;
    const errorMessage = document.getElementById('error-message-player');
    const quizData = JSON.parse(localStorage.getItem('quizData'));

    if (event.key === 'Enter') {
        event.preventDefault(); // 기본 Enter 동작을 막고
        fetch('/whoareyou/submitAnswer', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                input: userInput,
                attempts: quizData.attempts,
                answer: quizData.answer,
                guessedList: quizData.guessedList}), // 사용자 입력값 전달
        })
            .then(response => response.json())
            .then(data => {
                quizData.attempts = data.attempts;
                if (data.progamer) {
                    quizData.guessedList.push(data.progamer);
                }
                localStorage.setItem('quizData', JSON.stringify(quizData));
                if (data.isSubmitted === "false") {
                    showErrorMessage(errorMessage, 2000);
                } else {
                    showHint(quizData.guessedList, data.isCorrect, quizData.answer, quizData.imagePath);
                }
            })
            .catch(error => console.error("Error:", error));
    }
});


// 메시지 표시 함수
function showErrorMessage(element, duration) {
    element.style.display = "block";
    setTimeout(() => {
        element.style.display = "none";
    }, duration);
}

function showHint(guessedList, isCorrect, answer, imagePath) {
    const hintContainer = document.getElementById("hintContainer");
    const playerInput = document.getElementById("player-input");
    const playerImage = document.getElementById("player-image");
    const quizContainer = document.getElementById("quiz-container");
    const totalItems = 8; // 고정된 총 항목의 수
    const guessedListSize = guessedList.length; // guessedList의 사이즈
    playerInput.setAttribute("placeholder", `${guessedListSize} of ${totalItems}`);
    const failedMessage = document.getElementById("failed-message");
    const goMainButton = document.getElementById("go-to-main");
    hintContainer.innerHTML = "";
    const hintName = ['League', 'Team', 'Position', 'Birth', 'League Wins', 'Intl Wins'];
    // 힌트 행 생성
    const hintRow = document.createElement("div");
    hintRow.classList.add("hint-row");
    hintName.forEach(hint => {
        const hintItem = document.createElement("div");
        hintItem.classList.add("hint-item");
        hintItem.style.display = "block";   // 반드시 보이도록 설정
        hintItem.style.opacity = "1";      // 투명도를 명확히 설정
        const hintLabel = document.createElement("span");
        hintLabel.classList.add("hint-name");
        hintLabel.textContent = hint; // 각 힌트의 라벨 설정
        hintLabel.style.fontWeight = "bold";  // 강조 효과
        hintItem.appendChild(hintLabel); // 컨테이너에 라벨을 추가
        hintRow.appendChild(hintItem);
        hintRow.classList.add("fade-in");  // hintRow에 fade-in 적용
        hintRow.childNodes.forEach(item => {
            item.style.display = "block";   // 모든 자식 요소가 보이게 설정
            item.style.opacity = "1";      // 모든 자식 요소의 투명도 설정
        });
    });
    hintContainer.appendChild(hintRow);
    playerImage.src = imagePath;
    const answerPidElement = document.getElementById('answer-pid');
    // TryStatus가 1인 경우, 즉 정답을 맞춘 경우
    if (isCorrect === "true") {
        // 버튼과 정답 PID 표시
        goMainButton.style.display = 'block'; // 메인 페이지로 이동하는 버튼 보이게 함
        answerPidElement.textContent = answer.pid; // 정답의 PID 표시
        answerPidElement.style.display = 'block'; // 정답 PID 표시

        // 이미지 블러 해제 및 배경색 변경 (정답일 때 초록색으로 변경)
        playerImage.style.filter = "none";
        quizContainer.style.backgroundColor = "green";

        // 텍스트 필드 수정 불가능하게 설정
        playerInput.disabled = true;

        setTimeout(function() {
            quizContainer.style.backgroundColor = "#091428";
        }, 1500);
    } else if (isCorrect === "false") {
        quizContainer.style.backgroundColor = "red";
        setTimeout(function() {
            quizContainer.style.backgroundColor = "#091428";
        }, 1500);

    } else if(isCorrect === "start"){

    } else{
        goMainButton.style.display = 'block';

        answerPidElement.textContent = answer.pid; // pid 출력
        answerPidElement.style.display = 'block';
        showAnswer(answer);

        // 이미지 블러 해제
        playerImage.style.filter = "none";

        // 이미지 배경색을 초록색으로 변경
        quizContainer.style.backgroundColor = "red";

        // 텍스트 필드 수정 불가능하게 설정
        playerInput.disabled = true;

        // 3초 후에 메시지를 사라지게 함 (선택 사항)
        setTimeout(function () {
            failedMessage.style.display = 'none';
        }, 3000);
    }

    // guessedList에서 데이터를 가져와 동적으로 추가
    guessedList.slice().reverse().forEach((progamer) => {
        // 힌트 행 생성
        const hintRow = document.createElement("div");
        hintRow.classList.add("hint-row");
        // 각 힌트 정보를 아이템으로 추가
        const hintData = [
            {
                label: 'League',
                value: progamer.recentLeague,
                answerValue: answer.recentLeague,  // answer 값 추가
                icon: `/images/league/${progamer.recentLeague}.webp`,
                fallbackIcon: '/images/none.png'
            },
            {
                label: 'Team',
                value: progamer.recentTeam,
                answerValue: answer.recentTeam,  // answer 값 추가
                icon: `/images/team/${progamer.teamId}.webp`,
                fallbackIcon: '/images/none.png'
            },
            {
                label: 'Position',
                value: progamer.position,
                answerValue: answer.position,  // answer 값 추가
                icon: `/images/position/${progamer.position}.png`,
                fallbackIcon: '/images/none.png'
            },
            {
                label: 'Birth',
                value: progamer.birth,
                answerValue: answer.birth,  // answer 값 추가
                icon: `/images/number/number_${progamer.birth.toString().slice(-2)}.png`,
                fallbackIcon: '/images/none.png',
                getArrow: function () {
                    if (this.value > this.answerValue) {
                        return '↓'; //위쪽 화살표
                    } else if (this.value < this.answerValue) {
                        return '↑'; //아래쪽 화살표
                    }
                    return ''; // 값이 같을 경우 화살표 없음
                }
            },
            {
                label: 'Leag Wins',
                value: progamer.league_win != null ? progamer.league_win : 0,
                answerValue: answer.league_win != null ? answer.league_win : 0,  // answer 값 추가
                icon: `/images/number/number_${progamer.league_win != null ? progamer.league_win : 0}.png`,
                fallbackIcon: '/images/none.png',
                getArrow: function () {
                    if (this.value > this.answerValue) {
                        return '↓'; //아래쪽 화살표
                    } else if (this.value < this.answerValue) {
                        return '↑'; //위쪽 화살표
                    }
                    return ''; // 값이 같을 경우 화살표 없음
                }
            },
            {
                label: 'Intl Wins',
                value: progamer.intl_win != null ? progamer.intl_win : 0,
                answerValue: answer.intl_win != null ? answer.intl_win : 0,  // answer 값 추가
                icon: `/images/number/number_${progamer.intl_win != null ? progamer.intl_win : 0}.png`,
                fallbackIcon: '/images/none.png',
                getArrow: function () {
                    if (this.value > this.answerValue) {
                        return '↓'; //아래쪽 화살표
                    } else if (this.value < this.answerValue) {
                        return '↑'; //위쪽 화살표
                    }
                    return ''; // 값이 같을 경우 화살표 없음
                }
            }
        ];

        // 각 힌트 정보를 아이템으로 구성
        hintData.forEach(hint => {
            const hintItem = document.createElement("div");
            hintItem.classList.add("hint-item");
            hintItem.style.display = "block";   // 반드시 보이도록 설정
            hintItem.style.opacity = "1";      // 투명도를 명확히 설정
            // 이미지 추가
            const cover = document.createElement("div");
            cover.classList.add("cover");

            const img = document.createElement("img");

            img.src = hint.icon;  // 해당 힌트의 아이콘 경로
            /*img.onerror = function () {
                this.src = hint.fallbackIcon;
            };*/
            img.alt = hint.label;
            removeBlur(img);

            cover.appendChild(img);
            // 힌트 값 추가
            const span = document.createElement("span");
            span.classList.add("hint-value");
            // hint.getArrow가 존재할 경우에만 화살표를 추가
            span.textContent = `${hint.value}${hint.getArrow ? ` ${hint.getArrow()}` : ''}`;

            // answer의 값과 동일한 경우 배경색을 초록색으로 설정
            if (hint.value === hint.answerValue) {
                cover.style.backgroundColor = "green";  // 이미지 배경색을 초록색으로 변경
            }

            hintItem.appendChild(cover);
            hintItem.appendChild(span);
            hintRow.appendChild(hintItem);
            hintRow.classList.add("fade-in");  // hintRow에 fade-in 적용
            hintRow.childNodes.forEach(item => {
                item.style.display = "block";   // 모든 자식 요소가 보이게 설정
                item.style.opacity = "1";      // 모든 자식 요소의 투명도 설정
            });// hintItem을 hintRow에 추가
        });
        hintContainer.appendChild(hintRow);
    });
}

function showAnswer(answer){
    // 힌트 행 생성
    const hintContainer = document.getElementById("hintContainer");

    const hintRow = document.createElement("div");
    hintRow.classList.add("hint-row");

    const answerData = [
        {
            label: 'League',
            value: answer.recentLeague,  // answer 값 추가
            icon: `/images/league/${answer.recentLeague}.webp`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Team',
            value: answer.recentTeam,  // answer 값 추가
            icon: `/images/team/${answer.teamId}.webp`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Position',
            value: answer.position,  // answer 값 추가
            icon: `/images/position/${answer.position}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Birth',
            value: answer.birth,  // answer 값 추가
            icon: `/images/number/number_${answer.birth.toString().slice(-2)}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Leag Wins',
            value: answer.league_win != null ? answer.league_win : 0,  // answer 값 추가
            icon: `/images/number/number_${answer.league_win != null ? answer.league_win : 0}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Intl Wins',
            value: answer.intl_win != null ? answer.intl_win : 0,  // answer 값 추가
            icon: `/images/number/number_${answer.intl_win != null ? answer.intl_win : 0}.png`,
            fallbackIcon: '/images/none.png'
        }
    ];

    answerData.forEach(hint => {
        const hintItem = document.createElement("div");
        hintItem.classList.add("hint-item");
        hintItem.style.display = "block";   // 반드시 보이도록 설정
        hintItem.style.opacity = "1";      // 투명도를 명확히 설정
        // 이미지 추가
        const cover = document.createElement("div");
        cover.classList.add("cover");

        const img = document.createElement("img");
        img.src = hint.icon;  // 해당 힌트의 아이콘 경로
        img.onerror = function () {
            img.src = hint.fallbackIcon;
        };
        img.alt = hint.label;
        removeBlur(img);
        cover.appendChild(img);// 힌트 값 추가
        const span = document.createElement("span");
        span.classList.add("hint-value");
        span.textContent = hint.value;
        cover.style.backgroundColor = "green";  // 이미지 배경색을 초록색으로 변경

        hintItem.appendChild(cover);
        hintItem.appendChild(span);
        hintRow.appendChild(hintItem);  // hintItem을 hintRow에 추가
    });
    hintContainer.appendChild(hintRow);
}

function removeBlur(imageElement) {
    imageElement.classList.add('no-blur');
}

function goToMainPage() {
    // localStorage에서 guideShown 값을 삭제 (초기화)
    localStorage.removeItem('guideShown');
    window.location.href = '/'; // 메인 페이지 URL로 이동 ("/"는 메인 페이지로 이동하는 경로)
}

