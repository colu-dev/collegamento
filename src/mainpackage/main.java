package mainpackage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class main {
	private static String URL = "jdbc:mysql://localhost:3306/gestione_scuola";
	private static String USER = "root";
	private static String PASSWORD = "Simox00512!0";
	public static void main(String[] args) {
		
		Scanner scanner=new Scanner(System.in);
         int scelta=0;
         System.out.println("Benvenuto ");
         System.out.println("1.Inserisi studente ");
         System.out.println("2.Visualizza studenti ");
         System.out.println("3.Elimina studenti ");
         System.out.println("4.Aggiorna dati studente ");
         
       

		do {
			System.out.println("Scelta:");
		    scelta=scanner.nextInt();
			switch(scelta) {
			 case 1:
				 System.out.println("Inserisci nome: ");
				 String nome=scanner.next();
				 System.out.println("Inserisci cognome: ");
				 String cognome=scanner.next();
				 System.out.println("Inserisci email: ");
				 String email=scanner.next();
				 inseriscialunno(nome,cognome,email,18);
				 
				 
				break;
			 case 2:
				 visualizzastudenti();
				break;
			 case 3:
				 System.out.println("Elimina studente");
				break;
			 case 4:
				 System.out.println("Aggiorna studente");
				break;
			}
		}while(scelta>=1 && scelta<=4);
	}
	public static int inseriscialunno(String nome,String cognome,String email,int eta) {
		 String sql = "INSERT INTO alunni (nome, cognome, email,eta) VALUES (?, ?, ?, ?)";
		    try (Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
		         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
		        pstmt.setString(1, nome);
		        pstmt.setString(2, cognome);
		        pstmt.setString(3, email);
		        pstmt.setInt(4, eta);

		        int affectedRows = pstmt.executeUpdate();
		        if (affectedRows == 0) {
		            throw new SQLException("Creazione alunno fallita, nessuna riga aggiunta.");
		        }

		        // Recupero la chiave generata (ID auto-increment)
		        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		              
						return generatedKeys.getInt(1);
		            } else {
		                throw new SQLException("Creazione alunno fallita, ID non recuperato.");
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return -1; 
	}
public static void visualizzastudenti() {
	String query="SELECT * FROM alunni";
	 try (Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        System.out.println("Elenco alunni:");
	        while (rs.next()) {
	            int idalunni = rs.getInt("idalunni");
	            String nome = rs.getString("nome");
	            String cognome = rs.getString("cognome");
	            String email = rs.getString("email");
	            int eta = rs.getInt("eta");

	            System.out.printf("ID: %d | Nome: %s | Cognome: %s | Email: %s | eta: %d%n",
	                    idalunni, nome, cognome, email, eta);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

}
}
