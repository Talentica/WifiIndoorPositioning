package com.talentica.wifiindoorpositioning.wifiindoorpositioning.model;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by suyashg on 25/08/17.
 */

public class IndoorProject extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private Date createdAt = new Date();
    private String name;
    private String desc;
    private RealmList<AccessPoint> aps;
    private RealmList<ReferencePoint> rps;

    public IndoorProject() {
    }

    public IndoorProject(Date createdAt, String name, String desc) {
        this.createdAt = createdAt;
        this.name = name;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<AccessPoint> getAps() {
        return aps;
    }

    public void setAps(RealmList<AccessPoint> aps) {
        this.aps = aps;
    }

    public RealmList<ReferencePoint> getRps() {
        return rps;
    }

    public void setRps(RealmList<ReferencePoint> rps) {
        this.rps = rps;
    }
}
