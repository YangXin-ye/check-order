package com.utils;


import com.aliyun.dysmsapi20170525.Client;

import java.util.Random;

public class SendMsgUtil {

    /**
     * 使用AK&SK初始化账号Client
     * @return Client
     * @throws Exception
     */
    public static Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，请确保代码运行环境设置了环境变量
                .setAccessKeyId("")

                // 必填，请确保代码运行环境设置了环境变量
                .setAccessKeySecret("");
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    /**
     * API 相关
     * @return OpenApi.Params
     */
    public static com.aliyun.teaopenapi.models.Params createApiInfo() throws Exception {
        com.aliyun.teaopenapi.models.Params params = new com.aliyun.teaopenapi.models.Params()
                .setAction("SendSms")
                .setVersion("2017-05-25")
                .setProtocol("HTTPS")
                .setMethod("POST")
                .setAuthType("AK")
                .setStyle("RPC")
                .setPathname("/")
                .setReqBodyType("json")
                .setBodyType("json");
        return params;
    }

    public static void sendCode(String phone,String code) throws Exception {
        Client client = createClient();
        com.aliyun.teaopenapi.models.Params params = createApiInfo();
        java.util.Map<String, Object> queries = new java.util.HashMap<>();
        queries.put("PhoneNumbers", phone);
        queries.put("SignName", "阿里云短信测试");
        queries.put("TemplateCode", "SMS_154950909"); //您正在申请手机注册，验证码为：${code}，5分钟内有效！
        queries.put("TemplateParam", "{\"code\":\"" + code + "\"}");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        com.aliyun.teaopenapi.models.OpenApiRequest request = new com.aliyun.teaopenapi.models.OpenApiRequest()
                .setQuery(com.aliyun.openapiutil.Client.query(queries));
        client.callApi(params, request, runtime);
    }

}
