package usf.java.sqlreflect.client;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import usf.java.sqlreflect.adapter.ListAdapter;
import usf.java.sqlreflect.mapper.Mapper;
import usf.java.sqlreflect.sql.entry.data.Header;
import usf.java.sqlreflect.sql.entry.data.Row;
import usf.java.sqlreflect.sql.entry.item.Argument;
import usf.java.sqlreflect.sql.entry.item.Column;
import usf.java.sqlreflect.sql.entry.item.Database;
import usf.java.sqlreflect.sql.entry.item.Procedure;
import usf.java.sqlreflect.sql.entry.item.Table;

@XmlRootElement
@XmlSeeAlso({Database.class, Table.class, Column.class, Procedure.class, Argument.class, Row.class, Header.class})
public class ResponseAdapter<T> extends ListAdapter<T> {
	
	private List<String> columns;

	public ResponseAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void prepare(Mapper<T> mapper) {
		if(mapper != null)
		this.columns = Arrays.asList(mapper.getColumnNames());
	}
	
	public List<String> getColumns() {
		return columns;
	}
	
	public void setColumn(String columns){
		this.columns = Arrays.asList(columns);
	}

	
}
