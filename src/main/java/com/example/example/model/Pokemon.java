package com.example.example.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {
    private Long id;
    private String name;
    private String type;
    private int height;
    private int weight;
    private String imgUrl;
}
