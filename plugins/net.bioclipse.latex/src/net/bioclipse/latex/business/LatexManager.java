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

import java.io.ByteArrayInputStream;

import net.bioclipse.managers.business.IBioclipseManager;
import net.bioclipse.statistics.model.IMatrixResource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;

public class LatexManager implements IBioclipseManager {

    /**
     * Gives a short one word name of the manager used as variable name when
     * scripting.
     */
    public String getManagerName() {
        return "latex";
    }

    public IFile writeMatrix(
            IMatrixResource matrix,
            IFile target,
            IProgressMonitor monitor) throws Exception {
        StringBuilder latex = new StringBuilder();
        int rowCount = matrix.getRowCount();
        int colCount = matrix.getColumnCount();
        boolean hasRowLabels = matrix.hasRowHeader();
        boolean hasColLabels = matrix.hasColHeader();
        latex.append("\\begin{tabular}{");
        if (hasRowLabels) latex.append("l|");
        for (int col=0; col<colCount; col++) latex.append('c');
        latex.append("}\n");
        
        // optionally, create the header line
        if (hasColLabels) {
            if (hasRowLabels) latex.append(" & ");
            for (int col=0; col<colCount; col++) {
                latex.append(matrix.getColumnName(col+1));
                if (col+1 < colCount)
                    // there will be another column
                    latex.append(" & ");
            }
            latex.append(" \\\\\n\\hline\n");
        }
        
        // create the matrix content
        for (int row=0; row<rowCount; row++) {
            if (hasRowLabels) {
                latex.append(matrix.getRowName(row+1));
                latex.append(" & ");
            }
            for (int col=0; col<colCount; col++) {
                latex.append(matrix.get(row+1,col+1));
                if (col+1 < colCount)
                    // there will be another column
                    latex.append(" & ");
            }
            if (row+1 < rowCount)
                // there will be another row
                latex.append(" \\\\");
            latex.append('\n');
        }

        latex.append("\\end{tabular}");

        if (target.exists()) {
            target.setContents(
                new ByteArrayInputStream(
                    latex.toString().getBytes("US-ASCII")
                ),
                false,
                true, // overwrite
                monitor
            );
        } else {
            target.create(
                new ByteArrayInputStream(
                    latex.toString().getBytes("US-ASCII")
                ),
                false,
                monitor
            );
        }
        return target;
    }
}
