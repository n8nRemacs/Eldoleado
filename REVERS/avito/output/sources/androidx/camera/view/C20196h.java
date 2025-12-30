package androidx.camera.view;

import androidx.view.AbstractC22991Y;
import androidx.view.C23034e0;
import j.X;

/* compiled from: ForwardingLiveData.java */
@X
/* renamed from: androidx.camera.view.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20196h<T> extends C23034e0<T> {

    /* renamed from: b, reason: collision with root package name */
    public AbstractC22991Y<T> f25438b;

    @Override // androidx.view.AbstractC22991Y
    public final T getValue() {
        AbstractC22991Y<T> abstractC22991Y = this.f25438b;
        if (abstractC22991Y == null) {
            return null;
        }
        return abstractC22991Y.getValue();
    }
}
