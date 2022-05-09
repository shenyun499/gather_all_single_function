package optional;

import lombok.*;

import java.util.Optional;

/**
 * @author huangzhixue
 * @date 2022/1/25 12:22 下午
 * @Description
 */
public class ActualProjectExample {

    public void test1() {
        User user = User.builder().name("神韵").address(User.Address.builder().city("河源").build()).build();
        //例一  以前写法
        if (user != null) {
            dosomething(user);
        }

        // JAVA8写法
        Optional.ofNullable(user).ifPresent(u -> { dosomething(u);});
    }

    public void test2() {
        User user = User.builder().name("神韵").address(User.Address.builder().city("河源").build()).build();
        //例二  以前写法
        String city_old_02 = getCity_old_02(user);

        // JAVA8写法
        String city_java8_02 = getCity_java8_02(user);
    }

    public void test3() {
        User user = User.builder().name("神韵").address(User.Address.builder().city("河源").build()).build();
        //例三  以前写法
        User user_old_03 = getUser_old_03(user);

        // JAVA8写法
        User user_java8_03 = getUser_java8_03(user);
    }

    /**
     * 在函数方法中,以前写法
     * @param user
     * @return
     */
    public static String getCity_old_02(User user) {
        if (user != null) {
            if (user.getAddress() != null) {
                User.Address address = user.getAddress();
                if (address.getCity() != null) {
                    return address.getCity();
                }
            }
        }
        throw new RuntimeException("取值错误");
    }

    /**
     * JAVA8写法
     * @param user
     * @return
     */
    public String getCity_java8_02(User user) {
        return Optional.ofNullable(user)
                .map(u -> u.getAddress())
                .map(a -> a.getCity())
                .orElseThrow(() -> new RuntimeException("取指错误"));
    }


    /**
     * 以前写法
     * @param user
     * @return
     */
    public User getUser_old_03(User user) {
        if (user != null) {
            String name = user.getName();
            if ("zhangsan".equals(name)) {
                return user;
            }
        } else {
            user = new User();
            user.setName("zhangsan");
            return user;
        }
        return null;
    }

    /**
     * java8写法
     * @param user
     * @return
     */
    public User getUser_java8_03(User user){
        return Optional.ofNullable(user)
                .filter(u -> "zhangsan".equals(u.getName()))
                .orElseGet(() -> {
                    User user1 = new User();
                    user1.setName("zhangsan");
                    return user1;
                });
    }


    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String name;
        private Address address;
        @Setter
        @Getter
        @Builder
        static class Address {
            private String city;
        }
    }

    private void dosomething(Object u) {
        // do anything
    }
}
