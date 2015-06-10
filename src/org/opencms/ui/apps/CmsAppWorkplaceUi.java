/*
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (c) Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.ui.apps;

import org.opencms.main.OpenCms;
import org.opencms.ui.A_CmsUI;
import org.opencms.ui.I_CmsComponentFactory;
import org.opencms.ui.apps.CmsWorkplaceAppManager.NavigationState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;

/**
 * The workplace ui.<p>
 */
@Theme("opencms")
public class CmsAppWorkplaceUi extends A_CmsUI implements ViewDisplay, ViewProvider {

    /** The serial version id. */
    private static final long serialVersionUID = -5606711048683809028L;

    /** The home view path. */
    public static final String VIEW_HOME = "home";

    /** The navigation state manager. */
    private NavigationStateManager m_navigationStateManager;

    /**
     * Call to add a new browser history entry.<p>
     * 
     * @param state the current app view state
     */
    public void changeCurrentAppState(String state) {

        String view = getViewName(m_navigationStateManager.getState());
        m_navigationStateManager.setState(view + NavigationState.PARAM_SEPARATOR + state);
    }

    /**
     * Returns the menu items.<p>
     * 
     * @return the menu items
     */
    public List<I_CmsMenuItem> getMenuItems() {

        List<I_CmsMenuItem> items = new ArrayList<I_CmsMenuItem>();
        items.add(new A_CmsMenuItem("", FontAwesome.HOME) {

            @Override
            public void executeAction() {

                CmsAppWorkplaceUi ui = (CmsAppWorkplaceUi)A_CmsUI.get();
                ui.showHome();
            }

            @Override
            protected String getLabel(Locale locale) {

                return "Home";
            }
        });
        return items;
    }

    /**
     * @see com.vaadin.navigator.ViewProvider#getView(java.lang.String)
     */
    public View getView(String viewName) {

        if (viewName.startsWith(VIEW_HOME)) {
            return new CmsHomeView();
        } else {
            I_CmsWorkplaceAppConfiguration appConfig = OpenCms.getWorkplaceAppManager().getAppConfiguration(viewName);
            if (appConfig != null) {
                return new CmsAppView(appConfig);
            }
        }
        return null;
    }

    /**
     * @see com.vaadin.navigator.ViewProvider#getViewName(java.lang.String)
     */
    public String getViewName(String viewAndParameters) {

        NavigationState state = new NavigationState(viewAndParameters);
        return state.getViewName();
    }

    /**
     * Navigates to the given app.<p>
     * 
     * @param appConfig the app configuration
     */
    public void showApp(I_CmsWorkplaceAppConfiguration appConfig) {

        getNavigator().navigateTo(appConfig.getAppPath());
    }

    /**
     * Navigates to the home screen.<p>
     */
    public void showHome() {

        getNavigator().navigateTo("home");
    }

    /**
     * @see com.vaadin.navigator.ViewDisplay#showView(com.vaadin.navigator.View)
     */
    public void showView(View view) {

        Component component = null;
        if (view instanceof I_CmsComponentFactory) {
            component = ((I_CmsComponentFactory)view).createComponent();
        } else if (view instanceof Component) {
            component = (Component)view;
        }
        if (component != null) {
            setContent(component);
        }
    }

    /**
     * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
     */
    @Override
    protected void init(VaadinRequest request) {

        OpenCms.getWorkplaceAppManager().loadApps();
        m_navigationStateManager = new Navigator.UriFragmentManager(getPage());
        Navigator navigator = new Navigator(this, m_navigationStateManager, this);
        navigator.addProvider(this);
        String fragment = getPage().getUriFragment();
        if (fragment != null) {
            navigator.navigateTo(fragment);
        } else {
            showHome();
        }
    }

}