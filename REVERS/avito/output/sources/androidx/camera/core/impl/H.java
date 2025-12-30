package androidx.camera.core.impl;

import android.util.ArrayMap;
import android.util.Range;
import androidx.camera.core.impl.Config;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: CaptureConfig.java */
@j.X
/* loaded from: classes.dex */
public final class H {

    /* renamed from: i, reason: collision with root package name */
    public static final Config.a<Integer> f23965i = Config.a.a(Integer.TYPE, "camerax.core.captureConfig.rotation");

    /* renamed from: j, reason: collision with root package name */
    public static final Config.a<Integer> f23966j = Config.a.a(Integer.class, "camerax.core.captureConfig.jpegQuality");

    /* renamed from: k, reason: collision with root package name */
    public static final Config.a<Range<Integer>> f23967k = Config.a.a(Range.class, "camerax.core.captureConfig.resolvedFrameRate");

    /* renamed from: a, reason: collision with root package name */
    public final ArrayList f23968a;

    /* renamed from: b, reason: collision with root package name */
    public final s0 f23969b;

    /* renamed from: c, reason: collision with root package name */
    public final int f23970c;

    /* renamed from: d, reason: collision with root package name */
    public final Range<Integer> f23971d;

    /* renamed from: e, reason: collision with root package name */
    public final List<AbstractC20109p> f23972e;

    /* renamed from: f, reason: collision with root package name */
    public final boolean f23973f;

    /* renamed from: g, reason: collision with root package name */
    @j.N
    public final J0 f23974g;

    /* renamed from: h, reason: collision with root package name */
    @j.P
    public final r f23975h;

    /* compiled from: CaptureConfig.java */
    public interface b {
        void a(@j.N Z z12, @j.N a aVar);
    }

    public H(ArrayList arrayList, s0 s0Var, int i12, @j.N Range range, ArrayList arrayList2, boolean z12, @j.N J0 j02, @j.P r rVar) {
        this.f23968a = arrayList;
        this.f23969b = s0Var;
        this.f23970c = i12;
        this.f23971d = range;
        this.f23972e = Collections.unmodifiableList(arrayList2);
        this.f23973f = z12;
        this.f23974g = j02;
        this.f23975h = rVar;
    }

    /* compiled from: CaptureConfig.java */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        public final HashSet f23976a;

        /* renamed from: b, reason: collision with root package name */
        public n0 f23977b;

        /* renamed from: c, reason: collision with root package name */
        public int f23978c;

        /* renamed from: d, reason: collision with root package name */
        public final Range<Integer> f23979d;

        /* renamed from: e, reason: collision with root package name */
        public final ArrayList f23980e;

        /* renamed from: f, reason: collision with root package name */
        public boolean f23981f;

        /* renamed from: g, reason: collision with root package name */
        public final p0 f23982g;

        /* renamed from: h, reason: collision with root package name */
        @j.P
        public r f23983h;

        public a() {
            this.f23976a = new HashSet();
            this.f23977b = n0.R();
            this.f23978c = -1;
            this.f23979d = G0.f23964a;
            this.f23980e = new ArrayList();
            this.f23981f = false;
            this.f23982g = new p0(new ArrayMap());
        }

        public final void a(@j.N Collection<AbstractC20109p> collection) {
            Iterator<AbstractC20109p> it = collection.iterator();
            while (it.hasNext()) {
                b(it.next());
            }
        }

        public final void b(@j.N AbstractC20109p abstractC20109p) {
            ArrayList arrayList = this.f23980e;
            if (arrayList.contains(abstractC20109p)) {
                return;
            }
            arrayList.add(abstractC20109p);
        }

        public final void c(@j.N Config config) {
            Object objF;
            for (Config.a<?> aVar : config.e()) {
                n0 n0Var = this.f23977b;
                n0Var.getClass();
                try {
                    objF = n0Var.f(aVar);
                } catch (IllegalArgumentException unused) {
                    objF = null;
                }
                Object objF2 = config.f(aVar);
                if (objF instanceof AbstractC20104l0) {
                    AbstractC20104l0 abstractC20104l0 = (AbstractC20104l0) objF2;
                    abstractC20104l0.getClass();
                    ((AbstractC20104l0) objF).f24151a.addAll(Collections.unmodifiableList(new ArrayList(abstractC20104l0.f24151a)));
                } else {
                    if (objF2 instanceof AbstractC20104l0) {
                        objF2 = ((AbstractC20104l0) objF2).clone();
                    }
                    this.f23977b.T(aVar, config.i(aVar), objF2);
                }
            }
        }

        public final void d(@j.N DeferrableSurface deferrableSurface) {
            this.f23976a.add(deferrableSurface);
        }

        @j.N
        public final H e() {
            ArrayList arrayList = new ArrayList(this.f23976a);
            s0 s0VarQ = s0.Q(this.f23977b);
            int i12 = this.f23978c;
            ArrayList arrayList2 = new ArrayList(this.f23980e);
            boolean z12 = this.f23981f;
            J0 j02 = J0.f23986b;
            ArrayMap arrayMap = new ArrayMap();
            p0 p0Var = this.f23982g;
            for (String str : p0Var.f23987a.keySet()) {
                arrayMap.put(str, p0Var.f23987a.get(str));
            }
            return new H(arrayList, s0VarQ, i12, this.f23979d, arrayList2, z12, new J0(arrayMap), this.f23983h);
        }

        public a(H h12) {
            HashSet hashSet = new HashSet();
            this.f23976a = hashSet;
            this.f23977b = n0.R();
            this.f23978c = -1;
            this.f23979d = G0.f23964a;
            ArrayList arrayList = new ArrayList();
            this.f23980e = arrayList;
            this.f23981f = false;
            this.f23982g = new p0(new ArrayMap());
            hashSet.addAll(h12.f23968a);
            this.f23977b = n0.S(h12.f23969b);
            this.f23978c = h12.f23970c;
            this.f23979d = h12.f23971d;
            arrayList.addAll(h12.f23972e);
            this.f23981f = h12.f23973f;
            ArrayMap arrayMap = new ArrayMap();
            J0 j02 = h12.f23974g;
            for (String str : j02.f23987a.keySet()) {
                arrayMap.put(str, j02.f23987a.get(str));
            }
            this.f23982g = new p0(arrayMap);
        }
    }
}
