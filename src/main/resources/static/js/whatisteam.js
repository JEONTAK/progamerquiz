
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

function goToMainPage() {
    // localStorage에서 guideShown 값을 삭제 (초기화)
    localStorage.removeItem('guideShown');
    window.location.href = '/'; // 메인 페이지 URL로 이동 ("/"는 메인 페이지로 이동하는 경로)
}

document.addEventListener("DOMContentLoaded", function() {
    // JSON 파일을 fetch API로 로드
    fetch('/database/Team.json')  // static 경로를 통해 JSON 파일에 접근
        .then(response => response.json())
        .then(teamList => {
            const input = document.getElementById('player-input');
            const suggestions = document.getElementById('suggestions');

            // 입력값에 따라 필터링된 팀을 가져오는 함수
            function filterTeams(query) {
                query = query.toLowerCase();
                let filtered = [];

                if (query.length >= 2) {
                    filtered = teamList.filter(team => team.callName.toLowerCase().includes(query));
                }

                if (query.length >= 3) {
                    const filteredByName = teamList.filter(team => team.name.toLowerCase().includes(query));
                    filtered = [...filtered, ...filteredByName];
                }

                // 중복 제거를 위한 Set 사용
                const uniqueTeams = new Set();
                filtered = filtered.filter(team => {
                    if (!uniqueTeams.has(team.name)) {
                        uniqueTeams.add(team.name);
                        return true;
                    }
                    return false;
                });

                return filtered;
            }

            // Suggestion 항목을 만드는 함수
            function createSuggestionItem(team) {
                const suggestionItem = document.createElement('div');
                suggestionItem.textContent = `${team.name}`;  // 팀 이름과 시즌 표시

                // 클릭 이벤트 추가
                suggestionItem.addEventListener('click', function() {
                    input.value = team.name;  // 클릭한 팀 이름을 input에 설정
                    suggestions.innerHTML = '';  // 선택 후, suggestion 목록 지우기
                });

                return suggestionItem;
            }

            // 입력값에 따라 필터링하여 suggestion에 표시
            input.addEventListener('input', function () {
                const query = input.value;
                suggestions.innerHTML = '';  // 이전 내용을 비움

                if (query.length >= 2) {
                    const filteredTeams = filterTeams(query);

                    // 필터링된 팀 목록을 suggestion에 추가
                    filteredTeams.forEach(team => {
                        const suggestionItem = createSuggestionItem(team);
                        suggestions.appendChild(suggestionItem);
                    });
                }
            });
        })
        .catch(error => console.error('Error fetching JSON:', error));
});

const playerImage =  document.getElementById('player-image');
const playerInput = document.getElementById("player-input");

document.addEventListener('DOMContentLoaded', function () {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const currentIndex = urlParams.get('currentIndex') || 0; // currentIndex를 URL에서 가져옴

    // 서버로부터 currentTeam 데이터를 가져옴
    fetch(`/whatisteam/quiz/data?currentIndex=${currentIndex}`)
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

            // 필요한 처리 로직 추가: 예를 들어, 팀 정보를 화면에 표시
            playerImage.src = `/images/team/${currentTeam.image_path}.webp`;
            showHint(currentTeam); // currentTeam으로 힌트 표시
        })
        .catch(error => {
            console.error('Error fetching quiz data:', error);
        });
});

