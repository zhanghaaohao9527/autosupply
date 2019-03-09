/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.service;

import com.kc.auto.supply.entity.*;

/**
 * @author wuxiaowu
 */
public interface IAutoSupplyService {
    /**
     * 供包实体
     * @param entity entity
     * @return CommonResult
     */
    CommonResult<String> autoSupply(SupplyParcelEntity entity);

    /**
     * 查询供包站是否有供包任务
     * @param entity entity
     * @return CommonResult
     */
    CommonResult<PickUpTaskEntity> getTask(GetTaskInputEntity entity);

    /**
     * 查询工作站
     * @param entity entity
     * @return CommonResult
     */
    CommonResult<StationEntity> getStations(QueryStationInputEntity entity);

    /**
     * 上线供包站
     * @param entity entity
     * @return CommonResult
     */
    CommonResult<Void> onlineStation(OnOfflineStationEntity entity);

    /**
     * 下线供包站
     * @param entity entity
     * @return CommonResult
     */
    CommonResult<Void> offlineStation(OnOfflineStationEntity entity);

    /**
     * 扫描笼车 集货的第一步
     * @param warehouseID warehouseID
     * @param containerCode containerCode
     * @return CommonEntityResult
     */
    CommonEntityResult<ScanContainerTaskEntity> scanContainer(Long warehouseID, String containerCode);

    /**
     * 开始集货 集货的第二步
     * @param taskID taskID
     * @return CommonEntityResult
     */
    CommonEntityResult<Void> startCollect(String taskID);

    /**
     * 集货完成 集货的第三步
     * @param taskID taskID
     * @return CommonEntityResult
     */
    CommonEntityResult<String> finishCollect(String taskID);
}
