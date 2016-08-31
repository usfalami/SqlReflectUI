package usf.java.sqlreflect.client.service;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import usf.java.sqlreflect.field.Column;
import usf.java.sqlreflect.field.Database;
import usf.java.sqlreflect.field.Procedure;
import usf.java.sqlreflect.field.Table;
import usf.java.sqlreflect.reflect.TimePerform;

@XmlRootElement
@XmlSeeAlso({Database.class, Table.class, Procedure.class, Column.class})
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
