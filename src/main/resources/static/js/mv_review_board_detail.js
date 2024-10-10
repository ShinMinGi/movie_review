document.addEventListener('DOMContentLoaded', function () {
    // 댓글 작성
    document.getElementById('commentForm').addEventListener('submit', function (e) {
        e.preventDefault();

        const formData = {
            reviewId: document.querySelector('input[name="reviewId"]').value,
            movieId: document.querySelector('input[name="movieId"]').value,
            content: document.getElementById('commentContent').value,
        };

        console.log('Form Data:', formData); // 전송할 데이터 로그

        fetch('/comment/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        })
            .then(response => {
                console.log('Response Status:', response.status);
                if (response.ok) {
                    return response.text(); // JSON 대신 text로 응답을 처리
                } else {
                    throw new Error('응답 상태 코드: ' + response.status);
                }
            })
            .then(data => {
                console.log('Server Response:', data);
                alert('댓글이 성공적으로 작성되었습니다.');
                loadComments(formData.reviewId); // 댓글 목록 다시 로드
                document.getElementById('commentContent').value = ''; // 입력 필드 초기화
            })
            .catch(error => {
                console.error('Error:', error); // 에러 콘솔 출력
                alert('댓글 작성 중 오류가 발생했습니다. 다시 시도해주세요.');
            });
    });

    // 댓글 목록 로드
    function loadComments(reviewId) {
        console.log('Fetching comments for reviewId:', reviewId);
        fetch('/comment/list/' + reviewId)
            .then(response => {
                console.log('Response Status:', response.status);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json(); // JSON 데이터로 변환
            })
            .then(comments => {
                if (!Array.isArray(comments)) {
                    throw new Error('댓글 데이터 형식이 잘못되었습니다.');
                }
                console.log('Comments:', comments);
                const commentList = document.getElementById('commentList');
                commentList.innerHTML = ''; // 기존 목록 초기화

                // 댓글 목록을 렌더링
                comments.forEach(function (comment) {
                    const commentItem = `<li>${comment.userName}: ${comment.content} ${comment.updatedAt}</li>`;
                    commentList.insertAdjacentHTML('beforeend', commentItem);
                });

                // 댓글 개수 업데이트
                document.getElementById('commentCount').textContent = `댓글 ${comments.length}개`;
            })
            .catch(error => {
                console.error('Error:', error);
                alert('댓글 목록을 불러오는 중 오류가 발생했습니다.');
            });
    }

    // 페이지 로드시 댓글 목록 로드
    const reviewId = document.querySelector('input[name="reviewId"]').value;
    loadComments(reviewId);
});
