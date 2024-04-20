package hr.fer.oprpp2.p08;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebListener
public class Inicijalizacija implements ServletContextListener {

	/*
	* pravljenje bp:
	terminal -> cd /Users/jrajcic/Desktop/java/derby-skripte
	./ij-console.sh
	connect 'jdbc:derby://localhost:1527/votingDB;user=sa;password=sapwd22;create=true';
	CALL SYSCS_UTIL.SYSCS_CREATE_USER('ivica', 'ivo');
	CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.fullAccessUsers', 'sa,ivica');
	disconnect; -> u /Users/jrajcic/Desktop/java/derby-baze bi se trebao pojaviti folder votingDB

	*/

	// mvn jetty:run triggera ovaj kod
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Inicijalizacija...");

		ServletContext servletContext = sce.getServletContext();
        InputStream inputStream = servletContext.getResourceAsStream("/WEB-INF/database.properties");
        Properties properties = new Properties();
		String connectionURL = null; 

        try {
            properties.load(inputStream);
			String host = properties.getProperty("host");
			String port = properties.getProperty("port");
			String dbName = properties.getProperty("name");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password=" + password;
        } catch (Exception e) {
            throw new RuntimeException("Error loading database.properties file.", e);
        }

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		DataSource ds = cpds;
		try {
			Connection con = ds.getConnection();
			System.out.println("Connection established.");

			// strategija
			MyTableI[] tables = new MyTableI[] {
				new MyPollsTableImpl(con),
				new MyPollOptionsTableImpl(con),
			};

			for (MyTableI table : tables) {
				if (!table.doesTableExistInDB()) table.createTable();
				if (table.isTableEmpty()) table.fillTable();
			}
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException("Baza podataka nije dostupna.", e);
		}

		long startTime = System.currentTimeMillis();
        sce.getServletContext().setAttribute("startTime", startTime);
	}


	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}


interface MyTableI {

    boolean doesTableExistInDB() throws SQLException;

    void createTable() throws SQLException;

    boolean isTableEmpty() throws SQLException;

    void fillTable() throws SQLException;
}

class MyPollsTableImpl implements MyTableI {

    private Connection connection;

    public MyPollsTableImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean doesTableExistInDB() throws SQLException {
        ResultSet tmp = connection.getMetaData().getTables(null, null, "POLLS", null);
        return tmp.next();
    }

    @Override
    public void createTable() throws SQLException {
        System.out.println("Creating table POLLS...");
        String query = "" +
                "CREATE TABLE POLLS (" +
                "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                "title VARCHAR(150) NOT NULL," +
                "message CLOB(2048) NOT NULL" +
                ")";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.execute();
        System.out.println("Table POLLS created.");
        ps.close();
    }

    @Override
    public boolean isTableEmpty() throws SQLException {
        String query = "SELECT * FROM POLLS FETCH FIRST 1 ROWS ONLY";
        PreparedStatement ps = connection.prepareStatement(query);
        boolean isEmpty = !ps.executeQuery().next();
        ps.close();
        return isEmpty;
    }

    @Override
    public void fillTable() throws SQLException {
        System.out.println("Inserting into table POLLS...");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO POLLS (title, message) values (?, ?)");
        ps.setString(1, "Glasanje za omiljeni bend");
        ps.setString(2, "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
        ps.executeUpdate();
        System.out.println("Inserted into table POLLS.");
        ps.close();
    }
}

class MyPollOptionsTableImpl implements MyTableI {

    private Connection connection;

    public MyPollOptionsTableImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean doesTableExistInDB() throws SQLException {
        ResultSet tmp = connection.getMetaData().getTables(null, null, "POLLOPTIONS", null);
        return tmp.next();
    }

    @Override
    public void createTable() throws SQLException {
        System.out.println("Creating table POLLOPTIONS...");
        String query = "" +
                "CREATE TABLE POLLOPTIONS" +
                "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                "optionTitle VARCHAR(100) NOT NULL," +
                "optionLink VARCHAR(150) NOT NULL," +
                "pollID BIGINT," +
                "votesCount BIGINT," +
                "FOREIGN KEY (pollID) REFERENCES POLLS(id)" +
                ")";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.execute();
        System.out.println("Table POLLOPTIONS created.");
        ps.close();
    }

    @Override
    public boolean isTableEmpty() throws SQLException {
        String query = "SELECT * FROM POLLOPTIONS FETCH FIRST 1 ROWS ONLY";
        PreparedStatement ps = connection.prepareStatement(query);
        boolean isEmpty = !ps.executeQuery().next();
        ps.close();
        return isEmpty;
    }

    @Override
    public void fillTable() throws SQLException {
        System.out.println("Inserting into table POLLOPTIONS...");

        Object[][] data = new Object[][] {
                new Object[] { "The Beatles", "https://www.youtube.com/watch?v=z9ypq6_5bsg&ab_channel=SOADShaunxp", 1L,
                        152L },
                new Object[] { "The Platters",
                        "https://www.youtube.com/watch?v=H2di83WAOhU&ab_channel=OldManCrankyCane", 1L, 60L },
                new Object[] { "The Beach Boys", "https://www.youtube.com/watch?v=2s4slliAtQU", 1L, 151L },
                new Object[] { "The Four Seasons",
                        "https://www.youtube.com/watch?v=y8yvnqHmFds&ab_channel=wolverine9391", 1L, 20L },
                new Object[] { "The Marcels", "https://www.youtube.com/watch?v=qoi3TH59ZEs&ab_channel=MANNYMORA", 1L,
                        33L },
                new Object[] { "The Everly Brothers", "https://www.youtube.com/watch?v=tbU3zdAgiX8&ab_channel=meremrah",
                        1L, 26L },
                new Object[] { "The Mamas And The Papas",
                        "https://www.youtube.com/watch?v=N-aK6JnyFmk&ab_channel=FolkExperience", 1L, 21L },
        };

        for (Object[] row : data) {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO POLLOPTIONS (optionTitle, optionLink, pollID, votesCount) values (?, ?, ?, ?)");
            ps.setString(1, (String) row[0]);
            ps.setString(2, (String) row[1]);
            ps.setLong(3, (Long) row[2]);
            ps.setLong(4, (Long) row[3]);
            ps.executeUpdate();
            ps.close();
        }
        System.out.println("Inserted into table POLLS.");

    }

}
