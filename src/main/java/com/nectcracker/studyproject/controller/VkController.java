package com.nectcracker.studyproject.controller;

import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.nectcracker.studyproject.service.VkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;



@RequestMapping("/vk")
@Controller
public class VkController {

    private final VkService vkService;

    public VkController(VkService vkService) {
        this.vkService = vkService;
    }

    @Value("${spring.oauth2.vk.client-id}")
    private String vkClientId;

    @Value("${spring.oauth2.vk.client-secret}")
    private String vkClientSecret;

    @Value("${spring.oauth2.vk.scope}")
    private String vkScope;

    @Value("${spring.oauth2.vk.callback}")
    private String vkCallback;

    @Value("${spring.oauth2.vk.method}")
    private String vkMethod;

    //Because @Value initialize after OAuth20Service
    private OAuth20Service vkScribejavaService = null;

    @GetMapping("/login")
    public ModelAndView vk() {
        vkScribejavaService = new ServiceBuilder(vkClientId)
                .apiSecret(vkClientSecret)
                .defaultScope(vkScope)
                .callback(vkCallback)
                .build(VkontakteApi.instance());

        final String customScope = "friends, email";
        final String authorizationUrl = vkScribejavaService.createAuthorizationUrlBuilder()
                .scope(customScope)
                .build();
        return new ModelAndView("redirect:" + authorizationUrl);
    }

    @GetMapping("/callback")
    public String vk(@RequestParam("code") String code, Model model) throws InterruptedException, ExecutionException, IOException, ParseException {

        final OAuth2AccessToken accessToken = vkScribejavaService.getAccessToken(AccessTokenRequestParams.create(code));
        final OAuthRequest request = new OAuthRequest(Verb.GET, vkMethod+"users.get?fields=bdate&v="
                + VkontakteApi.VERSION);
        vkScribejavaService.signRequest(accessToken, request);
        final Response response = vkScribejavaService.execute(request);

        String vkId;
        if(!StringUtils.isEmpty(vkId = vkService.addUser(accessToken, response))){
            return "redirect:/login";
        }
        vkService.addFriends(vkScribejavaService, accessToken, vkId);
        return "redirect:/";
    }

}
