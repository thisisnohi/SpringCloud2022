package nohi.web.dto.cont;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author NOHI
 * @program: nohi-web
 * @description:
 * @create 2020-06-02 10:29
 **/
@Data
public class ContractDataReq {
    @NotBlank(message = "业务类别不能为空")
    private String ywlb; // 业务类别
    private String htmblx; // 合同模板类型
    private String qyxh; // 签约序号
    private String fwxh; // 房屋序号
    private String fbxh; // 房源发布序号
    @NotBlank(message = "合同编号不能为空")
    private String htbh; // 合同编号
    @NotBlank(message = "签约日期不能为空")
    private String qyrq; // 签约日期
    @NotBlank(message = "租赁起始日期不能为空")
    private String zlqsrq; // 租赁起始日期
    @NotBlank(message = "租赁终止日期不能为空")
    private String zlzzrq; // 租赁终止日期
    @NotNull(message = "租金不能为空")
    private BigDecimal zj; // 租金
    @NotNull(message = "合同总租金不能为空")
    private BigDecimal rent; // 合同总租金
    @NotBlank(message = "租金计费周期不能为空")
    private String zjjszq; // 租金计费周期
    private BigDecimal yj; // 押金

    private String yt; // 用途
    private String czyt; // 出租用途
    private String czbw; // 出租部位
    private BigDecimal czmj; // 出租面积
    private String czfs; // 出租方式
    private Integer ydjzry; // 实际居住人数
    private Integer zdjzrs; // 最大居住人数
    private Integer szc; // 所在层
    private String dy; // 单元
    private String shbw; // 室号/部位
    private String zl; // 坐落
    private String hx; // 户型
    private BigDecimal jzmj; // 建筑面积
    private String cx; // 朝向
    private String qqh; // 丘权号
    private String xq; // 小区
    private String qx; // 区县
    private String jdb; // 街道办
    private String ljx; // 路街巷
    private String ymph; // 院门牌号
    private String lh; // 楼号
    private Integer zcs; // 总层数
    private String jcnd; // 建成年代
    private String fyfl; // 房源分类
    private String glhth; // 关联合同号
    private String sm; // 说明

    // 20200605 变更字段
    /**
     * 1-是；0-否
     * 只有企业代理版合同此值才可能是1，当为1时，下面的账户即为租金托管账户
     */
    private String sfzjtg; // 是否租金托管
    private String jfskzh; // 甲方收款账号
    private String zhhm; // 账号户名
    private String khhmc; // 开户行名称
    private String bjsj; // 办结时间
    private String yhth; // 原合同号

    private String htsfyx; // 合同是否有效
    private String htzt;

    private List<ContractPartItem> ztb;
}
