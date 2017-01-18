import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoDatabase
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.fail

class MongoDbSmokeTest extends Specification {

    def 'MongoDb health'() {
        setup:
        String mongoConnectionUri = System.getenv 'MONGODB_CONNECTION_URI'
        if (!mongoConnectionUri) {
            fail 'MONGODB_CONNECTION_URI environment variable was not set'
        }

        MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoConnectionUri))
        MongoDatabase database = mongoClient.getDatabase 'hub_metadata'

        when:
        def command = database.runCommand(new BasicDBObject('buildInfo', 1))

        then:
        assertThat(command.getString('version')).isNotEmpty()
    }

}