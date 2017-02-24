import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import org.apache.http.HttpStatus
import spock.lang.Specification

class HubAdminUiSpec extends Specification {

    Hub theHub = new Hub()

    def 'Admin UI is healthy'() {
        when: 'I access the Admin UI'
        HttpResponse<String> response = Unirest.get(theHub.adminUiUri).asString();

        then:
        response.getStatus() == (HttpStatus.SC_OK);
    }

}
