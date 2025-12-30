package androidx.camera.core;

import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: FocusMeteringAction.java */
@j.X
/* loaded from: classes.dex */
public final class M {

    /* renamed from: a, reason: collision with root package name */
    public final List<s0> f23606a;

    /* renamed from: b, reason: collision with root package name */
    public final List<s0> f23607b;

    /* renamed from: c, reason: collision with root package name */
    public final List<s0> f23608c;

    /* renamed from: d, reason: collision with root package name */
    public final long f23609d;

    /* compiled from: FocusMeteringAction.java */
    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public final ArrayList f23610a;

        /* renamed from: b, reason: collision with root package name */
        public final ArrayList f23611b;

        /* renamed from: c, reason: collision with root package name */
        public final ArrayList f23612c;

        /* renamed from: d, reason: collision with root package name */
        public final long f23613d;

        public a(@j.N s0 s0Var) {
            this(s0Var, 7);
        }

        @j.N
        public final void a(@j.N s0 s0Var, int i12) {
            androidx.core.util.z.a("Invalid metering mode " + i12, i12 >= 1 && i12 <= 7);
            if ((i12 & 1) != 0) {
                this.f23610a.add(s0Var);
            }
            if ((i12 & 2) != 0) {
                this.f23611b.add(s0Var);
            }
            if ((i12 & 4) != 0) {
                this.f23612c.add(s0Var);
            }
        }

        @j.N
        @RestrictTo
        public final void b(int i12) {
            if ((i12 & 1) != 0) {
                this.f23610a.clear();
            }
            if ((i12 & 2) != 0) {
                this.f23611b.clear();
            }
            if ((i12 & 4) != 0) {
                this.f23612c.clear();
            }
        }

        public a(@j.N s0 s0Var, int i12) {
            this.f23610a = new ArrayList();
            this.f23611b = new ArrayList();
            this.f23612c = new ArrayList();
            this.f23613d = 5000L;
            a(s0Var, i12);
        }

        @RestrictTo
        public a(@j.N M m12) {
            ArrayList arrayList = new ArrayList();
            this.f23610a = arrayList;
            ArrayList arrayList2 = new ArrayList();
            this.f23611b = arrayList2;
            ArrayList arrayList3 = new ArrayList();
            this.f23612c = arrayList3;
            this.f23613d = 5000L;
            arrayList.addAll(m12.f23606a);
            arrayList2.addAll(m12.f23607b);
            arrayList3.addAll(m12.f23608c);
            this.f23613d = m12.f23609d;
        }
    }

    /* compiled from: FocusMeteringAction.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface b {
    }

    public M(a aVar) {
        this.f23606a = Collections.unmodifiableList(aVar.f23610a);
        this.f23607b = Collections.unmodifiableList(aVar.f23611b);
        this.f23608c = Collections.unmodifiableList(aVar.f23612c);
        this.f23609d = aVar.f23613d;
    }
}
