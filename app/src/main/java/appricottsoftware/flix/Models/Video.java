package appricottsoftware.flix.Models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Video {

    String site;
    String key;

    public Video() { /* Empty constructor required for Parceler */ }

    public Video(JSONObject object) throws JSONException {
        this.site = object.getString("site");
        this.key = object.getString("key");
    }

    public String getSite() {
        return site;
    }

    public String getKey() {
        return key;
    }
}
