package com.github.cb2222124.vlpms.backend.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Serializable implementation of Page to facilitate sorting pagination information in a cache.
 *
 * @param <T> Page content.
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = {"pageable"})
public class SerializablePage<T> extends PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SerializablePage(@JsonProperty("content") List<T> content,
                            @JsonProperty("number") int page,
                            @JsonProperty("size") int size,
                            @JsonProperty("totalElements") long total) {
        super(content, PageRequest.of(page, size), total);
    }

    public SerializablePage(Page<T> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }
}
