package androidx.camera.video;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: QualitySelector.java */
@j.X
/* loaded from: classes.dex */
public final class D {

    /* renamed from: a, reason: collision with root package name */
    public final List<A> f24693a;

    /* renamed from: b, reason: collision with root package name */
    public final C20181s f24694b;

    public D(@j.N List<A> list, @j.N C20181s c20181s) {
        androidx.core.util.z.a("No preferred quality and fallback strategy.", (list.isEmpty() && c20181s == C20181s.f25334a) ? false : true);
        this.f24693a = Collections.unmodifiableList(new ArrayList(list));
        this.f24694b = c20181s;
    }

    @j.N
    public static D a(@j.N List<A> list, @j.N C20181s c20181s) {
        androidx.core.util.z.f(list, "qualities cannot be null");
        androidx.core.util.z.a("qualities cannot be empty", !list.isEmpty());
        for (A a12 : list) {
            androidx.core.util.z.a("qualities contain invalid quality: " + a12, A.f24687h.contains(a12));
        }
        return new D(list, c20181s);
    }

    @j.N
    public final String toString() {
        return "QualitySelector{preferredQualities=" + this.f24693a + ", fallbackStrategy=" + this.f24694b + "}";
    }
}
