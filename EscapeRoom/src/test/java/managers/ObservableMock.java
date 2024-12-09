package managers;

import subscription.Observable;
import subscription.Subscriber;

public class ObservableMock implements Observable {

    Boolean notified = false;

    @Override
    public void subscribe(Subscriber subscriber) {

    }

    @Override
    public void unsubscribe(Subscriber subscriber) {

    }

    @Override
    public void notifySubscribers(String message) {
        this.notified = true;
    }
}
