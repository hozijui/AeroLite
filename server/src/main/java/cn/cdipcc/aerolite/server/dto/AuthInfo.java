package cn.cdipcc.aerolite.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description = "鉴权token")
public class AuthInfo {
    @ApiModelProperty(value = "Token")
    String token;

    @ApiModelProperty(value = "Refresh Token")
    String refresh_token;
}
