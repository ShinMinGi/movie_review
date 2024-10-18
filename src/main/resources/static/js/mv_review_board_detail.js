document.addEventListener('DOMContentLoaded', function () {
    // 댓글 작성
    document.getElementById('commentForm').addEventListener('submit', function (e) {
        e.preventDefault();

        const formData = {
            reviewId: document.querySelector('input[name="reviewId"]').value,
            movieId: document.querySelector('input[name="movieId"]').value,
            content: document.getElementById('commentContent').value,
        };

        if (!formData.content.trim()) {
            alert('댓글 내용을 입력해주세요.');
            return;
        }

        console.log('Form Data:', formData); // 전송할 데이터 로그

        fetch('/comment/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        })
            .then(response => response.ok ? response.text() : Promise.reject(response.statusText))
            .then(data => {
                console.log('Server Response:', data);
                alert('댓글이 작성되었습니다.');
                loadComments(formData.reviewId); // 댓글 목록 다시 로드
                document.getElementById('commentContent').value = ''; // 입력 필드 초기화
            })
            .catch(error => {
                console.error('Error:', error);
                alert('댓글 작성 중 오류가 발생했습니다. 다시 시도해주세요.');
            });
    });
});


// 댓글 목록 로드
function loadComments(reviewId, page = 1, pageSize = 5) {
    fetch(`/comment/list/${reviewId}?page=${page}&pageSize=${pageSize}`)
        .then(response => {
            console.log('Response Status:', response.status);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const comments = data.comments; // CommentDto 리스트
            const totalComments = data.total;
            const currentPage = data.currentPage;
            const totalPages = Math.ceil(totalComments / pageSize);

            const commentList = document.getElementById('commentList');
            commentList.innerHTML = ''; // 기존 목록 초기화

            // 댓글 목록을 렌더링
            comments.forEach(function (comment) {
                const formattedDate = formatDate(comment.createdAt);
                const isOwner = comment.showDropdown;

                // 댓글 항목 생성
                const commentItem = `
                    <li style="list-style-type: none;" data-id="${comment.id}">
                        <div style="font-weight: bold;">${comment.userName}</div>
                        <div>${comment.content}</div>
                        <div style="color: #999; font-size: 12px;">${formattedDate}</div>
                        ${isOwner ? `
                        <div class="dropdown" style="position: absolute; right: 10px; top: 10px; ">
                            <button class="dropbtn">⋮</button>
                            <div class="dropdown-content">
                                <button onclick="updateComment(${comment.id})">수정</button>
                                <button onclick="deleteComment(${comment.id})">삭제</button>
                            </div>
                        </div>
                        ` : ''}
                        <div>
                            <button class="replyButton" onclick="showReplyForm(${comment.id})">답글</button>
                            
                          <div class="replyForm" style="display: none; margin-top: 10px;">
                              
                                <hr style="border: none; border-top: 1px solid rgba(0,0,0,0.6); margin-bottom: 15px;">
                            
                                <div style="display: flex; align-items: flex-start;">
                                    <!-- 프로필 이미지 -->
                                    <div style="margin-right: 10px;">
                                        <img src="https://via.placeholder.com/50" alt="user profile" style="border-radius: 50%;">
                                    </div>
                                    
                                    <!-- 대댓글 입력란 -->
                                    <div style="flex-grow: 1;">
                                        <textarea placeholder="댓글을 입력해주세요" class="replyContent" style="width: 100%; height: 60px; padding: 10px; border: 1px solid #ddd; border-radius: 4px; resize: none;"></textarea>
                                        
                                        <!-- 등록 버튼을 오른쪽에 배치 -->
                                        <div style="margin-top: 10px; display: flex; justify-content: flex-end;">
                                            <button type="button" onclick="submitReply(${comment.id})" style="background-color: #4CAF50; color: white; padding: 8px 16px; border: none; border-radius: 4px;">
                                                등록
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <ul class="replyList" style="list-style-type: none; padding-left: 20px;"></ul>
                        </div>
                    </li>`;

                // 댓글 항목 추가
                commentList.insertAdjacentHTML('beforeend', commentItem);

                // 이미 대댓글이 포함되어 있는 경우에는 추가 로드를 하지 않도록 처리
                if (!comment.replies || comment.replies.length === 0) {
                    loadReplies(comment.id); // 대댓글 로드
                } else {
                    // 대댓글을 바로 렌더링
                    const replyList = commentList.querySelector(`li[data-id="${comment.id}"] .replyList`);
                    comment.replies.forEach(reply => {
                        const replyItem = `
                            <li style="list-style-type: none;" data-id="${reply.id}">
                                <div style="font-weight: bold;">${reply.userName}</div>
                                <div>${reply.content}</div>
                                <div style="color: #999; font-size: 12px;">${formatDate(reply.createdAt)}</div>
                            </li>`;
                        replyList.insertAdjacentHTML('beforeend', replyItem);
                    });
                }
            });

            // 댓글 개수 업데이트
            document.getElementById('commentCount').textContent = `댓글 ${totalComments}개`;

            // 페이지네이션 처리
            const pagination = document.querySelector('.pagination');
            pagination.innerHTML = ''; // 기존 페이지네이션 초기화
            if (currentPage > 1) {
                pagination.innerHTML += `<li class="page-item"><a class="page-link" href="#" onclick="loadComments(${reviewId}, ${currentPage - 1}, ${pageSize})">이전</a></li>`;
            }
            for (let i = 1; i <= totalPages; i++) {
                pagination.innerHTML += `<li class="page-item ${currentPage === i ? 'active' : ''}"><a class="page-link" href="#" onclick="loadComments(${reviewId}, ${i}, ${pageSize})">${i}</a></li>`;
            }
            if (currentPage < totalPages) {
                pagination.innerHTML += `<li class="page-item"><a class="page-link" href="#" onclick="loadComments(${reviewId}, ${currentPage + 1}, ${pageSize})">다음</a></li>`;
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('댓글 목록을 불러오는 중 오류가 발생했습니다.');
        });
}




// 대댓글 입력 폼을 표시하는 함수
function showReplyForm(commentId) {
    const replyForm = document.querySelector(`li[data-id='${commentId}'] .replyForm`);
    replyForm.style.display = replyForm.style.display === 'none' || replyForm.style.display === '' ? 'block' : 'none';
}
// 대댓글 작성 후 서버에 전송하는 함수
function submitReply(parentId) {
    const replyContent = document.querySelector(`li[data-id='${parentId}'] .replyContent`).value;
    const reviewId = document.querySelector('input[name="reviewId"]').value;
    const movieId = document.querySelector('input[name="movieId"]').value;

    if (!replyContent.trim()) {
        alert('대댓글 내용을 입력해주세요.');
        return;
    }

    fetch('/comment/reply/add', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            reviewId: reviewId,
            movieId: movieId,
            parentId: parentId,  // 대댓글임을 나타내는 parentId
            content: replyContent
        })
    })
        .then(response => response.ok ? response.json() : Promise.reject('대댓글 등록에 실패했습니다.'))
        .then(data => {
            loadReplies(parentId); // 부모 댓글 아래 대댓글을 로드
            document.querySelector(`li[data-id='${parentId}'] .replyForm`).style.display = 'none'; // 대댓글 입력폼 숨기기
            document.querySelector(`li[data-id='${parentId}'] .replyContent`).value = ''; // 입력 필드 초기화
        })
        .catch(error => {
            console.error('Error:', error);
            alert('대댓글 등록 중 오류가 발생했습니다: ' + error);
        });
}

