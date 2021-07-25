package com.airfrance.AirFranceTest.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 *     class User Entity
 * </p>
 * @author Younes Benkirane
 */
@Entity
@Table(name="user")
public class User implements Serializable {

    private String login;

    private String fullName;

    private String birthday;

    private String country;

    private String gender;

    private String phoneNumber;

    public User() {
    }

    public User(String login, String fullName, String birthday, String country) {
        this.login = login;
        this.fullName = fullName;
        this.birthday = birthday;
        this.country = country;
    }


    @Id
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    @NotEmpty
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Objects.equals(login, user.login) && Objects.equals(fullName, user.fullName) && Objects.equals(birthday, user.birthday) && Objects.equals(country, user.country) && Objects.equals(gender, user.gender) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash( login, fullName, birthday, country, gender, phoneNumber);
    }

    @Override
    public String toString() {
        return "User{" +
                ", login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", country='" + country + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
