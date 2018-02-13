package appricottsoftware.flix.Models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Genre {

    String genre;

    public Genre() { /* Empty constructor required for Parceler */ }

    public Genre(JSONObject object) throws JSONException {
        this.genre = object.getString("genre");
    }

    public String getGenre() {
        return genre;
    }
}
