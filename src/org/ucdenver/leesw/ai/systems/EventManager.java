package org.ucdenver.leesw.ai.systems;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by william.lees on 9/16/15.
 */
public class EventManager implements Runnable {

    private static Logger logger = LogManager.getLogger(EventManager.class);

    // Events to be processed
    private Queue<Event> events;

    // Process listener for events
    private ArrayList<EventListener>[] listeners;

    // Singleton Instance
    private static EventManager _singleton;

    public static EventManager getEventManager() {
        if (_singleton == null) {
            _singleton = new EventManager();
        }

        return _singleton;
    }

    private EventManager() {
        // Initialize the event queue
        this.events = new LinkedBlockingQueue<>();

        // Initialize the event listener lists
        this.listeners = new ArrayList[Event.EVENT_TYPE_NULL];
        for (int i = 0; i < Event.EVENT_TYPE_NULL; ++i) {
            listeners[i] = new ArrayList<>();

        }
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addEventListener(EventListener listener, byte eventType) {
        if (!(eventType < Event.EVENT_TYPE_NULL)) {
            logger.error("Attempting to add listener to eventType: {} which doesn't exist", eventType);
        }

        this.listeners[eventType].add(listener);
    }

    public void run() {
        while (events.peek() != null) {
            Event event = events.poll();
            for (EventListener listener : listeners[event.getEventType()]) {
                try {
                    listener.processEvent(event);
                } catch (Exception e) {

                }
            }
        }
    }
}
