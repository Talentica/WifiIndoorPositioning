package com.talentica.wifiindoorpositioning.wifiindoorpositioning.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.talentica.wifiindoorpositioning.wifiindoorpositioning.R;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.IndoorProject;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by suyashg on 25/08/17.
 */

public class NewProjectActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etProjectName, etProjectDesc;
    private Button btCreate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        etProjectName = findViewById(R.id.et_project_name);
        etProjectDesc = findViewById(R.id.et_project_desc);
        btCreate = findViewById(R.id.bn_project_create);
        btCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btCreate.getId()) {
            final String text = etProjectName.getText().toString().trim();
            final String desc = etProjectDesc.getText().toString().trim();
            if (text.isEmpty()) {
                Snackbar.make(btCreate, "Provide Project Name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                IndoorProject indoorProject = new IndoorProject(new Date(), text, desc);
                // Obtain a Realm instance
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        IndoorProject indoorProject = bgRealm.createObject(IndoorProject.class, UUID.randomUUID().toString());
                        indoorProject.setName(text);
                        indoorProject.setDesc(desc);
                        indoorProject.setCreatedAt(new Date());
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        // Transaction was a success.
                        NewProjectActivity.this.finish();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        // Transaction failed and was automatically canceled.
                        System.out.print(error.getMessage());
                    }
                });
            }
        }
    }
}
