package com.estyle.httpmock;

import android.content.Context;

import com.estyle.httpmock.common.AbstractHttpMockGenerator;
import com.estyle.httpmock.common.MockEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;

public class HttpMockInterceptor implements Interceptor {

    private Context mContext;
    private boolean mEnable;// 全局启用假数据
    private AbstractHttpMockGenerator mGenerator;

    public HttpMockInterceptor(Context context, boolean enable, Class<? extends AbstractHttpMockGenerator> clazz) {
        mContext = context;
        mEnable = enable;
        try {
            mGenerator = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        if (!mEnable) return chain.proceed(oldRequest);

        String url = oldRequest.url().toString();

        // 根据url获取对应mock
        String infoJson = mGenerator.getJSONString();
        TypeToken<List<MockEntity>> token = new TypeToken<List<MockEntity>>() {
        };
        List<MockEntity> mockList = new Gson().fromJson(infoJson, token.getType());
        MockEntity mockEntity = null;
        for (MockEntity entity : mockList) {
            if (url.contains(entity.getUrl())) {
                mockEntity = entity;
                break;
            }
        }
        if (!mockEntity.isEnable()) return chain.proceed(oldRequest);

        // 启用mock，则读取assets中的json文件
        InputStream open = mContext.getAssets()
                .open("httpmock_debug/" + mockEntity.getFileName());
        byte[] buff = new byte[open.available()];
        open.read(buff);
        String mockJson = new String(buff, "UTF-8");
        open.close();


        // 模拟网络延迟，单位ms
        try {
            long delayMillis = mockEntity.getDelayMillis() < 0 ? 0 : mockEntity.getDelayMillis();
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 创建Response
        Buffer buffer = new Buffer().writeUtf8(mockJson);
        return new Response.Builder()
                .request(oldRequest)
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("MOCK")
                .body(new RealResponseBody(
                        "application/infoJson; charset=UTF-8",
                        buffer.size(),
                        buffer
                ))
                .build();
    }
}
