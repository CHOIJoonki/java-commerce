package challenge3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Category {
    private String categoryName;      // 카테고리 이름
    private List<Product> products;   // 카테고리에 속한 상품 목록

    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.products = new ArrayList<>();
    }

    //상품 추가
    public void addProduct(Product product) {
        products.add(product);
    }
    //상품 삭제
    public void removeProduct(Product product) {
        products.remove(product);
    }

    //상품 검색
    public Product findProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    //중복 상품명 확인
    public boolean hasProduct(String name) {
        return products.stream()
                .anyMatch(product -> product.getName().equals(name));
    }

    //특정 가격 이하 상품 필터링
    public List<Product> filterByMaxPrice(int maxPrice) {
        return products.stream()
                .filter(product -> product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    //가격 초과 상품 필터링
    public List<Product> filterByMinPrice(int minPrice) {
        return products.stream()
                .filter(product -> product.getPrice() > minPrice)
                .collect(Collectors.toList());
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}