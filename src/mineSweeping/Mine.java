package mineSweeping;

import java.awt.*;
import javax.swing.*;

public class Mine extends JButton {    //藏雷点类

    boolean isfound;                                    //是否被点开
    boolean ismine;                                        //是否藏雷
    boolean canSearch;                                    //是否插旗，插旗则该点不会被搜索
    int x, y;                                            //确定坐标
    int aroundMine = 0;                                    //周围的雷数

    Mine(int x, int y) {
        this.x = x;
        this.y = y;
        isfound = false;
        ismine = false;
        canSearch = true;
        setBackground(Color.pink);
    }

}
