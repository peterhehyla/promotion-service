package com.hylamobile.promotion.domain;

import com.hylamobile.promotion.enums.RoundingType;
import com.hylamobile.trade.exceptions.CurrencyMismatchException;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Configurable(preConstruction = true)
public class Money implements Serializable, Comparable<Money> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private BigDecimal amount;

    private Currency currency;

    private Currency systemCurrency;

    private BigDecimal exchangeRate = BigDecimal.ONE;

    private BigDecimal systemAmount;

    protected Money() {

    }

    /**
     * @param amount
     * @param currency
     * @param systemCurrency
     * @param exchangeRate
     * @param systemAmount
     */
    private Money(BigDecimal amount, Currency currency, BigDecimal systemAmount, Currency systemCurrency,
            BigDecimal exchangeRate) {
        this.amount = amount;
        this.currency = currency;
        this.systemCurrency = systemCurrency;
        this.exchangeRate = exchangeRate;
        this.systemAmount = systemAmount;
    }

    public static final Money newZeroInstance(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    public static final Money newZeroInstance() {
        return new Money(BigDecimal.ZERO);
    }

    /**
     * Creates a money object and does automatic conversion. If the currency passed in matches the system currency, it
     * will convert the amount to the user's company's currency. If it does not match, it will convert the amount to the
     * system currency
     *
     * @param amount
     *            The amount given to the money object
     * @param currency
     *            The currency of the money object
     */
    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    /**
     * @see Money#Money(BigDecimal, Currency)
     */
    public Money(double amount, Currency currency) {
        this(new BigDecimal(amount), currency);
    }

    /**
     * Creates a money object with the user's default currency
     *
     * @see Money#Money(BigDecimal, Currency)
     * @param amount
     *            The amount of the money object
     */
    public Money(BigDecimal amount) {
        this(amount, null);
    }

    /**
     * @see Money#Money(BigDecimal)
     */
    public Money(double amount) {
        this(new BigDecimal(amount), null);
    }

    private static BigDecimal convertToSystemCurrency(BigDecimal amount, BigDecimal exchangeRate) {
        if (exchangeRate != null)
            return amount.divide(exchangeRate, 4, RoundingMode.HALF_UP);

        return null;
    }

    private static BigDecimal convertFromSystemCurrency(BigDecimal amount, BigDecimal exchangeRate) {
        if (exchangeRate != null)
            return amount.multiply(exchangeRate);

        return null;
    }


    /**
     * @return the tradeInCurrency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * NOTE: IF YOU ARE USING THIS METHOD YOU MUST SEEK APPROVAL!
     *
     * @return the convertedAmount
     */
    BigDecimal getSystemAmount() {
        return systemAmount;
    }

    /**
     * NOTE: IF YOU ARE USING THIS METHOD YOU MUST SEEK APPROVAL!
     *
     * @return Amount in system currency
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param currency
     *            the tradeInCurrency to set.<br/>
     * <br/>
     *
     *            <font color="red">Don't make this setter public. This create below issue. <br/>
     * <br/>
     *            Caused by: com.sun.xml.bind.v2.runtime.IllegalAnnotationsException: 1 counts of
     *            IllegalAnnotationExceptions java.util.Currency does not have a no-arg default constructor. this
     *            problem is related to the following location: at java.util.Currency at public java.util.Currency
     *            com.flipswap.domain.Money.getCurrency() at com.flipswap.domain.Money at public
     *            com.flipswap.domain.Money
     *            com.flipswap.webservice.v1.verizon.response.RemoteInvoiceQuoteResponse.getTotal() at
     *            com.flipswap.webservice.v1.verizon.response.RemoteInvoiceQuoteResponse at private
     *            com.flipswap.webservice.v1.verizon.response.RemoteInvoiceQuoteResponse
     *            com.flipswap.webservice.v1.verizon.jaxws_asm.GenerateInvoiceQuoteResponse._return at
     *            com.flipswap.webservice.v1.verizon.jaxws_asm.GenerateInvoiceQuoteResponse</font>
     */
    void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * @return the exchangeRate
     */
    BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate
     *            the exchangeRate to set
     */
    void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * @param amount
     *            the amount to set
     */
    void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the systemCurrency
     */
    Currency getSystemCurrency() {
        return systemCurrency;
    }

    /**
     * @param systemCurrency
     *            the systemCurrency to set
     */
    void setSystemCurrency(Currency systemCurrency) {
        this.systemCurrency = systemCurrency;
    }

    /**
     * @param systemAmount
     *            the systemAmount to set
     */
    void setSystemAmount(BigDecimal systemAmount) {
        this.systemAmount = systemAmount;
    }

    /**
     * Compares objects based on the System currency
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Money money) {
        if (!this.currency.equals(money.getCurrency()))
            throw new CurrencyMismatchException();

        return this.amount.compareTo(money.getAmount());
    }

    /**
     * Compares amounts based on the System currency
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(BigDecimal amount) {
        return this.amount.compareTo(amount);
    }

    /**
     * Compares amounts based on the System currency
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(double amount) {
        return compareTo(new BigDecimal(amount));
    }

    public int compareToZero() {
        return compareTo(BigDecimal.ZERO);
    }

    public boolean hasSameCurrency(Money money) {
        return this.getCurrency().equals(money.getCurrency());
    }

    public boolean hasSameCurrency(Currency currency) {
        return this.getCurrency().equals(currency);
    }

    /**
     * Returns a string representation of the money object as HTML using the currency set
     *
     * @return A string representation of the money object as HTML using the currency set
     */
    @Override
    public String toString() {
        return toString(Locale.getDefault(), true);
    }

    /**
     * Returns a string representation of the money object using the locale and currency set.
     *
     * @param locale
     *            The locale to display the money object in
     * @param displayHTML
     *            A boolean describing whether to return HTML or ASCII text
     * @return A string representation of the money object
     */
    public String toString(Locale locale, boolean displayHTML) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        nf.setCurrency(getCurrency());
        String s = nf.format(getAmount());

        if (displayHTML) {
            s = StringEscapeUtils.escapeHtml(s);
        }
        return s;
    }

    /**
     * Negates the money amount
     *
     * @return The money object with the negated amount
     */
    public Money negate() {
        return new Money(getAmount().negate(), getCurrency());
    }

    public Money add(Money money) {
        if(money==null){
            return new Money(getAmount(), getCurrency());
        }

        if (!this.currency.equals(money.getCurrency())) {
            throw new CurrencyMismatchException();
        }
        BigDecimal newAmount = getAmount().add(money.getAmount());
        return new Money(newAmount, getCurrency());
    }

    public Money subtract(Money money) {
        if (!this.currency.equals(money.getCurrency()))
            throw new CurrencyMismatchException();

        BigDecimal newAmount = getAmount().subtract(money.getAmount());
        return new Money(newAmount, getCurrency());
    }

    public Money multiply(Money money) {
        if (!this.currency.equals(money.getCurrency())) {
            throw new CurrencyMismatchException();
        }

        BigDecimal newAmount = getAmount().multiply(money.getAmount());
        return new Money(newAmount, getCurrency());
    }

    public Money divide(Money money) {
        if (!this.currency.equals(money.getCurrency())) {
            throw new CurrencyMismatchException();
        }

        BigDecimal newAmount = getAmount().divide(money.getAmount(), 4, RoundingMode.HALF_UP);
        return new Money(newAmount, getCurrency());
    }

    public Money multiply(int quantity) {
        BigDecimal newAmount = getAmount().multiply(new BigDecimal(quantity));
        return new Money(newAmount, getCurrency());
    }

    public Money divide(int quantity) {
        BigDecimal newAmount = getAmount().divide(new BigDecimal(quantity), 4, RoundingMode.HALF_UP);
        return new Money(newAmount, getCurrency());
    }

    /**
     * Method will round the amount.
     *
     * @return Money
     */
    public Money round(RoundingType roundingType) {

        if (amount != null) {
            amount = amount.setScale(0, roundingType.getRoundingKey());
            amount = amount.setScale(2, roundingType.getRoundingKey());

        }

        return this;
    }


}

