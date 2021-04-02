package framework.utilities.feedXMLUtil.xml;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BookAPIMethods {
    @GET
    Call<FeedModel> getFeedModel(@Url String path);
}
