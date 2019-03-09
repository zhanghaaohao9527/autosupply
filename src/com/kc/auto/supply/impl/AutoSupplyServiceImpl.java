/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kc.auto.supply.consts.IConstants;
import com.kc.auto.supply.entity.*;
import com.kc.auto.supply.http.HttpClientTool;
import com.kc.auto.supply.service.IAutoSupplyService;

import java.util.List;

/**
 * @author wuxiaowu
 */
public class AutoSupplyServiceImpl implements IAutoSupplyService {
    private HttpClientTool httpClientTool;
    public AutoSupplyServiceImpl(HttpClientTool httpClientTool) {
        this.httpClientTool = httpClientTool;
    }

    @Override
    public CommonResult<String> autoSupply(SupplyParcelEntity entity) {
        CommonResult<String> result = null;
        try {
            result = httpClientTool.doPost(IConstants.SUPPLY_PARCEL_URL, JSON.toJSONString(entity), CommonResult.class);
            result.setData(null);
        } catch (Exception e) {
            System.out.println("getTask exception:" + e.toString());
        }
        return result;
    }

    @Override
    public CommonResult<PickUpTaskEntity> getTask(GetTaskInputEntity entity) {
        CommonResult<PickUpTaskEntity> result = null;
        try {
            result = httpClientTool.doPost(IConstants.GET_TASK_URL, JSON.toJSONString(entity), CommonResult.class);
            if (null != result && result.isSuccess() && null != result.getData()) {
                List<PickUpTaskEntity> pickUpTaskEntities = JSON.parseArray(result.getData().toString(), PickUpTaskEntity.class);
                result.setData(pickUpTaskEntities);
            }
        } catch (Exception e) {
            System.out.println("getTask exception:" + e.toString());
        }
        return result;
    }

    @Override
    public CommonResult<StationEntity> getStations(QueryStationInputEntity entity) {
        CommonResult<StationEntity> result = null;
        try {
            result = httpClientTool.doPost(IConstants.GET_STATION_URL, JSON.toJSONString(entity), CommonResult.class);
            if (null != result && result.isSuccess() && null != result.getData()) {
                List<StationEntity> stationEntities = JSON.parseArray(result.getData().toString(), StationEntity.class);
                result.setData(stationEntities);
            }
        } catch (Exception e) {
            System.out.println("getStations exception:" + e.toString());
        }

        return result;
    }

    @Override
    public CommonResult<Void> onlineStation(OnOfflineStationEntity entity) {
        CommonResult<Void> result = null;
        try {
            result = httpClientTool.doPost(IConstants.STATION_ONLINE_URL, JSON.toJSONString(entity), CommonResult.class);
        } catch (Exception e) {
            System.out.println("onlineStation exception:" + e.toString());
        }

        return result;
    }

    @Override
    public CommonResult<Void> offlineStation(OnOfflineStationEntity entity) {
        CommonResult<Void> result = null;
        try {
            result = httpClientTool.doPost(IConstants.STATION_OFFLINE_URL, JSON.toJSONString(entity), CommonResult.class);
        } catch (Exception e) {
            System.out.println("offlineStation exception:" + e.toString());
        }

        return result;
    }

    @Override
    public CommonEntityResult<ScanContainerTaskEntity> scanContainer(Long warehouseID, String containerCode) {
        CommonEntityResult<ScanContainerTaskEntity> result = new CommonEntityResult<>();
        String url = IConstants.SCAN_CONTAINER_URL.replace("{warehouseID}", warehouseID.toString()).replace(
            "{containerCode}", containerCode);
        try {
            CommonEntityResult<JSONObject> tempResult = httpClientTool.doGet(url, CommonEntityResult.class);
            if (null != tempResult && tempResult.isSuccess() && null != tempResult.getData()) {
                JSONObject str = tempResult.getData();
                ScanContainerTaskEntity scanContainerTaskEntity = JSONObject.parseObject(str.toJSONString(), ScanContainerTaskEntity.class);

                result.setSuccess(tempResult.isSuccess());
                result.setErrorCode(tempResult.getErrorCode());
                result.setErrorDesc(tempResult.getErrorDesc());
                result.setData(scanContainerTaskEntity);
            }
        } catch (Exception e) {
            System.out.println("scanContainer exception:" + e.toString());
        }
        return result;
    }

    @Override
    public CommonEntityResult<Void> startCollect(String taskID) {
        CommonEntityResult<Void> result = null;
        String url = IConstants.COLLECT_START_URL.replace("{taskID}", taskID);
        try {
            result = httpClientTool.doGet(url, CommonEntityResult.class);
        } catch (Exception e) {
            System.out.println("startCollect exception:" + e.toString());
        }
        return result;
    }

    @Override
    public CommonEntityResult<String> finishCollect(String taskID) {
        CommonEntityResult<String> result = new CommonEntityResult<>();
        String url = IConstants.COLLECT_FINISH_URL.replace("{taskID}", taskID);
        try {
            CommonEntityResult<JSONObject> tempResult = httpClientTool.doGet(url, CommonEntityResult.class);
            if (null != tempResult && tempResult.isSuccess() && null != tempResult.getData()) {
                JSONObject str = tempResult.getData();

                result.setSuccess(tempResult.isSuccess());
                result.setErrorCode(tempResult.getErrorCode());
                result.setErrorDesc(tempResult.getErrorDesc());
                result.setData(tempResult.getData().toString());
            }
        } catch (Exception e) {
            System.out.println("finishCollect exception:" + e.toString());
        }
        return result;
    }
}
