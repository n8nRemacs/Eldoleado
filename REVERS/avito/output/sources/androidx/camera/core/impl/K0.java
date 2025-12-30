package androidx.camera.core.impl;

import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.SessionConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: UseCaseAttachState.java */
@j.X
/* loaded from: classes.dex */
public final class K0 {

    /* renamed from: a, reason: collision with root package name */
    public final LinkedHashMap f23990a = new LinkedHashMap();

    /* compiled from: UseCaseAttachState.java */
    public interface a {
    }

    /* compiled from: UseCaseAttachState.java */
    public static final class b {

        /* renamed from: a, reason: collision with root package name */
        @j.N
        public final SessionConfig f23991a;

        /* renamed from: b, reason: collision with root package name */
        @j.N
        public final L0<?> f23992b;

        /* renamed from: c, reason: collision with root package name */
        public boolean f23993c = false;

        /* renamed from: d, reason: collision with root package name */
        public boolean f23994d = false;

        public b(@j.N SessionConfig sessionConfig, @j.N L0<?> l02) {
            this.f23991a = sessionConfig;
            this.f23992b = l02;
        }
    }

    public K0(@j.N String str) {
    }

    @j.N
    public final SessionConfig.f a() {
        SessionConfig.f fVar = new SessionConfig.f();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : this.f23990a.entrySet()) {
            b bVar = (b) entry.getValue();
            if (bVar.f23994d && bVar.f23993c) {
                String str = (String) entry.getKey();
                fVar.a(bVar.f23991a);
                arrayList.add(str);
            }
        }
        arrayList.toString();
        C20140q0.d(3, "UseCaseAttachState");
        return fVar;
    }

    @j.N
    public final Collection<SessionConfig> b() {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : this.f23990a.entrySet()) {
            b bVar = (b) entry.getValue();
            if (bVar.f23994d && bVar.f23993c) {
                arrayList.add(((b) entry.getValue()).f23991a);
            }
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    @j.N
    public final SessionConfig.f c() {
        SessionConfig.f fVar = new SessionConfig.f();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : this.f23990a.entrySet()) {
            b bVar = (b) entry.getValue();
            if (bVar.f23993c) {
                fVar.a(bVar.f23991a);
                arrayList.add((String) entry.getKey());
            }
        }
        arrayList.toString();
        C20140q0.d(3, "UseCaseAttachState");
        return fVar;
    }

    @j.N
    public final Collection<SessionConfig> d() {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : this.f23990a.entrySet()) {
            if (((b) entry.getValue()).f23993c) {
                arrayList.add(((b) entry.getValue()).f23991a);
            }
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    @j.N
    public final Collection<L0<?>> e() {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : this.f23990a.entrySet()) {
            if (((b) entry.getValue()).f23993c) {
                arrayList.add(((b) entry.getValue()).f23992b);
            }
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    public final boolean f(@j.N String str) {
        LinkedHashMap linkedHashMap = this.f23990a;
        if (linkedHashMap.containsKey(str)) {
            return ((b) linkedHashMap.get(str)).f23993c;
        }
        return false;
    }

    public final void g(@j.N String str, @j.N SessionConfig sessionConfig, @j.N L0<?> l02) {
        LinkedHashMap linkedHashMap = this.f23990a;
        b bVar = (b) linkedHashMap.get(str);
        if (bVar == null) {
            bVar = new b(sessionConfig, l02);
            linkedHashMap.put(str, bVar);
        }
        bVar.f23994d = true;
    }

    public final void h(@j.N String str, @j.N SessionConfig sessionConfig, @j.N L0<?> l02) {
        LinkedHashMap linkedHashMap = this.f23990a;
        b bVar = (b) linkedHashMap.get(str);
        if (bVar == null) {
            bVar = new b(sessionConfig, l02);
            linkedHashMap.put(str, bVar);
        }
        bVar.f23993c = true;
    }

    public final void i(@j.N String str) {
        LinkedHashMap linkedHashMap = this.f23990a;
        if (linkedHashMap.containsKey(str)) {
            b bVar = (b) linkedHashMap.get(str);
            bVar.f23993c = false;
            if (bVar.f23994d) {
                return;
            }
            linkedHashMap.remove(str);
        }
    }

    public final void j(@j.N String str) {
        LinkedHashMap linkedHashMap = this.f23990a;
        if (linkedHashMap.containsKey(str)) {
            b bVar = (b) linkedHashMap.get(str);
            bVar.f23994d = false;
            if (bVar.f23993c) {
                return;
            }
            linkedHashMap.remove(str);
        }
    }

    public final void k(@j.N String str, @j.N SessionConfig sessionConfig, @j.N L0<?> l02) {
        LinkedHashMap linkedHashMap = this.f23990a;
        if (linkedHashMap.containsKey(str)) {
            b bVar = new b(sessionConfig, l02);
            b bVar2 = (b) linkedHashMap.get(str);
            bVar.f23993c = bVar2.f23993c;
            bVar.f23994d = bVar2.f23994d;
            linkedHashMap.put(str, bVar);
        }
    }
}
