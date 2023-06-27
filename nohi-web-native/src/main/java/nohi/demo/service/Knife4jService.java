package nohi.demo.service;

import com.alibaba.fastjson2.JSONObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.dto.hello.HelloReq;
import nohi.demo.dto.hello.HelloResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>service</p>
 * @date 2023/06/27 12:23
 **/
@Slf4j
@Service
public class Knife4jService {
    @Autowired
    private Validator validator;

    @Validated
    public HelloResp sayHello3(@Valid HelloReq req) {
        log.info("请求对象:{}", JSONObject.toJSONString(req));
        /** 校验 **/
        Set<ConstraintViolation<HelloReq>> violations = validator.validate(req);

        /** 构建返回对象 **/
        HelloResp resp = new HelloResp();
        BeanUtils.copyProperties(req, resp);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<HelloReq> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            resp.setRetCode("ERROR");
            resp.setRetMsg("响应信息..." + sb.toString());
            return resp;
        }
        resp.setRetCode("SUC");
        resp.setRetMsg("响应信息..." + req.getName());
        return resp;
    }
}
