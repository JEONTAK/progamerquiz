const teamImage =  document.getElementById('team-image');
const playerImage =  document.getElementById('player-image');
const teamName =  document.getElementById('team-name');
const progamerTag =  document.getElementById('progamer-tag');
const playerInput = document.getElementById("player-input");

function startQuiz() {
    fetch('/leagueoflegends/pieceofpuzzle/startQuiz', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({totalQuizCount: 15}),
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                console.error("Error initializing quiz:", data.error);
            } else {// 상태를 로컬로 저장
                localStorage.setItem('quizData', JSON.stringify({
                    id : data.id,
                    index: data.index,
                    totalQuizCount: data.totalQuizCount,
                    correctQuizCount: data.correctQuizCount,
                    quizList: data.quizList
                }));
                console.log(data);
                showHint(data.quizList[0], data.index, data.correctQuizCount, data.totalQuizCount); // 첫 번째 퀴즈 표시
            }
        })
        .catch(error => {
            console.error('Error setting up quiz:', error);
        });
}

function goToMainPage() {
    // localStorage에서 guideShown 값을 삭제 (초기화)
    localStorage.removeItem('guideShown');
    window.location.href = '/'; // 메인 페이지 URL로 이동 ("/"는 메인 페이지로 이동하는 경로)
}

document.addEventListener("DOMContentLoaded", function() {
    startQuiz();
    // JSON 파일을 fetch API로 로드
    fetch('/database/Progamer-LOL.json')  // static 경로를 통해 JSON 파일에 접근
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
                    const filtered = progamerList.filter(progamer => progamer.progamerTag.toLowerCase().includes(query));
                    filtered.forEach(progamer => {
                        const suggestionItem = document.createElement('div');
                        suggestionItem.textContent = progamer.progamerTag;  // pid를 표시
                        suggestionItem.classList.add('suggestion-item');  // 스타일링을 위한 클래스 추가

                        // suggestionItem 클릭 시 해당 값을 input에 넣음
                        suggestionItem.addEventListener('click', function() {
                            input.value = progamer.progamerTag;
                            suggestions.innerHTML = '';  // 클릭 후 suggestion 목록을 비움
                        });

                        suggestions.appendChild(suggestionItem);
                    });
                }
            });
        }).catch(error => console.error('Error fetching JSON:', error));
});

document.getElementById('player-input').addEventListener('keydown', function(event) {
    const savedQuizData = JSON.parse(localStorage.getItem('quizData'));

    if (event.key === 'Enter') {
        event.preventDefault();
        const userInput = document.getElementById('player-input').value;
        const errorMessage = document.getElementById('error-message-player');

        // Send the user input to the server
        fetch('/leagueoflegends/pieceofpuzzle/submitAnswer', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                id: savedQuizData.id,
                index: savedQuizData.index,
                input: userInput,
                correctQuizCount: savedQuizData.correctQuizCount,
                totalQuizCount: savedQuizData.totalQuizCount
            })
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('player-input').value = "";
                console.log(savedQuizData.correctQuizCount + " " + data.correctQuizCount);
                if (savedQuizData.correctQuizCount < data.correctQuizCount) {
                    savedQuizData.correctQuizCount++;
                    showCorrect(savedQuizData.quizList[savedQuizData.index], quizItem);
                    savedQuizData.index++;

                    setTimeout(() => {
                        goToNextQuiz(quizItem, savedQuizData, savedQuizData.index)
                    }, 2000);  // 1000ms = 1초
                } else {
                    showWrong(savedQuizData.quizList[savedQuizData.index], quizItem);
                    savedQuizData.index++;
                    setTimeout(() => {
                        goToNextQuiz(quizItem, savedQuizData, savedQuizData.index)
                    }, 2000);  // 1000ms = 1초
                }
            })
            .catch(error => {
                console.error("Error:", error);
                errorMessage.textContent = "An error occurred. Please try again.";
                errorMessage.style.display = "block";
            });
    }
});

const quizItem = document.querySelector('.quiz-item');


function showCorrect(currentTeam, quizItem) {
    quizItem.style.transition = 'background-color 1s ease';
    quizItem.style.backgroundColor = "green";
    // 텍스트 필드 수정 불가능하게 설정
    playerInput.disabled = true;
    playerImage.style.filter = "none";
    progamerTag.textContent = currentTeam.answerProgamerTag;
}

