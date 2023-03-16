package com.google.developers.wallet.chain.legacy;

import com.google.developers.wallet.UserProfile;

public class LegacyChainOfResponsibility {

    public static void main(String[] args) {
        var userProfile = new UserProfile(false, true, false);
        var userNotifier = createNotifier();
        userNotifier.notify(userProfile);
    }

    public static UserNotifier createNotifier() {
        var googleWalletPassUpdateNotifier = new GoogleWalletPassUpdateNotifier();
        var emailNotifier = new EmailNotifier();
        var smsNotifier = new SmsNotifier();

        googleWalletPassUpdateNotifier.setNextNotifier(emailNotifier);
        emailNotifier.setNextNotifier(smsNotifier);

        return googleWalletPassUpdateNotifier;
    }
}

interface UserNotifier {

    void notify(UserProfile profile);
    void setNextNotifier(UserNotifier notifier);

}

abstract class AbstractUserNotifier implements UserNotifier {
    protected UserNotifier nextNotifier;

    @Override
    public void setNextNotifier(UserNotifier notifier) {
        this.nextNotifier = notifier;
    }
}

class GoogleWalletPassUpdateNotifier extends AbstractUserNotifier {
    @Override
    public void notify(UserProfile profile) {
        if (profile.hasGoogleWalletPass()) {
            System.out.println("Google Wallet Pass updated.");
        } else if (nextNotifier != null) {
            nextNotifier.notify(profile);
        } else {
            throw new RuntimeException("No notification sent");
        }
    }
}

class EmailNotifier extends AbstractUserNotifier {
    @Override
    public void notify(UserProfile profile) {
        if (profile.allowsEmail()) {
            System.out.println("Email sent");
        } else if (nextNotifier != null) {
            nextNotifier.notify(profile);
        } else {
            throw new RuntimeException("No notification sent");
        }
    }
}

class SmsNotifier extends AbstractUserNotifier {
    @Override
    public void notify(UserProfile profile) {
        if (profile.allowsEmail()) {
            System.out.println("SMS sent");
        } else if (nextNotifier != null) {
            nextNotifier.notify(profile);
        } else {
            throw new RuntimeException("No notification sent");
        }
    }
}