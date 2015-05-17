package test.by.itechart.flowerty.persistence.repository.helper;

import by.itechart.flowerty.persistence.model.User;

/**
 * Created by Rostislav on 16-May-15
 */

public abstract class UserRepositoryHelperTests {

    public static User getUserWithIdOne() {
        User user = new User();

        user.setId(1L);
        user.setLogin("test");
        user.setPassword("$2a$10$ZWwh6S.iW5Sjeo2mklifkegHdSDOpmxpAw5oHDRTEMWgHLS.bILny");
        user.setRole(RoleRepositoryHelperTests.getRoleWithIdOne());
        user.setContact(ContactRepositoryHelperTests.getContactWithIdOne());

        return user;
    }
}
