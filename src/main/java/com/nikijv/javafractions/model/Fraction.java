package com.nikijv.javafractions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fraction {
    private int id;
    public int numerator;
    public int denominator;
}
