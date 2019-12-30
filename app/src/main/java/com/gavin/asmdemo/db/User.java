package com.gavin.asmdemo.db;


import com.gavin.asmdemo.db.aninations.DbFiled;
import com.gavin.asmdemo.db.aninations.DbTable;

@DbTable("tb_user")
public class User {
    @DbFiled
    private Integer id;
    @DbFiled
    private String name;
    @DbFiled
    private String password;


    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
