import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import org.apache.http.HttpStatus
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

class HubContentFeedUiSpec extends Specification {

    @Shared
    Hub theHub = new Hub()

    @Ignore
    def 'Content Feed UI application is available'() {
        when: 'I access the Content Feed UI'
        HttpResponse<String> response = Unirest.get(theHub.contentFeedUiUri).asString()

        then:
        response.getStatus() == (HttpStatus.SC_OK)
    }

}
