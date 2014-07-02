package com.delrima.webgene.arch.client.widgets.extensions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.delrima.webgene.arch.client.imagebundle.WebGeneImageBundle;
import com.delrima.webgene.arch.client.utils.GWTClientUtils;
import com.delrima.webgene.arch.client.utils.GWTFrameworkLogger;
import com.delrima.webgene.arch.client.utils.StringUtils;
import com.delrima.webgene.arch.client.utils.WidgetUtils;
import com.delrima.webgene.arch.client.validation.ValidationChangeManager;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <code><B>ExtendedPopupPanel</code></b>
 * <p>
 * Follow the FEATURE enum below for a list of features supported by this widget Also supports SEO Rendering
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Nov 7, 2008
 */
@SuppressWarnings("deprecation")
public class ManagedPopupPanel extends Composite implements ValueChangeHandler<String> {

    private static Logger sLogger = GWTFrameworkLogger.getCustomLogger(ManagedPopupPanel.class);

    /**
     * INVALID_POSITION - int - Used for auto-positioning
     */
    private static final int INVALID_POSITION = -99;

    private boolean bIgnoreTopInvalidPosition;

    /**
     * <code><B>FEATURE</code></b>
     * <p>
     * This widget is capable of being: VIEW_AWARE: If the pop-up was open in view1, then if the user goes to view2, the pop-up
     * will close. When the user comes back to view1, the pop-up will open again MOUSE_AWARE: Mouse events will be attached to the
     * "widget" field AUTO_HIDE: Enables auto hiding of pop-up-panel when clicking outside the pop-up. Also enables pop-up hiding
     * for mouse out. CLOSABLE_BUTTON_TOP: Puts a close button on the top right hand corner CLOSABLE_BUTTON_BUTTON: Puts a close
     * button on the bottom center
     * </p>
     * 
     * @author bhavesh.thakker@ihg.com
     * @since Jan 17, 2009
     */
    public enum FEATURE {
        AUTO_HIDE,
        MOUSE_AWARE,
        VIEW_AWARE,
        CLOSABLE_BUTTON_TOP,
        CLOSABLE_BUTTON_BOTTOM,
        VALIDATION_AWARE,
        SHOW_ON_HORIZONTAL_EDGE,
        CENTER,
        CLICK_AWARE,
        SHOW_WITH_AUTO_POSITIONING,
        HIDE_ON_CONTENT_MOUSE_OUT,
        SCROLLABLE
    }

    private boolean topPlacementPreferred = false;
    private boolean rightPlacementPreferred = true;

    public void setRightPlacementPreferred(boolean rightPlacementPreferred) {
        this.rightPlacementPreferred = rightPlacementPreferred;
    }

    /**
     * verticalPopupMargin - int - Show pop-up with these margins
     */
    private int verticalPopupMargin;
    private int horizontalPopupMargin;

    /**
     * widget - UIObject - Base widget that triggers the pop-up
     */
    private UIObject baseWidget;

    /**
     * contentWidget - UIObject - Widget added to the pop-up panel
     */
    private Widget contentWidget;

    /**
     * view - String - View name where the pop-up panel resides
     */
    private String view;

    /**
     * popupPanel - PopupPanel - Shows content in a <div /> pop-up
     */
    private PopupPanel popupPanel;

    /**
     * enableAutoFocus - boolean - To be used in conjunction with accessibility
     */
    private boolean preventAutoFocus;

    /**
     * Keeps track of whether the pop-up is expanded or not in the view of its origin
     */
    private boolean expanded = false;

    /**
     * autoHide - boolean - will hide mouse over
     */
    private boolean autoHide = false;

    /**
     * widgetPanel - Panel - initWidget(widgetPanel)
     */
    private Panel widgetPanel = WidgetUtils.createFlowPanel();

    /**
     * widgetPanel - Panel - initWidget(widgetPanel)
     */
    private Panel fpCloseButton = WidgetUtils.createFlowPanel("arch-CloseButton-Top");

    /**
     * popupContentPanel - Panel - added content into this panel
     */
    private Panel popupContentPanel = WidgetUtils.createFlowPanel();
    private FocusPanel popupContentFocusPanel = WidgetUtils.createFocusPanel();

