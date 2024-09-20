package example.com.config.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.InputStream

object FirebaseAdmin {
    private val serviceAccount: InputStream? =
        this::class.java.classLoader.getResourceAsStream("taskmaster-36ca0-firebase-adminsdk-ursy4-b26e79041b.json")
    private val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()
    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)
}
