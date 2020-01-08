package com.joker.utils;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.joker.model.ServiceRuntimeException;
import com.joker.model.SupplyChainReturnCode;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;


/**
 * Created by fyy on 2017/9/30.
 */
public class ExcelUtil {

    /**
     * 日志工具
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    private final static String XLS = "xls";

    private final static String XLSX = "xlsx";

    private static final String TITLE = "title";

    public static final String GENERATE_EXCEL_ERROR = "生成excel文失败";


    /**
     * 将数据流解析成excel
     *
     * @param url 文件地址
     * @param is  输入流
     * @return excel
     */
    private static Workbook getWorkBook(String url, InputStream is) {
        Workbook workbook;
        try {
            if (url.endsWith(XLS)) {
                workbook = new HSSFWorkbook(is);
            } else if (url.endsWith(XLSX)) {
                workbook = new XSSFWorkbook(is);
            } else {
                throw new ServiceRuntimeException(SupplyChainReturnCode.SERVER_ERROR, "请上传EXCEl文档进行导入！");
            }
        } catch (Exception e) {
            throw new ServiceRuntimeException(SupplyChainReturnCode.SERVER_ERROR, "文件类型错误或读取数据失败!");
        }
        return workbook;
    }

    /**
     * 生成excel
     *
     * @param sheetName  表格名称
     * @param entityList 每一行的数据，每个 list 是一行
     * @param headerEnum 自定义枚举必须包含 String类型title，String类型method 两个属性，使用 enum 默认顺序（ordinal）作为导出列顺序
     * @see com.joker.model.InventoryExportHeaderEnum
     */
    public static byte[] buildExcel(String sheetName, List<?> entityList, Enum[] headerEnum) {
        return buildExcel(sheetName, entityList, headerEnum, false);
    }

    /**
     * 生成excel
     *
     * @param sheetName       表格名称
     * @param entityList      每一行的数据，每个 list 是一行
     * @param headerEnum      自定义枚举必须包含int类型cellNum， String类型title，String类型method 三个属性
     * @param instanceOfClass 是否根据类型定制化
     * @see com.joker.model.InventoryExportHeaderEnum
     */
    public static byte[] buildExcel(String sheetName, List<?> entityList, Enum[] headerEnum, boolean instanceOfClass) {
        try {
            // 表头名称
            List<String> headList = Lists.newArrayList();
            for (Enum en : headerEnum) {
                Field titleField = en.getDeclaringClass().getDeclaredField(TITLE);
                titleField.setAccessible(true);
                headList.add((String) titleField.get(en));
            }
            // 每一行的数据
            List<List<Object>> dataList = Lists.newArrayList();
            for (Object inventory : entityList) {
                List<Object> list = Lists.newArrayList();
                for (Enum en : headerEnum) {
                    Field methodField = en.getDeclaringClass().getDeclaredField("field");
                    methodField.setAccessible(true);
                    String entityFieldName = (String) methodField.get(en);
                    Field fieldMethod = inventory.getClass().getDeclaredField(entityFieldName);
                    fieldMethod.setAccessible(true);
                    Object fieldValue = fieldMethod.get(inventory);
                    list.add(fieldValue);
                }
                dataList.add(list);
            }
            if (instanceOfClass) {
                return buildSheetRowDataInstanceOfClass(sheetName, headList, dataList);
            } else {
                return buildSheetRowData(sheetName, headList, dataList);
            }
        } catch (Exception e) {
            LOGGER.error(GENERATE_EXCEL_ERROR, e);
            throw new ServiceRuntimeException(SupplyChainReturnCode.SERVER_ERROR, "导出excel失败");
        }
    }

