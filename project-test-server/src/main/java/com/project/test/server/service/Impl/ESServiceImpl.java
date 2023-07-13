package com.project.test.server.service.Impl;

import com.alibaba.fastjson.JSON;
import com.project.test.server.entity.ESPayOrder;
import com.project.test.server.entity.ESReturn;
import com.project.test.server.entity.PayOrder;
import com.project.test.server.entity.PayOrderVO;
import com.project.test.server.service.ESService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wh
 * @Date: 2022/07/30/15:41
 * @Description:
 */
@Service
public class ESServiceImpl implements ESService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public void search(PayOrderVO payOrderVO) {
        // 返回体
        ESReturn result = null;
        // 1.准备检索请求 - 创建搜索构建者
        SearchRequest searchRequest = buildSearchRequest(payOrderVO);
        try {
            //2.执行检索请求
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            //3.分析响应数据封装成我们需要的格式
            result = buildSearchResult(response, payOrderVO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void del() {
        // 使用java8的日期类,获取前一天
        LocalDateTime dateTime = LocalDateTime.now().minusDays(1);
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(dateTime);

        DeleteByQueryRequest delete = new DeleteByQueryRequest("pay");
        BoolQueryBuilder where = QueryBuilders.boolQuery();
        // 时间范围 gt 大于，实际上是删除的是当前的日志
        QueryBuilder whereTime = QueryBuilders.rangeQuery("@timestamp").gt(format);
        where.must(whereTime);
        delete.setQuery(where);

        try {
            BulkByScrollResponse response = client.deleteByQuery(delete, RequestOptions.DEFAULT);
            System.out.println("删除当天记录耗时"+response.getTook());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ESReturn buildSearchResult(SearchResponse response, PayOrderVO payOrderVO) {
        ESReturn esReturn = new ESReturn();
        List<PayOrder> result = new ArrayList<>();
        //命中的所有记录
        SearchHits hits = response.getHits();

        if (hits.getHits() != null && hits.getHits().length > 0) {
            for (SearchHit hit : hits.getHits()) {
                //_Source就是保存的里面信息
                String sourceAsString = hit.getSourceAsString();
                PayOrder payOrder = JSON.parseObject(sourceAsString, PayOrder.class);
                result.add(payOrder);
            }
        }
        esReturn.setPayOrders(result);
        // 返回值 中需要有 Object[] esSortValues，取得经过排序的最后一个sort，用于记录下次将从这个地方开始取数
        if (result.size() > 0) {
            esReturn.setEsSortValues(response.getHits().getAt(result.size() - 1).getSortValues());
        }
        return esReturn;
    }

    private SearchRequest buildSearchRequest(PayOrderVO payOrderVO) {
        //构建DLS语句
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        /**
         * 设置构建搜索属性
         * 1.查询：模糊匹配，过滤
         */
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 1.1 must 模糊匹配
        if (StringUtils.isNotEmpty(payOrderVO.getIfCode())) {
            boolQuery.must(QueryBuilders.matchQuery("ifCode", payOrderVO.getIfCode()));
        }
        // 1.2 定义查询的字段
        String[] arrys = new String[]{"appId", "ifCode", "payOrderId"};
        sourceBuilder.fetchSource(arrys, Strings.EMPTY_ARRAY);
        sourceBuilder.query(boolQuery);
        // 分页 ,from size 查询的最大数 不能超过10000。默认设置 max_result_window 为10000
//        sourceBuilder.from((payOrderVO.getPageNum()-1)* payOrderVO.getPageSize());
        sourceBuilder.size(payOrderVO.getPageSize());
        sourceBuilder.sort(new FieldSortBuilder("_id").order(SortOrder.DESC));
        if (ObjectUtils.isNotEmpty(payOrderVO.getEsSortValues())) {
            sourceBuilder.searchAfter(payOrderVO.getEsSortValues());
        }
        sourceBuilder.searchAfter();

        // 索引: pay
        SearchRequest searchRequest = new SearchRequest(new String[]{"pay"}, sourceBuilder);
        return searchRequest;
    }
}
