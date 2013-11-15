package com.springapp.mvc.utils.openid;

import com.springapp.mvc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OpenIDUserReader {

    @Autowired
    private OpenIdUserParser openIdUserParser;

    @Autowired
    private JSONReader jsonReader;

    public User readUserFromUrl(String url) {
        User user = null;
        try {
            user = openIdUserParser.getUser(jsonReader.readJsonFromUrl(url));
        } catch (IOException e) {
            System.out.println("Can't read valid JSONObject from url " + url);
            e.printStackTrace();
        }
        return user;
    }

}
