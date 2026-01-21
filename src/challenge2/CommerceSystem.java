package challenge2;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class CommerceSystem {
    private List<Category> categories;
    private Cart cart;
    private Scanner scanner;
    private DecimalFormat formatter;

    public CommerceSystem(List<Category> categories) {
        this.categories = categories;
        this.cart = new Cart();
        this.scanner = new Scanner(System.in);
        this.formatter = new DecimalFormat("#,###");
    }

    public void start() {
        while (true) {
            printMainMenu();

            int maxChoice = cart.isEmpty() ? categories.size() : 5;
            int choice = getValidInput(0, maxChoice);

            if (choice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                break;
            } else if (choice >= 1 && choice <= categories.size()) {
                Category selectedCategory = categories.get(choice - 1);
                showCategoryMenu(selectedCategory);
            } else if (choice == 4 && !cart.isEmpty()) {
                showCartAndOrder();
            } else if (choice == 5 && !cart.isEmpty()) {
                cancelOrder();
            }
        }
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.println("0. 종료      | 프로그램 종료");

        if (!cart.isEmpty()) {
            System.out.println();
            System.out.println("[ 주문 관리 ]");
            System.out.println("4. 장바구니 확인    | 장바구니를 확인 후 주문합니다.");
            System.out.println("5. 주문 취소       | 진행중인 주문을 취소합니다.");
        }
    }

    private void showCategoryMenu(Category category) {
        while (true) {
            System.out.println();
            System.out.println("[ " + category.getCategoryName() + " 카테고리 ]");

            List<Product> products = category.getProducts();
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                String formattedPrice = String.format("%,10d", product.getPrice());
                System.out.println((i + 1) + ". " + String.format("%-14s", product.getName())
                        + " |" + formattedPrice + "원 | " + product.getDescription()
                        + " | 재고: " + product.getStock() + "개");
            }
            System.out.println("0. 뒤로가기");

            int choice = getValidInput(0, products.size());

            if (choice == 0) {
                break;
            } else if (choice >= 1 && choice <= products.size()) {
                Product selected = products.get(choice - 1);
                showProductDetail(selected);
            }
        }
    }

    private void showProductDetail(Product product) {
        System.out.println();
        System.out.println("\"" + product.getName() + " | "
                + formatter.format(product.getPrice()) + "원 | "
                + product.getDescription() + "\"");
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");

        int choice = getValidInput(1, 2);

        if (choice == 1) {
            if (product.getStock() <= 0) {
                System.out.println("재고가 부족합니다.");
                return;
            }
            cart.addItem(product, 1);
            System.out.println();
            System.out.println(product.getName() + "가 장바구니에 추가되었습니다.");
        }
    }

    private void showCartAndOrder() {
        System.out.println();
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println();
        System.out.println("[ 장바구니 내역 ]");

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            System.out.println(product.getName() + " | "
                    + formatter.format(product.getPrice()) + "원 | "
                    + product.getDescription() + " | 수량: " + item.getQuantity() + "개");
        }

        System.out.println();
        System.out.println("[ 총 주문 금액 ]");
        System.out.println(formatter.format(cart.getTotalPrice()) + "원");
        System.out.println();
        System.out.println("1. 주문 확정      2. 메인으로 돌아가기");

        int choice = getValidInput(1, 2);

        if (choice == 1) {
            processOrder();
        }
    }

    private void processOrder() {
        int totalPrice = cart.getTotalPrice();

        System.out.println();
        System.out.println("주문이 완료되었습니다! 총 금액: " + formatter.format(totalPrice) + "원");

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            int oldStock = product.getStock();
            product.decreaseStock(item.getQuantity());
            System.out.println(product.getName() + " 재고가 " + oldStock + "개 → "
                    + product.getStock() + "개로 업데이트되었습니다.");
        }

        cart.clear();
    }

    private void cancelOrder() {
        cart.clear();
        System.out.println("주문이 취소되었습니다. 장바구니가 비워졌습니다.");
    }

    private int getValidInput(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("잘못된 입력입니다. " + min + "~" + max + " 사이의 숫자를 입력해주세요.");
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.nextLine();
            }
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Cart getCart() {
        return cart;
    }
}