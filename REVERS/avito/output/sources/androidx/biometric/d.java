package androidx.biometric;

import j.P;
import java.util.Arrays;

/* compiled from: BiometricErrorData.java */
/* loaded from: classes.dex */
class d {

    /* renamed from: a, reason: collision with root package name */
    public final int f22750a;

    /* renamed from: b, reason: collision with root package name */
    @P
    public final CharSequence f22751b;

    public d(int i12, @P CharSequence charSequence) {
        this.f22750a = i12;
        this.f22751b = charSequence;
    }

    public final boolean equals(@P Object obj) {
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        if (this.f22750a != dVar.f22750a) {
            return false;
        }
        CharSequence charSequence = this.f22751b;
        String string = charSequence != null ? charSequence.toString() : null;
        CharSequence charSequence2 = dVar.f22751b;
        String string2 = charSequence2 != null ? charSequence2.toString() : null;
        return (string == null && string2 == null) || (string != null && string.equals(string2));
    }

    public final int hashCode() {
        Integer numValueOf = Integer.valueOf(this.f22750a);
        CharSequence charSequence = this.f22751b;
        return Arrays.hashCode(new Object[]{numValueOf, charSequence != null ? charSequence.toString() : null});
    }
}
