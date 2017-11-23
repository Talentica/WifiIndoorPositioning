package com.talentica.wifiindoorpositioning.wifiindoorpositioning.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.talentica.wifiindoorpositioning.wifiindoorpositioning.R;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.adapter.ReferenceReadingsAdapter;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.AccessPoint;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.IndoorProject;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.ReferencePoint;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.utils.AppContants;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by suyashg on 07/09/17.
 */

public class AddOrEditReferencePointActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "AddOrEditReferencePointActivity";
    private String projectId;

    private RecyclerView rvPoints;
    private LinearLayoutManager layoutManager;
    private EditText etRpName, etRpX, etRpY;
    private Button bnRpSave;

    private ReferenceReadingsAdapter readingsAdapter = new ReferenceReadingsAdapter();
    private List<AccessPoint> apsWithReading = new ArrayList<>();
    private Map<String, List<Integer>> readings = new HashMap<>();
    private Map<String, AccessPoint> aps = new HashMap<>();

    private AvailableAPsReceiver receiverWifi;

    private boolean wifiWasEnabled;
    private WifiManager mainWifi;
    private final Handler handler = new Handler();
    private boolean isCaliberating = false;
    private int readingsCount = 0;
    private boolean isEdit = false;
    private String rpId;
    private ReferencePoint referencePointFromDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reference_point);

        projectId = getIntent().getStringExtra("projectId");
        if (projectId == null) {
            Toast.makeText(this, "Reference point not found", Toast.LENGTH_LONG).show();
            this.finish();
        }

        if (getIntent().getStringExtra("rpId") != null) {
            isEdit = true;
            rpId = getIntent().getStringExtra("rpId");
        }
        initUI();
        Realm realm = Realm.getDefaultInstance();
        if (isEdit) {
            referencePointFromDB = realm.where(ReferencePoint.class).equalTo("id", rpId).findFirst();
            if (referencePointFromDB == null) {
                Toast.makeText(this, "Reference point not found", Toast.LENGTH_LONG).show();
                this.finish();
            }
            RealmList<AccessPoint> readings = referencePointFromDB.getReadings();
            for (AccessPoint ap:readings) {
                readingsAdapter.addAP(ap);
            }
            readingsAdapter.notifyDataSetChanged();
            etRpName.setText(referencePointFromDB.getName());
            etRpX.setText(String.valueOf(referencePointFromDB.getX()));
            etRpY.setText(String.valueOf(referencePointFromDB.getY()));
        } else {
            mainWifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            receiverWifi = new AvailableAPsReceiver();
            wifiWasEnabled = mainWifi.isWifiEnabled();

            IndoorProject project = realm.where(IndoorProject.class).equalTo("id", projectId).findFirst();
            RealmList<AccessPoint> points = project.getAps();
            for (AccessPoint accessPoint : points) {
                aps.put(accessPoint.getMac_address(), accessPoint);
            }
            if (aps.isEmpty()) {
                Toast.makeText(this, "No Access Points Found", Toast.LENGTH_SHORT).show();
            }
            if (!Utils.isLocationEnabled(this)) {
                Toast.makeText(this,"Please turn on the location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        if (!isEdit) {
            registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            Log.v(TAG, "caliberationStarted");
            if (!isCaliberating) {
                isCaliberating = true;
                refresh();
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (!isEdit) {
            unregisterReceiver(receiverWifi);
            isCaliberating = false;
        }
        super.onPause();
    }

    public void refresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainWifi.startScan();
                if (readingsCount < AppContants.READINGS_BATCH) {
                    refresh();
                } else {
                    caliberationCompleted();
                }
            }
        }, AppContants.FETCH_INTERVAL);
    }

    private void caliberationCompleted() {
        isCaliberating = false;
        Log.v(TAG, "caliberationCompleted");
        Map<String, List<Integer>> values = readings;
        Log.v(TAG, "values:"+values.toString());
        for (Map.Entry<String, List<Integer>> entry : values.entrySet()) {
            List<Integer> readingsOfAMac = entry.getValue();
            Double mean = calculateMeanValue(readingsOfAMac);
            Log.v(TAG, "entry.Key:"+entry.getKey()+" aps:"+aps);
            AccessPoint accessPoint = aps.get(entry.getKey());
            AccessPoint updatedPoint = new AccessPoint(accessPoint);
            updatedPoint.setMeanRss(mean);
            apsWithReading.add(updatedPoint);
        }
        readingsAdapter.setReadings(apsWithReading);
        readingsAdapter.notifyDataSetChanged();
        bnRpSave.setEnabled(true);
        bnRpSave.setText("Save");
    }

    private Double calculateMeanValue(List<Integer> readings) {
        if (readings.isEmpty()) {
            return 0.0d;
        }
        Integer sum = 0;
        for (Integer integer : readings) {
            sum = sum + integer;
        }
        double mean = Double.valueOf(sum) / Double.valueOf(readings.size());
        return mean;
    }

    private void initUI() {
        layoutManager = new LinearLayoutManager(this);
        rvPoints = findViewById(R.id.rv_points);
        rvPoints.setLayoutManager(layoutManager);
        rvPoints.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvPoints.setAdapter(readingsAdapter);

        bnRpSave = findViewById(R.id.bn_rp_save);
        bnRpSave.setOnClickListener(this);

        if (!isEdit) {
            bnRpSave.setEnabled(false);
            bnRpSave.setText("Caliberating...");
        } else {
            bnRpSave.setEnabled(true);
            bnRpSave.setText("Save");
        }

        etRpName = findViewById(R.id.et_rp_name);
        etRpX = findViewById(R.id.et_rp_x);
        etRpY = findViewById(R.id.et_rp_y);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == bnRpSave.getId() && !isEdit) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            ReferencePoint referencePoint = new ReferencePoint();
            referencePoint = setValues(referencePoint);
            referencePoint.setCreatedAt(Calendar.getInstance().getTime());
            referencePoint.setDescription("");
