package aa1;

import java.util.ArrayList;

/* loaded from: classes9.dex */
public final class q {

    /* renamed from: a, reason: collision with root package name */
    public final boolean f20976a;

    /* renamed from: b, reason: collision with root package name */
    public final ArrayList f20977b;

    /* renamed from: c, reason: collision with root package name */
    public final boolean f20978c;

    /* renamed from: d, reason: collision with root package name */
    public final boolean f20979d;

    public q(boolean z12, ArrayList arrayList, boolean z13, boolean z14) {
        this.f20976a = z12;
        this.f20977b = arrayList;
        this.f20978c = z13;
        this.f20979d = z14;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof q)) {
            return false;
        }
        q qVar = (q) obj;
        return this.f20976a == qVar.f20976a && this.f20977b.equals(qVar.f20977b) && this.f20978c == qVar.f20978c && this.f20979d == qVar.f20979d;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.f20979d) + androidx.appcompat.app.r.i(androidx.compose.ui.graphics.colorspace.e.g(this.f20977b, Boolean.hashCode(this.f20976a) * 31, 31), 31, this.f20978c);
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("Output(isPassportDetected=");
        sb2.append(this.f20976a);
        sb2.append(", points=");
        sb2.append(this.f20977b);
        sb2.append(", isUpPageSuccessful=");
        sb2.append(this.f20978c);
        sb2.append(", isDownPageSuccessful=");
        return androidx.appcompat.app.r.x(sb2, this.f20979d, ')');
    }
}
