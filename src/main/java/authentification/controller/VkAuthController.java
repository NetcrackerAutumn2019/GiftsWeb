package authentification.controller;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class VkAuthController {
    @GetMapping("/vkauth")
    public String vkauth(@RequestParam(required = false) String acess_token, @RequestParam(required = false) String user_id) throws ClientException, ApiException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient, new Gson(), 1);
//        UserAuthResponse authResponse = vk.oauth()
//                .userAuthorizationCodeFlow(7176812, "2nfTN5MTibW6pZdn1BYT", "/vkauth", code)
//                .execute();
//        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        return "redirect:/home";
    }
}
