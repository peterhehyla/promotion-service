package com.hylamobile.promotion.bean;

import com.hylamobile.promotion.domain.Money;
import com.hylamobile.promotion.domain.RequiresCalculation;
import com.hylamobile.promotion.dto.AdjustmentInfoDTO;

import java.io.Serializable;

/**
 * @author andrewberman
 *
 */
public class CalculatedAmountBean<T extends RequiresCalculation>  implements Serializable,
        Comparable<CalculatedAmountBean<T>> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private T object;

    private Money calculatedAmount;

    private Money calculatedTotalAmount;

    /**
     *
     */
    // INF-1404:Added adjustment info to allow adjustment based deductions.
    public CalculatedAmountBean(Money amount, T object, AdjustmentInfoDTO adjustmentInfo) {
        this.object = object;
        this.calculatedAmount = object.getCalculatedAmount(amount, adjustmentInfo);
        this.calculatedTotalAmount = amount.add(this.calculatedAmount);
    }

    /**
     * @return the object
     */
    public T getObject() {
        return object;
    }

    /**
     * Returns the calculated amount for the object. For example, if the promotion is 5% and the credit is $100, then
     * the calculated amount is $5
     *
     * @return the calculatedAmount
     */
    public Money getCalculatedAmount() {
        return calculatedAmount;
    }

    /**
     * Returns the calculated amount plus the amount originally passed in.
     *
     * @return the calculatedTotalAmount
     */
    public Money getCalculatedTotalAmount() {
        return calculatedTotalAmount;
    }

    /**
     * Allows you to add some amount to the calculated total amount
     *
     * @param staticAmount
     */
    public CalculatedAmountBean<T> addStaticAmount(Money staticAmount) {
        this.calculatedTotalAmount = this.calculatedTotalAmount.add(staticAmount);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(CalculatedAmountBean<T> o) {
        return this.getObject().compareTo(o.getObject());
    }

}

