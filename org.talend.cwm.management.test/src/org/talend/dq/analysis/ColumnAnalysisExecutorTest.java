// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dq.analysis;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.relational.TdColumn;
import org.talend.cwm.relational.TdTable;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.analysis.AnalysisContext;
import org.talend.dataquality.analysis.AnalysisFactory;
import org.talend.dataquality.analysis.AnalysisParameters;
import org.talend.dataquality.analysis.AnalysisResult;
import org.talend.dataquality.analysis.ExecutionInformations;
import orgomg.cwm.objectmodel.core.Package;

/**
 * DOC msjian class global comment. Detailled comment
 */
public class ColumnAnalysisExecutorTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    /**
     * Test method for
     * {@link org.talend.dq.analysis.ColumnAnalysisExecutor#createSqlStatement(org.talend.dataquality.analysis.Analysis)}
     * .
     */
    @Test
    public void testCreateSqlStatement() {
        Analysis analysis = AnalysisFactory.eINSTANCE.createAnalysis();
        AnalysisContext context = AnalysisFactory.eINSTANCE.createAnalysisContext();

        analysis.setContext(context);
        TdColumn tdColumn = RelationalFactory.eINSTANCE.createTdColumn();

        TdTable tdTable = RelationalFactory.eINSTANCE.createTdTable();
        tdTable.setName("tableName"); //$NON-NLS-1$
        tdColumn.setOwner(tdTable);
        tdColumn.setName("columnName"); //$NON-NLS-1$

        AnalysisParameters analysisPara = AnalysisFactory.eINSTANCE.createAnalysisParameters();
        analysisPara.setStoreData(false);
        analysis.setParameters(analysisPara);
        context.getAnalysedElements().add(tdColumn);

        Package schema = orgomg.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();// mock(Schema.class);
        tdTable.setNamespace(schema);

        AnalysisResult analysisResult = AnalysisFactory.eINSTANCE.createAnalysisResult();
        ExecutionInformations info = AnalysisFactory.eINSTANCE.createExecutionInformations();
        analysisResult.setResultMetadata(info);
        analysis.setResults(analysisResult);

        Package catalog = orgomg.cwm.resource.relational.RelationalFactory.eINSTANCE.createCatalog();// mock(Catalog.class);
        catalog.setName("catalogName"); //$NON-NLS-1$
        schema.setNamespace(catalog);

        ColumnAnalysisExecutor columnAnalysisExecutor = new ColumnAnalysisExecutor();
        assertEquals("SELECT columnName FROM tableName", columnAnalysisExecutor.createSqlStatement(analysis)); //$NON-NLS-1$
    }

}
