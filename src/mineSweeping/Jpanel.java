package mineSweeping;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Jpanel extends JPanel implements MouseListener, ActionListener {

    int x, y;
    Mine[][] mine;                                            //ÿ����
    JLabel flagPhoto;                                        //��ͼƬ
    JLabel blank1;                                            //�հ�ռ����
    JLabel blank2;                                            //�հ�ռ����
    JLabel blank3;                                            //�հ�ռ����
    JLabel blank4;                                            //�հ�ռ����
    JLabel flagNum;                                            //��¼ʣ������
    JLabel timeShow;                                        //��ʾ��ʱ
    Timer time;                                                //��ʱ
    int mineNum = 0;                                            //ʣ������
    int second = 0;                                            //��ʱ

    Jpanel(int x, int y, int mineNum) {
        this.x = x;
        this.y = y;
        this.mineNum = mineNum;

        GridLayout grid = new GridLayout(x + 1, y);                //���񲼾�ÿһ����һ����
        setLayout(grid);
        setFocusable(true);

        createMine();       //����ÿһ����

        flagPhoto = new JLabel();
        ImageIcon icon = new ImageIcon(".//photo//��.jpg");
        icon.setImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING));
        flagPhoto.setIcon(icon);
		add(flagPhoto);

        flagNum = new JLabel(":" + mineNum);
        flagNum.setFont(new java.awt.Font("24", 20, 23));
		blank1 = new JLabel();
		blank2 = new JLabel();
		blank3 = new JLabel();
		blank4 = new JLabel();
        add(flagNum);
		add(blank1);
		add(blank2);
		add(blank3);
		add(blank4);

        time = new Timer(1000, this);            //ÿ1�뷢��һ���¼�
        timeShow = new JLabel("" + second);
        add(timeShow);
        timeShow.setFont(new java.awt.Font("24", 20, 30));
    }

    private void createMine() {
		mine = new Mine[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				mine[i][j] = new Mine(i, j);
				add(mine[i][j]);
				mine[i][j].addMouseListener(this);
			}
		}

		int flag = 0;
		while (flag < mineNum) {                        //��������
			int i, j;
			i = (int) (Math.random() * x - 1);        //�������С�������С�������Ʒ�Χ��0~x-1
			j = (int) (Math.random() * y - 1);
			if (!mine[i][j].ismine) {            //�õ�û���׾�����
				flag++;
				mine[i][j].ismine = true;
				int[][] next = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};    //��Χ8������
				for (int k = 0; k < 8; k++) {                //ѭ������Χ8����
					int tx = i + next[k][0];
					int ty = j + next[k][1];
					if (tx < 0 || tx > x - 1 || ty < 0 || ty > y - 1)        //Խ������һ��
					{
						continue;
					}
					mine[tx][ty].aroundMine++;        //�õ����Χ������һ
				}
			}
		}
	}

    public void mouseClicked(MouseEvent e) {
        time.start();
        Mine t = new Mine(0, 0);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (e.getSource() == mine[i][j]) {
                    t = (Mine) e.getSource();                //��õ�����ĵ�ĵ�ַ
                    break;
                }
            }
        }

        if (e.getButton() == e.BUTTON3) {                    //����Ҽ�����
            if (t.canSearch && !t.isfound) {                //������������ɽ�������
                t.canSearch = false;
                ImageIcon icon = new ImageIcon(".\\photo\\��.jpg");        //�Ҽ�����
                icon.setImage(icon.getImage().getScaledInstance(t.getWidth(), t.getHeight(), Image.SCALE_DEFAULT));
                t.setIcon(icon);
                mineNum--;
                flagNum.setText(": " + mineNum);            //����������ʾ��һ
            } else if (!t.canSearch && !t.isfound) {
                t.canSearch = true;                        //������ٵ�һ���Ҽ����������
                t.setIcon(null);                        //�������ɾ��ͼ��
                mineNum++;                                //������������ʾ��һ
                flagNum.setText(": " + mineNum);
            }
        }
        if (e.getClickCount() == 1 && e.getButton() == e.BUTTON1) {                //��������򿪸õ�
            if (t.canSearch) {                            //���õ�û����Ϳ��Ե㿪
                t.setBackground(Color.LIGHT_GRAY);        //�����㿪���ɫ
                t.isfound = true;                            //�Ѿ����㿪
                if (t.aroundMine > 0 && !t.ismine) {            //����������Χ���׾ͱ����Χ������
                    t.setText("" + t.aroundMine);
                    t.setFont(new java.awt.Font("24", 20, 20));
                }
            }
            if (t.ismine && t.canSearch) {                    //���õ�������û�в���˫���㿪�������Ϸ
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (mine[i][j].ismine) {
                            ImageIcon icon = new ImageIcon(".//photo//��.jpg");
                            icon.setImage(icon.getImage()
                                    .getScaledInstance(mine[i][j].getWidth(), mine[i][j].getHeight(),
                                            Image.SCALE_AREA_AVERAGING));
                            mine[i][j].setIcon(icon);

                        }
                    }
                }
                time.stop();
                JOptionPane.showMessageDialog(this, "������˼ ������ �´κ���");
                Main.main(null);
            }
        }
        if (e.getClickCount() == 2 && e.getButton() == e.BUTTON1 && t.isfound && !t.ismine) {
            int[][] map = new int[x * y][];                //��ÿ�������еĵ�����
            for (int i = 0; i < x * y; i++) {
                map[i] = new int[2];
            }
            map[0][0] = t.x;
            map[0][1] = t.y;
            int head = 0, tail = 1;
            int[][] next = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};    //��Χ8������
            int tx, ty, flag = 0;
            while (head < tail) {
                for (int w = 0; w < 8; w++) {
                    tx = map[head][0] + next[w][0];
                    ty = map[head][1] + next[w][1];
                    if (tx < 0 || tx > x - 1 || ty < 0 || ty > y - 1) {
                        continue;
                    }
                    if (!mine[tx][ty].canSearch)                //��Χ�׵�����Ҫ�Ͳ��������һ��
                    {
                        flag++;
                    }
                }
                if (flag == mine[map[head][0]][map[head][1]].aroundMine) {
                    for (int k = 0; k < 8; k++) {                    //ѭ����Χ8������
                        tx = map[head][0] + next[k][0];
                        ty = map[head][1] + next[k][1];
                        if (tx < 0 || tx > x - 1 || ty < 0 || ty > y - 1)            //Խ���������һ��
                        {
                            continue;
                        }
                        if (mine[tx][ty].ismine && mine[tx][ty].canSearch) {    //�����ֱ�ӽ���
                            for (int i = 0; i < x; i++) {
                                for (int j = 0; j < y; j++) {
                                    if (mine[i][j].ismine) {
                                        ImageIcon icon = new ImageIcon(".//photo//��.jpg");
                                        icon.setImage(icon.getImage()
                                                .getScaledInstance(mine[i][j].getWidth(), mine[i][j].getHeight(),
                                                        Image.SCALE_AREA_AVERAGING));
                                        mine[i][j].setIcon(icon);
                                    }
                                }
                            }
                            time.stop();
                            JOptionPane.showMessageDialog(this, "������˼ ������ �´κ���");
                            Main.main(null);
                        }
                        //��嵽������յص㿪
                        if (!mine[tx][ty].isfound && !mine[tx][ty].ismine && mine[tx][ty].canSearch) {
                            mine[tx][ty].setBackground(Color.LIGHT_GRAY);        //�����㿪���ɫ
                            mine[tx][ty].isfound = true;                            //�Ѿ����㿪
                            if (mine[tx][ty].aroundMine > 0 && !mine[tx][ty].ismine) {//��Χ���׾ͱ����Χ������
                                mine[tx][ty].setText("" + mine[tx][ty].aroundMine);
                                mine[tx][ty].setFont(new java.awt.Font("24", 20, 20));
                            }
                            if (mine[tx][ty].aroundMine == 0) {//��Χ��û�׾�����Ըõ�Ϊ������
                                map[tail][0] = tx;
                                map[tail][1] = ty;
                                tail++;
                            }
                        }
                    }
                }
                head++;        //ÿ����һ�����ͳ���
            }
        }
        if (mineNum == 0) {
            boolean a = false;
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (!mine[i][j].isfound && !mine[i][j].ismine && mine[i][j].canSearch) {
                        a = true;
                        break;
                    }
                    if (mine[i][j].ismine && mine[i][j].canSearch) {
                        a = true;
                        break;
                    }
                }
            }
            if (!a) {
                time.stop();
                JOptionPane.showMessageDialog(this, "ʤ����");
                Main.main(null);
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == time) {
            second++;
            timeShow.setText("" + second);
        }
    }
}


