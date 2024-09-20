package example.com.data.model

import io.ktor.server.auth.*
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    @BsonId
    val _id: String,
    val username: String,
): Principal
