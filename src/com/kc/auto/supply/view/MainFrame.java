package com.kc.auto.supply.view;

import javax.swing.*;
import java.awt.*;

/**
 * 主窗体
 * @author yyp
 */
public class MainFrame extends JFrame {

    public void init() {
        this.setTitle("");
        this.setTitle("自动供包");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(1100, 700);
        this.setLocation(100, 30);
        //this.setLayout(new BorderLayout());
        //this.add(GlobalView.supplyStationAddPanel, BorderLayout.NORTH);
        //this.add(GlobalView.supplyStationPanel, BorderLayout.CENTER);
        this.setLayout(null);
        this.setContentPane(GlobalView.globalPanel);

        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocus();

        GlobalView.globalPanel.init();
        //GlobalView.supplyStationAddPanel.init();
    }


}
