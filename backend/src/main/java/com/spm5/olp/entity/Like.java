package com.spm5.olp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("likes")
public class Like {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Integer targetType;
    
    private Long targetId;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}