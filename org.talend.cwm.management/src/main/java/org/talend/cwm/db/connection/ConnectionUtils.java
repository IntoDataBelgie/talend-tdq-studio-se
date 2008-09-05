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
package org.talend.cwm.db.connection;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

import net.sourceforge.sqlexplorer.dbproduct.ManagedDriver;
import net.sourceforge.sqlexplorer.plugin.SQLExplorerPlugin;
import net.sourceforge.sqlexplorer.util.MyURLClassLoader;

import org.talend.utils.sugars.ReturnCode;

/**
 * Utility class for database connection handling.
 */
public final class ConnectionUtils {

    /**
     * The query to execute in order to verify the connection.
     */
    // private static final String PING_SELECT = "SELECT 1";
    /**
     * private constructor.
     */
    private ConnectionUtils() {
    }

    /**
     * Method "createConnection".
     * 
     * @param url the database url
     * @param driverClassName the Driver classname
     * @param props properties passed to the driver manager for getting the connection (normally at least a "user" and
     * "password" property should be included)
     * @return the connection
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Connection createConnection(String url, String driverClassName, Properties props) throws SQLException,
            InstantiationException, IllegalAccessException, ClassNotFoundException {
        // net.sourceforge.sqlexplorer.dbproduct.DriverManager driverModel =
        // SQLExplorerPlugin.getDefault().getDriverModel();
        // Driver driver = null;
        // try {
        // Collection<ManagedDriver> drivers = driverModel.getDrivers();
        // for (ManagedDriver managedDriver : drivers) {
        // LinkedList<String> jars = managedDriver.getJars();
        // for (String string : jars) {
        // File file = new File(string);
        // if (file.exists()) {
        // MyURLClassLoader cl;
        // cl = new MyURLClassLoader(file.toURL());
        // try {
        // Class clazz = cl.findClass(driverClassName);
        // if (clazz != null) {
        // driver = (Driver) clazz.newInstance();
        // return driver.connect(url, props);
        // }
        // } catch (ClassNotFoundException e) {
        // //
        // }
        // }
        // }
        // }
        // } catch (MalformedURLException e) {
        // e.printStackTrace();
        // }
        Driver driver = getClassDriver(driverClassName);
        Connection conn = DriverManager.getConnection(url, props);
        return conn;
        // if (driver != null) {
        // return driver.connect(url, props);
        // }
        // DriverManager.registerDriver(driver);
        // Connection connection = DriverManager.getConnection(url, props);
        // return null;
    }

    /**
     * DOC qzhang Comment method "getClassDriver".
     * 
     * @param driverClassName
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static Driver getClassDriver(String driverClassName) throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        SQLExplorerPlugin sqlExplorerPlugin = SQLExplorerPlugin.getDefault();
        Driver driver = null;
        if (sqlExplorerPlugin != null) {
            net.sourceforge.sqlexplorer.dbproduct.DriverManager driverModel = sqlExplorerPlugin.getDriverModel();
            try {
                Collection<ManagedDriver> drivers = driverModel.getDrivers();
                for (ManagedDriver managedDriver : drivers) {
                    LinkedList<String> jars = managedDriver.getJars();
                    for (String string : jars) {
                        File file = new File(string);
                        if (file.exists()) {
                            MyURLClassLoader cl;
                            cl = new MyURLClassLoader(file.toURL());
                            try {
                                Class clazz = cl.findClass(driverClassName);
                                if (clazz != null) {
                                    driver = (Driver) clazz.newInstance();
                                }
                            } catch (ClassNotFoundException e) {
                                // do nothings
                            }
                        }
                    }
                }
            } catch (MalformedURLException e) {
                // do nothings
            }
        }
        if (driver == null) {
            driver = (Driver) Class.forName(driverClassName).newInstance();
        }
        return driver;
    }

    /**
     * Method "isValid".
     * 
     * @param connection the connection to test
     * @return a return code with the appropriate message (never null)
     */
    public static ReturnCode isValid(final Connection connection) {
        return org.talend.utils.sql.ConnectionUtils.isValid(connection);
    }

    /**
     * Method "closeConnection".
     * 
     * @param connection the connection to close.
     * @return a ReturnCode with true if ok, false if problem. {@link ReturnCode#getMessage()} gives the error message
     * when there is a problem.
     */
    public static ReturnCode closeConnection(final Connection connection) {
        return org.talend.utils.sql.ConnectionUtils.closeConnection(connection);
    }
}
