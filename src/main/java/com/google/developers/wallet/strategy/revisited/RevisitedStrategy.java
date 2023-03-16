package com.google.developers.wallet.strategy.revisited;

import com.google.developers.wallet.PassInformation;
import com.google.developers.wallet.UserProfile;

import java.util.function.Function;

public class RevisitedStrategy {

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
        return strategy.apply(passInformation);
    }

    public void setStrategy(AddToGoogleWalletStrategy strategy) {
        this.strategy = strategy;
    }
}

interface AddToGoogleWalletStrategy extends Function<PassInformation, String> {

}

class CompleteJwtAddToGoogleWalletStrategy implements AddToGoogleWalletStrategy {

    @Override
    public String apply(PassInformation information) {
        return "JWT with complete information.";
    }
}

class PreCreatedJwtAddToGoogleWalletStrategy implements AddToGoogleWalletStrategy {

    @Override
    public String apply(PassInformation information) {
        return "JWT with just the IDs of the objects.";
    }

}