package com.uqam.latece.harissa.models;

import java.util.List;

public class Manifest {

    private final String packageName;
    private final int minSDK;
    private final int maxSDK;
    private final int targettedSDK;
    private final String applicationName;
    private final List<String> usedPermissions;
    private final List<String> activities;
    private final List<String> services;
    private final List<String> receivers;

    private Manifest(Builder builder)
    {
        this.packageName = builder.packageName;
        this.minSDK = builder.minSDK;
        this.maxSDK = builder.maxSDK;
        this.targettedSDK = builder.targettedSDK;
        this.applicationName = builder.applicationName;
        this.usedPermissions = builder.usedPermissions;
        this.activities = builder.activities;
        this.services = builder.services;
        this.receivers = builder.receivers;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getMinSDK() {
        return minSDK;
    }

    public int getMaxSDK() {
        return maxSDK;
    }

    public int getTargettedSDK() {
        return targettedSDK;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public List<String> getUsedPermissions() {
        return usedPermissions;
    }

    public List<String> getActivities() {
        return activities;
    }

    public List<String> getServices() {
        return services;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public static class Builder {
        private String packageName;
        private int minSDK;
        private int maxSDK;
        private int targettedSDK;
        private String applicationName;
        private List<String> usedPermissions;
        private List<String> activities;
        private List<String> services;
        private List<String> receivers;

        public Builder packageName(final String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder minSdk(final int minSDK) {
            this.minSDK = minSDK;
            return this;
        }

        public Builder maxSdk(final int maxSDK) {
            this.maxSDK = maxSDK;
            return this;
        }

        public Builder targettedSDK(final int targettedSDK) {
            this.targettedSDK = targettedSDK;
            return this;
        }

        public Builder applicationName(final String applicationName) {
            this.applicationName = applicationName;
            return this;
        }

        public Builder usedPermissions(final List<String> usedPermissions){
            this.usedPermissions = usedPermissions;
            return this;
        }

        public Builder activities(final List<String> activities){
            this.activities = activities;
            return this;
        }

        public Builder services(final List<String> services){
            this.services = services;
            return this;
        }

        public Builder receivers(final List<String> receivers){
            this.receivers = receivers;
            return this;
        }

        public Manifest build() {
            return new Manifest(this);
        }
    }
}
