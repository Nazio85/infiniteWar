package pro.x_way.infinities_war.units;

/**
 * Created by FlameXander on 13.11.2017.
 */

public class Stats implements Cloneable {
    private int baseStrength;
    private int baseDexterity;
    private int baseEndurance;
    private int baseDefence;
    private int baseSpellPower;
    private int baseExperience;

    private int strength;
    private int dexterity;
    private int endurance;
    private int defence;
    private int spellpower;
    private int experience;

    private int strengthPerLevel;
    private int dexterityPerLevel;
    private int endurancePerLevel;
    private int defencePerLevel;
    private int spellPowerPerLevel;
    private int experiencePerLevel;

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getBaseStrength() {
        return baseStrength;
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
    }

    public int getBaseDexterity() {
        return baseDexterity;
    }

    public void setBaseDexterity(int baseDexterity) {
        this.baseDexterity = baseDexterity;
    }

    public int getBaseEndurance() {
        return baseEndurance;
    }

    public void setBaseEndurance(int baseEndurance) {
        this.baseEndurance = baseEndurance;
    }

    public int getBaseDefence() {
        return baseDefence;
    }

    public void setBaseDefence(int baseDefence) {
        this.baseDefence = baseDefence;
    }

    public int getBaseSpellPower() {
        return baseSpellPower;
    }

    public void setBaseSpellPower(int baseSpellPower) {
        this.baseSpellPower = baseSpellPower;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getSpellpower() {
        return spellpower;
    }

    public void setSpellpower(int spellpower) {
        this.spellpower = spellpower;
    }

    public int getStrengthPerLevel() {
        return strengthPerLevel;
    }

    public void setStrengthPerLevel(int strengthPerLevel) {
        this.strengthPerLevel = strengthPerLevel;
    }

    public int getDexterityPerLevel() {
        return dexterityPerLevel;
    }

    public void setDexterityPerLevel(int dexterityPerLevel) {
        this.dexterityPerLevel = dexterityPerLevel;
    }

    public int getEndurancePerLevel() {
        return endurancePerLevel;
    }

    public void setEndurancePerLevel(int endurancePerLevel) {
        this.endurancePerLevel = endurancePerLevel;
    }

    public int getDefencePerLevel() {
        return defencePerLevel;
    }

    public void setDefencePerLevel(int defencePerLevel) {
        this.defencePerLevel = defencePerLevel;
    }

    public int getSpellPowerPerLevel() {
        return spellPowerPerLevel;
    }

    public void setSpellPowerPerLevel(int spellPowerPerLevel) {
        this.spellPowerPerLevel = spellPowerPerLevel;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Stats(int level, int baseStrength, int baseDexterity, int baseEndurance, int baseDefence,
                 int baseSpellPower, int baseExperience, int strengthPerLevel, int dexterityPerLevel,
                 int endurancePerLevel, int defencePerLevel, int spellPowerPerLevel, int experiencePerLevel) {
        this.baseStrength = baseStrength;
        this.baseDexterity = baseDexterity;
        this.baseEndurance = baseEndurance;
        this.baseDefence = baseDefence;
        this.baseSpellPower = baseSpellPower;
        this.baseExperience = baseExperience; 
        this.strengthPerLevel = strengthPerLevel;
        this.dexterityPerLevel = dexterityPerLevel;
        this.endurancePerLevel = endurancePerLevel;
        this.defencePerLevel = defencePerLevel;
        this.spellPowerPerLevel = spellPowerPerLevel;
        this.experiencePerLevel = experiencePerLevel; 
        this.recalculate(level);
    }

    public void recalculate(int level) {
        strength = baseStrength + ((level - 1) * strengthPerLevel);
        dexterity = baseDexterity + ((level - 1) * dexterityPerLevel);
        endurance = baseEndurance + ((level - 1) * endurancePerLevel);
        defence = baseDefence + ((level - 1) * defencePerLevel);
        spellpower = baseSpellPower + ((level - 1) * spellPowerPerLevel);
        experience = baseExperience + ((level - 1) * experiencePerLevel);
    }
}