package com.google.developers.wallet.specification.legacy;

public class LegacySpecification {

    public static void main(String[] args) {
        Specification rightLength = s -> s.length() == 16;
        Specification isNumeric = s -> s.matches("\\d+");

        Specification potentialCreditCardNumber = rightLength.and(isNumeric);
        System.out.println(potentialCreditCardNumber.isSatisfiedBy("1234123412341234"));
    }

}

interface Specification {
    boolean isSatisfiedBy(String s);

    default Specification and(Specification other) {
        return new AndSpecification(this, other);
    }
    default Specification or(Specification other) {
        return new OrSpecification(this, other);
    }
    default Specification not(Specification other) {
        return new NotSpecification(other);
    }

}

record AndSpecification(Specification left, Specification right) implements Specification {

    @Override
    public boolean isSatisfiedBy(String s) {
        return left.isSatisfiedBy(s) && right.isSatisfiedBy(s);
    }

}

record OrSpecification(Specification left, Specification right) implements Specification {

    @Override
    public boolean isSatisfiedBy(String s) {
        return left.isSatisfiedBy(s) || right.isSatisfiedBy(s);
    }

}

record NotSpecification(Specification specification) implements Specification {

    @Override
    public boolean isSatisfiedBy(String s) {
        return !specification.isSatisfiedBy(s);
    }

}

