package com.delrima.webgene.arch.client.base;

/**
 * <p>
 * Associates a model (plain java bean) with this widget
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Sep 22, 2008
 * 
 * @param <Model>
 */
public interface ModelBinder<Model> {

	/**
	 * Clear the values being shown on the display
	 */
	void clearDisplay();

	/**
	 * <p>
	 * Return the model associated with the widget
	 * </p>
	 * 
	 * @return
	 */
	Model extractModelFromDisplay();

	/**
	 * <p>
	 * Set an instance of the model associated with this widget
	 * </p>
	 * 
	 * @param model
	 */
	void updateDisplayWithModel(Model model);

}
