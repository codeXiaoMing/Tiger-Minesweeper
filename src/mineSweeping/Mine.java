package mineSweeping;

import java.awt.*;
import javax.swing.*;

public class Mine extends JButton {    //���׵���

    boolean isfound;                                    //�Ƿ񱻵㿪
    boolean ismine;                                        //�Ƿ����
    boolean canSearch;                                    //�Ƿ���죬������õ㲻�ᱻ����
    int x, y;                                            //ȷ������
    int aroundMine = 0;                                    //��Χ������

    Mine(int x, int y) {
        this.x = x;
        this.y = y;
        isfound = false;
        ismine = false;
        canSearch = true;
        setBackground(Color.pink);
    }

}
