
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

                return filtered;
            }

            // Suggestion 항목을 만드는 함수
            function createSuggestionItem(team) {
                const suggestionItem = document.createElement('div');
                suggestionItem.textContent = `${team.name} (${team.seasonYear})`;  // 팀 이름과 시즌 표시

                // 클릭 이벤트 추가
                suggestionItem.addEventListener('click', function() {
                    input.value = team.name + "/" + team.seasonYear;  // 클릭한 팀 이름을 input에 설정
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

//Team 입력 제출
document.addEventListener('DOMContentLoaded', function () {
    const input = document.getElementById('player-input');

    input.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault(); // 기본 Enter 동작을 막고
            document.getElementById('playerForm').submit(); // form 제출
        }
    });
});

document.addEventListener("DOMContentLoaded", function() {
    console.log("currentIdx : " + currentIdx);
    loadInitialQuiz();
    const errorMessage = document.getElementById('error-message');
    console.log(isSubmitted);
    if (isSubmitted === "true") {
        errorMessage.style.display = 'none';
    } else {
        console.log("팀 없음");
        errorMessage.style.display = 'block';
        setTimeout(() => {
            errorMessage.style.display = 'none';
        }, 2000);  // 1000ms = 1초
        return;
    }
    // isCorrect에 따른 동작
    handleResult(); // 정답 또는 오답 처리 = 2초
});

// 정답 또는 오답에 따른 처리를 담당하는 함수
function handleResult() {
    function correct() {
        const currentTeam = quizList[currentIdx];  // 첫 번째 퀴즈 데이터 가져오기
        const playerImage = document.getElementById("player-image");
        playerImage.src = `/images/team/${currentTeam.image_path}.webp`;
        playerImage.style.transition = 'background-color 1s ease, filter 1s ease';
        playerImage.style.backgroundColor = "green";  // 배경색을 초록색으로 변경
        playerImage.style.filter = "none";  // 블러 제거
        showHint(currentTeam);  // 첫 번째 힌트 표시
        // 2초 후 다음 퀴즈로 넘어가기
        setTimeout(() => {
            console.log("정답");
            goToNextQuiz();  // 다음 퀴즈로 넘어가는 함수를 호출
        }, 2000);  // 2000ms = 2초
    }
    function wrong() {
        const currentTeam = quizList[currentIdx];  // 첫 번째 퀴즈 데이터 가져오기
        const playerImage = document.getElementById("player-image");
        playerImage.src = `/images/team/${currentTeam.image_path}.webp`;
        playerImage.style.transition = 'background-color 1s ease, filter 1s ease';
        playerImage.style.backgroundColor = "green";  // 배경색을 초록색으로 변경
        playerImage.style.filter = "none";  // 블러 제거
        showHint(currentTeam);  // 첫 번째 힌트 표시
        // 2초 후 다음 퀴즈로 넘어가기
        setTimeout(() => {
            console.log("오답");
            goToNextQuiz();  // 다음 퀴즈로 넘어가는 함수를 호출
        }, 2000);  // 2000ms = 2초
    }
    if (isCorrect === "true") {
        correct();
    } else if (isCorrect === "false") {
        wrong();
    }
}

// 처음 시작할 때 첫 번째 퀴즈를 로드하는 함수
function loadInitialQuiz() {
    const currentTeam = quizList[currentIdx];  // 첫 번째 퀴즈 데이터 가져오기
    const playerImage = document.getElementById("player-image");
    playerImage.src = `/images/team/${currentTeam.image_path}.webp`;
    playerImage.style.filter = 'blur(30px)';
    showHint(currentTeam);  // 첫 번째 힌트 표시
}

// 다음 퀴즈로 넘어가는 함수 정의
function goToNextQuiz() {
    if (currentIdx >= quizList.length) {
        alert("퀴즈가 끝났습니다!");  // 마지막 퀴즈인 경우 알림 표시
        window.location.href = "/";  // 메인 페이지로 이동하거나 결과 페이지로 이동
    }
}

function showHint(currentTeam){

    // hint-item 크기 설정 함수
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

    if (currentTeam) {
        const hintName = ['League', 'Spring', 'Summer', 'Winter', 'Worlds', 'MSI'];
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

        // 새로운 행을 담을 변수 선언
        let rowDiv = document.createElement('div');
        rowDiv.classList.add('hint-row');  // 행을 만들기 위해 클래스를 추가
        let itemsInRow = 0;  // 현재 행에 추가된 항목 수


        // 마지막 남은 항목이 있으면 추가 (3개 이하로 남은 경우 처리)
        if (itemsInRow > 0) {
            hintContainer.appendChild(rowDiv);
        }

    } else {
        console.log('No player data available for this index.');
    }

    // 힌트 행 생성
    const hintRow = document.createElement("div");
    hintRow.classList.add("hint-row");
    // 각 힌트 정보를 아이템으로 추가

    const hintData = [
        {
            label: 'League',
            value: currentTeam.teamLeague,
            icon: `/images/league/${currentTeam.teamLeague}.webp`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Spring',
            value: currentTeam.spring_ranking,
            icon: `/images/number/number_${currentTeam.spring_ranking != null ? currentTeam.spring_ranking : 0}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Summer',
            value: currentTeam.summer_ranking,
            icon: `/images/number/number_${currentTeam.summer_ranking != null ? currentTeam.summer_ranking : 0}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Winter',
            value: currentTeam.winter_ranking,
            icon: `/images/number/number_${currentTeam.winter_ranking != null ? currentTeam.winter_ranking : 0}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'MSI',
            value: currentTeam.msi_ranking,
            icon: `/images/number/number_${currentTeam.msi_ranking != null ? currentTeam.msi_ranking : 0}.png`,
            fallbackIcon: '/images/none.png'
        },
        {
            label: 'Worlds',
            value: currentTeam.worlds_ranking,
            icon: `/images/number/number_${currentTeam.worlds_ranking != null ? currentTeam.worlds_ranking : 0}.png`,
            fallbackIcon: '/images/none.png'
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
        img.onerror = function () {
            this.src = hint.fallbackIcon;
        };
        img.onload = function() {
            console.log("Image loaded:", img.src);
        };

        img.onerror = function() {
            console.log("Failed to load image:", img.src);
            this.src = hint.fallbackIcon;
        };
        img.alt = hint.label;
        removeBlur(img);

        cover.appendChild(img);
        console.log("Generated img tag:", img);
        // 힌트 값 추가
        const span = document.createElement("span");
        span.classList.add("hint-value");

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


    // 페이지 로드 후 hint-item 크기 설정
    setHintItemSizes();

    // 창 크기가 변경될 때마다 hint-item 크기 다시 설정
    window.addEventListener('resize', setHintItemSizes);
}

window.addEventListener('resize', function() {
    const minWidth = 600;
    const minHeight = 400;

    if (window.innerWidth < minWidth) {
        document.body.style.width = `${minWidth}px`;
    } else {
        document.body.style.width = 'auto';  // 다시 기본값으로
    }

    if (window.innerHeight < minHeight) {
        document.body.style.height = `${minHeight}px`;
    } else {
        document.body.style.height = 'auto';  // 다시 기본값으로
    }
});

function removeBlur(imageElement) {
    imageElement.classList.add('no-blur');
}

function goToMainPage() {
    // localStorage에서 guideShown 값을 삭제 (초기화)
    localStorage.removeItem('guideShown');
    window.location.href = '/'; // 메인 페이지 URL로 이동 ("/"는 메인 페이지로 이동하는 경로)
}