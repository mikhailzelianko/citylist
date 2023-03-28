package com.andersen.demoproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String name;

    @Column(length = 1024)
    @NotNull
    private String pictureUrl;
}
