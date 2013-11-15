package com.springapp.mvc.utils.openid;


import com.springapp.mvc.entity.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class OpenIdUserParser {

    private static final String NAME = "name";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String IDENTITY = "identity";
    private static final String EMAIL = "email";

    public User getUser(JSONObject jsonObject) {
        User user = null;
        try {
            System.out.println(jsonObject);
            user = parseUser(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User parseUser(JSONObject userDetails) throws JSONException {

        String firstName = parseFirstName(userDetails);
        String lastName = parseLastName(userDetails);
        String email = parseEmail(userDetails);
        String identity = parseIdentity(userDetails);

        User user = new User();
        user.setName(firstName != null ? firstName : "");
        user.setSurname(lastName != null ? lastName : "");
        user.setMail(email != null ? email : "");
        if(identity != null)
        {
            user.setOpenIdIdentity(identity);
        }
        else
        {
            throw new JSONException("Can't parse identity");
        }
        return user;
    }

    private String parseFirstName(JSONObject userDetails) {
        try {
            return userDetails.getJSONObject(NAME).getString(FIRST_NAME);
        } catch (JSONException e) {
            return null;
        }
    }

    private String parseLastName(JSONObject userDetails) {
        try {
            return userDetails.getJSONObject(NAME).getString(LAST_NAME);
        } catch (JSONException e) {
            return null;
        }
    }

    private String parseEmail(JSONObject userDetails) {
        try {
            return userDetails.getString(EMAIL);
        } catch (JSONException e) {
            return null;
        }
    }

    private String parseIdentity(JSONObject userDetails) {
        try {
            return userDetails.getString(IDENTITY);
        } catch (JSONException e) {
            return null;
        }
    }
}
