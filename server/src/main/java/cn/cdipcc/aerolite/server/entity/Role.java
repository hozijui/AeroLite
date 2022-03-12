package cn.cdipcc.aerolite.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 角色表(Role)实体类
 *
 * @author hozijui
 */
@Data
@ApiModel(description = "角色表")
public class Role implements Serializable {
    private static final long serialVersionUID = 613970227331300363L;

    @ApiModelProperty()
    private Long id;

    @ApiModelProperty(value = "唯一标识")
    private String role;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "是否启用")
    private Integer enabled;
}
