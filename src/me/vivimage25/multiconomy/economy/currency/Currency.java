package me.vivimage25.multiconomy.economy.currency;

import java.util.List;

public interface Currency {
    
    public boolean isActive();
    public Currency setActive(boolean active);
    public String getName();
    public void setName(String name);
    public String getPrefix();
    public void setPrefix(String prefix);
    public String getSuffix();
    public void setSuffix(String suffix);
    public List<String> getDescription();
    public void setDescription(String... description);
    public CurrencyType getType();
    public double getValue();
    public void setValue(double value);
    public double getMaxBalance();
    public void setMaxBalance(double max_balance);
    
}
