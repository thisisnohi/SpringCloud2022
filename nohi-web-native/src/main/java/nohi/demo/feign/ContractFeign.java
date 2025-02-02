package nohi.demo.feign;

import nohi.common.config.FeignConfiguration;
import nohi.demo.dto.rsa.RsaMesage;
import nohi.demo.dto.contquery.ContractQueryReq;
import nohi.demo.dto.contquery.ContractQueryResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 11:10
 **/
@FeignClient(name = "cont", configuration = FeignConfiguration.class)
public interface ContractFeign {
    /**
     * contDemo
     * @param req 请求
     * @return 返回
     */
    @RequestMapping(value = "/cont/contDemo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ContractQueryResp contDemo(@RequestBody ContractQueryReq req);

    /**
     * rental_zjtg_gethtinfo
     * @param req 请求
     * @return 返回
     */
    @RequestMapping(value = "/bitsADI/rental_zjtg_gethtinfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ContractQueryResp contDemo2(@RequestBody ContractQueryReq req);
    /**
     * rental_zjtg_gethtinfo
     * @param req 请求
     * @return 返回
     */
    @RequestMapping(value = "/bitsADI/rental_zjtg_gethtinfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RsaMesage contDemo3(@RequestBody RsaMesage req);
    /**
     * rental_zjtg_gethtinfo
     * @param req 请求
     * @return 返回
     */
    @RequestMapping(value = "/bitsADI/rental_zjtg_gethtinfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    RsaMesage contDemo5(@RequestBody Object req);
    /**
     * rental_zjtg_gethtinfo
     * @return 返回
     * @param req 请求
     */
    @RequestMapping(value = "/bitsADI/rental_zjtg_gethtinfo", method = RequestMethod.GET)
    String contDemo7(@RequestParam("requestJSON") String req);
    /**
     * contDemoStr
     * @param req 请求
     * @return 返回
     */
    @RequestMapping(value = "/cont/contDemoStr", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String contDemo(@RequestBody Object req);
}
