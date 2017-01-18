import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import com.microsoft.azure.storage.CloudStorageAccount
import com.microsoft.azure.storage.blob.CloudBlobClient
import com.microsoft.azure.storage.blob.CloudBlobContainer
import spock.lang.Specification

import static java.lang.String.format
import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.fail

class AzureBlobStoreSmokeTest extends Specification {
    private static final String AZURE_CONTAINER_NAME = "content-items"
    private static final String IMAGE_FILE_NAME = 'hub-smoke-tests-1-pixel.png'

    def 'Azure blob store health'() {
        given:
        String azureConnectionUri = System.getenv 'AZURE_BLOB_STORE_CONNECTION_URI'
        if (!azureConnectionUri) {
            fail 'AZURE_BLOB_STORE_CONNECTION_URI environment variable was not set'
        }

        String azurePublicUrlBase = System.getenv 'AZURE_BLOB_STORE_PUBLIC_URL_BASE'
        if (!azurePublicUrlBase) {
            fail 'AZURE_BLOB_STORE_PUBLIC_URL_BASE environment variable was not set'
        }

        CloudBlobClient blobClient = CloudStorageAccount.parse(azureConnectionUri).createCloudBlobClient()
        CloudBlobContainer container = blobClient.getContainerReference(AZURE_CONTAINER_NAME)

        when:
        File file = new File(getClass().getResource(IMAGE_FILE_NAME).toURI())
        container.getBlockBlobReference(IMAGE_FILE_NAME).upload(new FileInputStream(file), file.size())

        String uri = format("%s/%s/%s", azurePublicUrlBase, AZURE_CONTAINER_NAME, IMAGE_FILE_NAME)
        HttpResponse<String> imageResponse = Unirest.get(uri).asString()

        then:
        assertThat(imageResponse.getStatus()).isEqualTo(200)
    }

}
