package com.spm5.olp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("exam_paper_questions")
public class ExamPaperQuestion {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long paperId;
    
    private Long questionId;
    
    private BigDecimal score;
    
    private Integer sortOrder;
}