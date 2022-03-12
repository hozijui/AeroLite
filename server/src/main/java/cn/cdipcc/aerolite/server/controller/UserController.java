package cn.cdipcc.aerolite.server.controller;

import cn.cdipcc.aerolite.server.dto.ApiResult;
import cn.cdipcc.aerolite.server.dto.UserInfo;
import cn.cdipcc.aerolite.server.entity.User;
import cn.cdipcc.aerolite.server.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户表(User)控制层
 *
 * @author hozijui
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("api/user")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("获取用户列表")
    @GetMapping
    public ApiResult<PageInfo<User>> queryByPage(
        @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "limit", defaultValue = "10") Integer limit) {
        return ApiResult.success(this.userService.queryByPage(page, limit));
    }

    @ApiOperation("获取用户信息")
    @GetMapping("{id}")
    public ApiResult<User> queryById(@PathVariable("id") Long id) {
        return ApiResult.success(this.userService.queryById(id));
    }

    @ApiOperation("新增用户")
    @PostMapping
    public ApiResult<User> add(User user) {
        return ApiResult.success(this.userService.insert(user));
    }

    @ApiOperation("修改用户信息")
    @PutMapping
    public ApiResult<User> edit(User user) {
        return ApiResult.success(this.userService.update(user));
    }

    @ApiOperation("删除用户")
    @DeleteMapping()
    public ApiResult<Boolean> deleteById(Long id) {
        return ApiResult.success(this.userService.deleteById(id));
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("info")
    public ApiResult<UserInfo> getUserInfo() {
        return ApiResult.success(this.userService.getUserInfo());
    }
}

