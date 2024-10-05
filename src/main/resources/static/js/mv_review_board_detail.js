function submitComment(event) {
    event.preventDefault();
    console.log("submitComment called");

    const commentContent = document.getElementById("commentContent").value;
    const reviewId = document.querySelector(".container").dataset.reviewId; // 데이터 속성에서 값 가져오기
    console.log("Review ID from data attribute:", reviewId);

    // reviewId가 null인지 체크
    if (reviewId === 'null' || reviewId === undefined || reviewId === null) {
        console.error("Review ID is null or undefined. Check the HTML element.");
        return; // 에러 처리
    }

    const commentDto = {
        content: commentContent,
        reviewId: reviewId, // reviewId를 문자열로 그대로 설정
    };

    console.log("Comment DTO:", commentDto);

    fetch("/comments/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(commentDto),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("댓글 등록 실패: " + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log("댓글 등록 성공:", data);
        })
        .catch(error => {
            console.error("에러 발생:", error);
        });
}







// 댓글 조회
// 댓글 조회
function loadComments(reviewId) {
    if (!reviewId) {
        console.error('Review ID is undefined or null');
        return; // reviewId가 없으면 함수 종료
    }

    fetch(`/comments/getComments/${reviewId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
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
    const reviewId = document.querySelector('.container').dataset.reviewId; // data-review-id에서 reviewId 가져오기
    loadComments(reviewId);
});


// 페이지 로드 시 댓글 불러오기
document.addEventListener('DOMContentLoaded', function () {
    const reviewId = document.querySelector('.container').getAttribute('data-review-id');
    loadComments(reviewId);
});
