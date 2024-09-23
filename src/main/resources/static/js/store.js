function kg_request_pay() {
    const quantity = parseInt(document.getElementById('quantity').value); // 수량 가져오기
    const unitPrice = 7000; // 단가 7000원
    const shippingCost = 3000; // 배송비 3000원
    const totalPrice = (unitPrice * quantity) + shippingCost; // (unitPrice * quantity) + shippingCost 셈입니다
    const orderId = 'order' + new Date().getTime();     // 현재시간을 밀리초로 가져온다 이를 통해 고유한 주문ID - orderId 를 생성한다
    const userId = document.getElementById('userId').value;  // 로그인한 사용자 ID를 입력 필드에서 값을 가져온다

    fetch('/payment/kg', {                                      // /payment/kg에 Post요청을 보낸다
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'                                                      // JSON 형식의 데이터를 전송한다
        },
        body: JSON.stringify({ amount: totalPrice, orderId: orderId, userId: userId })  // 결제정보를 JSON 문자열로 변환
    })                                                                                        // 이 문자열엔 (금액 ,주문ID, 사용자 ID)가 포함
        .then(response => response.json())     // 서버로부터 받은 응답 JSON 으로 변환
        .then(data => {
            if (data.success) {
                alert("결제가 성공했습니다!");
            } else {
                alert("결제에 실패했습니다.");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("결제 처리 중 오류가 발생했습니다.");
        });                                         // JSON 으로 받은 값을 응답한다
}

function ka_request_pay() {
    const quantity = parseInt(document.getElementById('quantity').value);
    const unitPrice = 7000;
    const shippingCost = 3000;
    const totalPrice = (unitPrice * quantity) + shippingCost;
    const orderId = 'order' + new Date().getTime();
    const userId = document.getElementById('userId').value;

    fetch('/payment/ka_request', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ amount: totalPrice, orderId: orderId, userId: userId })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("카카오페이 결제가 성공했습니다!");
            } else {
                alert("카카오페이 결제에 실패했습니다.");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("카카오페이 결제 처리 중 오류가 발생했습니다.");
        });
}
