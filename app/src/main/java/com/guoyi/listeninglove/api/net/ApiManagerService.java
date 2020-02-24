package com.guoyi.listeninglove.api.net;

import com.guoyi.listeninglove.api.ApiModel;
import com.guoyi.listeninglove.ui.my.user.User;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by yonglong on 2017/9/11.
 */

public interface ApiManagerService {

    @POST
    Observable<ApiModel<User>> getUserInfo(@Url String baseUrl, @QueryMap Map<String, String> params);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String downloadUrl, @HeaderMap Map<String, String> params);

}
