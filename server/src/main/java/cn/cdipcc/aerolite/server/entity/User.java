package cn.cdipcc.aerolite.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户表(User)实体类
 *
 * @author hozijui
 */
@Data
@ApiModel(description = "用户")
public class User implements Serializable {
    private static final long serialVersionUID = -37533217382345243L;

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "部门职位")
    private String department;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "最近登录时间")
    private Date lastLogin;

    @ApiModelProperty(value = "是否启用")
    private Integer enabled;
}
