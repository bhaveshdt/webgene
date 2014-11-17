package com.delrima.webgene.client.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * <code><B>FrameworkContextBean</code></b>
 * <p>
 * Information managed by the framework
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Mar 19, 2009
 * 
 */
public class FrameworkContextBean implements Serializable, IsSerializable {

	private static final long serialVersionUID = 1L;

	private List<String> navigationHistory = new ArrayList<String>();
	private String[] applicationViewTokens;

	/**
	 * <p>
	 * See {@link #setapplicationViewTokens(String[])}
	 * </p>
	 * 
	 * @return Returns the applicationViewToakens.
	 */
	public String[] getApplicationViewTokens() {
		return applicationViewTokens;
	}

	/**
	 * <p>
	 * Set the value of <code>applicationViewTokens</code>.
	 * </p>
	 * 
	 * @param applicationViewTokens
	 *            The applicationViewTokens to set.
	 */
	public void setApplicationViewTokens(String[] applicationViewTokens) {
		this.applicationViewTokens = applicationViewTokens;
	}

	/**
	 * <p>
	 * See {@link #setpreviousToken(String)}
	 * </p>
	 * 
	 * @return Returns the previousToken.
	 */
	public String getPreviousToken() {
		int index = navigationHistory.size() - 2;
		return (index < 0) ? "" : navigationHistory.get(index);
	}

	/**
	 * <p>
	 * See {@link #setnavigationHistory(Set<String>)}
	 * </p>
	 * 
	 * @return Returns the navigationHistory.
	 */
	public List<String> getNavigationHistory() {
		return navigationHistory;
	}

	/**
	 * <p>
	 * Set the value of <code>navigationHistory</code>.
	 * </p>
	 * 
	 * @param navigationHistory
	 *            The navigationHistory to set.
	 */
	public void setNavigationHistory(List<String> navigationHistory) {
		this.navigationHistory = navigationHistory;
	}

}
