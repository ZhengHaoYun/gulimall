package com.atguigu.gulimall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.common.to.es.SkuEsModel;
import com.atguigu.gulimall.search.config.GulimallElasticSearchConfig;
import com.atguigu.gulimall.search.constant.EsConstant;
import com.atguigu.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author haoyunzheng
 * @date 2022-07-05 15:00
 */
@Service
@Slf4j
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        // 保存到ES
        // 1.给ES建立一个索引 product
        BulkRequest bulkRequest = new BulkRequest();
        // 2.构造保存请求
        for (SkuEsModel esModel : skuEsModels) {
            // 设置索引
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            // 设置索引id
            indexRequest.id(esModel.getSkuId().toString());
            String jsonString = JSON.toJSONString(esModel);
            indexRequest.source(jsonString, XContentType.JSON);
            // add
            bulkRequest.add(indexRequest);
        }
        // bulk批量保存
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);
        boolean hasFailures = bulk.hasFailures();
        if (hasFailures) {
            List<String> collect = Arrays.stream(bulk.getItems()).map(BulkItemResponse::getId).collect(
                    Collectors.toList());
            log.error("商品上架错误：{}", collect);
        }
        return !hasFailures;

    }
}
