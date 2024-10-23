package com.config;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.Map;

/**
 * excel自定义拦截器
 *
 * @author mahe
 * @date 2023/9/14 16:29
 */
public class CommonSheetWriteHandler implements SheetWriteHandler {

    /**
     * 下拉框对应列数据关系信息
     */
    private final Map<Integer, String[]> mapDropDown;

    public CommonSheetWriteHandler(Map<Integer, String[]> mapDropDown) {
        this.mapDropDown = mapDropDown;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        // 获取一个workbook
        Sheet sheet = writeSheetHolder.getSheet();
        // 设置下拉框
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 定义sheet的名称
        String hiddenName = "hidden";
        // 1.创建一个隐藏的sheet 名称为 hidden
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        Sheet hidden = workbook.createSheet(hiddenName);
        Name category1Name = workbook.createName();
        category1Name.setNameName(hiddenName);
        for (Map.Entry<Integer, String[]> entry : mapDropDown.entrySet()) {
            // 下拉框的起始行,结束行,起始列,结束列
            CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, entry.getKey(), entry.getKey());
            // 获取excel列名
            String excelLine = getExcelLine(entry.getKey());
            // 2.循环赋值
            String[] values = entry.getValue();
            for (int i = 0, length = values.length; i < length; i++) {
                // 3.表示你开始的行数
                Row row = hidden.getRow(i);
                if (row == null) {
                    row = hidden.createRow(i);
                }
                row.createCell(entry.getKey()).setCellValue(values[i]);
            }
            // 4.=hidden!$H:$1:$H$50  sheet为hidden的H1列开始H50行数据获取下拉数组
            String refers = "=" + hiddenName + "!$" + excelLine +
                    "$1:$" + excelLine + "$" + (values.length);
            // 5.将刚才设置的sheet引用到你的下拉列表中
            DataValidationConstraint constraint = helper.createFormulaListConstraint(refers);
            DataValidation dataValidation = helper.createValidation(constraint, addressList);
            writeSheetHolder.getSheet().addValidationData(dataValidation);
        }
        // 设置列为隐藏
        int hiddenIndex = workbook.getSheetIndex("hidden");
        if (!workbook.isSheetHidden(hiddenIndex)) {
            workbook.setSheetHidden(hiddenIndex, true);
        }
    }

    /**
     * 获取excel列名
     *
     * @param num 列
     * @return 数据
     */
    private String getExcelLine(int num) {
        String line = "";
        int first = num / 26;
        int second = num % 26;
        if (first > 0) {
            line = (char) ('A' + first - 1) + "";
        }
        line += (char) ('A' + second) + "";
        return line;
    }
}
