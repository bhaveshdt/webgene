package com.delrima.webgene.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.OrgChart;

public class TreeInterfaceCreatorService {

	private List<TreeCreator> treeCreatorList = new ArrayList<TreeCreator>();
	{
		treeCreatorList.add(new HierarchicalTreeWidgetCreator());
		VisualizationUtils.loadVisualizationApi(new Runnable() {

			public void run() {
				treeCreatorList.add(new OrgChartTreeCreator());
			}
		}, OrgChart.PACKAGE);

	}

	public TreeCreator getTreeCreator(TreeViewType type) {
		TreeCreator result = null;
		for (TreeCreator creator : treeCreatorList) {
			creator.getWidget().setVisible(false);
			if (creator.getType() == type) {
				result = creator;
				creator.getWidget().setVisible(true);
			}
		}
		return result;
	}
}
