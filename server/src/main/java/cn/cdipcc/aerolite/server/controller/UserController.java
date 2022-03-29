package cn.cdipcc.aerolite.server.controller;

import cn.cdipcc.aerolite.server.dto.ApiResult;
import cn.cdipcc.aerolite.server.dto.PageDTO;
import cn.cdipcc.aerolite.server.dto.UserInfo;
import cn.cdipcc.aerolite.server.entity.User;
import cn.cdipcc.aerolite.server.service.UserService;
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
    public ApiResult<PageDTO<UserInfo>> queryByPage(
        @RequestParam(name="page", defaultValue="1") Integer page,
        @RequestParam(name="limit", defaultValue="10") Integer limit,
        @RequestParam(name="sortField", required = false) String sortField,
        @RequestParam(name="sortOrder", required = false) String sortOrder,
        @RequestParam(name="enabled", required = false) Integer enabled
    ) {
        String orderBy = sortField != null && sortOrder != null ? sortField + " " + sortOrder : null;
        User user = new User();
        user.setEnabled(enabled);
        PageDTO<UserInfo> pageDTO = new PageDTO<>(this.userService.queryByPage(page, limit, orderBy, user));
        return ApiResult.success(pageDTO);
    }

    @ApiOperation("新增用户")
    @PostMapping
    public ApiResult<User> add(User user, @RequestParam(name="role") Long roleId) {
        return ApiResult.success(this.userService.insert(user, roleId));
    }

    @ApiOperation("修改用户信息")
    @PatchMapping("{id}")
    public ApiResult<User> edit(User user, @PathVariable Long id, @RequestParam(name="role") Long roleId) {
        user.setId(id);
        return ApiResult.success(this.userService.update(user, roleId));
    }

    @ApiOperation("修改用户密码")
    @PutMapping("{id}/password")
    public ApiResult<User> modifyPassword(
        @PathVariable Long id,
        @RequestParam String password,
        @RequestParam String confirm
    ) {
        this.userService.updatePassword(id, password, confirm);
        return ApiResult.success();
    }

    @ApiOperation("删除用户")
    @DeleteMapping("{id}")
    public ApiResult<Boolean> deleteById(@PathVariable Long id) {
        return ApiResult.success(this.userService.deleteById(id));
    }
}

