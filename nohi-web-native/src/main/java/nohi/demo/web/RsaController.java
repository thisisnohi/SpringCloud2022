package nohi.demo.web;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.dto.rsa.RsaReqVo;
import nohi.demo.dto.rsa.RsaRespItemVO;
import nohi.demo.dto.rsa.RsaRespVO;
import nohi.demo.service.json.FastJsonService;
import nohi.demo.utils.RsaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;

/**
 * RSA 测试
 *
 * @author NOHI
 * @date 2022/9/3 13:20
 */
@RestController
@RequestMapping("rsa")
@Tag(name = "RSA")
@Slf4j
public class RsaController {

    private String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArvIOBK7f/DmH3N42f7wZWThULEXIXSePecT4adfHUnr56C0B/s1XFLZLd9jsKhety/1H1yQp+1zy+g2dH5P3OiLB0iQ7k6B1g3vbzCXtVtj0J7/e1S1PyW1H1Wy6pkiypdKfFZG7QCmeSNEwOhIStAZDsTiBbypSN7j0aNhT9/KPKVCNZzdLXeIrtL4WKTCoNaSMLd8esZIaEsI17mBhSs7CU52dEFXt+XIxq6HRzA0zavPlHkYDMt/8NC3VpFpSpaHS7UbO3VsRYWZAIEu2tzRZaysrXVruntH2W35rLedjBlgagrO95jeBJJfpJP4XYn7KUNwbL8kb/W1IGCyx7wIDAQAB";
    private String priKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCu8g4Ert/8OYfc3jZ/vBlZOFQsRchdJ495xPhp18dSevnoLQH+zVcUtkt32OwqF63L/UfXJCn7XPL6DZ0fk/c6IsHSJDuToHWDe9vMJe1W2PQnv97VLU/JbUfVbLqmSLKl0p8VkbtAKZ5I0TA6EhK0BkOxOIFvKlI3uPRo2FP38o8pUI1nN0td4iu0vhYpMKg1pIwt3x6xkhoSwjXuYGFKzsJTnZ0QVe35cjGrodHMDTNq8+UeRgMy3/w0LdWkWlKlodLtRs7dWxFhZkAgS7a3NFlrKytdWu6e0fZbfmst52MGWBqCs73mN4Ekl+kk/hdifspQ3BsvyRv9bUgYLLHvAgMBAAECggEAK2hJzCWr08RejpHgSd9dFsMgN3/1EBARzG/wBysP/MogmdgZB6Thd3Un0ovTRTps2BF3Ms15UTIl459fHgezrwwStVZiJeYJYl9oEQZUFllsczRrUM+WXW8tBKqewUWGdhiFem+XW3s4F+83nSEA3QQ05ZMUooWIsuTEWANdCkcOzn3uZv934pH/pWV5ZXg3KUN38uaLUVRLlnkTvFA3714mKqnGFZRbDeKD2YFv2FkMTbKaO0V3EtBbUZ52WAtqe6xqStykb0q6OSQLSDlH17wB/42ZZ195VYDsiKstuKyAAkAeobOe6rP7h2KWWbh4CSuKspJckP+MKHX/cP70eQKBgQDfCZQ0OshdBCgPrpeYDby/4jl07/DxBHOiXmvYzX8TKJR+py4RG8TI2wT+XYWqGsvgRKKb2BWgguyncDfSoMreGf5cp3GX6GQUjkh2FBa3R9vnCzd3Ssu2ozufIEWKKuvaOFJmRTzjmcqKghbjCLYal9wKsS8iSw4cE6tPfVrQPQKBgQDIzPZRAQQBUgfIb+1NBVb2LB0IX+F0WXM26LxoaQAYUHLCHC6MC1MfYnGk6Ftz8KeZGcWNANxb0aJYl2lEq7eXkpk+xg/qlnS0YGhDfIacBDcndbsrmGDkHoBo7t9LR87sVk8dzQ3N716F9HoZ3QMdZsB5SAhL2rtT6ywQ5YfhmwKBgASIeU6mczQQxY+sCMC6Zr+vpWNPDSKY6JDAQojhhFerV3lLhJAsdE9wxCU3WWQfr00bmAJ63dWpU44Dpd7ODdF9zcGsVY601nPkgGyF9AA9oaaMf9AY4RMRNkEBsugf/zMsOeCCP76QfaFbC4QJxUm9FGfTOhYEWIERlUXEUngxAoGAH3g31kQhe49+lsQr3ZlLorMRLcHRFKn8K4vYE0j5cdYR1igSh3Iy6cVE6EJeKtJ5gb4PPuT2pBE7r0Szg8ahtEiOac8iqXJBAg/l50W51vO2LSH/Xnq4HKhHGaruJd8wJ8vzBEmhszejibzh8fG4TkBPQ/KtEi5Kk4FU26UNYacCgYEAzm6zsCRDthE08bIYr5kJprsakZdcPpcjpGy4qBJBNQb4lpN36pxh0Cw97mL2hE4NdbQROghHtlCUnZizqxUvVnE4r67t2s+2PpzFWw69pYXjavjrKwYbZ7VjtsAp0qUaSs0Id5UIoaUaCr5iq7NLNhxfPvtyYIlM9ojN99AqjTk=";

