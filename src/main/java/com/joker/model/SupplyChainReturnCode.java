package com.joker.model;

/**
 * Created by xiangrui on 2018/3/6.
 *
 * @author xiangrui
 * @date 2018/3/6
 */
public class SupplyChainReturnCode extends AbstractReturnCode {
    public final static int _MIN = 20_000_001;
    public final static int _MAX = 21_000_000;

    public final static int _SERVER_ERROR = 20_000_001;
    public final static SupplyChainReturnCode SERVER_ERROR = new SupplyChainReturnCode("服务端错误", _SERVER_ERROR);

    public final static int _PARAMETER_ERROR = 20_000_002;
    public final static SupplyChainReturnCode PARAMETER_ERROR = new SupplyChainReturnCode("参数异常", _PARAMETER_ERROR);

    public final static int _MORE_IMPORT_FILE_ERROR = 20_000_003;
    public final static SupplyChainReturnCode MORE_IMPORT_FILE_ERROR = new SupplyChainReturnCode("导入失败，存在并发重复操作！", _MORE_IMPORT_FILE_ERROR);

    public final static int _NO_SHEET_ERROR = 20_000_004;
    public final static SupplyChainReturnCode NO_SHEET_ERROR = new SupplyChainReturnCode("该表格不符合导入要求，导入失败！", _NO_SHEET_ERROR);

    public final static int _NO_DATA_SHEET_ERROR = 20_000_005;
    public final static SupplyChainReturnCode NO_DATA_SHEET_ERROR = new SupplyChainReturnCode("数据不能为空！", _NO_DATA_SHEET_ERROR);

    public final static int _MORE_STOCKIN_ORDER_OPERATE = 20_000_006;
    public final static SupplyChainReturnCode MORE_STOCKIN_ORDER_OPERATE = new SupplyChainReturnCode("入库单出现并发操作！", _MORE_STOCKIN_ORDER_OPERATE);

    public final static int _EXIST_UN_SUCCESS_RECEIVE_ORDER = 20_000_007;
    public final static SupplyChainReturnCode EXIST_UN_SUCCESS_RECEIVE_ORDER = new SupplyChainReturnCode("存在未完成的收货单！", _EXIST_UN_SUCCESS_RECEIVE_ORDER);

    public final static int _MORE_INVENTORY_ORDER_OPERATE = 20_000_008;
    public final static SupplyChainReturnCode MORE_INVENTORY_ORDER_OPERATE = new SupplyChainReturnCode("盘点单出现并发操作！", _MORE_INVENTORY_ORDER_OPERATE);

    public final static int _INVOKE_PRODUCT_ERROR = 20_000_009;
    public final static SupplyChainReturnCode INVOKE_PRODUCT_ERROR = new SupplyChainReturnCode("调用商品服务失败", _INVOKE_PRODUCT_ERROR);

    public final static int _INVOKE_NEW_SUPPLIER_ERROR = 20_000_010;
    public final static SupplyChainReturnCode INVOKE_NEW_SUPPLIER_ERROR = new SupplyChainReturnCode("调用供应商服务失败", _INVOKE_NEW_SUPPLIER_ERROR);

    public final static int _INVOKE_LOGISTICS_ERROR = 20_000_011;
    public final static SupplyChainReturnCode INVOKE_LOGISTICS_ERROR = new SupplyChainReturnCode("调用物流服务失败", _INVOKE_LOGISTICS_ERROR);

    public final static int _INVOKE_STOCK_ERROR = 20_000_012;
    public final static SupplyChainReturnCode INVOKE_STOCK_ERROR = new SupplyChainReturnCode("调用库存服务失败", _INVOKE_STOCK_ERROR);

    public final static int _INVOKE_USER_ERROR = 20_000_013;
    public final static SupplyChainReturnCode INVOKE_USER_ERROR = new SupplyChainReturnCode("调用用户服务失败", _INVOKE_USER_ERROR);

    public final static int _INVOKE_ORDER_ERROR = 20_000_014;
    public final static SupplyChainReturnCode INVOKE_ORDER_ERROR = new SupplyChainReturnCode("调用订单服务失败", _INVOKE_ORDER_ERROR);

    public final static int _MORE_JD_RETURN_ORDER = 20_000_020;
    public final static SupplyChainReturnCode MORE_JD_RETURN_ORDER = new SupplyChainReturnCode("京东创建退货单出现并发操作！", _MORE_JD_RETURN_ORDER);

    public final static int _MORE_STOCKIN_ORDER_RMA = 20_000_021;
    public final static SupplyChainReturnCode MORE_STOCKIN_ORDER_RMA = new SupplyChainReturnCode("退货生成入库单出现并发操作！", _MORE_STOCKIN_ORDER_RMA);

