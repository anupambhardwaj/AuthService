package example.com.plugins

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.Document
import org.bson.types.ObjectId
import example.com.data.model.UserDetails

class UserService(private val database: MongoDatabase) {

    var collection: MongoCollection<Document>

    init {
        database.createCollection("UserMasterDb")
        collection = database.getCollection("UserMasterDb")
    }

    // Create new car
    suspend fun create(userDetails: UserDetails): String = withContext(Dispatchers.IO) {
        val doc = userDetails.toDocument()
        collection.insertOne(doc)
        doc["_id"].toString()
    }

    // Read a car
    suspend fun read(id: String): UserDetails? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("_id", ObjectId(id))).first()?.let(UserDetails::fromDocument)
    }

    // Update a car
    suspend fun update(id: String, userDetails: UserDetails): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), userDetails.toDocument())
    }

    // Delete a car
    suspend fun delete(id: String): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("_id", ObjectId(id)))
    }

    suspend fun signInExistingUser(_id: String, updatedToken: String) {
        val user = read(_id)
        val updatedUser = user?.copy(
            token = updatedToken
        )
        if (updatedUser != null) {
            update(_id, updatedUser)
        }
    }

}