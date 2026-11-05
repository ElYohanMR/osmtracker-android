package net.osmtracker.proposal;

/**
 * Source-level implementation notes for the BJY proposal; no runtime behavior.
 *
 * <p>1. WaypointList.onListItemClick already obtains a waypoint UUID and track ID.
 * A future form entry point should use that selection and verify the waypoint
 * belongs to the intended track. ElementDraft alone does not query or validate it.
 *
 * <p>2. TagButtonOnClickListener.onClick currently generates a new UUID per click.
 * Preserve that existing capture flow. An explicit form mode must instead route
 * attribute edits to a selected draft, without broadcasting a new waypoint.
 *
 * <p>3. GPSLogger handles INTENT_TRACK_WP and calls DataHelper.wayPoint. Do not
 * send that event for every attribute: doing so recreates the original problem.
 *
 * <p>4. DataHelper.updateWayPoint updates name/link, not an arbitrary attribute
 * collection. Persistence and reopening require a separate, reviewed design.
 * No schema change or storage implementation is included in this proposal.
 *
 * <p>5. ExportTrackTask.writeWayPoints serializes existing records. Export of
 * draft attributes requires a specified format and real reader compatibility
 * checks. Merely declaring GPX extensions is not evidence of interoperability.
 *
 * <p>6. Edit a copy of an existing record; cancellation must not mutate it.
 * A process restart loses this in-memory draft until recovery is implemented.
 */
public final class IntegrationNotes {
    private IntegrationNotes() {
        // Documentation-only class; do not instantiate or wire into activities.
    }
}
