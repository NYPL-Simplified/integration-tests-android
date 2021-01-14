package framework.utilities;

import java.util.Hashtable;

public class RomanNumericUtils {
    private static Hashtable<Character, Integer> ht = new Hashtable<>();

    public static int convertToInt(String input) {
        ht.put('i', 1);
        ht.put('x', 10);
        ht.put('c', 100);
        ht.put('m', 1000);
        ht.put('v', 5);
        ht.put('l', 50);
        ht.put('d', 500);
        int number = 0;
        int previousValue = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            int temp = ht.get(input.charAt(i));
            if (temp < previousValue)
                number -= temp;
            else
                number += temp;
            previousValue = temp;
        }
        return number;
    }
}
