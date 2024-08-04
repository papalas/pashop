package cz.mcity.pashop.controller;

import java.util.List;
import java.util.Objects;

public class PageDTO<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public PageDTO(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages) {
        this.content = content;
        this.pageNumber= pageNumber;
        this.pageSize=pageSize;
        this.totalPages=totalPages;
        this.totalElements= totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageDTO entity = (PageDTO) o;
        return Objects.equals(this.content, entity.content) &&
                Objects.equals(this.pageNumber, entity.pageNumber) &&
                Objects.equals(this.pageSize, entity.pageSize) &&
                Objects.equals(this.totalElements, entity.totalElements) &&
                Objects.equals(this.totalPages, entity.totalPages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, pageNumber, pageSize, totalElements, totalPages);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "content = " + content + ", " +
                "pageNumber = " + pageNumber + ", " +
                "pageSize = " + pageSize + ", " +
                "totalElements = " + totalElements + ", " +
                "totalPages = " + totalPages + ")";
    }

    // Constructor, getters, and setters
}