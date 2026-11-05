package net.osmtracker.proposal;

import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

/** Behavioral checks for the in-memory proposal, not end-to-end Android tests. */
public class ElementDraftTest {
    @Test public void differentElementsDoNotShareAttributes() {
        ElementDraft hydrant = new ElementDraft("wp-1", "Hidrante");
        ElementDraft stop = new ElementDraft("wp-2", "Parada");
        hydrant.putAttribute("color", "yellow");
        assertTrue(stop.getAttributes().isEmpty());
        assertEquals("wp-1", hydrant.getWaypointId());
    }

    @Test public void repeatedKeyCorrectsValueWithoutDuplicatingAttribute() {
        ElementDraft draft = new ElementDraft("wp-1", "Hidrante");
        draft.putAttribute("color", "red");
        draft.putAttribute(" color ", "yellow");
        assertEquals(1, draft.getAttributes().size());
        assertEquals("yellow", draft.getAttributes().get("color"));
    }

    @Test public void reviewSnapshotDoesNotChangeAfterAnotherEdit() {
        ElementDraft draft = new ElementDraft("wp-1", "Hidrante");
        draft.putAttribute("color", "red");
        Map<String, String> review = draft.getAttributes();
        draft.putAttribute("color", "yellow");
        assertEquals("red", review.get("color"));
        assertEquals("yellow", draft.getAttributes().get("color"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void callerCannotModifySnapshot() {
        new ElementDraft("wp-1", "Hidrante").getAttributes().put("color", "red");
    }

    @Test public void invalidInputIsRejectedWithoutChangingDraft() {
        ElementDraft draft = new ElementDraft("wp-1", "Hidrante");
        for (String invalid : new String[] {null, "", "   "}) {
            try { new ElementDraft(invalid, "Hidrante"); fail("Invalid ID accepted"); }
            catch (IllegalArgumentException expected) { }
            try { new ElementDraft("wp-1", invalid); fail("Invalid label accepted"); }
            catch (IllegalArgumentException expected) { }
            try { draft.putAttribute(invalid, "red"); fail("Invalid key accepted"); }
            catch (IllegalArgumentException expected) { }
            try { draft.putAttribute("color", invalid); fail("Invalid value accepted"); }
            catch (IllegalArgumentException expected) { }
        }
        assertTrue(draft.getAttributes().isEmpty());
    }
}
