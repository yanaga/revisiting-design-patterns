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
        endpoint.setStrategy(pass -> "abc");
        endpoint.setStrategy(new AddToGoogleWalletLink()::complete);
        endpoint.setStrategy(new AddToGoogleWalletLink()::preCreated);
        return endpoint;
    }

}

class GoogleWalletEndpoint {

    private Function<PassInformation, String> strategy;

    public String get(PassInformation passInformation) {
        return strategy.apply(passInformation);
    }

    public void setStrategy(Function<PassInformation, String> strategy) {
        this.strategy = strategy;
    }

}

class AddToGoogleWalletLink {

    public String complete(PassInformation information) {
        return "JWT with complete information.";
    }

    public String preCreated(PassInformation information) {
        return "JWT with just the IDs of the objects.";
    }
}
