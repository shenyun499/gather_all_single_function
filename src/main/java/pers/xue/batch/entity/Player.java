package pers.xue.batch.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author huangzhixue
 * @date 2022/2/9 3:47 下午
 * @Description
 * office example
 * https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/readersAndWriters.html#simpleDelimitedFileReadingExample
 */
@Setter
@Getter
public class Player implements Serializable {

    private String ID;
    private String lastName;
    private String firstName;
    private String position;
    private int birthYear;
    private int debutYear;

    @Override
    public String toString() {
        return "PLAYER:ID=" + ID + ",Last Name=" + lastName +
                ",First Name=" + firstName + ",Position=" + position +
                ",Birth Year=" + birthYear + ",DebutYear=" +
                debutYear;
    }

    // setters and getters...
}