document.getElementById('player-input').addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        const userInput = document.getElementById('player-input').value;
        const errorMessage = document.getElementById('error-message');

        // Send the user input to the server
        fetch('/whatisteam/submitAnswer', {
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
                        showCorrect();

                        setTimeout(() => {
                            goToNextQuiz()
                        }, 2000);  // 1000ms = 1초
                    } else if (data.isCorrect === "false") {
                        showWrong();

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

const quizItem = document.querySelector('.quiz-item');

function showCorrect(){
 /*   const answerPidElement = document.getElementById('answer-pid');
    answerPidElement.textContent = answer.pid; // 정답의 PID 표시
    answerPidElement.style.display = 'block'; // 정답 PID 표시*/

    // 이미지 블러 해제 및 배경색 변경 (정답일 때 초록색으로 변경)
    playerImage.style.filter = "none";
    quizItem.style.backgroundColor = "green";

    // 텍스트 필드 수정 불가능하게 설정
    playerInput.disabled = true;
}

function showWrong(){
 /*   const answerPidElement = document.getElementById('answer-pid');
    answerPidElement.textContent = answer.pid; // pid 출력
    answerPidElement.style.display = 'block';
*/
    // 이미지 블러 해제 및 배경색 변경 (정답일 때 초록색으로 변경)
    playerImage.style.filter = "none";
    quizItem.style.backgroundColor = "red";

    // 텍스트 필드 수정 불가능하게 설정
    playerInput.disabled = true;
}


// 다음 퀴즈로 이동하는 로직을 currentIndex를 사용해 처리
function goToNextQuiz() {
/*    answerPidElement.style.display = 'none';*/
    playerImage.style.filter = "blur(30px)";
    quizItem.style.backgroundColor = "#091428";
    playerInput.enabled = true;
    const nextIndex = parseInt(currentIndex) + 1;
    window.location.href = `/whatisteam/quiz?currentIndex=${nextIndex}`;
}

function showHint(currentTeam){
    const hintContainer = document.getElementById("hintContainer");
    function showHintNames() {
        const hintName = ['LEAGUE', 'YEAR', 'SPRING', 'SUMMER', 'WINTER', 'MSI', 'WORLDS'];
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
    }

    showHintNames();
    function setHintItemSizes() {
        const containerWidth = hintContainer.offsetWidth;
        const itemSize = containerWidth / 6; // hint-item 크기를 컨테이너 너비의 6등분으로 설정

        const hintItems = document.querySelectorAll('.hint-item');
        hintItems.forEach(item => {
            const cover = item.querySelector('.cover');
            const img = cover.querySelector('img');
            const span = item.querySelector('span');

            if (cover && img) {
                cover.style.width = `${itemSize * 0.7}px`;  // cover의 크기 설정
                cover.style.height = `${itemSize * 0.7}px`; // cover의 크기 설정
                img.style.width = `${itemSize * 0.6}px`;    // img의 크기 설정
                img.style.height = `${itemSize * 0.6}px`;   // img의 크기 설정
            }

            if (span) {
                span.style.fontSize = `${itemSize * 0.15}px`; // 텍스트 크기 설정
            }
        });
    }

    const hintRow = document.createElement("div");
    hintRow.classList.add("hint-row");
    const hintData = [
        {
            label: 'League',
            value: currentTeam.teamLeague,  // answer 값 추가
            icon: `/images/league/${currentTeam.teamLeague}.webp`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Year',
            value: currentTeam.teamYear,  // answer 값 추가
            icon: `/images/number/number_${currentTeam.teamYear.toString().slice(-2)}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Spring',
            value: currentTeam.spring_ranking != null ? currentTeam.spring_ranking : 0,  // answer 값 추가
            icon: `/images/number/number_${currentTeam.spring_ranking != null ? currentTeam.spring_ranking : 0}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Summer',
            value: currentTeam.summer_ranking != null ? currentTeam.summer_ranking : 0, // answer 값 추가
            icon: `/images/number/number_${ currentTeam.summer_ranking != null ? currentTeam.summer_ranking : 0}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Winter',
            value: currentTeam.winter_ranking != null ? currentTeam.winter_ranking : 0, // answer 값 추가
            icon: `/images/number/number_${currentTeam.winter_ranking != null ? currentTeam.winter_ranking : 0}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'MSI',
            value: currentTeam.msi_ranking != null ? currentTeam.msi_ranking : 0, // answer 값 추가
            icon: `/images/number/number_${currentTeam.msi_ranking != null ? currentTeam.msi_ranking : 0}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'WORLDS',
            value: currentTeam.worlds_ranking != null ? currentTeam.worlds_ranking : 0, // answer 값 추가
            icon: `/images/number/number_${currentTeam.worlds_ranking != null ? currentTeam.worlds_ranking : 0}.png`,
            fallbackIcon: '/images/none.png'
        }
    ];

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
        img.onerror = function () {
            this.src = hint.fallbackIcon;
        };
        img.onerror = function() {
            this.src = hint.fallbackIcon;
        };
        img.alt = hint.label;
        removeBlur(img);

        cover.appendChild(img);
        // 힌트 값 추가
        const span = document.createElement("span");
        span.classList.add("hint-value");
        span.textContent = hint.value;

        hintItem.appendChild(cover);
        hintItem.appendChild(span);
        hintRow.appendChild(hintItem);  // hintItem을 hintRow에 추가
        hintRow.classList.add("fade-in");  // hintRow에 fade-in 적용
        hintRow.childNodes.forEach(item => {
            item.style.display = "block";   // 모든 자식 요소가 보이게 설정
            item.style.opacity = "1";      // 모든 자식 요소의 투명도 설정
        });
    });
    hintContainer.appendChild(hintRow);
    setHintItemSizes();
}

function removeBlur(imageElement) {
    imageElement.classList.add('no-blur');
}