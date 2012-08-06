/*
 * ################################################################
 *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2012 INRIA/University of
 *                 Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 * $$ACTIVEEON_INITIAL_DEV$$
 */

package org.ow2.proactive_grid_cloud_portal.cli.cmd;

import java.io.File;
import java.util.Stack;

import org.ow2.proactive_grid_cloud_portal.cli.CLIException;
import org.ow2.proactive_grid_cloud_portal.cli.json.TaskResultView;
import org.ow2.proactive_grid_cloud_portal.cli.utils.FileUtility;

public class OutputCommand extends AbstractCommand implements Command {
    private String pathname;

    public OutputCommand(String pathname) {
        this.pathname = pathname;
    }

    @Override
    public void execute() throws CLIException {
        File outFile = new File(pathname);
        if (outFile.exists()) {
            outFile.delete();
        }
        Stack resultStack = resultStack();
        if (!resultStack.isEmpty()) {
            Object result = resultStack.peek();
            if (result instanceof String) {
                FileUtility.writeStringToFile(outFile, (String) result);
            } else if (result instanceof TaskResultView) {
                FileUtility
                        .writeByteArrayToFile(
                                ((TaskResultView) result).getSerializedValue(),
                                outFile);
            } else {
                FileUtility.writeObjectToFile(result, outFile);
            }
        } else {
            writeLine("No result available to write.");
        }
    }

}
