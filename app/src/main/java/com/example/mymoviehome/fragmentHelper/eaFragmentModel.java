package com.example.mymoviehome.fragmentHelper;

import androidx.fragment.app.Fragment;

public class eaFragmentModel {
    /**
     * This class performs base-container collaborative issue.
     * It performs a model to handle stacking operations.
     */
    //represents the fragment is base for the container or not.
    private Boolean isBase;

    //represents the container's name which is belong in.
    private eaStackHelper.Containers containerName;

    //fragment object
    private Fragment fragment;

    public eaFragmentModel(Boolean isBase, eaStackHelper.Containers containerName, Fragment fragment) {
        this.isBase = isBase;
        this.containerName = containerName;
        this.fragment = fragment;
    }

    public Boolean getBase() {
        return isBase;
    }

    public void setBase(Boolean base) {
        isBase = base;
    }

    public eaStackHelper.Containers getContainerName() {
        return containerName;
    }

    public void setContainerName(eaStackHelper.Containers containerName) {
        this.containerName = containerName;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
