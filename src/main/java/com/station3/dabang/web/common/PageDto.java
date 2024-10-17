package com.station3.dabang.web.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {
    private List<T> items;
    private PaginationDto pagination;

    public static <T> PageDto<T> from(Page<T> page) {
        return new PageDto<>(
                page.getContent(),
                new PaginationDto(
                        page.getTotalPages(),
                        page.getTotalElements(),
                        page.getSize(),
                        page.getNumber(),
                        page.getNumberOfElements()
                )
        );
    }
}
