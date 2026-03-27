package anlums.ui;

import javabean.EnemyCharacter;
import javabean.OurCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FinghtingGame {
    public void gameStart(String username) {

        System.out.println( ColorUtils.blue +  "————————————————————————————————————————" + ColorUtils.RESET);

        Scanner  sc = new Scanner(System.in);
        System.out.println(ColorUtils.yellow + "(●'◡'●)  欢迎" + username + "进入战斗界面  (●'◡'●)" + ColorUtils.RESET);
        System.out.println("请创建您的角色😉");
        System.out.println("您的角色名为：" + ColorUtils.cyan +username + ColorUtils.RESET);

        OurCharacter oc = createOurCharacter(username);

        System.out.println(  ColorUtils.purple + "角色创建成功！");
        System.out.println("⭐⭐初始属性："+username+"[HP: "+oc.HP+"/"+oc.maxHP+"，ATK: "+oc.attack+"，DEF: "+oc.defense+"]");
        System.out.println("⭐⭐拥有技能：普通攻击，强力一击，生命汲取" + ColorUtils.RESET);
        System.out.println("————————————————————————————————————————");
        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击", false));
        enemyList.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击", false));
        enemyList.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态", false));
        enemyList.add(new EnemyCharacter("神秘法师",70,25,8,"火球术",false));

        //5.准备战斗
        int counts = 0;
        int wins = 0;
        while(oc.isAlive()) {
            counts++;
            //5.1 敌人属性增加
            if(wins != 0) {
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c =  enemyList.get(i);
                    c.maxHP += 10; c.HP = c.maxHP;
                    c.attack += 3;
                    c.defense += 2;
                    c.defending = false;
                }
            }
            //5.2 随机生成敌人
            Random r = new Random();
            EnemyCharacter ec = enemyList.get(r.nextInt(enemyList.size()));
            ec.show();
            oc.show();
            System.out.println("————————————————————————————————————————");
            //5.3 开始和敌人战斗
            System.out.println( ColorUtils.green + "⚔️第 " + counts + " 场战斗开始！ 当前敌人为：" + ec.name + ColorUtils.RESET);
            System.out.println("————————————————————————————————————————");
            int round = 1;
            while(oc.isAlive()) {
                //显示双方生命值，显示回合数
                System.out.println(" ");
                System.out.println(ColorUtils.green+ "⚔️第 " + round + " 回合开始！ " + ColorUtils.RESET);
                System.out.println("------------------------------------");
                showHP(oc, ec);

                //玩家回合
                playerTurn(oc, ec);
                //判断敌方生命值是否为零
                if(!ec.isAlive()) {
                    System.out.println("👏👏👏恭喜，您已击败了" + ec.name);
                    int hpHeal = r.nextInt(20,41);
//                    oc.HP += hpHeal;
                    oc.heal(hpHeal);
                    System.out.println("💉💉战斗结束！你恢复了 " +ColorUtils.green+ hpHeal + ColorUtils.RESET+ "点生命值");
                    wins++;
                    System.out.println("🏆🏆当前胜场： " + wins);
                    System.out.println("------------------------------------");
                    round++;
                    break;
                }
                //敌方回合
                enemyTurn(oc, ec);
                if(!oc.isAlive()) {
                    round++;
                    System.out.println("☠️☠️☠️很遗憾，您被" + ec.name + "击败了");
                    System.out.println(" ");
                    break;
                }
                round++;
            }

            //玩家死亡，游戏结束
            if(!oc.isAlive()) {
                System.out.println("☠️☠️☠️游戏结束");
            }

            //玩家胜利，玩家失败时更新属性
            if(oc.isAlive() && wins > 0 && wins % 3 == 0)   playerUpgrade( oc,wins);
            System.out.println("------------------------------------");
            //询问是否继续战斗
            System.out.println("是否继续战斗？(y/n)");
            String choice = "";
            while(true) {
                choice = sc.next();
                if("n".equals( choice)) {
                    System.out.println("🎮游戏结束");
                    break;
                }else if ("y".equals(choice)) {
                    System.out.println("🎮游戏继续");
                   break;
                }else {
                    System.out.println("输入错误，请重新输入");
                    continue;
                }
            }
            if("n".equals( choice)) break;
        }

        //6.整个游戏最终结算
        System.out.println("------------------------------------");
        System.out.println("游戏结束，最终结算");
        System.out.println("⭐⭐⭐总战绩：" + username + " 战败 " + (counts - wins) + "次，战胜 " + wins + "次");
        System.out.println("谢谢游玩");

        //停止虚拟机运行
        System.exit(0);
    }

    public OurCharacter createOurCharacter(String username) {
        Scanner sc = new Scanner(System.in);
        OurCharacter oc = new OurCharacter(username, 100, 10, 10);
        int point = 20;
        String[] attribute = {"生命值", "攻击力", "防御力"};
        int[] attributePoint = {10,2,1};
        int[] value = new int[3];
        System.out.println(ColorUtils.blue +"请分配属性点数：(共" + point + "点)");
        System.out.println("1.生命值(每点 + 10HP)");
        System.out.println("2.攻击力(每点 + 2ATK)");
        System.out.println("3.防御力(每点 + 1DEF)"+ ColorUtils.RESET);
        for (int i = 0; i < attributePoint.length; i++) {
            int k;
            System.out.println("🧬🧬🧬请输入分配给"
                    + ColorUtils.red + attribute[i] + ColorUtils.RESET
                    + "的属性点：");
            while(true){
                k = sc.nextInt();
                if(k < 0) {
                    System.out.println("😔😔😔分配点数不足,默认0点");
                    continue;
                }
                if(k > point) {
                    System.out.println("😔😔😔分配点数超过当前剩余点数,请重新输入：");
                    continue;
                }
                value[i] += k*attributePoint[i];
                point -= k;
                break;
            }
            System.out.println("🫣🫣🫣" + ColorUtils.red + attribute[i] + ColorUtils.RESET
                    + "分配点数为" + ColorUtils.blue + k + ColorUtils.RESET
                    + "，剩余点数：" + ColorUtils.blue + point + ColorUtils.RESET);
        }
        oc.name = username;
        oc.HP += value[0];
        oc.maxHP += value[0];
        oc.attack += value[1];
        oc.defense += value[2];
        oc.skilllist.add("普通攻击");
        oc.skilllist.add("强力一击");
        oc.skilllist.add("生命汲取");

        return oc;
//        int k = 20;
//        int in;
//        System.out.println("请选择分配属性点");
//        System.out.println("1.生命值分配点数（每点+10HP）：");
//        while(true) {
//            in = sc.nextInt();
//            if(in < 0) {
//                System.out.println("分配点数不足,默认0点");
//                continue;
//            }
//            if(in > k) {
//                System.out.println("分配点数超过当前剩余点数,请重新输入");
//                continue;
//            }
//            oc.HP += in * 10;
//            oc.maxHP += in * 10;
//            k -= in;
//            break;
//        }
//        System.out.println("分配到HP点数为：" + in + "剩余分配点数：" + k);
//        System.out.println("2.攻击力分配点数（每点+2）：");
//       while(true) {
//           in = sc.nextInt();
//           if(in < 0) {
//               System.out.println("分配点数不足,默认0点，请重新输入");
//               continue;
//           }
//           if(in > k) {
//               System.out.println("分配点数超过当前剩余点数，请重新输入");
//               continue;
//           }
//           oc.attack += in * 2;
//           k -= in;
//           break;
//       }
//        System.out.println("分配到ATK点数为：" + in +"剩余分配点数：" + k);
//        System.out.println("3.防御力分配点数（每点+1）：");
//        while(true) {
//            in = sc.nextInt();
//            if(in < 0) {
//                System.out.println("分配点数不足,默认0点，请重新输入");
//                continue;
//            }
//            if(in > k) {
//                System.out.println("分配点数超过当前剩余点数，请重新输入");
//                continue;
//            }
//            oc.defense += in;
//            k -= in;
//            break;
//        }


    }

    //打印敌我双方血条
    public void showHP(OurCharacter oc, EnemyCharacter ec) {
        int count1 = oc.HP * 20 / oc.maxHP;
        int count2 = ec.HP * 20 / ec.maxHP;
        System.out.print(oc.name + ColorUtils.red + "：[");
        for (int i = 0; i < count1; i++) {
            System.out.print("🟦");
        }
        for (int i = count1; i < 20; i++) {
            System.out.print(" ");
        }
        System.out.println("]  " + oc.HP +" / " + oc.maxHP + "HP" + ColorUtils.RESET);
        System.out.print( ec.name + ColorUtils.red + "：[");
        for (int i = 0; i < count2; i++) {
            System.out.print("🟦");
        }
        for (int i = count2; i < 20; i++) {
            System.out.print(" ");
        }
        System.out.println("]  " + ec.HP +" / " + ec.maxHP + "HP" + ColorUtils.RESET);
    }

    //玩家回合，攻击敌人
    public void playerTurn(OurCharacter oc, EnemyCharacter ec) {
        System.out.println("====你的回合，请选择技能====");
        System.out.println("⚔️⚔️1.普通攻击");
        System.out.println("⚔️️⚔️2.强力一击");
        System.out.println("⚔️⚔️3.生命汲取");
        Scanner sc = new Scanner(System.in);
        System.out.println("请选择行动：");
        int choice = sc.nextInt();
        switch (choice) {
            default:
                System.out.println("😔😔没有这个操作，默认为普通攻击");
            case 1:
                System.out.println(oc.name + "使用了普通攻击，攻击力为" + oc.attack);
                int damage = countDamage(oc.attack, ec.defense);
                ec.takeDamage(damage);
                if (ec.defending) {
                    damage /= 2;
                    System.out.println("️⚔️" + ColorUtils.blue + ec.name
                            + "使用了普通攻击,由于敌人拥有防御buff，因此你对敌人造成了" + damage
                            + "伤害" + ColorUtils.RESET);
                }
                else System.out.println("️⚔️你对" + ColorUtils.blue +  ec.name
                        + "使用了普通攻击" + "造成了" + damage
                        + "伤害" + ColorUtils.RESET);

                break;
            case 2:
                if(oc.HP > 10) {
//                    oc.HP -= 10;
                    oc.takeDamage(10);
                    System.out.println(oc.name +ColorUtils.red +  "💥💥消耗10点生命，"
                            + ColorUtils.RESET +   "使用强力一击，攻击力为" + oc.attack*2);

                    int damage1 = countDamage(oc.attack*2, ec.defense);
                    ec.takeDamage(damage1);
                    if (ec.defending) {
                        damage1 /= 2;
                        System.out.println("️⚔️" + ec.name + ColorUtils.blue +
                                "使用了强力一击,由于敌人拥有防御buff，因此你对敌人造成了" + damage1
                                + "伤害" + ColorUtils.RESET);
                    }
                    else System.out.println("️⚔️你对" + ec.name + ColorUtils.blue
                            + "使用了强力一击" + "造成了" + damage1
                            + "伤害" + ColorUtils.RESET);
                    break;
                }
                else {
                    System.out.println( "😔😔" + oc.name + "的HP不足10，无法使用强力一击技能");
                    break;
                }
            case 3:
                if(oc.HP > 10) {
                    oc.takeDamage(10);
                    System.out.println(oc.name + "使用了生命汲取");
                    Random r = new Random();
                    int c = r.nextInt(10) +11;
                    oc.HP += c;
                    System.out.println(oc.name + ColorUtils.green + "💉💉恢复了" + c + "点HP" + ColorUtils.RESET);
                    break;
                }
                else  {
                    System.out.println( "😔😔" + oc.name + "的HP不足10，无法使用生命汲取技能");
                    break;
                }
        }

    }
    //造成伤害的数值
    public int countDamage(int attack, int defense) {
        int damage = attack - defense;
        if(damage < 0) damage = 0;
        return damage;
    }



    //敌方回合
    public void enemyTurn(OurCharacter oc, EnemyCharacter ec) {
        System.out.println("=====" + ec.name + "的回合=====");
        String action = "普通攻击";
        Random r = new Random();
        int choice = r.nextInt(2);
        if(choice == 1) {
            if(ec.name == "初级战士") action = "猛击";
            if(ec.name == "敏捷刺客") action = "快速攻击";
            if(ec.name == "重装坦克") action = "防御姿态";
            if(ec.name == "神秘法师") action = "火球术";

        }
        switch ( action) {
            case "普通攻击":
                System.out.println("⚔️当前敌人使用了普通攻击");
                int damage1= countDamage(ec.attack, oc.defense);
                System.out.println( "⚔️" +  ec.name + "使用了普通攻击，对你造成" + damage1 + "伤害");
                oc.takeDamage(damage1);
                break;
            case "猛击":
                System.out.println("⚔️当前敌人使用了猛击");
                int damage2= countDamage((int) (ec.attack*1.5), oc.defense);
                System.out.println( "⚔️" +  ec.name + "使用了猛击，对你造成" + damage2 + "伤害");
                oc.takeDamage(damage2);
                break;
            case "快速攻击":
                System.out.println("⚔️当前敌人使用了快速攻击");
                int damage3= 0;
                for(int i = 0; i < 3; i++) {
                    damage3 += countDamage(ec.attack/2, oc.defense);
                }
                System.out.println( "⚔️" +  ec.name + "使用了快速攻击，对你造成" + damage3 + "伤害");
                oc.takeDamage(damage3);
                break;
            case "防御姿态":
                System.out.println("🛡️当前敌人使用了防御姿态");
                ec.defending = true;
                System.out.println( "🛡️" +  ec.name + "使用了防御姿态");
                break;
            case "火球术":
                System.out.println("🔥当前敌人使用了火球术");
                int damage4= countDamage((int) (ec.attack*1.8), oc.defense);
                System.out.println( "🔥" +  ec.name + "使用了火球术，对你造成" + damage4 + "伤害");
                oc.takeDamage(damage4);
                break;
        }


    }

    //玩家属性提升
    public void playerUpgrade(OurCharacter oc, int wins) {
        System.out.println("👏👏👏" + " 恭喜你赢得 " + wins + " 场胜利，属性提升");
        oc.HP += 50;
        oc.maxHP += 50;
        oc.attack += 10;
        oc.defense += 10;
        System.out.println(ColorUtils.purple +  oc.name + "当前属性为：" +" HP: " + oc.HP + "/" + oc.maxHP
                + "，ATK: " + oc.attack + ",defense :" +oc.defense + ColorUtils.RESET );
    }

}
