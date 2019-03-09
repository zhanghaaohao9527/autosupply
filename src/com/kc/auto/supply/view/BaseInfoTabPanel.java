/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.view;

import com.kc.auto.supply.consts.IConstants;
import com.kc.auto.supply.entity.StationEntity;
import com.kc.auto.supply.entity.SupplyKeyEntity;
import com.kc.auto.supply.impl.AutoSupplyServiceFactory;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author wuxiaowu
 */
public class BaseInfoTabPanel extends JPanel {
    private Map<SupplyKeyEntity, StationEntity> stationEntityMap = new HashMap<>();

    private JLabel warehouseIdLabel;
    private JLabel operationIpLabel;
    private JLabel operationPortLabel;

    private JTextField warehouseIdText;
    private JTextField operationIpText;
    private JTextField operationPortText;

    private JButton querySupplyBtn;

    private JPanel editPanel = new JPanel();

    private JPanel stationsPanel = new JPanel();
    private DefaultListModel supplyStations = new DefaultListModel();
    private JList supplyStationList = new JList(supplyStations);
    private JScrollPane supplyStationScroll = new JScrollPane(supplyStationList);

    private JPanel opPanel = new JPanel();
    private JButton onlineBtn = new JButton("上线");
    private JButton offlineBtn = new JButton("下线");

    private JTextArea textArea = new JTextArea();
    private JScrollPane textAreaScroll = new JScrollPane(textArea);

    public BaseInfoTabPanel() {
        this.setBackground(Color.white);
        this.setLayout(new GridLayout(1, 7));
        warehouseIdLabel = new JLabel("仓库id：");
        operationIpLabel = new JLabel("作业中心ip：");
        operationPortLabel = new JLabel("作业中心端口：");
        warehouseIdText = new JTextField(7);
        operationIpText = new JTextField(10);
        operationPortText = new JTextField(7);
        querySupplyBtn = new JButton("查询供包站");
        textArea.setEditable(false);

        editPanel.add(warehouseIdLabel);
        editPanel.add(warehouseIdText);
        editPanel.add(operationIpLabel);
        editPanel.add(operationIpText);
        editPanel.add(operationPortLabel);
        editPanel.add(operationPortText);
        editPanel.add(querySupplyBtn);
        editPanel.setBackground(Color.lightGray);

        opPanel.add(onlineBtn);
        opPanel.add(offlineBtn);

        stationsPanel.setLayout(new BorderLayout());
        stationsPanel.add(supplyStationScroll, BorderLayout.CENTER);
        stationsPanel.add(opPanel, BorderLayout.NORTH);

        this.setLayout(new BorderLayout());
        this.add(editPanel, BorderLayout.NORTH);
        this.add(stationsPanel, BorderLayout.WEST);
        this.add(textAreaScroll, BorderLayout.CENTER);

        this.supplyStationList.setDragEnabled(true);
        this.supplyStationList.setBorder(BorderFactory.createTitledBorder("供包站列表"));
    }

    public void init() {
        querySupplyBtn.addActionListener(e -> {
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

            List<StationEntity> stationEntityList = AutoSupplyServiceFactory.getSupplyStations(operationIp,
                Integer.parseInt(operationPort));

            supplyStations.clear();
            stationEntityList.forEach(entity -> {
                supplyStations.addElement(entity.getStationCode());
                SupplyKeyEntity keyEntity = new SupplyKeyEntity(Long.parseLong(warehouseId), operationIp,
                    Integer.parseInt(operationPort), entity.getStationCode());
                stationEntityMap.put(keyEntity, entity);;
            });
            this.updateUI();
        });

        onlineBtn.addActionListener(e -> {
            Set<Boolean> rets = new HashSet<>();
            List list = supplyStationList.getSelectedValuesList();
            list.forEach(elem -> {
                Optional<SupplyKeyEntity> optional = stationEntityMap.keySet().stream().filter(
                    key -> Objects.equals(elem.toString(), key.getStationCode())).findFirst();
                optional.ifPresent(entity -> {
                    StationEntity stationEntity = stationEntityMap.get(entity);
                    if (Objects.equals(stationEntity.getState(), IConstants.STATION_OFFLINE)) {
                        String parcelSize = stationEntity.getParcelSize();
                        if (StringUtils.isEmpty(stationEntity.getParcelSize())) {
                            parcelSize = stationEntity.getStationCode().startsWith("S") ? IConstants.PARCEL_SIZE_SMALL
                                : IConstants.PARCEL_SIZE_LARGE;
                        }
                        Boolean ret = AutoSupplyServiceFactory.onlineStation(entity.getServerIp(), entity.getServerPort(),
                            stationEntity.getId(), parcelSize);
                        rets.add(ret);
                        if (ret) {
                            stationEntity.setState(IConstants.STATION_ONLINE);
                        }
                    }
                });
            });

            if (rets.stream().anyMatch(ret -> !ret)) {
                JOptionPane.showMessageDialog(null, "失败！");
            }
            else {
                JOptionPane.showMessageDialog(null, "成功！");
            }
        });

        offlineBtn.addActionListener(e -> {
            Set<Boolean> rets = new HashSet<>();
            List list = supplyStationList.getSelectedValuesList();
            list.forEach(elem -> {
                Optional<SupplyKeyEntity> optional = stationEntityMap.keySet().stream().filter(
                    key -> Objects.equals(elem.toString(), key.getStationCode())).findFirst();
                optional.ifPresent(entity -> {
                    StationEntity stationEntity = stationEntityMap.get(entity);
                    if (Objects.equals(stationEntity.getState(), IConstants.STATION_ONLINE)) {
                        Boolean ret = AutoSupplyServiceFactory.offlineStation(entity.getServerIp(), entity.getServerPort(),
                            stationEntity.getId(), stationEntity.getParcelSize());
                        rets.add(ret);
                        if (ret) {
                            stationEntity.setState(IConstants.STATION_OFFLINE);
                        }
                    }
                });
            });

            if (rets.stream().anyMatch(ret -> !ret)) {
                JOptionPane.showMessageDialog(null, "失败！");
            }
            else {
                JOptionPane.showMessageDialog(null, "成功！");
            }
        });

        this.supplyStationList.addListSelectionListener(e -> {
            this.textArea.setText("");
            List list = supplyStationList.getSelectedValuesList();
            Optional<SupplyKeyEntity> optional = stationEntityMap.keySet().stream().filter(
                key -> Objects.equals(list.get(0).toString(), key.getStationCode())).findFirst();
            optional.ifPresent(entity -> {
                stationEntityMap.get(entity).toView(this.textArea);
            });
        });
    }
}
