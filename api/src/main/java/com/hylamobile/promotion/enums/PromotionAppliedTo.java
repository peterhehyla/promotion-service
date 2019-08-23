package com.hylamobile.promotion.enums;

public enum PromotionAppliedTo {
    TRADE_POST_INSPECTION("label.promotion.applied.tradePostInspection", true, true),
    TRADE_IN_VALUE("label.promotion.applied.tradeInValue", true, false);

    private String displayKey;

    /**
     * Promotion should apply in CE
     */
    private boolean shouldApplyPromotionAtTrade;

    private boolean shouldApplyPromotionAtInspection;

    PromotionAppliedTo(String displayKey, boolean shouldApplyPromotionAtTrade,
            boolean shouldApplyPromotionAtInspection) {
        this.displayKey = displayKey;
        this.shouldApplyPromotionAtTrade = shouldApplyPromotionAtTrade;
        this.shouldApplyPromotionAtInspection = shouldApplyPromotionAtInspection;
    }

    public String getDisplayKey() {
        return displayKey;
    }

    public boolean isShouldApplyPromotionAtTrade() {
        return shouldApplyPromotionAtTrade;
    }

    public boolean isShouldApplyPromotionAtInspection() {
        return shouldApplyPromotionAtInspection;
    }
}
