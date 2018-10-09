package co.yodo.restapi;

import android.content.Context;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import co.yodo.restapi.business.injection.component.ApplicationComponent;
import co.yodo.restapi.business.injection.component.DaggerApplicationComponent;
import co.yodo.restapi.business.injection.component.DaggerGraphComponent;
import co.yodo.restapi.business.injection.component.GraphComponent;
import co.yodo.restapi.business.injection.module.ApiClientModule;
import co.yodo.restapi.business.injection.module.ApplicationModule;
import co.yodo.restapi.business.injection.module.CipherModule;
import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.requests.contract.ICommand;
import timber.log.Timber;

/**
 * Created by hei on 15/07/16.
 * Implements the logic to communicate with the server
 */
public class ApiClient {
    /** Component that buildBody the dependencies */
    private static GraphComponent component;

    /** The ip for the connection */
    private final String ip;

    /** The alias for the connection */
    private final String alias;

    ApiClient(Builder builder) {
        // Get elements
        component = builder.getGraphComponent();
        ip = builder.getIp();
        alias = builder.getAlias();

        // Get context
        Context context = builder.getContext();

        // Init secure preferences
        Hawk.init(context).build();

        // Init timber
        if (builder.getLog()) {
            // Develop -- All logs on
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ':' + element.getLineNumber();
                }
            });
        } else {
            // Release -- Remove unimportant logs
            Timber.plant(new CrashReportingTree());
        }
    }

    /**
     * Get the ip of the connection
     * @return String
     */
    String getIp() {
        return ip;
    }

    /**
     * Get the alias of the connection
     * @return String
     */
    String getAlias() {
        return alias;
    }

    /**
     * Gets the GraphComponent to inject the classes
     * @return GraphComponent
     */
    public static GraphComponent getComponent() {
        return component;
    }

    /**
     * Executes a request (extends IRequest class)
     * @param request The request to be executed
     */
    void invoke(ICommand request, RequestCallback callback) {
        request.execute(callback);
    }

    /**
     * API Builder
     */
    public static final class Builder {
        /** Application context */
        private Context context;

        /** Injection */
        private GraphComponent component;

        /** Configuration */
        private boolean log = false;

        /** Connection parameters */
        private String ip = "localhost";
        private String alias = "L";

        Builder(Context context) {
            if (context.getApplicationContext() != null) {
                // Could be null in unit tests
                this.context = context.getApplicationContext();
            }
        }

        /**
         * Sets the debug level log
         * @param log The state of the debug
         * @return The builder object
         */
        public Builder setLog(boolean log) {
            this.log = log;
            return this;
        }

        /**
         * Sets the IP and an alias to recognize the connection
         * @param ip The ip to connect
         * @param alias The alias for the connection
         * @return The builder object
         */
        public Builder server(String ip, String alias) {
            this.ip = ip;
            this.alias = alias;
            return this;
        }

        /**
         * Gets the graph component for injection
         * @return The injection graph component
         */
        GraphComponent getGraphComponent() {
            if (component == null) {
                ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                        .applicationModule(new ApplicationModule(context))
                        .build();

                component = DaggerGraphComponent.builder()
                        .applicationComponent(appComponent)
                        .apiClientModule(new ApiClientModule(ip, log))
                        .cipherModule(new CipherModule(ip))
                        .build();
            }
            return component;
        }

        /**
         * Gets the log state
         * @return boolean
         */
        boolean getLog() {
            return log;
        }

        /**
         * Get the ip for the connection
         * @return String
         */
        String getIp() {
            return ip;
        }

        /**
         * Get the alias for the connection
         * @return String
         */
        String getAlias() {
            return alias;
        }

        /**
         * Gets the application context
         * @return The application context
         */
        Context getContext() {
            return context;
        }

        /**
         * Builds the API
         */
        public void build() {
            YodoApi.build(this);
        }
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        /** The max size of a line */
        private static final int MAX_LOG_LENGTH = 4000;
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return;
            }

            if (message.length() < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message);
                } else {
                    Log.println(priority, tag, message);
                }
                return;
            }

            for (int i = 0, length = message.length(); i < length; i++) {
                int newLine = message.indexOf('\n', i);
                newLine = newLine != -1 ? newLine : length;
                do {
                    int end = Math.min(newLine, i + MAX_LOG_LENGTH);
                    String part = message.substring(i, end);
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part);
                    } else {
                        Log.println(priority, tag, part);
                    }
                    i = end;
                } while (i < newLine);
            }
        }
    }
}
