package com.uqam.latece.harissa.factory.loaders;

import java.util.List;

public class LoaderConfiguration {

    private String androidPlatformPath;
    private List<String> excludedPackages;
    private List<String> includedPackages;

    private LoaderConfiguration(Builder builder)
    {
        this.androidPlatformPath = builder.androidPlatformPath;
        this.excludedPackages    = builder.excludedPackages;
        this.includedPackages    = builder.includedPackages;
    }

    public String getAndroidPlatformPath() {
        return androidPlatformPath;
    }

    public List<String> getExcludedPackages() {
        return excludedPackages;
    }

    public List<String> getIncludedPackages() {
        return includedPackages;
    }

    public static class Builder
    {
        private String androidPlatformPath;
        private List<String> excludedPackages;
        private List<String> includedPackages;

        public Builder androidPlatformPath(String androidPlatformPath)
        {
            this.androidPlatformPath = androidPlatformPath;
            return this;
        }

        public Builder excludedPackages(List<String> excludedPackages)
        {
            this.excludedPackages = excludedPackages;
            return this;
        }

        public Builder includedPackages(List<String> includedPackages)
        {
            this.includedPackages = includedPackages;
            return this;
        }

        public LoaderConfiguration build()
        {
            return new LoaderConfiguration(this);
        }
    }
}
