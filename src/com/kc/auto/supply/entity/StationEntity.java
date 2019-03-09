/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.entity;

import javax.swing.*;

/**
 * @author wuxiaowu
 */
public class StationEntity {
    private String id;
    private String stationCode;
    private String stationName;
    private String stationType;
    private String parcelSize;
    private String state;
    private String enabled;
    private String ip;
    private Long warehouseID;
    private Long zoneID;
    private String zoneName;
    private String warehouseName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public String getParcelSize() {
        return parcelSize;
    }

    public void setParcelSize(String parcelSize) {
        this.parcelSize = parcelSize;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(Long warehouseID) {
        this.warehouseID = warehouseID;
    }

    public Long getZoneID() {
        return zoneID;
    }

    public void setZoneID(Long zoneID) {
        this.zoneID = zoneID;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StationEntity{");
        sb.append("stationCode='").append(stationCode).append('\'');
        sb.append(", stationName='").append(stationName).append('\'');
        sb.append(", stationType='").append(stationType).append('\'');
        sb.append(", parcelSize='").append(parcelSize).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", enabled='").append(enabled).append('\'');
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", warehouseID=").append(warehouseID);
        sb.append(", zoneID=").append(zoneID);
        sb.append(", zoneName='").append(zoneName).append('\'');
        sb.append(", warehouseName='").append(warehouseName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String toView(JTextArea textArea) {
        final StringBuffer sb = new StringBuffer();
        textArea.append("stationCode:" + stationCode);
        textArea.append("\r\n");
        textArea.append("stationName:" + stationName);
        textArea.append("\r\n");
        textArea.append("stationType:" + stationType);
        textArea.append("\r\n");
        textArea.append("parcelSize:" + parcelSize);
        textArea.append("\r\n");
        textArea.append("state:" + state);
        textArea.append("\r\n");
        textArea.append("enabled:" + enabled);
        textArea.append("\r\n");
        textArea.append("ip:" + ip);
        textArea.append("\r\n");
        textArea.append("warehouseID:" + warehouseID);
        textArea.append("\r\n");
        textArea.append("zoneID:" + zoneID);
        textArea.append("\r\n");
        textArea.append("zoneName:" + zoneName);
        textArea.append("\r\n");
        textArea.append("warehouseName:" + warehouseName);
        textArea.append("\r\n");
        return sb.toString();
    }
}
