package com.typeb.assignment.adapter.in.web.dto.response;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResponseTransformer {

    /**
     * Transform a single domain entity to ApiResponse with a transformer.
     *
     * @param entity Domain entity
     * @param transformer Transformer implementation
     * @param <T> Domain type
     * @return ResponseEntity<ApiResponse<Map<String, Object>>>
     */
    public static <T> ResponseEntity<ApiResponse<Map<String, Object>>> transform(
            T entity, ResponseEntityInterface<T> transformer) {
        Map<String, Object> map = transformer.transform(entity);
        return ResponseBuilder.success(map);
    }

    /**
     * Transform an array of domain entities to ApiResponse with a transformer.
     *
     * @param entities Array of domain entities
     * @param transformer Transformer implementation
     * @param <T> Domain type
     * @return ResponseEntity<ApiResponse<List<Map<String, Object>>>>
     */
    public static <T> ResponseEntity<ApiResponse<java.util.List<Map<String, Object>>>> transformList(
            java.util.List<T> entities, ResponseEntityInterface<T> transformer) {

        java.util.List<Map<String, Object>> transformedList = entities.stream()
                .map(transformer::transform)
                .toList();

        return ResponseBuilder.success(transformedList);
    }
}
