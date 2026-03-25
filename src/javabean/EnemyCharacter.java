package javabean;

public class EnemyCharacter extends Character{
    public String skill;
    public boolean defending;

    public EnemyCharacter(String skill, boolean defending) {
        this.skill = skill;
        this.defending = defending;
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill, boolean defending) {
        super(name, HP, attack, defense);
        this.skill = skill;
        this.defending = defending;
    }
    //重写攻击造成伤害，怪有防御buff
    @Override
    public void takeDamage(int num) {
        int damage = num;
        if(defending) {
            damage = num/2 > 1 ? num/2 : 1;
            defending = false;
        }
        //调用父类的
        super.takeDamage(damage);
    }
}
