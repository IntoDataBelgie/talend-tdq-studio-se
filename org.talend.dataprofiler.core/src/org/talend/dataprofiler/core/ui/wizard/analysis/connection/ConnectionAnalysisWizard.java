// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.wizard.analysis.connection;

import java.util.List;

import org.talend.cwm.helper.CatalogHelper;
import org.talend.cwm.helper.DataProviderHelper;
import org.talend.cwm.relational.TdCatalog;
import org.talend.cwm.relational.TdSchema;
import org.talend.cwm.softwaredeployment.TdDataProvider;
import org.talend.dataprofiler.core.ui.wizard.analysis.AnalysisMetadataWizardPage;
import org.talend.dataquality.indicators.schema.CatalogIndicator;
import org.talend.dataquality.indicators.schema.ConnectionIndicator;
import org.talend.dataquality.indicators.schema.SchemaFactory;
import org.talend.dq.analysis.AnalysisBuilder;
import org.talend.dq.analysis.parameters.AnalysisFilterParameter;

/**
 * @author zqin
 * 
 */
public class ConnectionAnalysisWizard extends AnalysisFilterWizard {

    protected ConnAnalysisDPSelectionPage dpSelectionPage;

    /**
     * 
     */
    public ConnectionAnalysisWizard(AnalysisFilterParameter parameter) {
        super(parameter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {

        addPage(new AnalysisMetadataWizardPage());

        if (anaFilterParameter.getTdDataProvider() == null) {
            dpSelectionPage = new ConnAnalysisDPSelectionPage();
            addPage(dpSelectionPage);
        }

        anaFilterPage = new ConnAnalysisFilterPage();
        addPage(anaFilterPage);
    }

    @Override
    protected void fillAnalysisBuilder(AnalysisBuilder analysisBuilder) {
        TdDataProvider tdProvider = anaFilterParameter.getTdDataProvider();
        analysisBuilder.setAnalysisConnection(tdProvider);
        ConnectionIndicator indicator = SchemaFactory.eINSTANCE.createConnectionIndicator();
        indicator.setAnalyzedElement(tdProvider);
        List<TdSchema> tdSchemas = DataProviderHelper.getTdSchema(tdProvider);
        if (tdSchemas.size() != 0) {
            addSchemaIndicator(tdSchemas, indicator);
        }
        List<TdCatalog> tdCatalogs = DataProviderHelper.getTdCatalogs(tdProvider);
        for (TdCatalog tdCatalog : tdCatalogs) {
            CatalogIndicator createCatalogIndicator = SchemaFactory.eINSTANCE.createCatalogIndicator();
            createCatalogIndicator.setAnalyzedElement(tdCatalog);
            indicator.addSchemaIndicator(createCatalogIndicator);
            addSchemaIndicator(CatalogHelper.getSchemas(tdCatalog), indicator);
        }
        analysisBuilder.addElementToAnalyze(tdProvider, indicator);
        super.fillAnalysisBuilder(analysisBuilder);
    }
}
