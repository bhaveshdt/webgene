package com.delrima.webgene.client.widgets;

import java.util.HashMap;
import java.util.Map;

import com.delrima.webgene.client.common.Command;
import com.delrima.webgene.client.common.MemberTreeIteratorTemplate.VisitorAction;
import com.delrima.webgene.client.common.RecursiveMemberTreeIterator;
import com.delrima.webgene.client.model.HasAncestors;
import com.delrima.webgene.client.model.HasDescendants;
import com.delrima.webgene.client.model.HasTreeMember;
import com.delrima.webgene.client.model.IsTreeMember;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.OrgChart;
import com.google.gwt.visualization.client.visualizations.OrgChart.Options;
import com.google.gwt.visualization.client.visualizations.OrgChart.Size;

public class OrgChartTreeCreator extends AbstractTreeCreator {

	private final OrgChart orgChart = new OrgChart();
	DataTable dataTable;
	private HasTreeMember rootMember;

	Map<Long, IsTreeMember> memberMap = new HashMap<Long, IsTreeMember>();

	public OrgChartTreeCreator() {
	}

	public Options createOptions() {
		Options options = Options.create();
		options.setAllowCollapse(true);
		options.setAllowHtml(true);
		options.setSize(Size.MEDIUM);
		return options;
	}

	public void initialize() {
		dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "memberId", "memberId");
		dataTable.addColumn(ColumnType.STRING, "parentMemberId", "parentMemberId");
	}

	public VisitorAction<IsTreeMember> createAction() {

		VisitorAction<IsTreeMember> action = new VisitorAction<IsTreeMember>() {

			@Override
			public void onRelation(IsTreeMember attachTo, IsTreeMember attach) {
				dataTable.addRow();
				int rowIndex = dataTable.getNumberOfRows() - 1;
				dataTable.setCell(rowIndex, 0, attach.getId().toString(), createItem(attach).toString(), null);
				if (attachTo != null) {
					dataTable.setCell(rowIndex, 1, attachTo.getId().toString(), "", null);
				}
			}

			@Override
			public IsTreeMember onMember(IsTreeMember member) {
				if (member.getId() == rootMember.getMember().getId()) {
					onRelation(null, member);
				}
				memberMap.put(member.getId(), member);
				return member;
			}
		};

		return action;
	}

	public void create(final HasAncestors rootMember) {
		this.rootMember = rootMember;
		initialize();
		VisitorAction<IsTreeMember> action = createAction();
		new RecursiveMemberTreeIterator<IsTreeMember>(action).visitAncestors(rootMember);
		orgChart.draw(dataTable, createOptions());
	}

	public void create(final HasDescendants rootMember) {
		this.rootMember = rootMember;
		initialize();
		VisitorAction<IsTreeMember> action = createAction();
		new RecursiveMemberTreeIterator<IsTreeMember>(action).visitDescendants(rootMember);

		// dataTable.setValue( rowIndex, columnIndex, value )
		orgChart.draw(dataTable, createOptions());

	}

	public Widget getWidget() {
		return orgChart;
	}

	private IsTreeMember selectedMember;

	@Override
	public void addSelectionHandler(final Command<IsTreeMember> command) {
		orgChart.addDomHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				selectedElement = event.getNativeEvent().getEventTarget().cast();
				command.execute(selectedMember);
			}
		}, ClickEvent.getType());
		orgChart.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				final JsArray<Selection> selections = orgChart.getSelections();
				selectedMember = findSelectedMemberById(dataTable.getValueString(selections.get(0).getRow(), 0));
			}

		});
	}

	public IsTreeMember findSelectedMemberById(final String selectedMemberId) {
		return memberMap.get(Long.valueOf(selectedMemberId));
	}

	@Override
	public TreeViewType getType() {
		return TreeViewType.CHART;
	}

}
