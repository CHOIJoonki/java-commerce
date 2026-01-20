package step3;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class CommerceSystem {
    List<Category> categories;
    Scanner scanner;
    DecimalFormat formatter;

    public CommerceSystem(List<Category> categories) {
        this.categories = categories;
        this.scanner = new Scanner(System.in);
        this.formatter = new DecimalFormat("#,###");
    }

    public void start() {
        while (true) {
            printMainMenu();

            int choice = getValidInput(0, categories.size());

            if (choice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                break;
            } else if (choice >= 1 && choice <= categories.size()) {
                Category selectedCategory = categories.get(choice - 1);
                showCategoryMenu(selectedCategory);
            }
        }
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.println("0. 종료      | 프로그램 종료");
    }

    private void showCategoryMenu(Category category) {
        while (true) {
            System.out.println();
            System.out.println("[ " + category.getCategoryName() + " 카테고리 ]");

            List<Product> products = category.getProducts();
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                String formattedPrice = String.format("%,10d", product.price);
                System.out.println((i + 1) + ". " + String.format("%-14s", product.name)
                        + " |" + formattedPrice + "원 | " + product.description);
            }
            System.out.println("0. 뒤로가기");

            int choice = getValidInput(0, products.size());

            if (choice == 0) {
                System.out.println();
                break;
            } else if (choice >= 1 && choice <= products.size()) {
                Product selected = products.get(choice - 1);
                System.out.println("선택한 상품: " + selected.name + " | "
                        + formatter.format(selected.price) + "원 | "
                        + selected.description + " | 재고: " + selected.stock + "개");
            }
        }
    }

    private int getValidInput(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
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
}