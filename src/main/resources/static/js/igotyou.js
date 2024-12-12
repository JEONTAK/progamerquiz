function goToMainPage() {
    // localStorage에서 guideShown 값을 삭제 (초기화)
    localStorage.removeItem('guideShown');
    window.location.href = '/'; // 메인 페이지 URL로 이동 ("/"는 메인 페이지로 이동하는 경로)
}

function removeBlur(imageElement) {
    imageElement.classList.add('no-blur');
    imageElement.style.maxWidth = "120px";
    imageElement.style.maxLength = "auto";
}

// 가이드 오버레이 보이기
function showGuide() {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'flex';
}

// 가이드 오버레이 닫기
function closeGuide(totalC) {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'none';

    // 서버로 totalCount 값을 전송
    fetch('/igotyou/select', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ totalCount: totalC }),
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                console.error("Error initializing quiz:", data.error);
            } else {// 상태를 로컬로 저장
                localStorage.setItem('quizData', JSON.stringify({
                    quizList: data.quizList,
                    correctCount: data.correctCount,
                    totalCount: data.totalCount,
                    currentIndex: 0, // 초기 상태
                }));
                showHint(data.quizList[0], 0, data.correctCount, data.totalCount); // 첫 번째 퀴즈 표시
            }
        })
        .catch(error => {
            console.error('Error setting up quiz:', error);
        });

    // 방문 기록 저장
    localStorage.setItem('guideShown', 'true');
}

// 페이지 로드 시 가이드 보여주기
window.onload = function () {
    if (!localStorage.getItem('guideShown')) {
        showGuide();
    } else {
        const savedQuizData = localStorage.getItem('quizData');
        if (savedQuizData) {
            const { quizList, currentIndex , correctCount, totalCount} = JSON.parse(savedQuizData);
            showHint(quizList[currentIndex], currentIndex, correctCount, totalCount);
        }
    }
};


//프로게이머 제안 기능
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

// quizList를 받아서 힌트(팀 정보)를 보여주는 함수
function showHint(currentPlayer, currentIndex, correctCount, totalCount) {
    playerInput.disabled = false;
    const questionNumberElement = document.getElementById('question-number');
    questionNumberElement.textContent = currentIndex + 1 + `(${currentPlayer.position})`;  // 문제 번호는 인덱스에 1을 더한 값
    document.getElementById('total-count').textContent = totalCount;
    // 맞춘 개수 / 전체 개수를 업데이트
    document.getElementById('correct-count').textContent = correctCount;
    const hintContainer = document.getElementById('hintContainer');
    hintContainer.innerHTML = ''; // 이전 힌트들을 초기화

    if (currentPlayer) {
        // 새로운 행을 담을 변수 선언
        let rowDiv = document.createElement('div');
        rowDiv.classList.add('hint-row');  // 행을 만들기 위해 클래스를 추가
        let itemsInRow = 0;  // 현재 행에 추가된 항목 수

        currentPlayer.teamNames.forEach((teamName, i) => {
            // 팀 항목을 감쌀 div 생성
            const cover = document.createElement('div');
            cover.classList.add('cover');  // 스타일 적용을 위한 클래스

            // 팀 이미지 추가
            const teamImage = document.createElement('img');
            teamImage.src = `/images/team/${currentPlayer.teamImages[i]}.webp`;  // 이미지 경로
            teamImage.alt = teamName;
            removeBlur(teamImage);  // 이미지 블러 제거 함수 호출
            cover.appendChild(teamImage);

            // 팀 이름 추가
            const teamNameDiv = document.createElement('div');
            teamNameDiv.classList.add('team-name');
            teamNameDiv.textContent = `${teamName} (${currentPlayer.teamYears[i]})`;
            cover.appendChild(teamNameDiv);

            // 현재 행에 항목 추가
            rowDiv.appendChild(cover);
            itemsInRow++;

            // 3개 항목이 채워지면 새로운 행 생성
            if (itemsInRow === 3) {
                hintContainer.appendChild(rowDiv);  // 완성된 행을 hintContainer에 추가
                rowDiv = document.createElement('div');  // 새로운 행 생성
                rowDiv.classList.add('hint-row');
                itemsInRow = 0;  // 행의 항목 수 초기화
            }
        });

        // 마지막 남은 항목이 있으면 추가 (3개 이하로 남은 경우 처리)
        if (itemsInRow > 0) {
            hintContainer.appendChild(rowDiv);
        }
    } else {
        console.log('No player data available for this index.');
    }
}

