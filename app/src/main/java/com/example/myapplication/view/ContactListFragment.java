package com.example.myapplication.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ContactAdapter;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.Groupable;
import com.example.myapplication.domain.GroupableComparator;
import com.example.myapplication.service.ReadAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ContactListFragment extends Fragment implements ContactAdapter.ItemClickListener,
        ReadAsyncTask.CallbackReceiver {

    private static final String IMPLEMENT_EXCEPTION = " must implement OnFragmentInteractionListener";

    private static final String CONTACT = "contact";

    private RecyclerView recyclerView;
    private ContactAdapter adapter;

    private OnFragmentInteractionListener listener;

    private List<Groupable<String>> contactList;
    private List<Groupable<String>> ascendingContactList;
    private List<Groupable<String>> descendingContactList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_list_view, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ReadAsyncTask readAsyncTask = new ReadAsyncTask(getContext(), this);
        readAsyncTask.execute();
    }

    private void showDetails(Contact contact) {
        Intent intent = new Intent();
        intent.setClass(requireActivity(), DetailsActivity.class);
        intent.putExtra(CONTACT, contact);
        startActivity(intent);
    }

    public void descendingSort() {
        if (descendingContactList == null) {
            descendingContactList = new ArrayList<>(contactList);
            Collections.sort(descendingContactList, Collections.reverseOrder(new GroupableComparator()));
        }
        contactList = descendingContactList;
        adapter.setItems(descendingContactList);
    }

    public void ascendingSort() {
        contactList = ascendingContactList;
        adapter.setItems(ascendingContactList);
    }

    @Override
    public void onItemClick(View view, int position) {
        showDetails((Contact) adapter.getItem(position));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + IMPLEMENT_EXCEPTION);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void receiveData(List<Groupable<String>> result) {
        contactList = result;
        ascendingContactList = new ArrayList<>(result);
        listener.updateToolbarInfo(Objects.requireNonNull(contactList).size());
        adapter = new ContactAdapter(getContext(), this);
        adapter.setItems(contactList);
        recyclerView.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        void updateToolbarInfo(int size);
    }
}