    /**
     * features - FEATURE[] - Features associated with this pop-up panel
     */
    private Set<FEATURE> features;

    private List<OpenHandler<PopupPanel>> openHandlers;
    private List<CloseHandler<PopupPanel>> closeHandlers;

    /**
     * <p>
     * Create a new instance of ExtendedPopupPanel.
     * </p>
     */
    public ManagedPopupPanel() {
        initWidget(widgetPanel);
        initializePopupPanel(false);
    }

    /**
     * <p>
     * Create a new instance of ViewAwareClickPopupPanel. FEATURES are added to this widget based on the parameter
     * </p>
     * 
     * @param iFeatures
     */
    public ManagedPopupPanel(FEATURE... iFeatures) {
        this(null, null, iFeatures);
    }

    /**
     * <p>
     * Create a new instance of ExtendedPopupPanel.
     * </p>
     * 
     * @param baseWidget
     *            - Widget that triggers the pop-up
     * @param contentWidget
     *            - Content to be shown within the pop-up
     * @param iFeatures
     *            - Features to be included
     */
    public ManagedPopupPanel(final UIObject base, Widget contentWidget, FEATURE... iFeatures) {
        this(base, contentWidget, "", iFeatures);
    }

    public ManagedPopupPanel(final UIObject base, Widget contentWidget, String style, FEATURE... iFeatures) {
        // initialize default constructor
        this();
        this.baseWidget = base;

        // add baseWidget to panel
        if (baseWidget != null && baseWidget instanceof Widget) {
            widgetPanel.add((Widget) baseWidget);
            baseWidget.getElement().setId(DOM.createUniqueId());
        }

        popupContentFocusPanel.getElement().setId(DOM.createUniqueId());

        if (!preventAutoFocus) {
            popupContentFocusPanel.addKeyUpHandler(new KeyUpHandler() {

                public void onKeyUp(KeyUpEvent event) {
                    if (baseWidget != null && baseWidget instanceof Widget) {
                        sLogger.fine("onKeyUp(): " + event.getNativeKeyCode());
                        if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE
                                || (event.getNativeKeyCode() == KeyCodes.KEY_TAB && ManagedPopupPanel.this.features.contains(FEATURE.MOUSE_AWARE))) {
                            if (features.contains(FEATURE.MOUSE_AWARE)) {
                                ManagedPopupPanel.this.hide();
                                ManagedPopupPanel.this.expanded = false;
                            } else {
                                sLogger.fine((baseWidget != null) + ", " + (baseWidget instanceof Focusable) + ", " + baseWidget.getClass().toString());
                                if (baseWidget != null) {
                                    ManagedPopupPanel.this.hide();
                                    ManagedPopupPanel.this.expanded = false;
                                    // set focus on the button clicked to trigger the pop-up
                                    if (baseWidget instanceof Focusable) {
                                        sLogger.fine("KEY_ESCAPE: baseWidget instanceof FocusWidget");
                                        ((Focusable) baseWidget).setFocus(true);
                                        baseWidget.getElement().setAttribute("aria-pressed", "false");
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }

        // if no features to be added, return
        if (iFeatures == null) {
            return;
        }

        features = new HashSet<FEATURE>(Arrays.asList(iFeatures));

        if (baseWidget != null) {
            // add features requested in the parameters
            addPreFeature(); // content that needs to be added before the content in
                             // the pop-up
            if (contentWidget != null) {
                this.contentWidget = contentWidget;
                popupContentPanel.add(this.contentWidget);
                sLogger.info("constructor contentWidget class: " + contentWidget.getClass().getName());
            }
            for (final FEATURE feature : features) {
                switch (feature) {
                case AUTO_HIDE:
                    initializePopupPanel(true);
                    break;
                case VIEW_AWARE:
                    addViewAwareFeature();
                    break;
                case MOUSE_AWARE:
                    addMouseAwareFeature();
                    break;
                case VALIDATION_AWARE:
                    addValidationAwareFeature();
                    break;
                case CLICK_AWARE:
                    addClickAwareFeature();
                    break;
                case HIDE_ON_CONTENT_MOUSE_OUT:
                    addCloseOnContentMouseOutFeature();
                    break;
                case SCROLLABLE:
                    addScrollableContentWidget();
                }
            }
            popupContentFocusPanel.add(popupContentPanel);
            addPostFeature(); // content that needs to be added after the content in
                              // the pop-up
        }
        this.popupPanel.add(popupContentFocusPanel);

        setPopupPanelStyle(style);
    }

    private void initializePopupPanel(boolean autoHide) {
        popupPanel = new PopupPanel(autoHide);
        popupPanel.setAnimationEnabled(false);
        this.autoHide = autoHide;
    }

    private final void addScrollableContentWidget() {
        VerticalPanel verticalPopupPanel = new VerticalPanel();
        popupContentPanel.add(verticalPopupPanel);

        ScrollPanel contentScrollPanel = new ScrollPanel();
        contentScrollPanel.add(this.contentWidget);
        verticalPopupPanel.add(contentScrollPanel);
        contentScrollPanel.addStyleName("reservationExtendedScrollablePopupPanel");
    }

    /**
     * <p>
     * Use this method to widgets that need to trigger the pop-up hide
     * </p>
     * 
     * @param closeButton
     */
    public final void addPopupCloseButton(HasClickHandlers closeButton) {
        closeButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                ManagedPopupPanel.this.hide(true);
            }

        });
    }

    /**
     * <p>
     * Will re-position the pop-up in case validation messages are added or cleared pushing the base widget up/down
     * </p>
     */
    private void addValidationAwareFeature() {
        ValidationChangeManager.addChangeListener(new ChangeListener() {

            public void onChange(Widget sender) {
                if (ManagedPopupPanel.this.isDisplayed() && GWTClientUtils.getCurrentViewName().equals(view)) {
                    sLogger.info("addValidationAwareFeature().onChange().deferShowPopup();");
                    showRelativeTo();
                }
            }

        });
    }

    /**
     * <p>
     * Open Popup on Click
     * </p>
     */
    private void addClickAwareFeature() {
        if (this.baseWidget != null && this.baseWidget instanceof HasClickHandlers) {
            ((HasClickHandlers) baseWidget).addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent paramClickEvent) {
                    ManagedPopupPanel.this.showWithAutoPositioning();
                }

            });
        }
    }

    /**
     * <p>
     * Close the pop-up, if the user's moves the mouse out of the content panel
     * </p>
     */
    private void addCloseOnContentMouseOutFeature() {
        if (this.contentWidget != null) {
            this.popupContentFocusPanel.addMouseOutHandler(new MouseOutHandler() {

                @Override
                public void onMouseOut(MouseOutEvent arg0) {
                    ManagedPopupPanel.this.hide(true);
                }
            });
        }
    }

    /**
     * @see com.ihg.dec.apps.hi.gwt.arch.event.handler.ValidationMessagesChangedHandler#onValidationMessagesChange(com.ihg.dec.apps.hi.gwt.arch.event.type.ValidationMessagesChangedEvent)
     * 
     *      public void onValidationMessagesChange( ValidationMessagesChangedEvent event ) { if (
     *      ManagedPopupPanel.this.isDisplayed() && GWTClientUtils.getCurrentViewName().equals( view ) ) { sLogger.info(
     *      "addValidationAwareFeature().onChange().deferShowPopup();" ); showWithAutoPositioning(); } }
     */

    public void addStyleToCloseButton(String style) {
        fpCloseButton.addStyleName(style);
    }

    /**
     * <p>
     * Places a close button at the top-right corner of the pop-up
     * </p>
     */
    private void addPreFeature() {
        // close button top
        if (features.contains(FEATURE.CLOSABLE_BUTTON_TOP)) {
            Image closeButtonImage = new Image(WebGeneImageBundle.Util.getInstance().close());
            PushButton closeButton = new PushButton(closeButtonImage);
            addPopupCloseButton(closeButton);
            fpCloseButton.add(closeButton);
            this.popupContentPanel.add(fpCloseButton);
        }

        // apply keyboard handlers
        if (baseWidget instanceof Widget) {
            final Widget baseWidgetCast = (Widget) baseWidget;

            // Add keyboard handlers
            if (baseWidgetCast instanceof HasKeyUpHandlers) {
                HasKeyUpHandlers keyboardAwareWidget = (HasKeyUpHandlers) baseWidget;

                keyboardAwareWidget.addKeyUpHandler(new KeyUpHandler() {

                    public void onKeyUp(KeyUpEvent event) {
                        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                            DeferredCommand.addCommand(new Command() {

                                public void execute() {
                                    if (features.contains(FEATURE.MOUSE_AWARE)) {
                                        showWithAutoPositioning();
                                    }
                                    if (isDisplayed()) {
                                        popupContentFocusPanel.setFocus(true);
                                    }
                                }
                            });
                        }
                    }

                });
            }
        }
    }