//            apsWithReading = realm.copyToRealmOrUpdate(apsWithReading);
            if (referencePoint.getReadings() == null) {
                RealmList<AccessPoint> readings = new RealmList<>();
                readings.addAll(apsWithReading);
                referencePoint.setReadings(readings);
            } else {
                referencePoint.getReadings().addAll(apsWithReading);
            }

            referencePoint.setId(UUID.randomUUID().toString());

            IndoorProject project = realm.where(IndoorProject.class).equalTo("id", projectId).findFirst();
            if (project.getRps() == null) {
                RealmList<ReferencePoint> points = new RealmList<>();
                points.add(referencePoint);
                project.setRps(points);
            } else {
                project.getRps().add(referencePoint);
            }

            realm.commitTransaction();
            Toast.makeText(this,"Reference Point Added", Toast.LENGTH_SHORT).show();
            this.finish();
        } else if (view.getId() == bnRpSave.getId() && isEdit) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            referencePointFromDB = setValues(referencePointFromDB);
            realm.commitTransaction();
            Toast.makeText(this,"Reference Point Updated", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    private ReferencePoint setValues(ReferencePoint referencePoint) {
        String x = etRpX.getText().toString();
        String y = etRpY.getText().toString();
        if (TextUtils.isEmpty(x)) {
            referencePoint.setX(0.0d);
        } else {
            referencePoint.setX(Double.valueOf(x));
        }

        if (TextUtils.isEmpty(y)) {
            referencePoint.setY(0.0d);
        } else {
            referencePoint.setY(Double.valueOf(y));
        }
        referencePoint.setLocId(referencePoint.getX() + " " + referencePoint.getY());
        referencePoint.setName(etRpName.getText().toString());
        return referencePoint;
    }

    class AvailableAPsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> scanResults = mainWifi.getScanResults();
            ++readingsCount;
            for (Map.Entry<String, AccessPoint> entry : aps.entrySet()) {
                String apMac = entry.getKey();
                for (ScanResult scanResult : scanResults) {
                    if (entry.getKey().equals(scanResult.BSSID)) {
                        checkAndAddApRSS(apMac, scanResult.level);
                        apMac = null;//do this after always :|
                        break;
                    }
                }
                if (apMac != null) {
                    checkAndAddApRSS(apMac, AppContants.NaN.intValue());
                }
            }
//            results.put(Calendar.getInstance(), map);

            Log.v(TAG, "Count:" + readingsCount+" scanResult:"+ scanResults.toString()+" aps:"+aps.toString());
            for (int i = 0; i < readingsCount; ++i) {
//                Log.v(TAG, "  BSSID       =" + results.get(i).BSSID);
//                Log.v(TAG, "  SSID        =" + results.get(i).SSID);
//                Log.v(TAG, "  Capabilities=" + results.get(i).capabilities);
//                Log.v(TAG, "  Frequency   =" + results.get(i).frequency);
//                Log.v(TAG, "  Level       =" + results.get(i).level);
//                Log.v(TAG, "---------------");
            }
        }
    }

    private void checkAndAddApRSS(String apMac, Integer level) {
        if (readings.containsKey(apMac)) {
            List<Integer> integers = readings.get(apMac);
            integers.add(level);
        } else {
            List<Integer> integers = new ArrayList<>();
            integers.add(level);
            readings.put(apMac, integers);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!wifiWasEnabled && !isEdit) {
            mainWifi.setWifiEnabled(false);
        }
    }
}
