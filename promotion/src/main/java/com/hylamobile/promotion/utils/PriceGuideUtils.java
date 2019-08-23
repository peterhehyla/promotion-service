package com.hylamobile.promotion.utils;

import com.hylamobile.promotion.domain.Money;
import com.hylamobile.promotion.dto.AdjustmentDTO;
import com.hylamobile.promotion.dto.AdjustmentInfoDTO;
import com.hylamobile.promotion.enums.CategoryQuestionCode;
import com.hylamobile.trade.Calculation;
import com.hylamobile.trade.CalculationFactory;
import com.hylamobile.trade.CalculationType;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static com.hylamobile.promotion.enums.AdjustmentCalculationType.CUMMULATIVE;
import static com.hylamobile.promotion.enums.AdjustmentCalculationType.CUSTOMADJUSTMENT;
import static com.hylamobile.promotion.enums.AdjustmentCalculationType.MAXADJUSTMENT;
import static com.hylamobile.promotion.enums.AdjustmentCalculationType.MAXDEDUCTIBLE;
import static com.hylamobile.promotion.enums.AdjustmentCalculationType.MIXADJUSTMENT;
import static com.hylamobile.promotion.enums.AdjustmentCalculationType.POSTINSPECTION;

public class PriceGuideUtils {
    // INF-1404: Moved this method to util class so that promo amount can be recalculated in domain class
    public static Money getResponsesAmount(Money credit, AdjustmentInfoDTO adjustmentInfo, boolean isPostFlipswapPercentage) {
        if (adjustmentInfo == null || StringUtils.isBlank(adjustmentInfo.getAdjustmentType())
                || adjustmentInfo.getAdjustments() == null || adjustmentInfo.getAdjustments().size() == 0) {
            return Money.newZeroInstance();
        } else {
            if (adjustmentInfo.getAdjustmentType().equals(POSTINSPECTION.toString())) {
                return getResponsesAmountPostInspection(credit, adjustmentInfo.getAdjustments());
            }if (adjustmentInfo.getAdjustmentType().equals(MAXADJUSTMENT.toString())) {
                return getResponsesAmountMaxAdustment(credit, adjustmentInfo.getAdjustments(), isPostFlipswapPercentage);
            }
            else if (adjustmentInfo.getAdjustmentType().equals(CUMMULATIVE.toString())) {
                return getResponsesAmountCummulative(credit, adjustmentInfo.getAdjustments(), isPostFlipswapPercentage);
            }
            else if (adjustmentInfo.getAdjustmentType().equals(MIXADJUSTMENT.toString())) {
                return getResponsesAmountMixAdustment(credit, adjustmentInfo, isPostFlipswapPercentage);
            }
            else if (adjustmentInfo.getAdjustmentType().equals(CUSTOMADJUSTMENT.toString())) {
                return getResponsesAmountCustomAdjustment(credit, adjustmentInfo.getAdjustments(),
                        isPostFlipswapPercentage);
            }
            else if (adjustmentInfo.getAdjustmentType().equals(MAXDEDUCTIBLE.toString())) {
                return getResponsesAmountMaxDeductableValue(credit, adjustmentInfo, isPostFlipswapPercentage);
            }
            return getResponsesAmountMaxAdustment(credit, adjustmentInfo.getAdjustments(), isPostFlipswapPercentage);
        }
    }
    private static Money getResponsesAmountPostInspection(Money credit, List<AdjustmentDTO> adjustments) {
        Money creditMods = Money.newZeroInstance(credit.getCurrency());
        if (adjustments != null) {
            // Not using collectionutils because we need to do the loop
            // anyway, so no point in doing double the loops
            for (AdjustmentDTO adjustment : adjustments) {
                if (adjustment != null && adjustment.getCalculationAmount() != null
                        && adjustment.getCalculationType().equals(CalculationType.PERCENTAGE)) {
                    creditMods = creditMods.add(CalculationFactory
                            .create(adjustment.getCalculationType(), adjustment.getCalculationAmount(), adjustment.getCurrency()).eval(credit));
                }
            }
            return creditMods;
        }

        return creditMods;
    }
    private static Money getResponsesAmountMaxAdustment(Money credit, List<AdjustmentDTO> adjustments,
            boolean isPostFlipswapPercentage) {
        Money creditMods = Money.newZeroInstance(credit.getCurrency());
        if (adjustments != null) {
            // Not using collectionutils because we need to do the loop
            // anyway, so no point in doing double the loops
            TreeSet<Money> calValues = new TreeSet<>();
            for (AdjustmentDTO adj : adjustments) {
                if (adj != null && StringUtils.isNotBlank(adj.getQuestionCode()) && adj.getCalculationAmount() != null
                        && adj.getPostFlipswapPercentage().booleanValue() == isPostFlipswapPercentage) {
                    calValues.add(CalculationFactory.create(adj.getCalculationType(), adj.getCalculationAmount(), adj.getCurrency()).eval(credit));
                }
            }
            if (calValues.size() > 0) {
                // Picked the first element from calValues for max value because when money amounts are compared -77 <
                // -5 hence will be placed first in treeset.
                creditMods = creditMods.add(calValues.first());
            }
            return creditMods;
        }
        return creditMods;
    }
    private static Money getResponsesAmountCummulative(Money credit, List<AdjustmentDTO> adjustments,
            boolean isPostFlipswapPercentage) {
        Money creditMods = Money.newZeroInstance(credit.getCurrency());
        if (adjustments != null) {
            // Not using collectionutils because we need to do the loop
            // anyway, so no point in doing double the loops
            for (AdjustmentDTO adjustment : adjustments) {
                if (adjustment != null && StringUtils.isNotBlank(adjustment.getQuestionCode())
                        && adjustment.getCalculationAmount() != null
                        && adjustment.getPostFlipswapPercentage().booleanValue() == isPostFlipswapPercentage) {
                    creditMods = creditMods.add(CalculationFactory.create(adjustment.getCalculationType(), adjustment.getCalculationAmount(), adjustment.getCurrency()).eval(credit));
                }
            }
            return creditMods;
        }

        return creditMods;
    }
    private static Money getResponsesAmountCustomAdjustment(Money credit, List<AdjustmentDTO> adjustments,
            boolean isPostFlipswapPercentage) {

        Money creditMods = Money.newZeroInstance(credit.getCurrency());
        if (adjustments != null) {
            boolean flag_Power_on_Lcd_Deactivated = true;
            boolean flag_No_Damage_No_Screen_Damage = true;
            boolean flag_Lcd_Functionality = true;
            Map<String, Calculation> questionsMap = new HashMap<String, Calculation>();
            for (AdjustmentDTO adjustment : adjustments) {
                if (adjustment != null && StringUtils.isNotBlank(adjustment.getQuestionCode())
                        && adjustment.getCalculationAmount() != null
                        && adjustment.getPostFlipswapPercentage().booleanValue() == isPostFlipswapPercentage
                        && adjustment.getCalculationAmount().compareTo(BigDecimal.ZERO) != 0) {
                    MapUtils.safeAddToMap(questionsMap, adjustment.getQuestionCode(), CalculationFactory.create(adjustment.getCalculationType(), adjustment.getCalculationAmount(), adjustment.getCurrency()));

                }
            }

            if (questionsMap.get(CategoryQuestionCode.DEACTIVATED.toString()) != null
                    && questionsMap.get(CategoryQuestionCode.LCD_FUNCTIONALITY.toString()) != null) {
                creditMods = creditMods.add((questionsMap.get(CategoryQuestionCode.DEACTIVATED.toString()))
                        .eval(credit));
                creditMods = creditMods.add((questionsMap.get(CategoryQuestionCode.LCD_FUNCTIONALITY.toString()))
                        .eval(credit));
                return creditMods;
            }

            if (questionsMap.get(CategoryQuestionCode.LCD_FUNCTIONALITY.toString()) != null
                    && questionsMap.get(CategoryQuestionCode.NO_DAMAGE.toString()) != null) {
                creditMods = creditMods.add((questionsMap.get(CategoryQuestionCode.LCD_FUNCTIONALITY.toString()))
                        .eval(credit));
                creditMods = creditMods
                        .add((questionsMap.get(CategoryQuestionCode.NO_DAMAGE.toString())).eval(credit));
                return creditMods;
            }

            if (flag_Power_on_Lcd_Deactivated && questionsMap.get(CategoryQuestionCode.POWER_ON.toString()) != null) {
                creditMods = creditMods.add((questionsMap.get(CategoryQuestionCode.POWER_ON.toString())).eval(credit));
                flag_Power_on_Lcd_Deactivated = false;
            }

            if (flag_Power_on_Lcd_Deactivated && questionsMap.get(CategoryQuestionCode.DEACTIVATED.toString()) != null) {
                creditMods = creditMods.add((questionsMap.get(CategoryQuestionCode.DEACTIVATED.toString()))
                        .eval(credit));
                flag_Power_on_Lcd_Deactivated = false;
            }

            if (flag_Power_on_Lcd_Deactivated
                    && questionsMap.get(CategoryQuestionCode.LCD_FUNCTIONALITY.toString()) != null) {
                creditMods = creditMods.add((questionsMap.get(CategoryQuestionCode.LCD_FUNCTIONALITY.toString()))
                        .eval(credit));
                flag_Lcd_Functionality = false;
            }

            if (flag_No_Damage_No_Screen_Damage && questionsMap.get(CategoryQuestionCode.NO_DAMAGE.toString()) != null) {
                creditMods = creditMods
                        .add((questionsMap.get(CategoryQuestionCode.NO_DAMAGE.toString())).eval(credit));
                flag_No_Damage_No_Screen_Damage = false;
            }

            if (flag_No_Damage_No_Screen_Damage
                    && questionsMap.get(CategoryQuestionCode.NO_SCREEN_DAMAGE.toString()) != null) {
                if (flag_Lcd_Functionality) {
                    creditMods = creditMods.add((questionsMap.get(CategoryQuestionCode.NO_SCREEN_DAMAGE.toString()))
                            .eval(credit));
                }
            }

            if (questionsMap.get(CategoryQuestionCode.BATTERY_PRESENT.toString()) != null) {
                creditMods = creditMods.add((questionsMap.get(CategoryQuestionCode.BATTERY_PRESENT.toString()))
                        .eval(credit));
            }

        }
        return creditMods;
    }


