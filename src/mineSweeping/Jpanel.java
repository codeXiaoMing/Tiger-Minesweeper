package mineSweeping;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Jpanel extends JPanel implements MouseListener, ActionListener {

    int x, y;
    Mine[][] mine;                                            //每个点
    JLabel flagPhoto;                                        //雷图片
    JLabel blank1;                                            //空白占网格
    JLabel blank2;                                            //空白占网格
    JLabel blank3;                                            //空白占网格
    JLabel blank4;                                            //空白占网格
    JLabel flagNum;                                            //记录剩余雷数
    JLabel timeShow;                                        //显示用时
    Timer time;                                                //计时
    int mineNum = 0;                                            //剩余雷数
    int second = 0;                                            //用时

    Jpanel(int x, int y, int mineNum) {
        this.x = x;
        this.y = y;
        this.mineNum = mineNum;

        GridLayout grid = new GridLayout(x + 1, y);                //网格布局每一格是一个点
        setLayout(grid);
        setFocusable(true);

        createMine();       //创建每一个点

        flagPhoto = new JLabel();
        ImageIcon icon = new ImageIcon(".//photo//雷.jpg");
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

        time = new Timer(1000, this);            //每1秒发生一次事件
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
		while (flag < mineNum) {                        //随机添加雷
			int i, j;
			i = (int) (Math.random() * x - 1);        //随机数乘小于坐标大小的数控制范围在0~x-1
			j = (int) (Math.random() * y - 1);
			if (!mine[i][j].ismine) {            //该点没有雷就埋雷
				flag++;
				mine[i][j].ismine = true;
				int[][] next = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};    //周围8个坐标
				for (int k = 0; k < 8; k++) {                //循环雷周围8个点
					int tx = i + next[k][0];
					int ty = j + next[k][1];
					if (tx < 0 || tx > x - 1 || ty < 0 || ty > y - 1)        //越界则下一个
					{
						continue;
					}
					mine[tx][ty].aroundMine++;        //该点的周围雷数加一
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
                    t = (Mine) e.getSource();                //获得点击到的点的地址
                    break;
                }
            }
        }

        if (e.getButton() == e.BUTTON3) {                    //点击右键排雷
            if (t.canSearch && !t.isfound) {                //插旗后点左键不可进行搜索
                t.canSearch = false;
                ImageIcon icon = new ImageIcon(".\\photo\\旗.jpg");        //右键插旗
                icon.setImage(icon.getImage().getScaledInstance(t.getWidth(), t.getHeight(), Image.SCALE_DEFAULT));
                t.setIcon(icon);
                mineNum--;
                flagNum.setText(": " + mineNum);            //插旗雷数显示减一
            } else if (!t.canSearch && !t.isfound) {
                t.canSearch = true;                        //插旗后再点一次右键可左键搜索
                t.setIcon(null);                        //撤掉旗就删掉图标
                mineNum++;                                //撤掉旗雷数显示加一
                flagNum.setText(": " + mineNum);
            }
        }
        if (e.getClickCount() == 1 && e.getButton() == e.BUTTON1) {                //单击左键打开该点
            if (t.canSearch) {                            //若该点没插旗就可以点开
                t.setBackground(Color.LIGHT_GRAY);        //当被点开后变色
                t.isfound = true;                            //已经被点开
                if (t.aroundMine > 0 && !t.ismine) {            //不是雷且周围有雷就标出周围有雷数
                    t.setText("" + t.aroundMine);
                    t.setFont(new java.awt.Font("24", 20, 20));
                }
            }
            if (t.ismine && t.canSearch) {                    //若该点有雷且没有插旗双击点开则结束游戏
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (mine[i][j].ismine) {
                            ImageIcon icon = new ImageIcon(".//photo//雷.jpg");
                            icon.setImage(icon.getImage()
                                    .getScaledInstance(mine[i][j].getWidth(), mine[i][j].getHeight(),
                                            Image.SCALE_AREA_AVERAGING));
                            mine[i][j].setIcon(icon);

                        }
                    }
                }
                time.stop();
                JOptionPane.showMessageDialog(this, "不好意思 您输了 下次好运");
                Main.main(null);
            }
        }
        if (e.getClickCount() == 2 && e.getButton() == e.BUTTON1 && t.isfound && !t.ismine) {
            int[][] map = new int[x * y][];                //存每个队列中的点坐标
            for (int i = 0; i < x * y; i++) {
                map[i] = new int[2];
            }
            map[0][0] = t.x;
            map[0][1] = t.y;
            int head = 0, tail = 1;
            int[][] next = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};    //周围8个坐标
            int tx, ty, flag = 0;
            while (head < tail) {
                for (int w = 0; w < 8; w++) {
                    tx = map[head][0] + next[w][0];
                    ty = map[head][1] + next[w][1];
                    if (tx < 0 || tx > x - 1 || ty < 0 || ty > y - 1) {
                        continue;
                    }
                    if (!mine[tx][ty].canSearch)                //周围雷的数量要和插的旗数量一样
                    {
                        flag++;
                    }
                }
                if (flag == mine[map[head][0]][map[head][1]].aroundMine) {
                    for (int k = 0; k < 8; k++) {                    //循环周围8个方向
                        tx = map[head][0] + next[k][0];
                        ty = map[head][1] + next[k][1];
                        if (tx < 0 || tx > x - 1 || ty < 0 || ty > y - 1)            //越界则进入下一个
                        {
                            continue;
                        }
                        if (mine[tx][ty].ismine && mine[tx][ty].canSearch) {    //差错旗直接结束
                            for (int i = 0; i < x; i++) {
                                for (int j = 0; j < y; j++) {
                                    if (mine[i][j].ismine) {
                                        ImageIcon icon = new ImageIcon(".//photo//雷.jpg");
                                        icon.setImage(icon.getImage()
                                                .getScaledInstance(mine[i][j].getWidth(), mine[i][j].getHeight(),
                                                        Image.SCALE_AREA_AVERAGING));
                                        mine[i][j].setIcon(icon);
                                    }
                                }
                            }
                            time.stop();
                            JOptionPane.showMessageDialog(this, "不好意思 您输了 下次好运");
                            Main.main(null);
                        }
                        //旗插到雷上则空地点开
                        if (!mine[tx][ty].isfound && !mine[tx][ty].ismine && mine[tx][ty].canSearch) {
                            mine[tx][ty].setBackground(Color.LIGHT_GRAY);        //当被点开后变色
                            mine[tx][ty].isfound = true;                            //已经被点开
                            if (mine[tx][ty].aroundMine > 0 && !mine[tx][ty].ismine) {//周围有雷就标出周围有雷数
                                mine[tx][ty].setText("" + mine[tx][ty].aroundMine);
                                mine[tx][ty].setFont(new java.awt.Font("24", 20, 20));
                            }
                            if (mine[tx][ty].aroundMine == 0) {//周围都没雷就入队以该点为中心搜
                                map[tail][0] = tx;
                                map[tail][1] = ty;
                                tail++;
                            }
                        }
                    }
                }
                head++;        //每搜完一个点后就出队
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
                JOptionPane.showMessageDialog(this, "胜利！");
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


