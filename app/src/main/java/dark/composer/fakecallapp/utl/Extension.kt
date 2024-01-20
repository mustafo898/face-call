package dark.composer.fakecallapp.utl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dark.composer.fakecallapp.contacts.adapter.ContactModel
import java.lang.reflect.Type

fun objectToJson(data: List<ContactModel>): String {
    return Gson().toJson(data)
}

fun jsonToObject(data: String?): List<ContactModel> {
    val type: Type = object : TypeToken<List<ContactModel?>?>() {}.type
    return Gson().fromJson(data, type)
}