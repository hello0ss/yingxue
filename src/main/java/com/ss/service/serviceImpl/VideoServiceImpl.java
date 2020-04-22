package com.ss.service.serviceImpl;

import com.ss.annotation.AddCache;
import com.ss.annotation.AddLog;
import com.ss.annotation.DelCache;
import com.ss.dao.VideoDAO;
import com.ss.entity.Video;
import com.ss.entity.Videoss;
import com.ss.po.VideoPo;
import com.ss.repository.VideossRepository;
import com.ss.service.VideoService;
import com.ss.util.AliyunOssUtil;
import com.ss.util.ImageCodeUtil;
import com.ss.vo.VideoVo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Resource
    VideoDAO videoDAO;

    @Resource
    ElasticsearchTemplate esTemplate;

    @Resource
    VideossRepository videossRepository;

    @AddCache
    @AddLog(value = "视频信息查询")
    @Override
    public HashMap<String, Object> queryAllVideo(Integer page, Integer rows) {
        /*
         * page: 当前页
         * rows: 每页条数
         * total: 总页数
         * records: 总条数
         * */
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);

        //开始页数
        Integer start = (page-1)*rows;
        //每页展示数据
        List<Video> list = videoDAO.showAllVideo(start, rows);
        map.put("rows",list);

        //总数据数
        Integer records = videoDAO.conter();
        map.put("records",records);

        //总页数
        Integer total = records%rows==0?records/rows:records/rows+1;
        map.put("total",total);

        return map;
    }

    @DelCache
    @AddLog(value = "视频上传")
    //文件上传
    public void uploadImgVideo(MultipartFile vipath, String id) {

        System.out.println("---------"+vipath.getOriginalFilename());
        //上传到阿里云
        AliyunOssUtil.upLoadFileBytes("ss-yingxue",vipath.getOriginalFilename(),vipath);

        //4.图片修改
        Video video = new Video();
        System.out.println("1111");
        videoDAO.updateVideo(video.setId(id)
                .setVipath("https://ss-yingxue.oss-cn-beijing.aliyuncs.com/" + vipath.getOriginalFilename()));

    }

    @DelCache
    @AddLog(value = "视频信息添加")
    @Override
    public String insertVideo(Video video) {
        videoDAO.insertVideo(video.setId(ImageCodeUtil.getNumber(5)).setImgpath("俺不是道").setPublishday(new Date()));

        videossRepository.save(new Videoss(video.getId(),
                video.getTitle(),
                video.getBrief(),
                video.getVipath(),
                video.getImgpath(),
                video.getPublishday(),
                video.getClassid(),
                video.getGroupid(),
                video.getUserid()));

        return video.getId();
    }

    @DelCache
    @AddLog(value = "视频信息修改")
    @Override
    public void updateVideo(Video video) {
        if (video.getVipath()=="") {
            video.setVipath(videoDAO.showVideo(video.getId()).getVipath());
        }
        videoDAO.updateVideo(video);
    }

    @DelCache
    @AddLog(value = "视频信息删除")
    @Override
    public HashMap<String, Object> deleteVideo(String id) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            //字符串拆分
            String[] viSplit = videoDAO.showVideo(id).getVipath().split("/");
            String[] imgSplit = videoDAO.showVideo(id).getImgpath().split("/");

            String viName = viSplit[viSplit.length - 1];
            String imgName = imgSplit[imgSplit.length - 1];

            System.out.println(viName);
            System.out.println(imgName);

            //删除数据
            videoDAO.deleteVideo(id);

            //删除阿里云视频文件
            AliyunOssUtil.delete("ss-yingxue", viName);//视频
            AliyunOssUtil.delete("ss-yingxue", imgName);//封面

            map.put("message", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "删除成功");
        }
        return map;
    }

    @AddCache
    @AddLog(value = "模糊查询")
    @Override
    public List<Videoss> querySearch(String content) {

        //处理高亮的操作
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>");//前缀
        field.postTags("</span>");//后缀
        //查询的条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxue")//指定索引名
                .withTypes("ss")//指定索引类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief"))//搜索的条件
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

        //videoList.forEach(ss -> System.out.println(ss));

        return videoList.getContent();
    }


    //前台首页展示全部视频
    @AddCache
    @AddLog(value = "前台首页展示")
    @Override
    public List<VideoVo> queryByReleaseTime() {

        List<VideoPo> list = videoDAO.queryByReleaseTime();

        ArrayList<VideoVo> vos = new ArrayList<>();

        for (VideoPo ss : list) {

            VideoVo videoVo = new VideoVo(ss.getId(),
                                            ss.getTitle(),
                                            ss.getImgpath(),
                                            ss.getVipath(),
                                            ss.getPublishday(),
                                            ss.getBrief(),
                                            ImageCodeUtil.getNumber(6),
                                            ss.getClassname(),
                                            ss.getImg());
            vos.add(videoVo);
        }
        return vos;

    }
}
