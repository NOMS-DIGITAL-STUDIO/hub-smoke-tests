import com.microsoft.azure.storage.CloudStorageAccount
import com.microsoft.azure.storage.blob.CloudBlobClient
import com.microsoft.azure.storage.blob.CloudBlobContainer
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.fail

class AzureBlobStoreSmokeTest extends Specification {

    def 'Azure blob store health'() {
        setup:
        String azureConnectionUri = System.getenv('AZURE_BLOB_STORE_CONNECTION_URI')
        if (!azureConnectionUri) {
            fail 'AZURE_BLOB_STORE_CONNECTION_URI environment variable was not set'
        }

        CloudStorageAccount storageAccount = CloudStorageAccount.parse(azureConnectionUri)
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient()
        CloudBlobContainer container = blobClient.getContainerReference('content-items')
        container.createIfNotExists()

        when:
        boolean contentItemsContainerExists = container.exists()

        then:
        assertThat(contentItemsContainerExists).isTrue()
    }

}
