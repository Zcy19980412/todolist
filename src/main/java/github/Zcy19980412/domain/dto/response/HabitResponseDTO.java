package github.Zcy19980412.domain.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import github.Zcy19980412.Constant.Constant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 习惯返回类
 * @author calvin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitResponseDTO {

    private Long id;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = Constant.Time.DATE_TIME,timezone = Constant.Time.ZONE)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = Constant.Time.DATE_TIME,timezone = Constant.Time.ZONE)
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

    @ApiModelProperty("今日是否需要打卡")
    private Boolean needCheck;

    @ApiModelProperty("已完成天数")
    private Integer doneDays;

    @ApiModelProperty("总天数")
    private Integer totalDays;

}
