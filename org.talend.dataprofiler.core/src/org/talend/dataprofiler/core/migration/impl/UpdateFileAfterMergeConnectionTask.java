// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.migration.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.talend.commons.emf.FactoriesUtil;
import org.talend.commons.utils.io.FilesUtils;
import org.talend.core.model.properties.Property;
import org.talend.core.model.properties.TDQItem;
import org.talend.dataprofiler.core.migration.AWorkspaceTask;
import org.talend.dq.helper.PropertyHelper;
import org.talend.repository.utils.ResourceFilenameHelper;
import org.talend.resource.EResourceConstant;
import org.talend.resource.ResourceManager;

/**
 * 
 * DOC zshen class global comment. Detailled comment Move Connection file form TDQ_Metadata to metadata
 */
public class UpdateFileAfterMergeConnectionTask extends AWorkspaceTask {

    private static Logger log = Logger.getLogger(UpdateFileAfterMergeConnectionTask.class);

    private Map<String, String> replaceStringMap;

    private HashMap<String, String> fileNameMap = new HashMap<String, String>();

    public Map<String, String> getReplaceStringMap() {
        if (this.replaceStringMap == null) {
            this.replaceStringMap = initReplaceStringMap();
            this.replaceStringMap.putAll(fileNameMap);
        }
        return this.replaceStringMap;
    }

    public UpdateFileAfterMergeConnectionTask() {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected boolean doExecute() throws Exception {
        boolean result = true;
        // Move the content of connection folder
        IFolder dbFolder = ResourceManager.getConnectionFolder();
        IFolder mdmFolder = ResourceManager.getMDMConnectionFolder();
        IFolder tDQDbFolder = ResourceManager.getRootProject().getFolder(new Path(ExchangeFileNameToReferenceTask.DB_CONNECTION));
        IFolder tDQMdmFolder = ResourceManager.getRootProject().getFolder(
                new Path(ExchangeFileNameToReferenceTask.MDM_CONNECTION));
        if (dbFolder.exists() && tDQDbFolder.exists()) {
            for (IResource theResource : tDQDbFolder.members()) {
                if (theResource == null || deleteOldFile(theResource)) {
                    continue;
                }
                theResource.move(dbFolder.getFullPath().append(theResource.getName()), true, null);
            }
        }
        if (mdmFolder.exists() && tDQMdmFolder.exists()) {
            for (IResource theResource : tDQMdmFolder.members()) {
                if (theResource == null || deleteOldFile(theResource)) {
                    continue;
                }
                theResource.move(mdmFolder.getFullPath().append(theResource.getName()), true, null);
            }
        }

        if (tDQDbFolder.members().length == 0 && tDQMdmFolder.members().length == 0) {
            tDQDbFolder.getParent().delete(true, null);
            result = true;
        } else {
            result = false;
        }
        // change the path which conation in analysis and dqrule.
        File fileAnalysis = new File(ResourceManager.getAnalysisFolder().getRawLocationURI());
        File fileRule = new File(ResourceManager.getRulesFolder().getRawLocationURI());
        try {
            String[] anaFileExtentionNames = { "." + FactoriesUtil.ANA };
            String[] rulesFileEctentionNames = { "." + FactoriesUtil.DQRULE };
            result &= FilesUtils.migrateFolder(fileAnalysis, anaFileExtentionNames, this.getReplaceStringMap(), log)
                    && FilesUtils.migrateFolder(fileRule, rulesFileEctentionNames, this.getReplaceStringMap(), log);

        } catch (Exception e) {
            result = false;
            log.error(e, e);
        }

        return result;

    }

    public MigrationTaskType getMigrationTaskType() {
        return MigrationTaskType.FILE;
    }

    public Date getOrder() {
        return createDate(2010, 8, 16);
    }

    private Map<String, String> initReplaceStringMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("TDQ_Metadata/DB Connections", EResourceConstant.DB_CONNECTIONS.getPath());
        result.put("TDQ_Metadata/MDM Connections", EResourceConstant.MDM_CONNECTIONS.getPath());

        return result;
    }

    private boolean deleteOldFile(IResource theResource) throws CoreException {
        boolean result = false;

        if (FactoriesUtil.isProvFile(theResource.getFileExtension())) {
            result = true;// theResource is prv file delete it and return true.
        } else if (FactoriesUtil.PROPERTIES_EXTENSION.equals(theResource.getFileExtension())) {
            IFile oldPropertiesFile = (IFile) theResource;
            Property property = PropertyHelper.getProperty(oldPropertiesFile);
            if (property.getItem() instanceof TDQItem) {

                this.fileNameMap.put(((TDQItem) property.getItem()).getFilename() + "#" + property.getId(),
                        ResourceFilenameHelper.getExpectedFileName(property.getLabel(), property.getVersion()) + "#/0");
                result = true;// theResource is old properties file delete it and return true.
            }

        } else {
            return result;// theResource isn't prv or old properties file,so don't delete it and return false.
        }
        theResource.delete(true, null);

        return result;
    }

}
