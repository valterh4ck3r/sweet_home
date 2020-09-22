package sweet_home.beans;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;

@ManagedBean(name="banco")
@RequestScoped
public class BancoDados {
    
    private static final String XML_FILE = "/dbunit/dataset.xml";
    
    @SuppressWarnings("UseSpecificCatch")
    public static String inserirDados() {
        Connection conn = null;
        IDatabaseConnection db_conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:C:\\Users\\henri\\Documents\\eclipse\\sweet_home.db");
            db_conn = new DatabaseConnection(conn, "sweet_home");
            DatabaseConfig dbConfig = db_conn.getConfig();
            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
            dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            InputStream in = BancoDados.class.getResourceAsStream(XML_FILE);
            IDataSet dataSet = builder.build(in);
            DatabaseOperation.CLEAN_INSERT.execute(db_conn, dataSet);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                
                if (db_conn != null) {
                    db_conn.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        }
    
        return "sucess";
    }
}
