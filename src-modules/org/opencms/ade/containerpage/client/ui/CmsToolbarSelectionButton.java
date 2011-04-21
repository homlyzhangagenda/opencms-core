/*
 * File   : $Source: /alkacon/cvs/opencms/src-modules/org/opencms/ade/containerpage/client/ui/Attic/CmsToolbarSelectionButton.java,v $
 * Date   : $Date: 2011/04/21 11:50:15 $
 * Version: $Revision: 1.10 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (C) 2002 - 2009 Alkacon Software (http://www.alkacon.com)
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

package org.opencms.ade.containerpage.client.ui;

import org.opencms.ade.containerpage.client.CmsContainerpageHandler;
import org.opencms.gwt.client.ui.I_CmsButton;

import com.google.gwt.event.dom.client.ClickEvent;

/**
 * The publish button holding all selection related methods.<p>
 * 
 * @author Tobias Herrmann
 * 
 * @version $Revision: 1.10 $
 * 
 * @since 8.0.0
 */
public class CmsToolbarSelectionButton extends A_CmsToolbarOptionButton {

    /**
     * Constructor.<p>
     * 
     * @param handler the container-page handler
     */
    public CmsToolbarSelectionButton(CmsContainerpageHandler handler) {

        super(I_CmsButton.ButtonData.SELECTION, handler);
    }

    /**
     * @see org.opencms.ade.containerpage.client.ui.A_CmsToolbarOptionButton#onElementClick(com.google.gwt.event.dom.client.ClickEvent, org.opencms.ade.containerpage.client.ui.CmsContainerPageElement)
     */
    @Override
    public void onElementClick(ClickEvent event, CmsContainerPageElement element) {

        // do nothing really
        event.stopPropagation();
        event.preventDefault();

    }

    /**
     * @see org.opencms.gwt.client.ui.I_CmsToolbarButton#onToolbarClick()
     */
    @Override
    public void onToolbarClick() {

        boolean active = isDown();

        if (active) {
            getHandler().deactivateCurrentButton();
            getHandler().setActiveButton(this);
            onToolbarActivate();
        } else {
            getHandler().deactivateCurrentButton();
        }
    }
}