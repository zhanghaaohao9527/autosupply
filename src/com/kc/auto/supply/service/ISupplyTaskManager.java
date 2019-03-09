/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.service;

/**
 * @author wuxiaowu
 */
public interface ISupplyTaskManager {
    /**
     * 对指定工作站自动供包
     * @param warehouseID warehouseID
     * @param stationCode stationCode
     * @param delaySec 模拟供包耗时
     * @return boolean
     */
    boolean supply4Station(String serIp, Integer serPort, Long warehouseID, String stationCode, Long delaySec);

    /**
     * 停止指定工作站自动供包
     * @param warehouseID warehouseID
     * @param stationCode stationCode
     * @return boolean
     */
    boolean stopSupply(String serIp, Integer serPort, Long warehouseID, String stationCode);
}
