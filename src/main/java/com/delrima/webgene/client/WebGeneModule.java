package com.delrima.webgene.client;

import com.delrima.webgene.arch.client.base.AbstractEntryPoint;
import com.delrima.webgene.client.place.MemberListPlace;
import com.delrima.webgene.client.place.WebGenePlace;
import com.google.gwt.core.client.GWT;

public class WebGeneModule extends AbstractEntryPoint {

    public static enum ViewIdentifier {
        HOME, MEMBER_LIST, HOME_VISUALIZATION
    }

    @Override
    protected void populateViewInstances() {
        viewInstances.put(ViewIdentifier.HOME.toString(), (WebGenePlace) GWT.create(WebGenePlace.class));
        viewInstances.put(ViewIdentifier.MEMBER_LIST.toString(), (MemberListPlace) GWT.create(MemberListPlace.class));
    }

}
