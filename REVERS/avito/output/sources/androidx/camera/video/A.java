package androidx.camera.video;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/* compiled from: Quality.java */
@j.X
/* loaded from: classes.dex */
public class A {

    /* renamed from: a, reason: collision with root package name */
    public static final A f24680a;

    /* renamed from: b, reason: collision with root package name */
    public static final A f24681b;

    /* renamed from: c, reason: collision with root package name */
    public static final A f24682c;

    /* renamed from: d, reason: collision with root package name */
    public static final A f24683d;

    /* renamed from: e, reason: collision with root package name */
    public static final A f24684e;

    /* renamed from: f, reason: collision with root package name */
    public static final A f24685f;

    /* renamed from: g, reason: collision with root package name */
    public static final A f24686g;

    /* renamed from: h, reason: collision with root package name */
    public static final HashSet f24687h;

    /* renamed from: i, reason: collision with root package name */
    public static final List<A> f24688i;

    /* compiled from: Quality.java */
    @VY0.c
    public static abstract class b extends A {
        public b() {
            super();
        }

        @j.N
        public abstract String a();

        public abstract int b();
    }

    static {
        C20175l c20175l = new C20175l(4, "SD");
        f24680a = c20175l;
        C20175l c20175l2 = new C20175l(5, "HD");
        f24681b = c20175l2;
        C20175l c20175l3 = new C20175l(6, "FHD");
        f24682c = c20175l3;
        C20175l c20175l4 = new C20175l(8, "UHD");
        f24683d = c20175l4;
        C20175l c20175l5 = new C20175l(0, "LOWEST");
        f24684e = c20175l5;
        C20175l c20175l6 = new C20175l(1, "HIGHEST");
        f24685f = c20175l6;
        f24686g = new C20175l(-1, "NONE");
        f24687h = new HashSet(Arrays.asList(c20175l5, c20175l6, c20175l, c20175l2, c20175l3, c20175l4));
        f24688i = Arrays.asList(c20175l4, c20175l3, c20175l2, c20175l);
    }

    public A() {
    }
}
