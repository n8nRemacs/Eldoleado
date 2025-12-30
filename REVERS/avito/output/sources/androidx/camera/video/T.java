package androidx.camera.video;

import android.util.Size;
import androidx.annotation.RestrictTo;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.S;
import androidx.camera.core.impl.z0;
import androidx.camera.video.A;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/* compiled from: RecorderVideoCapabilities.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public final class T implements X {

    /* renamed from: b, reason: collision with root package name */
    public final androidx.camera.video.internal.workaround.c f24825b;

    /* renamed from: c, reason: collision with root package name */
    public final HashMap f24826c = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    public final HashMap f24827d = new HashMap();

    /* compiled from: RecorderVideoCapabilities.java */
    @j.k0
    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public final LinkedHashMap f24828a = new LinkedHashMap();

        /* renamed from: b, reason: collision with root package name */
        public final TreeMap<Size, A> f24829b = new TreeMap<>(new androidx.camera.core.impl.utils.g());

        /* renamed from: c, reason: collision with root package name */
        public final androidx.camera.video.internal.f f24830c;

        /* renamed from: d, reason: collision with root package name */
        public final androidx.camera.video.internal.f f24831d;

        public a(@j.N androidx.camera.video.internal.d dVar) {
            A a12 = A.f24680a;
            Iterator it = new ArrayList(A.f24688i).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                A a13 = (A) it.next();
                androidx.core.util.z.g("Currently only support ConstantQuality", a13 instanceof A.b);
                androidx.camera.core.impl.S sC2 = dVar.c(((A.b) a13).b());
                if (sC2 != null) {
                    sC2.toString();
                    C20140q0.d(3, "RecorderVideoCapabilities");
                    androidx.camera.video.internal.f fVarE = sC2.d().isEmpty() ? null : androidx.camera.video.internal.f.e(sC2);
                    if (fVarE == null) {
                        Objects.toString(a13);
                        C20140q0.d(5, "RecorderVideoCapabilities");
                    } else {
                        S.c cVarG = fVarE.g();
                        this.f24829b.put(new Size(cVarG.k(), cVarG.h()), a13);
                        this.f24828a.put(a13, fVarE);
                    }
                }
            }
            if (this.f24828a.isEmpty()) {
                C20140q0.b("RecorderVideoCapabilities");
                this.f24831d = null;
                this.f24830c = null;
            } else {
                ArrayDeque arrayDeque = new ArrayDeque(this.f24828a.values());
                this.f24830c = (androidx.camera.video.internal.f) arrayDeque.peekFirst();
                this.f24831d = (androidx.camera.video.internal.f) arrayDeque.peekLast();
            }
        }

        @j.P
        public final androidx.camera.video.internal.f a(@j.N A a12) {
            androidx.core.util.z.a("Unknown quality: " + a12, A.f24687h.contains(a12));
            return a12 == A.f24685f ? this.f24830c : a12 == A.f24684e ? this.f24831d : (androidx.camera.video.internal.f) this.f24828a.get(a12);
        }
    }

    @j.k0
    public T(@j.N androidx.camera.core.impl.B b12) {
        androidx.camera.core.impl.Q qD2 = b12.d();
        Iterator<androidx.camera.core.F> it = b12.a().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            androidx.camera.core.F next = it.next();
            if (Integer.valueOf(next.f23568a).equals(3) && next.f23569b == 10) {
                qD2 = new androidx.camera.video.internal.b(qD2);
                break;
            }
        }
        this.f24825b = new androidx.camera.video.internal.workaround.c(new z0(qD2, b12.g()), b12, androidx.camera.video.internal.compat.quirk.e.f25058a);
        for (androidx.camera.core.F f12 : b12.a()) {
            a aVar = new a(new androidx.camera.video.internal.d(this.f24825b, f12));
            if (!new ArrayList(aVar.f24828a.keySet()).isEmpty()) {
                this.f24826c.put(f12, aVar);
            }
        }
    }

    public static boolean e(@j.N androidx.camera.core.F f12) {
        int i12 = f12.f23568a;
        return (i12 == 0 || i12 == 2 || f12.f23569b == 0) ? false : true;
    }

    @Override // androidx.camera.video.X
    @j.N
    public final ArrayList a(@j.N androidx.camera.core.F f12) {
        a aVarD = d(f12);
        return aVarD == null ? new ArrayList() : new ArrayList(aVarD.f24828a.keySet());
    }

    @Override // androidx.camera.video.X
    @j.P
    public final androidx.camera.video.internal.f b(@j.N A a12, @j.N androidx.camera.core.F f12) {
        a aVarD = d(f12);
        if (aVarD == null) {
            return null;
        }
        return aVarD.a(a12);
    }

    @Override // androidx.camera.video.X
    @j.P
    public final androidx.camera.video.internal.f c(@j.N Size size, @j.N androidx.camera.core.F f12) {
        A value;
        a aVarD = d(f12);
        androidx.camera.video.internal.f fVarA = null;
        if (aVarD != null) {
            TreeMap<Size, A> treeMap = aVarD.f24829b;
            Map.Entry<Size, A> entryCeilingEntry = treeMap.ceilingEntry(size);
            if (entryCeilingEntry != null) {
                value = entryCeilingEntry.getValue();
            } else {
                Map.Entry<Size, A> entryFloorEntry = treeMap.floorEntry(size);
                value = entryFloorEntry != null ? entryFloorEntry.getValue() : A.f24686g;
            }
            Objects.toString(value);
            Objects.toString(size);
            C20140q0.d(3, "RecorderVideoCapabilities");
            if (value != A.f24686g && (fVarA = aVarD.a(value)) == null) {
                throw new AssertionError("Camera advertised available quality but did not produce EncoderProfiles for advertised quality.");
            }
        }
        return fVarA;
    }

    @j.P
    public final a d(@j.N androidx.camera.core.F f12) {
        boolean zContains;
        boolean zE2 = e(f12);
        HashMap map = this.f24826c;
        if (zE2) {
            return (a) map.get(f12);
        }
        HashMap map2 = this.f24827d;
        if (map2.containsKey(f12)) {
            return (a) map2.get(f12);
        }
        Set<androidx.camera.core.F> setKeySet = map.keySet();
        if (e(f12)) {
            zContains = setKeySet.contains(f12);
        } else {
            for (androidx.camera.core.F f13 : setKeySet) {
                androidx.core.util.z.g("Fully specified range is not actually fully specified.", e(f13));
                int i12 = f12.f23569b;
                if (i12 == 0 || i12 == f13.f23569b) {
                    androidx.core.util.z.g("Fully specified range is not actually fully specified.", e(f13));
                    int i13 = f12.f23568a;
                    if (i13 != 0) {
                        int i14 = f13.f23568a;
                        if ((i13 != 2 || i14 == 1) && i13 != i14) {
                        }
                    }
                    zContains = true;
                    break;
                }
            }
            zContains = false;
        }
        a aVar = !zContains ? null : new a(new androidx.camera.video.internal.d(this.f24825b, f12));
        map2.put(f12, aVar);
        return aVar;
    }
}
