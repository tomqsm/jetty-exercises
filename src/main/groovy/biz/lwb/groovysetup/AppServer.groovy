package biz.lwb.groovysetup

import biz.lwb.groovysetup.servlet.ShopServlet
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.server.handler.ContextHandler
import org.eclipse.jetty.server.handler.ContextHandlerCollection
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.util.Callback
import org.eclipse.jetty.util.component.LifeCycle
import org.eclipse.jetty.util.thread.QueuedThreadPool
import org.eclipse.jetty.webapp.WebAppContext

import static java.lang.System.Logger.Level.INFO

class AppServer {

    private Server server;

    static void main(String[] args) {
        final server = new AppServer()
        server.startServer()
        server.setShutdownHook()
    }

    Server startServer() {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setName("server");

        server = new Server(threadPool);

        // Add an event listener of type LifeCycle.Listener.
        server.addEventListener(new LifeCycle.Listener() {
            @Override
            void lifeCycleStarted(LifeCycle lifeCycle) {
                System.getLogger("server").log(INFO, "Server {0} has been started", lifeCycle);
            }

            @Override
            void lifeCycleFailure(LifeCycle lifeCycle, Throwable failure) {
                System.getLogger("server").log(INFO, "Server {0} failed to start", lifeCycle, failure);
            }

            @Override
            void lifeCycleStopped(LifeCycle lifeCycle) {
                System.getLogger("server").log(INFO, "Server {0} has been stopped", lifeCycle);
            }
        });


        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9999);

        server.addConnector(connector);
        connector.addBean(new TimingHttpChannelListener());

        ContextHandler shopContext = new ContextHandler();
        shopContext.setContextPath(ShopHandler.PATH);
        shopContext.setHandler(new ShopHandler())

        ContextHandler apiContext = new ContextHandler();
        apiContext.setContextPath(RestHandler.PATH);
        apiContext.setHandler(new RestHandler())

        ServletContextHandler shoponeContextHandler = new ServletContextHandler(
                ServletContextHandler.NO_SESSIONS)
        shoponeContextHandler.addServlet(new ServletHolder(new ShopServlet()), "/shops/*")


        ContextHandlerCollection handlers = new ContextHandlerCollection();
        handlers.addHandler(shopContext)
        handlers.addHandler(apiContext)
        handlers.addHandler(shoponeContextHandler)

        server.setHandler(handlers);
        server.setStopAtShutdown(true)
        server.addShutdownHook {
            println("shutdown hook")
        }
        server.start()

        deployHandler(handlers)
//        server.join()
        return server
    }

    void setShutdownHook() {
        if (server != null) {
            Thread printingHook = new Thread(() -> server.stop())
            Runtime.getRuntime().addShutdownHook(printingHook);
        }
    }

    private WebAppContext createWebAppContext() {
        WebAppContext webAppContext = new WebAppContext()
        webAppContext.setResourceBase("/static")
        webAppContext.setContextPath("/webapp")
        return webAppContext
    }

    private void deployHandler(ContextHandlerCollection contextCollection) {
        ContextHandler apiContext = new ContextHandler("/umba");
        apiContext.setHandler(new RestHandler())
        contextCollection.deployHandler(apiContext, new Callback() {
            @Override
            void succeeded() {
                println("deploy succeeded")
            }

            @Override
            void failed(Throwable x) {
                println("deploy failed")
            }
        });
    }
}
