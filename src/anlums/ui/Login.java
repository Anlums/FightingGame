package anlums.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javabean.User;

public class Login {
    public void start() {
        ArrayList<User> list = new ArrayList<>();

        printStartUI();

        //ctrl + alt + T
        while (true) {
            System.out.println("[😉请选择操作：1.登录 2.注册 3.退出 4.忘记密码]");
            Scanner scanner = new Scanner(System.in);
            int choose = scanner.nextInt();
            switch (choose) {
                case 1 -> {
                    login(list);
                    break;
                }
                case 2 -> register(list);
                case 3 -> exit();
                case 4 -> modifyPassword( list);
                default -> System.out.println("你选择错误");
            }
        }
    }

    public void printStartUI() {
        ColorUtils.printColor("☆*: .｡. o(≧▽≦)o .｡.:*☆", ColorUtils.yellow);
        System.out.println("⌈−−−−−−−−−−−−−−−−−−---⌉");
        ColorUtils.printColor("😊欢迎来到文字格斗游戏😊", ColorUtils.yellow);
        System.out.println("⌊−−−−−−−−−−−−−−−−−−---⌋");
    }

    public void login(ArrayList<User> list) {
        System.out.println("你选择登录操作");
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("请输入用户名:");
            String username = sc.next();
            System.out.println("请输入密码:");
            String password = sc.next();
            boolean isfound = false;
            User rightUser = null;
            while (true) {
                String captcha = getCaptcha();
                System.out.println("验证码是：" + captcha);
                System.out.println("请输入验证码：");
                String inputCaptcha = sc.next();
                if(!captcha.equals(inputCaptcha)) {
                    System.out.println("输入验证码错误");
                    continue;
                }
                else {
                    System.out.println("输入验证码正确");
                    break;
                }
            }
            if(!listContains(list, username)) {
                System.out.println("用户名不存在，请先进行注册");
                return;
            }
            rightUser = list.get(findIndex(list, username));
//            for(User u : list) {
//                if(u.getUsername().equals( username)) {
//                    isfound = true;
//                    rightUser = u;
//                    break;
//                }
//            }

            if(!rightUser.isStatus()) {
                System.out.println("该用户已锁定");
                break;
            }

            if(rightUser.getPassword().equals(password)) {
                System.out.println("登录成功,游戏🎮启动");
                FinghtingGame fg = new FinghtingGame();
                fg.gameStart(rightUser.getUsername());
                break;
            }
            else if(rightUser.getLogincount() < 3){
                rightUser.setLogincount(rightUser.getLogincount()+1);
                System.out.println("密码错误,剩余错误次数为" + (3-rightUser.getLogincount()));
                continue;
            }
            else {
                System.out.println("密码错误，用户名" + rightUser.getUsername() + "已锁定，请找管理员处理" );
                rightUser.setStatus(false);
                break;
            }
        }
    }

    public int findIndex(ArrayList<User> list, String username){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    public String getCaptcha() {
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) (i + 'a'));
            list.add((char) (i + 'A'));
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int k = random.nextInt(list.size());
            char c = list.get(k);
            sb.append(c);
        }
        sb.append(random.nextInt(10));
        char[] a = sb.toString().toCharArray();
        int k = random.nextInt(a.length);
        char temp = a[k];
        a[k] = a[4];
        a[a.length-1] = temp;
        String s = new String(a);
        return s;
//        String s = new String();
//        StringBuilder sb = new StringBuilder();
//        char[] c = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
//        Random random = new Random();
//        for (int i = 0; i < 4; i++) {
//            sb.append(c[random.nextInt(52)]);
//        }

    }

    public void register(ArrayList<User> list) {
        System.out.println("你选择注册操作");
        Scanner sc = new Scanner(System.in);
        User u = new User();

        //输入并校验用户名
        while (true) {
            System.out.println("请输入用户名：");
            String username = sc.next();
            if (!checkLen(username, 3, 16)) {
                System.out.println("用户名长度必须在3-16个字符之间");
                continue;
            }
            if (!checkUsername(username)) {
                System.out.println("用户名只能由字母数字组成，不能是纯数字");
                continue;
            }
            if (listContains(list, username)) {
                System.out.println("用户名已存在");
                continue;
            }
            u.setUsername(username);
            break;
        }

        //输入并校验密码
        while (true) {
            System.out.println("请输入密码：");
            String firstPassword = sc.next();
            if (!checkLen(firstPassword, 3, 8)) {
                System.out.println("密码长度必须在3-8个字符之间");
                continue;
            }
            if (!checkPassword(firstPassword)) {
                System.out.println("密码只能由字母数字组成，不能是纯数字");
                continue;
            }
            System.out.println("请再次输入密码：");
            String secondPassword = sc.next();
            if (!firstPassword.equals(secondPassword)) {
                System.out.println("两次输入的密码不一致,请重新输入");
                continue;
            }
            u.setPassword(firstPassword);
            break;
        }

        //创建用户
        list.add(u);

        //提示注册成功
        System.out.println("用户名" + u.getUsername() + "注册成功");
    }

    public void exit() {
        System.out.println("你选择退出操作");
        System.exit(0);
    }

    public boolean checkLen(String s, int minLen, int maxLen) {
        if (s.length() < minLen || s.length() > maxLen) return false;
        else return true;
    }

    public boolean checkUsername(String username) {
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;
        char[] c = username.toCharArray();
        for (char ch : c) {
            if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') charCount++;
            else if (ch >= '0' && ch <= '9') numCount++;
            else otherCount++;
        }
//        if(charCount == 0 || otherCount > 0) return false;
//        else return true;

        return charCount > 0 && otherCount == 0 && numCount >= 0;
    }

    public boolean listContains(ArrayList<User> list, String username) {
        for (User u : list) {
            if (u.getUsername().equals(username)) return true;
        }
        return false;
    }

    public boolean checkPassword(String password) {
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;
        char[] c = password.toCharArray();
        for (char ch : c) {
            if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') charCount++;
            else if (ch >= '0' && ch <= '9') numCount++;
            else otherCount++;
        }
        return charCount > 0 && otherCount == 0 && numCount > 0;
    }

    public void modifyPassword(ArrayList<User> list) {
        System.out.println("你选择修改密码操作");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户名：");
            String username = sc.next();

            int index = findIndex(list, username);
            if (index == -1) {
                System.out.println("用户名不存在，请您先注册");
                break;
            }
            //验证码验证身份
            while(true) {
                String chaptcha = getCaptcha();
                System.out.println("验证码：" + chaptcha);
                System.out.println("请输入验证码：");
                String inputChaptcha = sc.next();
                if (!inputChaptcha.equals(chaptcha)) {
                    System.out.println("验证码输入错误，请重新输入");
                    continue;
                }else {
                    System.out.println("验证码输入正确");
                    break;
                }
            }

            while (true) {
                System.out.println("请输入新密码：");
                String firstPassword = sc.next();
                if (!checkLen(firstPassword, 3, 8)) {
                    System.out.println("密码长度必须在3-8个字符之间");
                    continue;
                }
                if (!checkPassword(firstPassword)) {
                    System.out.println("密码只能由字母数字组成，不能是纯数字");
                    continue;
                }
                System.out.println("请再次输入新密码：");
                String secondPassword = sc.next();
                if (!firstPassword.equals(secondPassword)) {
                    System.out.println("两次输入的密码不一致,请重新输入");
                    continue;
                }
                list.get(index).setPassword(firstPassword);
                break;
            }
            list.get( index).setStatus( true);
            System.out.println("密码修改成功");
            break;

        }

    }


}
