package cn.cdipcc.aerolite.server.controller;

import cn.cdipcc.aerolite.server.dto.ApiResult;
import cn.cdipcc.aerolite.server.dto.UserInfo;
import cn.cdipcc.aerolite.server.entity.User;
import cn.cdipcc.aerolite.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "账户信息管理")
@RestController
@RequestMapping("api/account")
public class AccountController {
    @Resource
    private UserService userService;

    @ApiOperation("获取账户信息")
    @GetMapping("info")
    public ApiResult<UserInfo> getUserInfo() {
        return ApiResult.success(this.userService.getUserInfo());
    }

    @ApiOperation("修改账户信息")
    @PatchMapping("info")
    public ApiResult<UserInfo> updateInfo(User user) {
        return ApiResult.success(this.userService.updateInfo(user));
    }

    @ApiOperation("修改账户密码")
    @PutMapping("password")
    public ApiResult<Void> modifyPassword(
        @RequestParam String origin,
        @RequestParam String password,
        @RequestParam String confirm
    ) {
        this.userService.updatePassword(origin, password, confirm);
        return ApiResult.success();
    }
}