    /**
     * Very similar to the cummulative method. However, it takes the overall negative credit value
     * into account when returning the credit mods. If the negative value (-[0 to 100]) provided
     * as the minimum is surpassed by the total negative amount of the credit mods, then it takes the
     * minimum value.
     * @param credit
     * @param adjustmentInfo
     * @param isPostFlipswapPercentage
     * @return
     */
    private static Money getResponsesAmountMaxDeductableValue(Money credit, AdjustmentInfoDTO adjustmentInfo,
            boolean isPostFlipswapPercentage) {

        BigDecimal totalAdjustment = new BigDecimal(0);
        Money creditMods = Money.newZeroInstance(credit.getCurrency());
        if (adjustmentInfo.getAdjustments() != null) {
            for (AdjustmentDTO adjustment : adjustmentInfo.getAdjustments()) {
                if (adjustment != null && StringUtils.isNotBlank(adjustment.getQuestionCode())
                        && adjustment.getCalculationAmount() != null
                        && adjustment.getPostFlipswapPercentage().booleanValue() == isPostFlipswapPercentage) {
                    creditMods = creditMods.add(CalculationFactory.create(adjustment.getCalculationType(), adjustment.getCalculationAmount(), adjustment.getCurrency()).eval(credit));
                    totalAdjustment = totalAdjustment.add(adjustment.getCalculationAmount());
                }
            }
            if(totalAdjustment.compareTo(new BigDecimal(adjustmentInfo.getMaxDeductiblePercentage())) < 0){
                creditMods = new Money(new BigDecimal(adjustmentInfo.getMaxDeductiblePercentage()).multiply(new BigDecimal(.01)).multiply(credit.getAmount()), credit.getCurrency());
            }
        }

        return creditMods;
    }
    private static Money getResponsesAmountMixAdustment(Money credit, AdjustmentInfoDTO adjustmentInfo,
            boolean isPostFlipswapPercentage) {
        List<AdjustmentDTO> adjustments = adjustmentInfo.getAdjustments();
        String mixAdjustmentQuestions = adjustmentInfo.getMixAdjustmentQuestions();
        Money creditMods = Money.newZeroInstance(credit.getCurrency());
        // To maintain the sequence of group of questions codes against their calculated credit value
        Map<Integer, List<String>> questionsGroupsMap = new HashMap<Integer, List<String>>();
        // For different questions groups, having separate money object
        Money[] questionsGroupCredit = null;

        // Question groups empty check
        if (StringUtils.isNotEmpty(mixAdjustmentQuestions)) {
            // Split the question groups, which are separated by ";"
            String questionsCodeGroups[] = StringUtils.trim(mixAdjustmentQuestions).split(";");
            questionsGroupCredit = new Money[questionsCodeGroups.length];
            for (int index = 0; index < questionsCodeGroups.length; index++) {
                // Split every question group in different question codes, which are separated by "|"
                String[] questionCodeArray = questionsCodeGroups[index].split("\\|");
                // Prepare list of question codes for individual group
                List<String> questionsCodes = new ArrayList<String>();
                for (String questionCode : questionCodeArray) {
                    questionsCodes.add(questionCode);
                }
                // Adding question code lists to map with their id, in order to link with questionsGroupCredit object
                questionsGroupsMap.put(index, questionsCodes);
            }
        }
        // Check item responses object is not null
        if (adjustments != null) {
            // Iterate over each question response
            for (AdjustmentDTO adjustment : adjustments) {
                if (adjustment != null && StringUtils.isNotBlank(adjustment.getQuestionCode())
                        && adjustment.getCalculationAmount() != null
                        && adjustment.getPostFlipswapPercentage().booleanValue() == isPostFlipswapPercentage) {
                    boolean questionProcessedflag = false;
                    if (questionsGroupsMap.size() > 0) {
                        // Iterate over keys of questionsGroupsMap, which are index values for lists
                        for (Integer key : questionsGroupsMap.keySet()) {
                            // Get the corresponding question code list
                            List<String> questionsCodes = questionsGroupsMap.get(key);
                            // Check if question code list contains the current question
                            if (questionsCodes.contains(adjustment.getQuestionCode())) {
                                // Check for negative response
                                if (!adjustment.getIsDefaultResponse()) {
                                    // If money object to corresponding question code list is null
                                    if (questionsGroupCredit[key] == null) {
                                        // Assign money object
                                        questionsGroupCredit[key] = CalculationFactory.create(adjustment.getCalculationType(), adjustment.getCalculationAmount(), adjustment.getCurrency()).eval(credit);
                                    }
                                    /*
                                     * Override the value of money object, if question code is the first one in the
                                     * mentioned order Note: For a group of questions codes, if all have different
                                     * values, then the adjustment values from the first question code mentioned in the
                                     * list will be used
                                     */
                                    else if (adjustment.getQuestionCode().equals(questionsCodes.get(0))) {
                                        // Assign money object
                                        questionsGroupCredit[key] = CalculationFactory.create(adjustment.getCalculationType(), adjustment.getCalculationAmount(), adjustment.getCurrency()).eval(credit);
                                    }
                                    // Set flag as true to bypass normal processing
                                    questionProcessedflag = true;
                                    break; // Break the loop once the question code present in list is processed
                                }
                            }
                        }
                    }
                    // Default processing(as CUMMULATIVE), if question response is not processed yet
                    if (!questionProcessedflag) {
                        // Add money object
                        creditMods = creditMods.add(CalculationFactory.create(adjustment.getCalculationType(), adjustment.getCalculationAmount(), adjustment.getCurrency()).eval(credit));
                    }
                }
            }
        }
        // Add the money objects calculated against different question groups
        for (Money money : questionsGroupCredit) {
            if (money != null) {
                creditMods = creditMods.add(money);
            }
        }
        return creditMods;
    }

}
