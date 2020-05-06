package com.tg.enterprise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CodeTreeVO {
    private String value;
    private String label;
    private List<CodeTreeVO> children;
}
