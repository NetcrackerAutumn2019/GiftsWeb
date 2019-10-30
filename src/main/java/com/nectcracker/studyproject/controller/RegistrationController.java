package com.nectcracker.studyproject.controller;


import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.apis.vk.VKOAuth2AccessToken;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserRegistrationRequest;
import com.nectcracker.studyproject.service.UserService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.impl.JsonReadContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RequestMapping("registration")
@Controller
@Slf4j
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    private final VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());
    private final String clientId = "7186647";
    private final String clientSecret = "AntS9vYhNpK8mKtkl8ae";

    private final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .defaultScope("friends, email") // replace with desired scope
            .callback("http://localhost:8080/registration/callback")
            .build(VkontakteApi.instance());

    private final String PROTECTED_RESOURCE_URL = "https://api.vk.com/method/users.get?v="
            + VkontakteApi.VERSION;

    @InitBinder("userRegistrationRequest") // For take Data from login.html
    public void initDateBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-mm-dd"), true));
    }

    @GetMapping("")
    public String registration(Model model) {
        model.addAttribute("userRegistrationRequest", new UserController());
        return "registration";
    }

    @PostMapping("")
    public String addUser(@ModelAttribute("userRegistrationRequest") UserRegistrationRequest userRegistrationRequest,
                          Map<String, Object> model) {
        String message = userService.addUser(userRegistrationRequest);
        if (!StringUtils.isEmpty(message)) {
            model.put("message", message);
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/vk")
    public ModelAndView vk() {
        final String customScope = "friends, email";
        final String authorizationUrl = service.createAuthorizationUrlBuilder()
                .scope(customScope)
                .build();
        return new ModelAndView("redirect:" + authorizationUrl);
    }

    @GetMapping("/callback")
    public String vk(@RequestParam("code") String code) throws InterruptedException, ExecutionException, IOException, ParseException {
        final OAuth2AccessToken accessToken = service.getAccessToken(AccessTokenRequestParams.create(code));
        VKOAuth2AccessToken vkoAuth2AccessToken = (VKOAuth2AccessToken) accessToken;

        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        final Response response = service.execute(request);
        log.debug("token {}", response);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(((JSONArray) ((JSONObject) jsonParser.parse(response.getBody())).get("response")).get(0).toString());

        User user = new User(jsonObject.get("id").toString(), jsonObject.get("id").toString(), vkoAuth2AccessToken.getEmail());

        userService.addUserFromVK(user);
        return "redirect:/cabinet";
    }


    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated)
            model.addAttribute("message", "Success");
        else
            model.addAttribute("message", "Sorry, something was wrong");

        return "login";
    }


}
