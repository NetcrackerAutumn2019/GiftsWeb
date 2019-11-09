package com.nectcracker.studyproject.json.friendsFromVK;

import java.util.List;
import java.util.Set;

public class Response {
    private int count;
    private Set<Nickname> items;

    public int getCount() {
        return count;
    }

    public Set<Nickname> getItems() {
        return items;
    }
}
