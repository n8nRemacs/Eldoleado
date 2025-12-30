package androidx.browser.customtabs;

import android.os.Bundle;
import j.InterfaceC42164u;
import j.N;
import j.P;
import j.X;

/* compiled from: Api33Impl.java */
@X
/* loaded from: classes.dex */
class a {
    @InterfaceC42164u
    public static <T> T a(@N Bundle bundle, @P String str, @N Class<T> cls) {
        return (T) bundle.getParcelable(str, cls);
    }
}
