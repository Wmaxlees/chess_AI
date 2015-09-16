package org.ucdenver.leesw.ai.systems;

/**
 * Created by william.lees on 9/16/15.
 */
public class Event {
    public static final byte EVENT_TYPE_TREE_BUILT = 0;
    public static final byte EVENT_TYPE_NULL       = 1;

    byte eventType;
    Object data;

    public Event(byte eventType) {
        this.eventType = eventType;
    }

    public byte getEventType() {
        return this.eventType;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }
}
