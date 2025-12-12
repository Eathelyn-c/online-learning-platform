package com.spm5.olp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spm5.olp.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}