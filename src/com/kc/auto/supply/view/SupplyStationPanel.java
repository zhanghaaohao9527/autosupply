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
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuxiaowu
 */
public class SupplyStationPanel extends JPanel {
    private int listNum = 0;
    private AtomicInteger total = new AtomicInteger(0);
    private Map<String, JTextField> detailStation = new HashMap<>();
    private Map<String, AtomicInteger> stationTotal = new HashMap<>();

    private List<JLabel> freeLabels = new ArrayList<>();
    private List<JTextField> freeTexts = new ArrayList<>();

    private DefaultListModel stations = new DefaultListModel();
    private JList stationList = new JList(stations);
    private JScrollPane scrollPane = new JScrollPane(stationList);
    private JTextArea textArea = new JTextArea();
    private JPanel textAreaPanel = new JPanel();
    private JScrollPane textAreaScroll = new JScrollPane(textArea);

    private JPanel detailPanel = new JPanel();
    private JLabel startTime = new JLabel("供包开始时间：");
    private JTextField startTimeText = new JTextField(7);
    private JLabel totalLabel = new JLabel("供包总计：");
    private JTextField totalText = new JTextField(7);

    public SupplyStationPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);
        this.stationList.setDragEnabled(true);
        this.stationList.setBorder(BorderFactory.createTitledBorder("供包站列表"));

        //this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.WEST);
        //this.add(scrollPane, BorderLayout.WEST);
        //this.add(textArea, BorderLayout.CENTER);

        //this.setLayout(new BorderLayout());
        this.detailPanel.setLayout(new GridLayout(20, 2));
        this.detailPanel.add(startTime);
        this.detailPanel.add(startTimeText);
        this.detailPanel.add(totalLabel);
        this.detailPanel.add(totalText);
        for (int i = 0; i < 18; i++) {
            JLabel label = new JLabel("");
            freeLabels.add(label);
            JTextField field = new JTextField(7);
            field.setEditable(false);
            freeTexts.add(field);
            this.detailPanel.add(label);
            this.detailPanel.add(field);
        }
        totalText.setEditable(false);
        startTimeText.setEditable(false);
        textArea.setEnabled(false);
        this.add(detailPanel, BorderLayout.EAST);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textAreaPanel.setLayout(new BorderLayout());
        textAreaPanel.add(textAreaScroll, BorderLayout.CENTER);
        this.add(textAreaPanel, BorderLayout.CENTER);

        this.stationList.addListSelectionListener(e -> {
            this.textArea.setText("");
            //JOptionPane.showMessageDialog(null, this.stationList.getSelectedValue());
        });
    }

    public void addStation(String stationCode) {
        this.stations.addElement(stationCode);
        listNum++;
        this.stationList.setSelectedIndex(listNum - 1);

        if (!detailStation.containsKey(stationCode) && freeLabels.size() > 0) {
            freeLabels.get(0).setText(stationCode);
            detailStation.put(stationCode, freeTexts.get(0));

            freeLabels.remove(0);
            freeTexts.remove(0);
        }

        this.updateUI();
    }

    public void delStation(String stationCode) {
        this.stations.removeElement(stationCode);
        listNum--;
        this.stationList.setSelectedIndex(listNum);
        this.updateUI();
    }

    public void addText(String stationCode, String text) {
        if (Objects.equals(stationCode, getSelectedStation())) {
            this.textArea.append(new Date().toString() + "  " + text);
            this.textArea.append("\n\r");
            JScrollBar scrollBar = textAreaScroll.getVerticalScrollBar();
            scrollBar.setValue(scrollBar.getMaximum());
        }
    }

    public String getSelectedStation() {
        if (null != this.stationList.getSelectedValue()) {
            return this.stationList.getSelectedValue().toString();
        }
        return null;
    }

    public void setStartTime(String time) {
        this.startTimeText.setText(time);
        this.updateUI();
    }

    public void setTotal(String stationCode, int num) {
        total.addAndGet(num);
        this.totalText.setText(total.toString());


        stationTotal.computeIfAbsent(stationCode, station -> stationTotal.putIfAbsent(stationCode, new AtomicInteger(0)));
        detailStation.get(stationCode).setText(String.valueOf(stationTotal.get(stationCode).incrementAndGet()));

        this.updateUI();
    }
}
