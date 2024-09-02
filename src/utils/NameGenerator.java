package utils;
import java.util.Set;
import java.util.HashSet;

public class NameGenerator {

    public static Set<Character> usedNames = new HashSet<Character>();

    public static char getNewFunVarName() {
        return getNewName(usedNames, 'A', 'N');
    }

    public static char getNewTermVarName() {
        return getNewName(usedNames, 'O', 'Z');
    }

    public static char getNewFunConstName() {
        return getNewName(usedNames, 'a', 'z');
    }

    private static char getNewName(Set<Character> usedNames, char start, char end) {
        for (char c = start; c <= end; c++) {
            if (!usedNames.contains(c)) {
                return c;
            }
        }
        
        char[] greekLetters = { 'α', 'β', 'γ', 'δ', 'ε', 'ζ', 'η', 'θ', 'ι', 'κ', 'λ', 'μ', 'ν', 'ξ', 'ο', 'π', 'ρ', 'σ', 'τ', 'υ', 'φ', 'χ', 'ψ', 'ω' };
        for (char c : greekLetters) {
            if (!usedNames.contains(c)) {
                return c;
            }
        }

        char fallback = 'A';
        int suffix = 1;
        while (true) {
            String name = fallback + String.valueOf(suffix);
            if (!usedNames.contains(name.charAt(0))) {
                return name.charAt(0); // Return first character
            }
            suffix++;
        }
    }

}
