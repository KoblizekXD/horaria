@file:Suppress("unused")

package dev.aa55h.horaria.data.model

import dev.aa55h.horaria.R

/**
 * Represents a full journey itinerary, composed of multiple legs and timing details.
 */
data class Itinerary(
    /**
     * Journey duration in seconds.
     */
    val duration: Long,

    /**
     * Journey departure time.
     */
    val startTime: String,

    /**
     * Journey arrival time.
     */
    val endTime: String,

    /**
     * The number of transfers this trip has.
     */
    val transfers: Int,

    /**
     * Journey legs.
     */
    val legs: List<Leg>,

    /**
     * Fare information.
     */
    val fareTransfers: List<FareTransfer>? = null
)

data class Leg(
    /**
     * Transport mode for this leg.
     */
    val mode: Mode,

    /**
     * Leg departure time.
     */
    val startTime: String,

    /**
     * Leg arrival time.
     */
    val endTime: String,

    /**
     * Scheduled leg departure time.
     */
    val scheduledStartTime: String,

    /**
     * Scheduled leg arrival time.
     */
    val scheduledEndTime: String,

    /**
     * Whether there is real-time data about this leg.
     */
    val realTime: Boolean,

    /**
     * Whether this leg was originally scheduled to run or is an additional service.
     * Scheduled times will equal realtime times in this case.
     */
    val scheduled: Boolean,

    /**
     * Leg duration in seconds.
     *
     * If leg is footpath:
     * The footpath duration is derived from the default footpath
     * duration using the query parameters `transferTimeFactor` and
     * `additionalTransferTime` as follows:
     * `leg.duration = defaultDuration * transferTimeFactor + additionalTransferTime.`
     * In case the defaultDuration is needed, it can be calculated by
     * `defaultDuration = (leg.duration - additionalTransferTime) / transferTimeFactor`.
     * Note that the default values are `transferTimeFactor = 1` and
     * `additionalTransferTime = 0` in case they are not explicitly
     * provided in the query.
     */
    val duration: Long,

    /**
     * Starting place of the leg.
     */
    val from: Place,

    /**
     * Ending place of the leg.
     */
    val to: Place,

    /**
     * Encoded polyline geometry of the leg.
     */
    val legGeometry: EncodedPolyline,

    /**
     * For non-transit legs the distance traveled while traversing this leg in meters.
     */
    val distance: Double? = null,

    /**
     * For transit legs, if the rider should stay on the vehicle as it changes route names.
     */
    val interlineWithPreviousLeg: Boolean? = null,

    /**
     * For transit legs, the headsign of the bus or train being used.
     * For non-transit legs, null.
     */
    val headsign: String? = null,

    val routeColor: String? = null,

    val routeTextColor: String? = null,

    val routeType: String? = null,

    val agencyName: String? = null,

    val agencyUrl: String? = null,

    val agencyId: String? = null,

    val tripId: String? = null,

    val routeShortName: String? = null,

    /**
     * Whether this trip is cancelled.
     */
    val cancelled: Boolean? = null,

    /**
     * Filename and line number where this trip is from.
     */
    val source: String? = null,

    /**
     * For transit legs, intermediate stops between the Place where the leg originates
     * and the Place where the leg ends. For non-transit legs, null.
     */
    val intermediateStops: List<Place>? = null,

    /**
     * A series of turn by turn instructions
     * used for walking, biking and driving.
     */
    val steps: List<StepInstruction>? = null,

    val rental: Rental? = null,

    /**
     * Index into `Itinerary.fareTransfers` array
     * to identify which fare transfer this leg belongs to.
     */
    val fareTransferIndex: Int? = null,

    /**
     * Index into the `Itinerary.fareTransfers[fareTransferIndex].effectiveFareLegProducts` array
     * to identify which effective fare leg this itinerary leg belongs to.
     */
    val effectiveFareLegIndex: Int? = null,

    /**
     * Alerts for this stop.
     */
    val alerts: List<Alert>? = null,

    /**
     * If set, this attribute indicates that this trip has been expanded
     * beyond the feed end date (enabled by config flag `timetable.dataset.extend_calendar`)
     * by looping active weekdays, e.g. from calendar.txt in GTFS.
     */
    val loopedCalendarSince: String? = null
)

/**
 * A segment of a route with detailed navigation instructions.
 */
data class StepInstruction(
    /** Level where this segment starts, based on OpenStreetMap data */
    val fromLevel: Int,

    /** Level where this segment ends, based on OpenStreetMap data */
    val toLevel: Int,

    /** Encoded polyline representing this segment of the path */
    val polyline: EncodedPolyline,

    /** Relative direction of this navigation step */
    val relativeDirection: Direction,

    /** The distance in meters that this step takes */
    val distance: Double,

    /** The name of the street */
    val streetName: String,

    /**
     * Not implemented!
     * When exiting a highway or traffic circle, the exit name/number
     */
    val exit: String,

    /**
     * Not implemented!
     * Indicates whether a street changes direction at an intersection
     */
    val stayOn: Boolean,

    /**
     * Not implemented!
     * This step is on an open area such as a plaza or train platform
     */
    val area: Boolean,

    /** OpenStreetMap way index */
    val osmWay: Int? = null,

    /** Indicates that a fee must be paid to use this segment */
    val toll: Boolean? = null,

    /**
     * Experimental. Indicates whether access is restricted.
     * See: https://wiki.openstreetmap.org/wiki/Conditional_restrictions
     */
    val accessRestriction: String? = null,

    /** Incline in meters across this path segment */
    val elevationUp: Int? = null,

    /** Decline in meters across this path segment */
    val elevationDown: Int? = null
)

