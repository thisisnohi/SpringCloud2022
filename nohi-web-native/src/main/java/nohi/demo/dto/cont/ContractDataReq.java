package nohi.demo.dto.cont;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
    private String ywlb;

    // 合同模板类型
    private String htmblx;

    // 签约序号
    private String qyxh;
    // 房屋序号
    private String fwxh;
    // 房源发布序号
    private String fbxh;
    // 合同编号
    @NotBlank(message = "合同编号不能为空")
    private String htbh;
    // 签约日期
    @NotBlank(message = "签约日期不能为空")
    private String qyrq;
    // 租赁起始日期
    @NotBlank(message = "租赁起始日期不能为空")
    private String zlqsrq;

    // 租赁终止日期
    @NotBlank(message = "租赁终止日期不能为空")
    private String zlzzrq;
    // 租金
    @NotNull(message = "租金不能为空")
    private BigDecimal zj;
    // 合同总租金
    @NotNull(message = "合同总租金不能为空")
    private BigDecimal rent;
    // 租金计费周期
    @NotBlank(message = "租金计费周期不能为空")
    private String zjjszq;
    // 押金
    private BigDecimal yj;

    // 用途
    private String yt;
    // 出租用途
    private String czyt;
    // 出租部位
    private String czbw;
    // 出租面积
    private BigDecimal czmj;
    // 出租方式
    private String czfs;
    // 实际居住人数
    private Integer ydjzry;
    // 最大居住人数
    private Integer zdjzrs;
    // 所在层
    private Integer szc;
    // 单元
    private String dy;
    // 室号/部位
    private String shbw;
    // 坐落
    private String zl;
    // 户型
    private String hx;
    // 建筑面积
    private BigDecimal jzmj;
    // 朝向
    private String cx;
    // 丘权号
    private String qqh;
    // 小区
    private String xq;
    // 区县
    private String qx;
    // 街道办
    private String jdb;
    // 路街巷
    private String ljx;
    // 院门牌号
    private String ymph;
    // 楼号
    private String lh;
    // 总层数
    private Integer zcs;
    // 建成年代
    private String jcnd;
    // 房源分类
    private String fyfl;
    // 关联合同号
    private String glhth;
    // 说明
    private String sm;

    // 20200605 变更字段
    /**
     * 是否租金托管
     * 1-是；0-否
     * 只有企业代理版合同此值才可能是1，当为1时，下面的账户即为租金托管账户
     */
    private String sfzjtg;
    // 甲方收款账号
    private String jfskzh;
    // 账号户名
    private String zhhm;
    // 开户行名称
    private String khhmc;
    // 办结时间
    private String bjsj;
    // 原合同号
    private String yhth;
    // 合同是否有效
    private String htsfyx;
    private String htzt;


    private List<ContractPartItem> ztb;
}