    /**
     * <p>
     * Places a close button at the bottom center of the pop-up
     * </p>
     */
    private void addPostFeature() {
        if (features.contains(FEATURE.CLOSABLE_BUTTON_BOTTOM)) {
            Panel p = WidgetUtils.createFlowPanel("arch-CloseButton-Bottom");
            Image closeButton = new Image(WebGeneImageBundle.Util.getInstance().close());
            PushButton pushButton = new PushButton(closeButton);
            addPopupCloseButton(pushButton);
            p.add(pushButton);
            this.popupContentPanel.add(p);
        }
    }

    /**
     * <p>
     * the popup's state needs to be managed via the history listener i.e. The pop-up, if visible in view1 must not be visible in
     * view2. Using this constructor will assure this behavior
     * </p>
     * 
     * @param viewAware
     */
    private void addViewAwareFeature() {
        this.view = GWTClientUtils.getCurrentViewName();
        History.addValueChangeHandler(this);
    }

    /**
     * <p>
     * Attach a mouse listener to widget, if it supports mouse events
     * </p>
     * 
     * @param viewAware
     * @param mouseAware
     */
    private void addMouseAwareFeature() {
        try {
            if (baseWidget instanceof Widget) {
                sLogger.info("baseWidget instanceof Widget");
                if (baseWidget instanceof HasAllMouseHandlers) {
                    sLogger.info("baseWidget instanceof SourcesMouseEvents");
                    if (this.features.contains(FEATURE.AUTO_HIDE)) {
                        addMouseOutHandler((HasMouseOutHandlers) baseWidget);
                    }
                    addMouseOverHandler((HasMouseOverHandlers) baseWidget);
                }
            }
        } catch (Exception e) {
            sLogger.info("Error adding MouseAware feature.");
        }
    }

