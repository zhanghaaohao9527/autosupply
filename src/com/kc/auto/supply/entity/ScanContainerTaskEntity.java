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
public class ScanContainerTaskEntity {
    private String containerCode;
    private String destinationName;
    private String warehouseID;
    private String zoneID;
    private String destinationID;
    private String driveUnitID;
    private String state;
    private String stationCode;
    private String pointCode;
    private String taskType;
    private String taskID;

    public String getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getZoneID() {
        return zoneID;
    }

    public void setZoneID(String zoneID) {
        this.zoneID = zoneID;
    }

    public String getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(String destinationID) {
        this.destinationID = destinationID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getDriveUnitID() {
        return driveUnitID;
    }

    public void setDriveUnitID(String driveUnitID) {
        this.driveUnitID = driveUnitID;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ScanContainerTaskEntity{");
        sb.append("containerCode='").append(containerCode).append('\'');
        sb.append(", destinationName='").append(destinationName).append('\'');
        sb.append(", warehouseID=").append(warehouseID);
        sb.append(", zoneID=").append(zoneID);
        sb.append(", destinationID=").append(destinationID);
        sb.append(", driveUnitID='").append(driveUnitID).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", stationCode='").append(stationCode).append('\'');
        sb.append(", pointCode=").append(pointCode);
        sb.append(", taskType='").append(taskType).append('\'');
        sb.append(", taskID='").append(taskID).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
