package com.delrima.webgene.client.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.delrima.webgene.arch.client.utils.StringUtils;
import com.delrima.webgene.arch.client.utils.WidgetUtils;
import com.delrima.webgene.arch.client.widgets.extensions.ExtendedListBox;
import com.delrima.webgene.client.common.Command;
import com.delrima.webgene.client.dto.Contact;
import com.delrima.webgene.client.dto.Member;
import com.delrima.webgene.client.enums.GenderIdentifier;
import com.delrima.webgene.client.event.RetrieveMemberLookupResultEvent;
import com.delrima.webgene.client.model.IsTreeMember;
import com.delrima.webgene.client.model.MemberWithImmediateRelations;
import com.delrima.webgene.client.result.MemberLookupResult;
import com.delrima.webgene.client.view.MemberEditView;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;

public class MemberEditViewImpl extends Composite implements MemberEditView, SelectionHandler<Suggestion> {

	private MemberEditView.Presenter presenter;

	private VerticalPanel mainPanel = new VerticalPanel();

	private Label errorMessageLabel = new Label();

	private Label id = new Label();

	private TextBox firstname = new TextBox();
	private TextBox middlename = new TextBox();
	private TextBox lastname = new TextBox();
	private TextBox maidenname = new TextBox();
	private DateBox dob = new DateBox();
	private DateBox dod = new DateBox();
	private ExtendedListBox gender = new ExtendedListBox();
	// private ExtendedListBox married = new ExtendedListBox();
	private TextBox occupation = new TextBox();

	private final SuggestBox spouseLookupSuggestBox;
	private final SuggestBox fatherLookupSuggestBox;
	private final SuggestBox motherLookupSuggestBox;

	private final ContactEditWidget contactEditWidget;

	private Button submit = new Button("Save Member");

