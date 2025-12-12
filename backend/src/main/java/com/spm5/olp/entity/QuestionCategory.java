package com.spm5.olp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("question_categories")
public class QuestionCategory {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String name;
    
    private Integer parentId;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}