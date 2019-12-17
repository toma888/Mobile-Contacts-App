package com.example.myapplication.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.domain.Contact;

public class ContactDetailFragment extends Fragment {

    private static final String CONTACT = "contact";

    private Contact getShownContact() {
        if (getArguments() != null) {
            return getArguments().getParcelable(CONTACT);
        }
        return null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_detail, container, false);
        TextView textName = v.findViewById(R.id.tv_name);
        ListView listEmail = v.findViewById(R.id.lv_email);
        ListView listPhone = v.findViewById(R.id.lv_phone);
        TextView textOrganization = v.findViewById(R.id.tv_organization);

        Contact contact = getShownContact();

        if (contact != null) {
            if (contact.getEmail().size() != 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_list_item_1, contact.getEmail());
                listEmail.setAdapter(adapter);
                listEmail.setOnItemClickListener((adapterView, view, position, id) -> performEmailTap(view));
            }
            if (contact.getName() != null) {
                textName.setText(contact.getName());
            }
            if (contact.getPhone().size() != 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_list_item_1, contact.getPhone());
                listPhone.setAdapter(adapter);
                listPhone.setOnItemClickListener((adapterView, view, position, id) -> performPhoneTap(view));
            }
            if (contact.getOrganization() != null) {
                textOrganization.setText(contact.getOrganization());
            }
        }
        return v;
    }

    private void performPhoneTap(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +
                ((TextView) view).getText()));
        startActivity(intent);
    }

    private void performEmailTap(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" +
                (((TextView) view).getText())));
        startActivity(Intent.createChooser(emailIntent, ""));
    }
}
