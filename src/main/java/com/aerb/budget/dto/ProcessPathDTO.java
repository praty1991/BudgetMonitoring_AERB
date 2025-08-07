package com.aerb.budget.dto;

import java.util.List;

public class ProcessPathDTO {
    private String pathString;
    private List<Long> idPathList;

    public ProcessPathDTO() {}

    public ProcessPathDTO(String pathString, List<Long> idPathList) {
        this.pathString = pathString;
        this.idPathList = idPathList;
    }

    public String getPathString() {
        return pathString;
    }

    public void setPathString(String pathString) {
        this.pathString = pathString;
    }

    public List<Long> getIdPathList() {
        return idPathList;
    }

    public void setIdPathList(List<Long> idPathList) {
        this.idPathList = idPathList;
    }
}
