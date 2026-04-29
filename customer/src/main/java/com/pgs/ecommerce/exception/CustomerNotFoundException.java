package com.pgs.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String msg;

	public CustomerNotFoundException(String msg) {
		super(msg);
		this.msg = msg;
	}
}
