package com.AS.Student_Attendance.enumDto;

public enum AttendanceStatus {
    PRESENT("present"),
    ABSENT("absent"),
    LATE("late");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    // Convert DB string → Enum
    public static AttendanceStatus fromString(String text) {
        for (AttendanceStatus s : AttendanceStatus.values()) {
            if (s.status.equalsIgnoreCase(text)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid attendance status: " + text);
    }
}

