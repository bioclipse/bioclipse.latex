/*******************************************************************************
 * Copyright (c) 2009  Egon Willighagen <egon.willighagen@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contact: http://www.bioclipse.net/
 ******************************************************************************/
package net.bioclipse.latex.business;

import net.bioclipse.core.PublishedClass;
import net.bioclipse.core.PublishedMethod;
import net.bioclipse.core.Recorded;
import net.bioclipse.managers.business.IBioclipseManager;
import net.bioclipse.statistics.model.IMatrixResource;

@PublishedClass(
    value="Manager that provides some convenience methods to create " +
    		"LaTeX."
)
public interface ILatexManager extends IBioclipseManager {

    @Recorded
    @PublishedMethod(methodSummary=
        "Converts an matrix resource into a LaTeX source file.",
        params="IMatrixResource matrix,String filename"
    )
    public String writeMatrix(
        IMatrixResource matrix,
        String filename
    );
    
}
