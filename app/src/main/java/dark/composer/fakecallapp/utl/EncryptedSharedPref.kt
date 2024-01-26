package dark.composer.fakecallapp.utl

import android.content.Context
import android.content.SharedPreferences
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.contacts.adapter.ContactModel

class EncryptedSharedPref(context: Context) {


    private var preferences: SharedPreferences

    private lateinit var editor: SharedPreferences.Editor

    init {
        preferences = context.getSharedPreferences("APP_PREFS_NAME", Context.MODE_PRIVATE)
        editor = preferences.edit()
    }


    val Key1 = "0"
    val Key2 = "1"
    val Key3 = "2"
    val Key4 = "3"
    val Key5 = "4"
    val Key6 = "5"

    fun setList(list: List<ContactModel>) {
        editor.putString("list", objectToJson(list))
        editor.apply()
    }


    fun getList(): List<ContactModel> {
        return jsonToObject(preferences.getString("list", "") ?: "")
    }

    fun save(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun get(key: String, defValue: Int) = preferences.getInt(key, defValue)

}