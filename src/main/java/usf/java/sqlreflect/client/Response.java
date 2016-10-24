package usf.java.sqlreflect.client;

import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import usf.java.sqlreflect.reflect.ActionTimer;
import usf.java.sqlreflect.sql.entry.data.Header;
import usf.java.sqlreflect.sql.entry.data.Row;
import usf.java.sqlreflect.sql.entry.item.Argument;
import usf.java.sqlreflect.sql.entry.item.Column;
import usf.java.sqlreflect.sql.entry.item.Database;
import usf.java.sqlreflect.sql.entry.item.Procedure;
import usf.java.sqlreflect.sql.entry.item.Table;

@XmlRootElement
@XmlSeeAlso({Database.class, Table.class, Column.class, Procedure.class, Argument.class, Row.class, Header.class})
public class Response<T> {
	
	private List<String> columns;
	private Collection<T> list;
	private ActionTimer time = new ActionTimer();

	public Response() {
		// TODO Auto-generated constructor stub
	}

	public Response(List<T> list) {
		super();
		this.list = list;
	}

	@XmlElementWrapper(name="columns")
	@XmlElement(name="column")
	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public Collection<T> getList() {
		return list;
	}
	
	@XmlElementWrapper(name="items")
	@XmlElement(name="item")
	public void setList(Collection<T> list) {
		this.list = list;
	}

	public ActionTimer getTimer() {
		return time;
	}

	public void setTimer(ActionTimer time) {
		this.time = time;
	}
	
}
