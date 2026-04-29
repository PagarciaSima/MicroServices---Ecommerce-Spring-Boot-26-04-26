package com.pgs.ecommerce.dto;

import com.pgs.ecommerce.entity.Address;

public record CustomerResponse(
	    String id,
	    String firstname,
	    String lastname,
	    String email,
	    Address address
	) {

	}
