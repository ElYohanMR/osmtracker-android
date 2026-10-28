package net.osmtracker.proposal;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/*
Experimental BJY proposal: attributes describing one selected waypoint.

This in-memory draft is not connected to capture, storage or GPX export.
A future integration must resolve the existing waypoint ID and define how
drafts survive cancellation, process death and reopening the application.
*/
public final class ElementDraft {

    private final String waypointId;
    private final String label;
    private final Map<String, String> attributes = new LinkedHashMap<>();

    /** Creates an independent draft referencing an existing waypoint. */
    public ElementDraft(String waypointId, String label) {
        this.waypointId = requireText(waypointId, "waypointId");
        this.label = requireText(label, "label");
    }

    public String getWaypointId() {
        return waypointId;
    }

    public String getLabel() {
        return label;
    }

    /** Adds an attribute, replacing the previous value for the same key. */
    public void putAttribute(String key, String value) {
        attributes.put(requireText(key, "key"), requireText(value, "value"));
    }

    /** Returns a read-only snapshot, independent of subsequent edits. */
    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(attributes));
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
