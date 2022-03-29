package cn.cdipcc.aerolite.server.controller;

import cn.cdipcc.aerolite.server.dto.ApiResult;
import cn.cdipcc.aerolite.server.dto.AuthInfo;
import cn.cdipcc.aerolite.server.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "鉴权接口")
@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Resource
    private AuthService authService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public ApiResult<AuthInfo> login(@RequestParam String username, @RequestParam String password) {
        return ApiResult.success(authService.login(username, password));
    }

    @ApiOperation("注销")
    @PostMapping("/logout")
    public ApiResult<Void> logout() {
        authService.logout();
        return ApiResult.success();
    }

    @ApiOperation("刷新Token")
    @PostMapping("/refresh")
    public ApiResult<AuthInfo> refreshToken(@RequestParam String refreshToken) {
        return ApiResult.success(authService.refresh(refreshToken));
    }
}
