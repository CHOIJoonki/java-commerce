package challenge3;


public class CartItem {
    private Product product;  // 상품
    private int quantity;     // 수량

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}