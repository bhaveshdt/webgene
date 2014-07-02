package com.delrima.webgene.arch.client.utils;

import com.delrima.webgene.client.common.FrameworkContextBean;

/**
 * @author Bhavesh
 * 
 */
public class ClientSession {

    private static final FrameworkContextBean frameworkContextBean = new FrameworkContextBean();

    public static FrameworkContextBean getFrameworkContextBean() {
        return frameworkContextBean;
    }

}
