package com.cssw.tutorial.author;

import com.cssw.tutorial.common.pagination.PageableRequest;

public class AuthorSearchDto {
    private PageableRequest pageable;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
