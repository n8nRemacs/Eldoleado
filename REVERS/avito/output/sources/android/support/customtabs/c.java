package android.support.customtabs;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import androidx.annotation.RestrictTo;

/* compiled from: IEngagementSignalsCallback.java */
@RestrictTo
/* loaded from: classes.dex */
public interface c extends IInterface {

    /* renamed from: e, reason: collision with root package name */
    public static final String f21170e = "android$support$customtabs$IEngagementSignalsCallback".replace('$', '.');

    /* compiled from: IEngagementSignalsCallback.java */
    public static class a implements c {
        @Override // android.support.customtabs.c
        public final void E3() {
            throw null;
        }

        @Override // android.support.customtabs.c
        public final void O0() {
            throw null;
        }

        @Override // android.support.customtabs.c
        public final void Y() {
            throw null;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }
    }

    /* compiled from: IEngagementSignalsCallback.java */
    /* renamed from: android.support.customtabs.c$c, reason: collision with other inner class name */
    public static class C1509c {
    }

    void E3();

    void O0();

    void Y();

    /* compiled from: IEngagementSignalsCallback.java */
    public static abstract class b extends Binder implements c {

        /* compiled from: IEngagementSignalsCallback.java */
        public static class a implements c {

            /* renamed from: a, reason: collision with root package name */
            public IBinder f21171a;

            @Override // android.support.customtabs.c
            public final void E3() {
                throw null;
            }

            @Override // android.support.customtabs.c
            public final void O0() {
                throw null;
            }

            @Override // android.support.customtabs.c
            public final void Y() {
                throw null;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.f21171a;
            }
        }

        public b() {
            attachInterface(this, c.f21170e);
        }

        public static c e4(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(c.f21170e);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof c)) {
                return (c) iInterfaceQueryLocalInterface;
            }
            a aVar = new a();
            aVar.f21171a = iBinder;
            return aVar;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i12, Parcel parcel, Parcel parcel2, int i13) {
            String str = c.f21170e;
            if (i12 >= 1 && i12 <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i12 == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i12 == 2) {
                parcel.readInt();
                Y();
                throw null;
            }
            if (i12 == 3) {
                parcel.readInt();
                E3();
                throw null;
            }
            if (i12 != 4) {
                return super.onTransact(i12, parcel, parcel2, i13);
            }
            parcel.readInt();
            O0();
            throw null;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
