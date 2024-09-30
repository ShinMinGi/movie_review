function updateTotalPrice() {
    const quantity = document.getElementById("quantity").value;
    const unitPrice = 7000; // 개당 가격
    const deliveryFee = 3000; // 배송비
    const totalAmount = (unitPrice * quantity) + deliveryFee;

    // 총 결제 금액을 표시
    document.getElementById("totalPrice").innerText = totalAmount + "원";

    // 숨겨진 입력 필드에 총 결제 금액 저장
    document.getElementById("hiddenTotalPrice").value = totalAmount;
}


function kg_request_pay() {
    IMP.init("imp33705720"); // Iamport 고유 코드 초기화

    const quantity = parseInt(document.getElementById('quantity').value);
    const unitPrice = 7000;
    const deliveryFee = 3000;
    const totalPrice = (unitPrice * quantity) + deliveryFee;

    const orderId = 'order' + new Date().getTime();
    const orderName = document.getElementById('orderName').value;
    const orderEmail = document.getElementById('orderEmail').value;
    const deliveryAddress = document.getElementById('deliveryAddress').value;
    const phoneNumber = document.getElementById('phoneNumber').value;

    if (!orderName || !orderEmail || !deliveryAddress || !phoneNumber) {
        alert("필수 입력 항목을 모두 채워주세요.");
        return;
    }

    IMP.request_pay({
        pg: "html5_inicis", // KG 이니시스 결제
        pay_method: "card", // 카드 결제
        amount: totalPrice,
        name: "해리포터 랜덤뱃지 ver.3",
        merchant_uid: orderId,
        buyer_name: orderName,
        buyer_email: orderEmail,
        buyer_tel: phoneNumber,
        buyer_addr: deliveryAddress,
    }, function (rsp) {
        if (rsp.success) {
            fetch('/store/order/details', { // 수정된 부분
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    orderId: orderId,
                    orderName: orderName,
                    orderEmail: orderEmail,
                    deliveryAddress: deliveryAddress,
                    phoneNumber: phoneNumber,
                    quantity: quantity,
                    deliveryFee: deliveryFee,
                    amount: totalPrice
                })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("서버 응답에 실패했습니다.");
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        alert("결제가 성공적으로 완료되었습니다.");
                        // orderId를 포함한 URL로 리다이렉트
                        window.location.href = `/store/order/details/${data.orderId}`; // 수정된 부분
                    } else {
                        alert("주문 처리에 실패했습니다.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("서버 요청 중 오류가 발생했습니다.");
                });
        } else {
            alert("오류 메시지: " + rsp.error_msg);
        }
    });
}


// // 카카오페이
function ka_request_pay() {
    IMP.init("imp33705720"); // Iamport 고유 코드 초기화

    const quantity = parseInt(document.getElementById('quantity').value);
    const unitPrice = 7000;
    const deliveryFee = 3000;
    const totalPrice = (unitPrice * quantity) + deliveryFee;

    const orderId = 'order' + new Date().getTime();
    const orderName = document.getElementById('orderName').value;
    const orderEmail = document.getElementById('orderEmail').value;
    const deliveryAddress = document.getElementById('deliveryAddress').value;
    const phoneNumber = document.getElementById('phoneNumber').value;

    if (!orderName || !orderEmail || !deliveryAddress || !phoneNumber) {
        alert("필수 입력 항목을 모두 채워주세요.");
        return;
    }

    IMP.request_pay({
        pg: "kakaopay",
        pay_method: "card",
        amount: totalPrice,
        name: "해리포터 랜덤뱃지 ver.3",
        merchant_uid: orderId,
        buyer_name: orderName,
        buyer_email: orderEmail,
        buyer_tel: phoneNumber,
        buyer_addr: deliveryAddress,
    }, function (rsp) {
        if (rsp.success) {
            fetch('/store/order/details', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    orderId: orderId,
                    orderName: orderName,
                    orderEmail: orderEmail,
                    deliveryAddress: deliveryAddress,
                    phoneNumber: phoneNumber,
                    quantity: quantity,
                    deliveryFee: deliveryFee,
                    amount: totalPrice
                })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("서버 응답에 실패했습니다.");
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        alert("결제가 완료되었습니다.");
                        window.location.href = `/store/order/details/${data.orderId}`;
                    } else {
                        alert("주문 처리에 실패했습니다.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("서버 요청 중 오류가 발생했습니다.");
                });
        } else {
            alert("오류 메시지: " + rsp.error_msg);
        }
    });
}

// submitOrder 함수도 마찬가지로 URL을 수정
function submitOrder() {
    const form = document.getElementById("orderForm");

    const orderData = {
        orderName: document.getElementById("orderName").value,
        orderEmail: document.getElementById("orderEmail").value,
        phoneNumber: document.getElementById("phoneNumber").value,
        deliveryAddress: document.getElementById("deliveryAddress").value,
        quantity: document.getElementById("quantity").value,
        deliveryFee: document.getElementById("deliveryFee").value,
        amount: document.getElementById("hiddenTotalPrice").value // 숨겨진 총 결제 금액
    };

    fetch("/store/order/details", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(orderData),
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // orderId를 포함한 URL로 리다이렉트
                window.location.href = `/store/order/details/${data.orderId}`;
            } else {
                alert("결제에 실패했습니다. 다시 시도해주세요.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}