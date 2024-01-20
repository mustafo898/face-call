package dark.composer.fakecallapp.utl

import android.content.Context
import android.content.SharedPreferences
import dark.composer.fakecallapp.contacts.adapter.ContactModel

class EncryptedSharedPref(context: Context) {


    private var preferences: SharedPreferences =
        context.getSharedPreferences("APP_PREFS_NAME", Context.MODE_PRIVATE)

    private lateinit var editor: SharedPreferences.Editor

    fun setList(list: List<ContactModel>) {
        editor = preferences.edit()
        editor.putString("list", objectToJson(list))
        editor.apply()
    }


    fun getList(): List<ContactModel> {
        return jsonToObject(preferences.getString("token", "") ?: "")
    }
}