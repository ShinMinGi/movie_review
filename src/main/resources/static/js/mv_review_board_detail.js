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
                alert('댓글이 작성되었습니다.');
                loadComments(formData.reviewId); // 댓글 목록 다시 로드
                document.getElementById('commentContent').value = ''; // 입력 필드 초기화
            })
            .catch(error => {
                console.error('Error:', error); // 에러 콘솔 출력
                alert('댓글 작성 중 오류가 발생했습니다. 다시 시도해주세요.');
            });
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
                    const formattedDate = formatDate(comment.createdAt);
                    const isOwner = comment.userId === currentUserId; // 댓글 작성자 여부 확인
                    const commentItem = `
                     <li style="list-style-type: none;">
                        <div style="font-weight: bold;">${comment.userName}</div>
                        <div>${comment.content}</div>
                        <div style="color: #999; font-size: 12px;">${formattedDate}</div>
                        
                       <div class="dropdown" style="position: absolute; right: 10px; top: 10px;">
                            <button class="dropbtn">⋮</button>
                            <div class="dropdown-content">
                            
                                <button onclick="updateComment(${comment.id})">수정</button>
                                <button onclick="deleteComment(${comment.id})">삭제</button>
                            </div>
                        </div>
                    </li>`;

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

// 날짜를 "YYYY.MM.DD. HH:MM" 형식으로 변환하는 함수
    function formatDate(isoString) {
        const date = new Date(isoString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // 월을 2자리로 맞추기
        const day = String(date.getDate()).padStart(2, '0'); // 일을 2자리로 맞추기
        const hours = String(date.getHours()).padStart(2, '0'); // 시간을 2자리로 맞추기
        const minutes = String(date.getMinutes()).padStart(2, '0'); // 분을 2자리로 맞추기
        return `${year}.${month}.${day}. ${hours}:${minutes}`;
    }



// 댓글 수정
function updateComment(commentId) {
    const content = prompt("수정할 댓글 내용을 입력하세요:");

    if (content) {
        fetch(`/comment/update/${commentId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                content: content
            })
        })
            .then(response => response.text())
            .then(data => {
                alert(data);
                // 수정 후 페이지를 새로고침하여 변경 사항을 반영
                location.reload();
            })
            .catch(error => console.error('Error:', error));
    }
}

// 댓글 삭제
function deleteComment(commentId) {
    if (confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
        fetch(`/comment/delete/${commentId}`, {
            method: 'POST'
        })
            .then(response => response.text())
            .then(data => {
                alert(data);
                // 삭제 후 페이지를 새로고침하여 변경 사항을 반영
                location.reload();
            })
            .catch(error => console.error('Error:', error));
    }
}
