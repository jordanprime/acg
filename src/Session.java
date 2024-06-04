import java.util.concurrent.*;

public class Session {
    private User user;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> inactivityTask;
    private static final int TIMEOUT_MINUTES = 10;

    public Session(User user) {
        this.user = user;
        scheduler = Executors.newScheduledThreadPool(1);
        resetInactivityTimer();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getName() {
        return user.getName();
    }

    public User getUser() {
        return user;
    }

    public void resetInactivityTimer() {
        if (inactivityTask != null) {
            inactivityTask.cancel(false);
        }
        inactivityTask = scheduler.schedule(this::logoutDueToInactivity, TIMEOUT_MINUTES, TimeUnit.MINUTES);
    }

    private void logoutDueToInactivity() {
        user.logout();
        System.out.println("User " + getUsername() + " logged out due to inactivity.");
    }

    public void invalidate() {
        if (inactivityTask != null) {
            inactivityTask.cancel(false);
        }
        scheduler.shutdown();
    }
}
