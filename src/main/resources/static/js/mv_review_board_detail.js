// 댓글 등록
function submitComment(event) {
    event.preventDefault();

    const reviewId = document.querySelector('.container').getAttribute('data-review-id');

    console.log('Review ID:', reviewId); // 확인용 로그 추가

    const content = document.getElementById('commentContent').value;

    console.log('~~~~~~~~~~~~~~~~~@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Review ID:', reviewId);  // reviewId 값 확인

    // 서버로 전송할 댓글 데이터
    const commentData = {
        reviewId: reviewId,
        content: content
    };
    console.log('Comment Data:', commentData); // 로그로 commentData 확인

    fetch('/comments/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(commentData),
    })
        .then(response => response.json())
        .then(data => {
            loadComments(reviewId);  // 댓글 목록 다시 불러오기
            document.getElementById('commentContent').value = ''; // 입력창 초기화
        })
        .catch(error => console.error('Error:', error));  // 에러 핸들링
}

// 댓글 조회
function loadComments(reviewId) {
    fetch(`/comments/review/${reviewId}`)
        .then(response => response.json())
        .then(comments => {
            const commentList = document.getElementById('commentList');
            commentList.innerHTML = '';
            comments.forEach(comment => {
                const li = document.createElement('li');
                li.innerText = comment.content;
                commentList.appendChild(li);
            });
        })
        .catch(error => console.error('Error:', error));  // 에러 핸들링
}

// 페이지 로드 시 댓글 불러오기
document.addEventListener('DOMContentLoaded', function () {
    const reviewId = document.querySelector('.container').getAttribute('data-review-id');
    loadComments(reviewId);
});
