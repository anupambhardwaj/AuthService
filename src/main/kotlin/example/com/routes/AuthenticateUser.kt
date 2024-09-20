package example.com.routes

import example.com.auth.firebase.FIREBASE_AUTH
import example.com.plugins.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import example.com.data.response.ApiResponse
import example.com.data.response.ErrorResponse
import example.com.data.model.User

fun Route.authenticateRoute() {
    authenticate(FIREBASE_AUTH) {
        get("/authenticated") {
            try {
                val user: User =
                    call.principal() ?: return@get call.respond(
                        message = ApiResponse(
                            success = false,
                            message = ErrorResponse.UNAUTHORIZED_REQUEST_RESPONSE.message,
                            data = null
                        ), status = HttpStatusCode.Unauthorized
                    )
                call.respond(HttpStatusCode.OK, "My name is ${user._id}, and I'm authenticated!")
            } catch (ex: Exception) {
                return@get call.respond(
                    message = ApiResponse(
                        success = false,
                        message = ex.message,
                        data = null
                    ), status = HttpStatusCode.Unauthorized
                )
            }

        }
    }
}