package com.hylamobile.trade;

import java.math.BigDecimal;
import java.util.Currency;

public class CalculationFactory {
    public static Calculation create(CalculationType calculationType, BigDecimal value, Currency currency){
        Calculation calculation;
        switch (calculationType){
        case ADDITION:
            calculation = new Calculation.AdditionCalculation(value, currency);
            break;
        case FLAT:
            calculation = new Calculation.FlatCalculation(value, currency);
            break;
        case PERCENTAGE:
            calculation = new Calculation.PercentageCalculation(value, currency);
            break;
        case FLOOR:
            calculation = new Calculation.FloorCalculation(value, currency);
            break;
        case MAXIMUM:
            calculation = new Calculation.MaximumCalculation(value, currency);
            break;
        case MINIMUM:
            calculation = new Calculation.MinimumCalculation(value, currency);
            break;
        default:
            calculation = null;
        }
        return calculation;
    }
    public static Calculation create(String calculationTypeCode, BigDecimal value, String currencyCode){
        Currency currency = Currency.getInstance(currencyCode);
        CalculationType calculationType = CalculationType.valueOf(calculationTypeCode);
        return create(calculationType, value, currency);
    }
}
