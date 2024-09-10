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
function deleteReview() {
    const reviewId = prompt("삭제할 리뷰 ID를 입력해주세요.");
    if (reviewId) {
        fetch(`/reviews/delete/${reviewId}`, {
            method: 'POST'
        })
            .then(response => {
                if (response.ok) {
                    alert('리뷰가 성공적으로 삭제되었습니다.');
                } else {
                    alert('삭제 실패!');
                }
            });
    }
}

