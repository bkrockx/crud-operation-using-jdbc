import java.io.*;
import java.sql.*;
import java.util.*;

public class DbConnection {
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{
		Connection con = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true){
			
			System.out.println("Welcome To Employee Management System");
			System.out.println("Enter your choice");
			
			Properties prop = new Properties();
			OutputStream output  = null;
			
			try{
				output = new FileOutputStream("config.properties");
				
				prop.setProperty("Enter_1","1_show employee");
				prop.setProperty("Enter_2","2_Create new employee");
				prop.setProperty("Enter_3","3_Delete employee");
				prop.setProperty("Enter_4","4_update employee");
				prop.setProperty("Enter_5","5_quit");
				
				prop.store(output,null);
			
			}catch(IOException e){
				e.printStackTrace();
			}
			
			
			Properties property = new Properties();
			InputStream input = null;
			
			try{
				input = new FileInputStream("config.properties");
				
				property.load(input);
				
				System.out.println(property.getProperty("Enter_1"));
				System.out.println(property.getProperty("Enter_2"));
				System.out.println(property.getProperty("Enter_3"));
				System.out.println(property.getProperty("Enter_4"));
				System.out.println(property.getProperty("Enter_5"));
				
			}catch(IOException e){
				e.printStackTrace();
			}
			
			
			int choice = 0;
			try{
				String str = br.readLine();
				int ch = Integer.parseInt(str);
				choice = ch;
			}
			catch(NumberFormatException e){
				System.out.println("Please enter a valid digit");
				try {
				    Thread.sleep(700);
				} catch ( java.lang.InterruptedException ie) {
				    System.out.println(ie);
				}
				continue;
			}
		
		
			if(choice==2){
				PreparedStatement pst = null;
				try{
					con = Dbcon.Getconnection();
					String query = "insert into employee(id,name,department) values(?,?,?)";
					pst = con.prepareStatement(query);
					int id;
					String str1,str2,str3;
					System.out.println("enter id,name,department");
					str1 = br.readLine();
					id = Integer.parseInt(str1);
					str2 = br.readLine();
					str3 = br.readLine();
					
					pst.setInt(1,id);
					pst.setString(2,str2);
					pst.setString(3,str3);
					
					pst.executeUpdate();
					//System.out.println(count);
					System.out.println("Press Y to continue");
					String s1 = br.readLine();
					if(s1.equalsIgnoreCase("Y")){
						continue;
					}
				}
				catch (Exception e) {
		            e.printStackTrace();
		        }
				finally{
		            try{
		                if(pst != null) pst.close();
		                if(con != null) con.close();
		            } catch(Exception ex){}
		        }
				
			}
			
			else if(choice==1){
				con = Dbcon.Getconnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select id,name,department from employee");
				while(rs.next()){
					System.out.println(rs.getInt("id"));
					System.out.println(rs.getString("name"));
					System.out.println(rs.getString("department"));
				}
				System.out.println("Press Y to continue");
				String s1 = br.readLine();
				if(s1.equalsIgnoreCase("Y")){
					continue;
				}
			}
			else if(choice==3){
				PreparedStatement pst1 = null;
				try{
					con = Dbcon.Getconnection();
					pst1 = con.prepareStatement("DELETE FROM employee WHERE name=?");
					System.out.println("Enter the name of the employee you want to delete");
					String name = br.readLine();
					pst1.setString(1,name);
					pst1.executeUpdate();
					
					System.out.println("Press Y to continue");
					String s1 = br.readLine();
					if(s1.equalsIgnoreCase("Y")){
						continue;
					}
					
				}
				catch (Exception e) {
		            e.printStackTrace();
		        }
				finally {
		            if (pst1 != null) {
		                try {
		                    pst1.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
				}
			}
			else if(choice==4){
				Statement st = null;
				try{
					con = Dbcon.Getconnection();
					st = con.createStatement();
					String str1,str2,str3;
					System.out.println("Enter the id of employee you to perform update on it");
					str1 = br.readLine();
					int id = Integer.parseInt(str1);
					str2 = br.readLine();
					str3 = br.readLine();
					
					String query = "UPDATE employee SET name='"+str2+"',department='"+str3+"' WHERE id="+id;
					//System.out.println(query);
					int count = st.executeUpdate(query);
				
					//System.out.println(count);
					
					
					System.out.println("Press Y to continue");
					String s1 = br.readLine();
					if(s1.equalsIgnoreCase("Y")){
						continue;
					}
					
				}
				catch (Exception e) {
		            e.printStackTrace();
		        }
				finally {
		            if (st != null) {
		                try {
		                    st.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
				}
				
			}
			else{
				System.exit(0);
			}
		}
	}	
}

class Dbcon{
	public static Connection Getconnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&password=Tpg@1234");
		return con;
	}
}