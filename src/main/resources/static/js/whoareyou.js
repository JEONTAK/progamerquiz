
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