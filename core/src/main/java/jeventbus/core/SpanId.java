package jeventbus.core;

import java.util.Random;

public class SpanId {

    public static String generate() {

        final int leftLimit = 48; // numeral '0'
        final int rightLimit = 122; // letter 'z'
        final int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                       .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                       .limit(targetStringLength)
                       .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                       .toString();
    }

}
