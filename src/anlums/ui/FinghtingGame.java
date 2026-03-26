package anlums.ui;

import javabean.EnemyCharacter;
import javabean.OurCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FinghtingGame {
    public void gameStart(String username) {
        Scanner  sc = new Scanner(System.in);
        System.out.println("欢迎" + username + "进入战斗界面");
        System.out.println("创建您的角色");
        System.out.println("您的角色名为：" + username);

        OurCharacter oc = createOurCharacter(username);

        System.out.println("角色创建成功！");
        System.out.println("初始属性："+username+"[HP: "+oc.HP+"/"+oc.maxHP+"，ATK: "+oc.attack+"，DEF: "+oc.defense+"]");
        System.out.println("拥有技能：普通攻击，强力一击，生命汲取");

        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击", false));
        enemyList.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击", false));
        enemyList.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态", false));
        enemyList.add(new EnemyCharacter("神秘法师",70,25,8,"火球术",false));

        //5.准备战斗
        int counts = 1;
        int wins = 0;
        while(oc.isAlive()) {
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
            //5.3 开始和敌人战斗
            System.out.println("第" + counts + " 场战斗开始，当前敌人为：" + ec.name );
            int round = 1;
            while(oc.isAlive()) {
                //显示双方生命值
                System.out.println("第" + round + "回合 " );
                showHP(oc, ec);

                //玩家回合
                playerTurn(oc, ec);
                //敌方回合


            }


        }


    }

    public OurCharacter createOurCharacter(String username) {
        Scanner sc = new Scanner(System.in);
        OurCharacter oc = new OurCharacter(username, 100, 10, 10);
        int point = 20;
        String[] attribute = {"生命值", "攻击力", "防御力"};
        int[] attributePoint = {10,2,1};
        int[] value = new int[3];
        System.out.println("请分配属性点数：" + point);
        for (int i = 0; i < attributePoint.length; i++) {
            int k;
            System.out.println("请输入分配给" + attribute[i] + "的属性点：");
            while(true){
                k = sc.nextInt();
                if(k < 0) {
                    System.out.println("分配点数不足,默认0点");
                    continue;
                }
                if(k > point) {
                    System.out.println("分配点数超过当前剩余点数,请重新输入：");
                    continue;
                }
                value[i] += k*attributePoint[i];
                point -= k;
                break;
            }
            System.out.println(attribute[i] + "分配点数为" + k + "，剩余点数：" + point);
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
        System.out.print(oc.name + "：[");
        for (int i = 0; i < count1; i++) {
            System.out.print("🟦");
        }
        for (int i = count1; i < 20; i++) {
            System.out.print(" ");
        }
        System.out.println("]  " + oc.HP +" / " + oc.maxHP + "HP");
        System.out.print( ec.name +  "：[");
        for (int i = 0; i < count2; i++) {
            System.out.print("🟦");
        }
        for (int i = count2; i < 20; i++) {
            System.out.print(" ");
        }
        System.out.println("]  " + ec.HP +" / " + ec.maxHP + "HP");
    }

    //玩家回合，攻击敌人
    public void playerTurn(OurCharacter oc, EnemyCharacter ec) {
        System.out.println("====你的回合，请选择技能====");
        System.out.println("1.普通攻击");
        System.out.println("2.强力一击");
        System.out.println("3.生命汲取");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            default:
                System.out.println("没有这个操作，默认为普通攻击");
            case 1:
                System.out.println(oc.name + "使用了普通攻击，攻击力为" + oc.attack);
                ec.takeDamage(countDamage(oc.attack, ec.defense));
                System.out.println("你对" + ec.name + "使用了普通攻击" + "造成了" + countDamage(oc.attack, ec.defense) + "伤害");

                break;
            case 2:
                if(oc.HP > 10) {
//                    oc.HP -= 10;
                    oc.takeDamage(10);
                    System.out.println(oc.name + "使用了强力一击，攻击力为" + oc.attack*2);
                    ec.takeDamage(countDamage(oc.attack*2, ec.defense));
                    System.out.println("你对" + ec.name + "使用了强力一击" + "造成了" + countDamage(oc.attack*2, ec.defense) + "伤害");
                    break;
                }
                else {
                    System.out.println(oc.name + "的HP不足10，无法使用强力一击技能");
                    break;
                }
            case 3:
                if(oc.HP > 10) {
                    oc.takeDamage(10);
                    System.out.println(oc.name + "使用了生命汲取");
                    Random r = new Random();
                    int c = r.nextInt(10) +11;
                    oc.HP += c;
                    System.out.println(oc.name + "恢复了" + c + "点HP");
                    break;
                }
                else  {
                    System.out.println(oc.name + "的HP不足10，无法使用生命汲取技能");
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



    }
}
