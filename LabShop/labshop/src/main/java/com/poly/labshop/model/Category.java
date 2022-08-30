package com.poly.labshop.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id /* Trường id mặc định phải có*/
    @GeneratedValue(strategy = GenerationType.IDENTITY) /* trường id tự tăng*/
    private Long id;

    @Column(length = 50)
//    @Column(columnDefinition = "nvarchar(50)")
    private String name;
}
