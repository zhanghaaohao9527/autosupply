/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.entity;

import java.util.Objects;

/**
 * @author wuxiaowu
 */
public class SupplyKeyEntity {
    private Long warehouseID;
    private String serverIp;
    private Integer serverPort;
    private String stationCode;

    public SupplyKeyEntity() {

    }

    public SupplyKeyEntity(Long warehouseID, String serverIp, Integer serverPort, String stationCode) {
        this.warehouseID = warehouseID;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.stationCode = stationCode;
    }

    public Long getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(Long warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        SupplyKeyEntity that = (SupplyKeyEntity)o;
        return Objects.equals(warehouseID, that.warehouseID) &&
            Objects.equals(serverIp, that.serverIp) &&
            Objects.equals(serverPort, that.serverPort) &&
            Objects.equals(stationCode, that.stationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseID, serverIp, serverPort, stationCode);
    }
}
