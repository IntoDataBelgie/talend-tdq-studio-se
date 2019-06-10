// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.action.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.TreeSelection;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.dataprofiler.core.ui.action.actions.OverviewAnalysisAction;
import org.talend.metadata.managment.utils.MetadataConnectionUtils;
import org.talend.repository.model.IRepositoryNode;

/**
 * DOC rli class global comment. Detailled comment
 */
public class OverViewAnalysisActionProvider extends AbstractCommonActionProvider {

    /**
     * DOC rli OverViewAnalysisActionProvider constructor comment.
     */
    public OverViewAnalysisActionProvider() {
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        // MOD mzhao user readonly role on svn repository mode.
        if (!isShowMenu()) {
            return;
        }
        TreeSelection currentSelection = ((TreeSelection) this.getContext().getSelection());
        List list = currentSelection.toList();
        // DOC MOD klliu 2010-12-14 feature 15750 for overview catalog or schema analysis.
        List<IRepositoryNode> packageList = new ArrayList<IRepositoryNode>();
        // check if the selected nodes are different type, or from different db, should not show menu
        String connName = null;
        String objectType = null;

        for (Object obj : list) {
            Connection conn = getConnection(obj);
            if (!MetadataConnectionUtils.isTDQSupportDBTemplate(conn)) {
                return;
            }
            if (connName == null) {
                connName = conn.getName();
            } else if (!StringUtils.equals(connName, conn.getName())) {
                // can not select nodes from different connections.
                return;
            }
            IRepositoryNode node = (IRepositoryNode) obj;
            if (objectType == null) {
                objectType = node.getObjectType().getType();
            } else if (!objectType.equals(node.getObjectType().getType())) {
                return;
            }
            packageList.add(node);
        }
        if (!packageList.isEmpty()) {
            OverviewAnalysisAction overviewAnalysisAction = new OverviewAnalysisAction(packageList);
            menu.add(overviewAnalysisAction);
        }
    }
}
