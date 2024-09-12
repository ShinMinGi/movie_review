// 리뷰 수정
function editReview() {
    const reviewId = prompt("수정할 리뷰 ID를 입력해주세요.");
    if (reviewId) {
        const reviewData = {
            writer: document.getElementById('writer_id').value,
            title: document.getElementById('title').value,
            content: document.getElementById('edit-body').value
        };

        fetch(`/reviews/update/${reviewId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(reviewData)
        })
            .then(response => {
                if (response.ok) {
                    alert('리뷰가 성공적으로 수정되었습니다.');
                } else {
                    alert('수정 실패!');
                }
            });
    }
}

// 리뷰 삭제
// 리뷰 삭제
function deleteReview() {
    const reviewId = prompt("삭제할 리뷰 ID를 입력해주세요.");
    if (reviewId) {
        fetch(`/remove/${Id}`, {
            method: 'DELETE', // DELETE 메소드로 변경
            headers: {
                'Content-Type': 'application/json' // 요청 타입 설정
            }
        })
            .then(response => {
                if (response.ok) {
                    alert('리뷰가 성공적으로 삭제되었습니다.');
                    window.location.reload(); // 페이지를 새로고침하여 삭제 후 리스트를 갱신
                } else {
                    alert('삭제 실패!');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('오류 발생! 다시 시도해 주세요.');
            });
    }
}

