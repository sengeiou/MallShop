package com.epro.comp.im.smack.xmppdebug;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.debugger.SmackDebugger;
import org.jivesoftware.smack.packet.TopLevelStreamElement;
import org.jivesoftware.smack.util.ObservableReader;
import org.jivesoftware.smack.util.ObservableWriter;
import org.jxmpp.jid.EntityFullJid;

import java.io.Reader;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by WangQX on 2018/4/16.
 * Description:此类用于打印xmpp协议收发包信息
 */

public class XmppDebugger extends SmackDebugger {
    public static final String LOGGER_NAME = "XMPP_SMACK";

    public static final AtomicBoolean printInterpreted = new AtomicBoolean(true);

    public static final String SENT_TAG = "SENT";
    public static final String RECEIVED_TAG = "RECV";
    private final XmppRawXmlListener XmppRawXmlListener = new XmppRawXmlListener();

    /**
     * Create new SLF4J Smack Debugger instance
     *
     * @param connection Smack connection to debug
     */
    public XmppDebugger(XMPPConnection connection) {
        super(connection);
        //this.connection.addConnectionListener(new XmppLoggingConnectionListener(connection));
    }

    @Override
    public void userHasLogged(EntityFullJid user) {

    }

    @Override
    public Reader newConnectionReader(Reader newReader) {
        ObservableReader reader = new ObservableReader(newReader);
        reader.addReaderListener(XmppRawXmlListener);
        return reader;
    }

    @Override
    public Writer newConnectionWriter(Writer newWriter) {
        ObservableWriter writer = new ObservableWriter(newWriter);
        writer.addWriterListener(XmppRawXmlListener);
        return writer;
    }

    @Override
    public void onIncomingStreamElement(TopLevelStreamElement streamElement) {

    }

    @Override
    public void onOutgoingStreamElement(TopLevelStreamElement streamElement) {

    }

}
