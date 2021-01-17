package cn.wangyudi.zjbtiautosign;

import cn.wangyudi.zjbtiautosign.mail.SendCloud;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class Sign {
    private final OkHttpClient client;
    private String lat;
    private String lng;
    private String studentId;
    private String token;
    private String chuangyi;

    public Sign() {
        this.client = new OkHttpClient();
    }

    public Sign set(String lat, String lng, String studentId, String token, String chuangyi) {
        this.lat = lat;
        this.lng = lng;
        this.studentId = studentId;
        this.token = token;
        this.chuangyi = chuangyi;
        return this;
    }

    public void build() {
        String postBody = String.format("lat=%s&lng=%s&studentId=%s&token=%s", this.lat, this.lng, this.studentId, this.token);
        System.out.println("Send->" + postBody);
        Headers headers = new Headers.Builder()
                .add("Host", "zgszjsq.cunminss.com")
                .add("Connection", "keep-alive")
                .add("Content-Length", String.valueOf(postBody.length()))
                .add("Accept", "application/json, text/plain, */*")
                .add("Origin", "https://zgszjsq.cunminss.com")
                .add("Content-Type", "application/x-www-form-urlencoded")
                .add("Referer", "https://zgszjsq.cunminss.com/?version1.3&code=0915EjGa1LExjA09ZDHa1F1uhw15EjGr&state=STATE")
                .add("User-Agent", "Mozilla/5.0 (Linux; Android 7.1.2; G011C Build/N2G48H; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.70 Mobile Safari/537.36 MMWEBID/7078 MicroMessenger/7.0.12.1620(0x27000C34) Process/tools NetType/WIFI Language/zh_CN ABI/arm32")
                .add("token", this.token)
                .add("chuangyi", this.chuangyi)
                .add("X-Requested-With", "com.tencent.mm")
                .add("Accept-Encoding", "gzip, deflate")
                .add("Accept-Language", "zh-CN")
                .build();
        Request request = new Request.Builder()
                .url("https://zgszjsq.cunminss.com/api/s/gpsSign")
                .headers(headers)
                .post(RequestBody.create(postBody, MediaType.parse("application/x-www-form-urlencoded")))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Error->" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = Objects.requireNonNull(response.body()).string();
                System.out.println("Return->" + res);
                new SendCloud("AbstractPrinter_test_Krm36X", "e78c32f95d33a0364c67becb0e6f9131")
                        .setFromMail("wangyudi007@gmail.com")
                        .setFromName("Wangyudi")
                        .setToMail("1580694189@qq.com")
                        .setSubject("ZJBTI每日签到")
                        .setHtml(res)
                        .build();
                App.isSigned = true;
            }
        });
    }
}
