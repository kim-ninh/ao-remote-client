package com.ninhhk.aoremote.model;

public class RemoteButton {

    private long id;
    private String name;
    private byte[] code;

    public RemoteButton(String name, byte[] code) {
        this(-1, name, code);
    }

    public RemoteButton() {
        this(-1, null, null);
    }

    public RemoteButton(long id, String name, byte[] code) {
        this.id = id;
        this.name = name;
        this.code = code;
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

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] code) {
        this.code = code;
    }
}
