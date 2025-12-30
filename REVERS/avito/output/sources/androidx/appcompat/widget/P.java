package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.widget.TextView;
import j.InterfaceC42164u;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: AppCompatTextViewAutoSizeHelper.java */
/* loaded from: classes.dex */
class P {

    /* renamed from: i, reason: collision with root package name */
    @SuppressLint({"BanConcurrentHashMap"})
    public static final ConcurrentHashMap<String, Method> f22235i;

    /* renamed from: a, reason: collision with root package name */
    public int f22236a = 0;

    /* renamed from: b, reason: collision with root package name */
    public float f22237b = -1.0f;

    /* renamed from: c, reason: collision with root package name */
    public float f22238c = -1.0f;

    /* renamed from: d, reason: collision with root package name */
    public float f22239d = -1.0f;

    /* renamed from: e, reason: collision with root package name */
    public int[] f22240e = new int[0];

    /* renamed from: f, reason: collision with root package name */
    public boolean f22241f = false;

    /* renamed from: g, reason: collision with root package name */
    @j.N
    public final TextView f22242g;

    /* renamed from: h, reason: collision with root package name */
    public final Context f22243h;

    /* compiled from: AppCompatTextViewAutoSizeHelper.java */
    @j.X
    public static final class a {
        @InterfaceC42164u
        @j.N
        public static StaticLayout a(@j.N CharSequence charSequence, @j.N Layout.Alignment alignment, int i12, int i13, @j.N TextView textView, @j.N TextPaint textPaint, @j.N d dVar) {
            StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), textPaint, i12);
            StaticLayout.Builder hyphenationFrequency = builderObtain.setAlignment(alignment).setLineSpacing(textView.getLineSpacingExtra(), textView.getLineSpacingMultiplier()).setIncludePad(textView.getIncludeFontPadding()).setBreakStrategy(textView.getBreakStrategy()).setHyphenationFrequency(textView.getHyphenationFrequency());
            if (i13 == -1) {
                i13 = Integer.MAX_VALUE;
            }
            hyphenationFrequency.setMaxLines(i13);
            try {
                dVar.a(builderObtain, textView);
            } catch (ClassCastException unused) {
            }
            return builderObtain.build();
        }
    }

    /* compiled from: AppCompatTextViewAutoSizeHelper.java */
    @j.X
    public static class b extends d {
        @Override // androidx.appcompat.widget.P.d
        public void a(StaticLayout.Builder builder, TextView textView) {
            builder.setTextDirection((TextDirectionHeuristic) P.c(textView, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR));
        }
    }

    /* compiled from: AppCompatTextViewAutoSizeHelper.java */
    @j.X
    public static class c extends b {
        @Override // androidx.appcompat.widget.P.b, androidx.appcompat.widget.P.d
        public void a(StaticLayout.Builder builder, TextView textView) {
            builder.setTextDirection(textView.getTextDirectionHeuristic());
        }
    }

    static {
        new RectF();
        f22235i = new ConcurrentHashMap<>();
    }

    public P(@j.N TextView textView) {
        this.f22242g = textView;
        this.f22243h = textView.getContext();
        if (Build.VERSION.SDK_INT >= 29) {
            new c();
        } else {
            new b();
        }
    }

    public static int[] a(int[] iArr) {
        int length = iArr.length;
        if (length == 0) {
            return iArr;
        }
        Arrays.sort(iArr);
        ArrayList arrayList = new ArrayList();
        for (int i12 : iArr) {
            if (i12 > 0 && Collections.binarySearch(arrayList, Integer.valueOf(i12)) < 0) {
                arrayList.add(Integer.valueOf(i12));
            }
        }
        if (length == arrayList.size()) {
            return iArr;
        }
        int size = arrayList.size();
        int[] iArr2 = new int[size];
        for (int i13 = 0; i13 < size; i13++) {
            iArr2[i13] = ((Integer) arrayList.get(i13)).intValue();
        }
        return iArr2;
    }

    @j.P
    public static Method b(@j.N String str) throws SecurityException {
        try {
            ConcurrentHashMap<String, Method> concurrentHashMap = f22235i;
            Method declaredMethod = concurrentHashMap.get(str);
            if (declaredMethod == null && (declaredMethod = TextView.class.getDeclaredMethod(str, new Class[0])) != null) {
                declaredMethod.setAccessible(true);
                concurrentHashMap.put(str, declaredMethod);
            }
            return declaredMethod;
        } catch (Exception unused) {
            return null;
        }
    }

    @SuppressLint({"BanUncheckedReflection"})
    public static <T> T c(@j.N Object obj, @j.N String str, @j.N T t12) {
        try {
            return (T) b(str).invoke(obj, new Object[0]);
        } catch (Exception unused) {
            return t12;
        }
    }

    public final boolean d() {
        return !(this.f22242g instanceof AppCompatEditText);
    }

    /* compiled from: AppCompatTextViewAutoSizeHelper.java */
    public static class d {
        public void a(StaticLayout.Builder builder, TextView textView) {
        }
    }
}
