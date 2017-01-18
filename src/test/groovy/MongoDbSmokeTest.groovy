import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoDatabase
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class MongoDbSmokeTest extends Specification {

    def 'MongoDb health'() {
        setup:
        String mongoConnectionUri = System.getenv 'MONGODB_CONNECTION_URI'
        if (!mongoConnectionUri) {
            mongoConnectionUri = 'mongodb://localhost:27017'
        }

        MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoConnectionUri))
        MongoDatabase database = mongoClient.getDatabase 'hub_metadata'

        when:
        def command = database.runCommand(new BasicDBObject('buildInfo', 1))

        then:
        println command.getString('version')
        assertThat(command.getString('version')).isNotEmpty()
        assertThat(false).isTrue();
    }

}