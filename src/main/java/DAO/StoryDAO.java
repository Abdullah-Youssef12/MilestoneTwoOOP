package DAO;

import database.Database;
import entities.issues.Story;

import java.util.ArrayList;
import java.util.List;

public class StoryDAO {

    public static void save(Story story) {
        Database.stories.add(story);
    }

    public static Story findById(String id) {
        for (Story s : Database.stories) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static List<Story> findAll() {
        return new ArrayList<>(Database.stories);
    }
}
