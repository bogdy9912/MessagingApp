package models;

public class User {

    private  String id;
    private final String name;
    private final String password;

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return name + ": " + id + ", " + password;
    }
/*
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
   /*     if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return user.id == id;
    }*/


}
