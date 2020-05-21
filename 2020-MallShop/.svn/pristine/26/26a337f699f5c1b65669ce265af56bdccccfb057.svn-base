package com.epro.mall.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * 1.0 订单串 - AlipayGlobal 境外收单支付 本地签名逻辑
 * 注意：本 Demo 仅作为展示用途，实际项目中不能将 RSA_PRIVATE 和签名逻辑放在客户端进行！
 * New Cross-border APP Payment ： https://global-pre.site.alipay.net/service/app_split/1
 */
public final class OrderUtils1_0_Global {

    // 商户PID
    //public static final String PID = "2088621891276675"; //sandbox env
    public static final String PID = "2088621924723642"; //sandbox env
    //public static final String PID = "2088021017666931"; //pro env

    // 商户私钥
    // saondbox env
    //public static final String RSA_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC8gZtwHZUoP7XCh4JpdqmpyNStUhVs/u3527LcBVLwHLbizlyN8GOOuMBYvgQdMXfvmhqk6BZI8z6Os/JYDjFnp3cBf0NbKb12w/3GNGaIWF0LKSCwmyUVnVyR2XAOU5WR9PhU7Gc7dAJB9YnFXSJfbLu3ljC30mdaI7oyDWtFJXTIR1sNZycNEJIiKAIvzF14UlfGIFTtIwOmE5UxtT/HWwTS8twe7rTylwnxrbJ3BY4Ekz4hbkt6gSFC0wsG0SigK0dF2Vx1Muo49Ben3q04n6feb7RRzMZj6kVJHP/nnH4t9uKHckH3uXMOUH20kLs1CYEgo/OLZ5iVzYwUCIjBAgMBAAECggEBAJxt7YCHfAyvefTZIPbF2xujJiJCWLdfgjpy/6Un4eCR446pcxLyppt8Y1oBLUbQk1fJCU1Jr5yQBpzDZVt/Q65hwRcD9fBD3g8dq418VI3WzjeBxLfpt0eNk5EQ4LVy1U8O+7j1iOFEbs8vpEmc9PY9NDWwM/OHbe7WcSAyFI+GjcnGUAz8Pw/hCm10BnEUy9MrIOfyQTIT/DCy/aN78L1sSSlCnj3nBIiPRVdm7uSKlnFB/iYnvdJ9qOcclC80/M3mL3zuLUJmMepz8BujzFhui+6e2+CQ1R1gr8DVmrmCnRpuS7SX2wxbfD9Igr+8PIcZh3Vxk9hG/7X8XZU0xZUCgYEA5UeotTLBy48RgA8oG6USb10AqfDBSexjaPATztEhzwxKWS7CXlZHomfZcJnBdI2vkR4nApaWbujXi68F7v+bogIoucM+m9YisJqd3QCKWDUeXnYya6ryYDkHng5CGNea4SUZ+It1yOHqpG8R3GmzwfGgCJwOYiOrVIxQg0sYT8sCgYEA0nmBtrHpzeUXsUJieV9aa/7m63p18owsnd3BTx0oL6qXAYVz269U3AZ/fjBzqoD+iIQKaSd8DFOzFhM1S/xlWJYUE2aR8viplC1JAQaOXfJj5epZ/sjh+ED9wHoNUFj04xPG6q1CvR8sMKU873CcMTYXJO7BKOp5Lw1yYRks4CMCgYARRaGu7MDunbt6wymJU5kc7IulQa696+HdQOCtHtIGL+3V5BDeXG+PC3W05tBNAHNuYjAcIM36Uu4R5sByrazCupjozY2E3c/FMBCeWnERjgbYpNzNgZY6q3EodfiCuJNBTLM1JwRberFba8aTrR7OiG4+18q5l+hLOTrDeT++mwKBgQDAvzd0CNA1lxQwZ7vorNMQzZnJkhdLrOT7GWZ7PhdcN5wcb4tfZtyOtoCxHkv+nCz89fbWv6sRWuFJWQPY92YUTzRJZAhKe0BJC4OspcVg8aZcmlYaRvrxrwry5pMkvqJdBfKHXW4d5dtBHUGFgRn40zE0yFUqVDfwSB80HqsDAwKBgQCHGfNqoilQeYfSX9hXgN0hTMidEX2znNbZ1Pc64lx1uXRTGppX95Qt6mrTbUo7zkoXkMlgWVMHG3NJerjyUMHYzB0OF6aDrn75SaF6p5j1rHX4/FXJHBvFxD6qB+IGASIbrsiGkB6p8t+Q6O8rS7MRjLbkirLHrWF/eSbZV/0ivg==";
     public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALIopmwQ/Y5RWoIH4ByLgbp3QhafOqL1LTHJSvaWh3LQYx8FfKMEyuNR9DNR3YPFAV0BHnz6eVOvVBVkcJJHL0vw/Ezr+Chk6BUJ4PWN5Q1wtu5WJm4Ns2lX4AiHsWa7RIVhYNHQOdYJgPoARmOTikTYi6Gf8NtGnrY731X/bS/7AgMBAAECgYBJkm/ngLy04PO3bvkl0JaqIkWwgeD9GrKnNLE4U8FrsJK+ZM2rpHcEmPt3lf8cQ2bkuWmVwvoHjQf0LZ4vSiZFTThrQ48UWupGJVakwbNb79LRQRpWBF50YfXryS6hwU051oaegNF+HmSNUr5FSQMb9zvwdg3MuQIRTJG7HOe0QQJBAOcNuTGohG0QqGmYkCUK2hcpQKTK3pg8erN/bnVRjETjlXlwZ9iosA9d9GOt/Y6v7HDoEih0SyB03l7GVjiGIeUCQQDFZOya9eTM8rtnom0zWc6GIuT46OzGd66LKBiHarjwrM6TGx8OfuvcmWoJJrz5MhQM6JOcIOW+9SdBygAEUmxfAkA0LK4oH4Sf2EYfajsKklPDLSaSz9jKCcYvaFQ6G8sP91BWluJsU1NGdRHtvGloYiODL3SCPBiG4L0iK6lmpCtdAkEAtZHHHicPBgJEpeg/YFu8X6AbVGYU3d5a3D/OiEl/wkqCXcg1wgS3ehxX8AEFFIee79zDYnrUOdmQ+bAQYelbKwJAaJByy3AoDc/EdaVQky029CfG+OX0zXGr7pB6EtIywyuusUV3m/8S/QTIRk5DEnjwiEzYwRWii2DhOqe+6iRDgg==";
    //online env
    //public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMg9WztcdrttAbB3FQ5gwfuDAwLgwjFcfD8vV+IH6jC8nRi0jz7SHOD7pQ4MBc+Q4PpQWAqjDLTiWNYmAuUVzHni2iylCnpnun3YwXODpJ6oq0QHJaIdk8g8QKd0vMjPBfqXdXQRncscFGaZxsstrRtfRKqLXuyy8sM+AHpwcHfVAgMBAAECgYAWrtym3NKWaMLIOrUn684Nr8mnic51yQRKJBLQiBT/cF5bbhjMBQFAe0E3ViVSXhceQ/u1OMM3umxV1fh9+vouoRYcWW+DeKt54tfFo+SdyOFdtlXdqA7vstQ+Nmd0JwH4uLGaF1WQGO7kPdiAg1N9ZBpQB9CSJJM0kNDstp18AQJBAPNaeiPHxyZ72bwVxbBVthj0hMWE4Vr83W0bP2yuY7Vzyp0BOsPYHNbFK92fnDJEAjPdHVj+YVl/Sc23sA21UisCQQDSpU2OF8W7Nkbtt4ZAMikzDbEym6ZZU+QgqlYCV/ZBor+cTg8QivEUyum+eOUx5KjoYVcN8U3uO29ecJmS7l3/AkEAmLAMMqcGrX7H/tsqTpl4x++j3sqhGxXNWMff47EHnrIoTpqW5IqUjazo+QVMW72QJDp4T35MVnsnM4wtSmyaQQJAEwPRCG6k7s1rgbH7cHgWuAEYadUbIx0rjrdRpEyEclBas6VoProMITBgAU2wgtx9UtzWmu+ZdVPwLbpEYrsZyQJBAJen1uHVmL3j4WSyI5OhNSHW5aAfeLGhbuEEYsDdNYewQ+OUz0ZtWZ2Ta+742yNw130nlckhapbHhtPdHUSayjw=";

    // 商户收款账号
   // public static final String SELLER = "2088621891276675";
    public static final String SELLER = "2088621924723642";

    public static String getSignedOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PID + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        //orderInfo += "&out_trade_no=\"201810100001\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情

        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品详情
        orderInfo += "&currency=\"USD\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";
        //orderInfo += "&rmb_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://orderPay/alipay/notify_url" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        orderInfo += "&appenv=\"system=Android^version=3.0.1.2\"";

        //orderInfo += "&secondary_merchant_id=\"A80001\"";

        //orderInfo += "&secondary_merchant_name=\"Muku\"";

        //orderInfo += "&secondary_merchant_industry=\"7011\"";


        //

        orderInfo += "&forex_biz=\"FP\"";
        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        //orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"http://orderPay/alipay/return_url\"";

        orderInfo += "&product_code=\"NEW_WAP_OVERSEAS_SELLER\"";


        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对 sign 做 URL 编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String signedOrderInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        return signedOrderInfo;
    }


    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public static String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE, false);
    }


    /**
     * get the sign type we use. 获取签名方式
     *
     */
    private static String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
