package cz.mcity.pashop.controller;

import java.util.List;

public record PageDTO<T>(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages) {

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