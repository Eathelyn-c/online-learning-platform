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
@TableName("exam_records")
public class ExamRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long paperId;
    
    private Long userId;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private BigDecimal score;
    
    private Integer status;
    
    private String answers;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}