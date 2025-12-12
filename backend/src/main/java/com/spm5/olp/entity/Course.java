package com.spm5.olp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("courses")
public class Course {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;

    private String description;
    
    private String coverImage;
    
    private Integer categoryId;
    
    private Long instructorId;
    
    private BigDecimal price;
    
    private BigDecimal originPrice;
    
    private Integer status;
    
    private Integer level;
    
    private Integer recommend;
    
    private Integer sortOrder;
    
    private LocalDateTime publishedAt;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}