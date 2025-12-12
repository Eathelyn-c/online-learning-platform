package com.spm5.olp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("permissions")
public class Permission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String name;
    
    private String description;
    
    private String url;
    
    private String method;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}