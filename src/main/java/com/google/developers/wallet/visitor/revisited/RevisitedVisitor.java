package com.google.developers.wallet.visitor.revisited;

public class RevisitedVisitor {

    public static void main(String[] args) {
        GoogleWalletPass pass = new Loyalty();

    }

    int doSomething(GoogleWalletPass pass) {
        return switch (pass) {
            case Loyalty l when l.size > 10 -> 1;
            case Loyalty l -> 3;
            case EventTicket e -> 2;
        };
    }

}

abstract sealed class GoogleWalletPass permits Loyalty, EventTicket{

}

final class Loyalty extends GoogleWalletPass {

    public int size;
}

final class EventTicket extends  GoogleWalletPass{}