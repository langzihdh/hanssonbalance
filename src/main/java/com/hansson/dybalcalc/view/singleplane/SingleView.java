package com.hansson.dybalcalc.view.singleplane;

import com.hansson.dybalcalc.component.BuildBaner;
import com.hansson.dybalcalc.component.ContentWrapper;
import com.hansson.dybalcalc.event.HanssonEvent.CloseOpenWindowsEvent;
import com.hansson.dybalcalc.event.HanssonEventBus;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class SingleView extends Panel implements View {

    public static final String EDIT_ID = "dashboard-edit";
    public static final String TITLE_ID = "dashboard-title";

    private Label titleLabel;
    private CssLayout dashboardPanels;
    private final VerticalLayout root;
	private ContentWrapper conWrap;
	private CssLayout singlePanel;

    public SingleView() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        HanssonEventBus.register(this);
        root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        Responsive.makeResponsive(root);
        

        
        root.addComponent(new BuildBaner());
        
        singlePanel = new CssLayout();
        singlePanel.addStyleName("dashboard-panels");
        ContentWrapper conWrap= new ContentWrapper(root, singlePanel);
        Component singleCalc = conWrap.wrapper(new SingleCalc());
        //singlePanel.setSizeUndefined();
        singlePanel.addComponent(singleCalc);
        root.addComponent(singlePanel);
        
        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);

        // All the open sub-windows should be closed whenever the root layout
        // gets clicked.
        root.addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(final LayoutClickEvent event) {
                HanssonEventBus.post(new CloseOpenWindowsEvent());
            }
        });
    }

    private Component buildContent() {
        dashboardPanels = new CssLayout();
        dashboardPanels.addStyleName("dashboard-panels");
        Responsive.makeResponsive(dashboardPanels);
        conWrap= new ContentWrapper(root, dashboardPanels);
        //dashboardPanels.addComponent(buildSingleCalc());
        //dashboardPanels.addComponent(buildSingleChart());

        return dashboardPanels;
    }

    private Component buildSingleCalc() {
        Component singleCalc = conWrap.wrapper(new SingleCalc());
        singleCalc.setSizeFull();
        singleCalc.addStyleName("top10-revenue");
        return singleCalc;
    }

    private Component buildSingleChart() {
        Component singleChart = conWrap.wrapper(new SingleChart());
        singleChart.setSizeFull();
        //vectorCalc.addStyleName("top10-revenue");
        return singleChart;
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        
    }

}
