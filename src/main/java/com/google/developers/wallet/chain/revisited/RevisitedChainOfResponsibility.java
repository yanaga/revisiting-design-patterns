package com.google.developers.wallet.chain.revisited;

import com.google.developers.wallet.UserProfile;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class RevisitedChainOfResponsibility {

    public static void main(String[] args) {
        var userProfile = new UserProfile(false, true, false);
        Stream.of(
                        new GoogleWalletPassUpdateNotifier(),
                        new SmsNotifier(),
                        new EmailNotifier()
                )
                .filter(n -> n.test(userProfile))
                .findFirst()
                .ifPresent(n -> n.accept(userProfile));

        Stream.of(new UserNotifier(UserProfile::allowsSms, u -> System.out.println("SMS sent")))
                .filter(u -> u.predicate().test(userProfile))
                .findFirst()
                .ifPresent(u -> u.consumer().accept(userProfile));
    }

}

record UserNotifier(Predicate<UserProfile> predicate, Consumer<UserProfile> consumer) {

}

class GoogleWalletPassUpdateNotifier implements Consumer<UserProfile>, Predicate<UserProfile> {
    @Override
    public void accept(UserProfile profile) {
        System.out.println("Google Wallet Pass updated.");
    }

    @Override
    public boolean test(UserProfile profile) {
        return profile.hasGoogleWalletPass();
    }
}

class EmailNotifier implements Consumer<UserProfile>, Predicate<UserProfile> {
    @Override
    public void accept(UserProfile profile) {
        System.out.println("Email sent.");
    }

    @Override
    public boolean test(UserProfile profile) {
        return profile.allowsEmail();
    }
}

class SmsNotifier implements Consumer<UserProfile>, Predicate<UserProfile> {
    @Override
    public void accept(UserProfile profile) {
        System.out.println("SMS sent.");
    }

    @Override
    public boolean test(UserProfile userProfile) {
        return userProfile.allowsSms();
    }
}