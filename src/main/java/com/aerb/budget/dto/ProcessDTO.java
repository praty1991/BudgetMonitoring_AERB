package com.aerb.budget.dto;

public class ProcessDTO {
    private Long id;
    private String code;
    private String name;
    private Integer level;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public ProcessDTO() {}

    public ProcessDTO(Long id, String code, String name, Integer level) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.level = level;
    }

    // Getters and Setters
}
