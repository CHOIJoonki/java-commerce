package challenge3;


public enum CustomerGrade {
    BRONZE("BRONZE", 0),
    SILVER("SILVER", 5),
    GOLD("GOLD", 10),
    PLATINUM("PLATINUM", 15);

    private final String name;
    private final int discountRate;

    CustomerGrade(String name, int discountRate) {
        this.name = name;
        this.discountRate = discountRate;
    }

    public String getName() {
        return name;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    // 할인 금액 계산
    public int calculateDiscount(int price) {
        return price * discountRate / 100;
    }

    // 할인 적용된 최종 금액 계산
    public int applyDiscount(int price) {
        return price - calculateDiscount(price);
    }
}