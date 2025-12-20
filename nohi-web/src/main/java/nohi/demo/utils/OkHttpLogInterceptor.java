package nohi.demo.utils;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author NOHI
 * @date 2022/8/25 22:25
 **/
public class OkHttpLogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }
}
