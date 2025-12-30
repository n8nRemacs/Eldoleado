package aa1;

/* loaded from: classes9.dex */
public final class o {

    /* renamed from: a, reason: collision with root package name */
    public final boolean f20967a;

    /* renamed from: b, reason: collision with root package name */
    public final boolean f20968b;

    /* renamed from: c, reason: collision with root package name */
    public final boolean f20969c;

    public o(boolean z12, boolean z13, boolean z14) {
        this.f20967a = z12;
        this.f20968b = z13;
        this.f20969c = z14;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof o)) {
            return false;
        }
        o oVar = (o) obj;
        return this.f20967a == oVar.f20967a && this.f20968b == oVar.f20968b && this.f20969c == oVar.f20969c;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.f20969c) + androidx.appcompat.app.r.i(Boolean.hashCode(this.f20967a) * 31, 31, this.f20968b);
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("AnalyzeResult(isDetected=");
        sb2.append(this.f20967a);
        sb2.append(", hasFlare=");
        sb2.append(this.f20968b);
        sb2.append(", isBlurry=");
        return androidx.appcompat.app.r.x(sb2, this.f20969c, ')');
    }
}
