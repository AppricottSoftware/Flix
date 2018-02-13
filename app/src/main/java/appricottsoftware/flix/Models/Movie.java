package appricottsoftware.flix.Models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Movie {

    String title;
    String overview;
    String posterPath;
    String backdropPath;
    Double voteAverage;
    Integer id;
    String releaseDate;

    public Movie() { /* Empty constructor required for Parceler */ }

    public Movie(JSONObject object) throws JSONException {
        this.title = object.getString("title");
        this.overview = object.getString("overview");
        this.posterPath = object.getString("poster_path");
        this.backdropPath = object.getString("backdrop_path");
        this.voteAverage = object.getDouble("vote_average");
        this.id = object.getInt("id");
        this.releaseDate = object.getString("release_date");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Integer getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
