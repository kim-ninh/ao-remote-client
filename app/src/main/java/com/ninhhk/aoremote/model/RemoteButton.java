package com.ninhhk.aoremote.model;

public class RemoteButton {

    private long id;
    private String name;
    private String code;
    private long remote;

    // for retrieve data from Button table
    public RemoteButton(long id, String name, String code, long remote) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.remote = remote;
    }

    // For insert new Button to Button table
    public RemoteButton(String name, String code, long remote) {
        this(-1, name, code, remote);
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getRemote() {
        return remote;
    }

    public void setRemote(long remote) {
        this.remote = remote;
    }
}
