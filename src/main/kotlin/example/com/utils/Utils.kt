package example.com.utils

import com.google.gson.Gson

const val API_KEY_PARAMETER = "api_key"

inline fun <reified T : Any> T.json(): String = Gson().toJson(this, T::class.java)

