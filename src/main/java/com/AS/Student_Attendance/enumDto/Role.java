package com.AS.Student_Attendance.enumDto;



public enum Role {
    ADMIN("Admin"),
    TEACHER("Teacher"),
    STUDENT("Student");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    // Convert from string (e.g., database value) to enum
    public static Role fromString(String text) {
        for (Role r : Role.values()) {
            if (r.roleName.equalsIgnoreCase(text)) {
                return r;
            }
        }
        throw new IllegalArgumentException("No enum constant for role: " + text);
    }
}