package com.barddoo.model;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Introspected
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {

    @QueryValue(defaultValue = "1")
    private Integer page;

    @QueryValue(defaultValue = "30")
    private Integer size;

    private HttpRequest<?> httpRequest;

}
