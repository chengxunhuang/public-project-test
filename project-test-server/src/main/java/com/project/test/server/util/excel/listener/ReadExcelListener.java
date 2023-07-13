package com.project.test.server.util.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.project.test.server.entity.PayOrderExcel;
import com.project.test.server.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: wh
 * @Date: 2022/08/09/16:14
 * @Description:
 */
@Slf4j
public class ReadExcelListener implements ReadListener<PayOrderExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以500条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 500;
    /**
     * 缓存的数据
     */
    private List<PayOrderExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private final PayOrderService payOrderService;

    public ReadExcelListener(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(PayOrderExcel data, AnalysisContext context) {
        log.info("解析到一条数据");
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList.clear();
//            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        payOrderService.save(cachedDataList);
    }
}
