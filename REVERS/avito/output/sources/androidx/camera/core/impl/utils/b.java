package androidx.camera.core.impl.utils;

import android.graphics.RectF;
import android.util.Rational;
import android.util.Size;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;
import java.util.Comparator;

/* compiled from: AspectRatioUtil.java */
@X
/* loaded from: classes.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    public static final Rational f24198a = new Rational(4, 3);

    /* renamed from: b, reason: collision with root package name */
    public static final Rational f24199b = new Rational(3, 4);

    /* renamed from: c, reason: collision with root package name */
    public static final Rational f24200c = new Rational(16, 9);

    /* renamed from: d, reason: collision with root package name */
    public static final Rational f24201d = new Rational(9, 16);

    /* compiled from: AspectRatioUtil.java */
    @X
    public static final class a implements Comparator<Rational> {

        /* renamed from: b, reason: collision with root package name */
        public final RectF f24202b;

        /* renamed from: c, reason: collision with root package name */
        public final Rational f24203c;

        public a(@N Rational rational, @P Rational rational2) {
            this.f24203c = rational2 == null ? new Rational(4, 3) : rational2;
            this.f24202b = b(rational);
        }

        public static float a(RectF rectF, RectF rectF2) {
            return (rectF.width() < rectF2.width() ? rectF.width() : rectF2.width()) * (rectF.height() < rectF2.height() ? rectF.height() : rectF2.height());
        }

        public final RectF b(Rational rational) {
            float fFloatValue = rational.floatValue();
            Rational rational2 = this.f24203c;
            return fFloatValue == rational2.floatValue() ? new RectF(0.0f, 0.0f, rational2.getNumerator(), rational2.getDenominator()) : rational.floatValue() > rational2.floatValue() ? new RectF(0.0f, 0.0f, rational2.getNumerator(), (rational.getDenominator() * rational2.getNumerator()) / rational.getNumerator()) : new RectF(0.0f, 0.0f, (rational.getNumerator() * rational2.getDenominator()) / rational.getDenominator(), rational2.getDenominator());
        }

        @Override // java.util.Comparator
        public final int compare(Rational rational, Rational rational2) {
            Rational rational3 = rational;
            Rational rational4 = rational2;
            boolean z12 = false;
            if (rational3.equals(rational4)) {
                return 0;
            }
            RectF rectFB = b(rational3);
            RectF rectFB2 = b(rational4);
            RectF rectF = this.f24202b;
            boolean z13 = rectFB.width() >= rectF.width() && rectFB.height() >= rectF.height();
            if (rectFB2.width() >= rectF.width() && rectFB2.height() >= rectF.height()) {
                z12 = true;
            }
            if (z13 && z12) {
                return (int) Math.signum((rectFB.height() * rectFB.width()) - (rectFB2.height() * rectFB2.width()));
            }
            if (z13) {
                return -1;
            }
            if (z12) {
                return 1;
            }
            return -((int) Math.signum(a(rectFB, rectF) - a(rectFB2, rectF)));
        }
    }

    public static boolean a(@N Size size, @P Rational rational, @N Size size2) {
        if (rational == null) {
            return false;
        }
        if (!rational.equals(new Rational(size.getWidth(), size.getHeight()))) {
            Size size3 = androidx.camera.core.internal.utils.c.f24370a;
            if (size.getHeight() * size.getWidth() < androidx.camera.core.internal.utils.c.a(size2)) {
                return false;
            }
            int width = size.getWidth();
            int height = size.getHeight();
            Rational rational2 = new Rational(rational.getDenominator(), rational.getNumerator());
            int i12 = width % 16;
            if (i12 != 0 || height % 16 != 0) {
                if (i12 == 0) {
                    return b(height, width, rational);
                }
                if (height % 16 == 0) {
                    return b(width, height, rational2);
                }
                return false;
            }
            if (!b(Math.max(0, height - 16), width, rational) && !b(Math.max(0, width - 16), height, rational2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean b(int i12, int i13, Rational rational) {
        z.b(i13 % 16 == 0);
        double numerator = (rational.getNumerator() * i12) / rational.getDenominator();
        return numerator > ((double) Math.max(0, i13 + (-16))) && numerator < ((double) (i13 + 16));
    }
}
