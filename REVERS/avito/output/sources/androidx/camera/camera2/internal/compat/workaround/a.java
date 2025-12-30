package androidx.camera.camera2.internal.compat.workaround;

import android.util.Range;
import androidx.camera.camera2.internal.compat.quirk.C19990a;
import androidx.camera.core.impl.w0;
import j.N;
import j.P;
import j.X;

/* compiled from: AeFpsRange.java */
@X
/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    @P
    public final Range<Integer> f23247a;

    public a(@N w0 w0Var) {
        C19990a c19990a = (C19990a) w0Var.b(C19990a.class);
        if (c19990a == null) {
            this.f23247a = null;
        } else {
            this.f23247a = c19990a.f23211a;
        }
    }
}
