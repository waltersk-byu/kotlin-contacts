import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

// Constants
object ContactConstants {
    const val CONTACTS_FILE = "kcontacts.txt"
}

// Contact class
class Contact {
    var FirstName = ""
    var LastName = ""
    fun fullName (): String {
        return "$FirstName $LastName"
    }
}

// main entry for the project
fun main() {
    var choice = showMenu()

    while (handleMenuChoice(choice)) {

        choice = showMenu()
    }

    println("Good bye")
}

// Show the main menu and return the user choice
fun showMenu(): Int {
    println("------------------------------------")
    println("Welcome to K-Contacts")
    println("------------------------------------")
    println("Main Menu")
    println("1. View Contacts")
    println("2. Add Contact")
    println("3. Edit Contact")
    println("4. Delete Contact")
    println("5. Exit")
    println()
    println("What do you want to do? Enter 1 - 5:")
    return readln().toInt()
}

// Handle the user choice
fun handleMenuChoice(choice: Int): Boolean {
    when (choice) {
        1 -> showContacts()
        2 -> addContact()
        3 -> editContact()
        4 -> deleteContact()
        5 -> return false
        else -> {
            println("Invalid option $choice")

        }

    }
    return true
}

// Show all the contacts
fun showContacts(): Int {
    val contactList = mutableListOf<String>()
    try {
        val inputStream: InputStream = File(ContactConstants.CONTACTS_FILE).inputStream()
        inputStream.bufferedReader().forEachLine {
            contactList.add(it)
        }

    } catch (e: Exception) {
        // We don't have a contacts file
        // we just show an empty list

    }

    if (contactList.isNotEmpty()) {
        var contactNumber = 1
        for (fullName in contactList) {
            println("$contactNumber: $fullName")
            contactNumber++
        }
    } else {
        println("No contacts")
    }

    return contactList.count()
}

// add a new contact
fun addContact() {
    val contact = Contact()
    println("First Name:")
    contact.FirstName = readln().toString()
    println("Last Name:")
    contact.LastName = readln().toString()
    val fullName = contact.fullName()
    println("Add $fullName (y/n)?")
    val addOrNot = readln().toString()
    if (addOrNot.uppercase(Locale.getDefault()).first() == 'Y') {
        val contactFile = File(ContactConstants.CONTACTS_FILE)
        val writer = FileOutputStream(contactFile, true).bufferedWriter()
        writer.write(fullName)
        writer.newLine()
        writer.flush()
        writer.close()
    }
}

// edit a contact
fun editContact() {
    // show the list of contacts first
    println("Current Contacts:")
    if (showContacts() > 0) {
        println("Which contact do you want to edit?")
        val contactNum = readln().toInt()

        // get all contacts into a list
        val contactList = mutableListOf<String>()
        val inputStream: InputStream = File(ContactConstants.CONTACTS_FILE).inputStream()
        inputStream.bufferedReader().forEachLine {
            contactList.add(it)
        }

        println("New First Name:")
        val fName = readln().toString()
        println("New Last Name:")
        val lName = readln().toString()
        val fullName = "$fName $lName"
        val curName = contactList[contactNum - 1]
        println("Change '$curName' to '$fullName'? (y/n)")
        val changeOrNot = readln().toString()
        if (changeOrNot.uppercase(Locale.getDefault()).first() == 'Y') {
            contactList[contactNum - 1] = fullName
        }

        //rewrite the file
        val contactFile = File(ContactConstants.CONTACTS_FILE)
        val writer = FileOutputStream(contactFile, false).bufferedWriter()

        for (name in contactList) {
            writer.write(name)
            writer.newLine()
        }
        writer.flush()
        writer.close()
    }

}

// delete a contact
fun deleteContact() {
    // show the list of contacts first
    println("Current Contacts:")
    if (showContacts() > 0) {
        println("Which contact do you want to delete?")
        val contactNum = readln().toInt()

        // get all contacts into a list
        val contactList = mutableListOf<String>()
        val inputStream: InputStream = File(ContactConstants.CONTACTS_FILE).inputStream()
        inputStream.bufferedReader().forEachLine {
            contactList.add(it)
        }

        val curName = contactList[contactNum - 1]
        println("Delete '$curName'? (y/n)")
        val delOrNot = readln().toString()
        if (delOrNot.uppercase(Locale.getDefault()).first() == 'Y') {
            contactList.removeAt(contactNum - 1)
        }

        //rewrite the file
        val contactFile = File(ContactConstants.CONTACTS_FILE)
        val writer = FileOutputStream(contactFile, false).bufferedWriter()

        for (name in contactList) {
            writer.write(name)
            writer.newLine()
        }
        writer.flush()
        writer.close()
    }


}
