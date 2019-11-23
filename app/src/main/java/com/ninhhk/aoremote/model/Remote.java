package com.ninhhk.aoremote.model;

import java.util.HashMap;
import java.util.List;

public class Remote {
    private long id;
    private String name;
    private int isTemplate;
    private String brand;
    private String deviceType;
    private HashMap<String, byte[]> buttonsCode = new HashMap<>();

    public Remote(long id, String name, int isTemplate, String brand, String deviceType) {
        this.id = id;
        this.name = name;
        this.isTemplate = isTemplate;
        this.brand = brand;
        this.deviceType = deviceType;
    }


    public void initButtons(List<RemoteButton> remoteButtons) {
        for (RemoteButton button :
                remoteButtons) {
            buttonsCode.put(button.getName(), button.getCode());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(int isTemplate) {
        this.isTemplate = isTemplate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public byte[] getButtonCode(String buttonName) {
        return buttonsCode.get(buttonName);
    }
}
