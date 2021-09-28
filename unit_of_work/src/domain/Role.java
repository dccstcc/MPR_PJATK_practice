package domain;

import java.util.*;

public class Role extends Entity {

	private String name;
	
	private List<Privilege> privileges;
	private List<User> users;
	
	public Role()
	{
		privileges=new ArrayList<Privilege>();
		users= new ArrayList<User>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
