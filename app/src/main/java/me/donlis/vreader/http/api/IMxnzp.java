package me.donlis.vreader.http.api;

import java.util.List;

import io.reactivex.Observable;
import me.donlis.vreader.bean.BaseMzBean;
import me.donlis.vreader.bean.NewsDetail;
import me.donlis.vreader.bean.NewsItem;
import me.donlis.vreader.bean.NewsType;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMxnzp {

    /**
     * 新闻分类
     * @return
     */
    @GET("news/types")
    Observable<BaseMzBean<List<NewsType>>> getType();

    /**
     * 新闻列表
     * @param typeId 分类代码
     * @param page 页数
     * @return
     */
    @GET("news/list")
    Observable<BaseMzBean<List<NewsItem>>> getNews(@Query("typeId") int typeId,@Query("page") int page);

    /**
     * 新闻详情
     * @param newsId id
     * @return
     */
    @GET("news/details")
    Observable<BaseMzBean<NewsDetail>> getNewsDetail(@Query("newsId") String newsId);

}
