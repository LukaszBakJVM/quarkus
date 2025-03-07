package exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static io.quarkus.arc.impl.UncaughtExceptions.LOGGER;

@Provider
public class ExceptionHandler implements ExceptionMapper<WebApplicationException> {


    @Override
    public Response toResponse(WebApplicationException e) {
        if (e.getResponse().getStatus()==404) {
            return handleUserNotFoundException(new UserNotFoundException("User not found"));
    }
          return handleGenericException (e);
}

    private Response handleUserNotFoundException(UserNotFoundException e) {
        LOGGER.error("UserNotFoundException caught: " + e.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new CustomError(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage()))
                .build();
    }

    private Response handleGenericException(Throwable e) {
        LOGGER.error("Unexpected error: " + e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new CustomError(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "An unexpected error occurred"))
                .build();
    }
}