    private static final int TMP_DATA_SIZE = 10;

    @Autowired
    private FastJsonService fastJsonService;

    @PostMapping(value = "/returnJson")
    @Operation(summary = "returnJson", description = "returnJson..")
    public String returnJson() {
        log.info("returnJson");
        return fastJsonService.toJson();
    }

    @PostMapping(value = "/testJson")
    @Operation(summary = "testJson", description = "Bean2Json 测试，RSA方法转换List to json异常..")
    public RsaRespVO testJson(@RequestBody RsaReqVo reqVo) {
        // 注册JSON reader writer
        fastJsonService.registerRsaRespItemVO();

        List<RsaRespItemVO> list = Lists.newArrayList();
        for (int i = 0; i < TMP_DATA_SIZE; i++) {
            RsaRespItemVO item = new RsaRespItemVO();
            item.setAcctNo("1");
            item.setAcctName("2");
            item.setAmt(new BigDecimal(i));
            item.setBalance(new BigDecimal(100 - i));
            item.setDateTime(LocalDateTime.now().toString());
            list.add(item);
        }
        System.out.println("list.size:" + list.size());

        RsaRespVO vo = new RsaRespVO();
        try {
            log.info("list:{}", list);
            log.info("item[0]:{}", JSONObject.toJSONString(list.get(0)));
        } catch (Exception e) {
            log.error("异常:{}", e.getMessage());
        }
        log.debug("===========2==============");
        try {
            String json = JSONObject.toJSONString(list);
            log.info("json2:{}", json);
            vo.setData(json);
        } catch (Exception e) {
            log.error("JSONObject.toJSONString异常:{}", e.getMessage());
        }
        return vo;
    }

    @PostMapping(value = "/encode")
    @Operation(method = "POST", summary = "测试RSA加密加签", description = "测试RSA加密加签,转换json异常，可先调用 testJson() 执行 fastJsonService.registerRsaRespItemVO(); ")
    public RsaRespVO encode(@RequestBody RsaReqVo reqVo) {
        log.info("rsa请求:{}", JSONObject.toJSONString(reqVo));

        long start = System.currentTimeMillis();
        String traceId = reqVo.getTraceId();
        if (StringUtils.isBlank(traceId)) {
            traceId = "NULL";
        }
        RsaRespVO vo = new RsaRespVO();

        try {
            PrivateKey privateKey = RsaUtils.loadPrivateKey(priKey);
            PublicKey publicKey = RsaUtils.loadPublicKey(pubKey);
            PublicKey locaPublicKey = RsaUtils.loadPublicKey(pubKey);
            // 加密
            String acctNo = reqVo.getAcctNo();
            String encryptStr = null;
            vo.setAcctNo(acctNo);
            // 对账号加签
            String json = acctNo;
            log.info("json:{}", json);
            encryptStr = RsaUtils.encryptData(json, publicKey);
            vo.setData(encryptStr);
            // RSA签
            String sign = RsaUtils.sign(encryptStr, privateKey);
            vo.setSign(sign);
            // 验签
            boolean result = RsaUtils.verify(encryptStr, locaPublicKey, sign);
            log.info("[{}]验签[{}]", traceId, result);
        } catch (Exception e) {
            log.error("[{}]异常:{}", traceId, e.getMessage(), e);
        }

        long end = System.currentTimeMillis();
        log.info("[{}]耗时[{}]", traceId, (end - start));
        return vo;
    }
}
