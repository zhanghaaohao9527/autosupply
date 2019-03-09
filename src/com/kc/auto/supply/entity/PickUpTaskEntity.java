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
public class PickUpTaskEntity {
    private String taskID;
    private String taskType;
    private int pointCode;
    private String stationCode;
    private int stationID;
    private String state;
    private String driveUnitID;
    private int warehouseID;
    private int zoneID;

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getPointCode() {
        return pointCode;
    }

    public void setPointCode(int pointCode) {
        this.pointCode = pointCode;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDriveUnitID() {
        return driveUnitID;
    }

    public void setDriveUnitID(String driveUnitID) {
        this.driveUnitID = driveUnitID;
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }

    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PickUpTaskEntity{");
        sb.append("taskID='").append(taskID).append('\'');
        sb.append(", taskType='").append(taskType).append('\'');
        sb.append(", pointCode=").append(pointCode);
        sb.append(", stationCode='").append(stationCode).append('\'');
        sb.append(", stationID=").append(stationID);
        sb.append(", state='").append(state).append('\'');
        sb.append(", driveUnitID='").append(driveUnitID).append('\'');
        sb.append(", warehouseID=").append(warehouseID);
        sb.append(", zoneID=").append(zoneID);
        sb.append('}');
        return sb.toString();
    }
}
