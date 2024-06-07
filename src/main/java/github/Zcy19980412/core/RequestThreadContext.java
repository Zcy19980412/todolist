package github.Zcy19980412.core;


import github.Zcy19980412.domain.entity.User;

public class RequestThreadContext {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();


    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static User getUser() {
        return currentUser.get();
    }

    public static void removeCurrentUser(){
        currentUser.remove();
    }


}