	@Inject
	public MemberEditViewImpl(MemberLookupSuggestOracle oracle, ContactEditWidget contactEditWidget) {
		this.spouseLookupSuggestBox = new SuggestBox(oracle);
		this.fatherLookupSuggestBox = new SuggestBox(oracle);
		this.motherLookupSuggestBox = new SuggestBox(oracle);

		this.contactEditWidget = contactEditWidget;

		initWidget(mainPanel);

		assemble();

		bind();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.event.logical.shared.SelectionHandler#onSelection(com.google .gwt.event.logical.shared.SelectionEvent)
	 */
	public void onSelection(SelectionEvent<Suggestion> suggestion) {
		((SuggestBox) suggestion.getSource()).setLayoutData(((MemberLookupSuggestion) suggestion.getSelectedItem()).getMember());
	}

	private void bind() {
		this.submit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				presenter.persistMemberChange();
			}
		});
		spouseLookupSuggestBox.addSelectionHandler(this);
		fatherLookupSuggestBox.addSelectionHandler(this);
		motherLookupSuggestBox.addSelectionHandler(this);
	}

	private void assemble() {

		Map<String, Widget> widgetMap = new LinkedHashMap<String, Widget>();
		{
			widgetMap.put("Id", id);
			widgetMap.put("First", firstname);
			widgetMap.put("Middle", middlename);
			widgetMap.put("Last", lastname);
			widgetMap.put("Maiden", maidenname);
			widgetMap.put("Birth", dob);
			widgetMap.put("Expire", dod);
			widgetMap.put("Gender", gender);
			// widgetMap.put( "Married", married );
			widgetMap.put("Occupation", occupation);

			widgetMap.put("Father", this.fatherLookupSuggestBox);
			widgetMap.put("Mother", this.motherLookupSuggestBox);
			widgetMap.put("Spouse", this.spouseLookupSuggestBox);

		}

		Grid formGrid = new Grid(widgetMap.size(), 2);

		// Assemble widgets
		int c = 0;
		Entry<String, Widget> entry = null;
		for (Iterator<Entry<String, Widget>> i = widgetMap.entrySet().iterator(); i.hasNext(); c++) {
			entry = i.next();
			formGrid.setWidget(c, 0, new Label(entry.getKey()));
			formGrid.setWidget(c, 1, entry.getValue());
		}
		mainPanel.add(errorMessageLabel);
		errorMessageLabel.setStyleName("errorMessage");
		mainPanel.add(WidgetUtils.getHorizontalPanelWithWidgets("", WidgetUtils.getVerticalPanelWithWidgets("", new Label("Member Information"), formGrid)// ,
																																							// contactEditWidget
		));
		mainPanel.add(submit);

		// Gender - Add List Items
		for (GenderIdentifier gender : GenderIdentifier.values()) {
			this.gender.addItem(gender.getDescription(), gender.toString());
		}

		// Married - Add List Items
		// for( MaritalStatus married : MaritalStatus.values() ) {
		// this.married.addItem( married.getDescription(), married.getCode() );
		// }
	}

	@Override
	public void displayErrorMessage(String errorMessage) {
		this.errorMessageLabel.setText(errorMessage);
	}

	@Override
	public void clearErrorMessage() {
		displayErrorMessage("");
	}

	@Override
	public void clearMemberEditData() {
		this.clearDisplay();
	}

	public void updateDisplayWithModel(MemberWithImmediateRelations hasRelations) {
		Member model = hasRelations.getMember();
		dob.setValue(model.getDob());
		dod.setValue(model.getDod());
		firstname.setValue(model.getFirstname());
		lastname.setValue(model.getLastname());
		maidenname.setValue(model.getMaidenname());
		gender.setValue(model.getGender());
		id.setText(String.valueOf(model.getId()));
		// married.setValue( model.getMarried() );
		middlename.setValue(model.getMiddlename());
		occupation.setValue(model.getOccupation());
		spouseLookupSuggestBox.setText("");
		motherLookupSuggestBox.setText("");
		fatherLookupSuggestBox.setText("");
		if (hasRelations.getSpouse() != null) {
			spouseLookupSuggestBox.setText(hasRelations.getSpouse().getFirstname() + " " + hasRelations.getSpouse().getLastname());
		}
		if (hasRelations.getFather() != null) {
			fatherLookupSuggestBox.setText(hasRelations.getFather().getFirstname() + " " + hasRelations.getFather().getLastname());
		}
		if (hasRelations.getMother() != null) {
			motherLookupSuggestBox.setText(hasRelations.getMother().getFirstname() + " " + hasRelations.getMother().getLastname());
		}
		this.spouseLookupSuggestBox.setLayoutData(hasRelations.getSpouse());
		this.motherLookupSuggestBox.setLayoutData(hasRelations.getMother());
		this.fatherLookupSuggestBox.setLayoutData(hasRelations.getFather());

		// this.updateContactDisplayWithModel( model.getContact() );
	}

	public void updateContactDisplayWithModel(Contact contact) {
		if (contact == null) {
			contact = new Contact();
		}
		contactEditWidget.getCity().setValue(contact.getCity());
		contactEditWidget.getCountry().setValue(contact.getCountry());
		contactEditWidget.getEmail().setValue(contact.getEmail());
		contactEditWidget.getFax().setValue(contact.getFax());
		contactEditWidget.getHomephone().setValue(contact.getHomephone());
		contactEditWidget.getIm().setValue(contact.getIm());
		contactEditWidget.getMobilephone().setValue(contact.getMobilephone());
		contactEditWidget.getName().setValue(contact.getName());
		contactEditWidget.getState().setValue(contact.getState());
		contactEditWidget.getStreet1().setValue(contact.getStreet1());
		contactEditWidget.getStreet2().setValue(contact.getStreet2());
		contactEditWidget.getWebsite().setValue(contact.getWebsite());
		contactEditWidget.getWorkphone().setValue(contact.getWorkphone());
		contactEditWidget.getZip().setValue(contact.getZip());
		contactEditWidget.getId().setText(String.valueOf(contact.getId()));
	}

	public MemberWithImmediateRelations extractModelFromDisplay() {
		Member model = new Member();
		model.setDob(dob.getValue());
		model.setDod(dod.getValue());
		model.setFirstname(firstname.getValue());
		model.setLastname(lastname.getValue());
		model.setMaidenname(maidenname.getValue());
		model.setGender(gender.getValue());

		if (StringUtils.isSet(id.getText())) {
			model.setId(Long.valueOf("0" + StringUtils.getBlankIfNull(id.getText())));
		}
		// model.setMarried( married.getValue() );
		model.setMiddlename(middlename.getValue());
		model.setOccupation(occupation.getValue());
		Member spouse = (Member) this.spouseLookupSuggestBox.getLayoutData();
		if (spouse != null && !this.spouseLookupSuggestBox.getText().equals("")) {
			model.setSpouseid(spouse.getId());
		}
		Member mother = (Member) this.motherLookupSuggestBox.getLayoutData();
		if (mother != null && !this.motherLookupSuggestBox.getText().equals("")) {
			model.setMotherid(mother.getId());
		}
		Member father = (Member) this.fatherLookupSuggestBox.getLayoutData();
		if (father != null && !this.fatherLookupSuggestBox.getText().equals("")) {
			model.setFatherid(father.getId());
		}
		// model.setContact( extractContactModelFromDisplay() );

		return new MemberWithImmediateRelations(model, father, mother, spouse, null);
	}

	private Contact extractContactModelFromDisplay() {
		Contact contact = new Contact();
		// contact.setId( Integer.valueOf( "0" + StringUtils.getBlankIfNull( id.getText() ) ) );
		contact.setCity(contactEditWidget.getCity().getValue());
		contact.setCountry(contactEditWidget.getCountry().getValue());
		contact.setEmail(contactEditWidget.getEmail().getValue());
		contact.setFax(contactEditWidget.getFax().getValue());
		contact.setHomephone(contactEditWidget.getHomephone().getValue());
		contact.setIm(contactEditWidget.getIm().getValue());
		contact.setMobilephone(contactEditWidget.getMobilephone().getValue());
		contact.setName(contactEditWidget.getName().getValue());
		contact.setState(contactEditWidget.getState().getValue());
		contact.setStreet1(contactEditWidget.getStreet1().getValue());
		contact.setStreet2(contactEditWidget.getStreet2().getValue());
		contact.setWebsite(contactEditWidget.getWebsite().getValue());
		contact.setWorkphone(contactEditWidget.getWorkphone().getValue());
		contact.setZip(contactEditWidget.getZip().getValue());
		return contact;
	}

	public void clearDisplay() {
		dob.setValue(null);
		dod.setValue(null);
		firstname.setValue("");
		lastname.setValue("");
		maidenname.setValue("");
		gender.setValue("");
		id.setText("");
		// married.setValue( "" );
		middlename.setValue("");
		occupation.setValue("");
		spouseLookupSuggestBox.setText("");
		motherLookupSuggestBox.setText("");
		fatherLookupSuggestBox.setText("");
		this.spouseLookupSuggestBox.setLayoutData(null);
		this.motherLookupSuggestBox.setLayoutData(null);
		this.fatherLookupSuggestBox.setLayoutData(null);
	}

	@Override
	public void setPresenter(MemberEditView.Presenter presenter) {
		this.presenter = presenter;
	}

	/**
	 * Makes remote procedure calls to retrieve members associated with the query
	 */
	public static class MemberLookupSuggestOracle extends SuggestOracle {

		private final HandlerManager eventBus;

		@Inject
		public MemberLookupSuggestOracle(final HandlerManager eventBus) {
			this.eventBus = eventBus;
		}

		public void requestSuggestions(final Request request, final Callback callback) {
			eventBus.fireEvent(new RetrieveMemberLookupResultEvent(request.getQuery(), new Command<MemberLookupResult>() {

				@Override
				public void execute(MemberLookupResult result) {
					Response response = new Response();
					List<MemberLookupSuggestion> memberLookupSuggestions = new ArrayList<MemberLookupSuggestion>();
					for (IsTreeMember member : result.getMembers()) {
						memberLookupSuggestions.add(new MemberLookupSuggestion(member));
					}
					response.setSuggestions(memberLookupSuggestions);
					callback.onSuggestionsReady(request, response);
				}
			}));
		}

	};

	/**
	 * Represents a suggestion associated with member lookup
	 * 
	 * @author bhaveshthakker
	 */
	public static class MemberLookupSuggestion implements Suggestion {

		private final IsTreeMember member;

		public MemberLookupSuggestion(IsTreeMember member) {
			this.member = member;
		}

		@Override
		public String getDisplayString() {
			return member.getFirstname() + " " + member.getLastname();
		}

		@Override
		public String getReplacementString() {
			return getDisplayString();
		}

		/**
		 * @return the member
		 */
		public IsTreeMember getMember() {
			return member;
		}
	}

}
