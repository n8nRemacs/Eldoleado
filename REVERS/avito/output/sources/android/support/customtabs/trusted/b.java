package android.support.customtabs.trusted;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import androidx.annotation.RestrictTo;

/* compiled from: ITrustedWebActivityService.java */
@RestrictTo
/* loaded from: classes.dex */
public interface b extends IInterface {

    /* renamed from: h, reason: collision with root package name */
    public static final String f21176h = "android$support$customtabs$trusted$ITrustedWebActivityService".replace('$', '.');

    /* compiled from: ITrustedWebActivityService.java */
    public static class a implements b {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }
    }

    /* compiled from: ITrustedWebActivityService.java */
    public static class c {
    }

    Bundle G3();

    int R3();

    Bundle S(Bundle bundle);

    Bundle S3(Bundle bundle);

    void V3(Bundle bundle);

    Bundle h3();

    void n2(IBinder iBinder);

    /* compiled from: ITrustedWebActivityService.java */
    /* renamed from: android.support.customtabs.trusted.b$b, reason: collision with other inner class name */
    public static abstract class AbstractBinderC1512b extends Binder implements b {

        /* compiled from: ITrustedWebActivityService.java */
        /* renamed from: android.support.customtabs.trusted.b$b$a */
        public static class a implements b {
            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return null;
            }
        }

        public AbstractBinderC1512b() {
            attachInterface(this, b.f21176h);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i12, Parcel parcel, Parcel parcel2, int i13) {
            String str = b.f21176h;
            if (i12 >= 1 && i12 <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i12 == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            switch (i12) {
                case 2:
                    Bundle bundleS = S((Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                    parcel2.writeNoException();
                    parcel2.writeInt(1);
                    bundleS.writeToParcel(parcel2, 1);
                    return true;
                case 3:
                    V3((Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int iR3 = R3();
                    parcel2.writeNoException();
                    parcel2.writeInt(iR3);
                    return true;
                case 5:
                    Bundle bundleG3 = G3();
                    parcel2.writeNoException();
                    parcel2.writeInt(1);
                    bundleG3.writeToParcel(parcel2, 1);
                    return true;
                case 6:
                    Bundle bundleS3 = S3((Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                    parcel2.writeNoException();
                    parcel2.writeInt(1);
                    bundleS3.writeToParcel(parcel2, 1);
                    return true;
                case 7:
                    Bundle bundleH3 = h3();
                    parcel2.writeNoException();
                    parcel2.writeInt(1);
                    bundleH3.writeToParcel(parcel2, 1);
                    return true;
                case 8:
                default:
                    return super.onTransact(i12, parcel, parcel2, i13);
                case 9:
                    parcel.readString();
                    n2(parcel.readStrongBinder());
                    parcel2.writeNoException();
                    parcel2.writeInt(0);
                    return true;
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
