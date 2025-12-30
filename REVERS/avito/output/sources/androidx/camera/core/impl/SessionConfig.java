package androidx.camera.core.impl;

import VY0.c;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.params.InputConfiguration;
import android.util.Range;
import android.util.Size;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.C20101k;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.H;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@j.X
/* loaded from: classes.dex */
public final class SessionConfig {

    /* renamed from: a, reason: collision with root package name */
    public final ArrayList f24012a;

    /* renamed from: b, reason: collision with root package name */
    public final List<CameraDevice.StateCallback> f24013b;

    /* renamed from: c, reason: collision with root package name */
    public final List<CameraCaptureSession.StateCallback> f24014c;

    /* renamed from: d, reason: collision with root package name */
    public final List<AbstractC20109p> f24015d;

    /* renamed from: e, reason: collision with root package name */
    public final List<c> f24016e;

    /* renamed from: f, reason: collision with root package name */
    public final H f24017f;

    /* renamed from: g, reason: collision with root package name */
    @j.P
    public final InputConfiguration f24018g;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class SessionError {

        /* renamed from: b, reason: collision with root package name */
        public static final /* synthetic */ SessionError[] f24019b = {new SessionError("SESSION_ERROR_SURFACE_NEEDS_RESET", 0), new SessionError("SESSION_ERROR_UNKNOWN", 1)};

        /* JADX INFO: Fake field, exist only in values array */
        SessionError EF5;

        public SessionError() {
            throw null;
        }

        public static SessionError valueOf(String str) {
            return (SessionError) Enum.valueOf(SessionError.class, str);
        }

        public static SessionError[] values() {
            return (SessionError[]) f24019b.clone();
        }
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public final LinkedHashSet f24020a = new LinkedHashSet();

        /* renamed from: b, reason: collision with root package name */
        public final H.a f24021b = new H.a();

        /* renamed from: c, reason: collision with root package name */
        public final ArrayList f24022c = new ArrayList();

        /* renamed from: d, reason: collision with root package name */
        public final ArrayList f24023d = new ArrayList();

        /* renamed from: e, reason: collision with root package name */
        public final ArrayList f24024e = new ArrayList();

        /* renamed from: f, reason: collision with root package name */
        public final ArrayList f24025f = new ArrayList();

        /* renamed from: g, reason: collision with root package name */
        @j.P
        public InputConfiguration f24026g;
    }

    public static class b extends a {
        @j.N
        public static b m(@j.N L0<?> l02, @j.N Size size) {
            d dVarO = l02.O();
            if (dVarO != null) {
                b bVar = new b();
                dVarO.a(size, l02, bVar);
                return bVar;
            }
            throw new IllegalStateException("Implementation is missing option unpacker for " + l02.j(l02.toString()));
        }

        @j.N
        public final void a(@j.N Collection collection) {
            this.f24021b.a(collection);
        }

        @j.N
        public final void b(@j.N AbstractC20109p abstractC20109p) {
            this.f24021b.b(abstractC20109p);
            ArrayList arrayList = this.f24025f;
            if (arrayList.contains(abstractC20109p)) {
                return;
            }
            arrayList.add(abstractC20109p);
        }

        @j.N
        public final void c(@j.N CameraDevice.StateCallback stateCallback) {
            ArrayList arrayList = this.f24022c;
            if (arrayList.contains(stateCallback)) {
                return;
            }
            arrayList.add(stateCallback);
        }

        @j.N
        public final void d(@j.N c cVar) {
            this.f24024e.add(cVar);
        }

        @j.N
        public final void e(@j.N Config config) {
            this.f24021b.c(config);
        }

        @j.N
        public final void f(@j.N DeferrableSurface deferrableSurface, @j.N androidx.camera.core.F f12) {
            e.a aVarA = e.a(deferrableSurface);
            C20101k.b bVar = (C20101k.b) aVarA;
            if (f12 == null) {
                throw new NullPointerException("Null dynamicRange");
            }
            bVar.f24144d = f12;
            this.f24020a.add(aVarA.a());
        }

        @j.N
        public final void g(@j.N AbstractC20109p abstractC20109p) {
            this.f24021b.b(abstractC20109p);
        }

        @j.N
        public final void h(@j.N CameraCaptureSession.StateCallback stateCallback) {
            ArrayList arrayList = this.f24023d;
            if (arrayList.contains(stateCallback)) {
                return;
            }
            arrayList.add(stateCallback);
        }

