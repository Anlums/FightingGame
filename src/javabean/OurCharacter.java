package javabean;

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


}
