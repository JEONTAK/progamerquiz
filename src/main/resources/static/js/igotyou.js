// 가이드 오버레이 보이기
function showGuide() {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'flex';
}

// 가이드 오버레이 닫기
function closeGuide(totalCount) {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'none';

    // 서버로 totalCount: 10 값을 전송
    fetch('/igotyou/'+ totalCount, {  // 서버의 실제 엔드포인트로 변경해야 함
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'totalCount=' + encodeURIComponent(totalCount)  // totalCount를 제대로 전송
    })
        .then(response => response.json()) // 서버 응답을 JSON으로 파싱
        .then(data => {
            console.log('Server response:', data);  // 서버 응답을 처리
        })
        .catch(error => {
            console.error('Error sending data:', error);  // 에러 처리
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

document.addEventListener('DOMContentLoaded', function () {
    // quizList가 빈 배열일 경우 1초 후 새로고침
    if (quizList.length === 0) {
        setTimeout(function() {
            location.reload();  // 페이지 새로고침
        }, 1000);  // 1초 후 새로고침
    } else {
        console.log("QuizList Loaded:", quizList);
        showHintsForPlayer(currentIndex);  // quizList가 있으면 실행
    }
});

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
                        suggestions.appendChild(suggestionItem);
                    });
                }
            });
        }).catch(error => console.error('Error fetching JSON:', error));
});

// 현재 진행 중인 퀴즈 인덱스
let currentIndex = 0;


// quizList를 받아서 힌트(팀 정보)를 보여주는 함수
function showHintsForPlayer(index) {
    const hintContainer = document.getElementById('hintContainer');
    hintContainer.innerHTML = ''; // 이전 힌트들을 초기화

    const currentPlayer = quizList[index];

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
let correctCount = 0;  // 맞춘 개수

// 페이지 로드 시 전체 문제 수를 표시
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('total-count').textContent = totalCount;
    updateQuestionNumber();
});

function updateQuestionNumber() {
    const questionNumberElement = document.getElementById('question-number');
    questionNumberElement.textContent = currentIndex + 1;  // 문제 번호는 인덱스에 1을 더한 값
}

// 정답 제출 함수
function checkAnswer() {
    const input = document.getElementById('player-input').value.trim().toLowerCase();
    const currentPlayer = quizList[currentIndex];
    const answerPid = currentPlayer.pid.toLowerCase();
    const quizItem = document.querySelector('.quiz-item');
    const answerPidElement = document.getElementById('answer-pid');  // pid를 표시할 요소 선택


    if (input === answerPid) {
        quizItem.style.transition = 'background-color 1s ease';
        quizItem.style.backgroundColor = "green";
        correctCount++;  // 맞춘 개수 증가
    } else {
        quizItem.style.transition = 'background-color 1s ease';
        quizItem.style.backgroundColor = "red";
    }
    // PID를 보여주기 위해 answerPidElement에 값을 넣음
    answerPidElement.textContent = currentPlayer.pid;  // 선수의 PID 표시
    answerPidElement.style.display = 'block';  // PID를 표시
    // 맞춘 개수 / 전체 개수를 업데이트
    document.getElementById('correct-count').textContent = correctCount;

    // 2초 후에 다음 퀴즈로 넘어가도록 설정
    setTimeout(function() {
        //선수 pid 초기화
        answerPidElement.textContent ='';
        answerPidElement.style.display = 'none';
        quizItem.style.transition = 'background-color 1s ease';
        quizItem.style.backgroundColor = "#c0c3cf";
        // 다음 퀴즈로 넘어가기
        currentIndex++;
        if (currentIndex < quizList.length) {
            showHintsForPlayer(currentIndex);  // 다음 선수의 힌트를 보여줌
            document.getElementById('player-input').value = '';  // 입력값 초기화
            quizItem.classList.remove('correct', 'incorrect');  // 다음 문제로 넘어갈 때 클래스 초기화
            answerPidElement.style.display = 'none';  // 다음 문제에서는 PID 숨김
            updateQuestionNumber();  // 문제 번호 업데이트
        } else {
            // 퀴즈가 완료되었을 때 오버레이를 띄움
            document.getElementById('correct-count-overlay').textContent = correctCount;
            document.getElementById('total-count-overlay').textContent = totalCount;
            document.getElementById('quiz-overlay').style.display = 'flex';  // 오버레이 보이기
        }
    }, 2000);  // 2000ms = 2초
}

// 처음 시작할 때 첫 번째 선수의 힌트를 보여줌
document.addEventListener('DOMContentLoaded', function () {
    showHintsForPlayer(currentIndex);
});

// 엔터키로 정답 제출 처리
document.getElementById('player-input').addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        checkAnswer();
    }
});

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