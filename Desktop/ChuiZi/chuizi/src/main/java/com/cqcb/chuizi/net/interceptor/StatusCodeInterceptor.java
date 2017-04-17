package com.cqcb.chuizi.net.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 作者：陈东
 * Github.com/chendongde310
 * 日期：2017/3/16 - 上午11:29
 * 状态码拦截器
 */
public class StatusCodeInterceptor implements Interceptor {

    List<Integer> codess = new ArrayList<>();
    private StatusCodeCallback callback;

    public StatusCodeInterceptor(@NonNull StatusCodeCallback callback,@NonNull int... codes) {
        this.callback = callback;
        for (int i = 0; i < codes.length; i++) {
            codess.add(codes[i]);
        }
    }

    public StatusCodeInterceptor(@NonNull StatusCodeCallback callback,@NonNull List<Integer> codes) {
        this.callback = callback;
        codess = codes;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        for (int code : codess) {
            if (response.code() == code) {
                callback.InterceptOperation(response);
            }
        }
        return response;
    }


    public interface StatusCodeCallback {
        void InterceptOperation(Response response);
    }
}
