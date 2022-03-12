package cn.cdipcc.aerolite.server.dto;

import cn.cdipcc.aerolite.server.entity.Permission;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class RolePermission implements Serializable {
    private static final long serialVersionUID = -5630591162304234408L;

    private Long id;

    private String role;

    private String name;

    private String description;

    private Date createTime;

    private Date modifyTime;

    private Integer enabled;

    private List<Permission> permissions;
}