        @j.N
        public final void i(@j.N DeferrableSurface deferrableSurface, @j.N androidx.camera.core.F f12) {
            e.a aVarA = e.a(deferrableSurface);
            C20101k.b bVar = (C20101k.b) aVarA;
            if (f12 == null) {
                throw new NullPointerException("Null dynamicRange");
            }
            bVar.f24144d = f12;
            this.f24020a.add(aVarA.a());
            this.f24021b.d(deferrableSurface);
        }

        @j.N
        public final void j(@j.N Object obj, @j.N String str) {
            this.f24021b.f23982g.f23987a.put(str, obj);
        }

        @j.N
        public final SessionConfig k() {
            return new SessionConfig(new ArrayList(this.f24020a), new ArrayList(this.f24022c), new ArrayList(this.f24023d), new ArrayList(this.f24025f), new ArrayList(this.f24024e), this.f24021b.e(), this.f24026g);
        }

        @j.N
        public final void l() {
            this.f24020a.clear();
            this.f24021b.f23976a.clear();
        }

        @j.N
        public final List<AbstractC20109p> n() {
            return Collections.unmodifiableList(this.f24025f);
        }

        public final void o(@j.N AbstractC20109p abstractC20109p) {
            this.f24021b.f23980e.remove(abstractC20109p);
            this.f24025f.remove(abstractC20109p);
        }

        @j.N
        public final void p(@j.N Range range) {
            H.a aVar = this.f24021b;
            aVar.getClass();
            aVar.f23977b.K(H.f23967k, range);
        }

        @j.N
        public final void q(@j.N Config config) {
            H.a aVar = this.f24021b;
            aVar.getClass();
            aVar.f23977b = n0.S(config);
        }

        @j.N
        public final void r(@j.P InputConfiguration inputConfiguration) {
            this.f24026g = inputConfiguration;
        }

        @j.N
        public final void s(int i12) {
            this.f24021b.f23978c = i12;
        }
    }

    public interface c {
        void onError();
    }

    public interface d {
        void a(@j.N Size size, @j.N L0<?> l02, @j.N b bVar);
    }

    @VY0.c
    public static abstract class e {

        @c.a
        public static abstract class a {
            @j.N
            public abstract e a();
        }

        @j.N
        public static a a(@j.N DeferrableSurface deferrableSurface) {
            C20101k.b bVar = new C20101k.b();
            if (deferrableSurface == null) {
                throw new NullPointerException("Null surface");
            }
            bVar.f24141a = deferrableSurface;
            List<DeferrableSurface> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                throw new NullPointerException("Null sharedSurfaces");
            }
            bVar.f24142b = listEmptyList;
            bVar.f24143c = -1;
            bVar.f24144d = androidx.camera.core.F.f23562d;
            return bVar;
        }

        @j.N
        public abstract androidx.camera.core.F b();

        @j.P
        public abstract String c();

        @j.N
        public abstract List<DeferrableSurface> d();

        @j.N
        public abstract DeferrableSurface e();

