/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.impl;

import com.kc.auto.supply.Main;
import com.kc.auto.supply.entity.SupplyKeyEntity;
import com.kc.auto.supply.service.ISupplyTaskManager;
import com.kc.auto.supply.task.SupplyStationTask;
import com.kc.auto.supply.view.GlobalView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author wuxiaowu
 */
public class SupplyTaskManagerImpl implements ISupplyTaskManager {
    private Map<SupplyKeyEntity, SupplyStationTask> supplyStationTaskMap = new HashMap<>();

    private Map<SupplyKeyEntity, ScheduledExecutorService> supplySchedulerMap = new HashMap<>();
    @Override
    public boolean supply4Station(String serIp, Integer serPort, Long warehouseID, String stationCode, Long delaySec) {
        SupplyKeyEntity keyEntity = new SupplyKeyEntity(warehouseID, serIp, serPort, stationCode);
        if (supplyStationTaskMap.containsKey(keyEntity)) {
            return true;
        }

        SupplyStationTask task = new SupplyStationTask(delaySec, warehouseID, stationCode,
            AutoSupplyServiceFactory.getAutoSupplyService(serIp, serPort));
        supplyStationTaskMap.put(keyEntity, task);
        ScheduledExecutorService scheduledService = new ScheduledThreadPoolExecutor(1);
        supplySchedulerMap.put(keyEntity, scheduledService);
        scheduledService.scheduleAtFixedRate(task, 1L, 1L, TimeUnit.SECONDS);

        if (1 == supplyStationTaskMap.size()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (!Main.linux) {
                GlobalView.supplyStationPanel.setStartTime(sdf.format(new Date()));
            }
        }

        return true;
    }

    @Override
    public boolean stopSupply(String serIp, Integer serPort, Long warehouseID, String stationCode) {
        SupplyKeyEntity keyEntity = new SupplyKeyEntity(warehouseID, serIp, serPort, stationCode);
        supplyStationTaskMap.computeIfPresent(keyEntity, (key, task) -> supplyStationTaskMap.remove(key));
        supplySchedulerMap.computeIfPresent(keyEntity, (key, scheduler) -> {
            scheduler.shutdown();
            return supplySchedulerMap.remove(key);
        });
        return true;
    }
}
