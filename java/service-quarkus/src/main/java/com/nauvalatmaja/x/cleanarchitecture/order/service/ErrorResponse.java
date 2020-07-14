package com.nauvalatmaja.x.cleanarchitecture.order.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	private String errorCode;
	private String errorDescription;
}
