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
public class SupplyStationAddPanel extends JPanel {
    private JLabel serverIp;
    private JLabel serverPort;
    private JLabel warehouseId;
    private JLabel stationCode;
    private JLabel delaySec;

    private JTextField serverIpText;
    private JTextField serverPortText;
    private JTextField warehouseIdText;
    private JTextField stationCodeText;
    private JTextField delaySecText;

    private JButton startSupply;
    private JButton stopSupply;

    public SupplyStationAddPanel() {
        this.setBackground(Color.lightGray);
        //this.setLayout(new GridLayout(1, 13));
        this.serverIp = new JLabel("作业中心IP：");
        this.serverPort = new JLabel("作业中心端口：");
        this.warehouseId = new JLabel("仓库id：");
        this.stationCode = new JLabel("供包站code：");
        this.delaySec = new JLabel("模拟供包实操耗时(秒)：");
        this.serverIpText = new JTextField(10);
        this.serverPortText = new JTextField(5);
        this.warehouseIdText = new JTextField(5);
        this.stationCodeText = new JTextField(10);
        this.delaySecText = new JTextField(5);
        this.startSupply = new JButton("开始供包");
        this.stopSupply = new JButton("停止供包");

        this.add(serverIp);
        this.add(serverIpText);
        this.add(serverPort);
        this.add(serverPortText);
        this.add(warehouseId);
        this.add(warehouseIdText);
        this.add(stationCode);
        this.add(stationCodeText);
        this.add(delaySec);
        this.add(delaySecText);
        this.add(startSupply);
        this.add(stopSupply);
    }

    public void init() {
        this.startSupply.addActionListener((e) -> {
            String serverIpAddr = serverIpText.getText();
            if (StringUtils.isBlank(serverIpAddr)) {
                JOptionPane.showMessageDialog(null, "请输入作业中心IP！");
            }

            String serverPortAddr = serverPortText.getText();
            if (StringUtils.isBlank(serverPortAddr)) {
                JOptionPane.showMessageDialog(null, "请输入作业中心端口！");
            }

            String warehouseIDStr = warehouseIdText.getText();
            if (StringUtils.isBlank(warehouseIDStr)) {
                JOptionPane.showMessageDialog(null, "请输入仓库id！");
            }

            String stationCodeStr = stationCodeText.getText();
            if (StringUtils.isBlank(stationCodeStr)) {
                JOptionPane.showMessageDialog(null, "请输入供包站code！");
            }

            String delaySecStr = delaySecText.getText();
            if (StringUtils.isBlank(delaySecStr)) {
                JOptionPane.showMessageDialog(null, "请输入实操模拟耗时！");
            }

            // 创建供包任务
            boolean result = AutoSupplyServiceFactory.startSupply(serverIpAddr, Integer.parseInt(serverPortAddr),
                Long.parseLong(warehouseIDStr), stationCodeStr, Long.parseLong(delaySecStr));
            if (result) {
                JOptionPane.showMessageDialog(null, "成功！");
                GlobalView.supplyStationPanel.addStation(stationCodeStr);
            } else {
                JOptionPane.showMessageDialog(null, "失败！");
            }
        });

        this.stopSupply.addActionListener((e) -> {
            int result = JOptionPane.showConfirmDialog(null, "确定停止供包！");
            if (0 == result) {
                // 关掉供包任务
                String serverIpAddr = serverIpText.getText();
                if (StringUtils.isBlank(serverIpAddr)) {
                    JOptionPane.showMessageDialog(null, "请输入作业中心IP！");
                }

                String serverPortAddr = serverPortText.getText();
                if (StringUtils.isBlank(serverPortAddr)) {
                    JOptionPane.showMessageDialog(null, "请输入作业中心端口！");
                }

                String warehouseIDStr = warehouseIdText.getText();
                if (StringUtils.isBlank(warehouseIDStr)) {
                    JOptionPane.showMessageDialog(null, "请输入仓库id！");
                }

                String stationCodeStr = stationCodeText.getText();
                if (StringUtils.isBlank(stationCodeStr)) {
                    JOptionPane.showMessageDialog(null, "请输入供包站code！");
                }

                boolean ret = AutoSupplyServiceFactory.stopSupply(serverIpAddr, Integer.parseInt(serverPortAddr),
                    Long.parseLong(warehouseIDStr), stationCodeStr);
                if (ret) {
                    JOptionPane.showMessageDialog(null, "成功！");
                    GlobalView.supplyStationPanel.delStation(stationCodeStr);
                }
                else {
                    JOptionPane.showMessageDialog(null, "失败！");
                }
            }
        });
    }
}
