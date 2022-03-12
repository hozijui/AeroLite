package cn.cdipcc.aerolite.server.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -1998114733787263118L;

    private Long id;

    private String username;

    private String nickname;

    private Date createTime;

    private Date modifyTime;

    private Date lastLogin;

    private Integer enabled;

    private Set<String> roles;

    private Set<String> permissions;
}
