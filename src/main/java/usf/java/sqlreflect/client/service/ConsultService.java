package usf.java.sqlreflect.client.service;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import usf.java.sqlreflect.client.ResponseAdapter;
import usf.java.sqlreflect.connection.manager.SimpleConnectionManager;
import usf.java.sqlreflect.connection.provider.ConnectionProvider;
import usf.java.sqlreflect.connection.provider.SimpleConnectionProvider;
import usf.java.sqlreflect.mapper.RowMapper;
import usf.java.sqlreflect.reflect.Reflector;
import usf.java.sqlreflect.reflect.scanner.NativeFunctionScanner;
import usf.java.sqlreflect.reflect.scanner.data.HeaderScanner;
import usf.java.sqlreflect.reflect.scanner.data.RowScanner;
import usf.java.sqlreflect.reflect.scanner.field.ArgumentScanner;
import usf.java.sqlreflect.reflect.scanner.field.ColumnScanner;
import usf.java.sqlreflect.reflect.scanner.field.DatabaseScanner;
import usf.java.sqlreflect.reflect.scanner.field.PrimaryKeyScanner;
import usf.java.sqlreflect.reflect.scanner.field.ProcedureScanner;
import usf.java.sqlreflect.reflect.scanner.field.TableScanner;
import usf.java.sqlreflect.server.Server;
import usf.java.sqlreflect.sql.entry.data.Header;
import usf.java.sqlreflect.sql.entry.data.Row;
import usf.java.sqlreflect.sql.entry.item.Argument;
import usf.java.sqlreflect.sql.entry.item.Column;
import usf.java.sqlreflect.sql.entry.item.Database;
import usf.java.sqlreflect.sql.entry.item.PrimaryKey;
import usf.java.sqlreflect.sql.entry.item.Procedure;
import usf.java.sqlreflect.sql.entry.item.Table;
import usf.java.sqlreflect.sql.type.NativeFunctions;
import usf.java.sqlreflect.sql.type.TableTypes;

