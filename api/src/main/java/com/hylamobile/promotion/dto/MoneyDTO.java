package com.hylamobile.promotion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

import com.hylamobile.trade.exceptions.CurrencyMismatchException;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDTO implements Serializable,Comparable<MoneyDTO> {

    private static final long serialVersionUID = 5205464988575653023L;

    private BigDecimal amount = BigDecimal.ZERO;

    private String currencyCode;

    public MoneyDTO(BigDecimal amount){
        this.amount = amount;
    }

    public static final MoneyDTO newZeroInstance(Currency currency) {
        return new MoneyDTO(BigDecimal.ZERO, currency.getCurrencyCode());
    }

    public static final MoneyDTO newZeroInstance() {
        return new MoneyDTO(BigDecimal.ZERO);
    }

    public static final MoneyDTO newZeroInstance(String currencyCode) {
        return new MoneyDTO(BigDecimal.ZERO, currencyCode);
    }
    public MoneyDTO add(MoneyDTO money) {
        if(money==null){
            return new MoneyDTO(getAmount(), getCurrencyCode());
        }

        if (!this.currencyCode.equals(money.getCurrencyCode())) {
            throw new CurrencyMismatchException();
        }

        BigDecimal newAmount = getAmount().add(money.getAmount());
        return new MoneyDTO(newAmount, getCurrencyCode());
    }

    public MoneyDTO subtract(MoneyDTO money) {
        if (!this.currencyCode.equals(money.getCurrencyCode())) {
            throw new CurrencyMismatchException();
        }

        BigDecimal newAmount = getAmount().subtract(money.getAmount());
        return new MoneyDTO(newAmount, getCurrencyCode());
    }
    @Override
    public int compareTo(MoneyDTO money) {
        if (!this.currencyCode.equals(money.getCurrencyCode())) {
            throw new CurrencyMismatchException();
        }

        return this.amount.compareTo(money.getAmount());
    }
    public int compareTo(BigDecimal amount) {
        return this.amount.compareTo(amount);
    }
}
