package com.nauvalatmaja.x.cleanarchitecture.order.service;

import com.nauvalatmaja.x.cleanarchitecture.core.order.InvalidRequestException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidRequestExceptionMapper implements ExceptionMapper<InvalidRequestException> {

	@Override
	public Response toResponse(InvalidRequestException exception) {
		return Response.status(400).entity(new ErrorResponse("400x01", "Invalid Request")).build();
	}

}
