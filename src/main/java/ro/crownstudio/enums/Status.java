package ro.crownstudio.enums;

import lombok.Getter;

public enum Status {
    NOT_STARTED(false),
    RUNNING(false),
    PASSED(true),
    FAILED(true),
    SKIPPED(true);

    @Getter
    private final boolean isFinished;

    Status(boolean isFinished) {
        this.isFinished = isFinished;
    }
}
