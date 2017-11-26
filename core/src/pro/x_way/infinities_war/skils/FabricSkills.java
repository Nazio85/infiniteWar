package pro.x_way.infinities_war.skils;


import java.util.List;


public class FabricSkills {
    private static List<Skill> skillList;

    public static Skill getSkill(SkillType skillType) {
        switch (skillType) {
            case actionMeleeAttack:
                return new MeleeAttackSkill();
            case rest:
                return new RestSkill();
            case defence:
                return new Defence();
            default:
                return null;
        }
    }

    public enum SkillType {
        actionMeleeAttack, rest, defence
    }
}
