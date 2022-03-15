package cn.cdipcc.aerolite.server.dto;

import cn.cdipcc.aerolite.server.entity.Permission;
import cn.cdipcc.aerolite.server.entity.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserRole implements Serializable {
    private static final long serialVersionUID = 2979917932179508707L;

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String department;

    private Date createTime;

    private Date modifyTime;

    private Date lastLogin;

    private Integer enabled;

    private List<Role> roles;
}
