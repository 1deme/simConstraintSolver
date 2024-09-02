package utils;
import java.util.Set;
import java.util.HashSet;

public class NameGenerator {

    public static Set<Character> usedChars = new HashSet<Character>();


    // Function to get a new function variable name (uppercase)
    public static char getNewFunVarName(Set<Character> usedNames) {
        return getNewName(usedNames, 'A', 'N');
    }

    // Function to get a new term variable name (uppercase)
    public static char getNewTermVarName(Set<Character> usedNames) {
        return getNewName(usedNames, 'O', 'Z');
    }

    // Function to get a new function constant name (lowercase)
    public static char getNewFunConstName(Set<Character> usedNames) {
        return getNewName(usedNames, 'a', 'z');
    }

    // Helper function to find an unused character within a given range
    private static char getNewName(Set<Character> usedNames, char start, char end) {
        for (char c = start; c <= end; c++) {
            if (!usedNames.contains(c)) {
                return c;
            }
        }
        
        // If all alphabetic characters are used, fall back to Greek letters (or other sets)
        char[] greekLetters = { 'α', 'β', 'γ', 'δ', 'ε', 'ζ', 'η', 'θ', 'ι', 'κ', 'λ', 'μ', 'ν', 'ξ', 'ο', 'π', 'ρ', 'σ', 'τ', 'υ', 'φ', 'χ', 'ψ', 'ω' };
        for (char c : greekLetters) {
            if (!usedNames.contains(c)) {
                return c;
            }
        }
        
        // If all predefined sets are used, generate new unique names (e.g., 'A1', 'A2')
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

    public static void main(String[] args) {
        // Example usage
        Set<Character> usedNames = Set.of('A', 'B', 'C', 'a', 'b', 'c', 'α', 'β', 'γ');
        
        System.out.println(getNewFunVarName(usedNames)); // Should return D
        System.out.println(getNewTermVarName(usedNames)); // Should return D
        System.out.println(getNewFunConstName(usedNames)); // Should return d
    }
}