/**
 * Represents an encoded polyline.
 * Define this class according to your actual polyline data structure.
 */
data class EncodedPolyline(
    val points: String
)

/**
 * Describes the relative direction for a navigation step.
 */
enum class Direction {
    // Define possible values here (not provided in your schema)
}

/**
 * Describes the physical form of a rental vehicle.
 */
enum class RentalFormFactor {
    BICYCLE,
    CARGO_BICYCLE,
    CAR,
    MOPED,
    SCOOTER_STANDING,
    SCOOTER_SEATED,
    OTHER
}

/**
 * Describes how a rental vehicle is powered.
 */
enum class RentalPropulsionType {
    HUMAN,
    ELECTRIC_ASSIST,
    ELECTRIC,
    COMBUSTION,
    COMBUSTION_DIESEL,
    HYBRID,
    PLUG_IN_HYBRID,
    HYDROGEN_FUEL_CELL
}

/**
 * Describes the rules about where a rented vehicle can be returned.
 */
enum class RentalReturnConstraint {
    NONE,
    ANY_STATION,
    ROUNDTRIP_STATION
}

/**
 * Vehicle rental metadata.
 */
data class Rental(
    /** Vehicle share system ID */
    val systemId: String,

    /** Vehicle share system name */
    val systemName: String? = null,

    /** URL of the vehicle share system */
    val url: String? = null,

    /** Name of the station */
    val stationName: String? = null,

    /** Name of the station where the vehicle is picked up (empty for free floating vehicles) */
    val fromStationName: String? = null,

    /** Name of the station where the vehicle is returned (empty for free floating vehicles) */
    val toStationName: String? = null,

    /** Rental URI for Android (deep link to the specific station or vehicle) */
    val rentalUriAndroid: String? = null,

    /** Rental URI for iOS (deep link to the specific station or vehicle) */
    val rentalUriIOS: String? = null,

    /** Rental URI for web (deep link to the specific station or vehicle) */
    val rentalUriWeb: String? = null,

    /** Physical form of the rental vehicle */
    val formFactor: RentalFormFactor? = null,

    /** Type of propulsion used by the rental vehicle */
    val propulsionType: RentalPropulsionType? = null,

    /** Rules for returning the rented vehicle */
    val returnConstraint: RentalReturnConstraint? = null
)

/**
 * Modes of transport including street and transit options.
 */
enum class Mode {
    // === Street Modes ===

    /** Walking mode */
    WALK,

    /** Biking mode */
    BIKE,

    /**
     * Experimental.
     * Rental vehicle (e.g. bike/scooter rental). Expect unannounced breaking changes.
     */
    RENTAL,

    /** Personal car travel */
    CAR,

    /**
     * Experimental.
     * Parking a car. Expect unannounced breaking changes.
     */
    CAR_PARKING,

    /**
     * Experimental.
     * Car dropoff (e.g. kiss & ride). Expect unannounced breaking changes.
     */
    CAR_DROPOFF,

    /**
     * On-demand mobility (e.g., taxis from the Prima+ÖV project)
     */
    ODM,

    /**
     * Flexible transport services (e.g., dial-a-ride)
     */
    FLEX,

    // === Transit Modes ===

    /**
     * Aggregated transit mode representing multiple transport types:
     * `RAIL`, `TRAM`, `BUS`, `FERRY`, `AIRPLANE`, `COACH`, `CABLE_CAR`, `FUNICULAR`, `AREAL_LIFT`, `OTHER`
     */
    TRANSIT,

    /** Tram vehicles */
    TRAM,

    /** Subway/underground metro systems */
    SUBWAY,

    /** Ferry services */
    FERRY,

    /** Airplane or airline flights */
    AIRPLANE,

    /** Metro systems (may overlap with subway) */
    METRO,

    /** Short distance buses (excludes long-distance coaches) */
    BUS,

    /** Long-distance coaches (excludes local buses) */
    COACH,

    /**
     * Aggregated rail mode:
     * `HIGHSPEED_RAIL`, `LONG_DISTANCE`, `NIGHT_RAIL`, `REGIONAL_RAIL`, `REGIONAL_FAST_RAIL`, `METRO`, `SUBWAY`
     */
    RAIL,

    /** Long-distance high-speed trains (e.g., TGV, Shinkansen) */
    HIGHSPEED_RAIL,

    /** Long-distance intercity rail services */
    LONG_DISTANCE,

    /** Long-distance night trains */
    NIGHT_RAIL,

    /**
     * Regional express rail that skips low-traffic stops for speed
     */
    REGIONAL_FAST_RAIL,