// 대댓글 로드 함수
function loadReplies(commentId) {
    const replyList = document.querySelector(`li[data-id="${commentId}"] .replyList`);
    replyList.innerHTML = ''; // 기존 대댓글 초기화

    fetch(`/comment/replies/${commentId}`)
        .then(response => response.json())
        .then(replies => {
            replies.forEach(reply => {
                const replyItem = `
                    <li style="list-style-type: none;" data-id="${reply.id}">
                        <div style="font-weight: bold;">${reply.userName}</div>
                        <div>${reply.content}</div>
                        <div style="color: #999; font-size: 12px;">${formatDate(reply.createdAt)}</div>
                    </li>`;
                replyList.insertAdjacentHTML('beforeend', replyItem);
            });
        })
        .catch(error => console.error('Error loading replies:', error));
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
    const commentItem = document.querySelector(`li[data-id='${commentId}']`);
    const contentDiv = commentItem.querySelector('div:nth-of-type(2)'); // 댓글 내용이 있는 div
    const currentContent = contentDiv.textContent; // 현재 댓글 내용을 가져옵니다.

    // 수정할 내용을 입력받기 위한 텍스트 영역 생성
    const input = document.createElement('textarea');
    input.value = currentContent; // 기존 댓글 내용을 초기값으로 설정
    const saveButton = document.createElement('button');
    saveButton.textContent = '저장';

    // 저장 버튼 클릭 시 처리
    saveButton.addEventListener('click', function () {
        const updatedContent = input.value; // 입력된 수정 내용 가져오기

        // 서버에 수정된 내용 전송
        fetch(`/comment/update/${commentId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({content: updatedContent}) // JSON 형식으로 수정된 내용 전송
        })

            .then(response => response.text())
            .then(data => {
                // 댓글 내용 업데이트
                contentDiv.textContent = updatedContent; // 수정된 내용으로 업데이트
                // 입력 영역과 버튼 제거
                input.remove();
                saveButton.remove();
            })
            .catch(error => console.error('Error:', error));
    });

    // 댓글 내용 대신 텍스트 영역과 저장 버튼 추가
    contentDiv.innerHTML = ''; // 댓글 내용을 지우고
    contentDiv.appendChild(input); // 텍스트 영역 추가
    contentDiv.appendChild(saveButton); // 저장 버튼 추가
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