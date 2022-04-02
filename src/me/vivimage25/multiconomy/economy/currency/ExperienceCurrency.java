package me.vivimage25.multiconomy.economy.currency;

import java.util.List;

public final class ExperienceCurrency implements Currency {

    private boolean active;
    private double value;

    public ExperienceCurrency(double value) {
        setValue(value);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public ExperienceCurrency setActive(boolean active) {
        active = active;
        return this;
    }

    @Override
    public String getName() {
        return "experience";
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public void setPrefix(String prefix) {
    }

    @Override
    public String getSuffix() {
        return "exp";
    }

    @Override
    public void setSuffix(String suffix) {
    }

    @Override
    public List<String> getDescription() {
        return List.of("Minecraft Experience Levels");
    }

    @Override
    public void setDescription(String... description) {
    }

    @Override
    public CurrencyType getType() {
        return CurrencyType.EXPERIENCE;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        value = value;
    }

    @Override
    public double getMaxBalance() {
        return 238609311;
    }

    @Override
    public void setMaxBalance(double max_balance) {
    }

}
