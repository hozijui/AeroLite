package cn.cdipcc.aerolite.server.controller;

import cn.cdipcc.aerolite.server.dto.ApiResult;
import cn.cdipcc.aerolite.server.entity.Permission;
import cn.cdipcc.aerolite.server.service.PermissionService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 权限表(Permission)控制层
 *
 * @author hozijui
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("api/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    @ApiOperation("获取权限列表")
    @GetMapping
    public ApiResult<PageInfo<Permission>> queryByPage(
        @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "limit", defaultValue = "10") Integer limit) {
        return ApiResult.success(this.permissionService.queryByPage(page, limit));
    }

    @ApiOperation("获取权限信息")
    @GetMapping("{id}")
    public ApiResult<Permission> queryById(@PathVariable("id") Long id) {
        return ApiResult.success(this.permissionService.queryById(id));
    }

    @ApiOperation("新增权限")
    @PostMapping
    public ApiResult<Permission> add(Permission permission) {
        return ApiResult.success(this.permissionService.insert(permission));
    }

    @ApiOperation("修改权限信息")
    @PutMapping
    public ApiResult<Permission> edit(Permission permission) {
        return ApiResult.success(this.permissionService.update(permission));
    }

    @ApiOperation("删除权限")
    @DeleteMapping
    public ApiResult<Boolean> deleteById(Long id) {
        return ApiResult.success(this.permissionService.deleteById(id));
    }
}

