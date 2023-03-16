package com.google.developers.wallet.template.legacy;

import com.google.developers.wallet.PassInformation;
import com.google.developers.wallet.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class LegacyTemplateMethod {

    public static void main(String[] args) {
        var passInformation = new PassInformation(new UserProfile(true, true, true));
        getGoogleWalletPassCreator().createPass(passInformation);
    }

    public static GoogleWalletPassCreator getGoogleWalletPassCreator() {
        return new EventTicketGoogleWalletPassCreator();
    }
}

abstract class GoogleWalletPassCreator {

    protected void authenticate() {
        System.out.println("Authenticating...");
    }

    public void createPass(PassInformation passInformation) {
        authenticate();
        Map<String, String> map = doCreatePass(passInformation);
        System.out.println(signToken(map));
    }

    protected abstract Map<String, String> doCreatePass(PassInformation passInformation);

    protected String signToken(Map<String, String> properties) {
        return properties.values().stream().findFirst().get();
    }
}

class LoyaltyGoogleWalletPassCreator extends GoogleWalletPassCreator {

    @Override
    protected Map<String, String> doCreatePass(PassInformation passInformation) {
        return Map.of("loyalty", "I'm loyal");
    }

}

class EventTicketGoogleWalletPassCreator extends GoogleWalletPassCreator {
    @Override
    protected Map<String, String> doCreatePass(PassInformation passInformation) {
        return Map.of("eventTicket", "I'm going!");
    }

}