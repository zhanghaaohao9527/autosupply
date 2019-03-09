/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */
package com.kc.auto.supply.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wuxiaowu
 */
public class ParcelNumberGenerator {
    private static final AtomicLong ID = new AtomicLong(400000);
    public static Long next() {
        return ID.incrementAndGet();
    }
}
