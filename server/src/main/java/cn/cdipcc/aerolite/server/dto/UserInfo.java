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

    private String department;

    private Date createTime;

    private Date modifyTime;

    private Date lastLogin;

    private Integer enabled;

    private Set<SimpleRole> roles;

    private Set<String> permissions;

    @Data
    static class SimpleRole {
        private String id;

        private String name;
    }
}
