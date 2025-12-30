package androidx.camera.camera2.internal;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.core.C20140q0;
import androidx.camera.core.C20144v;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.InitializationException;
import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.InterfaceC20117y;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/* compiled from: Camera2CameraFactory.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.q, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20028q implements InterfaceC20117y {

    /* renamed from: a, reason: collision with root package name */
    public final C.a f23419a;

    /* renamed from: b, reason: collision with root package name */
    public final androidx.camera.core.impl.F f23420b;

    /* renamed from: c, reason: collision with root package name */
    public final androidx.camera.core.impl.E f23421c;

    /* renamed from: d, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.w f23422d;

    /* renamed from: e, reason: collision with root package name */
    public final ArrayList f23423e;

    /* renamed from: f, reason: collision with root package name */
    public final C20013i0 f23424f;

    /* renamed from: g, reason: collision with root package name */
    public final HashMap f23425g = new HashMap();

    public C20028q(@j.N Context context, @j.N androidx.camera.core.impl.F f12, @j.P C20144v c20144v) throws InitializationException {
        String strA;
        this.f23420b = f12;
        androidx.camera.camera2.internal.compat.w wVarA = androidx.camera.camera2.internal.compat.w.a(context, f12.c());
        this.f23422d = wVarA;
        this.f23424f = C20013i0.b(context);
        try {
            ArrayList arrayList = new ArrayList();
            List<String> listAsList = Arrays.asList(wVarA.f23241a.c());
            if (c20144v == null) {
                Iterator it = listAsList.iterator();
                while (it.hasNext()) {
                    arrayList.add((String) it.next());
                }
            } else {
                try {
                    strA = V.a(wVarA, c20144v.c(), listAsList);
                } catch (IllegalStateException unused) {
                    strA = null;
                }
                ArrayList arrayList2 = new ArrayList();
                for (String str : listAsList) {
                    if (!str.equals(strA)) {
                        arrayList2.add(e(str));
                    }
                }
                Iterator it2 = c20144v.b(arrayList2).iterator();
                while (it2.hasNext()) {
                    arrayList.add(((androidx.camera.core.impl.B) ((InterfaceC20142t) it2.next())).k());
                }
            }
            ArrayList arrayList3 = new ArrayList();
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                String str2 = (String) it3.next();
                if (str2.equals("0") || str2.equals("1")) {
                    arrayList3.add(str2);
                } else {
                    if (!"robolectric".equals(Build.FINGERPRINT)) {
                        try {
                            int[] iArr = (int[]) this.f23422d.b(str2).a(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
                            if (iArr != null) {
                                for (int i12 : iArr) {
                                    if (i12 != 0) {
                                    }
                                }
                            }
                            C20140q0.d(3, "Camera2CameraFactory");
                        } catch (CameraAccessExceptionCompat e12) {
                            throw new InitializationException(X.a(e12));
                        }
                    }
                    arrayList3.add(str2);
                }
            }
            this.f23423e = arrayList3;
            C.a aVar = new C.a(this.f23422d);
            this.f23419a = aVar;
            androidx.camera.core.impl.E e13 = new androidx.camera.core.impl.E(aVar);
            this.f23421c = e13;
            aVar.f1846a.add(e13);
        } catch (CameraAccessExceptionCompat e14) {
            throw new InitializationException(X.a(e14));
        } catch (CameraUnavailableException e15) {
            throw new InitializationException(e15);
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20117y
    @j.N
    public final androidx.camera.camera2.internal.compat.w a() {
        return this.f23422d;
    }

    @Override // androidx.camera.core.impl.InterfaceC20117y
    @j.N
    public final CameraInternal b(@j.N String str) throws CameraUnavailableException {
        if (!this.f23423e.contains(str)) {
            throw new IllegalArgumentException("The given camera id is not on the available camera id list.");
        }
        C20045z c20045zE = e(str);
        C.a aVar = this.f23419a;
        androidx.camera.core.impl.F f12 = this.f23420b;
        return new C20039w(this.f23422d, str, c20045zE, aVar, this.f23421c, f12.b(), f12.c(), this.f23424f);
    }

    @Override // androidx.camera.core.impl.InterfaceC20117y
    @j.N
    public final LinkedHashSet c() {
        return new LinkedHashSet(this.f23423e);
    }

    @Override // androidx.camera.core.impl.InterfaceC20117y
    @j.N
    public final C.a d() {
        return this.f23419a;
    }

    public final C20045z e(@j.N String str) throws CameraUnavailableException {
        HashMap map = this.f23425g;
        try {
            C20045z c20045z = (C20045z) map.get(str);
            if (c20045z != null) {
                return c20045z;
            }
            C20045z c20045z2 = new C20045z(str, this.f23422d);
            map.put(str, c20045z2);
            return c20045z2;
        } catch (CameraAccessExceptionCompat e12) {
            throw X.a(e12);
        }
    }
}
