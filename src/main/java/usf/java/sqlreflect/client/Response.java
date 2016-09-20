package usf.java.sqlreflect.client;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import usf.java.sqlreflect.item.Argument;
import usf.java.sqlreflect.item.Column;
import usf.java.sqlreflect.item.Database;
import usf.java.sqlreflect.item.Header;
import usf.java.sqlreflect.item.Procedure;
import usf.java.sqlreflect.item.Row;
import usf.java.sqlreflect.item.Table;
import usf.java.sqlreflect.reflect.TimePerform;

@XmlRootElement
@XmlSeeAlso({Database.class, Table.class, Column.class, Procedure.class, Argument.class, Row.class, Header.class})
public class Response<T> {
	
	private List<String> columns;
	private List<T> list;
	private TimePerform time = new TimePerform();

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

	public List<T> getList() {
		return list;
	}
	
	@XmlElementWrapper(name="items")
	@XmlElement(name="item")
	public void setList(List<T> list) {
		this.list = list;
	}

	public TimePerform getTimePerform() {
		return time;
	}

	public void setTimePerform(TimePerform time) {
		this.time = time;
	}
	
}
