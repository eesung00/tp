package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.application.interview.Interview;
import seedu.address.model.application.interview.InterviewDate;
import seedu.address.model.application.interview.InterviewTime;
import seedu.address.model.application.interview.Location;
import seedu.address.model.application.interview.Round;

class JsonAdaptedInterview {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Interview's %s field is missing!";

    private final String round;
    private final String interviewDate;
    private final String interviewTime;
    private final String location;

    /**
     * Constructs a {@code JsonAdaptedInterview} with the given application details.
     */
    @JsonCreator
    public JsonAdaptedInterview(@JsonProperty("round") String round,
                                @JsonProperty("interviewDate") String interviewDate,
                                @JsonProperty("interviewTime") String interviewTime,
                                @JsonProperty("location") String position) {
        this.round = round;
        this.interviewDate = interviewDate;
        this.interviewTime = interviewTime;
        this.location = position;
    }

    /**
     * Converts a given {@code Application} into this class for Jackson use.
     */
    public JsonAdaptedInterview(Interview source) {
        round = source.getRound().value;
        interviewDate = source.getInterviewDate().value.toString();
        interviewTime = source.getInterviewTime().value.toString();
        location = source.getLocation().value;
    }

    @JsonValue
    public String getRound() {
        return round;
    }

    @JsonValue
    public String getInterviewDate() {
        return interviewDate;
    }

    @JsonValue
    public String getInterviewTime() {
        return interviewTime;
    }

    @JsonValue
    public String getLocation() {
        return location;
    }

    /**
     * Converts this Jackson-friendly adapted application object into the model's {@code Application} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted application.
     */
    public Interview toModelType() throws IllegalValueException {
        if (round == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Round.class.getSimpleName()));
        }
        if (!Round.isValidRound(round)) {
            throw new IllegalValueException(Round.MESSAGE_CONSTRAINTS);
        }
        final Round modelRound = new Round(round);

        if (interviewDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    InterviewDate.class.getSimpleName()));
        }
        if (!InterviewDate.isValidDate(interviewDate)) {
            throw new IllegalValueException(InterviewDate.MESSAGE_CONSTRAINTS);
        }
        final InterviewDate modelInterviewDate = new InterviewDate(interviewDate);

        if (interviewTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    InterviewTime.class.getSimpleName()));
        }
        if (!InterviewTime.isValidTime(interviewTime)) {
            throw new IllegalValueException(InterviewTime.MESSAGE_CONSTRAINTS);
        }
        final InterviewTime modelInterviewTime = new InterviewTime(interviewTime);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        return new Interview(modelRound, modelInterviewDate, modelInterviewTime, modelLocation);
    }
}
