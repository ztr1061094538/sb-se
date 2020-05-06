package com.tg.enterprise.util;


import jxl.write.WriteException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jxls.area.Area;
import org.jxls.command.AbstractCommand;
import org.jxls.command.Command;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.common.Size;
import org.jxls.transform.Transformer;
import org.jxls.transform.jexcel.JexcelTransformer;
import org.jxls.transform.poi.PoiCellData;
import org.jxls.transform.poi.PoiTransformer;

/**
 * <p>合并单元格</p>
 * jx:merge(
 * lastCell="单元格"
 * [, cols="合并的列数"]
 * [, rows="合并的行数"]
 * [, minCols="最小合并的列数"]
 * [, minRows="最小合并的行数"]
 * )
 *
 * @author Victor
 * @date 2018年12月6日
 */
public class MergeCommand extends AbstractCommand {
    private String cols;        //合并的列数
    private String rows;        //合并的行数
    private String minCols;     //最小合并的列数
    private String minRows;     //最小合并的行数
    private CellStyle cellStyle;//第一个单元格的样式

    private Area area;

    @Override
    public String getName() {
        return "merge";
    }

    @Override
    public Command addArea(Area area) {
        if (super.getAreaList().size() >= 1) {
            throw new IllegalArgumentException("You can add only a single area to 'merge' command");
        }
        this.area = area;
        return super.addArea(area);
    }

    @Override
    public Size applyAt(CellRef cellRef, Context context) {
        int rows = area.getSize().getHeight();
        int cols = area.getSize().getWidth();
        rows = Math.max(getVal(this.rows, context), rows);
        cols = Math.max(getVal(this.cols, context), cols);
        rows = Math.max(getVal(this.minRows, context), rows);
        cols = Math.max(getVal(this.minCols, context), cols);
        if (rows > 1 || cols > 1) {
            Transformer transformer = this.getTransformer();
            if (transformer instanceof PoiTransformer) {
                poiMerge(cellRef, context, (PoiTransformer) transformer, rows, cols);
            } else if (transformer instanceof JexcelTransformer) {
                jexcelMerge(cellRef, context, (JexcelTransformer) transformer, rows, cols);
            }
        }
        area.applyAt(cellRef, context);
        return new Size(cols, rows);
    }

    protected Size poiMerge(CellRef cellRef, Context context, PoiTransformer transformer, int rows, int cols) {
        Sheet sheet = transformer.getWorkbook().getSheet(cellRef.getSheetName());
        CellRangeAddress region = new CellRangeAddress(
                cellRef.getRow(),
                cellRef.getRow() + rows - 1,
                cellRef.getCol(),
                cellRef.getCol() + cols - 1);
        sheet.addMergedRegion(region);

        //合并之后单元格样式会丢失，以下操作将合并后的单元格恢复成合并前第一个单元格的样式
        area.applyAt(cellRef, context);
        if (cellStyle == null) {
            PoiCellData cellData = (PoiCellData) transformer.getCellData(area.getStartCellRef());
            if (cellData != null) {
                cellStyle = cellData.getCellStyle();
            }
        }
        setRegionStyle(cellStyle, region, sheet);
        return new Size(cols, rows);
    }

    protected Size jexcelMerge(CellRef cellRef, Context context, JexcelTransformer transformer, int rows, int cols) {
        try {
            transformer.getWritableWorkbook().getSheet(cellRef.getSheetName())
                    .mergeCells(
                            cellRef.getRow(),
                            cellRef.getCol(),
                            cellRef.getRow() + rows - 1,
                            cellRef.getCol() + cols - 1);
            area.applyAt(cellRef, context);
        } catch (WriteException e) {
            throw new IllegalArgumentException("合并单元格失败");
        }
        return new Size(cols, rows);
    }

    private static void setRegionStyle(CellStyle cs, CellRangeAddress region, Sheet sheet) {
        for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    cell = row.createCell(j);
                }
                if (cs == null) {
                    cell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
                    cell.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                } else {
                    cell.setCellStyle(cs);
                }
            }
        }
    }

    private int getVal(String expression, Context context) {
        if (StringUtils.isNotBlank(expression)) {
            Object obj = getTransformationConfig().getExpressionEvaluator().evaluate(expression, context.toMap());
            try {
                return Integer.parseInt(obj.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("表达式：" + expression + " 解析失败");
            }
        }
        return 0;
    }

    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getMinCols() {
        return minCols;
    }

    public void setMinCols(String minCols) {
        this.minCols = minCols;
    }

    public String getMinRows() {
        return minRows;
    }

    public void setMinRows(String minRows) {
        this.minRows = minRows;
    }
}