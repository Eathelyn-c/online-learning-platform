package com.spm5.olp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("course_resources")
public class CourseResource {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long courseId;
    
    private Long resourceId;
}