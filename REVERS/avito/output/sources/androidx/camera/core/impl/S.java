package androidx.camera.core.impl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: EncoderProfilesProxy.java */
@j.X
/* loaded from: classes.dex */
public interface S {

    /* compiled from: EncoderProfilesProxy.java */
    @VY0.c
    public static abstract class a {

        /* compiled from: EncoderProfilesProxy.java */
        @Retention(RetentionPolicy.SOURCE)
        /* renamed from: androidx.camera.core.impl.S$a$a, reason: collision with other inner class name */
        public @interface InterfaceC1533a {
        }

        @j.N
        public static a a(int i12, int i13, @j.N String str, int i14, int i15, int i16) {
            return new C20091f(i12, i13, str, i14, i15, i16);
        }

        public abstract int b();

        public abstract int c();

        public abstract int d();

        @j.N
        public abstract String e();

        public abstract int f();

        public abstract int g();
    }

    /* compiled from: EncoderProfilesProxy.java */
    @VY0.c
    public static abstract class b implements S {
        @j.N
        public static b e(int i12, int i13, @j.N ArrayList arrayList, @j.N List list) {
            return new C20093g(i12, i13, Collections.unmodifiableList(new ArrayList(list)), Collections.unmodifiableList(new ArrayList(arrayList)));
        }
    }

    /* compiled from: EncoderProfilesProxy.java */
    @VY0.c
    public static abstract class c {

        /* compiled from: EncoderProfilesProxy.java */
        @Retention(RetentionPolicy.SOURCE)
        public @interface a {
        }

        @j.N
        public static c a(int i12, @j.N String str, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i22) {
            return new C20095h(i12, str, i13, i14, i15, i16, i17, i18, i19, i22);
        }

        public abstract int b();

        public abstract int c();

        public abstract int d();

        public abstract int e();

        public abstract int f();

        public abstract int g();

        public abstract int h();

        @j.N
        public abstract String i();

        public abstract int j();

        public abstract int k();
    }

    int a();

    @j.N
    List<a> b();

    int c();

    @j.N
    List<c> d();
}
