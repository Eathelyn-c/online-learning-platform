package com.spm5.olp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("course_lessons")
public class CourseLesson {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long chapterId;
    
    private Long courseId;
    
    private String title;
    
    private String description;
    
    private String videoUrl;
    
    private Integer duration;
    
    private Integer sortOrder;
    
    private Integer freePreview;
    
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}