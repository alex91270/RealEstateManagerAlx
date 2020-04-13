package com.example.realestatemanageralx.login;

public class LoginHolder {

    private static final LoginHolder holder = new LoginHolder();

    private boolean isAgentLogged = false;
    private long agentId = 0;

    public static LoginHolder getInstance() {
        return holder;
    }

    public void setIsLogged(boolean logged){
        isAgentLogged = logged;
    }

    public boolean getIsAgentLogged() {
        return isAgentLogged;
    }

    public void setAgentId(long id) {
        agentId = id;
    }

    public long getAgentId() {
        return agentId;
    }

}
