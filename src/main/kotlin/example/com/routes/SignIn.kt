package task_master_server.project.routes

import example.com.data.request.FirebaseRequestBody
import example.com.plugins.UserService
import example.com.utils.API_KEY_PARAMETER
import example.com.utils.json
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import example.com.data.response.ApiResponse
import example.com.data.response.ErrorResponse
import example.com.data.response.FirebaseResponseBody
import example.com.data.model.UserDetails

fun Route.signIn(userService: UserService) {
    post("/signIn") {
        val originalCall = call
        val credentials = originalCall.receive<FirebaseRequestBody>()

        val apiKey = originalCall.parameters[API_KEY_PARAMETER]

        try {
            if (apiKey != null) {
                val firebaseResponse = firebaseSignInRequest(credentials, apiKey)
                if (firebaseResponse.error == null) {
                    val userDetails = UserDetails(
                        firebaseResponse.localId,
                        email = firebaseResponse.email,
                        password = credentials.password,
                        token = firebaseResponse.idToken
                    )

//                    userService.signInExistingUser(userDetails._id!!, userDetails.token)
                    val apiResponse = ApiResponse(
                        success = true,
                        message = "OK",
                        data = firebaseResponse
                    )
                    originalCall.respond(
                        message = apiResponse,
                        status = HttpStatusCode.OK
                    )
                } else {
                    val apiResponse = ApiResponse<String>(
                        success = false,
                        message = firebaseResponse.error.message,
                        data = null
                    )
                    originalCall.respond(message = apiResponse, status = HttpStatusCode.BadRequest)
                }
            } else {
                val apiResponse = ApiResponse<String>(
                    success = false,
                    message = ErrorResponse.INVALID_API_KEY.message,
                    data = null
                )
                originalCall.respond(status = HttpStatusCode.BadRequest, message = apiResponse)
            }


        } catch (ex: Exception) {
            val apiResponse = ApiResponse<String>(
                success = false,
                message = ex.message,
                data = null
            )
            originalCall.respond(status = HttpStatusCode.BadRequest, message = apiResponse)
        }
    }
}

private suspend fun firebaseSignInRequest(credentials: FirebaseRequestBody, apiKey: String): FirebaseResponseBody {
    HttpClient(CIO) {
        install(ContentNegotiation) {
            register(
                ContentType.Text.Plain, KotlinxSerializationConverter(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        explicitNulls = true
                    }
                )
            )
            register(
                ContentType.Application.Json, KotlinxSerializationConverter(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        explicitNulls = true
                    }
                )
            )
        }
    }.use {
        val response: HttpResponse = it.request("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword") {
            val body = FirebaseRequestBody(
                email = credentials.email,
                password = credentials.password
            )
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            println("body.json:  ${body.json()}")
            setBody(
                body.json()
            )
            parameter("key", apiKey)
        }
        val body = response.call.body<FirebaseResponseBody>()
        it.close()
        return body
    }
}
