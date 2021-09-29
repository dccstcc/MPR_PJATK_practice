package pl.edu.pjatk.mpr.lab5.model;

public class ClientDetails {
    private long id;
    private String login;
    private String name;
    private String surname;
    private int age;
    

    public ClientDetails(long id, String login, String name, String surname, int age) {
		super();
		this.id = id;
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.age = age;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
