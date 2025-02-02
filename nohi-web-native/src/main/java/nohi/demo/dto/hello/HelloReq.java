package nohi.demo.dto.hello;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>HelloReq</p>
 * @date 2023/06/26 13:00
 **/
@Data
@Schema(name = "Hello请求对象", title = "Hello-Req", description = "请求对象示例")
public class HelloReq {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "流水号2", description = "流水号3", example = "20230618000011110001")
    private String traceId;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "姓名", description = "打招的人", example = "张三")

    @NotBlank(message = "请输入名称")
    @Length(message = "名称[${validatedValue}]长度必须[{min}-{max}]字符", min = 5, max = 10)
    private String name;

    @Max(value=50,message = "count需要小于等于50")
    @Min(value=10,message = "count需要大于等于10")
    private int count;

    @NotBlank(message = "age不能为空")
    private String age;

}
