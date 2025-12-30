package androidx.browser.customtabs;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.RestrictTo;
import j.N;
import j.P;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: CustomTabsCallback.java */
/* loaded from: classes.dex */
public class c {

    /* compiled from: CustomTabsCallback.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface a {
    }

    @P
    public Bundle extraCallbackWithResult(@N String str, @P Bundle bundle) {
        return null;
    }

    public void onMessageChannelReady(@P Bundle bundle) {
    }

    @r
    public void onMinimized(@N Bundle bundle) {
    }

    @r
    public void onUnminimized(@N Bundle bundle) {
    }

    public void onWarmupCompleted(@N Bundle bundle) {
    }

    public void extraCallback(@N String str, @P Bundle bundle) {
    }

    public void onNavigationEvent(int i12, @P Bundle bundle) {
    }

    public void onPostMessage(@N String str, @P Bundle bundle) {
    }

    public void onActivityResized(@j.r int i12, @j.r int i13, @N Bundle bundle) {
    }

    public void onRelationshipValidationResult(int i12, @N Uri uri, boolean z12, @P Bundle bundle) {
    }

    public void onActivityLayout(@j.r int i12, @j.r int i13, @j.r int i14, @j.r int i15, int i16, @N Bundle bundle) {
    }
}
