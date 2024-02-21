package dark.composer.fakecallapp.contacts.adapter

import androidx.annotation.DrawableRes

data class ContactModel(
    val name:Int,
    val number:Int,
    @DrawableRes
    val image:Int,
    var count:Int,
    val limit:Int,
    var isOpen:Boolean,
    var selected:Boolean
)