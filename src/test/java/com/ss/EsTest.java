package com.ss;

import com.ss.entity.Emp;
import com.ss.entity.Video;
import com.ss.entity.Videoss;
import com.ss.repository.EmpRepository;
import com.ss.repository.VideossRepository;
import com.ss.service.VideoService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {

    @Resource
    ElasticsearchTemplate eat;

    @Resource
    EmpRepository empRepository;

    @Resource
    VideossRepository videossRepository;

    @Resource
    ElasticsearchTemplate esTemplate;
    
    @Resource
    VideoService videoService;

    @Test
    public void save() {
        Emp emp = new Emp("1", "巨石头",12,new Date());
        Emp emp1 = new Emp("2", "小石头",12,new Date());
        Emp emp2 = new Emp("3", "巨石头",12,new Date());
        empRepository.save(emp);
        empRepository.save(emp1);
        empRepository.save(emp2);

    }

    @Test
    public void delete() {
        Emp emp = new Emp("1", "巨石头",12,new Date());
        empRepository.delete(emp);

    }

    @Test
    public void query(){
        /*Iterable<Emp> all = empRepository.findAll();*/
        /*Iterable<Emp> all = empRepository.findAll(Sort.by(Sort.Order.asc("age")));*/
        /*Page<Emp> all = empRepository.findAll(PageRequest.of(0, 2));
        List<Emp> all = empRepository.findByName("巨石头");*/

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxuss")//指定索引名
                .withTypes("emp")//指定索引类型
                .withQuery(QueryBuilders.queryStringQuery("石头").field("name"))//搜索的条件
                .build();

        List<Emp> all = esTemplate.queryForList(nativeSearchQuery, Emp.class);

        all.forEach(ss -> System.out.println(ss));

    }

    @Test
    public void saveVideo(){

        HashMap<String, Object> map = videoService.queryAllVideo(1, 100);
        List<Video> rows = (List<Video>) map.get("rows");

        for (Video ss : rows) {
            videossRepository.save(new Videoss(ss.getId(),
                    ss.getTitle(),
                    ss.getBrief(),
                    ss.getVipath(),
                    ss.getImgpath(),
                    ss.getPublishday(),
                    ss.getClassid(),
                    ss.getGroupid(),
                    ss.getUserid()));
            System.out.println(ss);
        }

        //videossRepository.save(new Videoss("1","小橘子s石头","我是一棵小石头,静静的埋在泥土之中s","","2.jpg",new Date(),"2","1","1"));
    }

    @Test
    public void queryVideo () {

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxue")
                .withTypes("ss")
                .withQuery(QueryBuilders.queryStringQuery("石头").field("title").field("brief"))
                .build();

        List<Video> list = esTemplate.queryForList(nativeSearchQuery, Video.class);
        list.forEach(ss -> System.out.println(ss));
    }

    @Test
    public void SearchVideo (){
        //处理高亮的操作
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:blue'>");//前缀
        field.postTags("</span>");//后缀
        //查询的条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxue")//指定索引名
                .withTypes("ss")//指定索引类型
                .withQuery(QueryBuilders.queryStringQuery("石头").field("title").field("brief"))//搜索的条件
                .withHighlightFields(field)//处理高亮
                //.withFields("title","brief","cover")  //查询返回指定字段
                .build();
        //高亮查询
        AggregatedPage<Videoss> videoList = esTemplate.queryForPage(nativeSearchQuery, Videoss.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                ArrayList<Videoss> list = new ArrayList<>();
                //获取查询结果
                SearchHit[] hits = searchResponse.getHits().getHits();

                for (SearchHit ss : hits) {
                    //原始数据
                    Map<String, Object> map = ss.getSourceAsMap();
                    //处理普通数据
                    String id = map.containsKey("id")?map.get("id").toString():null;
                    String title = map.containsKey("title")?map.get("title").toString():null;
                    String brief = map.containsKey("brief")?map.get("brief").toString():null;
                    String vipath = map.containsKey("vipath")?map.get("vipath").toString():null;
                    String imgpath = map.containsKey("imgpath")?map.get("imgpath").toString():null;

                    Date publishday = null;
                    if (map.containsKey("publishday")) {
                        String day = map.get("publishday").toString();
                        //处理日期转换
                        publishday = new Date(Long.valueOf(day));
                    }
                    String classid = map.containsKey("classid")?map.get("classid").toString():null;
                    String groupid = map.containsKey("groupid")?map.get("groupid").toString():null;
                    String userid = map.containsKey("userid")?map.get("userid").toString():null;
                    //封装video对象
                    Videoss videoss = new Videoss(id, title, brief, vipath, imgpath, publishday, classid, groupid, userid);

                    //处理高亮数据
                    Map<String, HighlightField> fieldMap = ss.getHighlightFields();

                    if (title != null) {
                        if (fieldMap.get("title") != null) {
                            videoss.setTitle(fieldMap.get("title").fragments()[0].toString());
                        }
                    }
                    if (brief != null) {
                        if (fieldMap.get("brief") != null) {
                            videoss.setBrief(fieldMap.get("brief").fragments()[0].toString());
                        }
                    }
                    //将对象放入集合
                    list.add(videoss);
                }
                //强转 返回
                return new AggregatedPageImpl<T>((List<T>) list);
            }
        });

        videoList.forEach(ss -> System.out.println(ss));

        videoList.forEach(video -> System.out.println(video));

    }


}
