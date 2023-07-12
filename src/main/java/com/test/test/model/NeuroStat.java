package com.test.test.model;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NeuroStat {
    private Boolean flag;
    private Double curvature;
    private Double accaverage;
    private Double acc1;
    private Double acc2;
    private Double acc3;
    private Double acc4;
    private Double acc5;
    private Double acc6;
    private Double speedaverage;
    private Double speed1;
    private Double speed2;
    private Double speed3;
    private Double speed4;
    private Double speed5;
}
