package androidx.camera.video.internal.workaround;

import android.os.Build;
import android.util.Range;
import android.util.Size;
import androidx.camera.video.internal.compat.quirk.k;
import androidx.camera.video.internal.encoder.L;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/* compiled from: VideoEncoderInfoWrapper.java */
@X
/* loaded from: classes.dex */
public class d implements L {

    /* renamed from: a, reason: collision with root package name */
    public final L f25274a;

    /* renamed from: b, reason: collision with root package name */
    public final Range<Integer> f25275b;

    /* renamed from: c, reason: collision with root package name */
    public final Range<Integer> f25276c;

    /* renamed from: d, reason: collision with root package name */
    public final HashSet f25277d;

    public d(@N L l12, @P Size size) {
        HashSet hashSet = new HashSet();
        this.f25277d = hashSet;
        this.f25274a = l12;
        int iH2 = l12.h();
        this.f25275b = Range.create(Integer.valueOf(iH2), Integer.valueOf(((int) Math.ceil(4096.0d / iH2)) * iH2));
        int iG2 = l12.g();
        this.f25276c = Range.create(Integer.valueOf(iG2), Integer.valueOf(((int) Math.ceil(2160.0d / iG2)) * iG2));
        if (size != null) {
            hashSet.add(size);
        }
        List<String> list = k.f25060a;
        hashSet.addAll(k.f25060a.contains(Build.MODEL.toLowerCase(Locale.US)) ? Collections.singleton(new Size(1920, 1080)) : Collections.emptySet());
    }

    @Override // androidx.camera.video.internal.encoder.L
    @N
    public final Range<Integer> a(int i12) {
        Integer numValueOf = Integer.valueOf(i12);
        Range<Integer> range = this.f25276c;
        boolean zContains = range.contains((Range<Integer>) numValueOf);
        L l12 = this.f25274a;
        z.a("Not supported height: " + i12 + " which is not in " + range + " or can not be divided by alignment " + l12.g(), zContains && i12 % l12.g() == 0);
        return this.f25275b;
    }

    @Override // androidx.camera.video.internal.encoder.L
    @N
    public final Range<Integer> b() {
        return this.f25274a.b();
    }

    @Override // androidx.camera.video.internal.encoder.L
    public final boolean c(int i12, int i13) {
        HashSet hashSet = this.f25277d;
        if (!hashSet.isEmpty() && hashSet.contains(new Size(i12, i13))) {
            return true;
        }
        if (this.f25275b.contains((Range<Integer>) Integer.valueOf(i12))) {
            if (this.f25276c.contains((Range<Integer>) Integer.valueOf(i13))) {
                L l12 = this.f25274a;
                if (i12 % l12.h() == 0 && i13 % l12.g() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // androidx.camera.video.internal.encoder.L
    @N
    public final Range<Integer> d(int i12) {
        Integer numValueOf = Integer.valueOf(i12);
        Range<Integer> range = this.f25275b;
        boolean zContains = range.contains((Range<Integer>) numValueOf);
        L l12 = this.f25274a;
        z.a("Not supported width: " + i12 + " which is not in " + range + " or can not be divided by alignment " + l12.h(), zContains && i12 % l12.h() == 0);
        return this.f25276c;
    }

    @Override // androidx.camera.video.internal.encoder.L
    @N
    public final Range<Integer> e() {
        return this.f25275b;
    }

    @Override // androidx.camera.video.internal.encoder.L
    @N
    public final Range<Integer> f() {
        return this.f25276c;
    }

    @Override // androidx.camera.video.internal.encoder.L
    public final int g() {
        return this.f25274a.g();
    }

    @Override // androidx.camera.video.internal.encoder.L
    public final int h() {
        return this.f25274a.h();
    }
}
