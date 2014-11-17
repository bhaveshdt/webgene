package com.delrima.webgene.arch.client.interfaces;

/**
 * <code><B>DataRetrievalCompleteCallback</code></b>
 * <p>
 * Generic class used to execute code when all RPC requests have been met. For an example, look at thae <code>PopulatorManager</code>
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Jan 2, 2009
 */
public interface DataRetrievalCompleteCallback {

	void onDataRetrievalComplete();
}