package gs.calendar.appointments.api.auth

import gs.calendar.appointments.auth.AuthService
import io.swagger.v3.oas.annotations.Operation
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Singleton
@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
class AuthController @Inject constructor(
    private val service: AuthService
) {

    @GET
    fun authorize(@Context uri: UriInfo): Response = Response
        .seeOther(
            service.authorize(
                uri.requestUriBuilder
                    .path("handler")
                    .build()
            )
        )
        .build()

    @GET
    @Path("handler")
    @Operation(hidden = true)
    fun handleCallback(@QueryParam("code") code: String, @Context uri: UriInfo): Response {
        service.authorize(
            uri.requestUriBuilder
                .replaceQuery(null)
                .build(),
            code
        )

        return Response
            .seeOther(
                uri.baseUriBuilder
                    .path("agendas") // TODO improve this
                    .build()
            )
            .build()
    }

}
