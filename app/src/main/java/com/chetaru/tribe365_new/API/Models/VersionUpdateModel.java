package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionUpdateModel {
    @SerializedName("versionUpdate")
    @Expose
    private Boolean versionUpdate;
    @SerializedName("storedVersion")
    @Expose
    private String storedVersion;

    public Boolean getVersionUpdate() {
        return versionUpdate;
    }

    public void setVersionUpdate(Boolean versionUpdate) {
        this.versionUpdate = versionUpdate;
    }

    public String getStoredVersion() {
        return storedVersion;
    }

    public void setStoredVersion(String storedVersion) {
        this.storedVersion = storedVersion;
    }
}
