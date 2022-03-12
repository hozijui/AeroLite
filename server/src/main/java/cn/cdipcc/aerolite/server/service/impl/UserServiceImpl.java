package cn.cdipcc.aerolite.server.service.impl;

import cn.cdipcc.aerolite.server.dto.UserInfo;
import cn.cdipcc.aerolite.server.entity.User;
import cn.cdipcc.aerolite.server.dao.UserDao;
import cn.cdipcc.aerolite.server.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户表(User)服务实现类
 *
 * @author hozijui
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User queryById(Long id) {
        return this.userDao.queryById(id);
    }

    @Override
    public User queryByUsername(String username) {
        return this.userDao.queryByUsername(username);
    }

    @Override
    public PageInfo<User> queryByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<User> userList = this.userDao.queryAll();
        return new PageInfo<>(userList);
    }

    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        UserInfo userInfo = userDao.getUserInfo(username);
        Set<String> permissions = userInfo.getPermissions().stream().filter(p -> p.contains(":")).collect(Collectors.toSet());
        userInfo.setPermissions(permissions);
        return userInfo;
    }
}
