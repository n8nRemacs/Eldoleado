package androidx.camera.core.impl;

import androidx.camera.core.impl.F0;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: MutableStateObservable.java */
@j.X
/* loaded from: classes.dex */
public class o0<T> extends F0<T> {
    public final void a(@j.P T t12) {
        Iterator<F0.b<T>> it;
        int i12;
        synchronized (this.f23950a) {
            try {
                if (Objects.equals(this.f23951b.getAndSet(t12), t12)) {
                    return;
                }
                int i13 = this.f23952c + 1;
                this.f23952c = i13;
                if (this.f23953d) {
                    return;
                }
                this.f23953d = true;
                Iterator<F0.b<T>> it2 = this.f23955f.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        it2.next().a(i13);
                    } else {
                        synchronized (this.f23950a) {
                            try {
                                if (this.f23952c == i13) {
                                    this.f23953d = false;
                                    return;
                                } else {
                                    it = this.f23955f.iterator();
                                    i12 = this.f23952c;
                                }
                            } finally {
                            }
                        }
                        it2 = it;
                        i13 = i12;
                    }
                }
            } finally {
            }
        }
    }
}