    /** Regular regional rail services */
    REGIONAL_RAIL,

    /**
     * Cable tram — street-level rail vehicles powered by underground cable
     * (e.g., San Francisco cable car)
     */
    CABLE_CAR,

    /**
     * Funicular — rail transport designed for steep inclines
     */
    FUNICULAR,

    /**
     * Aerial lift (gondola, cable car) where cabins are suspended by cables
     */
    AREAL_LIFT,

    /** Other/unspecified mode */
    OTHER;

    fun pretty(): String {
        return when (this) {
            WALK -> "Walk"
            BIKE -> "Bike"
            RENTAL -> "Rental Vehicle"
            CAR -> "Car"
            CAR_PARKING -> "Car Parking"
            CAR_DROPOFF -> "Car Dropoff"
            ODM -> "On-Demand Mobility"
            FLEX -> "Flexible Transport"
            TRANSIT -> "Transit"
            TRAM -> "Tram"
            SUBWAY -> "Subway"
            FERRY -> "Ferry"
            AIRPLANE -> "Airplane"
            METRO -> "Metro"
            BUS -> "Bus"
            COACH -> "Coach"
            RAIL -> "Rail"
            HIGHSPEED_RAIL -> "High-Speed Rail"
            LONG_DISTANCE -> "Long-Distance Rail"
            NIGHT_RAIL -> "Night Rail"
            REGIONAL_FAST_RAIL -> "Regional Fast Rail"
            REGIONAL_RAIL -> "Regional Rail"
            CABLE_CAR -> "Cable Car"
            FUNICULAR -> "Funicular"
            AREAL_LIFT -> "Aerial Lift"
            OTHER -> "Other Transport Mode"
        }
    }

    fun icon(): Int {
        return when (this) {
            WALK -> R.drawable.ic_direction_walk
            CAR -> R.drawable.ic_direction_car
            TRAM -> R.drawable.ic_direction_tram
            SUBWAY -> R.drawable.ic_direction_subway
            METRO -> R.drawable.ic_direction_subway
            BUS -> R.drawable.ic_direction_bus
            COACH -> R.drawable.ic_direction_bus
            NIGHT_RAIL, RAIL -> R.drawable.ic_train
            REGIONAL_FAST_RAIL,
            REGIONAL_RAIL,
            LONG_DISTANCE,
            HIGHSPEED_RAIL -> R.drawable.ic_direction_railway
            else -> R.drawable.ic_direction
        }
    }
}

/**
 * A rider category with an optional eligibility page.
 */
data class RiderCategory(
    /** Name of the rider category as shown to the rider. */
    val riderCategoryName: String,

    /** Whether this is the default fare category. */
    val isDefaultFareCategory: Boolean,

    /** Optional URL with eligibility information for this rider category. */
    val eligibilityUrl: String? = null
)

/**
 * Enum representing types of fare media.
 */
enum class FareMediaType {
    /** No fare media involved (e.g., cash payment) */
    NONE,

    /** Physical paper ticket */
    PAPER_TICKET,

    /** Physical transit card with stored value */
    TRANSIT_CARD,

    /** Contactless EMV (e.g., credit card tap-to-pay) */
    CONTACTLESS_EMV,

    /** Mobile app with virtual tickets or passes */
    MOBILE_APP
}

/**
 * A medium through which a fare is paid.
 */
data class FareMedia(
    /** The type of fare media. */
    val fareMediaType: FareMediaType,

    /** Name of the fare media (required for some media types). */
    val fareMediaName: String? = null
)

/**
 * A fare product that can be purchased by a rider.
 */
data class FareProduct(
    /** Display name of the fare product. */
    val name: String,

    /** Cost of the product (may be negative for discounts, or zero if free). */
    val amount: Double,

    /** ISO 4217 currency code. */
    val currency: String,

    /** Optional category of rider this fare applies to. */
    val riderCategory: RiderCategory? = null,

    /** Optional medium through which this fare is available. */
    val media: FareMedia? = null
)

/**
 * Rule for how fare products are applied across effective fare legs and transfers.
 */
enum class FareTransferRule {
    /**
     * A_AB: First leg fare + transfer product.
     * All following leg fares are ignored.
     */
    A_AB,

    /**
     * A_AB_B: Each leg fare must be purchased + transfer product.
     */
    A_AB_B,

    /**
     * AB: Only the transfer product is needed.
     * All leg fares are ignored.
     */
    AB
}

/**
 * Represents a group of fare legs and the transfer product that joins them.
 */
data class FareTransfer(
    /**
     * The rule used to compute the total fare from leg fares and transfer product.
     */
    val rule: FareTransferRule? = null,

    /**
     * A list of transfer products (can be free).
     */
    val transferProducts: List<FareProduct>? = null,

    /**
     * For each effective fare leg, provides multiple fare product options.
     *
     * This is a 3-level structure:
     * - Outer list: all effective fare legs (AND)
     * - Middle list: fare options per leg (OR)
     * - Inner list: actual fare product variants
     */
    val effectiveFareLegProducts: List<List<List<FareProduct>>>
)
