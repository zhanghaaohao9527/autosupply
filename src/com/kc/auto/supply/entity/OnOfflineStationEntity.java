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
public class OnOfflineStationEntity {
    private String id;
    private String parcelSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParcelSize() {
        return parcelSize;
    }

    public void setParcelSize(String parcelSize) {
        this.parcelSize = parcelSize;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OnOfflineStationEntity{");
        sb.append("stationID='").append(id).append('\'');
        sb.append(", parcelSize='").append(parcelSize).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
