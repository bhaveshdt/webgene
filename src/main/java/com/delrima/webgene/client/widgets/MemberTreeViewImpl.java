package com.delrima.webgene.client.widgets;

import java.util.logging.Logger;

import com.delrima.webgene.arch.client.utils.GWTFrameworkLogger;
import com.delrima.webgene.arch.client.utils.WidgetUtils;
import com.delrima.webgene.arch.client.widgets.extensions.ManagedPopupPanel;
import com.delrima.webgene.arch.client.widgets.extensions.ManagedPopupPanel.FEATURE;
import com.delrima.webgene.client.enums.MemberDisplayMode;
import com.delrima.webgene.client.event.UpdateActionCompleteEvent;
import com.delrima.webgene.client.model.HasAncestors;
import com.delrima.webgene.client.model.HasDescendants;
import com.delrima.webgene.client.model.IsTreeMember;
import com.delrima.webgene.client.view.MemberEditView;
import com.delrima.webgene.client.view.MemberTreeView;
import com.delrima.webgene.client.widgets.MemberEditViewImpl.MemberLookupSuggestOracle;
import com.delrima.webgene.client.widgets.MemberEditViewImpl.MemberLookupSuggestion;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * Manages the display side of user-interaction logic such as: - Showing/Creating menu for each member tree item - Adding members
 * to the tree item
 * 
 * @author bhaveshthakker
 */
public class MemberTreeViewImpl extends Composite implements MemberTreeView {

    private static Logger sLogger = GWTFrameworkLogger.getCustomLogger(MemberTreeViewImpl.class);

    private Presenter presenter;

    private Label errorMessageLabel = new Label();

    // add member button
    private Button addMemberButton = new Button(" + Add Member");

    // context menu
    private MenuBar familyTreeMenu = new MenuBar(true);
    private final ManagedPopupPanel menuPopupPanel = new ManagedPopupPanel(new Label(), familyTreeMenu, FEATURE.AUTO_HIDE, FEATURE.SHOW_ON_HORIZONTAL_EDGE);

    // edit member popup panel
    private final ManagedPopupPanel memberEditPopupPanel;
    private final MemberEditView memberEditDisplay;

    //
    private final SuggestBox memberTreeSearchSuggestBox;

    private final TabBar displayMode = new TabBar();
    private final TabBar viewType = new TabBar();
    private final TreeInterfaceCreatorService treeCreatorService;
    private TreeCreator currentTreeCreator;
    private final FlowPanel treeContainerPanel;

    /**
     * Manage the actions associated with performing user-interactions with the member tree items
     * 
     * @param memberEditDisplay
     * @param eventBus
     */
    @Inject
    public MemberTreeViewImpl(MemberEditView memberEditDisplay, MemberLookupSuggestOracle oracle, TreeInterfaceCreatorService treeCreatorService) {
        this.memberEditDisplay = memberEditDisplay;
        this.memberTreeSearchSuggestBox = new SuggestBox(oracle);
        this.treeCreatorService = treeCreatorService;
        treeContainerPanel = new FlowPanel();
        for (MemberDisplayMode mode : MemberDisplayMode.values()) {
            this.displayMode.addTab(mode.toString(), false);
        }
        this.displayMode.selectTab(0);
        for (TreeViewType type : TreeViewType.values()) {
            this.viewType.addTab(type.toString(), false);
        }
        this.viewType.selectTab(0);

        memberEditPopupPanel = new ManagedPopupPanel(new Label(), (Widget) this.memberEditDisplay, FEATURE.AUTO_HIDE, FEATURE.SHOW_ON_HORIZONTAL_EDGE,
                FEATURE.SHOW_WITH_AUTO_POSITIONING, FEATURE.CLOSABLE_BUTTON_TOP);
        memberEditPopupPanel.setRightPlacementPreferred(false);

        Panel mainPanel = new FlowPanel();

        initWidget(mainPanel);

        // search box
        mainPanel.add(WidgetUtils.getFlowPanelWithWidgets(memberTreeSearchSuggestBox, new HTML("&nbsp;&nbsp;&nbsp;"), WidgetUtils.getFlowPanelWithWidgets(displayMode), new HTML(
                "&nbsp;&nbsp;&nbsp;"), WidgetUtils.getFlowPanelWithWidgets(viewType), new HTML("&nbsp;&nbsp;&nbsp;"), WidgetUtils.getFlowPanelWithWidgets(addMemberButton)));
        // error message
        mainPanel.add(errorMessageLabel);
        errorMessageLabel.setStyleName("errorMessage");

        // family tree
        mainPanel.add(treeContainerPanel);
        // attach listeners
        bind();
    }

