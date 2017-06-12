package com.renms.common.pool;

import org.apache.commons.dbcp.BasicDataSource;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class ZZBDataSource extends BasicDataSource {

	@Override
	public synchronized void setPassword(String password) {
		super.setPassword(decPwd(password));
	}

	/**
	 * 对数据库密码进行解密(不绑定MAC)
	 * @param passwd
	 */
	public String decPwd(String passwd) {
		PBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword("jesong");
		return encryptor.decrypt(passwd);
	}
	
}
