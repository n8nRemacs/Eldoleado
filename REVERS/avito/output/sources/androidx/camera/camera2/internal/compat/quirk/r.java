package androidx.camera.camera2.internal.compat.quirk;

import android.util.Pair;
import androidx.camera.core.impl.v0;
import j.X;
import java.util.HashSet;
import java.util.Locale;

/* compiled from: FlashAvailabilityBufferUnderflowQuirk.java */
@X
/* loaded from: classes.dex */
public class r implements v0 {

    /* renamed from: a, reason: collision with root package name */
    public static final HashSet f23224a;

    static {
        HashSet hashSet = new HashSet();
        f23224a = hashSet;
        Locale locale = Locale.US;
        hashSet.add(new Pair("sprd".toLowerCase(locale), "lemp".toLowerCase(locale)));
        hashSet.add(new Pair("sprd".toLowerCase(locale), "DM20C".toLowerCase(locale)));
    }
}
