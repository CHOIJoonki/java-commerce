package step4;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Category> categories = new ArrayList<>();

        Category electronics = new Category("전자제품");
        electronics.addProduct(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 50));
        electronics.addProduct(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30));
        electronics.addProduct(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 15));
        electronics.addProduct(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 100));
        categories.add(electronics);

        Category clothing = new Category("의류");
        clothing.addProduct(new Product("캐시미어 코트", 450000, "고급 캐시미어 소재", 20));
        clothing.addProduct(new Product("청바지", 89000, "슬림핏 데님", 100));
        clothing.addProduct(new Product("운동화", 129000, "편안한 러닝화", 50));
        clothing.addProduct(new Product("패딩 점퍼", 320000, "경량 구스다운", 30));
        categories.add(clothing);

        Category food = new Category("식품");
        food.addProduct(new Product("한우 세트", 180000, "1++ 등급 한우", 10));
        food.addProduct(new Product("유기농 사과", 25000, "충주산 유기농 사과 5kg", 50));
        food.addProduct(new Product("프리미엄 올리브유", 45000, "이탈리아산 엑스트라버진", 40));
        food.addProduct(new Product("견과류 선물세트", 35000, "아몬드, 호두, 캐슈넛", 60));
        categories.add(food);

        CommerceSystem commerceSystem = new CommerceSystem(categories);
        commerceSystem.start();
    }
}