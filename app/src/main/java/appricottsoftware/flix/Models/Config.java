package appricottsoftware.flix.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {

    private String imageBaseUrl; // Base url for loading images
    private String posterSize; // Poster size to use when fetching images, part of the url
    private String backdropSize; // The backdrop size to use when fetching images

    public Config(JSONObject object) throws JSONException {
        // Get the image base url
        JSONObject images = object.getJSONObject("images");
        this.imageBaseUrl = images.getString("secure_base_url");

        // Get the poster size, use the option at index 3 or w342 as a fallback
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        this.posterSize = posterSizeOptions.optString(3, "w342");

        // Parse the backdrop sizes and use the option at index 1 or w780 as a fallback
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        this.backdropSize = backdropSizeOptions.optString(1, "w780");
    }

    // Helper method for creating urls
    public String getImageUrl(String size, String path) {
        return String.format("%s%s%s", imageBaseUrl, size, path); // Concatenate all three
    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
