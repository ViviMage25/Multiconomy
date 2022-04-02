package me.vivimage25.multiconomy.economy.currency;

import java.util.List;

public final class VirtualCurrency implements Currency {

    private boolean active;
    private String name;
    private String prefix;
    private String suffix;
    private double value;
    private double max_balance;
    private List<String> description;

    public VirtualCurrency(String name, String prefix, String suffix, double value, double max_balance, String... description) {
        setName(name);
        setPrefix(prefix);
        setSuffix(suffix);
        setValue(value);
        setMaxBalance(max_balance);
        if (description != null) {
            setDescription(description);
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public VirtualCurrency setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public List<String> getDescription() {
        return description;
    }

    @Override
    public void setDescription(String... description) {
        this.description = List.of(description);
    }

    @Override
    public CurrencyType getType() {
        return CurrencyType.VIRTUAL;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double getMaxBalance() {
        return max_balance;
    }

    @Override
    public void setMaxBalance(double max_balance) {
        this.max_balance = max_balance;
    }

}
