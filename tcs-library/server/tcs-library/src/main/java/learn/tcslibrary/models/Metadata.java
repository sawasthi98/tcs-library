package learn.tcslibrary.models;

import java.util.List;

public class Metadata {
    private String identifier;
    private String titleOfSearch;
    public Metadata(String identifier, String titleOfSearch) {
        this.identifier = identifier;
        this.titleOfSearch = titleOfSearch;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTitleOfSearch() {
        return titleOfSearch;
    }

    public void setTitleOfSearch(String titleOfSearch) {
        this.titleOfSearch = titleOfSearch;
    }
}
