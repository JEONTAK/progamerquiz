function toggleMenu() {
    document.body.classList.toggle('menu-open');
    document.querySelector('.content-container').classList.toggle('menu-open');
}

localStorage.removeItem('guideShown');

// 퀴즈 클릭 핸들러 함수
function handleQuizClick(hasUrl, url) {
    if (hasUrl) {
        // URL이 유효한 경우 해당 페이지로 이동
        window.location.href = url;
    } else {
        // URL이 없을 경우 Coming Soon 오버레이 표시
        document.getElementById('coming-soon-overlay').style.display = 'flex';
    }
}

// 오버레이 닫기 함수
function closeOverlay() {
    document.getElementById('coming-soon-overlay').style.display = 'none';
}