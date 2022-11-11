package com.example.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "amount")
@NoArgsConstructor
@Getter
@Setter
public class Amount {

    @Id
    private Long id;
    private Long value;
}

