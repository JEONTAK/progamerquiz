
// 가이드 오버레이 보이기
function showGuide() {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'flex';
}

// 가이드 오버레이 닫기
function closeGuide(game) {
    const guideOverlay = document.getElementById('guide-overlay');
    guideOverlay.style.display = 'none';
    const startUrl = `/${game}/pieceofpuzzle/start`;
    // 사용자가 가이드를 닫았으므로, localStorage에 방문 기록을 저장
    localStorage.setItem('guideShown', 'true');
    window.location.href = startUrl;
}


// 페이지가 로드될 때 처음 방문한 경우에만 가이드 보여주기
window.onload = function() {
    // localStorage에 'guideShown' 키가 없는 경우에만 가이드 보여줌
    if (!localStorage.getItem('guideShown')) {
        showGuide();
    }
};
