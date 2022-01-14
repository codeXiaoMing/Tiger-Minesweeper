package mineSweeping;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Map extends JFrame implements ActionListener {

    Jpanel panel;                        //��Ϸ����
    JPanel home;                        //��ʼ����

    JButton easy;                    //��ģʽ
    JButton difficult;                //����ģʽ
    JButton diy;                    //�Զ���ģʽ

    Map() {
        home = new JPanel();
        add(home);

        home.setLayout(null);
        easy = new JButton("��  (9x9  10����)");
        difficult = new JButton("����  (25x25   120����)");
        diy = new JButton("�Զ���");
        home.add(easy);
        home.add(difficult);
        home.add(diy);

        //���ô�С
        easy.setBounds(100, 50, 190, 70);
        difficult.setBounds(100, 170, 190, 70);
        diy.setBounds(100, 290, 190, 70);

        //��Ӽ�����
        easy.addActionListener(this);
        difficult.addActionListener(this);
        diy.addActionListener(this);

        //���ñ�����ɫ
        easy.setBackground(Color.GRAY);
        difficult.setBackground(Color.gray);
        diy.setBackground(Color.gray);

        //�����ִ�С
        easy.setFont(new java.awt.Font("24", 20, 15));
        difficult.setFont(new java.awt.Font("24", 20, 15));
        diy.setFont(new java.awt.Font("24", 20, 15));

        setVisible(true);
        setBounds(400, 100, 400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easy) {                    //��ģʽ
            setBounds(300, 100, 800, 800);            //����������Ϸ�����С
            panel = new Jpanel(9, 9, 10);
            home.setVisible(false);
            this.remove(home);
            this.add(panel);
        }
        if (e.getSource() == difficult) {                //����ģʽ
            setBounds(100, 0, 1600, 1000);
            panel = new Jpanel(25, 25, 120);
            home.setVisible(false);
            this.remove(home);
            this.add(panel);
        }
        if (e.getSource() == diy) {                //�Զ���ģʽ
            String x = JOptionPane.showInputDialog(this, "������������", null, JOptionPane.INFORMATION_MESSAGE);
            String y = JOptionPane.showInputDialog(this, "������������", null, JOptionPane.INFORMATION_MESSAGE);
            String num = JOptionPane.showInputDialog(this, "������������", null, JOptionPane.INFORMATION_MESSAGE);
            setBounds(100, 0, 1600, 1000);
            panel = new Jpanel(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(num));
            home.setVisible(false);
            this.remove(home);
            this.add(panel);
        }
    }
}
