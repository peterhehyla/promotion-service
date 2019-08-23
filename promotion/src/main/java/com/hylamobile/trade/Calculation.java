package com.hylamobile.trade;
import com.hylamobile.promotion.domain.Money;
import com.hylamobile.trade.exceptions.CurrencyMismatchException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;


public abstract class Calculation implements Serializable, Comparable<Calculation> {
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100).setScale(4);
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private CalculationType calculationType = CalculationType.ADDITION;

    private BigDecimal calculationValue;

    private Currency currency;

    public static final Calculation newZeroInstance() {
        return new PercentageCalculation(Money.newZeroInstance().getAmount());
    }

    /**
     * @param type
     * @param value
     */
    protected Calculation(CalculationType type, BigDecimal value) {
        this(type, value, null);
    }

    /**
     * @param type
     * @param value
     */
    protected Calculation(CalculationType type, BigDecimal value, Currency currency) {
        this.calculationType = type;
        this.calculationValue = value;
        this.currency = currency;
    }

    protected Calculation() {

    }

    /**
     * @return Returns the calculationType.
     */
    public CalculationType getCalculationType() {
        return this.calculationType;
    }

    /**
     * @param calculationType
     *            The calculationType to set.
     */
    public void setCalculationType(CalculationType calculationType) {
        this.calculationType = calculationType;
    }

    /**
     * @return Returns the calculationValue.
     */
    public BigDecimal getCalculationValue() {
        return this.calculationValue;
    }

    /**
     * @param calculationValue
     *            The calculationValue to set.
     */
    public void setCalculationValue(BigDecimal calculationValue) {
        this.calculationValue = calculationValue;
    }

    /**
     * @return the currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @param currency
     *            the currency to set
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    protected Money getMoney() {
        return new Money(getCalculationValue(), getCurrency());
    }


    public abstract Money eval(Money value);

    public int compareCalculationValueToZero() {
        return compareCalculationValue(BigDecimal.ZERO);
    }

    public int compareCalculationValue(BigDecimal value) {
        return this.calculationValue.compareTo(value);
    }

    public int compareCalculationValue(Calculation calculation) {
        if (this.currency == null || calculation.getCurrency() == null) {
            return compareCalculationValue(calculation.getCalculationValue());
        }
        else if (!this.currency.equals(calculation.getCurrency())) {
            throw new CurrencyMismatchException();
        }

        return compareCalculationValue(calculation.getCalculationValue());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Calculation)) {
            return false;
        }

        Calculation calc = (Calculation) obj;
        return new EqualsBuilder().append(this.getCalculationType(), calc.getCalculationType())
                .append(this.calculationValue, calc.getCalculationValue()).append(this.currency, calc.getCurrency())
                .isEquals();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 3).append(this.calculationType).append(this.calculationValue)
                .append(this.currency).toHashCode();
    }

    public String getCalculatedValueAsString() {
        return getMoney().toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.calculationValue.toString() + "[" + this.calculationType.toString() + "]";
    }

    public static class PercentageCalculation extends Calculation {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */
        public PercentageCalculation(BigDecimal calculationValue) {
            super(CalculationType.PERCENTAGE, calculationValue);
        }

        public PercentageCalculation(BigDecimal calculationValue, Currency currency) {
            super(CalculationType.PERCENTAGE, calculationValue, currency);
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Calculation)) {
                return false;
            }
            Calculation calc = (Calculation) obj;
            return new EqualsBuilder().append(this.getCalculationType(), calc.getCalculationType())
                    .append(this.getCalculationValue(), calc.getCalculationValue()).isEquals();
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 3).append(this.getCalculationType()).append(this.getCalculationValue())
                    .toHashCode();
        }

        /*
         * (non-Javadoc)
         *
         * @see com.flipswap.domain.Calculation#eval(com.flipswap.domain.Money)
         */
        @Override
        public Money eval(Money value) {
            return new Money(value.getAmount().multiply(
                    getCalculationValue().divide(ONE_HUNDRED, 4, BigDecimal.ROUND_HALF_UP)),
                    value.getCurrency());
        }

    }

    public static class AdditionCalculation extends Calculation {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */
        public AdditionCalculation(BigDecimal calculationValue, Currency currency) {
            super(CalculationType.ADDITION, calculationValue, currency);
        }

        /*
         * (non-Javadoc)
         *
         * @see com.flipswap.domain.Calculation#eval(com.flipswap.domain.Money)
         */
        @Override
        public Money eval(Money value) {
            if (!getCurrency().getCurrencyCode().equals(value.getCurrency())) {
                throw new IllegalArgumentException("The currencies must be the same");
            }

            return getMoney();
        }

    }

    public static class FlatCalculation extends Calculation {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */
        public FlatCalculation(BigDecimal calculationValue, Currency currency) {
            super(CalculationType.FLAT, calculationValue, currency);
        }

        /*
         * (non-Javadoc)
         *
         * @see com.flipswap.domain.Calculation#eval(com.flipswap.domain.Money)
         */
        @Override
        public Money eval(Money value) {
            return new Money(getCalculationValue(), getCurrency()).subtract(value);
        }

    }

    public static class MinimumCalculation extends Calculation {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */
        public MinimumCalculation(BigDecimal calculationValue, Currency currency) {
            super(CalculationType.MINIMUM, calculationValue, currency);
        }

        /*
         * (non-Javadoc)
         *
         * @see com.flipswap.domain.Calculation#eval(com.flipswap.domain.Money)
         */
        @Override
        public Money eval(Money value) {
            if (value.compareTo(getCalculationValue()) >= 0) {
                return Money.newZeroInstance(value.getCurrency());
            }
            return getMoney().subtract(value);
        }
    }

    public static class FloorCalculation extends Calculation {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */
        public FloorCalculation(BigDecimal calculationValue, Currency currency) {
            super(CalculationType.FLOOR, calculationValue, currency);
        }

        /*
         * (non-Javadoc)
         *
         * @see com.flipswap.domain.Calculation#eval(com.flipswap.domain.Money)
         */
        @Override
        public Money eval(Money value) {
            Currency currency = value.getCurrency();
            BigDecimal floorAmount = new BigDecimal(value.getAmount().toBigInteger());
            return new Money(floorAmount, currency).subtract(value);
        }

    }

    public static class MaximumCalculation extends Calculation {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */
        public MaximumCalculation(BigDecimal calculationValue, Currency currency) {
            super(CalculationType.MAXIMUM, calculationValue, currency);
        }

        /*
         * (non-Javadoc)
         *
         * @see com.flipswap.domain.Calculation#eval(com.flipswap.domain.Money)
         */
        @Override
        public Money eval(Money value) {
            if (value.compareTo(getCalculationValue()) <= 0) {
                return Money.newZeroInstance(value.getCurrency());
            } else {
                return getMoney().subtract(value);
            }
        }
    }

    /**
     *
     */
    @Override
    public int compareTo(Calculation o) {
        return this.calculationValue.compareTo(o.calculationValue);
    }

}
