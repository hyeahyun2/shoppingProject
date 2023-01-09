package market.dto;
/*
 * CREATE TABLE `p_cart` (
	cartId INT not null PRIMARY KEY AUTO_INCREMENT,
	memberId VARCHAR(10) null, -- ��� ���̵�
	orderNo VARCHAR(50) NOT NULL, -- �ֹ� ��ȣ
	p_id VARCHAR(10) NOT NULL, -- ��ǰ ���̵�
	p_name VARCHAR(20) NOT NULL, -- ��ǰ �̸�
	p_unitPrice INT NOT NULL, -- ��ǰ ����
	cnt INT -- ��ٱ��Ͽ� ��� �ش� ��ǰ�� ����
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

	// ������
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
