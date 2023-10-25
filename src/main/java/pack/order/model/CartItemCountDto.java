package pack.order.model;

public class CartItemCountDto {
    private int cartItemId;
    private int cartCount; // 변수명을 orderCount에서 cartCount로 수정

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCartCount() { // 메소드명을 getOrderCount()에서 getCartCount()로 수정
        return cartCount;
    }

    public void setCartCount(int cartCount) { // 메소드명을 setOrderCount()에서 setCartCount()로 수정
        this.cartCount = cartCount;
    }
}
