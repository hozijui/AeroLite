package cn.cdipcc.aerolite.server.controller;

import cn.cdipcc.aerolite.server.dto.ApiResult;
import cn.cdipcc.aerolite.server.entity.Role;
import cn.cdipcc.aerolite.server.service.RoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色表(Role)控制层
 *
 * @author hozijui
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("api/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @ApiOperation("获取角色列表")
    @GetMapping
    public ApiResult<PageInfo<Role>> queryByPage(
        @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "limit", defaultValue = "10") Integer limit) {
        return ApiResult.success(this.roleService.queryByPage(page, limit));
    }

    @ApiOperation("获取角色信息")
    @GetMapping("{id}")
    public ApiResult<Role> queryById(@PathVariable("id") Long id) {
        return ApiResult.success(this.roleService.queryById(id));
    }

    @ApiOperation("新增角色")
    @PostMapping
    public ApiResult<Role> add(Role role) {
        return ApiResult.success(this.roleService.insert(role));
    }

    @ApiOperation("修改角色信息")
    @PutMapping
    public ApiResult<Role> edit(Role role) {
        return ApiResult.success(this.roleService.update(role));
    }

    @ApiOperation("删除角色")
    @DeleteMapping
    public ApiResult<Boolean> deleteById(Long id) {
        return ApiResult.success(this.roleService.deleteById(id));
    }
}

