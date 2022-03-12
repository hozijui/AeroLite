package cn.cdipcc.aerolite.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限表(Permission)实体类
 *
 * @author hozijui
 */
@Data
@ApiModel(description = "权限表")
public class Permission implements Serializable {
    private static final long serialVersionUID = 128241629289719615L;

    @ApiModelProperty()
    private Long id;

    @ApiModelProperty(value = "唯一标识")
    private String permission;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "允许的请求方法")
    private String method;

    @ApiModelProperty(value = "权限描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "是否有效")
    private Integer enabled;

    @ApiModelProperty(value = "可访问的地址")
    private String uri;
}
