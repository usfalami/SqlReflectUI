package usf.java.sqlreflect.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import usf.java.sqlreflect.Utils;
import usf.java.sqlreflect.adapter.ListAdapter;
import usf.java.sqlreflect.mapper.Property;
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
	
	private Collection<String> columns;
	
	@Override
	public void prepare(Class<T> clazz, Collection<Property> headers) {
		if(!Utils.isEmptyCollection(headers)) {
			columns = new ArrayList<String>();
			for(Property header : headers)
				columns.add(header.getName());
		}
	}

	public void setColumns(String... columnNames) {
		columns = Arrays.asList(columnNames);
	}
	public Collection<String> getColumns() {
		return columns;
	}
}
