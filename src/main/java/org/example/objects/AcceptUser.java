package org.example.objects;

import lombok.Getter;
import lombok.Setter;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AcceptUser {
    @Getter
    @Setter
    private String success;

    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private String accessToken;

    @Getter
    @Setter
    private String refreshToken;

    @Getter
    @Setter
    private String message;

    public AcceptUser() { }

    public AcceptUser(String success, User user, String accessToken, String refreshToken) {
        this.success = success;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public AcceptUser(String success, String message) {
        this.success = success;
        this.message = message;
    }
}

