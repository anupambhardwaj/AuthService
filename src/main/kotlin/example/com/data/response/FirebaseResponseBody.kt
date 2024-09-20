package example.com.data.response

import kotlinx.serialization.Serializable

val BLANK_STRING = ""

@Serializable
data class FirebaseResponseBody(
    val kind: String = BLANK_STRING,
    val idToken: String = BLANK_STRING,
    val email: String = BLANK_STRING,
    val refreshToken: String = BLANK_STRING,
    val expiresIn: String = BLANK_STRING,
    val localId: String = BLANK_STRING,
    val error: FirebaseErrorResponseModel? = null
)
