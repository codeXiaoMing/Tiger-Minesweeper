package mineSweeping;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Map extends JFrame implements ActionListener {

    Jpanel panel;                        //游戏画面
    JPanel home;                        //初始界面

    JButton easy;                    //简单模式
    JButton difficult;                //困难模式
    JButton diy;                    //自定义模式

    Map() {
        home = new JPanel();
        add(home);

        home.setLayout(null);
        easy = new JButton("简单  (9x9  10个雷)");
        difficult = new JButton("困难  (25x25   120个雷)");
        diy = new JButton("自定义");
        home.add(easy);
        home.add(difficult);
        home.add(diy);

        //设置大小
        easy.setBounds(100, 50, 190, 70);
        difficult.setBounds(100, 170, 190, 70);
        diy.setBounds(100, 290, 190, 70);

        //添加监听器
        easy.addActionListener(this);
        difficult.addActionListener(this);
        diy.addActionListener(this);

        //设置背景颜色
        easy.setBackground(Color.GRAY);
        difficult.setBackground(Color.gray);
        diy.setBackground(Color.gray);

        //设置字大小
        easy.setFont(new java.awt.Font("24", 20, 15));
        difficult.setFont(new java.awt.Font("24", 20, 15));
        diy.setFont(new java.awt.Font("24", 20, 15));

        setVisible(true);
        setBounds(400, 100, 400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easy) {                    //简单模式
            setBounds(300, 100, 800, 800);            //重新设置游戏界面大小
            panel = new Jpanel(9, 9, 10);
            home.setVisible(false);
            this.remove(home);
            this.add(panel);
        }
        if (e.getSource() == difficult) {                //困难模式
            setBounds(100, 0, 1600, 1000);
            panel = new Jpanel(25, 25, 120);
            home.setVisible(false);
            this.remove(home);
            this.add(panel);
        }
        if (e.getSource() == diy) {                //自定义模式
            String x = JOptionPane.showInputDialog(this, "请输入行数：", null, JOptionPane.INFORMATION_MESSAGE);
            String y = JOptionPane.showInputDialog(this, "请输入列数：", null, JOptionPane.INFORMATION_MESSAGE);
            String num = JOptionPane.showInputDialog(this, "请输入雷数：", null, JOptionPane.INFORMATION_MESSAGE);
            setBounds(100, 0, 1600, 1000);
            panel = new Jpanel(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(num));
            home.setVisible(false);
            this.remove(home);
            this.add(panel);
        }
    }
}
