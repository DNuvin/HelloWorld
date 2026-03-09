package com.typeb.assignment.adapter.in.web.dto.response;

import java.util.Map;

public interface ResponseEntityInterface<T> {
    Map<String, Object> transform(T entity);
}
