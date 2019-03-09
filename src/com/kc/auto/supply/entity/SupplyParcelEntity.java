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
public class SupplyParcelEntity {
    private String parcelNumber;
    private String weight;
    private String taskID;
    private String weightUnit = "KG";

    public String getParcelNumber() {
        return parcelNumber;
    }

    public void setParcelNumber(String parcelNumber) {
        this.parcelNumber = parcelNumber;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SupplyParcelEntity{");
        sb.append("parcelNumber='").append(parcelNumber).append('\'');
        sb.append(", weight='").append(weight).append('\'');
        sb.append(", taskID='").append(taskID).append('\'');
        sb.append(", weightUnit='").append(weightUnit).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
