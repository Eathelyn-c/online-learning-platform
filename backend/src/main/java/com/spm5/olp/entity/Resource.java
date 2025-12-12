package com.spm5.olp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("resources")
public class Resource {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Integer categoryId;
    
    private String title;
    
    private String description;
    
    private String fileUrl;
    
    private Long fileSize;
    
    private String fileType;
    
    private Integer downloadCount;
    
    private Long uploaderId;
    
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}