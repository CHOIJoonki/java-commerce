package challenge3;


public class Customer {
    // 등급 기준 금액 상수
    private static final int SILVER_THRESHOLD = 100000;
    private static final int GOLD_THRESHOLD = 300000;
    private static final int PLATINUM_THRESHOLD = 500000;

    private String name;
    private String email;
    private CustomerGrade grade;
    private int totalOrderAmount;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.grade = CustomerGrade.BRONZE;
        this.totalOrderAmount = 0;
    }

    // 주문 금액 추가 및 등급 자동 업데이트
    public void addOrderAmount(int amount) {
        this.totalOrderAmount += amount;
        updateGrade();
    }

    // 누적 금액에 따른 등급 자동 변경
    private void updateGrade() {
        if (totalOrderAmount >= PLATINUM_THRESHOLD) {
            this.grade = CustomerGrade.PLATINUM;
        } else if (totalOrderAmount >= GOLD_THRESHOLD) {
            this.grade = CustomerGrade.GOLD;
        } else if (totalOrderAmount >= SILVER_THRESHOLD) {
            this.grade = CustomerGrade.SILVER;
        } else {
            this.grade = CustomerGrade.BRONZE;
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public CustomerGrade getGrade() {
        return grade;
    }

    public int getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

}