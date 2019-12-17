package com.example.myapplication.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.myapplication.domain.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactReader {
    private Context context;

    public ContactReader(Context context) {
        this.context = context;
    }

    public List<Contact> readContacts() {
        List<Contact> contactList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String organization = "";
                List<String> phoneList = new ArrayList<>();
                List<String> emailList = new ArrayList<>();

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    Cursor cursorPhones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[]{id}, null);

                    if (cursorPhones != null) {
                        while (cursorPhones.moveToNext()) {
                            String phone = cursorPhones.getString(cursorPhones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phoneList.add(phone);
                        }
                        cursorPhones.close();
                    }
                }

                Cursor cursorEmails = contentResolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID
                                + " = ?", new String[]{id}, null);
                if (cursorEmails != null) {
                    while (cursorEmails.moveToNext()) {
                        String email = cursorEmails.getString(cursorEmails
                                .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        emailList.add(email);
                    }
                    cursorEmails.close();
                }

                Cursor cursorOrganization = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                        null,
                        ContactsContract.Data.CONTACT_ID + "=? AND " +
                                ContactsContract.Data.MIMETYPE + "=?",
                        new String[]{id, ContactsContract.CommonDataKinds
                                .Organization.CONTENT_ITEM_TYPE},
                        null);
                if (cursorOrganization != null && cursorOrganization.moveToNext()) {
                    organization = cursorOrganization.getString(cursorOrganization.getColumnIndex
                            (ContactsContract.CommonDataKinds.Organization
                                    .COMPANY));
                    cursorOrganization.close();
                }

                Contact contact = new Contact(name, phoneList, emailList, organization);
                contactList.add(contact);
            }
            cursor.close();
        }
        return contactList;
    }
}
