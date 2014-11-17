package com.delrima.webgene.client.place;

import com.delrima.webgene.arch.client.base.AbstractView;
import com.delrima.webgene.arch.client.widgets.extensions.ManagedPopupPanel;
import com.delrima.webgene.arch.client.widgets.extensions.ManagedPopupPanel.FEATURE;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MemberListPlace extends AbstractView {

	private Label status = new Label("Status: ");
	private ManagedPopupPanel managedAddMemberPopup;
	private Panel memberPanel;
	private VerticalPanel memberListPanel;

	@Override
	public Panel createWidget() {
		Panel mainPanel = new FlowPanel();
		memberPanel = new FlowPanel();

		Button addMemberButton = new Button("Add Member");
		addMemberButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				memberPanel.clear();
				// memberPanel.add(new MemberEditWidget());
				managedAddMemberPopup.showWithAutoPositioning();
			}
		});
		managedAddMemberPopup = new ManagedPopupPanel(addMemberButton, memberPanel, FEATURE.AUTO_HIDE, FEATURE.CLOSABLE_BUTTON_TOP);
		mainPanel.add(status);
		mainPanel.add(managedAddMemberPopup);
		memberListPanel = new VerticalPanel();
		mainPanel.add(memberListPanel);

		getMembersDescendantsViaRPC();
		return mainPanel;
	}

	private void getMembersDescendantsViaRPC() {
		/*
		 * WebGeneServiceAsync.Util.INSTANCE.getMemberDescendants(1, new AsyncCallback<List<Member>>() {
		 * 
		 * public void onFailure(Throwable caught) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * public void onSuccess(List<Member> result) { displayMembers(result); }
		 * 
		 * });
		 */
	}

	/*
	 * private void displayMembers(List<Member> members) { for (final Member m : members ) { displayMember(m); } } private void displayMember(Member m) { memberListPanel.add(new
	 * Label(m.getFirstname() + " " + m.getLastname())); }
	 */

	public void process() {
		// TODO Auto-generated method stub

	}

}
