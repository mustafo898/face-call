package dark.composer.fakecallapp.utl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.contacts.adapter.ContactModel
import java.lang.reflect.Type

fun objectToJson(data: List<ContactModel>): String {
    return Gson().toJson(data)
}

fun jsonToObject(data: String?): List<ContactModel> {
    val list = mutableListOf<ContactModel>()
    list.add(
        ContactModel(
            R.string.char1,
            R.string.num1,
            R.drawable.char1,
            0,
            0,
            isOpen = true,
            selected = true
        )
    )

    list.add(
        ContactModel(
            R.string.char2,
            R.string.num2,
            R.drawable.c2,
            0,
            2,
            isOpen = false,
            selected = false
        )
    )
    list.add(
        ContactModel(
            R.string.char3,
            R.string.num3,
            R.drawable.c3,
            0,
            3,
            isOpen = false,
            selected = false
        )
    )
    list.add(
        ContactModel(
            R.string.char4,
            R.string.num4,
            R.drawable.c4,
            0,
            4,
            isOpen = false,
            selected = false
        )
    )
    val type: Type = object : TypeToken<List<ContactModel?>?>() {}.type
    return Gson().fromJson(data, type) ?: list
}