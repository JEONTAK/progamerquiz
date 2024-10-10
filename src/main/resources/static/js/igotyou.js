
const playerInput = document.getElementById("player-input");

// 가이드 오버레이 보이기
function showGuide() {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'flex';
}

// 가이드 오버레이 닫기
function closeGuide(totalC) {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'none';

    // 서버로 totalCount 값을 전송 (POST 방식으로 변경됨)
    fetch('/igotyou/select', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ totalCount: totalC }) // 데이터를 JSON 형식으로 전송
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                console.error("Error initializing quiz:", data.error);
            } else {
                console.log('Quiz setup complete:', data);

                // 첫 번째 퀴즈 데이터를 받아와서 표시
                fetch(`/igotyou/quiz/data?currentIndex=0`)
                    .then(response => response.json())
                    .then(quizData => {
                        console.log('First quiz data:', quizData);
                        totalCount = quizData.totalCount;
                        correctCount = quizData.correctCount;
                        showHint(quizData.currentPlayer);  // 첫 번째 퀴즈 데이터를 화면에 표시
                    })
                    .catch(error => {
                        console.error('Error fetching first quiz data:', error);
                    });
            }
        })
        .catch(error => {
            console.error('Error setting up quiz:', error);
        });

    // 사용자가 가이드를 닫았으므로, localStorage에 방문 기록을 저장
    localStorage.setItem('guideShown', 'true');
}

// 페이지가 로드될 때 처음 방문한 경우에만 가이드 보여주기
window.onload = function() {
    // localStorage에 'guideShown' 키가 없는 경우에만 가이드 보여줌
    if (!localStorage.getItem('guideShown')) {
        showGuide();
    }
};

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

// 메뉴 토글 기능
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

document.addEventListener('DOMContentLoaded', function () {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const currentIndex = urlParams.get('currentIndex');
    if (currentIndex === null) {
        console.error("Invalid currentIndex. Please select quiz count first.");
        return;  // 요청을 중단하고 더 이상 실행하지 않음
    }
    // 서버로부터 currentTeam 데이터를 가져옴
    fetch(`/igotyou/quiz/data?currentIndex=${currentIndex}`)
        .then(response => {
            // 서버 응답이 정상적인 경우 (status code가 200)
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json(); // 응답을 JSON으로 파싱
        })
        .then(data => {
            // 서버에서 받은 데이터를 처리
            const currentPlayer = data.currentPlayer;
            console.log('Current Player:', currentPlayer);
            correctCount = data.correctCount;
            showHint(currentPlayer);
        })
        .catch(error => {
            console.error('Error fetching quiz data:', error);
        });
});


// 현재 진행 중인 퀴즈 인덱스
const quizItem = document.querySelector('.quiz-item');
const answerPidElement = document.getElementById('answer-pid');  // pid를 표시할 요소 선택

// quizList를 받아서 힌트(팀 정보)를 보여주는 함수
function showHint(currentPlayer) {
    const questionNumberElement = document.getElementById('question-number');
    console.log("TC : " + totalCount);
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

document.getElementById('player-input').addEventListener('keydown', function(event) {
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
            body: JSON.stringify({ input: userInput })
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
                        showCorrect(data.currentPlayer);
                        setTimeout(() => {
                            goToNextQuiz()
                        }, 2000);  // 1000ms = 1초
                    } else if (data.isCorrect === "false") {
                        showWrong(data.currentPlayer);
                        setTimeout(() => {
                            goToNextQuiz()
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

function showCorrect(currentPlayer){
    quizItem.style.transition = 'background-color 1s ease';
    quizItem.style.backgroundColor = "green";
    // 텍스트 필드 수정 불가능하게 설정
    playerInput.disabled = true;
    // PID를 보여주기 위해 answerPidElement에 값을 넣음
    answerPidElement.textContent = currentPlayer.pid;  // 선수의 PID 표시
    answerPidElement.style.display = 'block';  // PID를 표시
}

function showWrong(currentPlayer){
    quizItem.style.transition = 'background-color 1s ease';
    quizItem.style.backgroundColor = "red";
    // 텍스트 필드 수정 불가능하게 설정
    playerInput.disabled = true;
    // PID를 보여주기 위해 answerPidElement에 값을 넣음
    answerPidElement.textContent = currentPlayer.pid;  // 선수의 PID 표시
    answerPidElement.style.display = 'block';  // PID를 표시
}

// 다음 퀴즈로 이동하는 로직을 currentIndex를 사용해 처리
function goToNextQuiz() {
    // currentIndex 값이 제대로 설정되었는지 확인
    if (isNaN(currentIndex)) {
        console.error("currentIndex is not a number. Setting to 0 as default.");
        currentIndex = 0;  // 기본값으로 0 설정
    }
    //선수 pid 초기화
    answerPidElement.textContent ='';
    answerPidElement.style.display = 'none';
    quizItem.style.transition = 'background-color 1s ease';
    quizItem.style.backgroundColor = "#c0c3cf";
    const nextIndex = parseInt(currentIndex) + 1;
    console.log(nextIndex);
    console.log(totalCount);
    playerInput.enabled = true;
    // currentIndex와 quizList 길이 비교
    // 15번째 퀴즈 이후에는 끝내기 처리
    if (nextIndex >= totalCount) {
        // 퀴즈가 끝나면 서버에서 맞춘 개수와 전체 개수 가져오기
        fetch("/igotyou/end")
            .then(response => response.json())
            .then(data => {
                document.getElementById('correct-count-overlay').textContent = data.correctCount;
                document.getElementById('total-count-overlay').textContent = data.totalCount;
                document.getElementById('quiz-overlay').style.display = 'flex';  // 오버레이 보이기
            })
            .catch(error => console.error("Error fetching quiz results:", error));
    } else {
        window.location.href = `/igotyou/quiz?currentIndex=${nextIndex}`;
    }
}
