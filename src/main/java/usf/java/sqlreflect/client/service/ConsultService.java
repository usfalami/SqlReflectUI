package usf.java.sqlreflect.client.service;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.resource.Singleton;

import usf.java.sqlreflect.client.ResponseAdapter;
import usf.java.sqlreflect.connection.manager.ConnectionManagerImpl;
import usf.java.sqlreflect.connection.provider.ConnectionProvider;
import usf.java.sqlreflect.connection.provider.SimpleConnectionProvider;
import usf.java.sqlreflect.mapper.Mapper;
import usf.java.sqlreflect.mapper.entry.EntryMapper;
import usf.java.sqlreflect.reflect.Utils;
import usf.java.sqlreflect.reflect.scanner.NativeFunctionScanner;
import usf.java.sqlreflect.reflect.scanner.data.HeaderScanner;
import usf.java.sqlreflect.reflect.scanner.data.RowScanner;
import usf.java.sqlreflect.reflect.scanner.field.ArgumentScanner;
import usf.java.sqlreflect.reflect.scanner.field.ColumnScanner;
import usf.java.sqlreflect.reflect.scanner.field.DatabaseScanner;
import usf.java.sqlreflect.reflect.scanner.field.ImportedKeyScanner;
import usf.java.sqlreflect.reflect.scanner.field.PrimaryKeyScanner;
import usf.java.sqlreflect.reflect.scanner.field.ProcedureScanner;
import usf.java.sqlreflect.reflect.scanner.field.TableScanner;
import usf.java.sqlreflect.server.Server;
import usf.java.sqlreflect.sql.entry.Argument;
import usf.java.sqlreflect.sql.entry.Column;
import usf.java.sqlreflect.sql.entry.Database;
import usf.java.sqlreflect.sql.entry.Entry;
import usf.java.sqlreflect.sql.entry.Header;
import usf.java.sqlreflect.sql.entry.ImportedKey;
import usf.java.sqlreflect.sql.entry.PrimaryKey;
import usf.java.sqlreflect.sql.entry.Procedure;
import usf.java.sqlreflect.sql.entry.Table;
import usf.java.sqlreflect.sql.type.NativeFunctions;
import usf.java.sqlreflect.sql.type.TableTypes;
@Path("")
@Singleton
public class ConsultService {

	@GET
	@Path("databases")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseAdapter<Database> getDatabases(@QueryParam("databasePattern") String databasePattern) {
		showDetail("DatabaseScanner", databasePattern);
		ResponseAdapter<Database> adapter = new ResponseAdapter<Database>();
		try {
			new DatabaseScanner(new ConnectionManagerImpl(cp, server)).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail("DatabaseScanner", adapter);
		}
		return adapter;
	}
	
