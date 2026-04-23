package com.cssw.tutorial.prestamo;

import com.cssw.tutorial.common.pagination.PageableRequest;

import java.util.Date;

public class PrestamoSearchDTO {
    private PageableRequest pageable;

    private String titulo;
    private Long idClient;
    private Date fecha;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
