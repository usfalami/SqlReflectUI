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
import usf.java.sqlreflect.connection.manager.ConnectionManager;
import usf.java.sqlreflect.connection.manager.SimpleConnectionManager;
import usf.java.sqlreflect.connection.provider.ConnectionProvider;
import usf.java.sqlreflect.connection.provider.SimpleConnectionProvider;
import usf.java.sqlreflect.mapper.RowMapper;
import usf.java.sqlreflect.reflect.ActionTimer;
import usf.java.sqlreflect.reflect.scanner.ArgumentScanner;
import usf.java.sqlreflect.reflect.scanner.ColumnScanner;
import usf.java.sqlreflect.reflect.scanner.DatabaseScanner;
import usf.java.sqlreflect.reflect.scanner.HeaderScanner;
import usf.java.sqlreflect.reflect.scanner.NativeFunctionScanner;
import usf.java.sqlreflect.reflect.scanner.PrimaryKeyScanner;
import usf.java.sqlreflect.reflect.scanner.ProcedureScanner;
import usf.java.sqlreflect.reflect.scanner.RowScanner;
import usf.java.sqlreflect.reflect.scanner.TableScanner;
import usf.java.sqlreflect.server.Env;
import usf.java.sqlreflect.server.Server;
import usf.java.sqlreflect.server.User;
import usf.java.sqlreflect.sql.item.Argument;
import usf.java.sqlreflect.sql.item.Column;
import usf.java.sqlreflect.sql.item.Database;
import usf.java.sqlreflect.sql.item.Header;
import usf.java.sqlreflect.sql.item.PrimaryKey;
import usf.java.sqlreflect.sql.item.Procedure;
import usf.java.sqlreflect.sql.item.Row;
import usf.java.sqlreflect.sql.item.Table;
import usf.java.sqlreflect.sql.type.NativeFunctions;
import usf.java.sqlreflect.sql.type.TableTypes;

@Path("")
public class ConsultService {

