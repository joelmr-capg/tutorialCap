package com.cssw.tutorial.prestamo;

import com.cssw.tutorial.common.pagination.PageableRequest;

public class PrestamoSearchDTO {
    private PageableRequest pageable;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
