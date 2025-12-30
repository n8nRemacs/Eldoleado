package aa1;

import java.util.ArrayList;
import kotlin.jvm.internal.L;
import xa1.AbstractC49913g;

/* loaded from: classes9.dex */
public final class f {

    /* renamed from: a, reason: collision with root package name */
    public final ArrayList f20931a;

    /* renamed from: b, reason: collision with root package name */
    public final boolean f20932b;

    /* renamed from: c, reason: collision with root package name */
    public final boolean f20933c;

    /* renamed from: d, reason: collision with root package name */
    public final boolean f20934d;

    /* renamed from: e, reason: collision with root package name */
    public final boolean f20935e;

    /* renamed from: f, reason: collision with root package name */
    public final boolean f20936f;

    /* renamed from: g, reason: collision with root package name */
    public final boolean f20937g;

    /* renamed from: h, reason: collision with root package name */
    public final boolean f20938h;

    /* renamed from: i, reason: collision with root package name */
    public final AbstractC49913g f20939i;

    public f(ArrayList arrayList, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, AbstractC49913g abstractC49913g) {
        this.f20931a = arrayList;
        this.f20932b = z12;
        this.f20933c = z13;
        this.f20934d = z14;
        this.f20935e = z15;
        this.f20936f = z16;
        this.f20937g = z17;
        this.f20938h = z18;
        this.f20939i = abstractC49913g;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return this.f20931a.equals(fVar.f20931a) && this.f20932b == fVar.f20932b && this.f20933c == fVar.f20933c && this.f20934d == fVar.f20934d && this.f20935e == fVar.f20935e && this.f20936f == fVar.f20936f && this.f20937g == fVar.f20937g && this.f20938h == fVar.f20938h && L.f(this.f20939i, fVar.f20939i);
    }

    public final int hashCode() {
        return this.f20939i.hashCode() + androidx.appcompat.app.r.i(androidx.appcompat.app.r.i(androidx.appcompat.app.r.i(androidx.appcompat.app.r.i(androidx.appcompat.app.r.i(androidx.appcompat.app.r.i(androidx.appcompat.app.r.i(this.f20931a.hashCode() * 31, 31, this.f20932b), 31, this.f20933c), 31, this.f20934d), 31, this.f20935e), 31, this.f20936f), 31, this.f20937g), 31, this.f20938h);
    }

    public final String toString() {
        return "AnalyzeResult(points=" + this.f20931a + ", isPassportDetected=" + this.f20932b + ", isPassportInFrame=" + this.f20933c + ", isPassportInZone=" + this.f20934d + ", isPassportBlurry=" + this.f20935e + ", isSlowMotion=" + this.f20936f + ", hasFlare=" + this.f20937g + ", isLowBrightness=" + this.f20938h + ", tilt=" + this.f20939i + ')';
    }
}
