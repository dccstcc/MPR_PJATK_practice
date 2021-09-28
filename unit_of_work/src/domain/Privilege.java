package domain;

import java.util.ArrayList;
import java.util.List;

public class Privilege extends Entity {

	private String name;
	
	private List<Role> roles;

	public Privilege()
	{
		roles = new ArrayList<Role>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
}
