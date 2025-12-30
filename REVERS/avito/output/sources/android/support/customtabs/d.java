package android.support.customtabs;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.support.customtabs.a;
import androidx.annotation.RestrictTo;

/* compiled from: IPostMessageService.java */
@RestrictTo
/* loaded from: classes.dex */
public interface d extends IInterface {

    /* renamed from: f, reason: collision with root package name */
    public static final String f21172f = "android$support$customtabs$IPostMessageService".replace('$', '.');

    /* compiled from: IPostMessageService.java */
    public static class a implements d {
        @Override // android.support.customtabs.d
        public final void G(android.support.customtabs.a aVar, Bundle bundle) {
            throw null;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }
    }

    /* compiled from: IPostMessageService.java */
    public static class c {
    }

    void G(android.support.customtabs.a aVar, Bundle bundle);

    void P3(android.support.customtabs.a aVar, String str, Bundle bundle);

    /* compiled from: IPostMessageService.java */
    public static abstract class b extends Binder implements d {

        /* compiled from: IPostMessageService.java */
        public static class a implements d {

            /* renamed from: a, reason: collision with root package name */
            public IBinder f21173a;

            @Override // android.support.customtabs.d
            public final void G(android.support.customtabs.a aVar, Bundle bundle) {
                throw null;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.f21173a;
            }
        }

        public b() {
            attachInterface(this, d.f21172f);
        }

        public static d e4(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(d.f21172f);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof d)) {
                return (d) iInterfaceQueryLocalInterface;
            }
            a aVar = new a();
            aVar.f21173a = iBinder;
            return aVar;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i12, Parcel parcel, Parcel parcel2, int i13) {
            String str = d.f21172f;
            if (i12 >= 1 && i12 <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i12 == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i12 == 2) {
                G(a.b.e4(parcel.readStrongBinder()), (Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                parcel2.writeNoException();
            } else {
                if (i12 != 3) {
                    return super.onTransact(i12, parcel, parcel2, i13);
                }
                P3(a.b.e4(parcel.readStrongBinder()), parcel.readString(), (Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
