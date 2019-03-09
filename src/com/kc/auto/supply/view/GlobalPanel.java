/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author wuxiaowu
 */
public class GlobalPanel extends JPanel {
    /**
     * 存放选项卡的组件
     */
    private JTabbedPane jTabbedpane = new JTabbedPane();
    private String[] tabNames = { "工作站上下线", "供包" , "集货"};
    public GlobalPanel() {
    }

    public void init() {
        int i = 0;
        // 第一个标签下的JPanel
        GlobalView.baseInfoTabPanel.init();
        // 加入第一个页面
        jTabbedpane.addTab(tabNames[i++], GlobalView.baseInfoTabPanel);
        // 设置第一个位置的快捷键为0
        jTabbedpane.setMnemonicAt(0, KeyEvent.VK_0);

        // 第二个标签下的JPanel
        GlobalView.supplyTabPanel.init();
        // 加入第一个页面
        jTabbedpane.addTab(tabNames[i++], GlobalView.supplyTabPanel);
        // 设置快捷键为1
        jTabbedpane.setMnemonicAt(1, KeyEvent.VK_1);

        GlobalView.collectTabPanel.init();
        // 加入第一个页面
        jTabbedpane.addTab(tabNames[i++], GlobalView.collectTabPanel);
        // 设置快捷键为2
        jTabbedpane.setMnemonicAt(2, KeyEvent.VK_2);
        setLayout(new GridLayout(1, 1));
        add(jTabbedpane);

    }
}
