package nohi.demo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.dto.RsaMesage;
import nohi.demo.dto.cont.ContractDataReq;
import nohi.demo.dto.cont.ContractDataResp;
import nohi.demo.dto.contquery.*;
import nohi.demo.feign.ContractFeign;
import nohi.demo.utils.JsonUtils;
import nohi.demo.utils.RSAUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * ContractController
 * @author NOHI
 * @date 2022/9/14 15:32
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/cont")
@Slf4j
public class ContractController {
    private final static String DEFAULT_CHARSET = "UTF-8";
    private static final String DATA_PATTERN = "yyyy-MM-dd";

    @Autowired
    private feign.Logger logger;

    @PostMapping(value = {"/save4"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save4(@RequestBody String req) throws UnsupportedEncodingException {
        log.info("合同数据保存.REQ:{}", req);
        req = URLDecoder.decode(req, DEFAULT_CHARSET);
        log.info("合同数据保存.REQ:{}", req);
        return null;
    }

    @PostMapping(value = {"/save3"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save3(RsaMesage req) {
        log.info("合同数据保存.REQ:{}", req);
        return null;
    }

    @PostMapping(value = {"/save"})
    public RsaMesage save(@RequestBody RsaMesage reqMsg) {
        log.info("合同数据保存.REQ:{}", reqMsg);

        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+yLyxg2xb6LkkWnWZF+XXjkZN0Fhne1eJTA3EmOlMyD5fhOfs9Ri0qwWqRW9s4m4k4tgJX5tcGRwCd7x6PEyJXr3/YgcR81zFqMjsnCYdB24Ii3bLdGhcokwJ/cW2yTWwa7ujpjemEROv5JasFJwXKdJVz+uSJDYmOsylL1MvNwIDAQAB";
        String localPriKeyStr;
        String localPubStr;

        // 20200709 本地密钥
        localPriKeyStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDIA7ltUuLQ2olV7UoJe8gDKID/n1Zu1JE8rIZVhXFhfgb3R4vhN9QV30ZqNZr9Eq++oFGG457zmYC4NfJx1ne3javckYK7tR/N2ldlUkKdcXFDI34gBd97OYeOxvWnAxdo2H0Adujz6HPdsc3l47xPZLsPNhlMH55I8CyaJWqYcPtfPjOYSUHxjlJLIocwwusvt5PoCbo1xgH3KiLPkR0QhXc/8VvbRropY7gQcx7hjKhd92YrkrrMp6L3Iq8uE0ov5NuRqCoe2hswRubfsYjJHh9pCdvfz9TNqEaAPsTC6YOcg4Tn7MWOl/el1G8mxtzpGRniL9wC/WixHMGjAguPAgMBAAECggEAQSGdVzE/dN1FDXsYud/Z8NVWtGbRgYOsy8IueFjc4+9jG1DsBunkIT80sc9E6hYUrpGMGs2ybAbXeKTwjwtUrqvuhAPKO4+ujJ0GCpNv588SH9fF8W2YKYtBTkgRpFIIZHbita0DDLHsUQveTXcMUhouI7TtJfHB3GSBUazPelX0eWcGpkcaB/jCtqblfflWkp+AMsWP5jb3DMH1oUOyiOPJxjsbqdzCl9fSFODP9b1GEJELj9guYTfqnbgAFt57jGidz+nFs1UjGvEdLtFfboqXJ2sFkJvHscUHpusuRHs82iSeECsCPsqMYYuxCdcY2oUXImSjRmFPbUzrz5YIKQKBgQDyso8lpvrwy/jypf+IHwctlJXNasHTDVgsoJNe/MRbdKZOERfi4pUHrGlCfxdp5WF4FjM0JneWmMJ5mUfnRk7IDBVbvyBM7GEulLaLAos73URh2B0fb+AvhJ0f63gBWeJSZG2TbL56q6gKSfjygiC1GwuyKz9y7nLO6HzewOXdZQKBgQDS+kEBYSUMUI32zDw+3SiF4u/vdqBKiGuQIF5I2lpqcO9uWZRd3I8k6GgjAU2jUh1xooAeSdIGZo+34TlYXvlSA7VGKi0b+zjfv1IHGmepeBRzd/i7YtUq4v/htusMTNUIxuy8UaB8jRADek0y8n/bX3oN4pUIAsryqhdiz6mf4wKBgFgk1ax9GsJ9siqec1ICwt6hCk8SqNZ4EEAVCbED6GS21vefaadzV2D0Ez6dXemN73pnxaz5E5HUZT04mE680DwDd3Zc0eReLWV3iVyvTdYuJHnMvbb/MNLRPSeso2cKOBJoewuCASQYV+10tD+PV0WvTu4kmuSpCXVwJnnYYSOpAoGAc5Ktm5DY17clHaZh9ln2gemAYKCOTGYQ7mIc5DHhR9/X+Y5O7WAOdjxuF4b2PdqU0JQhNPZhfCog3q0dWeClGZ14Zxhhn6tuS3ul9sgrMzXZliuJcTVB6v1xCPfIKPPJ28YFF2Br1u77b7xGn/shuU3DMeoGk93byTUOHI3/sNsCgYEApKVpGs0QOABXV4BJs0gst12/cqxp3loBEmg6xZCl534cGr6uaWkUuzEz8ArxnxjrZBvg4yvVh3a+Penqa/NcvgmxGNCf1Vs0RKx4iwZWscT2ft3FEGgEmVBembsrZGN3XYeOz7w+l1x9ufxtB2+D4noTELcnD6Gwe6kDNBB9PSM=";
        localPubStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyAO5bVLi0NqJVe1KCXvIAyiA/59WbtSRPKyGVYVxYX4G90eL4TfUFd9GajWa/RKvvqBRhuOe85mAuDXycdZ3t42r3JGCu7UfzdpXZVJCnXFxQyN+IAXfezmHjsb1pwMXaNh9AHbo8+hz3bHN5eO8T2S7DzYZTB+eSPAsmiVqmHD7Xz4zmElB8Y5SSyKHMMLrL7eT6Am6NcYB9yoiz5EdEIV3P/Fb20a6KWO4EHMe4YyoXfdmK5K6zKei9yKvLhNKL+TbkagqHtobMEbm37GIyR4faQnb38/UzahGgD7EwumDnIOE5+zFjpf3pdRvJsbc6RkZ4i/cAv1osRzBowILjwIDAQAB";


        PrivateKey privateKey = null;
        PublicKey publicKey = null;
        PublicKey locaPublicKey = null;

        RsaMesage respMsg = new RsaMesage();
        ContractDataResp resp = new ContractDataResp();
        try {
            privateKey = RSAUtils.loadPrivateKey(localPriKeyStr);
            publicKey = RSAUtils.loadPublicKey(pubKey);
            locaPublicKey = RSAUtils.loadPublicKey(localPubStr);

            ObjectMapper objectMapper = new ObjectMapper();
            RsaMesage resMsg = reqMsg;
            // 解密
            String respMsgStr = RSAUtils.decryptData(resMsg.getData(), privateKey);
            log.info("解密:{}", respMsgStr);

            boolean singRs = RSAUtils.verify2(resMsg.getData(), publicKey, resMsg.getSign());
            log.info("验签:{}", singRs);

            ContractDataReq req;
            req = objectMapper.readValue(respMsgStr, ContractDataReq.class);
            resp.setFwxh(req.getFwxh());
            resp.setQyxh(req.getQyxh());
            resp.setJszt("SUC");
            StringBuffer msg = new StringBuffer();
            msg.append(this.validateStr(req.getYwlb(), "业务类别"));
            msg.append(this.validateStr(req.getHtbh(), "合同编号"));
            msg.append(this.validateDate(req.getQyrq(), DATA_PATTERN, "签约日期"));
            msg.append(this.validateDate(req.getZlqsrq(), DATA_PATTERN, "租赁起始日期"));
            msg.append(this.validateDate(req.getZlzzrq(), DATA_PATTERN, "租赁终止日期"));
            msg.append(this.validateAmt(req.getZj(), "月租金"));
            msg.append(this.validateAmt(req.getRent(), "合同总金额"));
            msg.append(this.validateStr(req.getZjjszq(), "租金计费周期"));

            if (!StringUtils.isBlank(msg.toString().trim())) {
                resp.setJszt("ERROR");
                resp.setMsg(msg.toString().trim());
            }
            log.info("合同数据保存.RESP:{}", JsonUtils.toString(resp));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resp.setJszt("ERROR");
            resp.setMsg(e.getMessage());
        }
        try {
            // 返回数据加密、加签
            String data = JsonUtils.toString(resp);
            log.info("返回数据:{}", data);
            String encryptStr = RSAUtils.encryptData(data, publicKey);
            log.info("返回数据加密:{}", encryptStr);

            // RSA签名
            String sign = RSAUtils.sign2(encryptStr, privateKey);
            log.info("返回数据加签:{}", sign);

            // 验签
            boolean result = RSAUtils.verify2(encryptStr, locaPublicKey, sign);
            log.info("验签结果1:" + result);

            // 加密数据
            respMsg.setData(encryptStr);
            respMsg.setSign(sign);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return respMsg;
    }

    @PostMapping(value = {"/save2"})
    public ContractDataResp save(@RequestBody ContractDataReq req) {
        log.info("合同数据保存.REQ:{}", JsonUtils.toString(req));
        ContractDataResp resp = new ContractDataResp();
        resp.setFwxh(req.getFwxh());
        resp.setQyxh(req.getQyxh());
        resp.setJszt("SUC");
        StringBuilder msg = new StringBuilder();
        msg.append(this.validateStr(req.getYwlb(), "业务类别"));
        msg.append(this.validateStr(req.getHtbh(), "合同编号"));
        msg.append(this.validateDate(req.getQyrq(), DATA_PATTERN, "签约日期"));
        msg.append(this.validateDate(req.getZlqsrq(), DATA_PATTERN, "租赁起始日期"));
        msg.append(this.validateDate(req.getZlzzrq(), DATA_PATTERN, "租赁终止日期"));
        msg.append(this.validateAmt(req.getZj(), "月租金"));
        msg.append(this.validateAmt(req.getRent(), "合同总金额"));
        msg.append(this.validateStr(req.getZjjszq(), "租金计费周期"));


        if (!StringUtils.isBlank(msg.toString().trim())) {
            resp.setJszt("ERROR");
            resp.setMsg(msg.toString().trim());
        }
        log.info("合同数据保存.RESP:{}", JsonUtils.toString(resp));


        return resp;
    }

    public java.lang.String validateAmt(BigDecimal value, String msg) {
        if (null == value) {
            return " " + msg + "不能为空";
        } else if (value.compareTo(BigDecimal.ZERO) <= 0) {
            return " " + msg + "不能小于0";
        }
        return "";
    }

    public java.lang.String validateStr(String value, String msg) {
        if (StringUtils.isBlank(value)) {
            return " " + msg + "不能为空";
        }
        return "";
    }

    public java.lang.String validateDate(String value, String pattern, String msg) {
        if (StringUtils.isBlank(value)) {
            return " " + msg + "不能为空";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            sdf.parse(value);
        } catch (ParseException e) {
            return " " + msg + value + "格式不正确[" + pattern + "]";
        }
        return "";
    }

    @PostMapping(value = {"/contDemo"})
    public ContractQueryResp contDemo(@RequestBody ContractQueryReq req) {
        log.info("模拟合同查询.REQ:{}", JsonUtils.toString(req));
        ContractQueryResp resp = new ContractQueryResp();
        ContractQueryRespBody respBody = new ContractQueryRespBody();
        resp.setBody(respBody);
        ContractQueryResult result = new ContractQueryResult();
        respBody.setResult(result);
        result.setHtbh(req.getBody().getHtbh());
        respBody.setJszt("OK");
        respBody.setZtxx("OK");
        log.info("模拟合同查询.REQ:{}", JsonUtils.toString(resp));
        return resp;
    }

    @PostMapping(value = {"/contDemoStr"})
    public String contDemoStr(@RequestBody String req) throws UnsupportedEncodingException {
        log.info("模拟合同查询.REQ:{}", req);
        req = URLDecoder.decode(req, DEFAULT_CHARSET);
        log.info("模拟合同查询.REQ.decode:{}", req);
        return req;
    }


    @PostMapping(value = {"/query"})
    public ContractQueryResp query(@RequestBody ContractQueryReq req) {
        ContractQueryResp resp = null;
        log.info("合同查询.REQ:{}", JsonUtils.toString(req));
        log.debug("======debug11========");
        ContractFeign feign = Feign.builder()
                .client(new Client.Default(null, null))
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .retryer(Retryer.NEVER_RETRY)
                .contract(new SpringMvcContract())
                .logger(logger)
                .logLevel(Logger.Level.FULL)
                .target(ContractFeign.class, "http://curbwv.natappfree.cc/")
//                .target(ContractFeign.class, "http://127.0.0.1:8099")
                ;
        Date date = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

        ContractQueryHead head = req.getHead();
        head.setTransSeqNo(UUID.randomUUID().toString().replaceAll("-", ""));
        head.setRequestDate(sdfDate.format(date));
        head.setRequestTime(sdfTime.format(date));
        head.setSystemId("SYSID");

        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+yLyxg2xb6LkkWnWZF+XXjkZN0Fhne1eJTA3EmOlMyD5fhOfs9Ri0qwWqRW9s4m4k4tgJX5tcGRwCd7x6PEyJXr3/YgcR81zFqMjsnCYdB24Ii3bLdGhcokwJ/cW2yTWwa7ujpjemEROv5JasFJwXKdJVz+uSJDYmOsylL1MvNwIDAQAB";
        String priKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALdpZLBuQeoVgYl6hWipnR9i2PtOjpBlBn9PughATgAURoju3i9ExgE/UFx2beAh/xBimQ8/xCPUynbW2t8BwM9VPv2gSJ7tp4b21VgVaK8cdTwTMkVarhtpOgnz8xgEoCmF3g/xmrm0gZuNFwXdtDQztB4zwR4EOUu0lyg8SgEXAgEDAoGAHpGQyBJgUa5AQZRrkXGaL+XO1I0XwruBFTf0VrViVVi2bCfPsot2VYqNZL5npVr/2BBu19/2BfjMaSPPJSr1d5sjTQxOFZ9c50JSQsKCygvlkNLmw+c59U93gwkaelCWhqoj4cVMeEwlVhXH0ukBROzcYTEy+YomQDbcMsxNuD8CQQDoQm0ctEjSbTSXHGXBE27u88cOuyhVnEB4hOQG3CqB2VGIFKKMdXJw5wvyqAuujRew/Rnk2s1XYRogqJopGFf9AkEAyijCOb/UXwzm0cthxUSD5iAQEw6FmbYbE+gTtngPnz+wAPIixVp1erqLF8AIuR9+9Yy9J7R3b/KwSsVdSV9XowJBAJrW82h4MIxIzboS7oC3n0n32gnSGuO9gFBYmASSxwE7i7ANwbL49vXvXUxwB8mzZSCou+3nM4+WEWsbEXC65VMCQQCGxdbRKo2Us0SL3OvY2FfuwAq3XwO7zry38A0kUApqKnVV9sHY5vj8fFy6gAXQv6n5CH4aeE+f9yAx2OjblOUXAkAY8fMi78MLIOaUIcktWJDp3ENGQe+hkjXTIKsiDl4sqs7c5dCOZi1cCgX+I8VPSEQXcNm1+QcX6BX53+YRxW2R";
        String localPub = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQC3aWSwbkHqFYGJeoVoqZ0fYtj7To6QZQZ/T7oIQE4AFEaI7t4vRMYBP1Bcdm3gIf8QYpkPP8Qj1Mp21trfAcDPVT79oEie7aeG9tVYFWivHHU8EzJFWq4baToJ8/MYBKAphd4P8Zq5tIGbjRcF3bQ0M7QeM8EeBDlLtJcoPEoBFwIBAw==";

        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(priKey);
            PublicKey publicKey = RSAUtils.loadPublicKey(pubKey);
            PublicKey locaPublicKey = RSAUtils.loadPublicKey(localPub);

            log.info("pubKey:{}", pubKey);
            log.info("localPub:{}", localPub);

            String data = JsonUtils.toString(req);
            log.info("请求:{}", data);
            String encryptStr = RSAUtils.encryptData(data, publicKey);
            log.info("加密:{}", encryptStr);

            // RSA签名
            String sign = RSAUtils.sign2(encryptStr, privateKey);
            log.info("加签:{}", sign);

            // 验签
            boolean result = RSAUtils.verify2(encryptStr, locaPublicKey, sign);
            log.info("验签结果1:" + result);

            // 加密数据
            RsaMesage rsaMesage = new RsaMesage();
            rsaMesage.setData(encryptStr);
            rsaMesage.setSign(sign);
            data = JsonUtils.toString(rsaMesage);
            log.info("data:" + data);
            rsaMesage = feign.contDemo5(rsaMesage);
            log.info("返回:{}", rsaMesage);
            // 解密
            String respMsg = RSAUtils.decryptData(rsaMesage.getData(), privateKey);
            log.info("返回解密:{}", respMsg);

            ObjectMapper objectMapper = new ObjectMapper();
            resp = objectMapper.readValue(respMsg, ContractQueryResp.class);
            log.info("contract:{}", objectMapper.writeValueAsString(resp));

            boolean singRs = RSAUtils.verify2(rsaMesage.getData(), publicKey, rsaMesage.getSign());
            log.info("singRs:{}", singRs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("合同查询.RESP:{}", JsonUtils.toString(resp));
        return resp;
    }
}
