package models;

public class UserGroup {
    private final String idGroup;
    private final String idUser;

    public UserGroup(String idGroup, String idUser) {
        this.idGroup = idGroup;
        this.idUser = idUser;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public String getIdUser() {
        return idUser;
    }
}
