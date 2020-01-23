package com.yit.test.entity;

import java.util.List;
import java.util.Objects;

public class PersonParent {

    private Person person;
    private int count;
    private List<Person> personList;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonParent)) return false;
        PersonParent that = (PersonParent) o;
        return count == that.count &&
                person.equals(that.person) &&
                personList.equals(that.personList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, count, personList);
    }
}
