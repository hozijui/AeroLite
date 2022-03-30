package cn.cdipcc.aerolite.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@ApiModel(description = "用户信息")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -1998114733787263118L;

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

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

    @ApiModelProperty(value = "用户角色")
    private Set<SimpleRole> roles;

    @ApiModelProperty(value = "用户权限")
    private Set<String> permissions;

    @Data
    static class SimpleRole {
        private Long id;

        private String role;

        private String name;
    }
}
