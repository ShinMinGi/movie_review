function updateTotalAmount() {
    const quantity = document.getElementById('quantity').value;
    const price = 7000; // 개당 가격
    const deliveryFee = 3000; // 배송비
    const totalAmount = (price * quantity) + deliveryFee;

    // 총 상품 금액 업데이트
    document.getElementById('totalAmount').innerText = totalAmount.toLocaleString() + '원';
    document.getElementById('hiddenTotalAmount').value = totalAmount;
}