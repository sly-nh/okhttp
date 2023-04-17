package okhttp3.me;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @description:
 * @author: xiaohaotian
 * @date: 2023/4/4 17:57
 */
public class OkHttpTest {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Test
    public void okHTTP() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request req = builder.url("https://www.baidu.com").build();
        String s = okHttpClient.newCall(req).execute().body().source().readUtf8();
        Assert.assertTrue(StringUtils.isNotBlank(s));
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