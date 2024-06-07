package github.Zcy19980412.domain.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 习惯请求类
 * @author calvin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitRequestDTO {

    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @JsonIgnore
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
