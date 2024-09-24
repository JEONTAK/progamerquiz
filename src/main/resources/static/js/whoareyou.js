
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
    // 이미지 요소 및 입력 필드 가져오기
    const playerImage = document.getElementById("player-image");
    const playerInput = document.getElementById("player-input");

    // TryStatus 값 확인 후 정답일 경우 이미지 블러 해제 및 배경색 변경
    if (tryStatus === 1) {
        // 이미지 블러 해제
        playerImage.style.filter = "none";

        // 이미지 배경색을 초록색으로 변경
        playerImage.style.backgroundColor = "green";

        // 텍스트 필드 수정 불가능하게 설정
        playerInput.disabled = true;
    }
});

document.addEventListener("DOMContentLoaded", function() {
    const hintContainer = document.getElementById("hintContainer");

    // guessedList에서 데이터를 가져와 동적으로 추가
    guessedList.forEach((progamer, index) => {
        // 힌트 행 생성
        const hintRow = document.createElement("div");
        hintRow.classList.add("hint-row");

        // 각 힌트 정보를 아이템으로 추가
        const hintData = [
            {
                label: 'League',
                value: progamer.recentLeague,
                answerValue: answer.recentLeague,  // answer 값 추가
                icon: `/images/league/${progamer.recentLeague}.png`,
                fallbackIcon: '/images/none.png'
            },
            {
                label: 'Team',
                value: progamer.latestTeam,
                answerValue: answer.latestTeam,  // answer 값 추가
                icon: `/images/team/${progamer.teamid}.png`,
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
                fallbackIcon: '/images/none.png'
            },
            {
                label: 'League Wins',
                value: progamer.league_win != null ? progamer.league_win : 0,
                answerValue: answer.league_win != null ? answer.league_win : 0,  // answer 값 추가
                icon: `/images/number/number_${progamer.league_win != null ? progamer.league_win : 0}.png`,
                fallbackIcon: '/images/none.png'
            },
            {
                label: 'International Wins',
                value: progamer.intl_win != null ? progamer.intl_win : 0,
                answerValue: answer.intl_win != null ? answer.intl_win : 0,  // answer 값 추가
                icon: `/images/number/number_${progamer.intl_win != null ? progamer.intl_win : 0}.png`,
                fallbackIcon: '/images/none.png'
            }
        ];

        // 각 힌트 정보를 아이템으로 구성
        hintData.forEach(hint => {
            const hintItem = document.createElement("div");
            hintItem.classList.add("hint-item");
            hintItem.style.display = "flex";   // 반드시 보이도록 설정
            hintItem.style.opacity = "1";      // 투명도를 명확히 설정

            // 이미지 추가
            const cover = document.createElement("div");
            cover.classList.add("cover");

            const img = document.createElement("img");

            img.src = hint.icon;  // 해당 힌트의 아이콘 경로
            /*img.onerror = function () {
                this.src = hint.fallbackIcon;
            };*/
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
            span.textContent = hint.value;

            // answer의 값과 동일한 경우 배경색을 초록색으로 설정
            if (hint.value === hint.answerValue) {
                cover.style.backgroundColor = "green";  // 이미지 배경색을 초록색으로 변경
            }

            hintItem.appendChild(cover);
            hintItem.appendChild(span);
            hintRow.appendChild(hintItem);  // hintItem을 hintRow에 추가
        });

        hintContainer.appendChild(hintRow);

        // fade-in 애니메이션을 적용하기 위해 짧은 지연 후 fade-in 클래스 추가
        setTimeout(() => {
            hintRow.classList.add("fade-in");  // hintRow에 fade-in 적용
            hintRow.childNodes.forEach(item => {
                item.style.display = "flex";   // 모든 자식 요소가 보이게 설정
                item.style.opacity = "1";      // 모든 자식 요소의 투명도 설정
            });
        }, index * 200);  // 각 행이 순차적으로 애니메이션되도록 지연 시간 추가
    });
});

function removeBlur(imageElement) {
    imageElement.classList.add('no-blur');
}
