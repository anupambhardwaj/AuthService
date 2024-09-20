package example.com.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.Document

@Serializable
data class UserDetails(
    val _id: String? = null,
    val email: String,
    val password: String,
    val token: String,
) {

    fun toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): UserDetails = json.decodeFromString(document.toJson())
    }

}
