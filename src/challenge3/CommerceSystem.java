package challenge3;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class CommerceSystem {
    private static final String ADMIN_PASSWORD = "admin123";
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final int PRICE_FILTER_THRESHOLD = 1000000; // 100만원

    private List<Category> categories;
    private List<Customer> customers;
    private Cart cart;
    private Scanner scanner;
    private DecimalFormat formatter;

    public CommerceSystem(List<Category> categories, List<Customer> customers) {
        this.categories = categories;
        this.customers = customers;
        this.cart = new Cart();
        this.scanner = new Scanner(System.in);
        this.formatter = new DecimalFormat("#,###");
    }

    public void start() {
        while (true) {
            printMainMenu();

            int maxChoice = 6;
            int choice = getValidInput(0, maxChoice);

            if (choice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                break;
            } else if (choice >= 1 && choice <= categories.size()) {
                Category selectedCategory = categories.get(choice - 1);
                showCategoryFilterMenu(selectedCategory);
            } else if (choice == 4 && !cart.isEmpty()) {
                showCartAndOrder();
            } else if (choice == 5 && !cart.isEmpty()) {
                cancelOrder();
            } else if (choice == 6) {
                adminLogin();
            } else if ((choice == 4 || choice == 5) && cart.isEmpty()) {
                System.out.println("장바구니가 비어있습니다.");
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

        System.out.println("6. 관리자 모드");
    }

    // 가격대별 필터링 메뉴
    private void showCategoryFilterMenu(Category category) {
        while (true) {
            System.out.println();
            System.out.println("[ " + category.getCategoryName() + " 카테고리 ]");
            System.out.println("1. 전체 상품 보기");
            System.out.println("2. 가격대별 필터링 (100만원 이하)");
            System.out.println("3. 가격대별 필터링 (100만원 초과)");
            System.out.println("0. 뒤로가기");

            int choice = getValidInput(0, 3);

            if (choice == 0) {
                break;
            } else if (choice == 1) {
                showProductList(category.getProducts(), "전체 상품 목록");
            } else if (choice == 2) {
                List<Product> filtered = category.filterByMaxPrice(PRICE_FILTER_THRESHOLD);
                showProductList(filtered, "100만원 이하 상품 목록");
            } else if (choice == 3) {
                List<Product> filtered = category.filterByMinPrice(PRICE_FILTER_THRESHOLD);
                showProductList(filtered, "100만원 초과 상품 목록");
            }
        }
    }

    private void showProductList(List<Product> products, String title) {
        while (true) {
            System.out.println();
            System.out.println("[ " + title + " ]");

            if (products.isEmpty()) {
                System.out.println("조건에 맞는 상품이 없습니다.");
                System.out.println("0. 뒤로가기");
                getValidInput(0, 0);
                return;
            }

            // 스트림을 활용한 상품 목록 출력
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
                + product.getDescription() + " | 재고: " + product.getStock() + "개\"");
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");

        int choice = getValidInput(1, 2);

        if (choice == 1) {
            if (product.getStock() <= 0) {
                System.out.println("재고가 부족합니다.");
                return;
            }

            // 수량 입력 받기
            System.out.print("수량을 입력해주세요: ");
            int quantity = getValidPositiveInput();

            if (quantity <= 0) {
                System.out.println("1개 이상 입력해주세요.");
                return;
            }

            // 재고 확인
            if (quantity > product.getStock()) {
                System.out.println("재고가 부족합니다. (현재 재고: " + product.getStock() + "개)");
                return;
            }

            cart.addItem(product, quantity);
            System.out.println();
            System.out.println(product.getName() + "가 " + quantity + "개 장바구니에 추가되었습니다.");
        }
    }

    private void showCartAndOrder() {
        System.out.println();
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println();
        System.out.println("[ 장바구니 내역 ]");

        // 스트림을 활용한 장바구니 출력
        cart.getItems().forEach(item -> {
            Product product = item.getProduct();
            System.out.println(product.getName() + " | "
                    + formatter.format(product.getPrice()) + "원 | "
                    + product.getDescription() + " | 수량: " + item.getQuantity() + "개");
        });

        System.out.println();
        System.out.println("[ 총 주문 금액 ]");
        System.out.println(formatter.format(cart.getTotalPrice()) + "원");
        System.out.println();
        System.out.println("1. 주문 확정      2. 메인으로 돌아가기");

        int choice = getValidInput(1, 2);

        if (choice == 1) {
            findCustomerAndOrder();
        }
    }

    // 이메일로 고객 조회 및 할인 적용
    private void findCustomerAndOrder() {
        System.out.println();
        System.out.print("고객 이메일을 입력해주세요: ");
        String email = scanner.nextLine();

        // 스트림을 활용한 고객 조회
        Customer customer = customers.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (customer == null) {
            System.out.println("등록되지 않은 고객입니다.");
            return;
        }

        System.out.println();
        System.out.println("고객 정보: " + customer.getName() + " (" + customer.getEmail() + ")");
        System.out.println("현재 등급: " + customer.getGrade().getName()
                + " (" + customer.getGrade().getDiscountRate() + "% 할인)");
        System.out.println("누적 주문 금액: " + formatter.format(customer.getTotalOrderAmount()) + "원");

        processOrderWithCustomer(customer);
    }

    private void processOrderWithCustomer(Customer customer) {
        int totalPrice = cart.getTotalPrice();
        CustomerGrade grade = customer.getGrade();
        int discountAmount = grade.calculateDiscount(totalPrice);
        int finalPrice = grade.applyDiscount(totalPrice);

        System.out.println();
        System.out.println("주문이 완료되었습니다!");
        System.out.println("할인 전 금액: " + formatter.format(totalPrice) + "원");

        if (discountAmount > 0) {
            System.out.println(grade.getName() + " 등급 할인(" + grade.getDiscountRate() + "%): -"
                    + formatter.format(discountAmount) + "원");
        }

        System.out.println("최종 결제 금액: " + formatter.format(finalPrice) + "원");

        // 재고 차감
        cart.getItems().forEach(item -> {
            Product product = item.getProduct();
            int oldStock = product.getStock();
            product.decreaseStock(item.getQuantity());
            System.out.println(product.getName() + " 재고가 " + oldStock + "개 → "
                    + product.getStock() + "개로 업데이트되었습니다.");
        });

        // 고객 누적 주문 금액 업데이트 및 등급 자동 변경
        CustomerGrade oldGrade = customer.getGrade();
        customer.addOrderAmount(finalPrice);
        CustomerGrade newGrade = customer.getGrade();

        System.out.println();
        System.out.println(customer.getName() + "님의 누적 주문 금액: "
                + formatter.format(customer.getTotalOrderAmount()) + "원");

        if (oldGrade != newGrade) {
            System.out.println("축하합니다! 등급이 " + oldGrade.getName() + " → "
                    + newGrade.getName() + "으로 승급되었습니다!");
        }

        cart.clear();
    }

    private void cancelOrder() {
        cart.clear();
        System.out.println("주문이 취소되었습니다. 장바구니가 비워졌습니다.");
    }

    //관리자 기능
    private void adminLogin() {
        System.out.println();
        int attempts = 0;

        while (attempts < MAX_LOGIN_ATTEMPTS) {
            System.out.print("관리자 비밀번호를 입력해주세요: ");
            String password = scanner.nextLine();

            if (password.equals(ADMIN_PASSWORD)) {
                showAdminMenu();
                return;
            } else {
                attempts++;
                if (attempts < MAX_LOGIN_ATTEMPTS) {
                    System.out.println("비밀번호가 틀렸습니다. 남은 시도 횟수: " + (MAX_LOGIN_ATTEMPTS - attempts));
                }
            }
        }

        System.out.println("3회 실패하여 메인 메뉴로 돌아갑니다.");
    }

    private void showAdminMenu() {
        while (true) {
            System.out.println();
            System.out.println("[ 관리자 모드 ]");
            System.out.println("1. 상품 추가");
            System.out.println("2. 상품 수정");
            System.out.println("3. 상품 삭제");
            System.out.println("4. 전체 상품 현황");
            System.out.println("0. 메인으로 돌아가기");

            int choice = getValidInput(0, 4);

            switch (choice) {
                case 0:
                    return;
                case 1:
                    addProduct();
                    break;
                case 2:
                    editProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    showAllProducts();
                    break;
            }
        }
    }

    private void addProduct() {
        System.out.println();
        System.out.println("어느 카테고리에 상품을 추가하시겠습니까?");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }

        int categoryChoice = getValidInput(1, categories.size());

        Category category = categories.get(categoryChoice - 1);
        System.out.println();
        System.out.println("[ " + category.getCategoryName() + " 카테고리에 상품 추가 ]");

        System.out.print("상품명을 입력해주세요: ");
        String name = scanner.nextLine();

        if (category.hasProduct(name)) {
            System.out.println("이미 존재하는 상품명입니다.");
            return;
        }

        System.out.print("가격을 입력해주세요: ");
        int price = getValidPositiveInput();

        System.out.print("상품 설명을 입력해주세요: ");
        String description = scanner.nextLine();

        System.out.print("재고수량을 입력해주세요: ");
        int stock = getValidPositiveInput();

        System.out.println();
        System.out.println(name + " | " + formatter.format(price) + "원 | " + description + " | 재고: " + stock + "개");
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인    2. 취소");

        int confirm = getValidInput(1, 2);

        if (confirm == 1) {
            category.addProduct(new Product(name, price, description, stock));
            System.out.println();
            System.out.println("상품이 성공적으로 추가되었습니다!");
        } else {
            System.out.println("상품 추가가 취소되었습니다.");
        }
    }

    private void editProduct() {
        System.out.println();
        System.out.print("수정할 상품명을 입력해주세요: ");
        String name = scanner.nextLine();

        Product product = findProductByName(name);
        if (product == null) {
            System.out.println("해당 상품을 찾을 수 없습니다.");
            return;
        }

        System.out.println("현재 상품 정보: " + product.getName() + " | "
                + formatter.format(product.getPrice()) + "원 | "
                + product.getDescription() + " | 재고: " + product.getStock() + "개");

        System.out.println();
        System.out.println("수정할 항목을 선택해주세요:");
        System.out.println("1. 가격");
        System.out.println("2. 설명");
        System.out.println("3. 재고수량");

        int choice = getValidInput(1, 3);

        switch (choice) {
            case 1:
                System.out.println("현재 가격: " + formatter.format(product.getPrice()) + "원");
                System.out.print("새로운 가격을 입력해주세요: ");
                int newPrice = getValidPositiveInput();
                int oldPrice = product.getPrice();
                product.setPrice(newPrice);
                System.out.println();
                System.out.println(product.getName() + "의 가격이 " + formatter.format(oldPrice)
                        + "원 → " + formatter.format(newPrice) + "원으로 수정되었습니다.");
                break;
            case 2:
                System.out.println("현재 설명: " + product.getDescription());
                System.out.print("새로운 설명을 입력해주세요: ");
                String newDesc = scanner.nextLine();
                product.setDescription(newDesc);
                System.out.println();
                System.out.println(product.getName() + "의 설명이 수정되었습니다.");
                break;
            case 3:
                System.out.println("현재 재고: " + product.getStock() + "개");
                System.out.print("새로운 재고수량을 입력해주세요: ");
                int newStock = getValidPositiveInput();
                int oldStock = product.getStock();
                product.setStock(newStock);
                System.out.println();
                System.out.println(product.getName() + "의 재고가 " + oldStock
                        + "개 → " + newStock + "개로 수정되었습니다.");
                break;
        }
    }

    private void deleteProduct() {
        System.out.println();
        System.out.print("삭제할 상품명을 입력해주세요: ");
        String name = scanner.nextLine();

        Product product = findProductByName(name);
        if (product == null) {
            System.out.println("해당 상품을 찾을 수 없습니다.");
            return;
        }

        System.out.println("현재 상품 정보: " + product.getName() + " | "
                + formatter.format(product.getPrice()) + "원 | "
                + product.getDescription() + " | 재고: " + product.getStock() + "개");
        System.out.println("정말 삭제하시겠습니까?");
        System.out.println("1. 확인    2. 취소");

        int confirm = getValidInput(1, 2);

        if (confirm == 1) {
            categories.stream()
                    .filter(category -> category.hasProduct(name))
                    .findFirst()
                    .ifPresent(category -> category.removeProduct(product));

            cart.removeItem(name);

            System.out.println();
            System.out.println(name + " 상품이 삭제되었습니다.");
        } else {
            System.out.println("삭제가 취소되었습니다.");
        }
    }

    private void showAllProducts() {
        System.out.println();
        System.out.println("[ 전체 상품 현황 ]");

        categories.forEach(category -> {
            System.out.println();
            System.out.println("== " + category.getCategoryName() + " ==");
            category.getProducts().forEach(product ->
                    System.out.println("  " + product.getName() + " | "
                            + formatter.format(product.getPrice()) + "원 | "
                            + product.getDescription() + " | 재고: " + product.getStock() + "개")
            );
        });
    }

    private Product findProductByName(String name) {
        return categories.stream()
                .map(category -> category.findProductByName(name))
                .filter(product -> product != null)
                .findFirst()
                .orElse(null);
    }

    // 유효한 정수 입력을 받는 메서드
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

    // 양수 정수 입력을 받는 메서드
    private int getValidPositiveInput() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input >= 0) {
                    return input;
                }
                System.out.println("잘못된 입력입니다. 0 이상의 숫자를 입력해주세요.");
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.nextLine();
            }
        }
    }

    public List<Category> getCategories() {
        return categories;
    }
}