package androidx.camera.camera2.internal.compat.quirk;

import android.util.Size;
import androidx.camera.core.C20140q0;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* compiled from: CamcorderProfileResolutionQuirk.java */
@X
/* renamed from: androidx.camera.camera2.internal.compat.quirk.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C19994e implements F.a {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.B f23212a;

    /* renamed from: b, reason: collision with root package name */
    public List<Size> f23213b = null;

    public C19994e(@N androidx.camera.camera2.internal.compat.n nVar) {
        this.f23212a = nVar.b();
    }

    @Override // F.a
    @N
    public final ArrayList h() {
        if (this.f23213b == null) {
            Size[] sizeArrA = this.f23212a.a(34);
            List<Size> listAsList = sizeArrA != null ? Arrays.asList((Size[]) sizeArrA.clone()) : Collections.emptyList();
            this.f23213b = listAsList;
            Objects.toString(listAsList);
            C20140q0.d(3, "CamcorderProfileResolutionQuirk");
        }
        return new ArrayList(this.f23213b);
    }
}
