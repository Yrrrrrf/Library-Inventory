package model.util;

import java.util.Scanner;

public class auxiliarMethods {
    
    static Scanner sc = new Scanner(System.in);


    /**
     * Asks for an integer and this funciton will only end when the given value were an Integer
     * @param askedValue
     * @return integerNumber
     */
    public static int askInteger(String askedValue) {
        int number = 0;
        try {
            System.out.print(askedValue);
            number = Integer.valueOf(sc.next());
        } catch (Exception e) {
            System.out.println("Please use only numbers");
            number = askInteger(askedValue);
        }
        return number;
    }


    /**
     * Asks for an integer with the specified lenght
     * @param askedValue
     * @param minValue
     * @param maxValue
     * @return
     */
    public static int askIntegerWithLenght(String askedValue, int lenght) {
        int number = 0;
        try {
            System.out.print(askedValue);
            String text = sc.next();
            text.replace(" ", "");
            text.replace("-", "");
            if (text.length() == 10) number = Integer.valueOf(text);
            else {
                System.out.println("Number should be a " + lenght + " char lenght");
                number = askIntegerWithLenght(askedValue, lenght);
            }
        } catch (Exception e) {
            System.out.println("Please use only numbers");
            number = askIntegerWithLenght(askedValue, lenght);
        }
        return number;
    }


    //! Need some improvements, and use... And a reason to exist 
    /**
     * Asks for an integer but the value only can be between minValue and maxValue
     * @param askedValue
     * @param minValue   Min value. Must be smaller than <code>maxValue</code>
     * @param maxValue   Max value. Must be greater than <code>minValue</code>
     * @throws IllegalArgumentException If min value and max value are inconsisten
     * @return
     */
    public static int askInteger(String askedValue, int minValue, int maxValue) {
        if (minValue >= maxValue)
        throw new IllegalArgumentException("maxValue must be > minValue");

        int number = 0;
        try {
            System.out.print(askedValue);
            String text = sc.next();
            text.replace(" ", "");
            text.replace("-", "");
            number = Integer.valueOf(text);
            if (number >= minValue && number <= maxValue) 
                return number;
            else {
                System.out.println("Number out of limit");
                number = askInteger(askedValue, minValue, maxValue);
            }
        } catch (Exception e) {
            System.out.println("Please use only numbers");
            number = askInteger(askedValue);
        }
        return number;
    }


}