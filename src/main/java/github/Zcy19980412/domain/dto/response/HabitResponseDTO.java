package github.Zcy19980412.domain.dto.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitResponseDTO {

    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("间隔日")
    private Integer gapDays;

    @ApiModelProperty("习惯名称")
    private String name;

    @ApiModelProperty("习惯描述")
    private String description;

    @ApiModelProperty("完成率")
    private BigDecimal doneRate;

    @ApiModelProperty("重要度")
    private Integer importantRate;

}