//유저가 정답 제출
document.getElementById('player-input').addEventListener('keydown', function(event) {
    // 현재 진행 중인 퀴즈 인덱스
    const quizItem = document.querySelector('.quiz-item');
    const answerPidElement = document.getElementById('answer-pid');  // pid를 표시할 요소 선택
    const savedQuizData = JSON.parse(localStorage.getItem('quizData'));

    if (event.key === 'Enter') {
        event.preventDefault();
        const userInput = document.getElementById('player-input').value;
        const errorMessage = document.getElementById('error-message-player');
        // Send the user input to the server
        fetch('/igotyou/submitAnswer', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                input: userInput,
                quizList: savedQuizData.quizList,
                currentIndex: savedQuizData.currentIndex,
                correctCount: savedQuizData.correctCount,
                totalCount : savedQuizData.totalCount
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.isSubmitted === "false") {
                    errorMessage.style.display = 'block';
                    setTimeout(() => {
                        errorMessage.style.display = 'none';
                    }, 2000);  // 1000ms = 1초
                }else{
                    errorMessage.style.display = 'none';
                    if (data.isCorrect === "true") {
                        savedQuizData.correctCount++;
                        showCorrect(data.currentPlayer, quizItem, answerPidElement);
                        setTimeout(() => {
                            goToNextQuiz(quizItem, answerPidElement, savedQuizData)
                        }, 2000);  // 1000ms = 1초
                    } else if (data.isCorrect === "false") {
                        showWrong(data.currentPlayer, quizItem, answerPidElement);
                        setTimeout(() => {
                            goToNextQuiz(quizItem, answerPidElement, savedQuizData)
                        }, 2000);  // 1000ms = 1초
                    } else{

                    }
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
});

const playerInput = document.getElementById("player-input");

function showCorrect(currentPlayer, quizItem, answerPidElement){
    quizItem.style.transition = 'background-color 1s ease';
    quizItem.style.backgroundColor = "green";
    // 텍스트 필드 수정 불가능하게 설정
    playerInput.disabled = true;
    // PID를 보여주기 위해 answerPidElement에 값을 넣음
    answerPidElement.textContent = currentPlayer.pid;  // 선수의 PID 표시
    answerPidElement.style.display = 'block';  // PID를 표시
}

function showWrong(currentPlayer, quizItem, answerPidElement){
    quizItem.style.transition = 'background-color 1s ease';
    quizItem.style.backgroundColor = "red";
    // 텍스트 필드 수정 불가능하게 설정
    playerInput.disabled = true;
    // PID를 보여주기 위해 answerPidElement에 값을 넣음
    answerPidElement.textContent = currentPlayer.pid;  // 선수의 PID 표시
    answerPidElement.style.display = 'block';  // PID를 표시
}

// 다음 퀴즈로 이동하는 로직을 currentIndex를 사용해 처리
function goToNextQuiz(quizItem, answerPidElement, savedQuizData) {
    // currentIndex 값이 제대로 설정되었는지 확인
    if (isNaN(savedQuizData.currentIndex)) {
        console.error("currentIndex is not a number. Setting to 0 as default.");
        savedQuizData.currentIndex = 0;  // 기본값으로 0 설정
    }
    //선수 pid 초기화
    answerPidElement.textContent ='';
    answerPidElement.style.display = 'none';
    quizItem.style.transition = 'background-color 1s ease';
    quizItem.style.backgroundColor = "#c0c3cf";

    savedQuizData.currentIndex++;
    localStorage.setItem('quizData', JSON.stringify(savedQuizData));
    if (savedQuizData.currentIndex >= savedQuizData.totalCount) {
        endQuiz(savedQuizData);
    }else{
        showHint(savedQuizData.quizList[savedQuizData.currentIndex], savedQuizData.currentIndex, savedQuizData.correctCount, savedQuizData.totalCount);
    }
}

// 퀴즈 종료 처리
function endQuiz(savedQuizData) {
    fetch('/igotyou/end', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(JSON.parse(localStorage.getItem('quizData'))),
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('correct-count-overlay').textContent = savedQuizData.correctCount;
            document.getElementById('total-count-overlay').textContent = savedQuizData.totalCount;
            document.getElementById('quiz-overlay').style.display = 'flex';
        })
        .catch(error => console.error('Error ending quiz:', error));
}
