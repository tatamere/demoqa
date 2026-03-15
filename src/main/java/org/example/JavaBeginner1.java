package org.example;

import java.util.Scanner;

public class JavaBeginner1 {
    public static void main(String[] args) {
      //  A1();
      //  A2();
      //  A3();
      //  B();
    }

    public static void A1() {
        int x = 10;
        int y = 5;
        int result = 0;

        if (x > y) {
            result = x * y;
        } else {
            result = x + y;
        }

        System.out.println(result);

        // დაიბეჭდება 50, რადგან სრულდება პირველი პირობა (x > y),  10 > 5, 5 * 10 = 50
    }

    public static void A2() {
        int count = 1;
        while (count <= 4) {
            System.out.println(count);
            count++;

            // ციკლი გაეშვება 4-ჯერ, რადგან count იწყება 1-დან ციკლი გაეშვება იქამე, სანამ count არ
            // გახდება 4-ზე მეტი. count++ ნიშნავს, რომ მნიშვნელობა ყოველ ჯერზე გაიზრდება 1-ით.
            // დაიბეჭდება 1, 2, 3, 4
        }
    }

    public static void A3() {
        String word = "Java";
        for (int i = 0; i < word.length(); i++) {
            System.out.println(word.charAt(i));
            // დაიბეჭდება:
            // J
            // a
            // v
            // a
// ციკლი გაეშვება 4-ჯერ. რადგან სიტყვის სიგრძე არის 4, ციკლი გაეშვება იქამდე, სანამ i < 4 -ზე
//ციკლი იწყება 0-დან. i მიიღებს 0, 1, 2, 3 მნიშვნელობებს.
        }
    }

    public static void B() {
        // 1
        Scanner input = new Scanner(System.in);

        String username;
        String password;
        int attempts = 3;
        int attemptNumber = 1;

        // 4
        while (attempts > 0) {

            System.out.println("Enter username:");
            username = input.nextLine();

            System.out.println("Enter password:");
            password = input.nextLine();

            //Bonus Task
            boolean hasDigit = false;
            for (int i = 0; i < password.length(); i++) {
                if (Character.isDigit(password.charAt(i))) {
                    hasDigit = true;
                    break;
                }
            }

            if (!hasDigit) {
                System.out.println("Password must contain at least one digit");
                continue;

            }


            // 2
            if (username.equals("admin") && password.equals("1234")) {
                System.out.println("Attempt " + attemptNumber + ": Login successful");
                break;
            }

            // 3
            else {
                System.out.println("Attempt " + attemptNumber + ": Login failed");
                attempts = attempts - 1;
                attemptNumber = attemptNumber + 1;
            }
        }

        // 5
        if (attempts == 0) {
            System.out.println("Account locked");
        }
    }
}


