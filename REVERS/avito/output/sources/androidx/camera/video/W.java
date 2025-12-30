package androidx.camera.video;

import androidx.annotation.RestrictTo;
import androidx.camera.core.K0;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: StreamInfo.java */
@VY0.c
@RestrictTo
@j.X
/* loaded from: classes.dex */
public abstract class W {

    /* renamed from: a, reason: collision with root package name */
    public static final W f24841a = new C20178o(0, a.f24845c, null);

    /* renamed from: b, reason: collision with root package name */
    public static final Set<Integer> f24842b = Collections.unmodifiableSet(new HashSet(Arrays.asList(0, -1)));

    /* renamed from: c, reason: collision with root package name */
    public static final androidx.camera.core.impl.K f24843c = new androidx.camera.core.impl.K(new C20178o(0, a.f24844b, null));

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: StreamInfo.java */
    @RestrictTo
    public static final class a {

        /* renamed from: b, reason: collision with root package name */
        public static final a f24844b;

        /* renamed from: c, reason: collision with root package name */
        public static final a f24845c;

        /* renamed from: d, reason: collision with root package name */
        public static final /* synthetic */ a[] f24846d;

        static {
            a aVar = new a("ACTIVE", 0);
            f24844b = aVar;
            a aVar2 = new a("INACTIVE", 1);
            f24845c = aVar2;
            f24846d = new a[]{aVar, aVar2};
        }

        public a() {
            throw null;
        }

        public static a valueOf(String str) {
            return (a) Enum.valueOf(a.class, str);
        }

        public static a[] values() {
            return (a[]) f24846d.clone();
        }
    }

    public abstract int a();

    @j.P
    public abstract K0.d b();

    @j.N
    public abstract a c();
}
