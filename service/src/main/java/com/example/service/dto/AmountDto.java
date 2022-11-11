package com.example.service.dto;

public class AmountDto {
    public AmountDto(Long id, Long value) {
        this.id = id;
        this.value = value;
    }

    private Long id;
    private Long value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
