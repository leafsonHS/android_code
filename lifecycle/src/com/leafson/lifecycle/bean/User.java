package com.leafson.lifecycle.bean;

import java.io.Serializable;

import com.leafson.lifecycle.db.Column;
import com.leafson.lifecycle.db.DBHelper;
import com.leafson.lifecycle.db.ID;
import com.leafson.lifecycle.db.TableName;

@TableName(DBHelper.TABLE_USER)
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ID(autoincrement = true)
	@Column(DBHelper.TABLE_ID)
	private Long id;
	@Column(DBHelper.TABLE_USER_NAME)
	private String username;
	@Column(DBHelper.TABLE_USER_PAZZWORDY)
	private String password;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setId(String id) {
		
		this.id = Long.valueOf(id);
		
	}
		
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
