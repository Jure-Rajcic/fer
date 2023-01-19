package hr.fer.oprpp1.custom.collections.demo;

import java.util.Arrays;

public class StackDemo extends ObjectStack {
    // java -cp target/hw01-0036536053-1.0.jar hr.fer.oprpp1.custom.collections.demo.StackDemo "8 2 /"
    public static void main(String[] args) {
        String input = args[0].strip().replaceAll(" +", " ");
        String[] expressions = input.split(" ");
        String[] operators = new String[] { "+", "-", "/", "*", "%" };
        ObjectStack stack = new ObjectStack();
        for (String s : expressions) {
            if (!Arrays.stream(operators).anyMatch(s::equals)) {
                stack.push(Integer.valueOf(s));
            } else {
                int second = (Integer) stack.pop();
                int first = (Integer) stack.pop();
                int result = 0;
                if (s.equals("+")) {
                    result = first + second;
                } else if (s.equals("-")) {
                    result = first - second;
                } else if (s.equals("/")) {
                    result = first / second;
                } else if (s.equals("*")) {
                    result = first * second;
                } else if (s.equals("%")) {
                    result = first % second;
                }
                stack.push(result);
            }
        }
        if (stack.size() != 1) 
            throw new EmptyStackException("stack size different from 1,");
        System.out.println(stack.pop());
    }

}
