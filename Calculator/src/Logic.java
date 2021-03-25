import java.util.regex.Pattern;

@FunctionalInterface
interface OperationsArabic { int operation (int a, int b); }
interface OperationsRoman  { String operation (String a, String b); }

class LogicArabic {
    public static OperationsArabic add = Integer::sum;
    public static OperationsArabic sub = (a, b) -> a - b;
    public static OperationsArabic mul = (a, b) -> a * b;
    public static OperationsArabic div = (a, b) -> a / b;
}

class LogicRoman extends ConvertingValues {
    public static OperationsRoman add = (a, b) -> intToRoman(romanToInt(a) + romanToInt(b));
    public static OperationsRoman sub = (a, b) -> intToRoman(romanToInt(a) - romanToInt(b));
    public static OperationsRoman mul = (a, b) -> intToRoman(romanToInt(a) * romanToInt(b));
    public static OperationsRoman div = (a, b) -> intToRoman(romanToInt(a) / romanToInt(b));
}

class Validation {
    public static boolean isCorrectArabic (String s) {
        Pattern pattern = Pattern.compile("\\d{1,2}[\\s\\+\\-\\*\\/]\\d{1,2}");
        return pattern.matcher(s).matches();
    }

    public static boolean isCorrectRoman (String s) {
        Pattern pattern = Pattern.compile("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})[\\+\\" +
                                          "-\\*/]M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})");
        return pattern.matcher(s).matches();
    }
}

class Parsing {
    public static int[] parseArabic (String s) {
        char[] sToChr = s.toCharArray();
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();

        for (char i: sToChr) {
            if (i == '+' || i == '-' || i == '/' || i == '*') break;
            left.append(i);
        }

        for (int i = sToChr.length - 1; i > 0; --i) {
            if (sToChr[i] == '+' || sToChr[i] == '-' || sToChr[i] == '/' || sToChr[i] == '*') break;
            right.append(sToChr[i]);
        }

        return new int[]{Integer.parseInt(left.toString()), Integer.parseInt(right.reverse().toString())};
    }

    public static String[] parseRoman (String s) {
        char[] sToChr = s.toCharArray();
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();

        for (char i: sToChr) {
            if (i == '+' || i == '-' || i == '/' || i == '*') break;
            left.append(i);
        }

        for (int i = sToChr.length - 1; i > 0; i--) {
            if (sToChr[i] == '+' || sToChr[i] == '-' || sToChr[i] == '/' || sToChr[i] == '*') break;
            right.append(sToChr[i]);
        }

        return new String[]{left.toString(), right.reverse().toString()};
    }

    public static char getTypeOfOperation (String s) {
        char res = 0;

        for (char i: s.toCharArray()) {
            if (i == '+' || i == '-' || i == '*' || i == '/') {
                res = i;
                break;
            }
        }

        return res;
    }

    public static void Calculate (String s) throws Exception {

        if (Character.isDigit(s.toCharArray()[0])) {
            boolean a = Validation.isCorrectArabic(s);
            if (!a) throw new Exception("Invalid input.");

            char operation = getTypeOfOperation(s);
            int[] values = parseArabic(s);
            int res = 0;

            switch (operation) {
                case '+' -> res = LogicArabic.add.operation(values[0], values[1]);
                case '-' -> res = LogicArabic.sub.operation(values[0], values[1]);
                case '/' -> res = LogicArabic.div.operation(values[0], values[1]);
                case '*' -> res = LogicArabic.mul.operation(values[0], values[1]);
            }

            System.out.printf("%d %s %d = %d", values[0], operation, values[1], res);
        }

        else if (Character.isLetter(s.toCharArray()[0])) {
            boolean a = Validation.isCorrectRoman(s);
            if (!a) throw new Exception("Invalid input");

            char operation = getTypeOfOperation(s);
            String[] values = parseRoman(s);
            StringBuilder res = new StringBuilder();

            switch (operation) {
                case '+' -> res.append(LogicRoman.add.operation(values[0], values[1]));
                case '-' -> res.append(LogicRoman.sub.operation(values[0], values[1]));
                case '/' -> res.append(LogicRoman.div.operation(values[0], values[1]));
                case '*' -> res.append(LogicRoman.mul.operation(values[0], values[1]));
            }

            System.out.printf("%s %s %s = %s", values[0], operation, values[1], res);
        }
    }
}

