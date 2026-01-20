package step1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Product> products = new ArrayList<>();
        products.add(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 50));
        products.add(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 30));
        products.add(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 15));
        products.add(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 100));

        while (true) {
            System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                System.out.println((i + 1) + ". " + String.format("%-14s", product.name)
                        + " | " + product.price + "원 | " + product.description);
            }
            System.out.println("0. 종료           | 프로그램 종료");

            int choice = getValidInput(scanner, -1, products.size());

            if (choice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                break;
            } else if (choice >= 1 && choice <= products.size()) {
                Product selected = products.get(choice - 1);
                System.out.println("선택한 상품: " + selected.name + " | "
                        + selected.price + "원 | "
                        + selected.description + " | 재고: " + selected.stock + "개");
            }
            System.out.println();
        }

        scanner.close();
    }

    private static int getValidInput(Scanner scanner, int min, int max) {
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