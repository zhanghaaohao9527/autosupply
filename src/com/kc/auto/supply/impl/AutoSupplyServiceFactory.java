/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.impl;

import com.kc.auto.supply.consts.IConstants;
import com.kc.auto.supply.entity.*;
import com.kc.auto.supply.http.HttpClientTool;
import com.kc.auto.supply.http.HttpClientToolBuilder;
import com.kc.auto.supply.service.IAutoSupplyService;
import com.kc.auto.supply.service.ISupplyTaskManager;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuxiaowu
 */
public class AutoSupplyServiceFactory {
    private static Map<String, IAutoSupplyService> serviceMap = new HashMap<>();
    private static ISupplyTaskManager supplyTaskManager = new SupplyTaskManagerImpl();

    public synchronized static IAutoSupplyService getAutoSupplyService(String ip, Integer port) {
        Objects.requireNonNull(ip);
        Objects.requireNonNull(port);

        String key = ip + port;
        if (serviceMap.containsKey(key)) {
            return serviceMap.get(key);
        }

        HttpClientToolBuilder httpClientToolBuilder = new HttpClientToolBuilder(ip, port, "http");
        HttpClientTool httpClientTool = httpClientToolBuilder.build();
        AutoSupplyServiceImpl autoSupplyService = new AutoSupplyServiceImpl(httpClientTool);
        serviceMap.put(key, autoSupplyService);
        return autoSupplyService;
    }

    public synchronized static boolean startSupply(String serIp, Integer serPort, Long warehouseID, String stationCode,
                                                   Long delaySec) {
        return supplyTaskManager.supply4Station(serIp, serPort, warehouseID, stationCode, delaySec);
    }

    public synchronized static boolean stopSupply(String serIp, Integer serPort, Long warehouseID, String stationCode) {
        return supplyTaskManager.stopSupply(serIp, serPort, warehouseID, stationCode);
    }

    public static List<StationEntity> getSupplyStations(String ip, Integer port) {
        IAutoSupplyService supplyService = getAutoSupplyService(ip, port);

        QueryStationInputEntity entity = new QueryStationInputEntity();
        CommonResult<StationEntity> result = supplyService.getStations(entity);
        if (null != result && result.isSuccess()) {
            return result.getData().stream().filter(
                e -> Objects.equals(e.getStationType(), IConstants.STATION_SUPPLY_TYPE)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static boolean onlineStation(String ip, Integer port, String stationID, String parcelSize) {
        IAutoSupplyService supplyService = getAutoSupplyService(ip, port);
        OnOfflineStationEntity entity = new OnOfflineStationEntity();
        entity.setId(stationID);
        entity.setParcelSize(parcelSize);
        CommonResult<Void> result = supplyService.onlineStation(entity);
        return null != result && result.isSuccess();
    }

    public static boolean offlineStation(String ip, Integer port, String stationID, String parcelSize) {
        IAutoSupplyService supplyService = getAutoSupplyService(ip, port);
        OnOfflineStationEntity entity = new OnOfflineStationEntity();
        entity.setId(stationID);
        entity.setParcelSize(parcelSize);
        CommonResult<Void> result = supplyService.offlineStation(entity);
        return null != result && result.isSuccess();
    }

    public static String collectParcel(String serIp, Integer serPort, Long warehouseID, String containerCode) {
        IAutoSupplyService supplyService = getAutoSupplyService(serIp, serPort);
        // 1. 扫描笼车
        CommonEntityResult<ScanContainerTaskEntity> scanResult = supplyService.scanContainer(warehouseID, containerCode);
        if (!scanResult.isSuccess()) {
            return "扫描笼车失败";
        }

        if (scanResult.getData() == null || !Objects.equals(IConstants.SUPPLY_STATE, scanResult.getData().getState())) {
            return "笼车没有集货任务";
        }

        String collectTask = scanResult.getData().getTaskID();
        String agvID = scanResult.getData().getDriveUnitID();

        // 2. 开始集货
        CommonEntityResult<Void> startResult = supplyService.startCollect(collectTask);
        if (!startResult.isSuccess()) {
            return "开始集货失败";
        }

        // 3. 集货完成
        CommonEntityResult<String> finishResult = supplyService.finishCollect(collectTask);
        if (!finishResult.isSuccess()) {
            return "完成集货失败";
        }
        else {
            return "集货成功,集货明细：" + finishResult.getData();
        }
    }
}
