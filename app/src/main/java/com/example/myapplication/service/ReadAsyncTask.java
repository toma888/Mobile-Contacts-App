package com.example.myapplication.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.myapplication.R;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.Groupable;
import com.example.myapplication.domain.Header;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReadAsyncTask extends AsyncTask<Void, Void, List<Groupable<String>>> {

    private final WeakReference<Context> weakContext;
    private final CallbackReceiver callbackReceiver;
    private final ProgressDialog dialog;

    public ReadAsyncTask(Context context, CallbackReceiver callbackReceiver) {
        this.weakContext = new WeakReference<>(context);
        this.callbackReceiver = callbackReceiver;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage(weakContext.get().getString(R.string.progress_description));
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected List<Groupable<String>> doInBackground(Void... params) {
        ContactReader contactReader = new ContactReader(weakContext.get());
        return generateGroupContactList(contactReader.readContacts());
    }

    private List<Groupable<String>> generateGroupContactList(List<Contact> contacts) {
        List<Groupable<String>> groupContactList = new ArrayList<>();
        char header = 0;
        for (Contact contact : contacts) {
            char currentHeader = contact.getName().charAt(0);
            if (header != currentHeader) {
                header = currentHeader;
                groupContactList.add(new Header(currentHeader, UUID.randomUUID().toString()));
            }
            contact.setId(UUID.randomUUID().toString());
            groupContactList.add(contact);
        }
        return groupContactList;
    }

    @Override
    protected void onPostExecute(List<Groupable<String>> contactList) {
        dialog.dismiss();
        callbackReceiver.receiveData(contactList);
    }

    public interface CallbackReceiver {
        void receiveData(List<Groupable<String>> result);
    }
}