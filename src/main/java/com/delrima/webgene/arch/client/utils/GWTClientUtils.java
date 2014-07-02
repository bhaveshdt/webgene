package com.delrima.webgene.arch.client.utils;

import com.google.gwt.user.client.History;

public final class GWTClientUtils {

    /**
     * <p>
     * Get the view token associated with the current view of the app
     * </p>
     * 
     * @return
     */
    public static String getCurrentViewName() {
        String token = History.getToken();
        // if first view, token is empty, get its value from session
        if (!StringUtils.isSet(token)) {
            token = ClientSession.getFrameworkContextBean().getApplicationViewTokens()[0];
        }
        return token;
    }

}
