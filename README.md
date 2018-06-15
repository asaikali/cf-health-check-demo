# cf-health-check-demo

Demo Showing how to Configure HTTP health checks on Cloud Foundr

Cloud Foundry can check application health using three methods process, port and http end point.
These methods are documented at <https://docs.cloudfoundry.org/devguide/deploy-apps/healthchecks.html>

The http end point health check can be used with spring boot applications to point at the actuator
health endpoint. CF will issue an HTTP GET on `/actuator/health` every 30 seconds if it gets and 
`HTTP 200` response then it will consider the app health, if not it will restart the app.

This application contains a custom health indicator that can be toggled to observe how CF reacts
to a failing health check.

```java
@Component
public class ExampleHealthIndicator implements HealthIndicator
{
    private boolean state = true;

    @Override
    public Health health() {
        System.out.println("ExampleHealthIndicator called at " + LocalDateTime.now() + " state=" + state);
        if(state) {
            return Health.up().build();
        } else {
            return Health.down().build();
        }
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }
}
```

Run the application and check that it cf consider's it healthy. Then send get request to `/fail` 
to make the health indicator fail this will cause  `/actuator/health` to start failing.

health check failures will be visible in the log stream from the application so make sure to monitor the
app logs using the `cf logs` command to see the reports of health check failures. 