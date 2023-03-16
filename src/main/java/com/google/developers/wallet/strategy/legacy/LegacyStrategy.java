package com.google.developers.wallet.strategy.legacy;

import com.google.developers.wallet.PassInformation;
import com.google.developers.wallet.UserProfile;

public class LegacyStrategy {

     public static void main(String[] args) {
        var endpoint = getEndpoint();
        var passInformation = new PassInformation(new UserProfile(true, true, true));
        String jwt = endpoint.get(passInformation);
        System.out.println(jwt);
    }

    public static GoogleWalletEndpoint getEndpoint() {
        var endpoint = new GoogleWalletEndpoint();
        endpoint.setStrategy(new CompleteJwtAddToGoogleWalletStrategy());
        return endpoint;
    }

}

class GoogleWalletEndpoint {

    private AddToGoogleWalletStrategy strategy;

    public String get(PassInformation passInformation) {
        return strategy.createLink(passInformation);
    }

    public void setStrategy(AddToGoogleWalletStrategy strategy) {
        this.strategy = strategy;
    }
}

interface AddToGoogleWalletStrategy {

    String createLink(PassInformation information);

}

class CompleteJwtAddToGoogleWalletStrategy implements AddToGoogleWalletStrategy {

    @Override
    public String createLink(PassInformation information) {
        return "JWT with complete information.";
    }
}

class PreCreatedJwtAddToGoogleWalletStrategy implements AddToGoogleWalletStrategy {

    @Override
    public String createLink(PassInformation information) {
        return "JWT with just the IDs of the objects.";
    }

}