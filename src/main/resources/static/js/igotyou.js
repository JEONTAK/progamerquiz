function goToMainPage() {
    // localStorage에서 guideShown 값을 삭제 (초기화)
    localStorage.removeItem('guideShown');
    window.location.href = '/'; // 메인 페이지 URL로 이동 ("/"는 메인 페이지로 이동하는 경로)
}

// 가이드 오버레이 보이기
function showGuide() {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'flex';
}

// 가이드 오버레이 닫기
function closeGuide(game) {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'none';
    const startUrl = `/${game}/igotyou/start`;
    window.location.href = startUrl;
}

// 페이지 로드 시 가이드 보여주기
window.onload = function () {
    showGuide();
};