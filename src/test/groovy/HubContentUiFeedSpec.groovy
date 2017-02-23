import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import org.apache.http.HttpStatus
import spock.lang.Specification

class HubContentUiFeedSpec extends Specification {

    def 'Content Feed UI is healthy'() {
        def hubContentFeedUriUrI = (System.getenv('HUB_CONTENT_FEED_URI') ?: "http://localhost:8000/")

        when: 'I access the Content Feed UI'
        HttpResponse<String> response = Unirest.get(hubContentFeedUriUrI).asString();

        then:
        response.getStatus() == (HttpStatus.SC_OK);
    }

}
