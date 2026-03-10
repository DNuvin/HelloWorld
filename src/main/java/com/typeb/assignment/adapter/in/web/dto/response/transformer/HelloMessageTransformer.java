package com.typeb.assignment.adapter.in.web.dto.response.transformer;

import com.typeb.assignment.adapter.in.web.dto.response.ResponseEntityInterface;
import com.typeb.assignment.domain.model.HelloMessage;

import java.util.HashMap;
import java.util.Map;

public class HelloMessageTransformer implements ResponseEntityInterface<HelloMessage> {

    @Override
    public Map<String, Object> transform(HelloMessage entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", entity.getMessage());
        return map;
    }
}
