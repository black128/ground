package com.zrw.playground.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class MyEasyPoi {
    private static List<ExcelPojo> getUsers() {
        List<ExcelPojo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ExcelPojo excelPojo = new ExcelPojo();
            excelPojo.setId(String.valueOf(i));
            excelPojo.setName("张三" + i);
            excelPojo.setAge(20 + i);
            list.add(excelPojo);
        }
        return list;
    }


    @Test
    public void teest() throws IOException {
        try {
            List<ExcelPojo> users = getUsers();
            Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "一班"), ExcelPojo.class, users);

            File file = new File("E:\\study\\playground\\src\\main\\java\\com\\zrw\\playground\\poi\\test.xlsx");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            sheets.write(fileOutputStream);
            sheets.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出错误{}", e.getLocalizedMessage());
        }

    }

    public static Lock lock = new ReentrantLock();
    public int count = 100;
    private static final CountDownLatch LATCH = new CountDownLatch(100);
    Runnable runnable = () -> {
        do {
            try {
                lock.lock();
                Thread.sleep(50);
                System.out.println(Thread.currentThread().getName() + ":" + count);
                count--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
                LATCH.countDown();
            }

        } while (count > 0);

    };

    @Test
    public void test2() throws InterruptedException {

        System.out.println("test2");
        new Thread(runnable).start();
        new Thread(runnable).start();
        LATCH.await();


    }

}
