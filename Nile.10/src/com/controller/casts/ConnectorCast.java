package com.controller.casts;

import com.controller.fetcher.Connector;

public class ConnectorCast {
	private Connector connector;

	public Connector convert(Object obj) {
		if(obj instanceof Connector) {
			connector = (Connector)obj;
		}else {
			connector = null;
		}
		return connector;
	}
}
