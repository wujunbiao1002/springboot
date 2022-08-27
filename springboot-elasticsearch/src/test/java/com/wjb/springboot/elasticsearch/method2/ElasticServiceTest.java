package com.wjb.springboot.elasticsearch.method2;

import com.alibaba.fastjson.JSON;
import com.wjb.springboot.elasticsearch.method1.entity.User;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <b><code>ElasticServiceTest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/8/25 17:23.
 *
 * @author Arjun
 * @version 1.0.0
 * @since springboot
 */
@SpringBootTest
class ElasticServiceTest {
    RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9211, "http")));

    // 创建索引
    @SneakyThrows
    @Test
    void creatIndex() {
        CreateIndexRequest request = new CreateIndexRequest("user");
        request.settings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2));

        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.out.println("操作状态" + acknowledged);
    }

    @SneakyThrows
    @Test
    void queryIndex() {
        GetIndexRequest request = new GetIndexRequest("user");
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        System.out.println("aliases" + response.getAliases());
        System.out.println("mapping" + response.getMappings());
        System.out.println("settings" + response.getSettings());
    }

    @SneakyThrows
    @Test
    void delIndex() {
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println("操作结果：" + response.isAcknowledged());
    }

    @SneakyThrows
    @Test
    void addDoc() {
        for (int i = 1001; i < 1010; i++) {
            User user = new User();
            user.setId(String.valueOf(i));
            user.setAge((short) (int) (Math.random() * 100));
            user.setName("lucy" + i);
            user.setDescription("美丽" + i);
            IndexRequest request = new IndexRequest();
            request.index("user").id(user.getId());
            request.source(JSON.toJSONString(user), XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            System.out.println("_index:" + response.getIndex());
            System.out.println("_id:" + response.getId());
            System.out.println("_result:" + response.getResult());
        }
    }

    @SneakyThrows
    @Test
    void updateDoc() {
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1001");
        request.doc(XContentType.JSON, "age", 18);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println("_index:" + response.getIndex());
        System.out.println("_id:" + response.getId());
        System.out.println("_result:" + response.getResult());
    }

    @Test
    @SneakyThrows
    void delDoc() {
        for (int i = 1000; i < 1010; i++) {
            DeleteRequest request = new DeleteRequest();
            request.index("user").id(String.valueOf(i));
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
            System.out.println("结果：" + response.getResult());
        }
    }

    @Test
    @SneakyThrows
    void queryDoc() {
        GetRequest request = new GetRequest().index("user").id("1000");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println("_index:" + response.getIndex());
        System.out.println("_id:" + response.getId());
        System.out.println("_type:" + response.getType());
        System.out.println("source:" + response.getSource());
    }

    @SneakyThrows
    @Test
    void addDocBath() {
        User user = new User();
        user.setId("1001");
        user.setAge((short) 63);
        user.setName("lucy");
        user.setDescription("美丽");
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest().index("user").source(JSON.toJSONString(user), XContentType.JSON));
        bulkRequest.add(new IndexRequest().index("user").source(JSON.toJSONString(user), XContentType.JSON));
        bulkRequest.add(new IndexRequest().index("user").source(JSON.toJSONString(user), XContentType.JSON));
        bulkRequest.add(new IndexRequest().index("user").source(JSON.toJSONString(user), XContentType.JSON));

        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("getTook:" + response.getTook());
        System.out.println("getItems:" + response.getItems());
    }

    // 高级查询
    @SneakyThrows
    @Test
    void queryHighLevel() {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // mathAll查询所有
//        sourceBuilder.query(QueryBuilders.matchAllQuery());

        // term,查询条件为关键字
//        sourceBuilder.query(QueryBuilders.termQuery("age","63"));

        // 分页查询
//        sourceBuilder.query(QueryBuilders.matchAllQuery());
//        sourceBuilder.from(0);
//        sourceBuilder.size(2);

        // 排序查询
//        sourceBuilder.query(QueryBuilders.matchAllQuery());
//        sourceBuilder.sort("age", SortOrder.ASC);

        // 过滤字段查询
//        String [] excludes = {};
//        String [] includes = {"name", "age"};
//        sourceBuilder.fetchSource(includes,excludes);

        // bool查询
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(QueryBuilders.matchQuery("age","60"));
//        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("name","lucy2"));
//        boolQueryBuilder.should(QueryBuilders.matchQuery("id", "1002"));
//        sourceBuilder.query(boolQueryBuilder);

        // 范围查询
//        sourceBuilder.query(QueryBuilders.rangeQuery("age").gte("54").lte("60"));

        // 模糊查询
//        sourceBuilder.query(QueryBuilders.fuzzyQuery("name","lucy").fuzziness(Fuzziness.ONE));

        // 高亮查询
//        sourceBuilder.query(QueryBuilders.termQuery("name","lucy"));
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags("<font color='red'>");
//        highlightBuilder.postTags("</font>");
//        highlightBuilder.field("name");
//        sourceBuilder.highlighter(highlightBuilder);

        // 聚合查询 max age 63
//        sourceBuilder.aggregation(AggregationBuilders.max("age").field("age"));

        // 分组统计
//        sourceBuilder.aggregation(AggregationBuilders.terms("age_group").field("age"));

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
            //打印高亮
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            System.out.println(highlightFields);
        }
        System.out.println("<<========");
    }

}