package android.support.customtabs.trusted;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import androidx.annotation.RestrictTo;

/* compiled from: ITrustedWebActivityCallback.java */
@RestrictTo
/* loaded from: classes.dex */
public interface a extends IInterface {

    /* renamed from: g, reason: collision with root package name */
    public static final String f21174g = "android$support$customtabs$trusted$ITrustedWebActivityCallback".replace('$', '.');

    /* compiled from: ITrustedWebActivityCallback.java */
    /* renamed from: android.support.customtabs.trusted.a$a, reason: collision with other inner class name */
    public static class C1510a implements a {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }
    }

    /* compiled from: ITrustedWebActivityCallback.java */
    public static class c {
    }

    void r1();

    /* compiled from: ITrustedWebActivityCallback.java */
    public static abstract class b extends Binder implements a {

        /* compiled from: ITrustedWebActivityCallback.java */
        /* renamed from: android.support.customtabs.trusted.a$b$a, reason: collision with other inner class name */
        public static class C1511a implements a {

            /* renamed from: a, reason: collision with root package name */
            public IBinder f21175a;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.f21175a;
            }
        }

        public b() {
            attachInterface(this, a.f21174g);
        }

        public static a e4(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(a.f21174g);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof a)) {
                return (a) iInterfaceQueryLocalInterface;
            }
            C1511a c1511a = new C1511a();
            c1511a.f21175a = iBinder;
            return c1511a;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i12, Parcel parcel, Parcel parcel2, int i13) {
            String str = a.f21174g;
            if (i12 >= 1 && i12 <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i12 == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i12 != 2) {
                return super.onTransact(i12, parcel, parcel2, i13);
            }
            parcel.readString();
            r1();
            throw null;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
