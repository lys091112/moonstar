package io.crescent.retrofit.autoconfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import retrofit2.Retrofit;

/*
 * 用来保存不同标识的retrofit Builder对象
 */
public class RetrofitServiceHolder {

  private static Map<String, Retrofit> retrofitMap = new HashMap<>();

  public void register(String tenant, Retrofit retrofit) {
    retrofitMap.putIfAbsent(tenant, retrofit);
  }

  public Optional<Retrofit> getRetrofit(String tenant) {
    return Optional.ofNullable(retrofitMap.getOrDefault(tenant, null));
  }
}
