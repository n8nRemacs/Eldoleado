package a11;

import com.jaredrummler.android.processes.models.AndroidProcess;
import java.util.Comparator;

/* compiled from: AndroidProcesses.java */
/* renamed from: a11.a, reason: case insensitive filesystem */
/* loaded from: classes7.dex */
public class C19666a {

    /* compiled from: AndroidProcesses.java */
    /* renamed from: a11.a$a, reason: collision with other inner class name */
    public static final class C1479a implements Comparator<AndroidProcess> {
        @Override // java.util.Comparator
        public final int compare(AndroidProcess androidProcess, AndroidProcess androidProcess2) {
            return androidProcess.f364305b.compareToIgnoreCase(androidProcess2.f364305b);
        }
    }

    public C19666a() {
        throw new AssertionError("no instances");
    }
}
