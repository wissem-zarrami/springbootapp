package com.inpocket.vitaverse.store.entity;

public enum ProductCategory {
    SPORTS_EQUIPMENT("Sports Equipment"),
    FITNESS_ACCESSORIES("Fitness Accessories"),
    GYM_APPAREL("Gym Apparel"),
    NUTRITIONAL_SUPPLEMENTS("Nutritional Supplements"),
    FITNESS_TRACKERS("Fitness Trackers"),
    SPORTS_NUTRITION("Sports Nutrition"),
    YOGA_ACCESSORIES("Yoga Accessories"),
    RECOVERY_EQUIPMENT("Recovery Equipment"),
    OUTDOOR_ACTIVITIES("Outdoor Activities");

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

