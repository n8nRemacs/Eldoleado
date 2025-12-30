package androidx.camera.core.impl;

import androidx.camera.core.InterfaceC20141s;
import androidx.camera.core.InterfaceC20142t;
import java.util.ArrayList;
import java.util.List;

/* compiled from: LensFacingCameraFilter.java */
@j.X
/* renamed from: androidx.camera.core.impl.h0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C20096h0 implements InterfaceC20141s {

    /* renamed from: b, reason: collision with root package name */
    public final int f24124b;

    public C20096h0(int i12) {
        this.f24124b = i12;
    }

    @Override // androidx.camera.core.InterfaceC20141s
    @j.N
    public final List<InterfaceC20142t> b(@j.N List<InterfaceC20142t> list) {
        ArrayList arrayList = new ArrayList();
        for (InterfaceC20142t interfaceC20142t : list) {
            androidx.core.util.z.a("The camera info doesn't contain internal implementation.", interfaceC20142t instanceof B);
            if (interfaceC20142t.b() == this.f24124b) {
                arrayList.add(interfaceC20142t);
            }
        }
        return arrayList;
    }
}