function showWrong(currentTeam, quizItem) {
    quizItem.style.transition = 'background-color 1s ease';
    quizItem.style.backgroundColor = "red";
    // 텍스트 필드 수정 불가능하게 설정
    playerInput.disabled = true;
    playerImage.style.filter = "none";
    progamerTag.textContent = currentTeam.answerProgamerTag;
}

// 다음 퀴즈로 이동하는 로직을 index를 사용해 처리
function goToNextQuiz(quizItem, savedQuizData, index) {
    // index 값이 제대로 설정되었는지 확인
    if (isNaN(index)) {
        console.error("index is not a number. Setting to 0 as default.");
        savedQuizData.index = 0;  // 기본값으로 0 설정
    }
    quizItem.style.transition = 'background-color 1s ease';
    quizItem.style.backgroundColor = "#091428";
    progamerTag.textContent = "";

    localStorage.setItem('quizData', JSON.stringify(savedQuizData));
    if (savedQuizData.index >= savedQuizData.totalQuizCount) {
        endQuiz(savedQuizData);
    } else {
        showHint(savedQuizData.quizList[index], savedQuizData.index, savedQuizData.correctQuizCount, savedQuizData.totalQuizCount);
    }
}

function showHint(currentTeam, index, correctQuizCount, totalQuizCount) {
    playerInput.disabled = false;

    teamImage.src = `/images/LOL/team/${currentTeam.imageId}.png`;  // 이미지 경로
    teamImage.alt = currentTeam.teamName;
    playerImage.src = `/images/LOL/player/${currentTeam.answerProgamerId}.png`;  // 이미지 경로
    playerImage.alt = currentTeam.answerProgamerTag;
    playerImage.style.filter = 'blur(5px)';
    teamName.textContent = currentTeam.teamName;

    const questionNumberElement = document.getElementById('question-number');
    questionNumberElement.textContent = index + 1 + `(${currentTeam.seasonYear})` + `(${currentTeam.answerProgamerPosition})`;  // 문제 번호는 인덱스에 1을 더한 값
    document.getElementById('total-count').textContent = totalQuizCount;
    // 맞춘 개수 / 전체 개수를 업데이트
    document.getElementById('correct-count').textContent = correctQuizCount;
    const hintContainer = document.getElementById('hintContainer');
    hintContainer.innerHTML = ''; // 이전 힌트들을 초기화

    if (currentTeam) {
        // 새로운 행을 담을 변수 선언
        let rowDiv = document.createElement('div');
        rowDiv.classList.add('hint-row');  // 행을 만들기 위해 클래스를 추가
        let itemsInRow = 0;  // 현재 행에 추가된 항목 수
        currentTeam.rosters.forEach((progamer) => {
            // 팀 항목을 감쌀 div 생성
            const cover = document.createElement('div');
            cover.classList.add('cover');  // 스타일 적용을 위한 클래스

            // 팀 이미지 추가
            const progamerImage = document.createElement('img');
            cover.appendChild(progamerImage);
            progamerImage.src = `/images/LOL/player/${progamer.id}.png`;  // 이미지 경로
            progamerImage.alt = progamer.progamerTag;
            removeBlur(teamImage);  // 이미지 블러 제거 함수 호출


            // 팀 이름 추가
            const progamerNameDiv = document.createElement('div');
            cover.appendChild(progamerNameDiv);
            progamerNameDiv.classList.add('team-name');
            progamerNameDiv.textContent = `${progamer.progamerTag}`;


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

// 퀴즈 종료 처리
function endQuiz(savedQuizData) {
    fetch('/leagueoflegends/pieceofpuzzle/end', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: savedQuizData.id,
            correctQuizCount: savedQuizData.correctQuizCount,
            totalQuizCount: savedQuizData.totalQuizCount
        })
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('correct-count-overlay').textContent = savedQuizData.correctQuizCount;
            document.getElementById('total-count-overlay').textContent = savedQuizData.totalQuizCount;
            document.getElementById('quiz-overlay').style.display = 'flex';
        })
        .catch(error => console.error('Error ending quiz:', error));
}

function removeBlur(imageElement) {
    imageElement.classList.add('no-blur');
}

