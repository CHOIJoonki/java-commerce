package challenge1;

import java.util.ArrayList;
import java.util.List;


public class Category {

    private String categoryName;
    private List<Product> products;

    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
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