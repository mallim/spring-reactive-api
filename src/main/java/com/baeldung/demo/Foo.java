package com.baeldung.demo;

import lombok.*;

@Getter
@Builder
@ToString
public class Foo {
    private long id;
    private String name;
}
