package me.donlis.vreader.http.api;

import io.reactivex.Observable;
import me.donlis.vreader.bean.HomeListBean;
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
    Observable<HomeListBean> getHomeList(@Path("page") int page, @Query("cid") Integer cid);

    /**
     * 玩安卓，项目；列表
     *
     * @param page 页码，从0开始
     */
    @GET("article/listproject/{page}/json")
    Observable<HomeListBean> getProjectList(@Path("page") int page);

}
