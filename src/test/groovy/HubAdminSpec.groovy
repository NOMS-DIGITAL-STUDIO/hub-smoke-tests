import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest
import org.apache.http.HttpStatus
import spock.lang.Shared
import spock.lang.Specification

class HubAdminSpec extends Specification {

    @Shared
    Hub theHub = new Hub()

    def 'Admin REST service is healthy'() {

        when:
        HttpResponse<JsonNode> response = Unirest.get(theHub.adminUri + "health").asJson()

        then:
        response.getStatus() == (HttpStatus.SC_OK)
        response.getBody().getObject().get("status") == ("UP")
    }

}
