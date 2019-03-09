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

/**
 * @author wuxiaowu
 */
public class SupplyTabPanel extends JPanel {
    public SupplyTabPanel() {

    }

    public void init() {
        this.setLayout(new BorderLayout());
        this.add(GlobalView.supplyStationAddPanel, BorderLayout.NORTH);
        this.add(GlobalView.supplyStationPanel, BorderLayout.CENTER);
        GlobalView.supplyStationAddPanel.init();
    }
}