@Path("")
public class ConsultService {

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("databases")
	public ResponseAdapter<Database> getDatabases(@QueryParam("databasePattern") String databasePattern) {
		showDetail("DatabaseScanner", databasePattern);
		ResponseAdapter<Database> adapter = new ResponseAdapter<Database>();
		try {
			new DatabaseScanner(new SimpleConnectionManager(cp, server)).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail(adapter);
		}
		return adapter;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("tables")
	public ResponseAdapter<Table> getTables(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern, 
			@QueryParam("tableType") List<String> tableType){
		showDetail("TableScanner", databasePattern, tablePattern);
		ResponseAdapter<Table> adapter = new ResponseAdapter<Table>();
		try {
			TableTypes[] types = null;
			if(tableType != null && !tableType.isEmpty()){
				types = new TableTypes[tableType.size()];
				for(int i=0; i<tableType.size(); i++) 
					types[i] = TableTypes.valueOf(tableType.get(i));
			}
			new TableScanner(new SimpleConnectionManager(cp, server)).set(databasePattern, tablePattern, false, types).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail(adapter);
		}
		return adapter;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("views")
	public ResponseAdapter<Table> getViews(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern){
		showDetail("TableScanner", databasePattern, tablePattern);
		ResponseAdapter<Table> adapter = new ResponseAdapter<Table>();
		try {
			new TableScanner(new SimpleConnectionManager(cp, server)).set(databasePattern, tablePattern, false, TableTypes.VIEW).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail(adapter);
		}
		return adapter;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("columns")
	public ResponseAdapter<Column> getColumns(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern,
			@QueryParam("columnPattern") String columnPattern){
		showDetail("ColumnScanner", databasePattern, tablePattern, columnPattern);
		ResponseAdapter<Column> adapter = new ResponseAdapter<Column>();
		try {
			new ColumnScanner(new SimpleConnectionManager(cp, server)).set(databasePattern, tablePattern, columnPattern).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail(adapter);
		}
		return adapter;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("primaryKeys")
	public ResponseAdapter<PrimaryKey> getPrimaryKeys(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern){
		showDetail("PrimaryKeyScanner", databasePattern, tablePattern);
		ResponseAdapter<PrimaryKey> adapter = new ResponseAdapter<PrimaryKey>();
		try {
			new PrimaryKeyScanner(new SimpleConnectionManager(cp, server)).set(databasePattern, tablePattern).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail(adapter);
		}
		return adapter;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("procedures")
	public ResponseAdapter<Procedure> getProcedures(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("procedurePattern") String procedurePattern){
		showDetail("ProcedureScanner", databasePattern, procedurePattern);
		ResponseAdapter<Procedure> adapter = new ResponseAdapter<Procedure>();
		try {
			new ProcedureScanner(new SimpleConnectionManager(cp, server)).set(databasePattern, procedurePattern, false).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail(adapter);
		}
		return adapter;
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("arguments")
	public ResponseAdapter<Argument> getArguments(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("procedurePattern") String procedurePattern,
			@QueryParam("argumentPattern") String argumentPattern){
		showDetail("ArgumentScanner", databasePattern, procedurePattern, argumentPattern);
		ResponseAdapter<Argument> adapter = new ResponseAdapter<Argument>();
		try {
			new ArgumentScanner(new SimpleConnectionManager(cp, server)).set(databasePattern, procedurePattern, argumentPattern).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail(adapter);
		}
		return adapter;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("nativeFunctions")
	public ResponseAdapter<String> getNativeFunction(
			@QueryParam("nativeFunction") String nativeFunction){
		showDetail("NativeFunctions", nativeFunction);
		ResponseAdapter<String> adapter = new ResponseAdapter<String>();
		try {
			NativeFunctions nf = NativeFunctions.valueOf(nativeFunction);
			new NativeFunctionScanner(new SimpleConnectionManager(cp, server)).set(nf).run(adapter);
			adapter.setColumn("Name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail(adapter);
		}
		return adapter;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("rows")
	public ResponseAdapter<Row> getRows(@QueryParam("query") String query){
		showDetail("RowScanner", query);
		ResponseAdapter<Row> adapter = new ResponseAdapter<Row>();
		try {
			new RowScanner<Void, Row>(new SimpleConnectionManager(cp, server), new RowMapper()).set(query).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail(adapter);
		}
		return adapter;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("headers")
	public ResponseAdapter<Header> getHeaders(@QueryParam("query") String query){
		showDetail("HeaderScanner", query);
		ResponseAdapter<Header> adapter = new ResponseAdapter<Header>();
		try {
			new HeaderScanner<Void>(new SimpleConnectionManager(cp, server)).set(query).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail(adapter);
		}
		return adapter;
	}
	
	private void showDetail(String reflector, String... args) {
		System.out.print("["+ reflector + "]\t");
		if(Reflector.Utils.isEmpty(args)) return;
		System.out.print("Query : " + args[0]);
		for(int i=1; i<args.length; i++)
			System.out.print("."+args[i]);
		System.out.println();
	}
	private void showDetail(ResponseAdapter<?> adapter) {
		System.out.println(adapter.getList().size() + "rows in " + adapter.getActionTimer().duration() + "ms");
	}

	
//	server=usf.java.sqlreflect.server.*****
//
//	env.host=******
//	env.port=****
//	env.params=*******
//	env.database=******
	
//	user.login=******
//	user.password=*****
	
	private ConnectionProvider cp;
	private Server server;
	
	public ConsultService() {
		try {
			InputStream inputStream  = ConsultService.class.getClassLoader().getResourceAsStream("env.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			
			this.server = (Server) Class.forName(properties.getProperty("server")).newInstance();
			Class.forName(server.getDriver());
			
			this.cp = new SimpleConnectionProvider(server, properties);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}