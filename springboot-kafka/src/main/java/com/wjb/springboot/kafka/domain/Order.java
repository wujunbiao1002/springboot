package com.wjb.springboot.kafka.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private String id;
    private String name;
    private String price;
}
