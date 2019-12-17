package com.example.myapplication.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

import java.util.Objects;


public class ContactsActivity extends AppCompatActivity implements ContactListFragment.OnFragmentInteractionListener {
    private static final String CONTACT_PERMISSION = Manifest.permission.READ_CONTACTS;
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static final String CONTACT_FRAGMENT = "CONTACT_FRAGMENT";

    private PowerConnectionReceiver powerConnectionReceiver;

    private ImageView imageCharge;
    private TextView tvToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageCharge = findViewById(R.id.image_charge);
        tvToolbar = findViewById(R.id.tv_toolbar_amount_contacts);
        powerConnectionReceiver = new PowerConnectionReceiver();
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(powerConnectionReceiver, iFilter);
        if (!checkPermission()) {
            requestPermission();
        } else {
            showFragment();
        }
    }

    private void showFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        ContactListFragment fragment = new ContactListFragment();
        fragmentTransaction.add(R.id.container, fragment, CONTACT_FRAGMENT);
        fragmentTransaction.commit();
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, CONTACT_PERMISSION)) {
            Toast.makeText(this, getString(R.string.contact_permission_description),
                    Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{CONTACT_PERMISSION}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showFragment();
            } else {
                Toast.makeText(this, getString(R.string.contact_permission_description_denied), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(this, CONTACT_PERMISSION);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        ContactListFragment fragment = (ContactListFragment)
                getSupportFragmentManager().findFragmentByTag(CONTACT_FRAGMENT);
        if (id == R.id.action_ascending_order) {
            Objects.requireNonNull(fragment).ascendingSort();
            return true;
        }
        if (id == R.id.action_descending_order) {
            Objects.requireNonNull(fragment).descendingSort();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.powerConnectionReceiver != null) {
            unregisterReceiver(this.powerConnectionReceiver);
        }
    }

    @Override
    public void updateToolbarInfo(int size) {
        tvToolbar.setText(getString(R.string.amount_contacts, size));
    }

    public class PowerConnectionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_BATTERY_CHANGED)) {
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
                if (isCharging) {
                    imageCharge.setImageResource(R.mipmap.ic_charging_connected);
                } else {
                    imageCharge.setImageResource(R.mipmap.ic_charging_disconnected);
                }
            }
        }
    }
}