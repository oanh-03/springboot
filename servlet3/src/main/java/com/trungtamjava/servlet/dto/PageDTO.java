package com.trungtamjava.servlet.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageDTO<T> {
	private int totalPages;
	private long totalElements;
	private List<T> contents;
}
