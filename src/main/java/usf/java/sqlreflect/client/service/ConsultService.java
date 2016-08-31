package usf.java.sqlreflect.client.service;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import usf.java.sqlreflect.client.CustomAdapter;
import usf.java.sqlreflect.client.Response;
import usf.java.sqlreflect.connection.User;
import usf.java.sqlreflect.connection.manager.ConnectionManager;
import usf.java.sqlreflect.connection.manager.SimpleConnectionManager;
import usf.java.sqlreflect.connection.provider.ConnectionProvider;
import usf.java.sqlreflect.connection.provider.SimpleConnectionProvider;
import usf.java.sqlreflect.field.Column;
import usf.java.sqlreflect.field.Database;
import usf.java.sqlreflect.field.Env;
import usf.java.sqlreflect.field.Header;
import usf.java.sqlreflect.field.Procedure;
import usf.java.sqlreflect.field.Row;
import usf.java.sqlreflect.field.Table;
import usf.java.sqlreflect.mapper.RowMapper;
import usf.java.sqlreflect.reflect.scanner.ColumnScanner;
import usf.java.sqlreflect.reflect.scanner.DatabaseScanner;
import usf.java.sqlreflect.reflect.scanner.HasColumn;
import usf.java.sqlreflect.reflect.scanner.HeaderScanner;
import usf.java.sqlreflect.reflect.scanner.ProcedureScanner;
import usf.java.sqlreflect.reflect.scanner.RowScanner;
import usf.java.sqlreflect.reflect.scanner.TableScanner;
import usf.java.sqlreflect.server.Server;

@Path("")
public class ConsultService {

	private static ConnectionManager cm;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("databases")
	public Response<Database> getDatabases(@QueryParam("databasePattern") String databasePattern) {
		System.out.println("DATABASES : " + "databasePattern="+databasePattern);
		Response<Database> res = new Response<Database>();
		CustomAdapter<Database> adapter = new CustomAdapter<Database>();
		try {
			new DatabaseScanner(cm).run(adapter);

			res.setColumns(Arrays.asList(adapter.getMapper().getColumnNames()));
			res.setList(adapter.getList());
			res.setTimePerform(adapter.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("tables")
	public Response<Table> getTables(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern){
		System.out.println("TABLES : " + "databasePattern="+databasePattern + " & tablePattern=" + tablePattern);
		Response<Table> res = new Response<Table>();
		CustomAdapter<Table> adapter = new CustomAdapter<Table>();
		try {
			new TableScanner(cm).set(databasePattern, tablePattern, false).run(adapter);
			
			res.setColumns(Arrays.asList(adapter.getMapper().getColumnNames()));
			res.setList(adapter.getList());
			res.setTimePerform(adapter.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("procedures")
	public Response<Procedure> getProcedures(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("procedurePattern") String procedurePattern){
		System.out.println("PROCEDURES : " + "databasePattern="+databasePattern + " & procedurePattern=" + procedurePattern);
		Response<Procedure> res = new Response<Procedure>();
		CustomAdapter<Procedure> adapter = new CustomAdapter<Procedure>();
		try {
			new ProcedureScanner(cm).set(databasePattern, procedurePattern, false).run(adapter);
			
			res.setColumns(Arrays.asList(adapter.getMapper().getColumnNames()));
			res.setList(adapter.getList());
			res.setTimePerform(adapter.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("columns")
	public Response<Column> getColumns(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("parentPattern") String parentPattern,
			@QueryParam("columnPattern") String columnPattern,
			@QueryParam("columnParent") String columnParent){
		System.out.println("COLUMNS : " + "databasePattern="+databasePattern + " & " + columnParent + "=" + parentPattern + " & columnPattern="+columnParent);
		
		Response<Column> res = new Response<Column>();
		CustomAdapter<Column> adapter = new CustomAdapter<Column>();
		try {
			HasColumn parent = HasColumn.valueOf(columnParent);
			if(parent != null) {
				new ColumnScanner(cm, parent).set(databasePattern, parentPattern, columnPattern).run(adapter);
				
				res.setColumns(Arrays.asList(adapter.getMapper().getColumnNames()));
				res.setList(adapter.getList());
				res.setTimePerform(adapter.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("rows")
	public Response<Row> getRows(@QueryParam("query") String query){
		System.out.println("ROWS : " + "query=" + query);
		Response<Row> res = new Response<Row>();
		CustomAdapter<Row> adapter = new CustomAdapter<Row>();
		try {
			new RowScanner<Row>(cm, new RowMapper()).set(query).run(adapter);
			
			res.setColumns(Arrays.asList(adapter.getMapper().getColumnNames()));
			res.setList(adapter.getList());
			res.setTimePerform(adapter.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("headers")
	public Response<Header> getHeaders(@QueryParam("query") String query){
		System.out.println("HEADERS : " + "query=" + query);
		Response<Header> res = new Response<Header>();
		CustomAdapter<Header> adapter = new CustomAdapter<Header>();
		try {
			new HeaderScanner(cm).set(query).run(adapter);
			
			res.setColumns(Arrays.asList(adapter.getMapper().getColumnNames()));
			res.setList(adapter.getList());
			res.setTimePerform(adapter.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	

	static { // user context listner
		try {

			InputStream inputStream  = ConsultService.class.getClassLoader().getResourceAsStream("env.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			
			Server server = (Server) Class.forName(properties.getProperty("server")).newInstance();
			
			Class.forName(server.getDriver());
			
			Env env = new Env(properties);
			User user = new User(properties);

			ConnectionProvider cp = new SimpleConnectionProvider(server, env);
			cm = new SimpleConnectionManager(cp, server, user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}