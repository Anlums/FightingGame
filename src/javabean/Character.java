package javabean;

public class Character {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defense;

    public Character() {
        this.name = "无";
        this.HP = 100;
        this.maxHP = 100;
        this.attack = 10;
        this.defense = 10;
    }
    public Character(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }
    //判断是否存活
    public boolean isAlive() {
        return HP > 0;
    }
    //恢复血量
    public void heal(int num) {
        HP += num;
        if(HP > maxHP) HP = maxHP;

    }
    //攻击造成伤害
    public void takeDamage(int num) {
        HP -= num;
        if(HP < 0) HP = 0;
    }
    //展示当前属性
    public void show(){
        System.out.println("[当前生命" + HP +"，攻击" + attack + "，防御" + defense +"]");
    }
}
