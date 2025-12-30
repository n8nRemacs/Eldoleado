package androidx.biometric;

import androidx.biometric.p;
import androidx.core.hardware.fingerprint.b;
import androidx.view.C23038g0;
import java.lang.ref.WeakReference;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* compiled from: AuthenticationCallbackProvider.java */
/* renamed from: androidx.biometric.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19978a extends b.AbstractC1732b {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ b f22745a;

    public C19978a(b bVar) {
        this.f22745a = bVar;
    }

    @Override // androidx.core.hardware.fingerprint.b.AbstractC1732b
    public final void a(int i12, CharSequence charSequence) {
        this.f22745a.f22748c.a(i12, charSequence);
    }

    @Override // androidx.core.hardware.fingerprint.b.AbstractC1732b
    public final void b() {
        this.f22745a.f22748c.b();
    }

    @Override // androidx.core.hardware.fingerprint.b.AbstractC1732b
    public final void c(CharSequence charSequence) {
        WeakReference<r> weakReference = this.f22745a.f22748c.f22812a;
        if (weakReference.get() != null) {
            r rVar = weakReference.get();
            if (rVar.f22804Y == null) {
                rVar.f22804Y = new C23038g0<>();
            }
            r.qe(rVar.f22804Y, charSequence);
        }
    }

    @Override // androidx.core.hardware.fingerprint.b.AbstractC1732b
    public final void d(b.c cVar) {
        p.c cVar2 = null;
        b.d dVar = cVar.f44788a;
        if (dVar != null) {
            Cipher cipher = dVar.f44790b;
            if (cipher != null) {
                cVar2 = new p.c(cipher);
            } else {
                Signature signature = dVar.f44789a;
                if (signature != null) {
                    cVar2 = new p.c(signature);
                } else {
                    Mac mac = dVar.f44791c;
                    if (mac != null) {
                        cVar2 = new p.c(mac);
                    }
                }
            }
        }
        this.f22745a.f22748c.c(new p.b(cVar2, 2));
    }
}
