package com.renms.common.util.dbaccess;

import lombok.extern.log4j.Log4j;

import com.renms.common.enums.DataSourceEnum;

@Log4j
public final class DataSourceHolder {
	private static ThreadLocal<DataSourceEnum> currentDBName = new ThreadLocal<DataSourceEnum>();
	
	private static DataSourceEnum DEFAULT_HASH = DataSourceEnum.anquan;

	public static DataSourceEnum determineDefault() {
		return determineDS(DEFAULT_HASH);
	}

	public static DataSourceEnum determineDS(DataSourceEnum key) {
		currentDBName.set(key);
		log.debug("determineDS:" + key);
		return key;
	}
	
	
	public static DataSourceEnum getCurrentDBName() {
		return currentDBName.get();
	}
}