package com.svarian.regusers;

public class ToDo {
    private String id;
    private String name;
    private Boolean status;
    private String login;
    public ToDo() {}
    public ToDo(String id, String name, Boolean status, String login) {
        this.id=id;
        this.name=name;
        this.status=status;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
