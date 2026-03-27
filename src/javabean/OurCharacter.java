package javabean;

import anlums.ui.ColorUtils;

import java.util.ArrayList;

public class OurCharacter extends Character{
    public ArrayList<String> skilllist;
    public OurCharacter() {
        super();
        skilllist = new ArrayList<>();
    }

    public OurCharacter(String name, int HP, int attack, int defense) {
        super(name, HP, attack, defense);
        skilllist = new ArrayList<>();
    }

    public void showSkill() {

    }
    public void show() {
        System.out.println( ColorUtils.purple + "名字：" + name + "【生命值"+ HP + "/"
                + maxHP +"，攻击 : " + attack
                + "，防御 : " + defense +"】" + ColorUtils.RESET );
    }

}
