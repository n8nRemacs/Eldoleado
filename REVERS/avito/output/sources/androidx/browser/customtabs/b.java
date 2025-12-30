package androidx.browser.customtabs;

import android.os.Bundle;
import j.InterfaceC42156l;
import j.N;
import j.P;

/* compiled from: CustomTabColorSchemeParams.java */
/* loaded from: classes.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    @P
    @InterfaceC42156l
    public final Integer f22825a;

    /* compiled from: CustomTabColorSchemeParams.java */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        @P
        @InterfaceC42156l
        public Integer f22826a;
    }

    public b(@P @InterfaceC42156l Integer num) {
        this.f22825a = num;
    }

    @N
    public final Bundle a() {
        Bundle bundle = new Bundle();
        Integer num = this.f22825a;
        if (num != null) {
            bundle.putInt("android.support.customtabs.extra.TOOLBAR_COLOR", num.intValue());
        }
        return bundle;
    }
}
