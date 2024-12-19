package co.pokeum.inputs.dialog.model

import org.json.JSONException
import org.json.JSONObject
import kotlin.jvm.Throws

/**
 * Data class representing a result model with a value and a type.
 *
 * @property value The value of the result.
 * @property type The type of the result, with a default value of -1.
 */
data class ResultModel(
    val value: String,
    val type: Int = -1
) {
    internal fun json(): JSONObject = JSONObject().apply {
        put(VALUE_KEY, value)
        put(TYPE_KEY, type)
    }

    companion object {
        private const val VALUE_KEY = "value"
        private const val TYPE_KEY = "type"

        /**
         * Parses a JSON string to extract a ResultModel instance.
         *
         * @param result The JSON string containing the result data.
         * @param key The key under which the result data is stored.
         * @return A ResultModel instance if parsing is successful, null otherwise.
         */
        @JvmStatic
        fun get(result: String, key: String): ResultModel? {
            try {
                val jsonObject = JSONObject(result).getJSONObject(key)
                return ResultModel(
                    jsonObject.getString(VALUE_KEY),
                    jsonObject.getInt(TYPE_KEY)
                )
            } catch (_: JSONException) {
                // Return null if there's an error during JSON parsing.
            }
            return null
        }

        /**
         * Extracts the value from a JSON string using the given key.
         *
         * @param result The JSON string containing the result data.
         * @param key The key under which the result data is stored.
         * @return The value extracted from the JSON string.
         * @throws NoSuchElementException If the key does not exist or parsing fails.
         */
        @JvmStatic
        @Throws(NoSuchElementException::class)
        fun getValue(result: String, key: String): String =
            (get(result, key) ?: throw NoSuchElementException()).value

        /**
         * Extracts the type from a JSON string using the given key.
         *
         * @param result The JSON string containing the result data.
         * @param key The key under which the result data is stored.
         * @return The type extracted from the JSON string.
         * @throws NoSuchElementException If the key does not exist or parsing fails.
         */
        @JvmStatic
        @Throws(NoSuchElementException::class)
        fun getType(result: String, key: String): Int =
            (get(result, key) ?: throw NoSuchElementException()).type
    }
}