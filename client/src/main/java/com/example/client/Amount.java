package com.example.client;

public class Amount {
    public Amount() {
    }

    public Amount(long id, long value) {
        this.id = id;
        this.value = value;
    }

    public long id;
    public long value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
