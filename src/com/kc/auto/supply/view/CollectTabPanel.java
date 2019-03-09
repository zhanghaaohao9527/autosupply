/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.view;

import com.kc.auto.supply.impl.AutoSupplyServiceFactory;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author wuxiaowu
 */
public class CollectTabPanel extends JPanel {
    private JLabel warehouseIdLabel;
    private JLabel operationIpLabel;
    private JLabel operationPortLabel;
    private JLabel containerCodeLabel;

    private JTextField warehouseIdText;
    private JTextField operationIpText;
    private JTextField operationPortText;
    private JTextField containerCodeText;
    private JButton collectBtn;

    private JPanel editPanel = new JPanel();

    public CollectTabPanel() {
        this.setBackground(Color.white);
        this.setLayout(new GridLayout(1, 9));
        warehouseIdLabel = new JLabel("仓库id：");
        operationIpLabel = new JLabel("作业中心ip：");
        operationPortLabel = new JLabel("作业中心端口：");
        containerCodeLabel = new JLabel("笼车编号：");
        warehouseIdText = new JTextField(7);
        operationIpText = new JTextField(10);
        operationPortText = new JTextField(7);
        containerCodeText = new JTextField(7);
        collectBtn = new JButton("集货");

        editPanel.add(warehouseIdLabel);
        editPanel.add(warehouseIdText);
        editPanel.add(operationIpLabel);
        editPanel.add(operationIpText);
        editPanel.add(operationPortLabel);
        editPanel.add(operationPortText);
        editPanel.add(containerCodeLabel);
        editPanel.add(containerCodeText);
        editPanel.add(collectBtn);
        editPanel.setBackground(Color.lightGray);

        this.setLayout(new BorderLayout());
        this.add(editPanel, BorderLayout.NORTH);
    }

    public void init() {
        this.collectBtn.addActionListener(e -> {
            String warehouseId = warehouseIdText.getText();
            if (StringUtils.isBlank(warehouseId)) {
                JOptionPane.showMessageDialog(null, "请输入仓库id！");
            }

            String operationIp = operationIpText.getText();
            if (StringUtils.isBlank(warehouseId)) {
                JOptionPane.showMessageDialog(null, "请输入作业中心ip！");
            }

            String operationPort = operationPortText.getText();
            if (StringUtils.isBlank(warehouseId)) {
                JOptionPane.showMessageDialog(null, "请输入作业中心端口！");
            }

            String containerCode = containerCodeText.getText();
            if (StringUtils.isBlank(containerCode)) {
                JOptionPane.showMessageDialog(null, "请输入笼车编号！");
            }

            String result = AutoSupplyServiceFactory.collectParcel(operationIp, Integer.parseInt(operationPort),
                Long.parseLong(warehouseId), containerCode);
            JOptionPane.showMessageDialog(null, result);
        });
    }
}
