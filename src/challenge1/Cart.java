package challenge1;

import java.util.ArrayList;
import java.util.List;


public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                item.addQuantity(quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public int getTotalPrice() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> getItems() {
        return items;
    }
}