package cn.cdipcc.aerolite.server.controller;

import cn.cdipcc.aerolite.server.dto.ApiResult;
import cn.cdipcc.aerolite.server.entity.Role;
import cn.cdipcc.aerolite.server.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public ApiResult<List<Role>> queryAll() {
        return ApiResult.success(this.roleService.queryAll());
    }
}