	@GET
	@Path("tables")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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
			new TableScanner(new ConnectionManagerImpl(cp, server)).set(databasePattern, tablePattern, types).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail("TableScanner", adapter);
		}
		return adapter;
	}
	
	@GET
	@Path("views")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseAdapter<Table> getViews(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern){
		showDetail("TableScanner", databasePattern, tablePattern);
		ResponseAdapter<Table> adapter = new ResponseAdapter<Table>();
		try {
			new TableScanner(new ConnectionManagerImpl(cp, server)).set(databasePattern, tablePattern, TableTypes.VIEW).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail("TableScanner", adapter);
		}
		return adapter;
	}
	
	@GET
	@Path("columns")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseAdapter<Column> getColumns(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern,
			@QueryParam("columnPattern") String columnPattern){
		showDetail("ColumnScanner", databasePattern, tablePattern, columnPattern);
		ResponseAdapter<Column> adapter = new ResponseAdapter<Column>();
		try {
			new ColumnScanner(new ConnectionManagerImpl(cp, server)).set(databasePattern, tablePattern, columnPattern).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail("ColumnScanner", adapter);
		}
		return adapter;
	}
	
	@GET
	@Path("primaryKeys")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseAdapter<PrimaryKey> getPrimaryKeys(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern){
		showDetail("PrimaryKeyScanner", databasePattern, tablePattern);
		ResponseAdapter<PrimaryKey> adapter = new ResponseAdapter<PrimaryKey>();
		try {
			new PrimaryKeyScanner(new ConnectionManagerImpl(cp, server)).set(databasePattern, tablePattern).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail("PrimaryKeyScanner", adapter);
		}
		return adapter;
	}
	
	@GET
	@Path("importedKeys")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseAdapter<ImportedKey> getImportedKeys(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("tablePattern") String tablePattern){
		showDetail("ImportedKeysScanner", databasePattern, tablePattern);
		ResponseAdapter<ImportedKey> adapter = new ResponseAdapter<ImportedKey>();
		try {
			new ImportedKeyScanner(new ConnectionManagerImpl(cp, server)).set(databasePattern, tablePattern).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail("ImportedKeysScanner", adapter);
		}
		return adapter;
	}
	@GET
	@Path("procedures")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseAdapter<Procedure> getProcedures(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("procedurePattern") String procedurePattern){
		showDetail("ProcedureScanner", databasePattern, procedurePattern);
		ResponseAdapter<Procedure> adapter = new ResponseAdapter<Procedure>();
		try {
			new ProcedureScanner(new ConnectionManagerImpl(cp, server)).set(databasePattern, procedurePattern).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail("ProcedureScanner", adapter);
		}
		return adapter;
	}
	
	
	@GET
	@Path("arguments")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseAdapter<Argument> getArguments(
			@QueryParam("databasePattern") String databasePattern,  
			@QueryParam("procedurePattern") String procedurePattern,
			@QueryParam("argumentPattern") String argumentPattern){
		showDetail("ArgumentScanner", databasePattern, procedurePattern, argumentPattern);
		ResponseAdapter<Argument> adapter = new ResponseAdapter<Argument>();
		try {
			new ArgumentScanner(new ConnectionManagerImpl(cp, server)).set(databasePattern, procedurePattern, argumentPattern).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail("ArgumentScanner", adapter);
		}
		return adapter;
	}
	
	@GET
	@Path("nativeFunctions")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseAdapter<String> getNativeFunction(
			@QueryParam("nativeFunction") @DefaultValue("STRING") String nativeFunction){
		showDetail("NativeFunctions", nativeFunction);
		ResponseAdapter<String> adapter = new ResponseAdapter<String>();
		try {
			NativeFunctions nf = NativeFunctions.valueOf(nativeFunction);
			new NativeFunctionScanner(new ConnectionManagerImpl(cp, server)).set(nf).run(adapter);
			adapter.setColumns("Name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail("NativeFunctions", adapter);
		}
		return adapter;
	}

	@GET
	@Path("rows")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseAdapter<Entry> getRows(@QueryParam("query") String query){
		showDetail("RowScanner", query);
		ResponseAdapter<Entry> adapter = new ResponseAdapter<Entry>();
		try {
			Mapper<Entry> mapper = new EntryMapper();
			new RowScanner<Void, Entry>(new ConnectionManagerImpl(cp, server), mapper).set(query).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail("RowScanner", adapter);
		}
		return adapter;
	}
	
	@GET
	@Path("headers")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseAdapter<Header> getHeaders(@QueryParam("query") String query){
		showDetail("HeaderScanner", query);
		ResponseAdapter<Header> adapter = new ResponseAdapter<Header>();
		try {
			new HeaderScanner<Void>(new ConnectionManagerImpl(cp, server)).set(query).run(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			showDetail("HeaderScanner", adapter);
		}
		return adapter;
	}
	
	private void showDetail(String reflector, String... args) {
		System.out.print("["+ reflector + "]\t");
		if(Utils.isEmptyArray(args)) return;
		System.out.print("Query : " + (Utils.isEmptyString(args[0]) ? "*" : args[0]));
		for(int i=1; i<args.length; i++)
			System.out.print("."+ (Utils.isEmptyString(args[i]) ? "*" : args[i]));
		System.out.println();
	}
	private void showDetail(String reflector, ResponseAdapter<?> adapter) {
		System.out.print("["+ reflector + "]\t");
		System.out.println(adapter.getList().size() + " rows in " + adapter.getActionTimer().duration() + "ms");
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