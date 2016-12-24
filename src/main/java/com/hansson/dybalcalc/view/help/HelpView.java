package com.hansson.dybalcalc.view.help;

import java.io.File;

import com.hansson.dybalcalc.component.BuildBaner;
import com.hansson.dybalcalc.component.ContentWrapper;
import com.hansson.dybalcalc.event.HanssonEvent.CloseOpenWindowsEvent;
import com.hansson.dybalcalc.event.HanssonEventBus;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.util.CurrentInstance;

import pl.pdfviewer.PdfViewer;

@SuppressWarnings("serial")
public final class HelpView extends Panel implements View {

    public static final String EDIT_ID = "dashboard-edit";
    public static final String TITLE_ID = "dashboard-title";

    private Label titleLabel;
    private CssLayout dashboardPanels;
    private final VerticalLayout root;
	private ContentWrapper conWrap;
	private CssLayout singlePanel;

    public HelpView() {
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
        File file;
        VaadinRequest vaadinRequest = CurrentInstance.get(VaadinRequest.class);

        File baseDirectory = vaadinRequest.getService().getBaseDirectory();
        file = new File(baseDirectory + "/help.pdf");

        final PdfViewer pdf = new PdfViewer(file);
        pdf.setCaption("帮助文档");
        
        singlePanel = new CssLayout();
        singlePanel.addStyleName("dashboard-panels");
        ContentWrapper conWrap= new ContentWrapper(root, singlePanel);
        Component singleCalc = conWrap.wrapper(pdf);
        singlePanel.setSizeUndefined();
        singlePanel.addComponent(singleCalc);
        root.addComponent(singlePanel);
        root.setExpandRatio(singlePanel, 1);

        // All the open sub-windows should be closed whenever the root layout
        // gets clicked.
        root.addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(final LayoutClickEvent event) {
                HanssonEventBus.post(new CloseOpenWindowsEvent());
            }
        });
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        
    }

}
