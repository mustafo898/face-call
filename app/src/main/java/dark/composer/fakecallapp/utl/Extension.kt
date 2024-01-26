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
            "Bob",
            "+7 999 192 99 12",
            R.drawable.main,
            0,
            0,
            isOpen = true,
            selected = true
        )
    )

    list.add(
        ContactModel(
            "Bugs",
            "+7 999 525 66 77",
            R.drawable.c2,
            0,
            2,
            isOpen = false,
            selected = false
        )
    )
    list.add(
        ContactModel(
            "Rocky",
            "+7 999 777 53 53",
            R.drawable.c3,
            0,
            3,
            isOpen = false,
            selected = false
        )
    )
    list.add(
        ContactModel(
            "Luna",
            "+7 999 925 75 87",
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