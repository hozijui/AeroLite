package cn.cdipcc.aerolite.server.service.impl;

import cn.cdipcc.aerolite.server.common.ResultCode;
import cn.cdipcc.aerolite.server.config.security.AuthUser;
import cn.cdipcc.aerolite.server.dto.UserInfo;
import cn.cdipcc.aerolite.server.entity.User;
import cn.cdipcc.aerolite.server.dao.UserDao;
import cn.cdipcc.aerolite.server.exception.CustomException;
import cn.cdipcc.aerolite.server.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Resource
    private PasswordEncoder passwordEncoder;

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
        UserDetails userDetails = ((UserDetails) authentication.getPrincipal());
        UserInfo userInfo = userDao.getUserInfo(userDetails);
        Set<String> permissions = userInfo.getPermissions().stream().filter(p -> p.contains(":")).collect(Collectors.toSet());
        userInfo.setPermissions(permissions);
        return userInfo;
    }

    @Override
    public UserInfo updateInfo(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = ((UserDetails) authentication.getPrincipal());
        Long id = ((AuthUser) userDetails).getId();
        if (id.equals(user.getId())) {
            this.userDao.update(user);
            UserInfo userInfo = this.userDao.getUserInfo(userDetails);
            Set<String> permissions = userInfo.getPermissions().stream().filter(p -> p.contains(":")).collect(Collectors.toSet());
            userInfo.setPermissions(permissions);
            return userInfo;
        }
        throw new CustomException(ResultCode.ACCESS_DENIED);
    }

    @Override
    public void updatePassword(String origin, String password, String confirm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = ((UserDetails) authentication.getPrincipal());
        if (!passwordEncoder.matches(origin, userDetails.getPassword())) {
            throw new CustomException(ResultCode.LOGIN_ERROR);
        }
        if (!password.equals(confirm)) {
            throw new CustomException(ResultCode.FAIL);
        }
        User user = new User();
        user.setId(((AuthUser) userDetails).getId());
        user.setPassword(passwordEncoder.encode(password));
        this.userDao.update(user);
    }
}