    /**
     * <p>
     * Provides behavior for this widget when rendering for SEO
     * </p>
     */
    public final void seoRender() {
        widgetPanel.add(this.popupPanel);
    }

    /**
     * <p>
     * Show pop-up relative to another widget
     * </p>
     * 
     * @param widget
     */
    public final void showRelativeTo(UIObject widget) {
        popupPanel.setPopupPosition(widget.getAbsoluteLeft(), widget.getOffsetHeight() + widget.getAbsoluteTop());
        deferShowPopup();
    }

    /**
     * <p>
     * Show relative to the provided coordinates.
     * </p>
     * 
     * @param left
     * @param top
     */
    public final void showRelativeTo(int left, int top) {
        popupPanel.setPopupPosition(left, top);
        deferShowPopup();
    }

    /**
     * <p>
     * Show relative to the provided baseWidget
     * </p>
     */
    private void showRelativeTo() {
        sLogger.info("basewidget position: " + baseWidget.getAbsoluteLeft() + "," + baseWidget.getOffsetHeight() + baseWidget.getAbsoluteTop());
        showRelativeTo(baseWidget);
    }

    /**
     * <p>
     * Process all pending events before displaying the pop-up
     * </p>
     */
    public final void deferShowPopup() {
        DeferredCommand.addCommand(new Command() {

            public void execute() {
                ManagedPopupPanel.this.show();
            }

        });
    }

