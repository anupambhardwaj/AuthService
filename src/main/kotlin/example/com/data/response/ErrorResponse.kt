package example.com.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val message: String) {
    companion object {
        val NOT_FOUND_RESPONSE = ErrorResponse(message = "User not found")
        val USER_ALREADY_EXISTS = ErrorResponse(message = "EMAIL_EXISTS")
        val BAD_REQUEST_RESPONSE = ErrorResponse("Invalid request")
        val UNAUTHORIZED_REQUEST_RESPONSE = ErrorResponse("User Unauthorized")
        val TASK_NOT_FOUND_RESPONSE = ErrorResponse("Task not Found!!")
        val INVALID_API_KEY = ErrorResponse("Invalid API key")
    }
}
