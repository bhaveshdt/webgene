package com.delrima.webgene.arch.client.interfaces;

/**
 * <code><B>View<code><B>
 * <p>
 * Marker interface used to indicate a "view" (i.e. page) in a GWT application
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Sep 22, 2008
 * 
 */
public interface View {

    /**
     * <p>
     * Pre-process before displaying widget
     * </p>
     * 
     * @return
     */
    void process();
}
