package app.universy.account.model;

public class ResendConfirmation implements UsernameContainer {
    private String username;

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
