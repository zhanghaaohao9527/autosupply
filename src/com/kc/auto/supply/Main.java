package com.kc.auto.supply;

import com.kc.auto.supply.impl.AutoSupplyServiceFactory;
import com.kc.auto.supply.view.GlobalView;

import javax.swing.*;
import java.util.Objects;

public class Main {
    private static final String LINUX = "linux";
    public static boolean linux = false;

    public static void main(String[] args) throws Exception {
        if (args != null && args.length > 0 && Objects.equals(args[0], LINUX)) {
            linux = true;
            if (args.length < 6) {
                throw new Exception(
                    "please specify parameters,second:operationIP, third:operationPort, forth:warehouseID, "
                        + "fifth:supplyStationCode(split with ,), sixth:operatonCost:");
            }
            else {
                String operationIp = args[1];
                Integer operationPort = Integer.parseInt(args[2]);
                Long warehouseID = Long.parseLong(args[3]);
                String[] supplyStationCodes = args[4].split(",");
                Long operateCost = Long.parseLong(args[5]);
                for (String supplyStationCode : supplyStationCodes) {
                    AutoSupplyServiceFactory.startSupply(operationIp, operationPort, warehouseID, supplyStationCode, operateCost);
                }
            }
        }
        else {
            JFrame.setDefaultLookAndFeelDecorated(true);
            GlobalView.mainFrame.init();
        }
    }
}
