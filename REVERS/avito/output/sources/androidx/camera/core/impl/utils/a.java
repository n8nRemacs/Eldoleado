package androidx.camera.core.impl.utils;

import j.N;
import j.P;
import j.X;

/* compiled from: Absent.java */
@X
/* loaded from: classes.dex */
final class a<T> extends q<T> {

    /* renamed from: b, reason: collision with root package name */
    public static final a<Object> f24197b = new a<>();
    private static final long serialVersionUID = 0;

    private Object readResolve() {
        return f24197b;
    }

    public final boolean equals(@P Object obj) {
        return obj == this;
    }

    public final int hashCode() {
        return 2040732332;
    }

    @N
    public final String toString() {
        return "Optional.absent()";
    }
}
