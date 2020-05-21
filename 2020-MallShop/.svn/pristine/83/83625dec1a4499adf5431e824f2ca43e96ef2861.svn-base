package com.epro.comp.im.smack;

import com.mike.baselib.utils.AppBusManager;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Nonza;
import org.jivesoftware.smack.sasl.packet.SaslStreamElements;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smack.util.XmlStringBuilder;

public class MyXMPPTCPConnection extends XMPPTCPConnection {
    public MyXMPPTCPConnection(XMPPTCPConnectionConfiguration config) {
        super(config);
    }

    @Override
    public void sendNonza(Nonza element) throws SmackException.NotConnectedException, InterruptedException {
        if (element instanceof SaslStreamElements.Response) {
            Response response = new Response(((SaslStreamElements.Response) element).getAuthenticationText());
            super.sendNonza(response);
        } else {
            super.sendNonza(element);
        }
    }

    /**
     * A SASL response stream element.
     */
    public static class Response implements Nonza {
        public static final String ELEMENT = "response";

        private final String authenticationText;

        public Response() {
            authenticationText = null;
        }

        public Response(String authenticationText) {
            this.authenticationText = StringUtils.returnIfNotEmptyTrimmed(authenticationText);
        }

        @Override
        public XmlStringBuilder toXML(String enclosingNamespace) {
            XmlStringBuilder xml = new XmlStringBuilder();
            xml.halfOpenElement(ELEMENT).xmlnsAttribute(SaslStreamElements.NAMESPACE)
                    .optAttribute("token", "appBearer " + AppBusManager.Companion.getToken()).rightAngleBracket();
            xml.optAppend(authenticationText);
            xml.closeElement(ELEMENT);
            return xml;
        }

        public String getAuthenticationText() {
            return authenticationText;
        }

        @Override
        public String getNamespace() {
            return SaslStreamElements.NAMESPACE;
        }

        @Override
        public String getElementName() {
            return ELEMENT;
        }
    }

//    @Override
//    protected void setWasAuthenticated() {
//        this.wasAuthenticated=false;
//        this.authenticated=false;
//        //super.setWasAuthenticated();
//    }
}
