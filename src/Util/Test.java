package Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Sony VAIO on 11/03/2016.
 */
public class Test {

    public static void main(String[] args) {

        String[] name = {"Lewis", "Leanne", "Nathan"};

        for (int i = 0; i < name.length; i++) {
            System.out.println(name[i]);
        }

//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter a number:");
//        int a = sc.nextInt();
//        System.out.println("Enter second number");
//        int b = sc.nextInt();
//
//        int answer = a + b;
//        System.out.printf("The answer of %d + %d = %d",a,b,answer);


        //printMyName();
        System.out.println(heyThere());
        rege();

    }

//    public static void printMyName(){
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Whats your name");
//        String name = sc.nextLine();
//        System.out.printf("Hello %s\n",name);
//    }


    public static ArrayList<Integer> heyThere() {
        int[] arr = {4, 5, 6, 77, 8};
        ArrayList<Integer> myList = new ArrayList<>();
        for (int a : arr) {
            myList.add(a);
            Collections.sort(myList);
            System.out.println(a);
        }
        int a = myList.get(2);
        int b = myList.get(3);

        System.out.println(a + b);


        return myList;
    }

    public static void rege() {
        Scanner sc = new Scanner(System.in);
        System.out.printf("Enter a zipcode");
        String m = sc.nextLine();
        if (m.matches("^[0-9]{5}$")) {
            System.out.printf("valid: %s ", m);

        } else {
            do {
                System.out.printf("Invalid: %s\n", m);
                System.out.printf("Enter a zipcode");
                m = sc.nextLine();
            } while (!m.matches("^[0-9]{5}$"));
        }
    }
}
