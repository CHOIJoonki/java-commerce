package challenge2;

import java.util.ArrayList;
import java.util.List;


public class Category {
    private String categoryName;      // 카테고리 이름
    private List<Product> products;   // 카테고리에 속한 상품 목록

    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public Product findProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public boolean hasProduct(String name) {
        return findProductByName(name) != null;
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