/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.entity;

/**
 * @author wuxiaowu
 */
public class GetTaskInputEntity {
    private Long warehouseID;
    private String stationCode;

    public Long getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(Long warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GetTaskInputEntity{");
        sb.append("warehouseID=").append(warehouseID);
        sb.append(", stationCode='").append(stationCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
