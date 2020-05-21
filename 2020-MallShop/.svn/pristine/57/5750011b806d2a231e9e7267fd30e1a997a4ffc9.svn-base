package debug;

import android.app.Application;

import com.billy.cc.core.component.CC;
import com.mike.baselib.base.BaseApplication;

/**
 * @author billy.qi
 * @since 17/11/20 20:02
 */
public class IMApp extends BaseApplication {

   public static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        CC.enableVerboseLog(true);
        CC.enableDebug(true);
        CC.enableRemoteCC(true);
    }
}
