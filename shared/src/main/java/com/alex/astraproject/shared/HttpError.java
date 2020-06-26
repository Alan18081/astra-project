package com.alex.astraproject.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpError {
	private String message;
	private int statusCode;
	private long timestamp;
}
