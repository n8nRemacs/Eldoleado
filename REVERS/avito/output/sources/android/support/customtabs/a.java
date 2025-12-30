package android.support.customtabs;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RestrictTo;

/* compiled from: ICustomTabsCallback.java */
@RestrictTo
/* loaded from: classes.dex */
public interface a extends IInterface {

    /* renamed from: c, reason: collision with root package name */
    public static final String f21166c = "android$support$customtabs$ICustomTabsCallback".replace('$', '.');

    /* compiled from: ICustomTabsCallback.java */
    public static class c {
        public static Object a(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        public static void b(Parcel parcel, Parcelable parcelable, int i12) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, i12);
            }
        }
    }

    Bundle C(String str, Bundle bundle);

    void C1(Bundle bundle);

    void F1(Bundle bundle);

    void G2(int i12, int i13, Bundle bundle);

    void K1(String str, Bundle bundle);

    void L1(Bundle bundle);

    void N2(int i12, Bundle bundle);

    void W1(int i12, int i13, int i14, int i15, int i16, Bundle bundle);

    void c4(int i12, Uri uri, boolean z12, Bundle bundle);

    void f1(String str, Bundle bundle);

    void p3(Bundle bundle);

    /* compiled from: ICustomTabsCallback.java */
    public static abstract class b extends Binder implements a {

        /* compiled from: ICustomTabsCallback.java */
        /* renamed from: android.support.customtabs.a$b$a, reason: collision with other inner class name */
        public static class C1507a implements a {

            /* renamed from: a, reason: collision with root package name */
            public IBinder f21167a;

            @Override // android.support.customtabs.a
            public final Bundle C(String str, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f21166c);
                    parcelObtain.writeString(str);
                    c.b(parcelObtain, bundle, 0);
                    this.f21167a.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) c.a(parcelObtain2, Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.a
            public final void C1(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f21166c);
                    c.b(parcelObtain, bundle, 0);
                    this.f21167a.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.a
            public final void F1(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f21166c);
                    c.b(parcelObtain, bundle, 0);
                    this.f21167a.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.a
            public final void G2(int i12, int i13, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f21166c);
                    parcelObtain.writeInt(i12);
                    parcelObtain.writeInt(i13);
                    c.b(parcelObtain, bundle, 0);
                    this.f21167a.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.a
            public final void K1(String str, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f21166c);
                    parcelObtain.writeString(str);
                    c.b(parcelObtain, bundle, 0);
                    this.f21167a.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.a
            public final void L1(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f21166c);
                    c.b(parcelObtain, bundle, 0);
                    this.f21167a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.a
            public final void N2(int i12, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f21166c);
                    parcelObtain.writeInt(i12);
                    c.b(parcelObtain, bundle, 0);
                    this.f21167a.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.a
            public final void W1(int i12, int i13, int i14, int i15, int i16, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f21166c);
                    parcelObtain.writeInt(i12);
                    parcelObtain.writeInt(i13);
                    parcelObtain.writeInt(i14);
                    parcelObtain.writeInt(i15);
                    parcelObtain.writeInt(i16);
                    c.b(parcelObtain, bundle, 0);
                    this.f21167a.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.f21167a;
            }

            @Override // android.support.customtabs.a
            public final void c4(int i12, Uri uri, boolean z12, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f21166c);
                    parcelObtain.writeInt(i12);
                    c.b(parcelObtain, uri, 0);
                    parcelObtain.writeInt(z12 ? 1 : 0);
                    c.b(parcelObtain, bundle, 0);
                    this.f21167a.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.a
            public final void f1(String str, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f21166c);
                    parcelObtain.writeString(str);
                    c.b(parcelObtain, bundle, 0);
                    this.f21167a.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.support.customtabs.a
            public final void p3(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f21166c);
                    c.b(parcelObtain, bundle, 0);
                    this.f21167a.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public b() {
            attachInterface(this, a.f21166c);
        }

        public static a e4(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(a.f21166c);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof a)) {
                return (a) iInterfaceQueryLocalInterface;
            }
            C1507a c1507a = new C1507a();
            c1507a.f21167a = iBinder;
            return c1507a;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i12, Parcel parcel, Parcel parcel2, int i13) {
            String str = a.f21166c;
            if (i12 >= 1 && i12 <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i12 == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            switch (i12) {
                case 2:
                    N2(parcel.readInt(), (Bundle) c.a(parcel, Bundle.CREATOR));
                    return true;
                case 3:
                    f1(parcel.readString(), (Bundle) c.a(parcel, Bundle.CREATOR));
                    return true;
                case 4:
                    L1((Bundle) c.a(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    K1(parcel.readString(), (Bundle) c.a(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    c4(parcel.readInt(), (Uri) c.a(parcel, Uri.CREATOR), parcel.readInt() != 0, (Bundle) c.a(parcel, Bundle.CREATOR));
                    return true;
                case 7:
                    Bundle bundleC = C(parcel.readString(), (Bundle) c.a(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    c.b(parcel2, bundleC, 1);
                    return true;
                case 8:
                    G2(parcel.readInt(), parcel.readInt(), (Bundle) c.a(parcel, Bundle.CREATOR));
                    return true;
                case 9:
                    p3((Bundle) c.a(parcel, Bundle.CREATOR));
                    return true;
                case 10:
                    W1(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (Bundle) c.a(parcel, Bundle.CREATOR));
                    return true;
                case 11:
                    C1((Bundle) c.a(parcel, Bundle.CREATOR));
                    return true;
                case 12:
                    F1((Bundle) c.a(parcel, Bundle.CREATOR));
                    return true;
                default:
                    return super.onTransact(i12, parcel, parcel2, i13);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: ICustomTabsCallback.java */
    /* renamed from: android.support.customtabs.a$a, reason: collision with other inner class name */
    public static class C1506a implements a {
        @Override // android.support.customtabs.a
        public final Bundle C(String str, Bundle bundle) {
            return null;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // android.support.customtabs.a
        public final void C1(Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void F1(Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void L1(Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void p3(Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void K1(String str, Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void N2(int i12, Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void f1(String str, Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void G2(int i12, int i13, Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void c4(int i12, Uri uri, boolean z12, Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void W1(int i12, int i13, int i14, int i15, int i16, Bundle bundle) {
        }
    }
}
