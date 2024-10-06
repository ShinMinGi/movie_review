// 댓글을 제출하는 함수
function submitComment() {
    event.preventDefault(); // 기본 폼 제출 방지

    console.log('submitComment 함수가 호출되었습니다.'); // 함수 호출 로그 추가


    // HTML에서 reviewId와 movieId 값을 가져옴
    const commentContainer = document.querySelector('.comment-container');
    const reviewId = commentContainer.getAttribute('data-review-id');
    const movieId = commentContainer.getAttribute('data-movie-id');

    // 사용자가 입력한 댓글 내용 가져오기
    const content = document.getElementById('commentContent').value;

    // 값 확인 (디버깅용)
    console.log('Review ID:', reviewId);
    console.log('Movie ID:', movieId);
    console.log('Content:', content);

    // 전송할 댓글 데이터 객체 생성
    const commentData = {
        reviewId: reviewId, // reviewId 값이 int이므로 변환
        movieId: movieId,   // movieId 값도 int로 변환
        content: content
    };

    // 댓글을 서버로 전송 (AJAX 사용)
    fetch('/comments/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(commentData),
    })
        .then(response => {
            if (!response.ok) {
                const errorMsg = `Error: ${response.status} - ${response.statusText}`;
                throw new Error(errorMsg);
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            addCommentToUI(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });

}

// 서버에서 받은 댓글을 UI에 추가하는 함수
function addCommentToUI(comment) {
    const commentList = document.getElementById('commentList');
    const newComment = document.createElement('li');
    newComment.textContent = `${comment.userName}: ${comment.content}`; // 댓글 작성자와 내용을 리스트에 추가
    commentList.appendChild(newComment);

    // 댓글 수 업데이트
    const commentCount = document.getElementById('commentCount');
    const currentCount = parseInt(commentCount.textContent.match(/\d+/)[0]);
    commentCount.textContent = `댓글 ${currentCount + 1}개`;

    // 댓글 작성 후 입력 폼 초기화
    document.getElementById('commentContent').value = '';
}
