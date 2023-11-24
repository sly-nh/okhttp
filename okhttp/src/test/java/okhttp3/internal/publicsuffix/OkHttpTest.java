package okhttp3.internal.publicsuffix;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: xiaohaotian
 * @date: 2023/4/4 17:57
 */
public class OkHttpTest {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Test
    public void okSyncHTTP() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        Request req = builder.url("https://www.baidu.com").build();
        Response resp = okHttpClient.newCall(req).execute();
        String s = resp.body().source().readUtf8();
        System.out.println(s);
        Assert.assertTrue(s.length() > 0);
    }

    @Test
    public void asyncHTTP() throws IOException, InterruptedException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request req = builder.url("https://www.baidu.com").build();
        final int[] success = {0};

        System.out.println();
        for (int i = 0; i < 10; i++) {
            Call call = okHttpClient.newCall(req);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println(String.format("pool:%s, threadname:%s", Thread.currentThread().getThreadGroup().getName(),Thread.currentThread().getName()));
                    success[0]++;
                }
            });
        }

        Response resp = okHttpClient.newCall(req).execute();
        String s = resp.body().source().readUtf8();
        TimeUnit.SECONDS.sleep(100);
        Assert.assertTrue(success[0] == 10);
    }

    @Test
    public void postRequest() {
        String content = "{\n"
                + "    \"masterDesignCodeList\": [\n"
                + "        \"100\"\n"
                + "    ],\n"
                + "    \"masterDesignCode\": \"MD1643196796614213632\"\n"
                + "}";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, content);



    }
}