package com.epro.comp.im.smack.xmppdebug;

import android.support.annotation.RequiresPermission;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.debugger.SmackDebugger;
import org.jivesoftware.smack.debugger.SmackDebuggerFactory;
import org.jivesoftware.smack.packet.TopLevelStreamElement;
import org.jivesoftware.smack.util.ObservableReader;
import org.jivesoftware.smack.util.ObservableWriter;
import org.jxmpp.jid.EntityFullJid;

import java.io.Reader;
import java.io.Writer;

/**
 * Created by WangQX on 2018/4/16.
 * Description:此类用于打印xmpp协议收发包信息
 */

public class XmppDebuggerFactory implements SmackDebuggerFactory {
    @Override
    public SmackDebugger create(XMPPConnection connection) throws IllegalArgumentException {
        return new XmppDebugger(connection);
    }
}
