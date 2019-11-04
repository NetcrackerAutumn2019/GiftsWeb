package com.nectcracker.studyproject.json.userFromVK;

public class UserInfoFromVk{
    private Long id;
    private String first_name;
    private String last_name;
    private boolean is_closed;
    private boolean can_access_closed;
    private String bdate;

    public Long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public boolean isIs_closed() {
        return is_closed;
    }

    public boolean isCan_access_closed() {
        return can_access_closed;
    }

    public String getBirthday() {
        return bdate;
    }
}
