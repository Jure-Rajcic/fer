package hr.fer.zemris.java.custom.scripting.exec;

public class ValueWrapper {

    private Object value;

    public ValueWrapper(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    private enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE;

        public <T extends Number> T apply(T value1, T value2) {
            Double result = switch (this) {
                case ADD -> value1.doubleValue() + value2.doubleValue();
                case SUBTRACT -> value1.doubleValue() - value2.doubleValue();
                case MULTIPLY -> value1.doubleValue() * value2.doubleValue();
                case DIVIDE -> value1.doubleValue() / value2.doubleValue();
            };
            if (value1 instanceof Integer && value2 instanceof Integer)
                return (T) Integer.valueOf(result.intValue());
            return (T) result;
        }
    }

    public void add(Object incValue) {
        performOperation(incValue, Operation.ADD);
    }

    public void subtract(Object decValue) {
        performOperation(decValue, Operation.SUBTRACT);
    }

    public void multiply(Object mulValue) {
        performOperation(mulValue, Operation.MULTIPLY);
    }

    public void divide(Object divValue) {
        performOperation(divValue, Operation.DIVIDE);
    }

    private void performOperation(Object otherValue, Operation operation) {
        Number currentValue = convertToNumber(this.value);
        Number otherNumber = convertToNumber(otherValue);
        if (currentValue instanceof Double || otherNumber instanceof Double) {
            double result = operation.apply(currentValue.doubleValue(), otherNumber.doubleValue());
            this.value = result;
        } else {
            int result = operation.apply(currentValue.intValue(), otherNumber.intValue());
            this.value = result;
        }
    }

    public int numCompare(Object withValue) {
        Number currentValue = convertToNumber(this.value);
        Number otherValue = convertToNumber(withValue);
        return Double.compare(currentValue.doubleValue(), otherValue.doubleValue());
    }

    private static Number convertToNumber(Object value) {
        if (value == null)
            return 0;
        if (value instanceof Number)
            return (Number) value;
        if (value instanceof String) {
            String stringValue = (String) value;
            if (stringValue.contains(".") || stringValue.contains("E"))
                return Double.valueOf(stringValue);
            return Integer.valueOf(stringValue);
        }
        throw new RuntimeException("Invalid value type");
    }

    // public static void main(String[] args) {
        // ValueWrapper v1 = new ValueWrapper(null);
        // ValueWrapper v2 = new ValueWrapper(null);
        // System.out.println(v2.getValue());
        // v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
        // System.out.println(v1.getValue());
        // System.out.println(v2.getValue());
        // ValueWrapper v3 = new ValueWrapper("1.2E1");
        // ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
        // v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
        // System.out.println(v3.getValue());
        // System.out.println(v4.getValue());
        // ValueWrapper v5 = new ValueWrapper("12");
        // ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
        // v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
        // System.out.println(v5.getValue());
        // System.out.println(v6.getValue());
        // ValueWrapper v7 = new ValueWrapper("Ankica");
        // ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
        // v7.add(v8.getValue()); // throws RuntimeException
    // }
}
