package challenge3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 장바구니를 관리하는 클래스
 */
public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    // 장바구니에 상품 추가
    public void addItem(Product product, int quantity) {
        // 스트림을 활용한 기존 상품 확인
        items.stream()
                .filter(item -> item.getProduct().getName().equals(product.getName()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.addQuantity(quantity),
                        () -> items.add(new CartItem(product, quantity))
                );
    }

    // 스트림을 활용한 특정 상품 제거
    public void removeItem(String productName) {
        items = items.stream()
                .filter(item -> !item.getProduct().getName().equals(productName))
                .collect(Collectors.toList());
    }

    // 스트림을 활용한 총 금액 계산
    public int getTotalPrice() {
        return items.stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();
    }

    // 장바구니 비우기
    public void clear() {
        items.clear();
    }

    // 장바구니가 비어있는지 확인
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Getter
    public List<CartItem> getItems() {
        return items;
    }
}