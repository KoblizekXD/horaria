package dev.aa55h.horaria.data.model

/**
 * An alert, indicating some sort of incident in the public transit network.
 */
data class Alert(
    /**
     * Time when the alert should be shown to the user.
     * If missing, the alert will be shown as long as it appears in the feed.
     * If multiple ranges are given, the alert will be shown during all of them.
     */
    val communicationPeriod: List<TimeRange>? = null,

    /**
     * Time when the services are affected by the disruption mentioned in the alert.
     */
    val impactPeriod: List<TimeRange>? = null,

    /**
     * Broad cause of the alert.
     */
    val cause: AlertCause? = null,

    /**
     * Description of the cause of the alert that allows for agency-specific language;
     * more specific than the Cause.
     */
    val causeDetail: String? = null,

    /**
     * Broad effect of the alert.
     */
    val effect: AlertEffect? = null,

    /**
     * Description of the effect of the alert that allows for agency-specific language;
     * more specific than the Effect.
     */
    val effectDetail: String? = null,

    /**
     * The URL which provides additional information about the alert.
     */
    val url: String? = null,

    /**
     * Header for the alert. This plain-text string will be highlighted, for example in boldface.
     */
    val headerText: String,

    /**
     * Description for the alert.
     * This plain-text string will be formatted as the body of the alert (or shown on an explicit "expand" request by the user).
     * The information in the description should add to the information of the header.
     */
    val descriptionText: String,

    /**
     * Text containing the alert's header to be used for text-to-speech implementations.
     * This field is the text-to-speech version of headerText.
     * It should contain the same information as headerText but formatted such that it can be read as text-to-speech
     * (for example, abbreviations removed, numbers spelled out, etc.)
     */
    val ttsHeaderText: String? = null,

    /**
     * Text containing a description for the alert to be used for text-to-speech implementations.
     * This field is the text-to-speech version of descriptionText.
     * It should contain the same information as descriptionText but formatted such that it can be read as text-to-speech
     * (for example, abbreviations removed, numbers spelled out, etc.)
     */
    val ttsDescriptionText: String? = null,

    /**
     * Severity of the alert.
     */
    val severityLevel: AlertSeverityLevel? = null,

    /**
     * String containing a URL linking to an image.
     */
    val imageUrl: String? = null,

    /**
     * IANA media type as to specify the type of image to be displayed.
     * The type must start with "image/"
     */
    val imageMediaType: String? = null,

    /**
     * Text describing the appearance of the linked image in the image field
     * (e.g., in case the image can't be displayed or the user can't see the image for accessibility reasons).
     * See the HTML spec for alt image text.
     */
    val imageAlternativeText: String? = null
)

/**
 * Cause of this alert.
 */
enum class AlertCause {
    UNKNOWN_CAUSE,
    OTHER_CAUSE,
    TECHNICAL_PROBLEM,
    STRIKE,
    DEMONSTRATION,
    ACCIDENT,
    HOLIDAY,
    WEATHER,
    MAINTENANCE,
    CONSTRUCTION,
    POLICE_ACTIVITY,
    MEDICAL_EMERGENCY
}

/**
 * The effect of this problem on the affected entity.
 */
enum class AlertEffect {
    NO_SERVICE,
    REDUCED_SERVICE,
    SIGNIFICANT_DELAYS,
    DETOUR,
    ADDITIONAL_SERVICE,
    MODIFIED_SERVICE,
    OTHER_EFFECT,
    UNKNOWN_EFFECT,
    STOP_MOVED,
    NO_EFFECT,
    ACCESSIBILITY_ISSUE
}

/**
 * The severity of the alert.
 */
enum class AlertSeverityLevel {
    UNKNOWN_SEVERITY,
    INFO,
    WARNING,
    SEVERE
}
