package io.crescent.retrofit.endpoint;

import io.crescent.retrofit.RetrofitService;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;

@RetrofitService("alert")
public interface AlertEndPoint {

    @GET(value = "/alert/infos")
    public Call<Map<String, Object>> getAlertInfo();

}
