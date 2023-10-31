// ============================================================================
//
// Copyright (C) 2006-2023 Talend Inc. - www.talend.com
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.context.ContextUtils;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.metadata.builder.connection.TacokitDatabaseConnection;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.ContextItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.model.properties.TacokitDatabaseConnectionItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.services.IGenericDBService;
import org.talend.dataprofiler.core.migration.AbstractWorksapceUpdateTask;
import org.talend.designer.core.model.utils.emf.talendfile.ContextParameterType;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.sdk.component.server.front.model.ConfigTypeNode;
import org.talend.sdk.component.studio.Lookups;
import org.talend.sdk.component.studio.metadata.model.TaCoKitConfigurationModel.BuiltInKeys;

/**
 * ADD TDQ-21221 msjian: this task will convert generic JDBC connection to Tacokit JDBC connection.
 * the same function class is GenericJDBCConnectionToTacokitJDBCMigrationTask
 * which will run from DI side.
 */
public class GenericJDBCConnectionToTacokitJDBCTask extends AbstractWorksapceUpdateTask {

    /*
     * (non-Javadoc)
     *
     * @see org.talend.dataprofiler.migration.IMigrationTask#getOrder()
     */
    public Date getOrder() {
        return createDate(2023, 10, 30);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.talend.dataprofiler.migration.IMigrationTask#getMigrationTaskType()
     */
    public MigrationTaskType getMigrationTaskType() {
        return MigrationTaskType.FILE;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.talend.dataprofiler.migration.AMigrationTask#doExecute()
     */
    @Override
    protected boolean doExecute() throws Exception {
        ProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        List<IRepositoryViewObject> allConnectionObject = factory.getAll(
                ERepositoryObjectType.METADATA_CONNECTIONS);

        for (IRepositoryViewObject object : allConnectionObject) {
            ConnectionItem item = (ConnectionItem) object.getProperty().getItem();
            Connection connection = item.getConnection();
            if (connection instanceof DatabaseConnection) {
                if (connection instanceof TacokitDatabaseConnection) {
                    continue;
                }
                DatabaseConnection dbConn = (DatabaseConnection) connection;
                String dbType = dbConn.getDatabaseType();
                if (dbType == null || !dbType.equals("JDBC")) {
                    continue;
                }
                
                TacokitDatabaseConnection tacokitDatabaseConnection = ConnectionFactory.eINSTANCE.createTacokitDatabaseConnection();
                TacokitDatabaseConnectionItem tacokitDatabaseConnectionItem = PropertiesFactory.eINSTANCE
                        .createTacokitDatabaseConnectionItem();
                tacokitDatabaseConnectionItem.setConnection(tacokitDatabaseConnection);
                tacokitDatabaseConnectionItem.setFileExtension(item.getFileExtension());
                tacokitDatabaseConnectionItem.setParent(item.getParent());
                Property property = PropertiesFactory.eINSTANCE.createProperty();
                tacokitDatabaseConnectionItem.setProperty(property);

                property.setAuthor(item.getProperty().getAuthor());
                property.setCreationDate(item.getProperty().getCreationDate());
                property.setDescription(item.getProperty().getDescription());
                property.setDisplayName(item.getProperty().getDisplayName());
                property.setId(item.getProperty().getId());
                property.setItem(tacokitDatabaseConnectionItem);
                property.setLabel(item.getProperty().getLabel());
                property.setMaxInformationLevel(item.getProperty().getMaxInformationLevel());
                property.setModificationDate(item.getProperty().getModificationDate());
                property.setOldStatusCode(item.getProperty().getOldStatusCode());
                property.setPurpose(item.getProperty().getPurpose());
                property.setStatusCode(item.getProperty().getStatusCode());
                property.setVersion(item.getProperty().getVersion());
                property.getAdditionalProperties().addAll(item.getProperty().getAdditionalProperties());

                tacokitDatabaseConnection.getProperties().putAll(dbConn.getProperties());
                tacokitDatabaseConnection.setDbmsId(dbConn.getDbmsId());
                tacokitDatabaseConnection.setURL(dbConn.getURL());
                tacokitDatabaseConnection.setDatabaseType(dbConn.getDatabaseType());
                tacokitDatabaseConnection.setDriverJarPath(dbConn.getDriverJarPath());
                tacokitDatabaseConnection.setDriverClass(dbConn.getDriverClass());
                tacokitDatabaseConnection.setUsername(dbConn.getUsername());
                tacokitDatabaseConnection.setPassword(dbConn.getPassword());
                tacokitDatabaseConnection.setProductId(dbConn.getProductId());
                ConfigTypeNode configNode = Lookups.taCoKitCache().findDatastoreConfigTypeNodeByName("JDBC");
                tacokitDatabaseConnection.getProperties().put(BuiltInKeys.TACOKIT_CONFIG_ID, configNode.getId());
                tacokitDatabaseConnection.getProperties().put(BuiltInKeys.TACOKIT_CONFIG_PARENT_ID, configNode.getParentId());

                tacokitDatabaseConnection.setCdcConns(dbConn.getCdcConns());
                tacokitDatabaseConnection.setCdcTypeMode(dbConn.getCdcTypeMode());
                tacokitDatabaseConnection.setContextId(dbConn.getContextId());
                tacokitDatabaseConnection.setContextMode(dbConn.isContextMode());
                tacokitDatabaseConnection.setContextName(dbConn.getContextName());
                tacokitDatabaseConnection.setAdditionalParams(dbConn.getAdditionalParams());
                tacokitDatabaseConnection.setDatasourceName(dbConn.getDatasourceName());
                tacokitDatabaseConnection.setDBRootPath(dbConn.getDBRootPath());
                tacokitDatabaseConnection.setDbVersionString(dbConn.getDbVersionString());
                tacokitDatabaseConnection.setDivergency(dbConn.isDivergency());
                tacokitDatabaseConnection.setFileFieldName(dbConn.getFileFieldName());
                tacokitDatabaseConnection.setPort(dbConn.getPort());
                tacokitDatabaseConnection.setUiSchema(dbConn.getUiSchema());
                tacokitDatabaseConnection.setServerName(dbConn.getServerName());
                tacokitDatabaseConnection.setSID(dbConn.getSID());
                tacokitDatabaseConnection.setComment(dbConn.getComment());

                tacokitDatabaseConnection.setId(dbConn.getId());
                tacokitDatabaseConnection.setLabel(dbConn.getLabel());
                tacokitDatabaseConnection.setNullChar(dbConn.getNullChar());
                tacokitDatabaseConnection.setProductId(dbConn.getProductId());
                tacokitDatabaseConnection.setSqlSynthax(dbConn.getSqlSynthax());
                tacokitDatabaseConnection.setStandardSQL(dbConn.isStandardSQL());
                tacokitDatabaseConnection.setStringQuote(dbConn.getStringQuote());
                tacokitDatabaseConnection.setSynchronised(dbConn.isSynchronised());
                tacokitDatabaseConnection.setSystemSQL(dbConn.isSystemSQL());
                tacokitDatabaseConnection.setVersion(dbConn.getVersion());
                tacokitDatabaseConnection.setReadOnly(dbConn.isReadOnly());
                tacokitDatabaseConnection.setName(dbConn.getName());
                tacokitDatabaseConnection.setNamespace(dbConn.getNamespace());

                tacokitDatabaseConnection.setIsCaseSensitive(dbConn.isIsCaseSensitive());
                tacokitDatabaseConnection.setMachine(dbConn.getMachine());
                tacokitDatabaseConnection.setPathname(dbConn.getPathname());
                tacokitDatabaseConnection.setPort(dbConn.getPort());
                tacokitDatabaseConnection.setQueries(dbConn.getQueries());
                tacokitDatabaseConnection.setStereotype(dbConn.getStereotype());
                tacokitDatabaseConnection.setSupportNLS(dbConn.isSupportNLS());

                tacokitDatabaseConnection.getDataPackage().addAll(dbConn.getDataPackage());
                tacokitDatabaseConnection.getConstraint().addAll(dbConn.getConstraint());
                tacokitDatabaseConnection.getChangeRequest().addAll(dbConn.getChangeRequest());
                tacokitDatabaseConnection.getClientDependency().addAll(dbConn.getClientDependency());
                tacokitDatabaseConnection.getDataManager().addAll(dbConn.getDataManager());
                tacokitDatabaseConnection.getDasdlProperty().addAll(dbConn.getDasdlProperty());
                tacokitDatabaseConnection.getDeployedSoftwareSystem().addAll(dbConn.getDeployedSoftwareSystem());
                tacokitDatabaseConnection.getDescription().addAll(dbConn.getDescription());
                tacokitDatabaseConnection.getDocument().addAll(dbConn.getDocument());
                tacokitDatabaseConnection.getElementNode().addAll(dbConn.getElementNode());
                tacokitDatabaseConnection.getImportedElement().addAll(dbConn.getImportedElement());
                tacokitDatabaseConnection.getImporter().addAll(dbConn.getImporter());
                tacokitDatabaseConnection.getMeasurement().addAll(dbConn.getMeasurement());
                tacokitDatabaseConnection.getOwnedElement().addAll(dbConn.getOwnedElement());
                tacokitDatabaseConnection.getParameters().addAll(dbConn.getParameters());
                tacokitDatabaseConnection.getRenderedObject().addAll(dbConn.getRenderedObject());
                tacokitDatabaseConnection.getResourceConnection().addAll(dbConn.getResourceConnection());
                tacokitDatabaseConnection.getResponsibleParty().addAll(dbConn.getResponsibleParty());
                tacokitDatabaseConnection.getSupplierDependency().addAll(dbConn.getSupplierDependency());
                tacokitDatabaseConnection.getTaggedValue().addAll(dbConn.getTaggedValue());
                tacokitDatabaseConnection.getVocabularyElement().addAll(dbConn.getVocabularyElement());
                
                tacokitDatabaseConnection.setEnableDBType(false);

                if (dbConn.isSetSQLMode()) {
                    tacokitDatabaseConnection.setSQLMode(dbConn.isSQLMode());
                } else {
                    // set true by default as it's only used actually for teradata.
                    // should be modified if default value is changed later.
                    tacokitDatabaseConnection.setSQLMode(true);
                }
                try {
                    boolean isContextMode = dbConn.isContextMode();
                    if (isContextMode) {
                        String contextId = dbConn.getContextId();
                        ContextItem contextItem = ContextUtils.getContextItemById2(contextId);
                        if (contextItem != null) {
                            setContextDriversValue(contextItem, dbConn.getDriverJarPath());
                            factory.save(contextItem, true);
                        }
                    }
                    IRepositoryViewObject obj = factory.getSpecificVersion(item.getProperty().getId(),
                            item.getProperty().getVersion(), true);
                    factory.deleteObjectPhysical(obj);
                    factory.create(tacokitDatabaseConnectionItem, new Path(item.getState().getPath()), true);
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }

        }
        return true;
    }

    private void setContextDriversValue(ContextItem contextItem, String paramName) {
        IGenericDBService dbService = null;
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IGenericDBService.class)) {
            dbService = (IGenericDBService) GlobalServiceRegister.getDefault().getService(IGenericDBService.class);
        }
        boolean containContextParam = ContextParameterUtils.isContainContextParam(paramName);
        if (!containContextParam || dbService == null) {
            return;
        }
        paramName = ContextParameterUtils.getContextString(paramName);
        EList contexts = contextItem.getContext();
        for (Object context : contexts) {
            if (context instanceof ContextType) {
                ContextParameterType contextParam =
                        ContextUtils.getContextParameterTypeByName((ContextType) context, paramName);
                if (contextParam == null || StringUtils.isBlank(contextParam.getValue())) {
                    continue;
                }
                String[] jars = contextParam.getValue().split(";");
                List<Map<String, Object>> drivers = new ArrayList<Map<String, Object>>();
                for (String jar : jars) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put(TacokitDatabaseConnection.KEY_DRIVER_PATH, jar);
                    drivers.add(map);
                }
                contextParam.setValue(drivers.toString());
            }
        }

    }

}
