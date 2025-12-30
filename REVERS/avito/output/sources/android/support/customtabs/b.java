package android.support.customtabs;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.customtabs.a;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;

/* compiled from: ICustomTabsService.java */
@RestrictTo
/* loaded from: classes.dex */
public interface b extends IInterface {

    /* renamed from: d, reason: collision with root package name */
    public static final String f21168d = "android$support$customtabs$ICustomTabsService".replace('$', '.');

    /* compiled from: ICustomTabsService.java */
    public static class a implements b {
        @Override // android.support.customtabs.b
        public final boolean B2(long j12) {
            return false;
        }

        @Override // android.support.customtabs.b
        public final boolean J2(android.support.customtabs.a aVar) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // android.support.customtabs.b
        public final boolean l3(android.support.customtabs.a aVar, Bundle bundle) {
            return false;
        }
    }

    /* compiled from: ICustomTabsService.java */
    public static class c {
        public static Object a(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }
    }

    boolean B2(long j12);

    boolean I(int i12, Uri uri, Bundle bundle, android.support.customtabs.a aVar);

    boolean J2(android.support.customtabs.a aVar);

    boolean L2(android.support.customtabs.a aVar, Uri uri, Bundle bundle, ArrayList arrayList);

    Bundle N(Bundle bundle, String str);

    boolean S0(int i12, Uri uri, Bundle bundle, android.support.customtabs.a aVar);

    boolean U(android.support.customtabs.a aVar, Bundle bundle);

    boolean Y3(android.support.customtabs.a aVar, Uri uri);

    void d4(android.support.customtabs.a aVar, Bundle bundle);

    void g2(android.support.customtabs.a aVar, IBinder iBinder, Bundle bundle);

    boolean k3(android.support.customtabs.a aVar, Uri uri, Bundle bundle);

    boolean l3(android.support.customtabs.a aVar, Bundle bundle);

    int z0(android.support.customtabs.a aVar, String str, Bundle bundle);

    /* compiled from: ICustomTabsService.java */
    /* renamed from: android.support.customtabs.b$b, reason: collision with other inner class name */
    public static abstract class AbstractBinderC1508b extends Binder implements b {

        /* compiled from: ICustomTabsService.java */
        /* renamed from: android.support.customtabs.b$b$a */
        public static class a implements b {

            /* renamed from: a, reason: collision with root package name */
            public IBinder f21169a;

            @Override // android.support.customtabs.b
            public final boolean B2(long j12) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21168d);
                    parcelObtain.writeLong(0L);
                    this.f21169a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.b
            public final boolean J2(android.support.customtabs.a aVar) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21168d);
                    parcelObtain.writeStrongInterface(aVar);
                    this.f21169a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.f21169a;
            }

            @Override // android.support.customtabs.b
            public final boolean l3(android.support.customtabs.a aVar, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21168d);
                    parcelObtain.writeStrongInterface(aVar);
                    parcelObtain.writeInt(1);
                    bundle.writeToParcel(parcelObtain, 0);
                    this.f21169a.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public AbstractBinderC1508b() {
            attachInterface(this, b.f21168d);
        }

        public static b e4(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(b.f21168d);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof b)) {
                return (b) iInterfaceQueryLocalInterface;
            }
            a aVar = new a();
            aVar.f21169a = iBinder;
            return aVar;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i12, Parcel parcel, Parcel parcel2, int i13) {
            String str = b.f21168d;
            if (i12 >= 1 && i12 <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i12 == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            switch (i12) {
                case 2:
                    boolean zB2 = B2(parcel.readLong());
                    parcel2.writeNoException();
                    parcel2.writeInt(zB2 ? 1 : 0);
                    return true;
                case 3:
                    boolean zJ2 = J2(a.b.e4(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(zJ2 ? 1 : 0);
                    return true;
                case 4:
                    android.support.customtabs.a aVarE4 = a.b.e4(parcel.readStrongBinder());
                    Uri uri = (Uri) c.a(parcel, Uri.CREATOR);
                    Parcelable.Creator creator = Bundle.CREATOR;
                    boolean zL2 = L2(aVarE4, uri, (Bundle) c.a(parcel, creator), parcel.createTypedArrayList(creator));
                    parcel2.writeNoException();
                    parcel2.writeInt(zL2 ? 1 : 0);
                    return true;
                case 5:
                    Bundle bundleN = N((Bundle) c.a(parcel, Bundle.CREATOR), parcel.readString());
                    parcel2.writeNoException();
                    if (bundleN != null) {
                        parcel2.writeInt(1);
                        bundleN.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 6:
                    boolean zU2 = U(a.b.e4(parcel.readStrongBinder()), (Bundle) c.a(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(zU2 ? 1 : 0);
                    return true;
                case 7:
                    boolean zY3 = Y3(a.b.e4(parcel.readStrongBinder()), (Uri) c.a(parcel, Uri.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(zY3 ? 1 : 0);
                    return true;
                case 8:
                    int iZ02 = z0(a.b.e4(parcel.readStrongBinder()), parcel.readString(), (Bundle) c.a(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(iZ02);
                    return true;
                case 9:
                    boolean zS02 = S0(parcel.readInt(), (Uri) c.a(parcel, Uri.CREATOR), (Bundle) c.a(parcel, Bundle.CREATOR), a.b.e4(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(zS02 ? 1 : 0);
                    return true;
                case 10:
                    boolean zL3 = l3(a.b.e4(parcel.readStrongBinder()), (Bundle) c.a(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(zL3 ? 1 : 0);
                    return true;
                case 11:
                    boolean zK3 = k3(a.b.e4(parcel.readStrongBinder()), (Uri) c.a(parcel, Uri.CREATOR), (Bundle) c.a(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(zK3 ? 1 : 0);
                    return true;
                case 12:
                    android.support.customtabs.a aVarE42 = a.b.e4(parcel.readStrongBinder());
                    boolean zI2 = I(parcel.readInt(), (Uri) c.a(parcel, Uri.CREATOR), (Bundle) c.a(parcel, Bundle.CREATOR), aVarE42);
                    parcel2.writeNoException();
                    parcel2.writeInt(zI2 ? 1 : 0);
                    return true;
                case 13:
                    d4(a.b.e4(parcel.readStrongBinder()), (Bundle) c.a(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(0);
                    return true;
                case 14:
                    g2(a.b.e4(parcel.readStrongBinder()), parcel.readStrongBinder(), (Bundle) c.a(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(0);
                    return true;
                default:
                    return super.onTransact(i12, parcel, parcel2, i13);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
