package usf.java.sqlreflect.client;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import usf.java.sqlreflect.adapter.ListAdapter;
import usf.java.sqlreflect.mapper.Mapper;
import usf.java.sqlreflect.reflect.ActionTimer;
import usf.java.sqlreflect.sql.entry.Argument;
import usf.java.sqlreflect.sql.entry.Column;
import usf.java.sqlreflect.sql.entry.Database;
import usf.java.sqlreflect.sql.entry.Entry;
import usf.java.sqlreflect.sql.entry.Header;
import usf.java.sqlreflect.sql.entry.Procedure;
import usf.java.sqlreflect.sql.entry.Table;

@XmlRootElement
@XmlSeeAlso({Entry.class, Database.class, Table.class, Column.class, Procedure.class, Argument.class, Header.class, ActionTimer.class})
public class ResponseAdapter<T> extends ListAdapter<T> {
	
	private List<String> columns;

	public ResponseAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void prepare(Mapper<T> mapper) {
		if(mapper != null)
			setColumn(mapper.getColumnNames());
	}
	
	public List<String> getColumns() {
		return columns;
	}
	
	public void setColumn(String... columns){
		this.columns = Arrays.asList(columns);
	}

	
}