    /**
     * <p>
     * Display pop-up centered on the screen
     * </p>
     */
    public final void center() {
        show(preventAutoFocus, true);
    }

    /**
     * <p>
     * Hide the pop-up after all pending events have been fired
     * </p>
     * 
     * @param b
     */
    public final void hide(final boolean b) {
        if (this.isDisplayed() || this.popupPanel.isShowing()) {
            ManagedPopupPanel.this.expanded = false;
            DeferredCommand.addCommand(new Command() {

                public void execute() {
                    popupPanel.hide(b);
                    if (closeHandlers != null) {
                        for (CloseHandler<PopupPanel> handler : closeHandlers) {
                            handler.onClose(null);
                        }
                    }

                    if (!preventAutoFocus && baseWidget != null && baseWidget instanceof Focusable) {
                        sLogger.info("hide(): baseWidget instanceof HasFocus");
                        ((Focusable) baseWidget).setFocus(true);
                        baseWidget.getElement().setAttribute("aria-pressed", "false");
                    }

                }
            });
        }

    }

    /**
     * <p>
     * Hide pop-up with auto-hide set to false
     * </p>
     */
    public final void hide() {
        hide(false);
    }

    /**
     * <p>
     * Show pop-up at the center of the screen
     * </p>
     */
    public final void showCentered() {
        show(preventAutoFocus, true);
    }

    /**
     * <p>
     * Display pop-up
     * </p>
     */
    public final void show() {
        show(preventAutoFocus, false);
    }

    public final void show(boolean preventAutoFocusOverride, final boolean center) {
        sLogger.info("show()");
        popupPanel.show();
        if (openHandlers != null) {
            for (OpenHandler<PopupPanel> handler : openHandlers) {
                handler.onOpen(null);
            }
        }

        popupPanel.setVisible(true);
        if (features.contains(FEATURE.CENTER) || center) {
            popupPanel.center();
        }
        if (!preventAutoFocusOverride) {
            popupContentFocusPanel.setFocus(true);
        }
        this.expanded = true;
    }

    /**
     * <p>
     * Ensure the popup is visible within the "viewable" area of the browser
     * </p>
     */
    public final void showWithAutoPositioning() {
        sLogger.fine("showWithAutoPositioning()");
        if (features.contains(FEATURE.MOUSE_AWARE) || features.contains(FEATURE.SHOW_WITH_AUTO_POSITIONING)) {
            showWithAutoPositioning(baseWidget.getElement(), popupPanel);
        } else {
            showRelativeTo(baseWidget);
        }
    }

    /**
     * <p>
     * Ensure the popup is visible within the "viewable" area of the browser and relative to the UIObject
     * </p>
     */
    public final void showWithAutoPositioning(Element relativeTo) {
        sLogger.fine("showWithAutoPositioning(UIObject)");
        showWithAutoPositioning(relativeTo, popupPanel);
    }

    /**
     * <p>
     * Show with auto-positioning and dictate auto positioning via the boolean flag
     * </p>
     * 
     * @param relativeTo
     * @param preventAutoFocus
     */
    public final void showWithAutoPositioning(Element relativeTo, boolean preventAutoFocus) {
        this.preventAutoFocus = preventAutoFocus;
        sLogger.fine("showWithAutoPositioning(Element)");
        showWithAutoPositioning(relativeTo, popupPanel);
    }

    /**
     * <p>
     * Ensure the provided popup is visible within the "viewable" area of the browser relative to the Element
     * </p>
     */
    public final void showWithAutoPositioning(final Element relativeTo, final PopupPanel popupPanel) {
        sLogger.fine("showWithAutoPositioning(UIObject, PopupPanel)");
        this.popupPanel = popupPanel;
        popupPanel.setPopupPositionAndShow(new PositionCallback() {

            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {
                Coordinates coordinates = getAutoPositionCoordinates(relativeTo, offsetWidth, offsetHeight);
                int top = coordinates.getTop();
                int left = coordinates.getLeft();
                if (coordinates.isInvalid()) {
                    showRelativeTo(left, top);
                } else {
                    popupPanel.setPopupPosition(left, top);
                }
            }
        });
    }

