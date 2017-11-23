package com.talentica.wifiindoorpositioning.wifiindoorpositioning.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.talentica.wifiindoorpositioning.wifiindoorpositioning.R;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.AccessPoint;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.IndoorProject;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by suyashg on 26/08/17.
 */

public class AddOrEditAccessPointActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addAp, btnScanAP;
    private EditText etName, etDesc, etX, etY, etMAC;
    private String projectId, apID;
    private boolean isEdit = false;
    private AccessPoint apToBeEdited;
    private int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 199;
    private static final int REQ_CODE = 1212;//this is always positive

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_access_point);

        projectId = getIntent().getStringExtra("projectId");
        if(projectId == null) {
            Toast.makeText(this, "Access point not found", Toast.LENGTH_LONG).show();
            this.finish();
        }

        apID = getIntent().getStringExtra("apID");
        initUI();
        if (apID.equals("")) {
            isEdit = false;
        } else {
            isEdit = true;
            addAp.setText("Save");
        }

        if (isEdit)
        setUpEditMode();
    }

    private void setUpEditMode() {
        Realm realm = Realm.getDefaultInstance();
        apToBeEdited = realm.where(AccessPoint.class).equalTo("id", apID).findFirst();
        setValuesToFields(apToBeEdited);
    }

    private void setValuesToFields(AccessPoint accessPoint) {
        etName.setText(accessPoint.getSsid());
        etDesc.setText(accessPoint.getDescription());
        etX.setText(String.valueOf(accessPoint.getX()));
        etY.setText(String.valueOf(accessPoint.getY()));
        etMAC.setText(accessPoint.getMac_address());
    }

    private void initUI() {
        etName = findViewById(R.id.et_ap_name);
        etDesc = findViewById(R.id.et_ap_desc);
        etX = findViewById(R.id.et_ap_x);
        etY = findViewById(R.id.et_ap_y);
        etMAC = findViewById(R.id.et_ap_mac);
        addAp = findViewById(R.id.bn_ap_create);
        addAp.setOnClickListener(this);
        btnScanAP = findViewById(R.id.bn_ap_scan);
        btnScanAP.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == addAp.getId()) {
            final String text = etName.getText().toString().trim();
            final String desc = etDesc.getText().toString().trim();
            final String x = etX.getText().toString().trim();
            final String y = etY.getText().toString().trim();
            final String mac = etMAC.getText().toString().trim();
            final boolean isEditMode = isEdit;

            if (text.isEmpty()) {
                Snackbar.make(addAp, "Provide Access Point Name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                // Obtain a Realm instance
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                IndoorProject project = realm.where(IndoorProject.class).equalTo("id", projectId).findFirst();
                if (isEditMode) {
                    apToBeEdited.setSsid(text);
                    apToBeEdited.setDescription(desc);
                    apToBeEdited.setX(Double.valueOf(x));
                    apToBeEdited.setY(Double.valueOf(y));
                    apToBeEdited.setMac_address(mac);
                } else {
                    AccessPoint accessPoint = realm.createObject(AccessPoint.class, UUID.randomUUID().toString());
                    accessPoint.setBssid(mac);
                    accessPoint.setDescription(desc);
                    accessPoint.setCreatedAt(new Date());
                    accessPoint.setX(Double.valueOf(x));
                    accessPoint.setY(Double.valueOf(y));
                    accessPoint.setSsid(text);
                    accessPoint.setMac_address(mac);
                    project.getAps().add(accessPoint);
                }
                realm.commitTransaction();
                this.finish();
            }
        } else if (view.getId() == btnScanAP.getId()) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
                //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

            } else{
                startSearchWifiActivity();
            }
        }
    }

    private void startSearchWifiActivity() {
        Intent intent = new Intent(this, SearchWifiAccessPointActivity.class);
        startActivityForResult(intent, REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startSearchWifiActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            AccessPoint accessPoint = (AccessPoint) data.getParcelableExtra("accessPoint");
            setValuesToFields(accessPoint);
        }
    }
}
