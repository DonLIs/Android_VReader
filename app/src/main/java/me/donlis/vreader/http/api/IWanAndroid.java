package me.donlis.vreader.http.api;

import java.util.List;

import io.reactivex.Observable;
import me.donlis.vreader.bean.BaseWanAndroidBean;
import me.donlis.vreader.bean.DataBean;
import me.donlis.vreader.bean.NavBean;
import me.donlis.vreader.bean.TreeBean;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IWanAndroid {

    /**
     * 玩安卓，文章列表、知识体系下的文章
     *
     * @param page 页码，从0开始
     * @param cid  体系id
     */
    @GET("article/list/{page}/json")
    Observable<BaseWanAndroidBean<DataBean>> getHomeList(@Path("page") int page, @Query("cid") Integer cid);

    /**
     * 玩安卓，项目；列表
     *
     * @param page 页码，从0开始
     */
    @GET("article/listproject/{page}/json")
    Observable<BaseWanAndroidBean<DataBean>> getProjectList(@Path("page") int page);

    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<BaseWanAndroidBean<List<TreeBean>>> getTree();

    /**
     * 导航数据
     */
    @GET("navi/json")
    Observable<BaseWanAndroidBean<List<NavBean>>> getNavi();

}
