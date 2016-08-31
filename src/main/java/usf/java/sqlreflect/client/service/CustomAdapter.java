package usf.java.sqlreflect.client.service;

import usf.java.sqlreflect.adapter.ListAdapter;
import usf.java.sqlreflect.mapper.Mapper;

public class CustomAdapter<T> extends ListAdapter<T> {
	
	private Mapper<T> mapper;
	
	@Override
	public void prepare(Mapper<T> mapper) {
		super.prepare(mapper);
		this.mapper = mapper;
	}

	public Mapper<T> getMapper() {
		return mapper;
	}

}
