package soap.hello.world.listener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.event.AuthenticationCredentialsNotFoundEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.support.ServletRequestHandledEvent;

public class CustomLoggerListener implements ApplicationListener<ApplicationEvent> {
    private static Logger logger = Logger.getLogger(CustomLoggerListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if ( event instanceof AuthenticationCredentialsNotFoundEvent) {
            logger.info( "AuthenticationCredentialsNotFoundEvent: " + event );
        }
        else if ( event instanceof AuthorizationFailureEvent) {
            AuthorizationFailureEvent a = (AuthorizationFailureEvent)event;
            logger.info( "AUTH-FAIL [" + a.getSource() + "]");
        }
        else if ( event instanceof PublicInvocationEvent) {
            logger.trace( "PublicInvocationEvent: " + event );
        }
        else if ( event instanceof AuthenticationFailureBadCredentialsEvent ) {
            AuthenticationFailureBadCredentialsEvent f = (AuthenticationFailureBadCredentialsEvent)event;
            logger.info( "AUTH-FAIL BAD-CREDENTIALS [" + f.getSource() + "]" );
        }
        else if ( event instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent s = (AuthenticationSuccessEvent)event;
            Authentication a = s.getAuthentication();
            logger.info( "AUTH-SUCCESS [" + a + "]");

        }
        else if ( event instanceof ServletRequestHandledEvent ) {
            ServletRequestHandledEvent r = (ServletRequestHandledEvent)event;
            logger.trace( "request[ USR=" + r.getUserName() +", "+r.getMethod() +", URL="+r.getRequestUrl() + ", client="+r.getClientAddress() + "]" );
        }

        else {
            logger.trace( "evento ricevuto: " + event.getClass().getCanonicalName() );
            logger.trace( "event: "+event );
        }
    }
}
