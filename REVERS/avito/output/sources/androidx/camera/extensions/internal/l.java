package androidx.camera.extensions.internal;

import android.text.TextUtils;
import j.N;
import j.P;
import j.X;
import java.math.BigInteger;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: Version.java */
@VY0.c
@X
/* loaded from: classes.dex */
public abstract class l implements Comparable<l> {

    /* renamed from: b, reason: collision with root package name */
    public static final l f24636b;

    /* renamed from: c, reason: collision with root package name */
    public static final Pattern f24637c;

    static {
        new b(1, 0, "", 0);
        new b(1, 1, "", 0);
        new b(1, 2, "", 0);
        f24636b = new b(1, 3, "", 0);
        new b(1, 4, "", 0);
        f24637c = Pattern.compile("(\\d+)(?:\\.(\\d+))(?:\\.(\\d+))(?:\\-(.+))?");
    }

    public static BigInteger b(l lVar) {
        return BigInteger.valueOf(lVar.d()).shiftLeft(32).or(BigInteger.valueOf(lVar.e())).shiftLeft(32).or(BigInteger.valueOf(lVar.f()));
    }

    @P
    public static l h(@N String str) throws NumberFormatException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Matcher matcher = f24637c.matcher(str);
        if (!matcher.matches()) {
            return null;
        }
        return new b(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), matcher.group(4) != null ? matcher.group(4) : "", Integer.parseInt(matcher.group(3)));
    }

    public final int a(int i12, int i13) {
        return d() == i12 ? Integer.compare(e(), i13) : Integer.compare(d(), i12);
    }

    public abstract String c();

    @Override // java.lang.Comparable
    public final int compareTo(@N l lVar) {
        return b(this).compareTo(b(lVar));
    }

    public abstract int d();

    public abstract int e();

    public final boolean equals(Object obj) {
        if (!(obj instanceof l)) {
            return false;
        }
        l lVar = (l) obj;
        return Integer.valueOf(d()).equals(Integer.valueOf(lVar.d())) && Integer.valueOf(e()).equals(Integer.valueOf(lVar.e())) && Integer.valueOf(f()).equals(Integer.valueOf(lVar.f()));
    }

    public abstract int f();

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(d()), Integer.valueOf(e()), Integer.valueOf(f()));
    }

    @N
    public final String toString() {
        StringBuilder sb2 = new StringBuilder(d() + "." + e() + "." + f());
        if (!TextUtils.isEmpty(c())) {
            sb2.append("-" + c());
        }
        return sb2.toString();
    }
}
