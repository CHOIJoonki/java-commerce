package step2;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class CommerceSystem {
    List<Product> products;
    Scanner scanner;
    DecimalFormat formatter;

    public CommerceSystem(List<Product> products) {
        this.products = products;
        this.scanner = new Scanner(System.in);
        this.formatter = new DecimalFormat("#,###");
    }

    public void start() {
        while (true) {
            printProductList();

            int choice = getValidInput(0, products.size());

            if (choice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                break;
            } else if (choice >= 1 && choice <= products.size()) {
                Product selected = products.get(choice - 1);
                System.out.println("선택한 상품: " + selected.name + " | "
                        + formatter.format(selected.price) + "원 | "
                        + selected.description + " | 재고: " + selected.stock + "개");
            }
            System.out.println();
        }
        scanner.close();
    }

    private void printProductList() {
        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String formattedPrice = String.format("%,10d", product.price);
            System.out.println((i + 1) + ". " + String.format("%-14s", product.name)
                    + " |" + formattedPrice + "원 | " + product.description);
        }
        System.out.println("0. 종료           | 프로그램 종료");
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