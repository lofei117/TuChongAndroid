package info.lofei.app.tuchong.data.request.result;

/**
 * Created by jerrysher on 15/12/4.
 */
public class FavoriteResult {

    private static final String FAVORITE = "favorite";
    private static final String UNFAVORITE = "unfavorite";

    private String favoriteStatus;
    private String favoriteCount;
    private String message;
    private String result;

    public boolean isFavorited(){
        return FAVORITE.equalsIgnoreCase(getFavoriteStatus());
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getFavoriteStatus() {
        return favoriteStatus;
    }

    public void setFavoriteStatus(String favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
