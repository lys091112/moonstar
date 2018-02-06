package com.xianyue.retrofit.endpoint;

import com.xianyue.retrofit.RetrofitService;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;

@RetrofitService("git")
public interface GitHubEndPoint {

    @GET(value = "/users/lys091112")
    public Call<Map<String, Object>> getUserInfo();


}
