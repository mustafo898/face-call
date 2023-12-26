package dark.composer.fakecallapp.contacts.adapter

data class ContactModel(
    val name:String,
    val number:String,
    var count:Int,
    var isOpen:Boolean,
    var clicked:Boolean
)