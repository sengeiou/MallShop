package com.epro.comp.im.smack.xmppdebug;

import android.util.Log;

import com.mike.baselib.utils.LogTools;

import org.jivesoftware.smack.util.ReaderListener;
import org.jivesoftware.smack.util.WriterListener;

/**
 * Description:此类用于打印xmpp协议收发包信息
 */

public class XmppRawXmlListener implements ReaderListener, WriterListener {

    XmppRawXmlListener() {
    }

    @Override
    public void write(String str) {
        LogTools.debug("XmppRawXmlListener_", XmppDebugger.SENT_TAG + ": " + str);
    }

    @Override
    public void read(String str) {
        LogTools.debug("XmppRawXmlListener_", XmppDebugger.RECEIVED_TAG + ": " + str);
    }
}