    /**
     * <p>
     * Provides
     * </p>
     * 
     * @param relativeTo
     * @param popupPanel
     * @return
     */
    public Coordinates getAutoPositionCoordinates(final Element relativeTo, final int offsetWidth, int offsetHeight) {
        boolean invalid = false;
        int top = calculateOptimalTop(topPlacementPreferred, relativeTo, offsetHeight);
        int left = calculateOptimalRight(rightPlacementPreferred, relativeTo, offsetWidth);
        if (top == INVALID_POSITION && left == INVALID_POSITION) {
            invalid = true;
            left = relativeTo.getAbsoluteLeft() + relativeTo.getOffsetWidth();
            top = relativeTo.getOffsetHeight() + relativeTo.getAbsoluteTop();
        } else {
            if (top == INVALID_POSITION) { // could fit above or below
                sLogger.info("top == INVALID_POSITION");
                top = Window.getScrollTop();
            } else if (left == INVALID_POSITION) { // could fit above or below
                sLogger.info("left == INVALID_POSITION");
                left = Window.getScrollLeft();
            }
        }
        sLogger.info("left,top = " + left + "," + top);
        return new Coordinates(invalid, left, top);
    }

    public static class Coordinates {

        private final int left;
        private final int top;
        private final boolean invalid;

        public Coordinates(boolean invalid, int left, int top) {
            super();
            this.left = left;
            this.top = top;
            this.invalid = invalid;
        }

        public int getLeft() {
            return left;
        }

        public int getTop() {
            return top;
        }

        public boolean isInvalid() {
            return invalid;
        }
    }

    /**
     * <p>
     * Calculate the top pixel value where the popup is best and fully viewable
     * </p>
     * 
     * @param topPlacementPreferred
     * @return
     */
    private int calculateOptimalTop(boolean topPlacementPreferred, Element relativeTo, int offsetHeight) {
        int position = doCalculateOptimalTop(topPlacementPreferred, relativeTo, offsetHeight);
        if (position == INVALID_POSITION) {
            position = doCalculateOptimalTop(!topPlacementPreferred, relativeTo, offsetHeight);
        }
        return position;
    }

    /**
     * <p>
     * Calculate the left pixel value where the popup is best and fully viewable
     * </p>
     * 
     * @param rightPlacementPreferred
     * @return
     */
    private int calculateOptimalRight(boolean rightPlacementPreferred, Element relativeTo, int offsetWidth) {
        int position = doCalculateOptimalRight(rightPlacementPreferred, relativeTo, offsetWidth);
        if (position == INVALID_POSITION) {
            position = doCalculateOptimalRight(!rightPlacementPreferred, relativeTo, offsetWidth);
        }
        return position;
    }

    private int getAbsoluteScreenTop(Element w) {
        return w.getAbsoluteTop() - Window.getScrollTop();
    }

    private int getAbsoluteScreenLeft(Element w) {
        return w.getAbsoluteLeft() - Window.getScrollLeft();
    }

    private int doCalculateOptimalTop(boolean topPlacementPreferred, Element relativeTo, int offsetHeight) {
        int position = INVALID_POSITION;
        sLogger.info("doCalculateOptimalTop() " + topPlacementPreferred);
        if (topPlacementPreferred) {
            // if popup cannot fit above base widget
            sLogger.info(offsetHeight + " and " + getAbsoluteScreenTop(relativeTo) + " and " + Window.getScrollTop());
            if (!bIgnoreTopInvalidPosition && offsetHeight > getAbsoluteScreenTop(relativeTo)) {
                return INVALID_POSITION;
            }
            sLogger.info(relativeTo.getAbsoluteTop() + " and " + offsetHeight);
            position = relativeTo.getAbsoluteTop() - offsetHeight;
            position -= this.getVerticalPopupMargin();
        } else {
            int reqPosition = getAbsoluteScreenTop(relativeTo) + relativeTo.getOffsetHeight() + offsetHeight;
            // if popup cannot fit under base widget
            if (!bIgnoreTopInvalidPosition && reqPosition > Window.getClientHeight()) {
                return INVALID_POSITION;
            }
            position = relativeTo.getAbsoluteTop() + relativeTo.getOffsetHeight();
            position += this.getVerticalPopupMargin();
        }
        return position;
    }

