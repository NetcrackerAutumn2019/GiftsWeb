package authentification.config;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public class VkAuth {
    TransportClient transportClient = HttpTransportClient.getInstance();
    VkApiClient vk = new VkApiClient(transportClient, new Gson(), 1);
}
