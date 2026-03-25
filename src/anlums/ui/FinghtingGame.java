package anlums.ui;

import javabean.EnemyCharacter;
import javabean.OurCharacter;

import java.util.ArrayList;
import java.util.Scanner;

public class FinghtingGame {
    public void gameStart(String username) {
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

    }

    public OurCharacter createOurCharacter(String username) {
        Scanner sc = new Scanner(System.in);
        OurCharacter oc = new OurCharacter(username, 100, 10, 10);
        int point = 20;
        String[] attribute = {"生命值", "攻击力", "防御力"};
        int[] attributePoint = {10,2,1};
        int[] value = new int[3];

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

}
