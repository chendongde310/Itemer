package com.cqcb.chuizi.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cqcb.chuizi.net.interceptor.StatusCodeInterceptor;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 *
 * 作者：陈东
 * 1279262533@qq.com
 * 日期：2017/3/17 - 下午1:20
 *
 */
public class MrService {

    private static MrService ourInstance = new MrService();

    private Retrofit mRetrofit;
    private OkHttpClient okHttpClient;
    private String mosaicUrl;

    private MrService() {

    }

    public static MrService getInstance() {
        if (ourInstance.mRetrofit == null) {
            throw new NullPointerException("先Builder初始化再使用");
        }

        ourInstance.mosaicUrl = null;

        return ourInstance;
    }

    public static Retrofit retrofit() {
        return getInstance().mRetrofit;
    }

    private MrService create(Retrofit mRetrofit, OkHttpClient okHttpClient) {
        this.mRetrofit = mRetrofit;
        this.okHttpClient = okHttpClient;
        return ourInstance;
    }


    public static Retrofit getMosaicRetrofit(String mosaicurl) {
        return getInstance().mosaicRetrofit(mosaicurl);
    }

    /**
     * 创建一个新的Retrofit
     * @param mosaicurl
     * @return
     */
    public static Retrofit CreateMosaicRetrofit(String mosaicurl) {
        return getInstance().mosaicRetrofit(mosaicurl);
    }




    /**
     * 拼接URL
     * @param mosaicurl
     */
    private  Retrofit mosaicRetrofit(String mosaicurl) {
        ourInstance.mosaicUrl = mosaicurl;
        return mRetrofit;
    }






    public static class Builder {
        List<StatusCodeInterceptor> sciList = new ArrayList<>();
        /**
         * 是否为开发者模式，默认为true
         */
        private boolean is_dev = true;
        private boolean isStatusCode = true; //是否拦http截状态码
        private boolean useCookie = true; //是否保存cookie
        private boolean useRxJava = true;//是否使用RxJava
        private boolean useNativeCache = false; //是否使用本地缓存
        private int linkTimeout = 10;// 连接超时时间
        private int readTimeout = 10;// 读取超时时间
        private Context context;
        private String baseUrl; //
        private Retrofit mRetrofit;
        private int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
        private int maxStale = 60 * 60 * 24 * 7; // 无网络时，设置超时为1周
        private Interceptor HeadInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!isConnectedByState(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    if (is_dev) {
                        Logger.d("没有网络连接");
                    }
                }
                Response response = chain.proceed(request);

                if (isConnectedByState(context)) {
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    Logger.d("network error");
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }

        };
        private long cacheSize = 200 * 1024 * 1024;//缓存区大小 默认200M
        private String cacheDirectoryName = "retrofit_cache_default";
        private File httpCacheDirectory;
        private Cache cache;
        private boolean useNetCache = true;


        private Builder(Context context) {
            this.context = context;

        }

        public static Builder newBuilder(Context context) {
            return new Builder(context);
        }

        public Builder addStatusCodeInterceptor(StatusCodeInterceptor statusCodeInterceptor) {
            if (statusCodeInterceptor != null)
                sciList.add(statusCodeInterceptor);
            return this;
        }

        /**
         * 判断当前网络是否已经连接
         *
         * @param context 上下文
         * @return 当前网络是否已经连接。false：尚未连接
         */
        private boolean isConnectedByState(Context context) {
            return getCurrentNetworkState(context) == NetworkInfo.State.CONNECTED;
        }