    private int doCalculateOptimalRight(boolean rightPlacementPreferred, Element relativeTo, int offsetWidth) {
        int position = INVALID_POSITION;
        sLogger.info("doCalculateOptimalRight() " + rightPlacementPreferred);
        if (rightPlacementPreferred) {
            // if popup cannot fit on the right to the base widget
            int reqPosition = getAbsoluteScreenLeft(relativeTo) + relativeTo.getOffsetWidth() + offsetWidth;
            sLogger.info(getAbsoluteScreenLeft(relativeTo) + ", " + relativeTo.getOffsetWidth() + "," + offsetWidth);
            if (reqPosition > Window.getClientWidth()) {
                return INVALID_POSITION;
            }
            position = relativeTo.getAbsoluteLeft() + relativeTo.getOffsetWidth();
            if (features.contains(FEATURE.SHOW_ON_HORIZONTAL_EDGE)) {
                position -= relativeTo.getOffsetWidth();
            }
        } else {
            // if popup cannot fit right of the base widget
            if (offsetWidth > (getAbsoluteScreenLeft(relativeTo) + relativeTo.getOffsetWidth())) {
                sLogger.info(offsetWidth + "," + getAbsoluteScreenLeft(relativeTo) + "," + relativeTo.getOffsetWidth());
                return INVALID_POSITION;
            }
            if (features.contains(FEATURE.SHOW_ON_HORIZONTAL_EDGE)) {
                position = relativeTo.getAbsoluteLeft() + relativeTo.getOffsetWidth() - offsetWidth;
            } else {
                position = relativeTo.getAbsoluteLeft() - offsetWidth;
            }
        }
        return position;
    }

    /**
     * Returns "true" if the popup is visible. "false" if the popup is not visible.
     * 
     * @return
     */
    public final boolean isDisplayed() {
        return this.expanded;
    }

    /**
     * @see com.google.gwt.user.client.HistoryListener#onHistoryChanged(java.lang.String)
     */
    public void onValueChange(ValueChangeEvent<String> event) {
        String historyToken = GWTClientUtils.getCurrentViewName();
        // manage the tracked state of the popup panel.
        if (historyToken.equals(view)) {
            if (this.expanded) {
                showRelativeTo();
            } else {
                hideInternal();
            }
        } else {
            hideInternal();
        }
    }

    private void hideInternal() {
        popupPanel.hide();
        ManagedPopupPanel.this.expanded = false;
    }

    /**
     * <p>
     * See {@link #setpopupPanel(PopupPanel)}
     * </p>
     * 
     * @return Returns the popupPanel.
     */
    public final PopupPanel getPopupPanel() {
        return popupPanel;
    }

    /**
     * <p>
     * Add mouse-over handler
     * </p>
     * 
     * @param widget
     */
    private void addMouseOverHandler(HasMouseOverHandlers widget) {
        widget.addMouseOverHandler(new MouseOverHandler() {

            public void onMouseOver(MouseOverEvent event) {
                if (!isDisplayed()) {
                    WidgetUtils.addStylesToWidget(((UIObject) event.getSource()), "roll-over");
                    showWithAutoPositioning();
                }
            }

        });
    }

    /**
     * <p>
     * Add mouse out handler
     * </p>
     * 
     * @param widget
     */
    private void addMouseOutHandler(HasMouseOutHandlers widget) {
        widget.addMouseOutHandler(new MouseOutHandler() {

            public void onMouseOut(final MouseOutEvent event) {
                if (autoHide) {
                    sLogger.fine("onMouseOutHandler(): " + event.getSource().getClass());
                    ((Widget) event.getSource()).removeStyleName("roll-over");
                    DeferredCommand.addCommand(new Command() {

                        public void execute() {
                            popupPanel.hide();
                            ManagedPopupPanel.this.expanded = false;
                        }
                    });
                }
            }
        });
    }

