package example.com.data.response

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseErrorResponseModel(
    val code: Int,
    val message: String,
    val errors: List<Errors>
)
