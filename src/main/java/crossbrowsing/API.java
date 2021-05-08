package crossbrowsing;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import models.profiles.Credentials;

/**
 * Documentation: "https://crossbrowsertesting.com/apidocs/v3/selenium.html#!/default/get_selenium_browsers"
 */
public /*abstract*/ class API {
    private String session_id;

    public API(String session_id) {
        this.session_id = session_id;
    }

    public void setScore(String score) {
        try {
            Unirest.put("http://crossbrowsertesting.com/api/v3/selenium/{seleniumTestId}")
                    .basicAuth(Credentials.getUSERNAME(), Credentials.getAUTHKEY())
                    .routeParam("seleniumTestId", this.session_id)
                    .field("action", "set_score")
                    .field("score", score)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
