package com.kc.auto.supply.view;

import javax.swing.*;

/**
 * 全局界面组件FastLink
 * @author yyp
 */
public class GlobalView {
    /** 窗体 */
    public static MainFrame mainFrame = new MainFrame();
    /** 全局panel */
    public static GlobalPanel globalPanel = new GlobalPanel();
    /** 基础信息管理tab */
    public static BaseInfoTabPanel baseInfoTabPanel = new BaseInfoTabPanel();
    /** 集货作业tab */
    public static CollectTabPanel collectTabPanel = new CollectTabPanel();
    /** 供包tab */
    public static SupplyTabPanel supplyTabPanel = new SupplyTabPanel();
    /** 供包站列表 */
    public static SupplyStationPanel supplyStationPanel = new SupplyStationPanel();
    /** 添加自动供包的供包站 */
    public static SupplyStationAddPanel supplyStationAddPanel = new SupplyStationAddPanel();
}
