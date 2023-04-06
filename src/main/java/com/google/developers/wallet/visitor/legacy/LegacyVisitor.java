package com.google.developers.wallet.visitor.legacy;

public class LegacyVisitor {

    public static void main(String[] args) {
        GoogleWalletPass card = new LoyaltyCard();
        card.accept(new Visitor<Object>() {
            @Override
            public Object visit(LoyaltyCard loyaltyCard) {
                return null;
            }

            @Override
            public Object visit(EventTicket eventTicket) {
                return null;
            }
        });
    }

}

interface Visitor<T> {
    T visit(LoyaltyCard loyaltyCard);

    T visit(EventTicket eventTicket);

}

abstract class GoogleWalletPass {

    public abstract <T> T accept(Visitor<T> visitor);

}

class LoyaltyCard extends GoogleWalletPass {

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}

class EventTicket extends GoogleWalletPass {

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}