        /**
         * 获取当前网络的状态
         *
         * @param context 上下文
         * @return 当前网络的状态。具体类型可参照NetworkInfo.State.CONNECTED、NetworkInfo.State.CONNECTED.DISCONNECTED等字段。当前没有网络连接时返回null
         */
        private NetworkInfo.State getCurrentNetworkState(Context context) {
            NetworkInfo networkInfo
                    = ((ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return networkInfo != null ? networkInfo.getState() : null;
        }


        /**
         * 是否为开发者模式 ，默认为true
         *
         * @param isDev
         * @return Builder
         */
        public Builder devMod(boolean isDev) {
            is_dev = isDev;
            return this;
        }

        /**
         * 是否开启本地缓存
         *
         * @param isCache
         * @return
         */
        public Builder useNativeCache(boolean isCache) {
            useNativeCache = isCache;
            return this;
        }

        /**
         * Set the API base URL.
         *
         * @param baseUrl HttpUrl
         * @return Builder
         */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 连接超时时间
         *
         * @param linkTimeout
         * @return Builder
         */
        public Builder linkTimeout(int linkTimeout) {
            this.linkTimeout = linkTimeout;
            return this;
        }

        /**
         * 读取超时时间
         *
         * @param readTimeout
         * @return Builder
         */
        public Builder readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * 设置缓存区大小
         *
         * @param cacheSize 默认20M
         * @return
         */
        public Builder cacheSize(long cacheSize) {
            this.cacheSize = cacheSize;
            return this;
        }

        /**
         * 是否使用RxJava
         *
         * @param useRxJava
         * @return Builder
         */
        public Builder useRxJava(boolean useRxJava) {
            this.useRxJava = useRxJava;
            return this;
        }

        /**
         * 是否开始Cookie持久化
         *
         * @param useCookie 默认开启
         * @return
         */
        public Builder useCookie(boolean useCookie) {
            this.useCookie = useCookie;
            return this;
        }

        /**
         * 是否使用网络缓存
         *
         * @param useNetCache
         * @return
         */
        public Builder useNetCache(boolean useNetCache) {
            this.useNetCache = useNetCache;
            return this;
        }

        /**
         * 有网络时 设置缓存超时时间
         *
         * @param maxAge 默认0个小时
         * @return Builder
         */
        public Builder cacheTimeout(int maxAge) {
            this.maxAge = maxAge;
            return this;
        }

        /**
         * 无网络时，设置缓存超时时间
         *
         * @param maxStale 默认超时为1周
         * @return Builder
         */
        public Builder notNetCacheTimeout(int maxStale) {
            this.maxStale = maxStale;
            return this;
        }

        /**
         * 设置缓存目录名称
         *
         * @param cacheDirectoryName 默认 retrofit_cache_default
         * @return
         */
        public Builder cacheDirectoryName(String cacheDirectoryName) {
            this.cacheDirectoryName = cacheDirectoryName;
            return this;
        }


        /**
         * @return
         */
        public MrService build() {
            OkHttpClient okHttpClient = getClient();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(FastJsonConverterFactory.create());
            if (useRxJava) {
                builder.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));
            }
            mRetrofit = builder.build();
            return MrService.ourInstance.create(mRetrofit, okHttpClient);
        }







        private OkHttpClient getClient() {
            // log用拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
            if (is_dev) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            }
            OkHttpClient.Builder builder = new OkHttpClient.Builder()

                    .addInterceptor(logging)
                    // 连接超时时间设置
                    .connectTimeout(linkTimeout, TimeUnit.SECONDS)
                    // 读取超时时间设置
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    // 信任所有主机名
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            // TODO: 2017/3/16  设置验证开关
                            return true;
                        }
                    });

            if (useNetCache) {
                // HeadInterceptor实现了Interceptor，用来往Request Header添加一些业务相关数据，如APP版本，token信息
                builder.addInterceptor(HeadInterceptor);
            }


            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                   // Logger.d("拼接字段 = "+ourInstance.mosaicUrl);
                    if (ourInstance.mosaicUrl!=null) {
                        request = request.newBuilder()
                                .url(getUrl(request.url()))
                                .build();
                    }
                    //Logger.d("请求连接"+request.url());
                    Response response = chain.proceed(request);
                    return response;
                }
            });



            if (isStatusCode) {
                if (sciList.size() > 0) {
                    for (StatusCodeInterceptor sci : sciList) {
                        builder.addInterceptor(sci);
                    }
                }
            }


            if (useCookie) {
                builder.cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        // 这里使用host name作为cookie保存的key
                        cookieStore.put(HttpUrl.parse(url.host()), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse(url.host()));
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                });
            }

            if (useNativeCache) {
                this.httpCacheDirectory = new File(context.getCacheDir(), cacheDirectoryName);
                this.cache = new Cache(httpCacheDirectory, cacheSize);
                builder.cache(cache);
            }


            return builder.build();
        }


        private String getUrl(HttpUrl url) {
            String newUrl = mRetrofit.baseUrl().toString() + ourInstance.mosaicUrl;
            newUrl = newUrl + url.toString().substring(mRetrofit.baseUrl().toString().length());
            Logger.d(newUrl);
            return newUrl;
        }

    }
}
