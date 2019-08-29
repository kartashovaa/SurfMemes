package com.kyd3snik.surfmemes.repositories;

import com.kyd3snik.surfmemes.models.UserResponse;
import com.kyd3snik.surfmemes.utils.PrefUtil;

import static com.kyd3snik.surfmemes.utils.PrefUtil.Keys.ACCESS_TOKEN;
import static com.kyd3snik.surfmemes.utils.PrefUtil.Keys.FIRSTNAME;
import static com.kyd3snik.surfmemes.utils.PrefUtil.Keys.LASTNAME;
import static com.kyd3snik.surfmemes.utils.PrefUtil.Keys.USERNAME;
import static com.kyd3snik.surfmemes.utils.PrefUtil.Keys.USER_DESCRIPTION;

public class UserStorage {
    public static void saveUser(UserResponse user) {
        PrefUtil util = PrefUtil.getInstance();
        util.putString(ACCESS_TOKEN, user.accessToken);
        util.putString(USERNAME, user.userInfo.username);
        util.putString(FIRSTNAME, user.userInfo.firstName);
        util.putString(LASTNAME, user.userInfo.lastName);
        util.putString(USER_DESCRIPTION, user.userInfo.userDescription);
    }

    public static String getUserName() {
        return PrefUtil.getInstance().getString(USERNAME);
    }

    public static String getFirstName() {
        return PrefUtil.getInstance().getString(FIRSTNAME);

    }

    public static String getLastName() {
        return PrefUtil.getInstance().getString(LASTNAME);

    }

    public static String getUserDescription() {
        return PrefUtil.getInstance().getString(USER_DESCRIPTION);

    }

    public static String getToken() {
        return PrefUtil.getInstance().getString(ACCESS_TOKEN);

    }

    public static void clear() {
        PrefUtil.getInstance().clear();
    }

}
