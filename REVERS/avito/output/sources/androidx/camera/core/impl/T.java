package androidx.camera.core.impl;

import android.util.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: EncoderProfilesResolutionValidator.java */
@j.X
/* loaded from: classes.dex */
public class T {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final ArrayList f24046a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final Set<Size> f24047b;

    public T(@j.P ArrayList arrayList) {
        Set<Size> setEmptySet;
        ArrayList arrayList2 = new ArrayList();
        this.f24046a = arrayList2;
        arrayList2.addAll(arrayList);
        if (arrayList.isEmpty()) {
            setEmptySet = Collections.emptySet();
        } else {
            HashSet hashSet = new HashSet(((F.a) arrayList.get(0)).h());
            for (int i12 = 1; i12 < arrayList.size(); i12++) {
                hashSet.retainAll(((F.a) arrayList.get(i12)).h());
            }
            setEmptySet = hashSet;
        }
        this.f24047b = setEmptySet;
    }
}
