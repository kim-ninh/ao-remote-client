package com.ninhhk.aoremote.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Remote {
    private long id;
    private String name;
    private int isTemplate;
    private String brand;
    private String deviceType;
    private HashMap<String, String> buttonsCode = new HashMap<>();
    private List<RemoteButton> buttonList = new ArrayList<>();

    // for retrieve data from Remote table
    public Remote(long id, String name, int isTemplate, String brand, String deviceType) {
        this.id = id;
        this.name = name;
        this.isTemplate = isTemplate;
        this.brand = brand;
        this.deviceType = deviceType;
    }

    // for insert new remote to Remote table
    public Remote(String name, int isTemplate, String brand, String deviceType) {
        this(-1, name, isTemplate, brand, deviceType);
    }

    // for user insert custom remote
    public Remote(String deviceType) {
        this(-1, "No name", 0, "Unknown", deviceType);
    }

    public void initButtons(List<RemoteButton> remoteButtons) {
        for (RemoteButton button :
                remoteButtons) {
            buttonsCode.put(button.getName(), button.getCode());
        }
        buttonList = remoteButtons;
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

    public String getButtonCode(String buttonName) {
        return buttonsCode.get(buttonName);
    }

    public void updateButton(String name, String code) {
        buttonsCode.put(name, code);
    }

    public Iterator<Map.Entry<String, String>> getButtonsIterator() {
        return buttonsCode.entrySet().iterator();
    }

    public List<RemoteButton> getButtonList() {
        return buttonList;
    }

    public boolean hasButton(String name) {
        return buttonsCode.containsKey(name);
    }
}