        public abstract int f();
    }

    public static final class f extends a {

        /* renamed from: k, reason: collision with root package name */
        public static final List<Integer> f24027k = Arrays.asList(1, 5, 3);

        /* renamed from: h, reason: collision with root package name */
        public final androidx.camera.core.internal.compat.workaround.f f24028h = new androidx.camera.core.internal.compat.workaround.f();

        /* renamed from: i, reason: collision with root package name */
        public boolean f24029i = true;

        /* renamed from: j, reason: collision with root package name */
        public boolean f24030j = false;

        public final void a(@j.N SessionConfig sessionConfig) {
            Object objF;
            H h12 = sessionConfig.f24017f;
            int i12 = h12.f23970c;
            H.a aVar = this.f24021b;
            if (i12 != -1) {
                this.f24030j = true;
                int i13 = aVar.f23978c;
                Integer numValueOf = Integer.valueOf(i12);
                List<Integer> list = f24027k;
                if (list.indexOf(numValueOf) < list.indexOf(Integer.valueOf(i13))) {
                    i12 = i13;
                }
                aVar.f23978c = i12;
            }
            Config.a<Range<Integer>> aVar2 = H.f23967k;
            Object objF2 = G0.f23964a;
            s0 s0Var = h12.f23969b;
            try {
                objF2 = s0Var.f(aVar2);
            } catch (IllegalArgumentException unused) {
            }
            Range range = (Range) objF2;
            Objects.requireNonNull(range);
            Range<Integer> range2 = G0.f23964a;
            if (!range.equals(range2)) {
                n0 n0Var = aVar.f23977b;
                Config.a<Range<Integer>> aVar3 = H.f23967k;
                n0Var.getClass();
                try {
                    objF = n0Var.f(aVar3);
                } catch (IllegalArgumentException unused2) {
                    objF = range2;
                }
                if (((Range) objF).equals(range2)) {
                    aVar.f23977b.K(H.f23967k, range);
                } else {
                    n0 n0Var2 = aVar.f23977b;
                    Config.a<Range<Integer>> aVar4 = H.f23967k;
                    Object objF3 = G0.f23964a;
                    n0Var2.getClass();
                    try {
                        objF3 = n0Var2.f(aVar4);
                    } catch (IllegalArgumentException unused3) {
                    }
                    if (!((Range) objF3).equals(range)) {
                        this.f24029i = false;
                        C20140q0.d(3, "ValidatingBuilder");
                    }
                }
            }
            H h13 = sessionConfig.f24017f;
            aVar.f23982g.f23987a.putAll((Map) h13.f23974g.f23987a);
            this.f24022c.addAll(sessionConfig.f24013b);
            this.f24023d.addAll(sessionConfig.f24014c);
            aVar.a(h13.f23972e);
            this.f24025f.addAll(sessionConfig.f24015d);
            this.f24024e.addAll(sessionConfig.f24016e);
            InputConfiguration inputConfiguration = sessionConfig.f24018g;
            if (inputConfiguration != null) {
                this.f24026g = inputConfiguration;
            }
            LinkedHashSet<e> linkedHashSet = this.f24020a;
            linkedHashSet.addAll(sessionConfig.f24012a);
            HashSet hashSet = aVar.f23976a;
            hashSet.addAll(Collections.unmodifiableList(h12.f23968a));
            ArrayList arrayList = new ArrayList();
            for (e eVar : linkedHashSet) {
                arrayList.add(eVar.e());
                Iterator<DeferrableSurface> it = eVar.d().iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next());
                }
            }
            if (!arrayList.containsAll(hashSet)) {
                C20140q0.d(3, "ValidatingBuilder");
                this.f24029i = false;
            }
            aVar.c(s0Var);
        }

        @j.N
        public final SessionConfig b() {
            if (!this.f24029i) {
                throw new IllegalArgumentException("Unsupported session configuration combination");
            }
            ArrayList arrayList = new ArrayList(this.f24020a);
            androidx.camera.core.internal.compat.workaround.f fVar = this.f24028h;
            if (fVar.f24346a) {
                Collections.sort(arrayList, new androidx.camera.core.internal.compat.workaround.e(fVar, 0));
            }
            return new SessionConfig(arrayList, new ArrayList(this.f24022c), new ArrayList(this.f24023d), new ArrayList(this.f24025f), new ArrayList(this.f24024e), this.f24021b.e(), this.f24026g);
        }
    }

    public SessionConfig(ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4, ArrayList arrayList5, H h12, @j.P InputConfiguration inputConfiguration) {
        this.f24012a = arrayList;
        this.f24013b = Collections.unmodifiableList(arrayList2);
        this.f24014c = Collections.unmodifiableList(arrayList3);
        this.f24015d = Collections.unmodifiableList(arrayList4);
        this.f24016e = Collections.unmodifiableList(arrayList5);
        this.f24017f = h12;
        this.f24018g = inputConfiguration;
    }

    @j.N
    public static SessionConfig a() {
        return new SessionConfig(new ArrayList(), new ArrayList(0), new ArrayList(0), new ArrayList(0), new ArrayList(0), new H.a().e(), null);
    }

    @j.N
    public final List<DeferrableSurface> b() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.f24012a.iterator();
        while (it.hasNext()) {
            e eVar = (e) it.next();
            arrayList.add(eVar.e());
            Iterator<DeferrableSurface> it2 = eVar.d().iterator();
            while (it2.hasNext()) {
                arrayList.add(it2.next());
            }
        }
        return Collections.unmodifiableList(arrayList);
    }
}