    /**
     * Show "member operation" menu when user clicks on a member tree item.
     */
    public void bind() {
        // search
        memberTreeSearchSuggestBox.addSelectionHandler(new SelectionHandler<Suggestion>() {

            @Override
            public void onSelection(SelectionEvent<Suggestion> suggestion) {
                IsTreeMember selectedMember = ((MemberLookupSuggestion) suggestion.getSelectedItem()).getMember();
                sLogger.fine("" + selectedMember.toString());
                presenter.onRootMemberSuggestionSelected(selectedMember);
            }
        });
        displayMode.addSelectionHandler(new SelectionHandler<Integer>() {

            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                presenter.onDisplayModeSelected();
            }

        });
        viewType.addSelectionHandler(new SelectionHandler<Integer>() {

            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                presenter.onViewTypeSelected();
            }

        });
        familyTreeMenu.addItem(UpdateActionCompleteEvent.NAME.EDIT_MEMBER.getDescription(), new Command() {

            public void execute() {
                presenter.onEditMember();
            }
        });
        familyTreeMenu.addSeparator();
        familyTreeMenu.addItem(UpdateActionCompleteEvent.NAME.ADD_CHILD.getDescription(), new Command() {

            public void execute() {
                presenter.onAddChild();
            }
        });
        familyTreeMenu.addItem(UpdateActionCompleteEvent.NAME.ADD_PARENT.getDescription(), new Command() {

            public void execute() {
                presenter.onAddParent();
            }
        });
        familyTreeMenu.addItem(UpdateActionCompleteEvent.NAME.SELECT_MEMBER.getDescription(), new Command() {

            public void execute() {
                presenter.onSelectMember();
            }
        });
        familyTreeMenu.addSeparator();
        familyTreeMenu.addItem(UpdateActionCompleteEvent.NAME.DELETE_MEMBER.getDescription(), new Command() {

            public void execute() {
                presenter.onDeleteMember();
            }
        });
        addMemberButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                presenter.onAddMember();
            }

        });
    }

    public MemberDisplayMode getSelectedDisplayMode() {
        final String tabText = displayMode.getTabHTML(displayMode.getSelectedTab());
        return MemberDisplayMode.valueOf(tabText);
    }

    @Override
    public void showMenuForMember(IsTreeMember member) {
        presenter.onTreeMemberSelected(member);
        menuPopupPanel.showWithAutoPositioning(currentTreeCreator.getSelectedElement());
    }

    @Override
    public void showMemberEditLayer() {
        menuPopupPanel.hide();
        memberEditPopupPanel.showCentered();
    }

    @Override
    public void clearTree() {
        memberEditPopupPanel.hide();
        treeContainerPanel.clear();
    }

    @Override
    public void renderFamilyTree(HasAncestors ancestors, TreeViewType viewType) {
        clearTree();

        // create tree
        currentTreeCreator = this.treeCreatorService.getTreeCreator(viewType);
        currentTreeCreator.addSelectionHandler(new com.delrima.webgene.client.common.Command<IsTreeMember>() {

            @Override
            public void execute(IsTreeMember member) {
                MemberTreeViewImpl.this.showMenuForMember(member);
            }
        });
        currentTreeCreator.create(ancestors);
        treeContainerPanel.add(currentTreeCreator.getWidget());
    }


    @Override
    public void renderFamilyTree(HasDescendants descendants, TreeViewType viewType) {
        clearTree();

        // create tree
        currentTreeCreator = this.treeCreatorService.getTreeCreator(viewType);
        currentTreeCreator.addSelectionHandler(new com.delrima.webgene.client.common.Command<IsTreeMember>() {

            @Override
            public void execute(IsTreeMember member) {
                MemberTreeViewImpl.this.showMenuForMember(member);
            }
        });
        currentTreeCreator.create(descendants);
        treeContainerPanel.add(currentTreeCreator.getWidget());
    }

    @Override
    public void addToContainer(HasWidgets container) {
        container.add(this);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public TreeViewType getSelectedViewType() {
        final String tabText = viewType.getTabHTML(viewType.getSelectedTab());
        return TreeViewType.valueOf(tabText);
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
    public void updateRootMember(String name) {
        menuPopupPanel.hide();
        this.memberTreeSearchSuggestBox.setValue(name);
    }

}
