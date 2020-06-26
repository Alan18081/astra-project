package com.alex.astraproject.shared.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpErrorResponse {
	private String message;
	private long timestamp;
	private int statusCode;
}
