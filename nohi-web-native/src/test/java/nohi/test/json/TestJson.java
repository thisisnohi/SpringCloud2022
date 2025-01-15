package nohi.test.json;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.dto.rsa.RsaRespItemVO;
import nohi.demo.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>json</p>
 * @date 2023/06/27 13:34
 **/
@Slf4j
public class TestJson {
    private int TMP_DATA_SIZE = 10;

    @Test
    public void testJson() {

        List<RsaRespItemVO> list = Lists.newArrayList();
        for (int i = 0; i < TMP_DATA_SIZE; i++) {
            RsaRespItemVO item = new RsaRespItemVO();
            item.setAcctNo("");
            item.setAcctName("");
            item.setAmt(new BigDecimal(i));
            item.setBalance(new BigDecimal(100 - i));
            item.setDateTime(LocalDateTime.now().toString());
            list.add(item);
        }

        System.out.println("list:" + list);
        String json = JsonUtils.toString(list);
        System.out.println("json1:" + json);
        log.debug("JSON1:{}", json);
        json = JSONObject.toJSONString(list);
        System.out.println("json2:" + json);
        log.debug("JSON2:{}", json);
    }
}