    public final static int _WORKBOOK_OUT_OF_MEMORY = 20_000_022;
    public final static SupplyChainReturnCode WORKBOOK_OUT_OF_MEMORY = new SupplyChainReturnCode("当前文档数据不符合预期，请检查文件模版是否正确！", _WORKBOOK_OUT_OF_MEMORY);

    public static final int _USER_LOCKED = 20_000_023;
    public static final SupplyChainReturnCode USER_LOCKED = new SupplyChainReturnCode("供应商账户已停用", _USER_LOCKED);

    public static final int _STORE_NO_WAREHOUSE = 20_000_024;
    public static final SupplyChainReturnCode STORE_NO_WAREHOUSE = new SupplyChainReturnCode("门店下不存在仓库", _STORE_NO_WAREHOUSE);

    public static final int _NEED_DEFERENCE_REASON = 20_000_025;
    public static final SupplyChainReturnCode NEED_DEFERENCE_REASON = new SupplyChainReturnCode("请选择差异原因！", _NEED_DEFERENCE_REASON);

    public static final int _REPEAT_INVENTORY_DETAIL = 20_000_026;
    public static final SupplyChainReturnCode REPEAT_INVENTORY_DETAIL = new SupplyChainReturnCode("盘点单明细Id不匹配！", _REPEAT_INVENTORY_DETAIL);

    public static final int _EXIST_UN_FINISH_INVENTORY_ORDER = 20_000_027;
    public static final SupplyChainReturnCode EXIST_UN_FINISH_INVENTORY_ORDER = new SupplyChainReturnCode("您存在未完成的盘点单", _EXIST_UN_FINISH_INVENTORY_ORDER);

    public static final int _SKU_BARCODE_NOT_CORRECT = 20_000_028;
    public static final SupplyChainReturnCode SKU_BARCODE_NOT_CORRECT = new SupplyChainReturnCode("sku 与商品条码不匹配", _SKU_BARCODE_NOT_CORRECT);

    public final static int _STOCK_UNDER_ZERO_ERROR = 20_000_029;
    public final static SupplyChainReturnCode STOCK_UNDER_ZERO_ERROR = new SupplyChainReturnCode("库存不得小于0", _STOCK_UNDER_ZERO_ERROR);

    public final static int _INVOKE_SUPPLIER_ERROR = 20_000_030;
    public final static SupplyChainReturnCode INVOKE_SUPPLIER_ERROR = new SupplyChainReturnCode("调用供应商服务失败", _INVOKE_SUPPLIER_ERROR);

    public static final int _CONCURRENT_ERROR = 20_000_031;
    public static final SupplyChainReturnCode CONCURRENT_ERROR = new SupplyChainReturnCode("当前有多人操作请稍后尝试", _CONCURRENT_ERROR);

    public static final int _INVOKE_JD_ERROR = 20_000_032;
    public static final SupplyChainReturnCode INVOKE_JD_ERROR = new SupplyChainReturnCode("调用supplier linker 失败", _INVOKE_JD_ERROR);

    public final static int _STOCKIN_ORDER_TAG_ERROR = 20_000_033;
    public final static SupplyChainReturnCode STOCKIN_ORDER_TAG_ERROR = new SupplyChainReturnCode("生成入库单tag错误", _STOCKIN_ORDER_TAG_ERROR);

    public final static int _RETURN_ORDER_TAG_ERROR = 20_000_034;
    public final static SupplyChainReturnCode RETURN_ORDER_TAG_ERROR = new SupplyChainReturnCode("生成入库单tag错误", _RETURN_ORDER_TAG_ERROR);

    public final static int _STOCKIN_ORDER_BUILD_ERR = 20_000_035;
    public final static SupplyChainReturnCode STOCKIN_ORDER_BUILD_ERR = new SupplyChainReturnCode("生成入库单异常", _STOCKIN_ORDER_BUILD_ERR);

    public final static int _RETURN_ORDER_BUILD_ERR = 20_000_036;
    public final static SupplyChainReturnCode RETURN_ORDER_BUILD_ERR = new SupplyChainReturnCode("生成退货单异常", _RETURN_ORDER_BUILD_ERR);

    public final static int _REPEAT_MARK_SPECIMEN = 20_000_037;
    public final static SupplyChainReturnCode REPEAT_MARK_SPECIMEN = new SupplyChainReturnCode("重复标记样品", _REPEAT_MARK_SPECIMEN);