	private static ConnectionManager cm;

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("databases")
	public Response<Database> getDatabases(@QueryParam("databasePattern") String databasePattern) {
		System.out.println("DATABASES : " + "database="+databasePattern);
		Response<Database> res = new Response<Database>();
		CustomAdapter<Database> adapter = new CustomAdapter<Database>();
		try {
			new DatabaseScanner(cm).run(adapter);
			res = adaptToReponse(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ActionTimer ap = adapter.getActionTimer().getTimers().iterator().next();
			if(ap!= null)
				System.out.println("Elapsed Time : "+ ap.duration() + "ms");
		}
		return res;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("tables")
	public Response<Table> getTables(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern){
		System.out.println("TABLES : " + "database="+databasePattern + " & table=" + tablePattern);
		Response<Table> res = new Response<Table>();
		CustomAdapter<Table> adapter = new CustomAdapter<Table>();
		try {
			new TableScanner(cm).set(databasePattern, tablePattern, false).run(adapter);
			res = adaptToReponse(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ActionTimer ap = adapter.getActionTimer().getTimers().iterator().next();
			if(ap!= null)
				System.out.println("Elapsed Time : "+ ap.duration() + "ms");
		}
		return res;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("views")
	public Response<Table> getViews(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("viewPattern") String viewPattern){
		System.out.println("TABLES : " + "database="+databasePattern + " & view=" + viewPattern);
		Response<Table> res = new Response<Table>();
		CustomAdapter<Table> adapter = new CustomAdapter<Table>();
		try {
			new TableScanner(cm).set(databasePattern, viewPattern, false, TableTypes.VIEW).run(adapter);
			res = adaptToReponse(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ActionTimer ap = adapter.getActionTimer().getTimers().iterator().next();
			if(ap!= null)
				System.out.println("Elapsed Time : "+ ap.duration() + "ms");
		}
		return res;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("columns")
	public Response<Column> getColumns(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern,
			@QueryParam("columnPattern") String columnPattern){
		System.out.println("COLUMNS : " + "database="+databasePattern + " & table=" + tablePattern + " & column="+columnPattern);
		
		Response<Column> res = new Response<Column>();
		CustomAdapter<Column> adapter = new CustomAdapter<Column>();
		try {
			new ColumnScanner(cm).set(databasePattern, tablePattern, columnPattern).run(adapter);
			res = adaptToReponse(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ActionTimer ap = adapter.getActionTimer().getTimers().iterator().next();
			if(ap!= null)
				System.out.println("Elapsed Time : "+ ap.duration() + "ms");
		}
		return res;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("primaryKeys")
	public Response<PrimaryKey> getPrimaryKeys(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern){
		System.out.println("PRIMARY_KEYS : " + "database="+databasePattern + " & table=" + tablePattern);
		
		Response<PrimaryKey> res = new Response<PrimaryKey>();
		CustomAdapter<PrimaryKey> adapter = new CustomAdapter<PrimaryKey>();
		try {
			new PrimaryKeyScanner(cm).set(databasePattern, tablePattern).run(adapter);
			res = adaptToReponse(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ActionTimer ap = adapter.getActionTimer().getTimers().iterator().next();
			if(ap!= null)
				System.out.println("Elapsed Time : "+ ap.duration() + "ms");
		}
		return res;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("procedures")
	public Response<Procedure> getProcedures(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("procedurePattern") String procedurePattern){
		System.out.println("PROCEDURES : " + "database="+databasePattern + " & procedure=" + procedurePattern);
		Response<Procedure> res = new Response<Procedure>();
		CustomAdapter<Procedure> adapter = new CustomAdapter<Procedure>();
		try {
			new ProcedureScanner(cm).set(databasePattern, procedurePattern, false).run(adapter);
			res = adaptToReponse(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ActionTimer ap = adapter.getActionTimer().getTimers().iterator().next();
			if(ap!= null)
				System.out.println("Elapsed Time : "+ ap.duration() + "ms");
		}
		return res;
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("arguments")
	public Response<Argument> getArguments(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("procedurePattern") String procedurePattern,
			@QueryParam("argumentPattern") String argumentPattern){
		System.out.println("COLUMNS : " + "database="+databasePattern + " & procedure=" + procedurePattern + " & column="+argumentPattern);
		
		Response<Argument> res = new Response<Argument>();
		CustomAdapter<Argument> adapter = new CustomAdapter<Argument>();
		try {
			new ArgumentScanner(cm).set(databasePattern, procedurePattern, argumentPattern).run(adapter);
			res = adaptToReponse(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ActionTimer ap = adapter.getActionTimer().getTimers().iterator().next();
			if(ap!= null)
				System.out.println("Elapsed Time : "+ ap.duration() + "ms");
		}
		return res;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("nativeFunctions")
	public Response<String> getNativeFunction(
			@QueryParam("nativeFunction") String nativeFunction){
		System.out.println("NativeFunction : " + "pattern="+nativeFunction);
		
		Response<String> res = new Response<String>();
		CustomAdapter<String> adapter = new CustomAdapter<String>();
		try {
			NativeFunctions nf = NativeFunctions.valueOf(nativeFunction);
			new NativeFunctionScanner(cm).set(nf).run(adapter);
			res = adaptToReponse(adapter, "Name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ActionTimer ap = adapter.getActionTimer().getTimers().iterator().next();
			if(ap!= null)
				System.out.println("Elapsed Time : "+ ap.duration() + "ms");
		}
		return res;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("rows")
	public Response<Row> getRows(@QueryParam("query") String query){
		System.out.println("ROWS : " + "query=" + query);
		Response<Row> res = new Response<Row>();
		CustomAdapter<Row> adapter = new CustomAdapter<Row>();
		try {
			new RowScanner<Void, Row>(cm, new RowMapper()).set(query).run(adapter);
			res = adaptToReponse(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ActionTimer ap = adapter.getActionTimer().getTimers().iterator().next();
			if(ap!= null)
				System.out.println("Elapsed Time : "+ ap.duration() + "ms");
		}
		return res;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("headers")
	public Response<Header> getHeaders(@QueryParam("query") String query){
		System.out.println("HEADERS : " + "query=" + query);
		Response<Header> res = new Response<Header>();
		CustomAdapter<Header> adapter = new CustomAdapter<Header>();
		try {
			new HeaderScanner(cm).set(query).run(adapter);
			res = adaptToReponse(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ActionTimer ap = adapter.getActionTimer().getTimers().iterator().next();
			if(ap!= null)
				System.out.println("Elapsed Time : "+ ap.duration() + "ms");
		}
		return res;
	}
	
	private <T> Response<T> adaptToReponse(CustomAdapter<T> adapter, String... columns) {
		Response<T> res = new Response<T>();
		if(columns != null && columns.length > 0)
			res.setColumns(Arrays.asList(columns));
		else
			res.setColumns(Arrays.asList(adapter.getMapper().getColumnNames()));
		res.setList(adapter.getList());
		res.setTimer(adapter.getActionTimer());
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

			ConnectionProvider cp = new SimpleConnectionProvider(server, env, user);
			cm = new SimpleConnectionManager(cp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}