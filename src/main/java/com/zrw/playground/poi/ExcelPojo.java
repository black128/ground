package com.zrw.playground.poi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zrw
 */
@Data
@ExcelTarget("users")
public class ExcelPojo implements Serializable {

    @Excel(name = "序号")
    private String id;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "年龄", suffix = "")
    private Integer age;

    private ExcelPojoSon excelPojoSon;

}
