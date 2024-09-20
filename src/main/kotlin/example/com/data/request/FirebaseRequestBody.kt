package example.com.data.request

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseRequestBody(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)
