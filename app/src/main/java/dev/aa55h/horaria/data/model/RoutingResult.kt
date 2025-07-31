package dev.aa55h.horaria.data.model

import java.time.OffsetDateTime

/**
 * Represents a routing response.
 */
data class RoutingResponse(
    /**
     * The routing query.
     */
    val requestParameters: Map<String, String> = emptyMap(),

    /**
     * Debug statistics.
     */
    val debugOutput: Map<String, Int> = emptyMap(),

    /**
     * The starting place.
     */
    val from: Place? = null,

    /**
     * The destination place.
     */
    val to: Place? = null,

    /**
     * Direct trips by WALK, BIKE, CAR, etc. without time-dependency.
     *
     * The starting time (arriveBy=false) / arrival time (arriveBy=true) is always the queried time parameter
     * (set to "now" if not set). But all direct connections are meant to be independent of absolute times.
     */
    val direct: List<Itinerary> = emptyList(),

    /**
     * List of itineraries.
     */
    val itineraries: List<Itinerary> = emptyList(),

    /**
     * Use the cursor to get the previous page of results. Insert the cursor into the request and post it
     * to get the previous page.
     *
     * The previous page is a set of itineraries departing BEFORE the first itinerary in the result
     * for a depart after search. When using the default sort order, the previous set of itineraries is
     * inserted before the current result.
     */
    val previousPageCursor: String? = null,

    /**
     * Use the cursor to get the next page of results. Insert the cursor into the request and post it
     * to get the next page.
     *
     * The next page is a set of itineraries departing AFTER the last itinerary in this result.
     */
    val nextPageCursor: String? = null
)


/**
 * - NORMAL: latitude / longitude coordinate or address
 * - BIKESHARE: bike sharing station
 * - TRANSIT: transit stop
 */
enum class VertexType {
    NORMAL,
    BIKESHARE,
    TRANSIT
}

/**
 * - NORMAL: entry/exit is possible normally
 * - NOT_ALLOWED: entry/exit is not allowed
 */
enum class PickupDropoffType {
    NORMAL,
    NOT_ALLOWED
}


/**
 * Represents a transit stop, point of interest, or address.
 */
data class Place(
    /**
     * Name of the transit stop / PoI / address
     */
    val name: String,

    /**
     * The ID of the stop. This is often something that users don't care about.
     */
    val stopId: String? = null,

    /**
     * Latitude
     */
    val lat: Double,

    /**
     * Longitude
     */
    val lon: Double,

    /**
     * Level according to OpenStreetMap
     */
    val level: Double,

    /**
     * Arrival time
     */
    val arrival: String? = null, // Use java.time.Instant if parsing datetime

    /**
     * Departure time
     */
    val departure: String? = null,

    /**
     * Scheduled arrival time
     */
    val scheduledArrival: String? = null,

    /**
     * Scheduled departure time
     */
    val scheduledDeparture: String? = null,

    /**
     * Scheduled track from the static schedule timetable dataset
     */
    val scheduledTrack: String? = null,

    /**
     * The current track/platform information, updated with real-time updates if available.
     * Can be missing if neither real-time updates nor the schedule timetable contains track information.
     */
    val track: String? = null,

    /**
     * Description of the location that provides more detailed information
     */
    val description: String? = null,

    /**
     * Vertex type (e.g., NORMAL, BIKESHARE, TRANSIT)
     */
    val vertexType: VertexType? = null,

    /**
     * Type of pickup. It could be disallowed due to schedule, skipped stops or cancellations.
     */
    val pickupType: PickupDropoffType? = null,

    /**
     * Type of dropoff. It could be disallowed due to schedule, skipped stops or cancellations.
     */
    val dropoffType: PickupDropoffType? = null,

    /**
     * Whether this stop is cancelled due to the realtime situation.
     */
    val cancelled: Boolean? = null,

    /**
     * Alerts for this stop.
     */
    val alerts: List<Alert>? = null,

    /**
     * For FLEX transports, the flex location area or location group name
     */
    val flex: String? = null,

    /**
     * For FLEX transports, the flex location area ID or location group ID
     */
    val flexId: String? = null,

    /**
     * Time that on-demand service becomes available
     */
    val flexStartPickupDropOffWindow: String? = null,

    /**
     * Time that on-demand service ends
     */
    val flexEndPickupDropOffWindow: String? = null
)

/**
 * A time interval.
 * The interval is considered active at time `t` if `t` is greater than or equal to the start time
 * and less than the end time.
 */
data class TimeRange(
    /**
     * If missing, the interval starts at minus infinity.
     * If a TimeRange is provided, either start or end must be provided - both fields cannot be empty.
     */
    val start: OffsetDateTime? = null,

    /**
     * If missing, the interval ends at plus infinity.
     * If a TimeRange is provided, either start or end must be provided - both fields cannot be empty.
     */
    val end: OffsetDateTime? = null
)
