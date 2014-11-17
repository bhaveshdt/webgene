package com.delrima.webgene.arch.client.imagebundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * <code><B>ReservationProcessImageBundle</code></b>
 * <p>
 * Interface used to introduce static images into the client side
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Nov 5, 2008
 * 
 */
public interface WebGeneImageBundle extends ClientBundle {

	@Source("com/delrima/webgene/images/close.gif")
	public ImageResource close();

	@Source("com/delrima/webgene/images/loading.gif")
	public ImageResource loading();

	public class Util {

		private static WebGeneImageBundle ib = null;

		public static WebGeneImageBundle getInstance() {
			if (ib == null)
				ib = (WebGeneImageBundle) GWT.create(WebGeneImageBundle.class);
			return ib;
		}
	}
}