    // #66770:-Reward Nights page / UI issue with with layer background
    /**
     * It adds popup close handler.
     * 
     * @param closeHandler
     *            {@link CloseHandler<PopupPanel>} object
     */
    public void addPopupPanelCloseHandler(CloseHandler<PopupPanel> closeHandler) {
        popupPanel.addCloseHandler(closeHandler);
    }

    /**
     * <p>
     * Clear contents of the pop-up and the base widget
     * </p>
     */
    public final void clear() {
        if (null != popupContentPanel) {
            popupPanel.remove(popupContentPanel);
        }
        if (null != baseWidget) {
            baseWidget.getElement().setInnerHTML("");
        }
    }

    public final void setPopupPanelId(String id) {
        this.popupPanel.getElement().setId(id);
    }

    public final void setPopupPanelStyle(String style) {
        if (StringUtils.isSet(style)) {
            this.popupPanel.addStyleName(style);
        }
    }

    /**
     * <p>
     * See {@link #setverticalPopupMargin(int)}
     * </p>
     * 
     * @return Returns the verticalPopupMargin.
     */
    public final int getVerticalPopupMargin() {
        return verticalPopupMargin;
    }

    /**
     * <p>
     * Set the value of <code>verticalPopupMargin</code>.
     * </p>
     * 
     * @param verticalPopupMargin
     *            The verticalPopupMargin to set.
     */
    public final void setVerticalPopupMargin(int verticalPopupMargin) {
        this.verticalPopupMargin = verticalPopupMargin;
    }

    /**
     * <p>
     * See {@link #sethorizontalPopupMargin(int)}
     * </p>
     * 
     * @return Returns the horizontalPopupMargin.
     */
    public final int getHorizontalPopupMargin() {
        return horizontalPopupMargin;
    }

    /**
     * <p>
     * Set the value of <code>horizontalPopupMargin</code>.
     * </p>
     * 
     * @param horizontalPopupMargin
     *            The horizontalPopupMargin to set.
     */
    public final void setHorizontalPopupMargin(int horizontalPopupMargin) {
        this.horizontalPopupMargin = horizontalPopupMargin;
    }

    public final void setStyleToContentWidget(String style) {
        this.popupPanel.addStyleName(style);
    }

    /**
     * <p>
     * Set the value of <code>topPlacementPreferred</code>.
     * </p>
     * 
     * @param topPlacementPreferred
     *            The topPlacementPreferred to set.
     */
    public final void setTopPlacementPreferred(boolean topPlacementPreferred) {
        this.topPlacementPreferred = topPlacementPreferred;
    }

    /**
     * <p>
     * Set the value of <code>baseWidget</code>.
     * </p>
     * 
     * @param baseWidget
     *            The baseWidget to set.
     */
    public final void setBaseWidget(UIObject baseWidget) {
        this.baseWidget = baseWidget;
    }

    /**
     * <p>
     * Set the preventAutoFocus property
     * </p>
     * 
     * @param preventAutoFocus
     */
    public final void setPreventAutoFocus(boolean preventAutoFocus) {
        this.preventAutoFocus = preventAutoFocus;
    }

    /**
     * <p>
     * Add open handler
     * </p>
     * 
     * @param handler
     */
    public void addOpenHandler(OpenHandler<PopupPanel> handler) {
        if (openHandlers == null) {
            openHandlers = new ArrayList<OpenHandler<PopupPanel>>();
        }
        openHandlers.add(handler);
    }

    /**
     * <p>
     * Add close handler
     * </p>
     * 
     * @param handler
     */
    public void addCloseHandler(CloseHandler<PopupPanel> handler) {
        if (closeHandlers == null) {
            closeHandlers = new ArrayList<CloseHandler<PopupPanel>>();
        }
        closeHandlers.add(handler);
    }

    public boolean isIgnoreTopInvalidPosition() {
        return bIgnoreTopInvalidPosition;
    }

    public void setIgnoreTopInvalidPosition(boolean bIgnoreTopInvalidPosition) {
        this.bIgnoreTopInvalidPosition = bIgnoreTopInvalidPosition;
    }

}
