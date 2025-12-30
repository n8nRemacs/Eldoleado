package androidx.arch.core.internal;

import androidx.annotation.RestrictTo;
import androidx.arch.core.internal.b;
import androidx.view.InterfaceC22982O;
import j.N;
import j.P;
import java.util.HashMap;
import java.util.Map;

/* compiled from: FastSafeIterableMap.java */
@RestrictTo
/* loaded from: classes.dex */
public class a<K, V> extends b<K, V> {

    /* renamed from: f, reason: collision with root package name */
    public final HashMap<K, b.c<K, V>> f22700f = new HashMap<>();

    @Override // androidx.arch.core.internal.b
    @P
    public final b.c<K, V> a(K k12) {
        return this.f22700f.get(k12);
    }

    @Override // androidx.arch.core.internal.b
    public final V b(@N K k12, @N V v12) {
        b.c<K, V> cVarA = a(k12);
        if (cVarA != null) {
            return cVarA.f22706c;
        }
        HashMap<K, b.c<K, V>> map = this.f22700f;
        b.c<K, V> cVar = new b.c<>(k12, v12);
        this.f22704e++;
        b.c<K, V> cVar2 = this.f22702c;
        if (cVar2 == null) {
            this.f22701b = cVar;
            this.f22702c = cVar;
        } else {
            cVar2.f22707d = cVar;
            cVar.f22708e = cVar2;
            this.f22702c = cVar;
        }
        map.put(k12, cVar);
        return null;
    }

    @Override // androidx.arch.core.internal.b
    public final V d(@N K k12) {
        V v12 = (V) super.d(k12);
        this.f22700f.remove(k12);
        return v12;
    }

    @P
    public final Map.Entry e(InterfaceC22982O interfaceC22982O) {
        HashMap<K, b.c<K, V>> map = this.f22700f;
        if (map.containsKey(interfaceC22982O)) {
            return map.get(interfaceC22982O).f22708e;
        }
        return null;
    }
}
