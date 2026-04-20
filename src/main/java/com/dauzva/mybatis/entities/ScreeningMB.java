package com.dauzva.mybatis.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class ScreeningMB {
    private Integer id;
    private LocalDateTime startTime;
    private BigDecimal price;

    // These come from JOINs in the SQL — not single-table columns
    private String movieTitle;
    private Integer movieDuration;
    private String hallName;
    private String cinemaName;
}