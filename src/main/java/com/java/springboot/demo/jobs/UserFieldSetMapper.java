package com.java.springboot.demo.jobs;

import com.java.springboot.demo.model.User;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.math.BigDecimal;

public class UserFieldSetMapper implements FieldSetMapper<User> {
    public User mapFieldSet(FieldSet fieldSet) {

        if (fieldSet == null) {
            return null;
        }

        User user = new User();
        String[] nameSurnamePair = fieldSet.readString("Name").split(",");
        user.setName(nameSurnamePair[1].trim());
        user.setSurname(nameSurnamePair[0].trim());
        user.setAddress(fieldSet.readString("Address"));
        user.setPostCode(fieldSet.readString("Postcode"));
        user.setPhone(fieldSet.readString("Phone"));
        user.setCreditLimit(new BigDecimal(fieldSet.readString("Credit Limit")));
        user.setBirthday(fieldSet.readDate("Birthday", "dd/mm/yyyy"));
        return user;
    }
}