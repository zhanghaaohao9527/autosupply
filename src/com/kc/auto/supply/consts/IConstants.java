/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

package com.kc.auto.supply.consts;

/**
 * @author wuxiaowu
 */
public interface IConstants {
    String GET_TASK_URL = "/pss-operation/stationtask/getTask";

    String SUPPLY_PARCEL_URL = "/pss-operation/station/createParcel";

    String GET_STATION_URL = "/pss-operation/station/list";

    String STATION_ONLINE_URL = "/pss-operation/station/online";

    String STATION_OFFLINE_URL = "/pss-operation/station/offline";

    String SCAN_CONTAINER_URL = "/pss-operation/stationtask/collection/scan/{warehouseID}/{containerCode}";

    String COLLECT_START_URL = "/pss-operation/stationtask/collection/start/{taskID}";

    String COLLECT_FINISH_URL = "/pss-operation/stationtask/collection/end/{taskID}";

    String SUPPLY_STATE = "NEW";

    String STATION_ONLINE = "ONLINE";

    String STATION_OFFLINE = "OFFLINE";

    String STATION_SUPPLY_TYPE = "SUPPLY";

    String STATION_COLLECTION_TYPE = "COLLECTION";

    String PARCEL_SIZE_SMALL = "SMALL";

    String PARCEL_SIZE_LARGE = "LARGE";
}
