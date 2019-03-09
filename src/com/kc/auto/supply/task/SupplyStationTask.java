/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.task;

import com.kc.auto.supply.Main;
import com.kc.auto.supply.consts.IConstants;
import com.kc.auto.supply.entity.CommonResult;
import com.kc.auto.supply.entity.GetTaskInputEntity;
import com.kc.auto.supply.entity.PickUpTaskEntity;
import com.kc.auto.supply.entity.SupplyParcelEntity;
import com.kc.auto.supply.service.IAutoSupplyService;
import com.kc.auto.supply.utils.ParcelNumberGenerator;
import com.kc.auto.supply.view.GlobalView;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuxiaowu
 */
public class SupplyStationTask implements Runnable {
    private static AtomicInteger supplyCount = new AtomicInteger(0);
    private static Map<String, AtomicInteger> stationCount = new HashMap<>();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Long warehouseID;
    private final String stationCode;
    private final IAutoSupplyService autoSupplyService;
    private final long operateCost;
    private final Map<String, AtomicInteger> taskTs;
    public SupplyStationTask(long operateCost, Long warehouseID, String stationCode, IAutoSupplyService autoSupplyService) {
        this.warehouseID = warehouseID;
        this.stationCode = stationCode;
        this.autoSupplyService = autoSupplyService;
        this.operateCost = operateCost;
        this.taskTs = new HashMap<>();
    }
    @Override
    public void run() {
        GetTaskInputEntity entity = new GetTaskInputEntity();
        entity.setWarehouseID(warehouseID);
        entity.setStationCode(stationCode);
        CommonResult<PickUpTaskEntity> pickUpTaskResult = autoSupplyService.getTask(entity);
        if (null == pickUpTaskResult || !pickUpTaskResult.isSuccess()) {
            error("failed to get pick up task for station:");
            return;
        }

        List<PickUpTaskEntity> pickUpTaskEntities = pickUpTaskResult.getData();
        if (null != pickUpTaskEntities && pickUpTaskEntities.size() > 0) {
            pickUpTaskEntities.stream().filter(pickUpTaskEntity -> Objects.equals(pickUpTaskEntity.getState(), IConstants.SUPPLY_STATE)).forEach(e -> {
                // 已存在则增长1000ms
                taskTs.computeIfPresent(e.getTaskID(), (taskID, ts) -> {
                    ts.incrementAndGet();
                    return ts;});
                // 不存在则新增
                taskTs.computeIfAbsent(e.getTaskID(), taskID -> taskTs.put(e.getTaskID(), new AtomicInteger(0)));
            });

            if (taskTs.size() == 0) {
                log("no agv for station:");
                return;
            }

            // 可以供包
            Iterator<Map.Entry<String, AtomicInteger>> iterator = taskTs.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, AtomicInteger> entry = iterator.next();
                if (entry.getValue().get() >= operateCost) {
                    SupplyParcelEntity supplyParcelEntity = new SupplyParcelEntity();
                    supplyParcelEntity.setParcelNumber(ParcelNumberGenerator.next().toString());
                    supplyParcelEntity.setTaskID(entry.getKey());
                    supplyParcelEntity.setWeight("0.1");
                    CommonResult<String> stringCommonResult = autoSupplyService.autoSupply(supplyParcelEntity);
                    if (null != stringCommonResult && stringCommonResult.isSuccess()) {
                        log("success to supply parcelNumber:" + supplyParcelEntity.getParcelNumber() + ", taskID:"
                                + entry.getKey());
                        record(1);
                        iterator.remove();
                    }
                    else {
                        error("fail to supply, result:");
                    }
                }
            }

        }
        else {
            log("no agv for station:");
        }
    }

    private void log(String info) {
        if (!Main.linux) {
            GlobalView.supplyStationPanel.addText(stationCode, info + stationCode);
        }
        else {
            System.out.println(info + stationCode);
        }
    }

    private void error(String error) {
        if (!Main.linux) {
            GlobalView.supplyStationPanel.addText(stationCode, error + stationCode);
        }
        else {
            System.out.println(error + stationCode);
        }
    }

    private void record(int num) {
        if (!Main.linux) {
            GlobalView.supplyStationPanel.setTotal(stationCode, 1);
        }
        else {
            String date = sdf.format(new Date());
            System.out.println(date + ":supply total:" + supplyCount.incrementAndGet());
            stationCount.computeIfAbsent(stationCode, station -> stationCount.put(station, new AtomicInteger(0)));
            System.out.println(date + ":" + stationCode + " supply total:" + stationCount.get(stationCode).incrementAndGet());
        }
    }
}