    public final static int _STATE_MACHINE_TYPE_ERROR = 20_000_038;
    public final static SupplyChainReturnCode STATE_MACHINE_TYPE_ERROR = new SupplyChainReturnCode("状态机异常，状态机类型异常", _STATE_MACHINE_TYPE_ERROR);

    public final static int _STATE_MACHINE_PROCESSING = 20_000_039;
    public final static SupplyChainReturnCode STATE_MACHINE_PROCESSING = new SupplyChainReturnCode("操作正在进行中，请稍候再试", _STATE_MACHINE_PROCESSING);

    public final static int _STATE_MACHINE_INTERNAL_ERROR = 20_000_040;
    public final static SupplyChainReturnCode STATE_MACHINE_INTERNAL_ERROR = new SupplyChainReturnCode("状态机异常，内部异常", _STATE_MACHINE_INTERNAL_ERROR);

    public final static int _STATE_MACHINE_CONTEXT_ERROR = 20_000_041;
    public final static SupplyChainReturnCode STATE_MACHINE_CONTEXT_ERROR = new SupplyChainReturnCode("状态机异常，状态机上下文异常", _STATE_MACHINE_CONTEXT_ERROR);

    public final static int _STATE_MACHINE_DECLINED = 20_000_042;
    public final static SupplyChainReturnCode STATE_MACHINE_DECLINED = new SupplyChainReturnCode("状态发生变化，请重试", _STATE_MACHINE_DECLINED);

    public final static int _JEDIS_LOCK_ERR = 20_000_043;
    public final static SupplyChainReturnCode JEDIS_LOCK_ERR = new SupplyChainReturnCode("获取分布式锁失败，请稍后重试！", _JEDIS_LOCK_ERR);

    public final static int _MARK_SPECIMEN_ERROR = 20_000_044;
    public final static SupplyChainReturnCode MARK_SPECIMEN_ERROR = new SupplyChainReturnCode("样品标记出现异常", _MARK_SPECIMEN_ERROR);

    public final static int _INVOKE_RETAIL_STORE_ERROR = 20_000_045;
    public final static SupplyChainReturnCode INVOKE_RETAIL_STORE_ERROR = new SupplyChainReturnCode("调用门店服务失败", _INVOKE_RETAIL_STORE_ERROR);

    public final static int _PROFILT_LOSS_ORDER_STATUS_ERROR = 20_000_046;
    public final static SupplyChainReturnCode PROFILT_LOSS_ORDER_STATUS_ERROR = new SupplyChainReturnCode("损溢单无法进行该操作，请检查当前状态是否符合要求！", _PROFILT_LOSS_ORDER_STATUS_ERROR);

    public final static int _EXIST_STOCKIN_RECEIVE_USER = 20_000_047;
    public final static SupplyChainReturnCode EXIST_STOCKIN_RECEIVE_USER = new SupplyChainReturnCode("当前入库单在其它设备上操作中", _EXIST_STOCKIN_RECEIVE_USER);

    public final static int _INVOKE_PRICE_ERROR = 20_000_048;
    public final static SupplyChainReturnCode INVOKE_PRICE_ERROR = new SupplyChainReturnCode("调用价格服务失败", _INVOKE_PRICE_ERROR);

    public final static int _INVOKE_DATE_SERVICE_ERROR = 20_000_049;
    public final static SupplyChainReturnCode INVOKE_DATE_SERVICE_ERROR = new SupplyChainReturnCode("调用DateService失败失败", _INVOKE_DATE_SERVICE_ERROR);

    public final static int _LOSS_ORDER_STATUS_ERROR = 20_000_050;
    public final static SupplyChainReturnCode LOSS_ORDER_STATUS_ERROR = new SupplyChainReturnCode("报损单无法进行该操作，请检查当前状态是否符合要求！", _LOSS_ORDER_STATUS_ERROR);

    public final static int _DEBANG_ERROR = 20_000_051;
    public final static SupplyChainReturnCode DEBANG_ERROR = new SupplyChainReturnCode("调用德邦服务出错！", _DEBANG_ERROR);

    public final static int _RMA_ERROR = 20_000_052;
    public final static SupplyChainReturnCode RMA_ERROR = new SupplyChainReturnCode("调用RMA服务出错！", _RMA_ERROR);

    public final static int _FINANCE_ERROR = 20_000_053;
    public final static SupplyChainReturnCode FINANCE_ERROR = new SupplyChainReturnCode("调用FINANCE服务出错！", _FINANCE_ERROR);




    public SupplyChainReturnCode(String desc, int code) {
        super(desc, code);
    }

    public SupplyChainReturnCode(int code, AbstractReturnCode shadow) {
        super(code, shadow);
    }
}
