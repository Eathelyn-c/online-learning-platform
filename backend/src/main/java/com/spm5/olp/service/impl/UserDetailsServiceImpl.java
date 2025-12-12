package com.spm5.olp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spm5.olp.entity.User;
import com.spm5.olp.entity.UserRole;
import com.spm5.olp.mapper.UserMapper;
import com.spm5.olp.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Autowired
    private com.spm5.olp.mapper.RoleMapper roleMapper;
    
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userMapper.selectById(Long.valueOf(userId));
        
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        // 获取用户角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        QueryWrapper<UserRole> userRoleQuery = new QueryWrapper<>();
        userRoleQuery.eq("user_id", user.getId());
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQuery);
        
        for (UserRole userRole : userRoles) {
            com.spm5.olp.entity.Role role = roleMapper.selectById(userRole.getRoleId());
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
        }
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getId().toString())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(user.getStatus() == 0) // 0表示禁用
                .build();
    }
}