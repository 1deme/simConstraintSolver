
public class stringHelper {

    public static String charWithSubscript(char c, int i) {
        if(i == 0){
            return Character.toString(c);
        }
        String subscript = toSubscript(i);
        return c + subscript;
    }

    private static String toSubscript(int number) {
        StringBuilder subscript = new StringBuilder();
        String numberStr = String.valueOf(number);

        for (char digit : numberStr.toCharArray()) {
            subscript.append(toSubscriptChar(digit));
        }

        return subscript.toString();
    }

    private static char toSubscriptChar(char digit) {
        switch (digit) {
            case '0': return '₀';
            case '1': return '₁';
            case '2': return '₂';
            case '3': return '₃';
            case '4': return '₄';
            case '5': return '₅';
            case '6': return '₆';
            case '7': return '₇';
            case '8': return '₈';
            case '9': return '₉';
            default: return digit;
        }
    }

}