    /**
     * 生成excel
     *
     * @param sheetName sheet 名称
     * @param headList  列名
     * @param dataList  数据list 强依赖内部list 的顺序
     * @param <T>       范型
     * @return excel 转化的 byte 数组
     */
    public static <T> byte[] buildSheetRowDataInstanceOfClass(String sheetName, List<String> headList, List<List<T>> dataList) {
        byte[] bytes;
        try {
            Workbook workbook = new SXSSFWorkbook();
            // 创建第一个sheet（页），并命名
            Sheet sheet = workbook.createSheet(sheetName);
            // 创建表头
            buildSheetHead(sheet, headList);
            int index = 1;
            for (List<T> k : dataList) {
                Row row = sheet.createRow(index);
                for (int i = 0; i < k.size(); i++) {
                    T obj = k.get(i);
                    if (obj instanceof Integer) {
                        Cell cell = row.createCell(i, HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(((Integer) obj).doubleValue());
                    } else if (obj instanceof Double) {
                        Cell cell = row.createCell(i, HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue((Double) obj);
                    } else if (obj instanceof BigDecimal) {
                        Cell cell = row.createCell(i, HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(((BigDecimal) obj).doubleValue());
                    } else if (obj instanceof RichTextString) {
                        Cell cell = row.createCell(i);
                        cell.setCellValue((RichTextString) obj);
                    } else {
                        Cell cell = row.createCell(i);
                        cell.setCellValue(obj == null ? "" : obj.toString());
                    }
                }
                index++;
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            bytes = os.toByteArray();
            // 关闭流
            os.close();
        } catch (Exception e) {
            throw new ServiceRuntimeException(SupplyChainReturnCode.SERVER_ERROR, "导出excel失败");
        }
        return bytes;
    }

    /**
     * 生成excel
     *
     * @param sheetName sheet 名称
     * @param headList  列名
     * @param dataList  数据list 强依赖内部list 的顺序
     * @param <T>       范型
     * @return excel 转化的 byte 数组
     */
    public static <T> byte[] buildSheetRowData(String sheetName, List<String> headList, List<List<T>> dataList) {
        byte[] bytes;
        try {
            Workbook workbook = new SXSSFWorkbook();
            // 创建第一个sheet（页），并命名
            Sheet sheet = workbook.createSheet(sheetName);
            // 创建表头
            buildSheetHead(sheet, headList);
            int index = 1;
            for (List<T> k : dataList) {
                Row row = sheet.createRow(index);
                for (int i = 0; i < k.size(); i++) {
                    Cell cell = row.createCell(i);
                    T t = k.get(i);
                    cell.setCellValue(t == null ? "" : t.toString());
                }
                index++;
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            bytes = os.toByteArray();
            // 关闭流
            os.close();
        } catch (Exception e) {
            throw new ServiceRuntimeException(SupplyChainReturnCode.SERVER_ERROR, "导出excel失败");
        }
        return bytes;
    }

    public static void buildSheetHead(Sheet sheet, List<String> headList) {
        //创建表头单元格
        Row row1 = sheet.createRow(0);
        for (int i = 0; i < headList.size(); i++) {
            Cell cell = row1.createCell(i);
            cell.setCellValue(headList.get(i));
        }
    }

    public static void buildExcelHeadWithHeadRow(Sheet sheet, Enum[] headers, Integer rownum, CellStyle cellStyle) {
        if (headers == null || headers.length <= 0) {
            return;
        }
        Row headerRow = sheet.createRow(rownum);
        for (Enum column : headers) {
            try {
                Field cellNumField = column.getClass().getDeclaredField("cellNum");
                cellNumField.setAccessible(true);
                int anInt = cellNumField.getInt(column);
                Field titleField = column.getClass().getDeclaredField(TITLE);
                titleField.setAccessible(true);
                String title = (String) titleField.get(column);
                if (anInt < 0) {
                    continue;
                }
                Cell cell = headerRow.createCell(anInt);
                cell.setCellValue(title);
                if (cellStyle != null) {
                    cell.setCellStyle(cellStyle);
                }
            } catch (Exception e) {
                throw new ServiceRuntimeException(SupplyChainReturnCode.SERVER_ERROR, "构造Excel表头出错", e);
            }
        }
    }

    /**
     * 生成Excel 支持多 sheet
     * Triplet.getValue0() -> sheet名称
     * Triplet.getValue1() -> sheet列名
     * Triplet.getValue2() -> sheet数据
     *
     * @param tripletList sheet 列表
     * @return excel
     */
    public static byte[] buildExcel(List<Triplet<String, Enum[], List<?>>> tripletList) {
        if (CollectionUtils.isEmpty(tripletList)) {
            return new byte[0];
        }
        byte[] bytes;
        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            if (CollectionUtils.isEmpty(tripletList)) {
                workbook.createSheet();
            } else {
                for (Triplet<String, Enum[], List<?>> objects : tripletList) {
                    // 创建第一个sheet（页），并命名
                    Sheet sheet = workbook.createSheet(objects.getValue0());
                    // 构建标题头部
                    buildSheetHeader(sheet, objects.getValue1());
                    // 构建文档内容
                    buildSheetContent(sheet, objects.getValue2(), objects.getValue1());
                }
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            bytes = os.toByteArray();
            // 关闭流
            os.close();
        } catch (Exception e) {
            throw new ServiceRuntimeException(SupplyChainReturnCode.SERVER_ERROR, "导出excel失败");
        }
        return bytes;
    }

    public static <T> byte[] buildExcel(Map<String, List<T>> sheetMap, Enum[] headerEnum) {
        if (MapUtils.isEmpty(sheetMap)) {
            return new byte[0];
        }
        byte[] bytes;
        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            Set<Map.Entry<String, List<T>>> entries = sheetMap.entrySet();
            if (CollectionUtils.isEmpty(entries)) {
                workbook.createSheet();
            } else {
                for (Map.Entry<String, List<T>> entry : sheetMap.entrySet()) {
                    // 创建第一个sheet（页），并命名
                    Sheet sheet = workbook.createSheet(entry.getKey());
                    // 构建标题头部
                    buildSheetHeader(sheet, headerEnum);
                    // 构建文档内容
                    buildSheetContent(sheet, entry.getValue(), headerEnum);
                }
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            bytes = os.toByteArray();
            // 关闭流
            os.close();
        } catch (Exception e) {
            LOGGER.error(GENERATE_EXCEL_ERROR, e);
            throw new ServiceRuntimeException(SupplyChainReturnCode.SERVER_ERROR, "导出excel失败");
        }
        return bytes;
    }

    private static <T> void buildSheetContent(Sheet sheet, List<T> list, Enum[] headerEnum)
            throws IllegalAccessException, NoSuchFieldException {
        int index = 1;
        for (Object data : list) {
            Row row = sheet.createRow(index);
            for (Enum en : headerEnum) {
                Cell cell = row.createCell(en.ordinal());
                Field methodField = en.getDeclaringClass().getDeclaredField("field");
                methodField.setAccessible(true);
                String entityFieldName = (String) methodField.get(en);
                Field fieldMethod = data.getClass().getDeclaredField(entityFieldName);
                fieldMethod.setAccessible(true);
                Object fieldVaule = fieldMethod.get(data);
                if (fieldVaule instanceof String || fieldVaule instanceof Integer || fieldVaule instanceof BigDecimal) {
                    cell.setCellValue(String.valueOf(fieldVaule));
                } else if (fieldVaule instanceof Date) {
                    cell.setCellValue((Date) fieldVaule);
                } else if (fieldVaule instanceof Double) {
                    cell.setCellValue((Double) fieldVaule);
                } else if (fieldVaule instanceof Calendar) {
                    cell.setCellValue((Calendar) fieldVaule);
                } else if (fieldVaule instanceof RichTextString) {
                    cell.setCellValue((RichTextString) fieldVaule);
                } else {
                    // 类型不支持，默认为空
                }
            }
            index++;
        }
    }

    private static void buildSheetHeader(Sheet sheet, Enum[] headerEnum)
            throws IllegalAccessException, NoSuchFieldException {
        Row row = sheet.createRow(0);
        for (Enum en : headerEnum) {
            Field titleField = en.getDeclaringClass().getDeclaredField(TITLE);
            titleField.setAccessible(true);
            Cell cell = row.createCell(en.ordinal());
            cell.setCellValue((String) titleField.get(en));
        }
    }

}
