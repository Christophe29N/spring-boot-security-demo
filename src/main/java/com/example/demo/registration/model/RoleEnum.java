package com.example.demo.registration.model;

import java.util.HashMap;
import java.util.Map;

public enum RoleEnum {
    ADMIN(0,"Administrateur"),
    CONTRIBUTOR(1,"Contributeur"),
    MODERATOR(2,"Modérateur"),
    READER(3,"Lecteur");
    
    private int index;
    private String value;

    RoleEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
        
    public int getIndex() {
        return index;
    }

    private static final Map<Integer, RoleEnum> MAPByIndex = new HashMap<>();

    static {
        for (RoleEnum role : RoleEnum.values()) {
            MAPByIndex.put(role.getIndex(), role);
        }
    }

    public static RoleEnum fromIndex(int index) {
        return MAPByIndex.get(index);
    }
    
    @Override
    public String toString() {
        return this.name()+ " (" + this.getValue()+")";
    }   
}