import java.util.Stack;

public class StringProcessor {

    /**
     * @param password the password to check
     * @return true if the password is strong; otherwise false
     */
    public boolean isStrongPassword(String password) {
        // Check if the password is at least 8 characters long
        if (password.length() < 8) return false;

        // Flags to track if the password has certain character types
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        // Loop through each character in the password
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true; // Check for uppercase
            else if (Character.isLowerCase(c)) hasLower = true; // Check for lowercase
            else if (Character.isDigit(c)) hasDigit = true; // Check for digit
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true; // Check for special character
        }

        // Return true if all conditions are met
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    /**
     * Counts the number of digits in the given sentence.
     *
     * @param sentence the sentence to analyze
     * @return the count of digits
     */
    public int calculateDigits(String sentence) {
        int count = 0; // Initialize a counter for digits
        // Loop through each character in the sentence
        for (char c : sentence.toCharArray()) {
            if (Character.isDigit(c)) count++; // Increment count for each digit
        }
        return count; // Return the total count
    }

    /**
     * Calculates the number of words in the given sentence.
     * Words are considered to be separated by spaces.
     *
     * @param sentence the sentence to analyze
     * @return the count of words
     */
    public int calculateWords(String sentence) {
        // Check if the sentence is null or empty
        if (sentence == null || sentence.trim().isEmpty()) return 0;
        // Split the sentence into words based on spaces and count them
        String[] words = sentence.trim().split("\\s+");
        return words.length; // Return the number of words
    }

    /**
     * Calculates and returns the result of a mathematical expression.
     *
     * @param expression the expression to evaluate
     * @return the result of the expression
     */
    public double calculateExpression(String expression) {
        return evaluateExpression(expression); // Call the helper method to evaluate
    }

    private double evaluateExpression(String expression) {
        Stack<Double> values = new Stack<>(); // Stack to hold numbers
        Stack<Character> ops = new Stack<>(); // Stack to hold operators
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i); // Get the current character

            // Skip whitespace
            if (c == ' ') continue;

            // If the character is a number, parse it
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++; // Move to the next character
                }
                values.push(Double.parseDouble(sb.toString())); // Add the number to the stack
                i--; // Adjust the index
            }
            // If the character is an operator (+, -, *, /)
            else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!ops.isEmpty() && hasPrecedence(c, ops.peek())) {
                    // Apply the last operation if it has higher or equal precedence
                    values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(c); // Add the current operator to the stack
            }
            // If the character is an open bracket
            else if (c == '(') {
                ops.push(c); // Push the bracket to the operators stack
            }
            // If the character is a closing bracket
            else if (c == ')') {
                while (ops.peek() != '(') {
                    // Apply operations until the matching opening bracket is found
                    values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop(); // Remove the '(' from the stack
            }
        }

        // Apply any remaining operations
        while (!ops.isEmpty()) {
            values.push(applyOperation(ops.pop(), values.pop(), values.pop()));
        }
        return values.pop(); // The final result is on top of the stack
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    private double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            default: return 0; // Return 0 for unknown operations
        }
    }

    // Test cases for the methods
    public static void main(String[] args) {
        StringProcessor sp = new StringProcessor();

        // Test cases for isStrongPassword
        System.out.println(sp.isStrongPassword("Strong1!")); // true: meets all criteria
        System.out.println(sp.isStrongPassword("weakpassword")); // false: no uppercase, digit, or special char
        System.out.println(sp.isStrongPassword("12345678")); // false: no letters or special char
        System.out.println(sp.isStrongPassword("Short1!")); // false: too short
        System.out.println(sp.isStrongPassword("NoSpecialChar1")); // false: no special char

        // Test cases for calculateDigits
        System.out.println(sp.calculateDigits("There are 2 apples.")); // 1: one digit
        System.out.println(sp.calculateDigits("123 Main St.")); // 3: three digits
        System.out.println(sp.calculateDigits("No digits here!")); // 0: no digits
        System.out.println(sp.calculateDigits("My number is 456-7890.")); // 4: four digits
        System.out.println(sp.calculateDigits("In 2020, I got 1 dog.")); // 3: three digits

        // Test cases for calculateWords
        System.out.println(sp.calculateWords("Hello world!")); // 2: two words
        System.out.println(sp.calculateWords("   Leading spaces")); // 2: two words
        System.out.println(sp.calculateWords("Trailing spaces   ")); // 2: two words
        System.out.println(sp.calculateWords("")); // 0: no words
        System.out.println(sp.calculateWords("SingleWord")); // 1: one word

        // Test cases for calculateExpression
        System.out.println(sp.calculateExpression("3 + 5")); // 8.0: basic addition
        System.out.println(sp.calculateExpression("10 + 2 * 6")); // 22.0: multiplication before addition
        System.out.println(sp.calculateExpression("100 * 2 + 12")); // 212.0: multiplication before addition
        System.out.println(sp.calculateExpression("100 * ( 2 + 12 )")); // 1400.0: using brackets
        System.out.println(sp.calculateExpression("100 * ( 2 + 12 ) / 14")); // 100.0: combining operations
    }
}
