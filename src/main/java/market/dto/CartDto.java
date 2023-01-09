package market.dto;
/*
 * CREATE TABLE `p_cart` (
	cartId INT not null PRIMARY KEY AUTO_INCREMENT,
	memberId VARCHAR(10) null, -- 멤버 아이디
	orderNo VARCHAR(50) NOT NULL, -- 주문 번호
	p_id VARCHAR(10) NOT NULL, -- 상품 아이디
	p_name VARCHAR(20) NOT NULL, -- 상품 이름
	p_unitPrice INT NOT NULL, -- 상품 가격
	cnt INT -- 장바구니에 담긴 해당 상품의 개수
);
 * */
public class CartDto {
	private int cartId;
	private String memberId;
	private String orderNo;
	private String productId;
	private String pName;
	private int pUnitPrice;
	private int cnt;

	// 생성자
	public CartDto() {
	}

	// getter setter
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public int getpUnitPrice() {
		return pUnitPrice;
	}
	public void setpUnitPrice(int pUnitPrice) {
		this.pUnitPrice = pUnitPrice;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}
