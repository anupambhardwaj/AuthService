package example.com.data.response

import kotlinx.serialization.Serializable

@Serializable
data class Errors(
    val message: String,
    val domain: String,
    val reason: String
)
