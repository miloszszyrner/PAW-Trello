package com.paw.trello.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationException implements ExceptionMapper<
        javax.validation.ValidationException> {

    @Override
    public Response toResponse(javax.validation.ValidationException e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorMessage(e))
                .build();
    }
}
