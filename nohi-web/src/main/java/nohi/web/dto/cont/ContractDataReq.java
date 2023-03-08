package nohi.web.dto.cont;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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
@ApiModel( description = "合同数据保存")
public class ContractDataReq {
    @NotBlank(message = "业务类别不能为空")
    @ApiModelProperty( value = "业务类别")
    private String ywlb; // 业务类别
    @ApiModelProperty( value = "合同模板类型")
    private String htmblx; // 合同模板类型
    @ApiModelProperty( value = "签约序号")
    private String qyxh; // 签约序号
    @ApiModelProperty( value = "房屋序号")
    private String fwxh; // 房屋序号
    @ApiModelProperty( value = "房源发布序号")
    private String fbxh; // 房源发布序号
    @NotBlank(message = "合同编号不能为空")
    @ApiModelProperty( value = "合同编号")
    private String htbh; // 合同编号
    @NotBlank(message = "签约日期不能为空")
    @ApiModelProperty( value = "签约日期")
    private String qyrq; // 签约日期
    @NotBlank(message = "租赁起始日期不能为空")
    @ApiModelProperty( value = "租赁起始日期")
    private String zlqsrq; // 租赁起始日期
    @NotBlank(message = "租赁终止日期不能为空")
    @ApiModelProperty( value = "租赁终止日期")
    private String zlzzrq; // 租赁终止日期
    @NotNull(message = "租金不能为空")
    @ApiModelProperty( value = "租金")
    private BigDecimal zj; // 租金
    @NotNull(message = "合同总租金不能为空")
    @ApiModelProperty( value = "合同总租金")
    private BigDecimal rent; // 合同总租金
    @NotBlank(message = "租金计费周期不能为空")
    @ApiModelProperty( value = "租金计费周期")
    private String zjjszq; // 租金计费周期
    @ApiModelProperty( value = "押金")
    private BigDecimal yj; // 押金

    @ApiModelProperty( value = "用途")
    private String yt; // 用途
    @ApiModelProperty( value = "出租用途")
    private String czyt; // 出租用途
    @ApiModelProperty( value = "出租部位")
    private String czbw; // 出租部位
    @ApiModelProperty( value = "出租面积")
    private BigDecimal czmj; // 出租面积
    @ApiModelProperty( value = "出租方式")
    private String czfs; // 出租方式
    @ApiModelProperty( value = "实际居住人数")
    private Integer ydjzry; // 实际居住人数
    @ApiModelProperty( value = "最大居住人数")
    private Integer zdjzrs; // 最大居住人数
    @ApiModelProperty( value = "所在层")
    private Integer szc; // 所在层
    @ApiModelProperty( value = "单元")
    private String dy; // 单元
    @ApiModelProperty( value = "室号/部位")
    private String shbw; // 室号/部位
    @ApiModelProperty( value = "坐落")
    private String zl; // 坐落
    @ApiModelProperty( value = "户型")
    private String hx; // 户型
    @ApiModelProperty( value = "建筑面积")
    private BigDecimal jzmj; // 建筑面积
    @ApiModelProperty( value = "朝向")
    private String cx; // 朝向
    @ApiModelProperty( value = "丘权号")
    private String qqh; // 丘权号
    @ApiModelProperty( value = "小区")
    private String xq; // 小区
    @ApiModelProperty( value = "区县")
    private String qx; // 区县
    @ApiModelProperty( value = "街道办")
    private String jdb; // 街道办
    @ApiModelProperty( value = "路街巷")
    private String ljx; // 路街巷
    @ApiModelProperty( value = "院门牌号")
    private String ymph; // 院门牌号
    @ApiModelProperty( value = "楼号")
    private String lh; // 楼号
    @ApiModelProperty( value = "总层数")
    private Integer zcs; // 总层数
    @ApiModelProperty( value = "建成年代")
    private String jcnd; // 建成年代
    @ApiModelProperty( value = "房源分类")
    private String fyfl; // 房源分类
    @ApiModelProperty( value = "关联合同号")
    private String glhth; // 关联合同号
    @ApiModelProperty( value = "说明")
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

    @ApiModelProperty(value = "主体")
    private List<ContractPartItem> ztb;
}
