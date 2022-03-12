package cn.cdipcc.aerolite.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class AuthInfo {
    String token;

    String refresh_token;
}
