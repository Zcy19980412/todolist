package github.Zcy19980412.domain.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitRecord {

    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("习惯id")
    private Long habitId;

    @ApiModelProperty("打卡")
    private Boolean checked;

    @ApiModelProperty("备注")
    private String remark;

}
