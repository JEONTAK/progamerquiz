
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
}


// 페이지가 로드될 때 처음 방문한 경우에만 가이드 보여주기
window.onload = function() {
    // localStorage에 'guideShown' 키가 없는 경우에만 가이드 보여줌
    if (!localStorage.getItem('guideShown')) {
        showGuide();
    }
};
// 메뉴 토글 기능
function toggleMenu() {
    document.body.classList.toggle('menu-open');
    document.querySelector('.content-container').classList.toggle('menu-open');
}

function removeBlur(imageElement) {
    imageElement.classList.add('no-blur');
}


function goToMainPage() {
    // localStorage에서 guideShown 값을 삭제 (초기화)
    localStorage.removeItem('guideShown');
    window.location.href = '/'; // 메인 페이지 URL로 이동 ("/"는 메인 페이지로 이동하는 경로)
}

const teamImage =  document.getElementById('team-image');
const playerInput = document.getElementById("player-input");

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

document.getElementById('player-input').addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        const userInput = document.getElementById('player-input').value;
        const errorMessage = document.getElementById('error-message');

        // Send the user input to the server
        fetch('/pieceofpuzzle/submitAnswer', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                input: userInput,
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
                        showCorrect(data.correctId);
                        setTimeout(() => {
                            if (data.isFinish === "false") {
                                playerInput.disabled = false;
                            }else{
                                goToNextQuiz();
                            }
                        }, 2000);  // 1000ms = 1초
                    } else if (data.isCorrect === "false") {
                        showWrong();
                        setTimeout(() => {
                            quizItem.style.backgroundColor = "";  // 원래 배경색으로 복원
                            if (data.isFinish === "false") {
                                playerInput.disabled = false;
                            }else{
                                goToNextQuiz();
                            }
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

document.addEventListener('DOMContentLoaded', function () {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const currentIndex = urlParams.get('currentIndex') || 0; // currentIndex를 URL에서 가져옴

    // 서버로부터 currentTeam 데이터를 가져옴
    fetch(`/pieceofpuzzle/quiz/data?currentIndex=${currentIndex}`)
        .then(response => {
            // 서버 응답이 정상적인 경우 (status code가 200)
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json(); // 응답을 JSON으로 파싱
        })
        .then(data => {
            // 서버에서 받은 데이터를 처리
            const currentTeam = data.currentTeam;
            console.log('Current Team:', currentTeam);

            document.getElementById('team-name').textContent = currentTeam.teamName;
            document.getElementById('team-year').textContent = currentTeam.teamYear;
            // 필요한 처리 로직 추가: 예를 들어, 팀 정보를 화면에 표시
            teamImage.src = `/images/team/${currentTeam.image_path}.webp`;
            removeBlur(teamImage);
            showHint(currentTeam); // currentTeam으로 힌트 표시
        })
        .catch(error => {
            console.error('Error fetching quiz data:', error);
        });
});


const quizItem = document.querySelector('.quiz-item');

function showCorrect(correctId) {
    // 선수 이미지와 pid를 correctId로 찾음
    const playerImage = document.querySelector(`img[data-player-id='${correctId}']`);
    const playerPid = document.querySelector(`p[data-player-id='${correctId}']`);

    if (playerImage && playerPid) {
        // 이미지 블러 해제
        playerImage.style.filter = "none";

        // 배경색 초록색으로 변경
        playerImage.style.backgroundColor = "green";

        // PID를 ???에서 원래 PID로 변경
        const originalPid = playerPid.getAttribute("data-original-pid");
        if (originalPid) {
            playerPid.textContent = originalPid;  // 원래 PID로 변경
        }

        // 2초 후에 배경색을 원래대로 변경
        setTimeout(() => {
            playerImage.style.backgroundColor = "";  // 원래 배경색으로 복원
        }, 2000);
    }

    // 텍스트 필드 수정 불가능하게 설정
    playerInput.disabled = true;
}

function showWrong(){
    quizItem.style.backgroundColor = "red";
    // 텍스트 필드 수정 불가능하게 설정
    playerInput.disabled = true;
}


// 다음 퀴즈로 이동하는 로직을 currentIndex를 사용해 처리
function goToNextQuiz() {
    const nextIndex = parseInt(currentIndex) + 1;
    playerInput.disabled = false;
    // currentIndex와 quizList 길이 비교
    // 15번째 퀴즈 이후에는 끝내기 처리
    if (nextIndex >= quizList.length) {
        // 퀴즈가 끝나면 서버에서 맞춘 개수와 전체 개수 가져오기
        fetch("/pieceofpuzzle/end")
            .then(response => response.json())
            .then(data => {
                document.getElementById('correct-count-overlay').textContent = data.correctCount;
                document.getElementById('total-count-overlay').textContent = data.totalCount;
                document.getElementById('quiz-overlay').style.display = 'flex';  // 오버레이 보이기
            })
            .catch(error => console.error("Error fetching quiz results:", error));
    } else {
        window.location.href = `/pieceofpuzzle/quiz?currentIndex=${nextIndex}`;
    }
}

function showHint(currentTeam){
    const hintContainer = document.getElementById('hintContainer');
    hintContainer.innerHTML = ''; // 이전 힌트들을 초기화

    if (currentTeam) {
        // 새로운 행을 담을 변수 선언
        let rowDiv = document.createElement('div');
        rowDiv.classList.add('hint-row');  // 행을 만들기 위해 클래스를 추가
        let itemsInRow = 0;  // 현재 행에 추가된 항목 수

        currentTeam.roster.forEach(player => {
            const isAnswer = currentTeam.answer.some(answer => answer[player.id] === false);

            // 선수 정보를 담은 div 생성
            const cover = document.createElement('div');
            cover.classList.add('cover');

            const playerImage = document.createElement('img');
            playerImage.src = `/images/player/${player.id}.webp`;
            playerImage.setAttribute('data-player-id', player.id);  // player ID를 속성으로 저장

            // answer에 해당하는 선수는 블러 처리
            if (!isAnswer) {
                removeBlur(playerImage);
            } else {
                playerImage.style.filter = 'blur(5px)';
            }

            const playerPid = document.createElement('p');
            playerPid.textContent = isAnswer ? "???" : player.pid;
            playerPid.setAttribute('data-player-id', player.id);  // player ID를 속성으로 저장
            playerPid.setAttribute('data-original-pid', player.pid);  // 실제 pid를 저장

            cover.appendChild(playerImage);
            cover.appendChild(playerPid);
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

function removeBlur(imageElement) {
    imageElement.classList.add('no-blur');
}