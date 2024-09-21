
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

//guessedList 입력 Function
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
                value: progamer.latestLeague,
                icon: `/images/league/${progamer.latestLeague}.png`,
                fallbackIcon: '/images/none.png'
            },
            {
                label: 'Team',
                value: progamer.latestTeam,
                icon: `/images/teams/${progamer.latestTeam}.png`,
                fallbackIcon: '/images/none.png'
            },
            {
                label: 'Position',
                value: progamer.position,
                icon: `/images/positions/${progamer.position}.png`,
                fallbackIcon: '/images/none.png'
            },
            {
                label: 'Birth',
                value: progamer.birth,
                icon: `/images/number/number_${progamer.birth.toString().slice(-2)}.png`,
                fallbackIcon: '/images/none.png'
            },
            {
                label: 'League Wins',
                value: progamer.league_win,
                icon: `/images/number/${progamer.league_win}.png`,
                fallbackIcon: '/images/none.png'
            },
            {
                label: 'International Wins',
                value: progamer.intl_win,
                icon: `/images/number/${progamer.intl_win}.png`,
                fallbackIcon: '/images/none.png'
            }
        ];

        // 각 힌트 정보를 아이템으로 구성
        hintData.forEach(hint => {
            const hintItem = document.createElement("div");
            hintItem.classList.add("hint-item");

            // 이미지 추가
            const cover = document.createElement("div");
            cover.classList.add("cover");

            const img = document.createElement("img");
            img.src = hint.icon;  // 해당 힌트의 아이콘 경로
            img.onerror = function () {
                this.src = hint.fallbackIcon;
            };
            img.alt = hint.label;
            cover.appendChild(img);

            // 힌트 값 추가
            const span = document.createElement("span");
            span.textContent = hint.value;

            hintItem.appendChild(cover);
            hintItem.appendChild(span);
            hintRow.appendChild(hintItem);
        });

        hintContainer.appendChild(hintRow);

        // fade-in 애니메이션을 적용하기 위해 짧은 지연 후 fade-in 클래스 추가
        setTimeout(() => {
            hintRow.childNodes.forEach(item => item.classList.add("fade-in"));
        }, index * 200);  // 각 행이 순차적으로 애니메이션되도록 지연 시간 추가
    });
});