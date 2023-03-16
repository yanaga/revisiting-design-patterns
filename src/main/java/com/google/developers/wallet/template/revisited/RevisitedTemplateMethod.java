package com.google.developers.wallet.template.revisited;

import com.google.developers.wallet.PassInformation;
import com.google.developers.wallet.UserProfile;

import java.util.Map;
import java.util.function.Function;

public class RevisitedTemplateMethod {

    public static void main(String[] args) {
        var passInformation = new PassInformation(new UserProfile(true, true, true));
        getGoogleWalletPassCreator().createPass(passInformation);
    }

    public static GoogleWalletPassCreator getGoogleWalletPassCreator() {
        return new GoogleWalletPassCreator(p -> Map.of("eventTicket", "I'm going!"));
    }
}

class GoogleWalletPassCreator {

    private final Function<PassInformation, Map<String, String>> strategy;

    public GoogleWalletPassCreator(Function<PassInformation, Map<String, String>> strategy) {
        this.strategy = strategy;
    }

    protected void authenticate() {
        System.out.println("Authenticating...");
    }

    public void createPass(PassInformation passInformation) {
        authenticate();
        Map<String, String> map = strategy.apply(passInformation);
        System.out.println(signToken(map));
    }

    protected String signToken(Map<String, String> properties) {
        return properties.values().stream().findFirst().get();
    }
}